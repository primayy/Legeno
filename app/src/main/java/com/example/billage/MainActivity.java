package com.example.billage;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.unity3d.player.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


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
