package com.example.billage.frontend.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.billage.R;
import com.example.billage.frontend.ui.signup.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import com.example.billage.frontend.adapter.PageAdaper;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import com.yy.mobile.rollingtextview.strategy.Strategy;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        TabLayout tabLayout = root.findViewById(R.id.three_tabs) ;

        final ViewPager viewPager = root.findViewById(R.id.home_viewpager);
        final PagerAdapter myPagerAdapter = new PageAdaper(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        RollingTextView rollingTextView = (RollingTextView) root.findViewById(R.id.current_money);
        rollingTextView.setAnimationDuration(1500L);
        rollingTextView.setCharStrategy(Strategy.NormalAnimation());
        rollingTextView.addCharOrder(CharOrder.Number);
        rollingTextView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        rollingTextView.setText("3,100,450");


        return root;
    }


}
