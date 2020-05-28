package com.example.billage.frontend.ui.home;

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
import com.example.billage.backend.GetSetADUserInfo;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.google.android.material.tabs.TabLayout;

import com.example.billage.frontend.adapter.PageAdaper;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Strategy;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewPager viewPager;
    private PageAdaper myPagerAdapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        DecimalFormat number_format = new DecimalFormat("###,###");

        RollingTextView rollingTextView = (RollingTextView) root.findViewById(R.id.current_money);
        rollingTextView.setAnimationDuration(1000L);
        rollingTextView.setCharStrategy(Strategy.NormalAnimation());
        rollingTextView.addCharOrder(CharOrder.Number);
        rollingTextView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        rollingTextView.setText(number_format.format(Integer.parseInt(AppData.getPref().getString("balance","0"))));

        Utils.getUserBalance();


        GetSetADUserInfo getSetADUserInfo = new GetSetADUserInfo();

        if(getSetADUserInfo.IsThereUserInfo()){
            TextView user_name = root.findViewById(R.id.username);
            user_name.setText(String.format("%s 님 현재", getSetADUserInfo.getUserInfo("user_name")));
        }


        return root;
    }

    @Override
    public void onResume()
    {
        TabLayout tabLayout = root.findViewById(R.id.three_tabs) ;

        viewPager = root.findViewById(R.id.home_viewpager);
        myPagerAdapter = new PageAdaper(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        super.onResume();
    }

}
