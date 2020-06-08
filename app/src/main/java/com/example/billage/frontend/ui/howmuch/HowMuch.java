package com.example.billage.frontend.ui.howmuch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.billage.R;
import com.example.billage.backend.HowMuchPay;
import com.example.billage.backend.api.StatisticTransaction;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.example.billage.frontend.adapter.HowMuchAdapter;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.home.subView.statistic.CustomMarker;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class HowMuch extends AppCompatActivity {

    private HorizontalBarChart horizontalBarChart;
    private HowMuchAdapter usageAdapter;
    private Activity mActivity;
    private Context context;
    private int cur_value;

    public HowMuch() {

        this.mActivity = this;
        this.context = this;
        this.cur_value = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_much);

        IndicatorSeekBar seekBar = findViewById(R.id.seek_bar);
        TextView header = findViewById(R.id.how_much_header);
        ImageButton refresh_btn = findViewById(R.id.refresh_btn);


        header.setText("지난 1개월간 총 지출은 " +AppData.mdb.getSelectTransOutMonths(1) +"원 입니다.");

        ArrayList<Integer> usage = StatisticTransaction.monthly_statistic("출금");
        horizontalBarChart = findViewById(R.id.horizontal_chart);
        horizontalBarChart.setVisibility(View.GONE);

        setSeekBar(seekBar, header, usage);
        setListView(cur_value);
        setRefresh(refresh_btn);

    }

    private void setRefresh(ImageButton refresh_btn) {
        Animation animation = new RotateAnimation(0.0f, 540.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(500);

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh_btn.startAnimation(animation);
                setListView(cur_value);
            }
        });
    }

    private void setListView(int cur_value) {
        ArrayList<HowMuchPay> items= Utils.getHowMuchPays(AppData.mdb.getSelectTransOutMonths(cur_value));
        final ListView listview = (ListView) findViewById(R.id.howmuch_list) ;
        usageAdapter = new HowMuchAdapter(context,items,listview,mActivity);
        listview.setAdapter(usageAdapter);
    }

    private void setSeekBar(IndicatorSeekBar seekBar, TextView header, ArrayList<Integer> usage) {
        seekBar.setIndicatorTextFormat("${TICK_TEXT}개월");
        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSeeking(SeekParams seekParams) {

                cur_value = seekBar.getProgress();
                header.setText("지난 "+cur_value + "개월간 총 지출은 " +AppData.mdb.getSelectTransOutMonths(cur_value) +"원 입니다.");

                int cur_value = seekBar.getProgress();
                header.setText(cur_value + "개월간 총 지출은 " +String.valueOf(AppData.mdb.getSelectTransOutMonths(cur_value)) +"원 입니다.");
                ArrayList<HowMuchPay> items;
                if(cur_value != 1){
                    horizontalBarChart.setVisibility(View.VISIBLE);
                    ArrayList<BarEntry> usage_entries = new ArrayList<>();

                    for(int i=2;i<=cur_value+1;i++){
                        usage_entries.add(new BarEntry(8-i, usage.get(i-2)));
                    }
                    setBarChart(usage_entries);
                    items= Utils.getHowMuchPays(AppData.mdb.getSelectTransOutMonths(cur_value),2);
                }
                else{
                    horizontalBarChart.setVisibility(View.GONE);
                    items= Utils.getHowMuchPays(AppData.mdb.getSelectTransOutMonths(cur_value),4);
                }
                setListView(cur_value);

                final ListView listview = (ListView) findViewById(R.id.howmuch_list) ;
                usageAdapter = new HowMuchAdapter(context,items,listview,mActivity);
                listview.setAdapter(usageAdapter);

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setBarChart(ArrayList usage_entries) {

        horizontalBarChart.getDescription().setEnabled(false);

        BarDataSet depenses_usage = new BarDataSet(usage_entries, "수입");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add((IBarDataSet)depenses_usage);
        BarData data = new BarData(dataSets);

        depenses_usage.setBarBorderWidth(0.9f);
        depenses_usage.setColors(ColorTemplate.VORDIPLOM_COLORS);
        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        LocalDate now = LocalDate.now();
        final String[] months = new String[]{"",now.minusMonths(6).getMonth().getValue()+"월", now.minusMonths(5).getMonth().getValue()+"월", now.minusMonths(4).getMonth().getValue()+"월", now.minusMonths(3).getMonth().getValue()+"월", now.minusMonths(2).getMonth().getValue()+"월",now.minusMonths(1).getMonth().getValue()+"월"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxis_right = horizontalBarChart.getAxisRight();
        yAxis_right.setDrawLabels(false);

        //value customizing
        data.setValueTextSize(0f);
        data.setBarWidth(0.5f);
        horizontalBarChart.setData(data);
        horizontalBarChart.animateXY(1000, 1000);
        horizontalBarChart.invalidate();
    }

}
