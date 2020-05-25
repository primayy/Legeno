package com.example.billage.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.billage.R;

import com.example.billage.backend.GetADUserInfo;
import com.example.billage.backend.GetSetDB;
import com.example.billage.backend.JSONTask_Get;
import com.example.billage.backend.JSONTask_Post;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.api.UserInfo;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.internal.Util;


public class MainActivity extends AppCompatActivity {

    private UnityPlayer mUnityPlayer; //이름 바꾸지말 것. Unityplayer
    public static  QuestChecker questChecker;

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

        //잔액 및 거래내역 조회
        Utils.getUserBalance();
        Utils.getUserTrans();
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
}
