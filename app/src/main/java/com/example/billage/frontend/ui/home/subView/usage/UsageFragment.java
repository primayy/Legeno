package com.example.billage.frontend.ui.home.subView.usage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.frontend.adapter.UsageAdapter;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.ui.addUsage.AddUsage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UsageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment usageFragment.
     */
    public static UsageFragment newInstance(String param1, String param2) {
        UsageFragment fragment = new UsageFragment();
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
        View root = inflater.inflate(R.layout.fragment_usage, container, false);


        final ArrayList<UsageList> items = AppData.mdb.getTransColumns();
        //Log.d("ddd",""+items.get(0).getDate());

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) root.findViewById(R.id.usage_list) ;
       // listview.setAdapter(adapter) ;


        final UsageAdapter usageAdapter = new UsageAdapter(getActivity(),items,listview,getActivity());
        listview.setAdapter(usageAdapter);

        FloatingActionButton usageButton = (FloatingActionButton) root.findViewById(R.id.add_usage);
        usageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddUsage.class);
                startActivity(intent);
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FFC100"));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //To-do
                Log.d("refesh","dd");

                // 새로고침 로드 완료
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        return root;
    }

}
