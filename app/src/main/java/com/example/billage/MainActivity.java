package com.example.billage;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.billage.api.Statistic_transaction;
import com.example.billage.api.data.token.TokenRequest;
import com.example.billage.common.AppData;
import com.example.billage.common.DbOpenHelper;
import com.example.billage.common.Utils;
import com.example.billage.api.Account_transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.unity3d.player.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.ParseException;


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

        //유저 코드 저장 api 쓸거면 이거 주석 풀고 한번 실행해야댐

        SharedPreferences.Editor editor = AppData.getPref().edit();
        editor.putString("auth_code","m0WMfi2oSHfBk0SyDrfMAupzsoGiCD");
        editor.putString("access_token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJUOTkxNjIwODEwIiwic2NvcGUiOlsib29iIl0sImlzcyI6Imh0dHBzOi8vd3d3Lm9wZW5iYW5raW5nLm9yLmtyIiwiZXhwIjoxNTk2MDIwODIxLCJqdGkiOiI1MmI5OGIwMy1iYTU1LTRiOTEtYjRhNS00ZGFlNDIwNjE4ZGYifQ.esJCewec6IN-D3QH34DLDpYfenr5oKdRFt2f-25Nfhg");
        editor.putString("client_use_code","T991620810");
        editor.apply();

        // 거래내역조회 앱 디비에 데이터 넣을거면 이거 한번 실행하고 다시 주석처리
        // 주석 처리 안하면 같은 데이터 계속 추가됨 -> 수정할 예정
//        Account_transaction .request_transaction("20200429","20200501");

        //통계데이터 호출 코드
//        try {
//            Statistic_transaction.daily_statistic("입금");
//            Statistic_transaction.monthly_statistic("입금");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        //일별 수입,지출 합 리스트 리턴 log :days_data
//        AppData.mdb.getTransDaysColumns();

        //달별 수입,지출 합, 카운트 리턴
//        AppData.mdb.getTransMonthsColumns("입금");
//        AppData.mdb.getTransMonthsColumns("출금");
        //이전 3개월의 수입 or 지출 평균 리턴
//        AppData.mdb.getTransThreeMonthsAvg("입금");
//        AppData.mdb.getTransThreeMonthsAvg("출금");
        /**/

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
