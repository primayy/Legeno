package com.example.billage.frontend.ui.quest.subView.month;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.billage.R;
import com.example.billage.frontend.MainActivity;
import com.example.billage.frontend.adapter.QuestAdapter;
import com.example.billage.frontend.data.QuestList;
import com.yy.mobile.rollingtextview.RollingTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Month extends Fragment {
    RollingTextView coin;

    public Month(RollingTextView coin) {
        this.coin = coin;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int completeRate = 0;
        int completeCount = 0;
        // Inflate the layout for this fragment\
        View root = inflater.inflate(R.layout.quest_month, container, false);

        MainActivity mainActivity = new MainActivity();

        ArrayList<QuestList> items = mainActivity.getMonthQuestList();

        for(int i = 0; i<items.size();i++){

            if(items.get(i).getComplete().equals("true")){
                completeCount++;
            }
        }

        completeRate = 100*completeCount/items.size();
        ListView listview = (ListView) root.findViewById(R.id.month_list);

        QuestAdapter questAdapter = new QuestAdapter(getActivity(),items,listview,getActivity(),coin);
        listview.setAdapter(questAdapter);

        ProgressBar progressBar = root.findViewById(R.id.quest_progress);
        progressBar.setProgress(completeRate);

        TextView progress_text = root.findViewById(R.id.progress_text);
        progress_text.setText(completeRate+"%");
        return root;
    }
}
