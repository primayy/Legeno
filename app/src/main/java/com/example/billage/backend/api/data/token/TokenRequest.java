package com.example.billage.backend.api.data.token;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.billage.backend.common.AppData;
import com.example.billage.backend.http.ApiCallAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRequest {
    public static void request_token(){
        String client_id = "dukjFhxkn3ji94NDLBzGCZwMomK526dfK9M6cIM8";
        String client_secret = "2zsTRQkDSavQP170dpTZmvbnDzEZNT61NFCTEnT7";
        String auth_code = "m0WMfi2oSHfBk0SyDrfMAupzsoGiCD"; //user별 코드 받도록 해야함
        HashMap<String, String> paramMap = new HashMap<>();

        paramMap.put("code", auth_code);
        paramMap.put("client_id", client_id);
        paramMap.put("client_secret", client_secret);
//        paramMap.put("scope", "oob");   // 고정값
//        paramMap.put("grant_type", "client_credentials");   // 고정값
        paramMap.put("grant_type", "authorization_code");   // 고정값
        paramMap.put("redirect_uri", "https://localhost:8890/callback");   // 고정값

        ApiCallAdapter.getInstance()
                .token(paramMap)
                .enqueue(new Callback<Map>(){
                    @Override
                    public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                        if(response.isSuccessful()){
                            String responseJson = new Gson().toJson(response.body());
                            Log.d("token",responseJson);

                            //json 파싱
                            JsonObject json = new JsonParser().parse(responseJson).getAsJsonObject();
                            JsonElement token = json.get("access_token");
                            String value = token.getAsString();
                            Log.d("token",value);

                            JsonElement client_use_code = json.get("client_use_code");
                            String cl_use_code = client_use_code.getAsString();

                            //앱데이터에 저장
                            SharedPreferences.Editor editor = AppData.getPref().edit();
                            editor.putString("access_token",value);
                            editor.putString("client_use_code",cl_use_code);

                            editor.apply();



                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {
                        System.out.println("통신실패: 서버 접속에 실패하였습니다.");
                    }
                });
    }
}
