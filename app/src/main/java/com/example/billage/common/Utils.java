package com.example.billage.common;

import android.annotation.SuppressLint;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    //오늘 날짜 및 시간을 20200430163945 형태로 표현
    public static String getDate(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String today = simpleDate.format(mDate);

        return today;
    }

    // 현재 시간/분/초/밀리세컨드를 9자리 문자열로 표현
    public static String getCurrentTime() {
        return new SimpleDateFormat("HHmmssSSS", Locale.KOREA).format(new Date());
    }

    public static String setRandomBankTranId() {
        String clientUseCode = AppData.getPref().getString("client_use_code","");
        String randomUnique9String = getCurrentTime();    // 이용기관 부여번호를 임시로 시간데이터 사용
        String result = String.format("%sU%s", clientUseCode, randomUnique9String);

        return result;
    }

}
