package com.example.billage.backend;

import android.content.Intent;

import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.data.QuestList;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.signup.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuestProcessor {

    public static  QuestChecker questChecker;

    public static  ArrayList<QuestList> dailyQuestList;
    public static  ArrayList<QuestList> weekendQuestList;

    public static  ArrayList<QuestList> monthQuestList;
    public static  ArrayList<QuestList> ingameQuestList;

    private void getQuestList() {

        dailyQuestList = getQuest(dailyQuestList,"daily");
        weekendQuestList = getQuest(weekendQuestList,"weekly");
        monthQuestList = getQuest(monthQuestList,"monthly");
        ingameQuestList = getQuest(ingameQuestList,"ingame");
    }

    private ArrayList<QuestList> getQuest(ArrayList<QuestList> questList, String key) {
        try {
            questList = questChecker.parseQuestList();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        int size = questList.size();
        for(int i=0;i<size;i++){

            if(!(questList.get(i).getType().equals(key))){
                questList.remove(i);
                size--;
                i--;
            }
        }

        return questList;
    }

    private void questPreprocessing() {
        ArrayList<UsageList> tmp= AppData.mdb.getTransDaysColumns();
        GetSetADUserInfo getSetADUserInfo = new GetSetADUserInfo();
        JSONObject data2Quest=new JSONObject();
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        //예상 소비량
        int expectedOutcome=AppData.mdb.getTransThreeMonthsAvg("출금");
        try {
            data2Quest.accumulate("dateInfo",cal);
            data2Quest.accumulate("expectation",Integer.toString(expectedOutcome));
            JSONArray jarray=new JSONArray();
            for(int i=0;i<tmp.size();i++){
                JSONObject dailyData=new JSONObject();
                dailyData.accumulate("date",tmp.get(i).getDate());
                dailyData.accumulate("cost",tmp.get(i).getCost());
                jarray.put(dailyData);
            }
            data2Quest.accumulate("daily",jarray);
            for(int i=1;i<cal.get(Calendar.DATE);i++){
                int itIs=0;
                cal.add(Calendar.DATE,-i);
                Date cmpDate=cal.getTime();
                SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
                for(int j=0;j<data2Quest.getJSONArray("daily").length();j++){
                    if(data2Quest.getJSONArray("daily").getJSONObject(i).getString("date").equals(date2str.format(cmpDate))){
                        itIs=1;
                        break;
                    } else ;
                }
                if(itIs==0){
                    JSONObject zeroData=new JSONObject();
                    zeroData.accumulate("date",date2str.format(cmpDate));
                    zeroData.accumulate("cost",0);
                    data2Quest.getJSONArray("daily").put(zeroData);
                }
                cal.add(Calendar.DATE,i);
            }

            questChecker=new QuestChecker(data2Quest);
            getQuestList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public QuestChecker getQuestChecker(){
        return questChecker;
    }

    public ArrayList<QuestList> getDailyQuestList() {
        return dailyQuestList;
    }
    public ArrayList<QuestList> getWeekendQuestList() {
        return weekendQuestList;
    }
    public ArrayList<QuestList> getMonthQuestList() {
        return monthQuestList;
    }

    public ArrayList<QuestList> getIngameQuestList() {
        return ingameQuestList;
    }
}
