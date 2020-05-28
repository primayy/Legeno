package com.example.billage.backend;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.billage.backend.common.AppData;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetSetADUserInfo {

    public String getUserInfo(String key) {
        JSONArray jarray= null;
        try {
            jarray = new JSONArray(AppData.getPref().getString("user_info",null));
            return jarray.getJSONObject(0).getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getRewardInfo(String type,int id){
        JSONArray jarr=null;
        try{
            jarr=new JSONArray(AppData.getPref().getString(type+"Reward",null));
            Log.d("testt",jarr+""+id);

            for(int i=0;i<jarr.length();i++){
                if(jarr.getJSONObject(i).getInt("quest_id")==id)return jarr.getJSONObject(i).getInt("reward");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int setRewardInfo(String type,int id){
        JSONArray jarr=null;
        try{
            jarr=new JSONArray(AppData.getPref().getString(type+"Reward",null));

            for(int i=0;i<jarr.length();i++){
                if(jarr.getJSONObject(i).getInt("quest_id")==id){
                    jarr.getJSONObject(i).put("reward",1);
                }else ;
            }
            SharedPreferences.Editor editor=AppData.getPref().edit();
            editor.putString(type+"Reward",jarr.toString());
            editor.apply();
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void initializeRewardInfo(){
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        SharedPreferences.Editor editor=AppData.getPref().edit();
        editor.putString("dailyReward","[{\"quest_id\":1,\"reward\":0},{\"quest_id\":2,\"reward\":0},{\"quest_id\":3,\"reward\":0},{\"quest_id\":4,\"reward\":0},{\"quest_id\":17,\"reward\":0},{\"quest_id\":18,\"reward\":0},{\"quest_id\":19,\"reward\":0}]");
        editor.putString("weeklyReward", "[{\"quest_id\":5,\"reward\":0},{\"quest_id\":6,\"reward\":0},{\"quest_id\":7,\"reward\":0},{\"quest_id\":8,\"reward\":0},{\"quest_id\":9,\"reward\":0},{\"quest_id\":10,\"reward\":0}]");
        editor.putString("monthlyReward", "[{\"quest_id\":11,\"reward\":0},{\"quest_id\":12,\"reward\":0},{\"quest_id\":13,\"reward\":0},{\"quest_id\":14,\"reward\":0},{\"quest_id\":15,\"reward\":0},{\"quest_id\":16,\"reward\":0}]");
        editor.putString("ingameReward","[{\"quest_id\":20,\"reward\":0},{\"quest_id\":21,\"reward\":0},{\"quest_id\":25,\"reward\":0}]");
        editor.putInt("date",cal.get(Calendar.DAY_OF_MONTH));
        editor.apply();
    }

    public void reset_dailyRewardInfo(){
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        SharedPreferences.Editor editor=AppData.getPref().edit();

        if(AppData.getPref().getInt("date",0)!=cal.get(Calendar.DAY_OF_MONTH)){//날짜 바꼈을때 보상정보 초기화
            editor.putInt("date",cal.get(Calendar.DAY_OF_MONTH));
            editor.putString("dailyReward","[{\"quest_id\":1,\"reward\":0},{\"quest_id\":2,\"reward\":0},{\"quest_id\":3,\"reward\":0},{\"quest_id\":4,\"reward\":0},{\"quest_id\":17,\"reward\":0},{\"quest_id\":18,\"reward\":0},{\"quest_id\":19,\"reward\":0}]");
            editor.apply();
        }
    }

    public void reset_weeklyRewardInfo(){
        Date today=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        SharedPreferences.Editor editor = AppData.getPref().edit();
        if(cal.get(Calendar.DAY_OF_WEEK)==1) {
            editor.putString("weeklyReward", "[{\"quest_id\":5,\"reward\":0},{\"quest_id\":6,\"reward\":0},{\"quest_id\":7,\"reward\":0},{\"quest_id\":8,\"reward\":0},{\"quest_id\":9,\"reward\":0},{\"quest_id\":10,\"reward\":0}]");
            editor.apply();
        }
    }

    public void reset_monthlyRewardInfo(){
        Date today=new Date();
        SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
        if(date2str.format(today).substring(8).equals("01")) {
            SharedPreferences.Editor editor = AppData.getPref().edit();
            editor.putString("monthlyReward", "[{\"quest_id\":11,\"reward\":0},{\"quest_id\":12,\"reward\":0},{\"quest_id\":13,\"reward\":0},{\"quest_id\":14,\"reward\":0},{\"quest_id\":15,\"reward\":0},{\"quest_id\":16,\"reward\":0}]");
            editor.apply();
        }
    }

    public void reset_ingameRewardInfo(){
        SharedPreferences.Editor editor=AppData.getPref().edit();
        editor.putString("ingameReward","[{\"quest_id\":20,\"reward\":0},{\"quest_id\":21,\"reward\":0},{\"quest_id\":25,\"reward\":0}]");
        editor.apply();
    }

    public boolean IsThereUserInfo(){
        if(AppData.getPref().getString("user_info",null)==null) return false;
        else return true;
    }
}
