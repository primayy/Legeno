package com.example.billage.frontend.ui.quest.subView.ingame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.billage.R;
import com.example.billage.frontend.adapter.QuestAdapter;
import com.example.billage.frontend.data.QuestList;

import java.util.ArrayList;


public class InGame extends Fragment {

    public InGame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int complete_rate = 75;
        // Inflate the layout for this fragment\
        View root = inflater.inflate(R.layout.quest_in_game, container, false);

        // ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        ArrayList<QuestList> items = new ArrayList<>();
        items.add(new QuestList(1,"계획 소비","일일 소비량을 10000원 이내로 사용한다.","완료","1200","일간"));
        items.add(new QuestList(1,"계획 소비","일일 소비량을 10000원 이내로 사용한다.","완료","1200","일간"));
        items.add(new QuestList(1,"계획 소비","일일 소비량을 10000원 이내로 사용한다.","진행중","1200","일간"));
        items.add(new QuestList(1,"계획 소비","일일 소비량을 10000원 이내로 사용한다.","완료","1200","일간"));

        ListView listview = (ListView) root.findViewById(R.id.ingame_list);

        QuestAdapter questAdapter = new QuestAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(questAdapter);

        ProgressBar progressBar = root.findViewById(R.id.quest_progress);
        progressBar.setProgress(complete_rate);

        TextView progress_text = root.findViewById(R.id.progress_text);
        progress_text.setText(complete_rate+"%");
        return root;
}
}
