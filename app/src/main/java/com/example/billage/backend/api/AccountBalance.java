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

public class AccountBalance {
    public static Integer amtSum=0;
    public static void request_balance(String fintech_num){
        String accessToken = AppData.getPref().getString("access_token","");
        HashMap<String, String> paramMap = new HashMap<>();

        //요청시 필요한 parameters
        paramMap.put("bank_tran_id", Utils.setRandomBankTranId());//거래고유번호(참가은행)
        paramMap.put("tran_dtime", Utils.getDate());//요청일시 YYYYMMDDHHMMSS
        //핀테크 이용
        paramMap.put("fintech_use_num", fintech_num);//거래고유번호(참가은행)

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

                                //api 호출한 잔액
                                String amt = amt_elem.getAsString();
                                Log.d("balance_amt", amt);

                                //앱데이터 잔액 읽기
                                String appAmt = AppData.getPref().getString("balance","");

                                if(appAmt.equals("")){ //저장된 값 없으면 새로 추가
                                    SharedPreferences.Editor editor = AppData.getPref().edit();
                                    editor.putString("balance",amt);
                                    editor.apply();
                                } else // 저장된 값 있으면 읽어서 더한 뒤 추가
                                {
                                    amtSum += Integer.parseInt(amt);
                                    SharedPreferences.Editor editor = AppData.getPref().edit();
                                    editor.putString("balance", String.valueOf(amtSum));
                                    editor.apply();
                                }
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
