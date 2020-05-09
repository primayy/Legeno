package com.example.billage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.billage.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(400);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
