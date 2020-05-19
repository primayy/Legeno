package com.example.billage.frontend.ui.quest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.billage.R;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.adapter.PageAdaper;
import com.google.android.material.tabs.TabLayout;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import java.text.DecimalFormat;

public class QuestFragment extends Fragment {

    private QuestViewModel questViewModel;
    private ViewPager viewPager;
    private PageAdaper myPagerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questViewModel = ViewModelProviders.of(this).get(QuestViewModel.class);
        View root = inflater.inflate(R.layout.quest, container, false);


        int avg_usage = AppData.mdb.getTransThreeMonthsAvg("출금");
        DecimalFormat number_format = new DecimalFormat("###,###");


        TabLayout tabLayout = root.findViewById(R.id.three_tabs) ;

        viewPager = root.findViewById(R.id.quest_viewpager);
        myPagerAdapter = new PageAdaper(getChildFragmentManager(), tabLayout.getTabCount());
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
