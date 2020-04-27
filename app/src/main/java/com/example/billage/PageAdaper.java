package com.example.billage;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.billage.ui.home.subView.UsageFragment;
import com.example.billage.ui.home.subView.CalenderFragment;
import com.example.billage.ui.home.subView.StatisticFragment;

public class PageAdaper extends FragmentPagerAdapter {
    int mNumOfTabs; //tab의 갯수

    public PageAdaper(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UsageFragment tab1 = new UsageFragment();
                return tab1;
            case 1:
                CalenderFragment tab2 = new CalenderFragment();
                return tab2;
            case 2:
                StatisticFragment tab3 = new StatisticFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}


