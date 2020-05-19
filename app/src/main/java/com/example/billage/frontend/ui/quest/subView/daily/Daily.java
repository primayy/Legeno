package com.example.billage.frontend.ui.quest.subView.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;

import java.util.ArrayList;


public class Daily extends Fragment {

    public Daily() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quest_daily, container, false);
        // Inflate the layout for this fragment

//      ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        ArrayList<UsageList> items = new ArrayList<>();

//        ListView listview = (ListView) root.findViewById(R.id.daliy_list) ;
////        // listview.setAdapter(adapter) ;
////
////        final UsageAdapter usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
////        listview.setAdapter(usageAdapter);
        return root;
    }
}
