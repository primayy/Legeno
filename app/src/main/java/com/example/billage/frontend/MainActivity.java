package com.example.billage.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.billage.R;

import com.example.billage.backend.GetSetADUserInfo;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.QuestProcessor;
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

import okhttp3.internal.Util;


public class MainActivity extends AppCompatActivity {

    private UnityPlayer mUnityPlayer; //이름 바꾸지말 것. Unityplayer

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

        //유저정보 조회
        UserInfo.request_userInfo();

        //잔액 조회
       // AccountBalance.request_balance();

        //잔액 및 거래내역 조회
        Utils.getUserTrans();

        //퀘스트 보상획득 정보 리셋
        GetSetADUserInfo resetReward=new GetSetADUserInfo();
       // resetReward.initializeRewardInfo();
        resetReward.reset_dailyRewardInfo();
        resetReward.reset_weeklyRewardInfo();
        resetReward.reset_monthlyRewardInfo();

        GetSetADUserInfo getSetADUserInfo=new GetSetADUserInfo();
        if(getSetADUserInfo.IsThereUserInfo()){

            QuestProcessor questProcessor = new QuestProcessor();
            questProcessor.questPreprocessing();

        }else{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
            //서버 꺼져서 유저 정보 못불러올 때 이거 쓰셈
//        Utils.getTestUserInfo();

        //        //AVD에서 인증 불가할 때 걍 이거 쓰셈
        //Utils.getTestUserToken();
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
}
