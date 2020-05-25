package com.example.billage.frontend.ui.home.subView.usage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.api.AccountTransaction;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.ui.addUsage.AddUsage;
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

        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        final ListView listview = (ListView) root.findViewById(R.id.usage_list) ;
        usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(usageAdapter);

        setAddUsage();
        setRefresh();



        return root;
    }

    private void setAddUsage() {
        FloatingActionButton usageButton = (FloatingActionButton) root.findViewById(R.id.add_usage);
        usageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddUsage.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void setRefresh() {
        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FFC100"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //To-do
                Log.d("refesh","dd");
                AccountTransaction.request_transaction("20200429","20200501");
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    public void refresh(){
        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        //Log.d("ddd",""+items.get(0).getDate());
        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) root.findViewById(R.id.usage_list) ;
        // listview.setAdapter(adapter) ;

        usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(usageAdapter);
    }


}
