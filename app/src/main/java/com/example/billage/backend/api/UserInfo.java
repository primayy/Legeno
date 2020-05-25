package com.example.billage.backend.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.billage.backend.api.data.UserAccount;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.example.billage.backend.http.ApiCallAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo {
    public static void request_userInfo(){
        String accessToken = AppData.getPref().getString("access_token","");
        ArrayList<String> user_accounts = new ArrayList<String>();

        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("user_seq_no", AppData.getPref().getString("user_seq_no",""));//사용자 일련번호

        ApiCallAdapter.getInstance()
                .userMe("Bearer" + accessToken, paramMap)
                .enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                        if(response.isSuccessful()){
                            try {
                                String responseJson = new Gson().toJson(response.body());
                                JsonObject json = new JsonParser().parse(responseJson).getAsJsonObject();

                                //사용자 이름
                                JsonElement name_elem = json.get("user_name");
                                String name = name_elem.getAsString();
                                //등록된 계좌 수
                                JsonElement acc_count_elem = json.get("res_cnt");
                                String acc_count = acc_count_elem.getAsString();

                                //등록된 계좌 목록
                                JsonArray acc_list = json.getAsJsonArray("res_list");

                                for(int i = 0 ; i<acc_list.size(); i++){
                                    JsonObject acc = acc_list.get(i).getAsJsonObject();
                                    JsonElement acc_fintechNum = acc.get("fintech_use_num");
                                    JsonElement acc_bankCode = acc.get("bank_code_std");
                                    JsonElement acc_bankName = acc.get("bank_name");
                                    JsonElement acc_num = acc.get("account_num_masked");
                                    Log.d("acc_info",acc_fintechNum.getAsString()+" "+acc_bankCode.getAsString()+" "+acc_bankName.getAsString()+" "+acc_num.getAsString());

                                    user_accounts.add(acc_fintechNum.getAsString()+","+acc_bankCode.getAsString()+","+acc_bankName.getAsString()+","+acc_num.getAsString());
                                }
                                //앱데이터에 저장
                                SharedPreferences.Editor editor = AppData.getPref().edit();
                                editor.putString("user_name",name);

                                JSONArray tmp = new JSONArray();
                                for (int i=0; i<user_accounts.size(); i++){
                                    tmp.put(user_accounts.get(i));
                                }
                                if (!user_accounts.isEmpty()) {
                                    editor.putString("user_accounts", tmp.toString());
                                } else {
                                    editor.putString("user_accounts", null);
                                }
                                editor.apply();

                            } catch (Exception e){
                                Log.d("user_info_err", String.valueOf(e));
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
