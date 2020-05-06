package com.example.billage.api;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.billage.common.AppData;
import com.example.billage.common.DbOpenHelper;
import com.example.billage.common.Utils;
import com.example.billage.http.ApiCallAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account_transaction {

    //거래내역 요청
    //값 변수로 넣을 수 있게 수정해야함
    public static void request_transaction(String from_date, String to_date){
//        String accessToken = AppData.getPref().getString("access_token","");
        //사용자 인증 후 발급 받은 토큰으로 변경해야함. 사용자별로 다름
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzU4NDkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1OTYwMjM0NDgsImp0aSI6IjZmYzg2ZmIyLWI1MDUtNGNlZS04Yjg1LWE3YmYyZTJlZmZiMiJ9.nEM9xPjedWztnQ8BPKR3m5lMDJ1imwt04rGFIJcYYuc";

        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("bank_tran_id", Utils.setRandomBankTranId());//거래고유번호(참가은행)
        paramMap.put("inquiry_type", "A");//조회구분코드
        paramMap.put("inquiry_base", "D");//조회기준코드
        paramMap.put("from_date", from_date);//조회시작일자
        paramMap.put("to_date", to_date);//조회종료일자
        paramMap.put("sort_order", "D");//정렬순서
        paramMap.put("tran_dtime", Utils.getDate());//요청일시 YYYYMMDDHHMMSS
//        paramMap.put("befor_inquiry_trace_info", "20");//직전조회추적정보 20개씩

        //핀테크 이용
        paramMap.put("fintech_use_num", "199162081057883042588959");//거래고유번호(참가은행)

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
                            Log.d("tran_size", String.valueOf(value.size()));

                            for(int i = 0 ; i<value.size(); i++){
                                JsonObject trans = value.get(i).getAsJsonObject();
                                JsonElement tran_date = trans.get("tran_date");
                                JsonElement inout_type = trans.get("inout_type");
                                JsonElement tran_amt = trans.get("tran_amt");
                                JsonElement branch_name = trans.get("branch_name");
                                Log.d("tran",tran_date.getAsString()+" "+inout_type.getAsString()+" "+tran_amt.getAsString()+" "+branch_name.getAsString());
                                AppData.mdb.insertTransColumn(tran_date.getAsString(),branch_name.getAsString(),tran_amt.getAsString(),inout_type.getAsString());
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
