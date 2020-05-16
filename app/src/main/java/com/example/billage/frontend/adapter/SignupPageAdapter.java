package com.example.billage.frontend.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.billage.frontend.ui.signup.subView.step1.StepOne;
import com.example.billage.frontend.ui.signup.subView.step2.StepTwo;
import com.example.billage.frontend.ui.signup.subView.step3.StepThree;
import com.example.billage.frontend.ui.signup.subView.step4.StepFour;
import com.example.billage.frontend.ui.signup.subView.step5.StepFive;

public class SignupPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs; //tab의 갯수

    public SignupPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = numOfTabs;
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
                StepThree tab3 = new StepThree();
                return tab3;
            case 3:
                StepFour tab4 = new StepFour();
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

