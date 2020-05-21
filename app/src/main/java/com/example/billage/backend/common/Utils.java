package com.example.billage.backend.common;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //오늘이 무슨 요일인지 반환 일요일이 1
    public static int getDayOfWeek() throws ParseException {
        String now = getDate();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = simpleDate.parse(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDay(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        String today = simpleDate.format(mDate);

        return today;
    }

    public static String getYear(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
        String year = simpleDate.format(mDate);

        return year;
    }

    // 날짜가 yyyymmdd 형식으로 입력되었을 경우 yyyy-mm-dd로 변경하는 메서드
    public static String transformDate(String date)
    {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");

        // Date로 변경하기 위해서는 날짜 형식을 yyyy-mm-dd로 변경해야 한다.
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

        java.util.Date tempDate = null;

        try {
            // 현재 yyyymmdd로된 날짜 형식으로 java.util.Date객체를 만든다.
            tempDate = beforeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // java.util.Date를 yyyy-mm-dd 형식으로 변경하여 String로 반환한다.
        String transDate = afterFormat.format(tempDate);

        return transDate;
    }

    // 시간이 HHmmss 형식으로 입력되었을 경우 HH:mm:ss로 변경하는 메서드
    public static String transformTime(String time)
    {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("HHmmss");

        SimpleDateFormat afterFormat = new SimpleDateFormat("HH:mm:ss");

        java.util.Date tempTime = null;

        try {
            tempTime = beforeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String transTime = afterFormat.format(tempTime);

        return transTime;
    }

    //은행 코드를 은행명으로 매핑
    public static String bankCodeMapping(String bank_code){
        Log.d("qwe",bank_code);
        switch (bank_code){
            case "002":
                return "KDB산업은행";
            case "003":
                return "IBK기업은행";
            case "004":
                return "KB국민은행";
            case "007":
                return "수협은행";
            case "011":
                return "NH농협은행";
            case "020":
                return "우리은행";
            case "023":
                return "SC제일은행";
            case "027":
                return "한국씨티은행";
            case "031":
                return "대구은행";
            case "032":
                return "부산은행";
            case "034":
                return "광주은행";
            case "035":
                return "제주은행";
            case "037":
                return "전북은행";
            case "039":
                return "경남은행";
            case "045":
                return "새마을금고";
            case "050":
                return "상호저축은행";
            case "081":
                return "KEB하나은행";
            case "088":
                return "신한은행";
            case "089":
                return "케이뱅크";
            case "090":
                return "카카오은행";
            case "097":
                return "오픈은행";
        }
        return "은행";
    }

}
