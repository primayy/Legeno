package com.example.billage.frontend.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.example.billage.backend.HowMuchPay;
import com.example.billage.frontend.data.UsageList;
import com.example.billage.frontend.ui.addUsage.AddUsage;

import java.util.List;
import java.util.Random;

public class HowMuchAdapter extends ArrayAdapter<HowMuchPay> {

    private Activity mActivity;
    private Context context;
    private List mList;
    private ListView mListView;


    static class UserViewHolder {
        public TextView list_header;
        public TextView list_content;
        public TextView list_unit;


    }


    public HowMuchAdapter(Context context, List<HowMuchPay> list, ListView listview, Activity activity) {
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
            rowView = layoutInflater.inflate(R.layout.howmuch_list, parentViewGroup, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            //
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰 찾기.
            viewHolder = new UserViewHolder();
            viewHolder.list_header = (TextView) rowView.findViewById(R.id.list_header);
            viewHolder.list_content = (TextView) rowView.findViewById(R.id.list_content);
            viewHolder.list_unit = (TextView) rowView.findViewById(R.id.list_unit);

            rowView.setTag(viewHolder);

        } else {

            viewHolder = (UserViewHolder) rowView.getTag();
        }

        HowMuchPay howMuchPay = (HowMuchPay) mList.get(position);

        viewHolder.list_header.setText(howMuchPay.getName());
        viewHolder.list_content.setText(String.valueOf(howMuchPay.getValue()));
        viewHolder.list_unit.setText(" "+howMuchPay.getUnit());

        Resources res = context.getResources();
        int [] colors = res.getIntArray(R.array.textColorArray);

        viewHolder.list_content.setTextColor(colors[new Random().nextInt(colors.length)]);
        viewHolder.list_unit.setTextColor(colors[new Random().nextInt(colors.length)]);

        return rowView;
    }

}
