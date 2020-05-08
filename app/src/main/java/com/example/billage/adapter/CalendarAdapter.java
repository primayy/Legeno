package com.example.billage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.billage.MonthUsageList;
import com.example.billage.UsageList;
import com.example.billage.databinding.CalendarHeaderBinding;
import com.example.billage.databinding.DayItemBinding;
import com.example.billage.databinding.EmptyDayBinding;
import com.example.billage.ui.addUsage.DetailUsage;
import com.example.billage.ui.calendar.CalendarHeaderViewModel;
import com.example.billage.ui.calendar.CalendarViewModel;
import com.example.billage.ui.calendar.EmptyViewModel;
import com.example.billage.R;
import com.example.billage.ui.addUsage.AddUsage;


public class CalendarAdapter extends RecyclerView.Adapter {
    private final int HEADER_TYPE = 0;
    private final int EMPTY_TYPE = 1;
    private final int DAY_TYPE = 2;

    private List<Object> mCalendarList;
    private ArrayList<UsageList> usageList;
    private ArrayList<MonthUsageList> monthIncome;
    private ArrayList<MonthUsageList> monthUsage;
    private Context mContext;

    public CalendarAdapter(Context mContext, List<Object> calendarList, ArrayList<UsageList> items,ArrayList<MonthUsageList> month_income, ArrayList<MonthUsageList> month_usage) {
        this.mContext = mContext;
        mCalendarList = calendarList;
        usageList = items;
        monthIncome = month_income;
        monthUsage = month_usage;

    }

    public void setCalendarList(List<Object> calendarList) {
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) { //뷰타입 나누기
        Object item = mCalendarList.get(position);
        if (item instanceof Long) {
            return HEADER_TYPE; //날짜 타입
        } else if (item instanceof String) {
            return EMPTY_TYPE; // 비어있는 일자 타입
        } else {
            return DAY_TYPE; // 일자 타입

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) { // 날짜 타입
            CalendarHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_calendar_header, parent, false);
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
            params.setFullSpan(true); //Span을 하나로 통합하기
            binding.getRoot().setLayoutParams(params);
            return new HeaderViewHolder(binding);
        } else if (viewType == EMPTY_TYPE) { //비어있는 일자 타입
            EmptyDayBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_day_empty, parent, false);
            return new EmptyViewHolder(binding);
        }
        DayItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_day, parent, false);// 일자 타입
        return new DayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEADER_TYPE) { //날짜 타입 꾸미기
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarHeaderViewModel model = new CalendarHeaderViewModel();
            if (item instanceof Long) {

                Calendar month = Calendar.getInstance();
                Calendar current_month = Calendar.getInstance();
                month.setTimeInMillis((Long)item);

                // initialize
                model.setHeaderEarn("수입: 0원");
                model.setHeaderUsage("수입: 0원");
                model.setHeaderCount("수입: 0건");

                int index_tracker = 3 - (current_month.get(Calendar.MONTH) - month.get(Calendar.MONTH) );
                int total_count = monthIncome.get(index_tracker).getCount() + monthUsage.get(index_tracker).getCount();

                if(monthIncome.get(index_tracker).getCost().equals("")){
                    model.setHeaderEarn("수입: 0원");
                }else{
                    model.setHeaderEarn("수입: "+monthIncome.get(index_tracker).getCost()+"원");//월간 수입 값 넣는 곳
                }
                if(monthUsage.get(index_tracker).getCost().equals("")){
                    model.setHeaderEarn("지출: 0원");
                }else{
                    model.setHeaderUsage("지출: "+monthUsage.get(index_tracker).getCost()+"원"); //월간 지출 값 넣는 곳
                }

                model.setHeaderDate((Long) item);
                model.setHeaderCount("거래 횟수: "+total_count+"건"); //월간 거래 수 값 넣는 곳
            }
            holder.setViewModel(model);

        } else if (viewType == EMPTY_TYPE) { //비어있는 날짜 타입 꾸미기
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            EmptyViewModel model = new EmptyViewModel();
            holder.setViewModel(model);
        } else if (viewType == DAY_TYPE) { // 일자 타입 꾸미기
            DayViewHolder holder = (DayViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarViewModel model = new CalendarViewModel();
            if (item instanceof Calendar) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                model.setCalendar((Calendar) item);
                model.setCalendarEarn("");
                model.setCalendarUsage("");

                for(int i=0;i<usageList.size();i++){
                    try {
                        Date date = dateFormat.parse(usageList.get(i).getDate());
                        if(date.equals(((Calendar) item).getTime())){

                            if(usageList.get(i).getUsage_type().equals("입금")){

                               // model.setCalendarEarn("+"+usageList.get(i).getCost()); // 일간 수입 값 넣는 곳
                                model.setCalendarEarn("+"+usageList.get(i).getCost());
                            }else {
                                model.setCalendarUsage("-" + usageList.get(i).getCost()); // 일간 지출 값 넣는 곳
                                break;
                            }

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            holder.setViewModel(model);

        }
    }

    @Override
    public int getItemCount() {
        if (mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder { //날짜 타입 ViewHolder
        private CalendarHeaderBinding binding;

        private HeaderViewHolder(@NonNull CalendarHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(CalendarHeaderViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }


    private class EmptyViewHolder extends RecyclerView.ViewHolder { // 비어있는 요일 타입 ViewHolder
        private EmptyDayBinding binding;

        private EmptyViewHolder(@NonNull EmptyDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(EmptyViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }

    }

    private class DayViewHolder extends RecyclerView.ViewHolder {// 요일 타입 ViewHolder
        private DayItemBinding binding;

        private DayViewHolder(@NonNull DayItemBinding binding) { // 특정 날짜 클릭 이벤트 리스너
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext() , DetailUsage.class);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    intent.putExtra("click_date",simpleDateFormat.format(binding.getModel().getCalendar().getTime()));
                    mContext.startActivity(intent);
                }
            });
            this.binding = binding;
        }

        private void setViewModel(CalendarViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
