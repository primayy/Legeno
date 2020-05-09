package com.example.billage.frontend.ui.home.subView;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.frontend.data.MonthUsageList;
import com.example.billage.R;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.adapter.CalendarAdapter;
import com.example.billage.backend.common.AppData;
import com.example.billage.frontend.ui.calendar.CalendarListViewModel;
import com.example.billage.databinding.CalendarListBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CalendarListBinding binding;
    private CalendarListViewModel model;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallenderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
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



        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_calender,container,false);

        if(viewModelFactory == null){
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        }
        model = new ViewModelProvider(getActivity(),viewModelFactory).get(CalendarListViewModel.class);
        binding.setModel(model);
        binding.setLifecycleOwner(getActivity());

        if (model != null) {
            model.initCalendarList();
        }

        //달별 수입,지출 합, 카운트 리턴
        ArrayList<MonthUsageList> month_income = AppData.mdb.getTransMonthsColumns("입금");
        ArrayList<MonthUsageList> month_usage = AppData.mdb.getTransMonthsColumns("출금");


        ArrayList<UsageList> items = AppData.mdb.getTransDaysColumns();
        observe(getActivity(),items,month_income,month_usage);

        return binding.getRoot();
    }

    private void observe(FragmentActivity activity, ArrayList<UsageList> items,ArrayList<MonthUsageList> month_income, ArrayList<MonthUsageList> month_usage) {
        model.mCalendarList.observe(activity, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> objects) {
                RecyclerView view = binding.pagerCalendar;
                CalendarAdapter adapter = (CalendarAdapter) view.getAdapter();
                if (adapter != null) {
                    adapter.setCalendarList(objects);
                } else {
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
                    adapter = new CalendarAdapter(view.getContext(),objects,items, month_income, month_usage);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);

                    if (model.mCenterPosition >= 0) {
                        view.scrollToPosition(model.mCenterPosition);
                    }
                }
            }
        });
    }


}
