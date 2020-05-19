package com.example.billage.frontend.ui.signup.subView.step3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.billage.R;
import com.example.billage.backend.JSONTask_Post;
import com.example.billage.backend.common.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * create an instance of this fragment.
 */
public class StepThree extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button next_btn;
    private ViewPager view_pager;
    private EditText user_name;
    private EditText nick_name;

    public StepThree(Button parent_view,ViewPager viewPager) {
        next_btn = parent_view;
        view_pager = viewPager;
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
        View root = inflater.inflate(R.layout.fragment_step3, container, false);

        user_name = root.findViewById(R.id.user_name);
        nick_name = root.findViewById(R.id.nickname);
        return root;
    }

    @Override
    public void onResume()
    {
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = user_name.getText().toString();
                String nickname = nick_name.getText().toString();
                Log.d("test", name+nickname);

                try {
                    //서버에 JSON data post
                    JSONObject signupData = new JSONObject();
                    signupData.accumulate("name", name);
                    signupData.accumulate("nickname",nickname);
                    signupData.accumulate("callID","signUp");
                    JSONTask_Post jsonTask= new JSONTask_Post(signupData);
                    jsonTask.execute("http://192.168.25.80:3000/SignUp");

                }catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
                int currentPage = view_pager.getCurrentItem();
                view_pager.setCurrentItem(currentPage + 1);
                next_btn.setText("시작하기");
            }
        });
        super.onResume();
    }


}
