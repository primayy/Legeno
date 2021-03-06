package com.example.billage.frontend.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.billage.R;
import com.example.billage.backend.common.Utils;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.addUsage.AddUsage;
import com.example.billage.frontend.ui.home.subView.usage.UsageFragment;


import java.util.List;

public class UsageAdapter extends ArrayAdapter<UsageList> {

    private Activity mActivity;
    private Context context;
    private List mList;
    private ListView mListView;


    static class UserViewHolder {
        public TextView date;
        public TextView cost;
        public TextView destination;
        public TextView time;
        public ImageView type_image;
        public TextView bank_name;
        public CardView cardView;

    }


    public UsageAdapter(Context context, List<UsageList> list, ListView listview, Activity activity) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
        this.mListView = listview;
        this.mActivity = activity;
    }

    // ListView의  한 줄(row)이 렌더링(rendering)될 때 호출되는 메소드로 row를 위한 view를 리턴.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {
        View rowView = convertView;
        UserViewHolder viewHolder;
        String Status;

        if (rowView == null) {

            // 레이아웃을 정의한 XML 파일(R.layout.list_item)을 읽어서 계층 구조의 뷰 객체(rowView)로 변환.
            // rowView 객체는 3개의 TextView로 구성.
            // rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parentViewGroup, false);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.group_list, parentViewGroup, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            //
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰 찾기.
            viewHolder = new UserViewHolder();
            viewHolder.date = (TextView) rowView.findViewById(R.id.date);
            viewHolder.cost = (TextView) rowView.findViewById(R.id.cost);
            viewHolder.destination = (TextView) rowView.findViewById(R.id.destination);
            viewHolder.time = (TextView) rowView.findViewById(R.id.time);
            viewHolder.type_image= (ImageView) rowView.findViewById(R.id.type_image);
            viewHolder.bank_name = (TextView) rowView.findViewById(R.id.bank_name);
            viewHolder.cardView = (CardView) rowView.findViewById(R.id.cardview);


            rowView.setTag(viewHolder);

            Status = "created";
        } else {

            viewHolder = (UserViewHolder) rowView.getTag();
            Status = "reused";
        }

        // 태그 분석을 위한 코드 시작
        String Tag = rowView.getTag().toString();
        int idx = Tag.indexOf("@");
        String tag = Tag.substring(idx + 1);


        UsageList usage = (UsageList) mList.get(position);

        if(position == 0){
            rowView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
            rowView.findViewById(R.id.date).setVisibility(View.VISIBLE);

        }
        else{

            if(isNewGroupTitle(position)){
                rowView.findViewById(R.id.divider).setVisibility(View.VISIBLE);
                rowView.findViewById(R.id.date).setVisibility(View.VISIBLE);

            }
            else{
                rowView.findViewById(R.id.divider).setVisibility(View.GONE);
                rowView.findViewById(R.id.date).setVisibility(View.GONE);
            }
        }



        if(usage.getDate().equals(Utils.transformDate(Utils.getDate()))){
            viewHolder.date.setText(usage.getDate()+" (오늘)");
        }
        else{
            viewHolder.date.setText(usage.getDate());
        }


        viewHolder.cost.setText(usage.getCost());
        viewHolder.destination.setText(usage.getDestination());
        viewHolder.time.setText(usage.getTime().substring(0,5));

        // 추후 수정
        viewHolder.bank_name.setText(usage.getBank_name());

        if(usage.getUsage_type().equals("입금")){
            viewHolder.cost.setText("+ "+usage.getCost()+"원");
            viewHolder.type_image.setImageResource(R.drawable.deposit);
        }else {
            viewHolder.cost.setText("- "+usage.getCost()+"원");
            viewHolder.type_image.setImageResource(R.drawable.withdarw);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mActivity, AddUsage.class);
                intent.putExtra("cardTypeInfo",usage.getUsage_type());
                intent.putExtra("cardCostInfo",usage.getCost());
                intent.putExtra("cardDateInfo",usage.getDate());
                intent.putExtra("cardTimeInfo",usage.getTime());
                intent.putExtra("cardDestInfo",usage.getDestination());
                intent.putExtra("cardMemoInfo",usage.getMemo());
                intent.putExtra("cardTransTypeInfo",usage.getType());
                intent.putExtra("cardId",usage.getId());
                intent.putExtra("cardBankCode",usage.getBank_code());

                mActivity.startActivity(intent);
            }
        });

        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return true;
            }
        });

        return rowView;
    }

    private boolean isNewGroupTitle(int position)
    {
        //현재의 타이틀을 판단
        UsageList nowUsageList = (UsageList) mList.get(position);
        String nowDate  = nowUsageList.getDate();

        UsageList preUsageList = (UsageList) mList.get(position-1);
        String preDate = preUsageList.getDate();
        if(nowDate.equals(preDate)){
            return false;
        }

        return true;


    }



}