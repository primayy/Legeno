package com.example.billage.api.data.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiCallTransactionRequest implements Parcelable {
    private String bank_tran_id;
    private String fintech_use_num;
    private String inquiry_type;
    private String inquiry_base;
    private String from_date;
    private String to_date;
    private String sort_order;
    private String tran_dtime;
    private String befor_inquiry_trace_info;

    protected ApiCallTransactionRequest(Parcel in) {
    }

    public static final Creator<ApiCallTransactionRequest> CREATOR = new Creator<ApiCallTransactionRequest>() {
        @Override
        public ApiCallTransactionRequest createFromParcel(Parcel in) {
            return new ApiCallTransactionRequest(in);
        }

        @Override
        public ApiCallTransactionRequest[] newArray(int size) {
            return new ApiCallTransactionRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
