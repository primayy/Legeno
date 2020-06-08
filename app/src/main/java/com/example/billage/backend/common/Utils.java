package com.example.billage.backend.common;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.billage.R;
import com.example.billage.backend.HowMuchPay;
import com.example.billage.backend.api.AccountBalance;
import com.example.billage.backend.api.AccountTransaction;
import com.example.billage.backend.api.data.UserAccount;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

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
        String clientUseCode = ApiConst.BILLAGE_CODE;
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
        return "현금";
    }

    public static String convertMapToString(Map<?, ?> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> e : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", defaultString(e.getKey()), defaultString(e.getValue())));
        }
        return sb.toString();
    }

    // String null 체크
    public static String defaultString(final String str) {
        return str == null ? "" : str;
    }

    // Object 를 string 으로 변환
    public static String defaultString(Object src){
        return defaultString(src, null);
    }

    // Object 를 string 으로 변환
    public static String defaultString(Object src, final String defaultStr){
        if(src != null){
            if(src instanceof String){
                return (String)src;
            }else{
                return String.valueOf(src);
            }
        }else{
            return (defaultStr == null) ? "" : defaultStr;
        }
    }

    // url 문자열에 포함된 특정 파라미터의 값을 리턴한다.
    public static String getParamValFromUrlString(String url, String paramKey){

        Log.d("## url", url);
        String[] urlParamPair = url.split("\\?");
        if(urlParamPair.length < 2){
            Log.d("##", "파라미터가 존재하지 않는 URL 입니다.");
            return "";
        }
        String queryString = urlParamPair[1];
        Log.d("## queryString", queryString);
        StringTokenizer st = new StringTokenizer(queryString, "&");
        while(st.hasMoreTokens()){
            String pair = st.nextToken();
            Log.d("## pair", pair);
            String[] pairArr = pair.split("=");
            if(paramKey.equals(pairArr[0])){
                return pairArr[1]; // 찾았을 경우
            }
        }
        return "";
    }

    //난수 설정
    public static String numberGen(int len) {

        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {

            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;

        }
        return numStr;
    }

    public static ArrayList<UserAccount> getUserAccount(){
        String user_accounts_str = AppData.getPref().getString("user_accounts","");
        ArrayList<UserAccount> user_accounts = new ArrayList<UserAccount>();
        try {
            JSONArray tmp = new JSONArray(user_accounts_str);
            for (int i = 0; i < tmp.length(); i++) {
                String account_str = tmp.optString(i);
                String[] account = account_str.split(",");
                user_accounts.add(new UserAccount(account[0],account[1],account[2],account[3]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_accounts;
    }

    public static void getUserBalance(){
        ArrayList<UserAccount> user_accounts = Utils.getUserAccount();
        //저장된 잔액 초기화
        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString("balance","0");
        editor.apply();

        for(int i=0; i<user_accounts.size();i++)
        {
            AccountBalance.request_balance(user_accounts.get(i).getFintech_num());
        }
    }
    public static void getUserTrans(){
        ArrayList<UserAccount> user_accounts = Utils.getUserAccount();

        for(int i=0; i<user_accounts.size();i++)
        {
            try{
                AccountTransaction.request_transaction(user_accounts.get(i).getFintech_num());
            } catch (Exception e){

            }
        }
    }

    public static void getTestUserToken(){
        SharedPreferences.Editor editor = AppData.getPref().edit();

        editor.putString("access_token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzU4NDkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1OTg4NzEzMDgsImp0aSI6IjJkMTIzMTI0LTFiMzUtNDBhZC1hZTI0LWZlMzFkYzNhNDBiMyJ9.t7lTUZC6VeAh-prhgZtCAo5OW62n2DET2gVJS7v-8Dw");
        editor.putString("user_seq_no","1100758490");
        editor.apply();
    }

    public static void getTestUserInfo() {
        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString("user_info", "[{\"user_id\":90,\"user_name\":\"김용표\",\"nickname\":\"캡디중간발표\",\"bank_list\":null,\"auth_check\":0}]");
        editor.putInt("attendanceCount",0);
        editor.apply();
    }

    public static int getCurMonth(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        System.out.println(month);
        return month;
    }

    public static ArrayList<HowMuchPay> getHowMuchPays(double total_money,int get_size){
        ArrayList<HowMuchPay> howMuchPays = new ArrayList<HowMuchPay>();
        int randNum[] = getRandNum(get_size);
        for(int i=0; i<randNum.length;i++){
            howMuchPays.add(getHowMuchItem(randNum[i],total_money));
        }

        return howMuchPays;
    }

    public static int[] getRandNum(int num_size){
        int num[] = new int[num_size];
        boolean chk = false;
        for(int i=0;i<num_size;i++){
            while(true){
                num[i] = (int)(Math.random()*15);
                for(int j=0; j<i; j++){
                    if(num[i]==num[j]){
                        chk = true;
                        break;
                    } else{
                        chk = false;
                    }
                }
                if(!chk){
                    break;
                }
            }
        }
        return num;
    }
    public static HowMuchPay getHowMuchItem(int num, double total_money){
        switch (num){
            case 0:
                return new HowMuchPay("압구정 현대아파트",Math.round((total_money/100000000) * 100) / 100.0,"평");
            case 1:
                return new HowMuchPay("최저임금 아르바이트",Math.round((total_money/8590) * 100) / 100.0,"시간");
            case 2:
                return new HowMuchPay("연애횟수",0,"번");
            case 3:
                return new HowMuchPay("국밥",Math.round((total_money/7000) * 100) / 100.0,"그릇");
            case 4:
                return new HowMuchPay("람보르기니 우루스",Math.round((total_money/256000000) * 100) / 100.0,"개");
            case 5:
                return new HowMuchPay("대학교",Math.round((total_money/4500000) * 100) / 100.0,"학기");
            case 6:
                return new HowMuchPay("이성이랑 영화볼 기회",0,"번");
            case 7:
                return new HowMuchPay("부모님과 전화",Math.round((total_money/1.98)),"초");
            case 8:
                return new HowMuchPay("에버랜드 주간 이용권",Math.round((total_money/56000)*100) / 100.0,"장");
            case 9:
                return new HowMuchPay("에어팟 프로",Math.round((total_money/329000)*100) / 100.0,"개");
            case 10:
                return new HowMuchPay("영화 예매권",Math.round((total_money/10000)*100) / 100.0,"장");
            case 11:
                return new HowMuchPay("피씨방",Math.round((total_money/1000)*100) / 100.0,"시간");
            case 12:
                return new HowMuchPay("코인 노래방",Math.round((total_money/333)*100) / 100.0,"곡");
            case 13:
                return new HowMuchPay("스타벅스 아메리카노",Math.round((total_money/4100)*100) / 100.0,"잔");
            case 14:
                return new HowMuchPay("BBQ 황금올리브 치킨",Math.round((total_money/20000)*100) / 100.0,"마리");
            default:
                return null;
        }
    }
}
