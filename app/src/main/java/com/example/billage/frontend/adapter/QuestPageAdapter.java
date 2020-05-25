package com.example.billage.frontend.adapter;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.billage.frontend.ui.quest.subView.daily.Daily;
import com.example.billage.frontend.ui.quest.subView.ingame.InGame;
import com.example.billage.frontend.ui.quest.subView.month.Month;
import com.example.billage.frontend.ui.quest.subView.weekend.Weekend;

public class QuestPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs; //tab의 갯수
    TextView coin;

    public QuestPageAdapter(FragmentManager fm, int numOfTabs, TextView coin) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = numOfTabs;
        this.coin = coin;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Daily tab1 = new Daily(coin);
                return tab1;
            case 1:
                Weekend tab2 = new Weekend(coin);
                return tab2;
            case 2:
                Month tab3 = new Month(coin);
                return tab3;
            case 3:
                InGame tab4 = new InGame(coin);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}