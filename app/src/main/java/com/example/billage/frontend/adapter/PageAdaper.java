package com.example.billage.frontend.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.billage.frontend.ui.home.subView.usage.UsageFragment;
import com.example.billage.frontend.ui.home.subView.calendar.CalenderFragment;
import com.example.billage.frontend.ui.home.subView.statistic.StatisticFragment;

public class PageAdaper extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    int mNumOfTabs; //tab의 갯수

    public PageAdaper(FragmentManager fm, int numOfTabs) {
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

    @Override

    public int getItemPosition(Object object) {

        return POSITION_NONE;

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("sese",position+"");
        switch (position) {
            case 0:
                Log.d("sese","fdf");
                refresh();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void refresh() {
        this.notifyDataSetChanged();

    }


}


