package com.example.billage.frontend.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.billage.frontend.ui.home.subView.calendar.CalenderFragment;
import com.example.billage.frontend.ui.home.subView.statistic.StatisticFragment;
import com.example.billage.frontend.ui.home.subView.usage.UsageFragment;

public class QuestPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs; //tab의 갯수

    public QuestPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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