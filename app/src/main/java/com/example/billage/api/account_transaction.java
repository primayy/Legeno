package com.example.billage.api;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.billage.http.ApiCallAdapter;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_transaction {
    private HashMap<String, String> paramMap;
    private Bundle args;

    public EditText fromTime; //조회시작시간 20200428
    public EditText toTime; //조회종료시간 20200429

    //거래내역 요청
    //값 변수로 넣을 수 있게 수정해야함
    void request_transaction(){
        //요청시 필요한 parameters
        paramMap.put("bank_tran_id", "00");//거래고유번호(참가은행)
        paramMap.put("inquiry_type", "A");//조회구분코드
        paramMap.put("inquiry_base", "T");//조회기준코드
        paramMap.put("from_date", "20200428");//조회시작일자
        paramMap.put("to_date", "20200428");//조회종료일자
        paramMap.put("sort_order", "D");//정렬순서
        paramMap.put("tran_dtime", "20200428101921");//요청일시 YYYYMMDDHHMMSS
        paramMap.put("befor_inquiry_trace_info", "20");//직전조회추적정보

//        if(true){ // fintech 번호 사용시
//            paramMap.put("fintech_use_num", getRandomBankTranId());//거래고유번호(참가은행)
//        } else { // 계좌번호 사용시
//            paramMap.put("bank_code_std", bankCode);
//            paramMap.put("account_num", accountNum);
//            paramMap.put("user_seq_no", userSeqNo);
//            ApiCallAdapter.getInstance()
//                    .accountTrasactionListAcntNum("Bearer" + accessToken, paramMap)
//        }

    }
}
