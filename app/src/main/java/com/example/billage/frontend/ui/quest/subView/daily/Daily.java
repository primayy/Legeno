package com.example.billage.frontend.ui.quest.subView.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.billage.R;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.QuestProcessor;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.MainActivity;
import com.example.billage.frontend.adapter.QuestAdapter;
import com.example.billage.frontend.data.QuestList;
import com.yy.mobile.rollingtextview.RollingTextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;


public class Daily extends Fragment {

    RollingTextView coin;

    public Daily(RollingTextView coin) {
        this.coin = coin;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quest_daily, container, false);
        // Inflate the layout for this fragment

     // ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        QuestProcessor questProcessor = new QuestProcessor();

        ArrayList<QuestList> items = questProcessor.getDailyQuestList();


        ListView listview = (ListView) root.findViewById(R.id.daliy_list) ;

        QuestAdapter questAdapter = new QuestAdapter(getActivity(),items,listview,getActivity(),coin);
        listview.setAdapter(questAdapter);
        return root;
    }
}
