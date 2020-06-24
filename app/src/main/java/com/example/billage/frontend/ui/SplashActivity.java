package com.example.billage.frontend.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.billage.R;
import com.example.billage.backend.GetSetADUserInfo;
import com.example.billage.backend.QuestProcessor;
import com.example.billage.frontend.MainActivity;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.billage.frontend.ui.signup.SignupActivity;
import com.jaredrummler.android.widget.AnimatedSvgView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AnimatedSvgView svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.start();

        TextView app_name = findViewById(R.id.app_name);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_fade);
        animation.setStartOffset(1000);
        app_name.startAnimation(animation);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 3000);

    }

    private class splashhandler implements Runnable {
        public void run() {

            GetSetADUserInfo getSetADUserInfo=new GetSetADUserInfo();
            if(getSetADUserInfo.IsThereUserInfo()){

                startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
            }else{
                Intent intent = new Intent(getApplication(), SignupActivity.class);
                startActivity(intent);
            }


        }
    }
}


