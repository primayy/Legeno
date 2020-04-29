package com.example.billage.api.data.transaction;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ApiCallTransactionResponse implements Parcelable {
    private String api_tran_id;
    private String api_tran_dtm;
    private String rsp_code;
    private String rsp_message;
    private String bank_tran_id;
    private String bank_tran_date;
    private String bank_code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;

    // 거래내역조회
    private String fintech_use_num;

    // 송금인정보조회
    private String bank_code_std;
    private String account_num;

    private String balance_amt;
    private String page_record_cnt;
    private String next_page_yn;
    private String befor_inquiry_trace_info;
    private ArrayList<Transaction> res_list;

    protected ApiCallTransactionResponse(Parcel in) {
        api_tran_id = in.readString();
        api_tran_dtm = in.readString();
        rsp_code = in.readString();
        rsp_message = in.readString();
        bank_tran_id = in.readString();
        bank_tran_date = in.readString();
        bank_code_tran = in.readString();
        bank_rsp_code = in.readString();
        bank_rsp_message = in.readString();
        fintech_use_num = in.readString();
        bank_code_std = in.readString();
        account_num = in.readString();
        balance_amt = in.readString();
        page_record_cnt = in.readString();
        next_page_yn = in.readString();
        befor_inquiry_trace_info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(api_tran_id);
        dest.writeString(api_tran_dtm);
        dest.writeString(rsp_code);
        dest.writeString(rsp_message);
        dest.writeString(bank_tran_id);
        dest.writeString(bank_tran_date);
        dest.writeString(bank_code_tran);
        dest.writeString(bank_rsp_code);
        dest.writeString(bank_rsp_message);
        dest.writeString(fintech_use_num);
        dest.writeString(bank_code_std);
        dest.writeString(account_num);
        dest.writeString(balance_amt);
        dest.writeString(page_record_cnt);
        dest.writeString(next_page_yn);
        dest.writeString(befor_inquiry_trace_info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiCallTransactionResponse> CREATOR = new Creator<ApiCallTransactionResponse>() {
        @Override
        public ApiCallTransactionResponse createFromParcel(Parcel in) {
            return new ApiCallTransactionResponse(in);
        }

        @Override
        public ApiCallTransactionResponse[] newArray(int size) {
            return new ApiCallTransactionResponse[size];
        }
    };
}
