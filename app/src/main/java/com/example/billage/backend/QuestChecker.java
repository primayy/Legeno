package com.example.billage.backend;

import android.content.SharedPreferences;

import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.data.QuestList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class QuestChecker {
    //퀘스트 완료여부 확인에 필요한 데이터 생성
    JSONObject data2Quest=new JSONObject();
    int[] currentWeeklyAchieve =new int[6];
    int[] finallyWeeklyAchieve ={3,5,7,3,5,7};

    int[] currentMonthlyAchieve=new int[6];
    int[] finallyMonthlyAchieve={10,15,25,10,20,30};

    public QuestChecker(JSONObject data){
        this.data2Quest=data;
    }

    public int[] getCurrentWeeklyAchieve(){
        return this.currentWeeklyAchieve;
    }

    public int[] getFinallyWeeklyAchieve(){
        return this.finallyWeeklyAchieve;
    }

    public int[] getCurrentMonthlyAchieve() {
        return currentMonthlyAchieve;
    }

    public int[] getFinallyMonthlyAchieve() {
        return finallyMonthlyAchieve;
    }

    public ArrayList<QuestList> parseQuestList() throws JSONException {
        ArrayList<QuestList> questListAL=new ArrayList<>();

        JSONArray questlist=checkQuest();

        for(int i=0;i<questlist.length();i++){
            int id=questlist.getJSONObject(i).getInt("quest_id");
            String qname=questlist.getJSONObject(i).getString("quest_name");
            String description=questlist.getJSONObject(i).getString("quest_description");
            String complete=questlist.getJSONObject(i).getString("complete");
            String reward=questlist.getJSONObject(i).getString("quest_reward");
            String type=questlist.getJSONObject(i).getString("quest_type"   );

            QuestList ql=new QuestList(id,qname,description,complete,reward,type);
            questListAL.add(ql);
        }

        return questListAL;
    }

    public JSONArray checkQuest() throws JSONException {

        Calendar cal=Calendar.getInstance();
        cal.setTime(subDate(0));
        if(cal.get(Calendar.DATE)==1){//1일 되면 출석보상 횟수 초기화
            SharedPreferences.Editor editor=AppData.getPref().edit();
            editor.putInt("attendanceCount",0);
        }
        try {
            JSONTask_Get jsonTask = new JSONTask_Get();
            JSONArray questList = new JSONArray(jsonTask.execute("http://18.219.106.101/Quest/getQuest").get());

            //퀘스트 description 수정
            questList.getJSONObject(0).put("quest_description",questList.getJSONObject(0).getString("quest_description").replace("%","%(~"+Integer.toString((int)Math.round(getExpectation() *0.95*0.1)*10)+")"));
            questList.getJSONObject(1).put("quest_description",questList.getJSONObject(1).getString("quest_description").replace("%","%(~"+Integer.toString((int)Math.round(getExpectation() *0.90*0.1)*10)+")"));
            questList.getJSONObject(2).put("quest_description",questList.getJSONObject(2).getString("quest_description").replace("%","%(~"+Integer.toString((int)Math.round(getExpectation() *0.80*0.1)*10)+")"));
            questList.getJSONObject(3).put("quest_description",questList.getJSONObject(3).getString("quest_description").replace("%","%("+Integer.toString((int)Math.round(getExpectation() *0.95*0.1)*10)+"~"+Integer.toString((int)Math.round(getExpectation() *1.05*0.1)*10)+")"));

            //일간 소비절약확인
            int checkDailySaveRes=checkDailySave(subDate(1), getDaily(), getExpectation());
            if(checkDailySaveRes==1) {
                questList.getJSONObject(0).put("complete",true);
            }else if(checkDailySaveRes==2) {
                questList.getJSONObject(0).put("complete",true);
                questList.getJSONObject(1).put("complete",true);
            }else if(checkDailySaveRes==3) {
                questList.getJSONObject(0).put("complete",true);
                questList.getJSONObject(1).put("complete",true);
                questList.getJSONObject(2).put("complete",true);
            }
            //일간 계획소비 확인
            if(checkDailyPlan(subDate(1), getDaily(), getExpectation())==true) questList.getJSONObject(3).put("complete",true);

            //주간소비절약 확인
            int checkWeeklySaveRes=checkWeeklySave(subDate(0), getDaily(),getExpectation());
            if(checkWeeklySaveRes==1){
                questList.getJSONObject(4).put("complete",true);
            }else if(checkWeeklySaveRes==2){
                questList.getJSONObject(4).put("complete",true);
                questList.getJSONObject(5).put("complete",true);
            }else if(checkWeeklySaveRes==3){
                questList.getJSONObject(4).put("complete",true);
                questList.getJSONObject(5).put("complete",true);
                questList.getJSONObject(6).put("complete",true);
            }

            //주간 계획소비 확인
            int checkWeeklyPlanRes=checkWeeklyPlan(subDate(0),getDaily(),getExpectation());
            if(checkWeeklyPlanRes==1){
                questList.getJSONObject(7).put("complete",true);
            }else if(checkWeeklyPlanRes==2){
                questList.getJSONObject(7).put("complete",true);
                questList.getJSONObject(8).put("complete",true);
            }else if(checkWeeklyPlanRes==3){
                questList.getJSONObject(7).put("complete",true);
                questList.getJSONObject(8).put("complete",true);
                questList.getJSONObject(9).put("complete",true);
            }
            //월간소비절약 확인
            int checkMonthlySaveRes=checkMonthlySave(subDate(0), getDaily(),getExpectation());
            if(checkMonthlySaveRes==1){
                questList.getJSONObject(10).put("complete",true);
            }else if(checkMonthlySaveRes==2){
                questList.getJSONObject(10).put("complete",true);
                questList.getJSONObject(11).put("complete",true);
            }else if(checkMonthlySaveRes==3){
                questList.getJSONObject(10).put("complete",true);
                questList.getJSONObject(11).put("complete",true);
                questList.getJSONObject(12).put("complete",true);
            }
            //월간계획소비 확인
            int checkMonthlyPlanRes=checkMonthlyPlan(subDate(0),getDaily(),getExpectation());
            if(checkMonthlyPlanRes==1){
                questList.getJSONObject(13).put("complete",true);
            }else if(checkMonthlyPlanRes==2){
                questList.getJSONObject(13).put("complete",true);
                questList.getJSONObject(14).put("complete",true);
            }else if(checkMonthlyPlanRes==3){
                questList.getJSONObject(13).put("complete",true);
                questList.getJSONObject(14).put("complete",true);
                questList.getJSONObject(15).put("complete",true);
            }

            //출석보상 확인
            int checkAttendance=successfulYesterday(subDate(1),getDaily(),getExpectation());
            SharedPreferences.Editor editor=AppData.getPref().edit();
            if(checkAttendance==1){
                questList.getJSONObject(16).put("complete",true);
                editor.putInt("attendanceCount",AppData.getPref().getInt("attendanceCount",0)+1);
                if(successfulYesterday(subDate(2),getDaily(),getExpectation())==1)questList.getJSONObject(18).put("complete",true);
                else ;
            }else{
                questList.getJSONObject(17).put("complete",true);
                editor.putInt("attendanceCount",AppData.getPref().getInt("attendanceCount",0)+1);
            }
            //전설이다 퀘스트 확인
            if(AppData.getPref().getInt("attendanceCount",0)==30) questList.getJSONObject(24).put("complete",true);

            GetSetADUserInfo getUserInfo=new GetSetADUserInfo();
            JSONObject postdata=new JSONObject();
            postdata.put("user_id",getUserInfo.getUserInfo("user_id"));
            JSONTask_Post jspost=new JSONTask_Post(postdata);

            //너 부자구나? 퀘스트 확인
            String res=jspost.execute("http://18.219.106.101/Quest/checkCoin").get();

            JSONArray coinCost=new JSONArray(res);
            if(coinCost.getJSONObject(0).getInt("coin")>=coinCost.getJSONObject(0).getInt("billage_cost")*0.1){
                questList.getJSONObject(19).put("complete",true);
            }

            //이런 인기쟁이!퀘스트 확인
            String dbLike=jsonTask.execute("http://18.219.106.101/Read/Like"+getUserInfo.getUserInfo("user_id")).get();
            if(Integer.parseInt(dbLike)==100){
                questList.getJSONObject(20).put("complete",true);
            }

            return questList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double getExpectation() throws JSONException {
        return this.data2Quest.getDouble("expectation");
    }

    private JSONArray getDaily() throws JSONException {
        return this.data2Quest.getJSONArray("daily");
    }

    public int test() throws JSONException, ParseException {
        return successfulYesterday(subDate(6), getDaily(), getExpectation());
    }

    //날짜 계산(현재 날짜로부터 i만큼 뺌)
    public Date subDate(int i){
        Calendar tmp=Calendar.getInstance();
        try {
            tmp=(Calendar) this.data2Quest.get("dateInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tmp.add(Calendar.DATE,-i);
        Date res=tmp.getTime();
        tmp.add(Calendar.DATE,i);

        return res;
    }

    //일간 소비절약 확인
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

    //일간 계획소비 확인
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

    //주간 계획소비 확인
    public int checkWeeklyPlan(Date today,JSONArray Data,double expectation) throws JSONException {
        int count=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        int day=cal.get(Calendar.DAY_OF_WEEK);
        if(day==1) day=8;
        for(int i=1;i<day;i++){
            if(checkDailyPlan(subDate(i),Data,expectation)) count++;
            else ;
        }

        this.currentWeeklyAchieve[3]=count;
        this.currentWeeklyAchieve[4]=count;
        this.currentWeeklyAchieve[5]=count;

        if(count>=7) return 3;
        else if(count>=5&&count<7) return 2;
        else if(count<5&&count>=3) return 1;
        else return 0;
    }

    //주간 소비절약 확인
    public int checkWeeklySave(Date today,JSONArray Data,double expectation) throws JSONException {
        int countLv1=0;
        int countLv2=0;
        SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);

        int day=cal.get(Calendar.DAY_OF_WEEK);
        if(day==1) day=8;

        for(int i=0;i<day;i++){
            if(checkDailySave(subDate(i),Data,expectation)==1) countLv1++;
            else if(checkDailySave(subDate(i),Data,expectation)>=2) countLv2++;
        }

        this.currentWeeklyAchieve[0]=countLv1;
        this.currentWeeklyAchieve[1]=countLv2;
        this.currentWeeklyAchieve[2]=countLv2;

        if(countLv2>=7) return 3;
        else if(countLv2<7&&countLv2>=5) return 2;
        else if(countLv1>=3) return 1;
        else return 0;
    }

    //월간 계획소비 확인
    public int checkMonthlyPlan(Date today,JSONArray Data,double expectation) throws JSONException {
        int count=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        int month=cal.get(Calendar.MONTH)+1;

        for(int i=0;i<Data.length();i++){
            if(Integer.parseInt(Data.getJSONObject(i).getString("date").substring(5,7))==month){
                if(Data.getJSONObject(i).getDouble("cost")<=expectation*1.05&&Data.getJSONObject(i).getDouble("cost")>=expectation*0.95) count++;
                else;
            }
        }

        this.currentMonthlyAchieve[3]=count;
        this.currentMonthlyAchieve[4]=count;
        this.currentMonthlyAchieve[5]=count;

        if(count>=10&&count<20) return 1;
        else if(count>=20&&count<30) return 2;
        else if(count>=30) return 3;
        else return 0;
    }

    //월간 소비절약 확인
    public int checkMonthlySave(Date today,JSONArray Data,double expectation) throws JSONException, ParseException {
        int countLv1=0;
        int countLv2=0;

        SimpleDateFormat str2date=new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal=Calendar.getInstance();
        cal.setTime(today);
        int month=cal.get(Calendar.MONTH)+1;

        for(int i=0;i<Data.length();i++){
            if(Integer.parseInt(Data.getJSONObject(i).getString("date").substring(5,7))==month){
                if(checkDailySave(str2date.parse(Data.getJSONObject(i).getString("date")),Data,expectation)==1) countLv1++;
                else if(checkDailySave(str2date.parse(Data.getJSONObject(i).getString("date")),Data,expectation)>=2) countLv2++;
                else ;
            }
        }

        this.currentMonthlyAchieve[0]=countLv1;
        this.currentMonthlyAchieve[1]=countLv2;
        this.currentMonthlyAchieve[2]=countLv2;

        if(countLv2>=25) return 3;
        else if(countLv2<25&&countLv2>=15) return 2;
        else if(countLv1>=10) return 1;
        else return 0;
    }

    //오늘은 성공했어,내일은 성공하자 완료확인
    public int successfulYesterday(Date yesterday, JSONArray Data,double expectation) throws JSONException {
        SimpleDateFormat date2str=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<Data.length();i++){
            if(Data.getJSONObject(i).getString("date").equals(date2str.format(yesterday))){
                if(Data.getJSONObject(i).getDouble("cost")<expectation) return 1;//어제는 성공했어 성공
                else return 0;//내일은 성공하자
            }
        }
        return -1;
    }

}
