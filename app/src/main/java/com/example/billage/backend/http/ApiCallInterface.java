package com.example.billage.backend.http;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiCallInterface {
    // 본인확인 웹뷰 호출시(사용자인증)
    String authorizeUrl = "/oauth/2.0/authorize";

    // 본인확인 웹뷰 호출시(계좌등록확인)
    String authorizeAccountUrl = "/oauth/2.0/authorize_account";

    // 본인확인 웹뷰 호출전 정상유무 테스트용
    @GET(authorizeUrl)
    Call<Map> authorize(@HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> params);

    // access_token 획득
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<Map> token(@FieldMap Map<String, String> params);

    // 계좌등록
    @POST("/v2.0/user/register")
    Call<Map> userRegister(@Header("Authorization") String token, @Body Map<String, String> params);

    // 사용자 정보조회
    @GET("/v2.0/user/me")
    Call<Map> userMe(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 잔액조회(핀테크이용번호 사용)
    @GET("/v2.0/account/balance/fin_num")
    Call<Map> accountBalanceFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 잔액조회(계좌번호 사용, 자체인증)
    @POST("/v2.0/account/balance/acnt_num")
    Call<Map> accountBalanceAcntNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 거래내역조회(핀테크이용번호 사용)
    @GET("/v2.0/account/transaction_list/fin_num")
    Call<Map> accountTrasactionListFinNum(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 거래내역조회(계좌번호 사용)
    @POST("/v2.0/account/transaction_list/acnt_num")
    Call<Map> accountTrasactionListAcntNum(@Header("Authorization") String token, @Body Map<String, String> params);

    // 계좌실명조회
    @POST("/v2.0/inquiry/real_name")
    Call<Map> inquiryRealName(@Header("Authorization") String token, @Body Map<String, String> params);

    // 등록계좌조회
    @GET("/v2.0/account/list")
    Call<Map> accountList(@Header("Authorization") String token, @QueryMap Map<String, String> params);

    // 송금인정보조회
    @POST("/v2.0/inquiry/remit_list")
    Call<Map> inquiryRemitList(@Header("Authorization") String token, @Body Map<String, String> params);

    // 수취조회
    @POST("/v2.0/inquiry/receive")
    Call<Map> inquiryReceive(@Header("Authorization") String token, @Body Map<String, String> params);
}
