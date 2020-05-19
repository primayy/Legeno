package com.example.billage.frontend.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.billage.R;
import com.example.billage.frontend.adapter.SignupPageAdapter;
import com.google.android.material.tabs.TabLayout;


public class SignupActivity extends AppCompatActivity {

    TextView tvData;

    private String user_name;

    public void settvData(String data){
        this.tvData.setText(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        setViewPage();
    }

    private void setViewPage() {
        TabLayout tabLayout = findViewById(R.id.circle_tab) ;
        Button next_btn = findViewById(R.id.next_btn);

        final ViewPager viewPager = findViewById(R.id.signup_viewpager);
        final PagerAdapter myPagerAdapter = new SignupPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),next_btn,viewPager);
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(0);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentPage+1);
                if(currentPage==2){
                    next_btn.setText("시작하기");
                    Log.d("asdfasdf",user_name);
                }
                if(currentPage==3){
                    Log.d("test", viewPager.getCurrentItem()+"");
                    finish();
                }
            }
        });
    }



}


