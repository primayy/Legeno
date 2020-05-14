package com.example.billage.backend.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
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

public class Account_balance {
    public static void request_balance(){
//        String accessToken = AppData.getPref().getString("access_token","");
        //사용자 인증 후 발급 받은 토큰으로 변경해야함. 사용자별로 다름
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzU4NDkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1OTYwMjM0NDgsImp0aSI6IjZmYzg2ZmIyLWI1MDUtNGNlZS04Yjg1LWE3YmYyZTJlZmZiMiJ9.nEM9xPjedWztnQ8BPKR3m5lMDJ1imwt04rGFIJcYYuc";

        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("bank_tran_id", Utils.setRandomBankTranId());//거래고유번호(참가은행)
        paramMap.put("tran_dtime", Utils.getDate());//요청일시 YYYYMMDDHHMMSS
        //핀테크 이용
        paramMap.put("fintech_use_num", "199162081057883042588959");//거래고유번호(참가은행)

        //요청 부분
        ApiCallAdapter.getInstance()
                .accountBalanceFinNum("Bearer" + accessToken, paramMap)
                .enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                        if(response.isSuccessful()){
                            try {
                                String responseJson = new Gson().toJson(response.body());
                                JsonObject json = new JsonParser().parse(responseJson).getAsJsonObject();
                                JsonElement amt_elem = json.get("balance_amt");
                                String amt = amt_elem.getAsString();
                                Log.d("balance_amt", amt);

                                SharedPreferences.Editor editor = AppData.getPref().edit();
                                editor.putString("balance",amt);
                                editor.apply();
                            } catch (Exception e){
                                Log.d("balance_err", String.valueOf(e));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {
                        System.out.println("통신실패: 서버 접속에 실패하였습니다.");
                    }
                });
    }
}
