package com.example.billage.frontend.ui.home.subView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.R;
import com.example.billage.backend.api.Statistic_transaction;
import com.example.billage.backend.common.AppData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
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

import java.text.ParseException;
import java.util.ArrayList;

public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticFragment() {
        // Required empty public constructor
    }

    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_statistic, container, false);

        ArrayList<Entry> income_entries = new ArrayList<>();
        ArrayList<Entry> usage_entries = new ArrayList<>();

        ArrayList<Integer> income = Statistic_transaction.monthly_statistic("입금");
        ArrayList<Integer> usage = Statistic_transaction.monthly_statistic("출금");

        for(int i=1;i<=income.size();i++){
            income_entries.add(new Entry(i, income.get(i-1)));
        }
        for(int i=1;i<=usage.size();i++){
            usage_entries.add(new Entry(i, usage.get(i-1)));
        }


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        //통계데이터 호출 코드
        try {
            ArrayList<Integer> bar_data = Statistic_transaction.daily_statistic("출금");
            for(int i=1;i<=bar_data.size();i++){
                barEntries.add(new BarEntry(i, bar_data.get(i-1)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //이전 3개월의 수입 or 지출 평균 리턴
        int avg_usage = AppData.mdb.getTransThreeMonthsAvg("출금");
        /**/

        setLineChart(root,income_entries,usage_entries);
        setBarChart(root,barEntries,avg_usage);

        return root;
    }

    public void setLineChart(View root, ArrayList<Entry> income_entries,  ArrayList<Entry> usage_entries){

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);

        // x,y축 맵핑
        LineDataSet depenses_income = new LineDataSet(income_entries, "수입");
        LineDataSet depenses_usage = new LineDataSet(usage_entries, "지출");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add((ILineDataSet)depenses_income);
        dataSets.add((ILineDataSet)depenses_usage);
        LineData data = new LineData(dataSets);

        depenses_income.setDrawFilled(true); //그래프 밑부분 색칠
        depenses_usage.setDrawFilled(true);
        Drawable gradient_income = ContextCompat.getDrawable(root.getContext(), R.drawable.fade);
        Drawable gradient_usage = ContextCompat.getDrawable(root.getContext(), R.drawable.fade_usage);
        depenses_income.setFillDrawable(gradient_income);
        depenses_usage.setFillDrawable(gradient_usage);

        // 그래프 customizing
        depenses_income.setDrawValues(false);
        depenses_usage.setDrawValues(false);
        depenses_income.setCircleRadius(5f);
        depenses_usage.setCircleRadius(5f);
        depenses_income.setCircleColor(Color.parseColor("#0A9FEF"));
        depenses_usage.setCircleColor(Color.parseColor("#EF0A4F"));
        depenses_income.setColor(Color.parseColor("#0A9FEF"));
        depenses_usage.setColor(Color.parseColor("#EF0A4F"));

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setExtraRightOffset(40);

        //x축 customizing
        final String[] months = {"","1월", "2월", "3월", "4월", "5월", "6월", "7월","8월","9월","10월","11월","12월"}; // Your List / array with String Values For X-axis Labels

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(data.getEntryCount()-6);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13f);
        xAxis.setDrawGridLines(false);

        //y축 customizing
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextSize(13f);
        leftAxis.setDrawZeroLine(true);

        //custom marker view 설정
        CustomMarker marker = new CustomMarker(root.getContext(),R.layout.custom_marker);
        lineChart.setMarker(marker);

        //value customizing
        data.setValueTextSize(15f);

        lineChart.setData(data);
        lineChart.animateXY(1000,1000);
        lineChart.invalidate();

    }

    public void setBarChart(View root, ArrayList entries, int avg_usage){

        BarChart barChart = (BarChart) root.findViewById(R.id.bar_chart);

        // x,y축 맵핑
        BarDataSet depenses = new BarDataSet(entries, "주간 지출 금액");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add((IBarDataSet)depenses);
        BarData data = new BarData(dataSets);



        // 그래프 customizing
        depenses.setDrawValues(false);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setExtraRightOffset(40);

        depenses.setGradientColor(Color.parseColor("#a2d0ea"),Color.parseColor("#0A9FEF"));


        //x축 customizing

        final String[] weekdays = {"","월","화","수","목","금","토","일"}; // Your List / array with String Values For X-axis Labels

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(data.getEntryCount());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13f);
        xAxis.setDrawGridLines(false);

        //limit line customizing


        LimitLine limitLine = new LimitLine((float)avg_usage,"");
        limitLine.setLineWidth(2f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        limitLine.setTextSize(10f);


        //y축 customizing
        YAxis rightAxis = barChart.getAxisRight();

        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextSize(13f);
        Log.d("dd",leftAxis.getAxisMaximum()+"");
        leftAxis.addLimitLine(limitLine);

        //custom marker view 설정
        CustomMarker marker = new CustomMarker(root.getContext(),R.layout.custom_marker);
        barChart.setMarker(marker);

        barChart.setData(data);
        barChart.animateXY(1000,1000);
        barChart.invalidate();

    }
}
