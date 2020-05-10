package com.example.billage.frontend.ui.addUsage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import com.example.billage.R;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.adapter.DetailAdapter;
import com.example.billage.backend.common.AppData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class DetailUsage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_usage);

        Intent intent = getIntent();
        String click_date = Objects.requireNonNull(intent.getExtras()).getString("click_date");

        // 헤더 날짜 데이터 받기
        TextView header_date = (TextView)findViewById(R.id.header_date);
        try {
            header_date.setText(transformDate(click_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ImageButton close_btn = (ImageButton) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createListView(click_date);

    }


    private void createListView(String click_date) {
        // 해당 날짜 이력 출력
        ArrayList<UsageList> selected_items = dataSearch(click_date);

        if(selected_items.size() == 0) {
            TextView empty_view = (TextView) findViewById(R.id.empty_text);
            empty_view.setText("거래내역이 없습니다.");
        }else {
            final ListView listView = (ListView) findViewById(R.id.selected_list);
            final DetailAdapter usageAdapter = new DetailAdapter(this, selected_items, listView);

            listView.setAdapter(usageAdapter);
        }
    }

    private ArrayList<UsageList> dataSearch(String click_date) {
        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        ArrayList<UsageList> selected_item = new ArrayList<UsageList>();
        for(int i=0; i< items.size();i++){

            if(items.get(i).getDate().equals(click_date)){
                selected_item.add(items.get(i));
            }
        }

        return selected_item;
    }

    //날짜 데이터 형 변환
    private String transformDate (String date) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date prev_date = transFormat.parse(date);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");


        return simpleDateFormat.format(prev_date);

    }
}
