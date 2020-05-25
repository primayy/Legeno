package com.example.billage.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.billage.R;

import com.example.billage.backend.GetADUserInfo;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.api.UserInfo;
import com.example.billage.backend.api.AccountBalance;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.example.billage.frontend.data.QuestList;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.signup.SignupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unity3d.player.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.internal.Util;


public class MainActivity extends AppCompatActivity {

    private UnityPlayer mUnityPlayer; //이름 바꾸지말 것. Unityplayer
    public static  QuestChecker questChecker;

    public static  ArrayList<QuestList> dailyQuestList;
    public static  ArrayList<QuestList> weekendQuestList;

    public static  ArrayList<QuestList> monthQuestList;
    public static  ArrayList<QuestList> ingameQuestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUnityPlayer = new UnityPlayer(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_billage, R.id.navigation_quest,R.id.navigation_mypage)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        questPreprocessing();

        //유저정보 조회
        UserInfo.request_userInfo();
        getQuestList(); // 퀘스트 리스트 set

        // 거래내역조회 앱 디비에 데이터 넣을거면 이거 한번 실행하고 다시 주석처리
        // 주석 처리 안하면 같은 데이터 계속 추가됨 -> 수정할 예정
        //AccountTransaction.request_transaction("20200429","20200501");
        ArrayList<UsageList> tmp=AppData.mdb.getTransDaysColumns();

        //잔액 및 거래내역 조회
        Utils.getUserBalance();
        Utils.getUserTrans();
    }

    private void getQuestList() {

        dailyQuestList = getQuest(dailyQuestList,"daily");
        weekendQuestList = getQuest(weekendQuestList,"weekly");
        monthQuestList = getQuest(monthQuestList,"monthly");
        ingameQuestList = getQuest(ingameQuestList,"ingame");
    }

    private ArrayList<QuestList> getQuest( ArrayList<QuestList> questList, String key) {
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
        GetADUserInfo getADUserInfo = new GetADUserInfo();
        if(getADUserInfo.IsThereUserInfo()){
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
    }

    @Override public void onDestroy ()
{

    Log.d("Main1","Destroy");
    super.onDestroy();
    mUnityPlayer.quit();
}

    // Pause Unity
    @Override public void onPause()
    {
        Log.d("Main1","Pause");
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override public void onResume()
    {
        Log.d("Main1","Resume");
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override public void onStart()
    {
        Log.d("Main1","start");
        super.onStart();
        mUnityPlayer.start();
    }

    @Override public void onStop()
    {
        Log.d("Main1","stop");
        super.onStop();
        mUnityPlayer.stop();
    }


    public UnityPlayer GetUnityPlayer() {
        return this.mUnityPlayer;
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
