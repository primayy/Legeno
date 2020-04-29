package com.example.billage.api.data.token;

import com.example.billage.http.ApiCallAdapter;
import com.google.gson.JsonArray;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRequest {
    public static void request_token(){
        String client_id = "5kCPktYvfvDDKT00EFPfbbaKgcK2ZzHgQl567QDb";
        String client_secret = "tJFSvMaW1eybSx9l2ZG43c5ozfaAehWMJWFStMtl";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", client_id);
        paramMap.put("client_secret", client_secret);
        paramMap.put("scope", "oob");   // 고정값
        paramMap.put("grant_type", "client_credentials");   // 고정값

        ApiCallAdapter.getInstance()
                .token(paramMap)
                .enqueue(new Callback<Map>(){
                    @Override
                    public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                        if(response.isSuccessful()){
                            Map result = response.body();
                            System.out.println(result);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {
                        System.out.println("통신실패: 서버 접속에 실패하였습니다.");
                    }
                });
    }

}
