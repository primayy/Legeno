package com.example.billage.ui.home.subView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1f, 20000));
        entries.add(new Entry(2f, 40000));
        entries.add(new Entry(3f, 160000));
        entries.add(new Entry(4f, -12000));
        entries.add(new Entry(5f, 17000));
        entries.add(new Entry(6f, 10000));
//        entries.add(new Entry(7f, 360));
//        entries.add(new Entry(8f, -200));
//        entries.add(new Entry(9f, 400));
//        entries.add(new Entry(10f, 100));
//        entries.add(new Entry(11f, 100));
//        entries.add(new Entry(12f, 100));

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 20000));
        barEntries.add(new BarEntry(2f, 4000));
        barEntries.add(new BarEntry(3f, 1600));
        barEntries.add(new BarEntry(4f, 12000));
        barEntries.add(new BarEntry(5f, 17000));
        barEntries.add(new BarEntry(6f, 10000));
        barEntries.add(new BarEntry(7f, 36300));

        setLineChart(root,entries);
        setBarChart(root,barEntries);

        return root;
    }

    public void setLineChart(View root, ArrayList<Entry> entries){

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);

        // x,y축 맵핑
        LineDataSet depenses = new LineDataSet(entries, "");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add((ILineDataSet)depenses);
        LineData data = new LineData(dataSets);

        depenses.setDrawFilled(true); //그래프 밑부분 색칠
        Drawable gradient = ContextCompat.getDrawable(root.getContext(), R.drawable.fade);
        depenses.setFillDrawable(gradient);
        depenses.setLabel("월간 수입 대비 지출 금액");

        // 그래프 customizing
        depenses.setDrawValues(false);
        depenses.setCircleRadius(5f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setExtraRightOffset(40);


        //x축 customizing
        final String[] months = {"","1월", "2월", "3월", "4월", "5월", "6월", "7월","8월","9월","10월","11월","12월"}; // Your List / array with String Values For X-axis Labels

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(data.getEntryCount());
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

    public void setBarChart(View root, ArrayList entries){

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

        //y축 customizing
        LimitLine limitLine = new LimitLine(15000f,"평균 지출 금액");
        limitLine.setLineWidth(2f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        limitLine.setTextSize(10f);



        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextSize(13f);
        leftAxis.addLimitLine(limitLine);

        //custom marker view 설정
        CustomMarker marker = new CustomMarker(root.getContext(),R.layout.custom_marker);
        barChart.setMarker(marker);

        barChart.setData(data);
        barChart.animateXY(1000,1000);
        barChart.invalidate();

    }


}
