package com.example.billage.frontend.ui.quest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.billage.R;
import com.example.billage.backend.GetSetDB;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.adapter.QuestPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import java.text.DecimalFormat;

public class QuestFragment extends Fragment {

    private QuestViewModel questViewModel;
    private ViewPager viewPager;
    private QuestPageAdapter myPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questViewModel = ViewModelProviders.of(this).get(QuestViewModel.class);
        View root = inflater.inflate(R.layout.quest, container, false);

        GetSetDB getSetDB = new GetSetDB();

        int avg_usage = AppData.mdb.getTransUsedAvg("출금");
        DecimalFormat number_format = new DecimalFormat("###,###");

        RollingTextView coin = (RollingTextView) root.findViewById(R.id.coin_value);

        coin.setAnimationDuration(1000L);
        coin.setCharStrategy(Strategy.NormalAnimation());
        coin.addCharOrder(CharOrder.Number);
        coin.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        coin.setText(String.valueOf(number_format.format(getSetDB.getCoin())));

        TabLayout tabLayout = root.findViewById(R.id.three_tabs) ;

        viewPager = root.findViewById(R.id.quest_viewpager);
        myPagerAdapter = new QuestPageAdapter(getChildFragmentManager(), tabLayout.getTabCount(),coin);
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        RollingTextView rollingTextView = (RollingTextView) root.findViewById(R.id.average_value);

        rollingTextView.setAnimationDuration(1000L);
        rollingTextView.setCharStrategy(Strategy.NormalAnimation());
        rollingTextView.addCharOrder(CharOrder.Number);
        rollingTextView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        rollingTextView.setText(String.valueOf(number_format.format(avg_usage)));





        return root;
    }
}
