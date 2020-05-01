package com.example.billage.ui.home.subView;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);

        ArrayList<Entry> entries = new ArrayList<>();


        entries.add(new Entry(1f, 200));
        entries.add(new Entry(2f, 400));
        entries.add(new Entry(3f, 160));
        entries.add(new Entry(4f, 120));
        entries.add(new Entry(5f, 170));
        entries.add(new Entry(6f, 100));
        entries.add(new Entry(7f, 360));
        entries.add(new Entry(8f, -200));
        entries.add(new Entry(9f, 400));
        entries.add(new Entry(10f, 100));
        entries.add(new Entry(11f, 100));
        entries.add(new Entry(12f, 100));


        // x,y축 맵핑
        LineDataSet depenses = new LineDataSet(entries, "월별 지출 금액");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add((ILineDataSet)depenses);
        LineData data = new LineData(dataSets);

        depenses.setDrawCircles(true); //선 둥글게 만들기
        depenses.setDrawFilled(true); //그래프 밑부분 색칠


        //x축 customizing
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15f);

        //y축 customizing
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextSize(15f);
        leftAxis.setDrawZeroLine(true);

        //value customizing
        data.setValueTextSize(15f);

        ViewPortHandler handler = lineChart.getViewPortHandler();


        //그래프 확대 방지
        lineChart.getViewPortHandler().setMaximumScaleX(1f);
        lineChart.getViewPortHandler().setMaximumScaleY(1f);
        lineChart.getViewPortHandler().setMinimumScaleX(1f);
        lineChart.getViewPortHandler().setMinimumScaleY(1f);

        
        lineChart.setData(data);
        lineChart.animateXY(1000,1000);
        lineChart.invalidate();





        return root;
    }




}
