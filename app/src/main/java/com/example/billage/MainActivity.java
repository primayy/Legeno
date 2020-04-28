package com.example.billage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.billage.ui.billage.BillageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.unity3d.player.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.billage.ui.billage.BillageFragment;

public class MainActivity extends AppCompatActivity {

    BillageService bs; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용
    private UnityPlayer mUnityPlayer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        /**/

    }
//    ServiceConnection conn = new ServiceConnection() {
//        public void onServiceConnected(ComponentName name,
//                                       IBinder service) {
//            // 서비스와 연결되었을 때 호출되는 메서드
//            // 서비스 객체를 전역변수로 저장
//            BillageService.BillageBinder billageBinder = (BillageService.BillageBinder) service;
//            bs = billageBinder.getService(); // 서비스가 제공하는 메소드 호출하여
//            // 서비스쪽 객체를 전달받을수 있슴
//            isService = true;
//        }
//
//        public void onServiceDisconnected(ComponentName name) {
//            // 서비스와 연결이 끊겼을 때 호출되는 메서드
//            isService = false;
//        }
//    };
//
//
//
//    // 백그라운드 Service 종료
//    @Override protected void onDestroy ()
//    {
////        // 서비스 종료하기
////        Log.d("test", "액티비티-서비스 종료버튼클릭");
////        Intent intent = new Intent(
////                getApplicationContext(),//현재제어권자
////                BillageService.class); // 이동할 컴포넌트
////        stopService(intent); // 서비스 종료
//        if(isService){
//            unbindService(conn); // 서비스 종료
//            isService = false;
//        }
//        else {
//            Log.d("test","연결데이터없음");
//        }
//
//        super.onDestroy();
//    }
    // 백그라운드 Service 시작
//    @Override protected void onStart()
//    {
////        Log.d("test", "액티비티-서비스 시작버튼클릭");
//////        Intent intent = new Intent(
//////                getApplicationContext(),//현재제어권자
//////                BillageService.class); // 이동할 컴포넌트
//////        startService(intent); // 서비스 시작
//        Intent intent = new Intent(
//                MainActivity.this, // 현재 화면
//                BillageService.class); // 다음넘어갈 컴퍼넌
//
//        bindService(intent, // intent 객체
//                conn, // 서비스와 연결에 대한 정의
//                Context.BIND_AUTO_CREATE);
//
//
//        super.onStart();
//    }
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
