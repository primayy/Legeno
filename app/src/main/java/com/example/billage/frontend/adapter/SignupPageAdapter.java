package com.example.billage.frontend.adapter;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.billage.frontend.ui.signup.subView.step1.StepOne;
import com.example.billage.frontend.ui.signup.subView.step2.StepTwo;
import com.example.billage.frontend.ui.signup.subView.step3.StepThree;
import com.example.billage.frontend.ui.signup.subView.step4.StepFour;
import com.example.billage.frontend.ui.signup.subView.step5.StepFive;

public class SignupPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs; //tab의 갯수
    private Button parent;
    private ViewPager view_pager;

    public SignupPageAdapter(FragmentManager fm, int numOfTabs, Button next_btn, ViewPager viewPager) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = numOfTabs;
        parent = next_btn;
        view_pager=viewPager;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StepOne tab1 = new StepOne();
                return tab1;
            case 1:
                StepTwo tab2 = new StepTwo();
                return tab2;
            case 2:
                StepThree tab3 = new StepThree(parent,view_pager);
                return tab3;
            case 3:
                StepFour tab4 = new StepFour(parent,view_pager);
                return tab4;
            case 4:
                StepFive tab5 = new StepFive();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

