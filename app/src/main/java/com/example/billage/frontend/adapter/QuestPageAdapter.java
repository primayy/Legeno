package com.example.billage.frontend.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.billage.frontend.ui.quest.subView.daily.Daily;
import com.example.billage.frontend.ui.quest.subView.month.Month;
import com.example.billage.frontend.ui.quest.subView.weekend.Weekend;

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
                Daily tab1 = new Daily();
                return tab1;
            case 1:
                Month tab2 = new Month();
                return tab2;
            case 2:
                Weekend tab3 = new Weekend();
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