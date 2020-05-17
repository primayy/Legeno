package com.example.billage.frontend.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.billage.R;
import com.example.billage.frontend.MainActivity;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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
            startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
            SplashActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }
}


