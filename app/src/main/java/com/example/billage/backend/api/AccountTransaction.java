package com.example.billage.backend.api;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.example.billage.backend.http.ApiCallAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountTransaction {
    //거래내역 요청
    public static void request_transaction(String fintech_use_num){
        String accessToken = AppData.getPref().getString("access_token","");

        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("bank_tran_id", Utils.setRandomBankTranId());//거래고유번호(참가은행)
        paramMap.put("inquiry_type", "A");//조회구분코드
        paramMap.put("inquiry_base", "D");//조회기준코드
        paramMap.put("from_date", "19990101");//조회시작일자
        paramMap.put("to_date", Utils.getDay());//조회종료일자
        paramMap.put("sort_order", "D");//정렬순서
        paramMap.put("tran_dtime", Utils.getDate());//요청일시 YYYYMMDDHHMMSS

        //핀테크 이용
        paramMap.put("fintech_use_num", fintech_use_num);//거래고유번호(참가은행)

        //요청 부분
        ApiCallAdapter.getInstance()
                .accountTrasactionListFinNum("Bearer" + accessToken, paramMap)
                .enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                        if(response.isSuccessful()){
                            String responseJson = new Gson().toJson(response.body());
                            JsonObject json = new JsonParser().parse(responseJson).getAsJsonObject();
                            JsonArray value = json.getAsJsonArray("res_list");
                            try{
                                Log.d("tran_size", String.valueOf(value.size()));
                                for(int i = 0 ; i<value.size(); i++){
                                    JsonObject trans = value.get(i).getAsJsonObject();
                                    JsonElement tran_date = trans.get("tran_date");
                                    JsonElement tran_time = trans.get("tran_time");
                                    JsonElement inout_type = trans.get("inout_type");
                                    JsonElement tran_amt = trans.get("tran_amt");
                                    JsonElement branch_name = trans.get("branch_name");
                                    JsonElement bank_code = json.get("bank_code_tran");
                                    Log.d("tran",tran_date.getAsString()+" "+inout_type.getAsString()+" "+tran_amt.getAsString()+" "+branch_name.getAsString()+" "+bank_code.getAsString());
                                    //앱 로컬db에 거래 일자, 거래시간, 거래장소, 금액, 거래 타입을 저장
                                    AppData.mdb.insertTransColumn(tran_date.getAsString(),tran_time.getAsString(), branch_name.getAsString(),tran_amt.getAsString(),inout_type.getAsString(),bank_code.getAsString(),"api");
                                }
                            } catch(Exception e){

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
