package com.example.billage.frontend.ui.home.subView.usage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.api.data.UserAccount;
import com.example.billage.backend.common.Utils;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.ui.addUsage.AddUsage;
import com.example.billage.frontend.ui.howmuch.HowMuch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class UsageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private UsageAdapter usageAdapter;
    private View root;
    private com.getbase.floatingactionbutton.FloatingActionsMenu multipleButton;

    public UsageFragment() {
    }

    public static UsageFragment newInstance(String param1, String param2) {
        UsageFragment fragment = new UsageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_usage, container, false);
        multipleButton =  root.findViewById(R.id.multiple_actions);

        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        final ListView listview = (ListView) root.findViewById(R.id.usage_list) ;
        usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(usageAdapter);

        setAddUsage();
        setRefresh();


        setHowMuch();


        return root;
    }

    private void setHowMuch() {
        com.getbase.floatingactionbutton.FloatingActionButton howmuchButton =  root.findViewById(R.id.how_much);
        howmuchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                multipleButton.toggle();
                Intent intent=new Intent(getActivity(), HowMuch.class);
                startActivity(intent);
            }
        });
    }

    private void setAddUsage() {
        com.getbase.floatingactionbutton.FloatingActionButton usageButton =  root.findViewById(R.id.add_usage);
        usageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                multipleButton.toggle();
                Intent intent=new Intent(getActivity(), AddUsage.class);
                startActivity(intent);
            }
        });
    }

    private void setRefresh() {
        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FFC100"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<UserAccount> user_accounts = Utils.getUserAccount();

                //To-do
                Utils.getUserTrans();
                Utils.getUserBalance();
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    public void refresh(){
        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) root.findViewById(R.id.usage_list) ;
        usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(usageAdapter);
    }
    @Override
    public void onResume()
    {

        Log.d("resume","rere");
        super.onResume();
    }


}
