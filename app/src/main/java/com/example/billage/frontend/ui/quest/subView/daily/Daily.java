package com.example.billage.frontend.ui.quest.subView.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.QuestChecker;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.MainActivity;
import com.example.billage.frontend.adapter.QuestAdapter;
import com.example.billage.frontend.data.QuestList;

import org.json.JSONException;

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

     // ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        MainActivity mainActivity = new MainActivity();
        QuestChecker questChecker = mainActivity.getQuestChecker();

        ArrayList<QuestList> items = null;
        try {
            items = questChecker.parseQuestList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<items.size();i++){
            items
        }

        ListView listview = (ListView) root.findViewById(R.id.daliy_list) ;
        // listview.setAdapter(adapter) ;

        QuestAdapter questAdapter = new QuestAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(questAdapter);
        return root;
    }
}
