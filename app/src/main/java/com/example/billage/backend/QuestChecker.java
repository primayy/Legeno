package com.example.billage.backend;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class QuestChecker {
    //퀘스트 완료여부 확인에 필요한 데이터 생성
    JSONObject data2Quest=new JSONObject();

    public QuestChecker(JSONObject data){
        this.data2Quest=data;
    }

//    public JSONObject checkQuest(){
//        try {
//            JSONTask_Get jsonTask = new JSONTask_Get();
//            JSONArray questList = new JSONArray(jsonTask.execute("http://192.168.0.9:3000/Quest/getQuest").get());
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public Date subDate(int i){
        Calendar tmp=Calendar.getInstance();
        try {
            tmp=(Calendar) this.data2Quest.get("dateInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tmp.add(Calendar.DATE,i);
        return tmp.getTime();
    }

    public boolean test() throws JSONException {
        return checkDailyPlan(subDate(-6),this.data2Quest.getJSONArray("daily"),this.data2Quest.getDouble("expectation"));
    }

    public int checkDailySave(Date targetDay,JSONArray Data,double expectation) throws JSONException {
        SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<Data.length();i++){
            if(Data.getJSONObject(i).getString("date").equals(date2str.format(targetDay))){
                if(IsDailySaveLv1(Data.getJSONObject(i).getDouble("cost"),expectation)){
                    return 1;
                }else if(IsDailySaveLv2(Data.getJSONObject(i).getDouble("cost"),expectation)){
                    return 2;
                }else if(IsDailySaveLv3(Data.getJSONObject(i).getDouble("cost"),expectation)){
                    return 3;
                }else return 0;
            }
            else ;
        }
        return 0;
    }

    public boolean IsDailySaveLv1(double cost,double expectation){
        if(cost>expectation*0.9&&cost<=expectation*0.95) return true;
        else return false;
    }

    public boolean IsDailySaveLv2(double cost,double expectation){
        if(cost>expectation*0.8&&cost<=expectation*0.9) return true;
        else return false;
    }

    public boolean IsDailySaveLv3(double cost,double expectation){
        if(cost<=expectation*0.8) return true;
        else return false;
    }

    public boolean checkDailyPlan(Date targetDay,JSONArray Data,double expectation) throws JSONException {
        SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<Data.length();i++){
            if(Data.getJSONObject(i).getString("date").equals(date2str.format(targetDay))) {
                if(Data.getJSONObject(i).getDouble("cost")<=expectation*1.05&&Data.getJSONObject(i).getDouble("cost")>=expectation*0.95) return true;
                else ;
            }
        }
        return false;
    }

    public int checkWeeklyPlan(Date today,JSONArray Data,double expectation){
        int count=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        int day=cal.get(Calendar.DAY_OF_WEEK);
        for(int i=1;i<day;i++){
            if(che)
        }
    }

}
