package com.example.billage.frontend.ui.home.subView.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

public class UnfixDetail extends AppCompatActivity {

    private UsageAdapter usageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfix_detail);

        Intent intent = getIntent();

        final ArrayList<UsageList> items = AppData.mdb.getAbnormalTrans();
        final ListView listview = (ListView) findViewById(R.id.usage_list) ;
        usageAdapter = new UsageAdapter(this,items,listview,this);
        listview.setAdapter(usageAdapter);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        super.onBackPressed();
    }
}
