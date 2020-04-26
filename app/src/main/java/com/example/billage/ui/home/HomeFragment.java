package com.example.billage.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.billage.R;
import com.google.android.material.tabs.TabLayout;

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
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.three_tabs) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.d("test", String.valueOf(tab.getPosition()));
                int position = tab.getPosition();
                //changeView(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        }) ;

        return root;
    }

//    private void changeView (int index) {
//        TextView textView1 = (TextView) root.findViewById(R.id.text1) ;
//        TextView textView2 = (TextView) root.findViewById(R.id.text2) ;
//        TextView textView3 = (TextView) root.findViewById(R.id.text3) ;
//
//        switch (index) {
//            case 0 :
//                textView1.setVisibility(View.VISIBLE) ;
//                textView2.setVisibility(View.INVISIBLE) ;
//                textView3.setVisibility(View.INVISIBLE) ;
//                break ;
//            case 1 :
//                textView1.setVisibility(View.INVISIBLE) ;
//                textView2.setVisibility(View.VISIBLE) ;
//                textView3.setVisibility(View.INVISIBLE) ;
//                break ;
//            case 2 :
//                textView1.setVisibility(View.INVISIBLE) ;
//                textView2.setVisibility(View.INVISIBLE) ;
//                textView3.setVisibility(View.VISIBLE) ;
//                break ;
//
//        }
//    }
}
