package com.example.billage.frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.billage.R;

import com.example.billage.backend.GetADUserInfo;
import com.example.billage.backend.GetSetDB;
import com.example.billage.backend.JSONTask_Get;
import com.example.billage.backend.JSONTask_Post;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.api.AccountBalance;
import com.example.billage.backend.api.AccountTransaction;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.signup.SignupActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unity3d.player.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


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
        //AccountTransaction.request_transaction("20200429","20200501");
        ArrayList<UsageList> tmp=AppData.mdb.getTransDaysColumns();

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
                QuestChecker questChecker=new QuestChecker(data2Quest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
        //잔액 조회
        AccountBalance.request_balance();

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
