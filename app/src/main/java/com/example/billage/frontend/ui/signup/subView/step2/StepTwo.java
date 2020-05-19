package com.example.billage.frontend.ui.signup.subView.step2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.billage.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepTwo extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View root;

    public StepTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment step2.
     */
    public static StepTwo newInstance(String param1, String param2) {
        StepTwo fragment = new StepTwo();
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
        root = inflater.inflate(R.layout.signup_step2, container, false);




        return root;
    }

    private void setFadeAnimation(View root,int target,int delay) {
        TextView welcome = root.findViewById(target);
        Animation animation = AnimationUtils.loadAnimation(root.getContext(),R.anim.animation_fade);
        animation.setStartOffset(delay);
        welcome.startAnimation(animation);
    }


    @Override public void onResume()
    {

        setFadeAnimation(root,R.id.textView,0);
        setFadeAnimation(root,R.id.textView2,250);
        setFadeAnimation(root,R.id.textView3,500);
        setFadeAnimation(root,R.id.textView4,750);
        setFadeAnimation(root,R.id.textView5,1000);
        super.onResume();
    }

}
