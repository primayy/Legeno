package com.example.billage.frontend.ui.signup.subView.step1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepOne extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Handler mHandler;

    public StepOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment step1.
     */
    public static StepOne newInstance(String param1, String param2) {
        StepOne fragment = new StepOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_step1, container, false);

        setAnimation(root);

        return root;
    }

    private void setAnimation(View root) {
        TextView welcome = root.findViewById(R.id.welcome);
        Animation animation = AnimationUtils.loadAnimation(root.getContext(),R.anim.animation_fade);
        welcome.startAnimation(animation);

        ImageView coin_img = root.findViewById(R.id.coin_img);
        Animation animation_coin = AnimationUtils.loadAnimation(root.getContext(),R.anim.animation_coin);
        coin_img.startAnimation(animation_coin);

        ImageView home_img = root.findViewById(R.id.home_img);
        Animation animation_home = AnimationUtils.loadAnimation(root.getContext(),R.anim.animation_home);
        home_img.startAnimation(animation_home);
    }
}
