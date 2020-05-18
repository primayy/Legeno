package com.example.billage.frontend.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.billage.R;
import com.example.billage.backend.JSONTask_Post;
import com.example.billage.frontend.adapter.PageAdaper;
import com.example.billage.frontend.adapter.SignupPageAdapter;
import com.example.billage.frontend.ui.signup.subView.step2.StepTwo;
import com.google.android.material.tabs.TabLayout;


public class SignupActivity extends AppCompatActivity {

    TextView tvData;

    public void settvData(String data){
        this.tvData.setText(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setViewPage();
    }

    private void setViewPage() {
        TabLayout tabLayout = findViewById(R.id.circle_tab) ;

        final ViewPager viewPager = findViewById(R.id.signup_viewpager);
        final PagerAdapter myPagerAdapter = new SignupPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(0);

        Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage+1);


                if(currentPage==3){
                    next_btn.setText("시작하기");
                }
                if(currentPage==4){
                    finish();
                }
            }
        });
    }
}


