package com.example.billage.frontend.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.billage.R;
import com.example.billage.backend.api.Authorize;
import com.example.billage.backend.api.TokenRequest;
import com.example.billage.backend.common.ApiConst;
import com.example.billage.backend.common.Utils;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    // data
    private Bundle args;
    private String urlToLoad;
    private String state;
    private Map<String, String> headerMap;

    ////인증서 관련
    // CallBack FilePath
    private ValueCallback<Uri[]> filePath;
    // 인증서 저장 기본 디렉토리(변경가능)
    private static final String CERT_DEFAULT_DIR = "NPKI/yessign/USER";
    // 인증서 파일 전송 기본 mime-type
    private static final String CERT_DEFAULT_MIME_TYPE = "*/*";
    // 인증서 가져오기 타이틀
    private static final String CERT_INTENT_TITLE = "인증서 가져오기";
    // 인증서 관련 user agent
    private static final String CERT_USER_AGENT_YESSIGN_ANDROID = "\u0020yessign/wv/A-8GYT7FZ5Qb37lB4Ud4Lt;1.0";
    //// 인증서 끝

    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_webview);
        args = Authorize.request_authorize();
        urlToLoad = args.getString("URL_TO_LOAD", "");
        state = args.getString("STATE", "");
        headerMap = (Map<String, String>) args.getSerializable("headerMap");
        webView = (WebView) findViewById(R.id.webView);
        webSettings = webView.getSettings();

        loadUrlOnWebView();
    }

    void loadUrlOnWebView() {
        // 웹뷰 시작
        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultTextEncodingName("UTF-8");

        ////////// 브라우저 인증서 사용을 위한 옵션 시작 //////////
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // 일부폰에서 접근성 설정에 의한 css 깨짐 발생방지
        webSettings.setTextZoom(100);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 모바일 브라우저와 웹뷰(하이브리드 앱)를 구분하기 위한 UserAgent 설정
        StringBuffer sb = new StringBuffer(webSettings.getUserAgentString()).append(CERT_USER_AGENT_YESSIGN_ANDROID);
        webSettings.setUserAgentString(sb.toString());
        ////////// 브라우저 인증서 사용을 위한 옵션 끝 //////////

        webView.setWebChromeClient(new WebChromeClient(){
            // 브라우저 인증서 사용을 위해, 새창을 띄우기 위한 설정(자세히보기, 개인정보수집이용 약관 등)
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(getApplicationContext());
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                        return true;
                    }
                });
                return true;
            }

            // 인증서를 파일탐색기에서 가져오기 위한 설정
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (filePath != null) {
                    filePath.onReceiveValue(null);
                    filePath = null;
                }
                filePath = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                String dirStr = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CERT_DEFAULT_DIR + File.separator;
                //String dirStr = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + CERT_DEFAULT_DIR + File.separator;
                Uri uri = Uri.parse(dirStr);
                intent.setDataAndType(uri, CERT_DEFAULT_MIME_TYPE);
                startActivityForResult(Intent.createChooser(intent, CERT_INTENT_TITLE), 0);
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Timber.d("#### (AUTH) onConsoleMessage : %d-%s-%s", consoleMessage.lineNumber(), consoleMessage.message(), consoleMessage.sourceId());
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*
                 * AuthorizationCode 발급이 완료된 이후에, 해당 코드를 사용하여 토큰발급까지의 흐름을 UI상에 보여주기 위해서 추가한 코드 예시
                 * 이용기관에서는 redirect uri 에 해당하는 페이지에서 에러처리를 해야한다.
                 */
                String callbackUrl = ApiConst.CALLBACK_URL;

                if(url.startsWith(callbackUrl)){

                    // error 코드가 있을 경우 에러처리
                    String error = Utils.getParamValFromUrlString(url, "error");
                    if (!error.isEmpty()) {
                        String errorDesc = Utils.getParamValFromUrlString(url, "error_description");
                        if (errorDesc.isEmpty()) {
                            errorDesc = "알 수 없는 오류가 발생하였습니다.";
                        }
                        Log.d("tetete1",errorDesc);
//                        showAlert("인증 에러코드 : " + error, Utils.urlDecode(errorDesc), url, (dialog, which) -> onBackPressed());
                        return true;
                    }

                    // error 코드가 없으면 토큰발급으로 이동
                    String code = Utils.getParamValFromUrlString(url, "code");
                    String scope = Utils.getParamValFromUrlString(url, "scope");
                    String client_info = Utils.getParamValFromUrlString(url, "client_info");

                    // 요청시 이용기관이 세팅한 state 값을 그대로 전달받는 것으로, 이용기관은 CSRF 보안위협에 대응하기 위해 요청 시의 state 값과 응답 시의 state 값을 비교해야 함
                    String returnState = Utils.getParamValFromUrlString(url, "state");
                    if (!returnState.equals(state)) {
//                        showAlert("상태난수값 오류", "보낸 난수값과 서버로부터 받은 난수값이 서로 일치하지 않습니다.", "보낸 난수값: " + state + "\n\n받은 난수값: " + returnState);
                        return true;
                    }
                    args.putString("code", code);
                    args.putString("scope", scope);
                    args.putString("client_info", client_info);
                    args.putString("state", state);

                    TokenRequest.request_token(args);
                    finish();
                    return true;
                }

                // PASS intent URI 호출 지원(앱이 설치되어있으면 앱이 실행되도록 하기 위함)
                if (url.startsWith("intent://") || url.startsWith("tauthlink://") || url.startsWith("ktauthexternalcall://") || url.startsWith("upluscorporation://")) {
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        startActivity(intent);
                    } catch (Exception e) {
//                        Timber.e(e);
                    }
                    return true;
                }

                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    Timber.d("#### (AUTH) onReceivedError: %s, %s", error.getErrorCode(), error.getDescription());
                } else {
//                    Timber.d("#### (AUTH) onReceivedError");
                }
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
                super.onReceivedLoginRequest(view, realm, account, args);
//                Timber.d("#### (AUTH) onRreceivedLoginRequest");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                Timber.d("#### (AUTH) onReceivedSslError : %s", error.getCertificate().toString());

                // 테스트에서만 사용
                handler.proceed(); // Ignore SSL certificate errors
            }
        });

        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.loadUrl(urlToLoad, headerMap);
    }

}
