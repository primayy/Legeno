package com.example.billage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.billage.R;
import com.example.billage.UsageList;


import java.util.List;

public class UsageAdapter extends ArrayAdapter<UsageList> {

    private Context context;
    private List mList;
    private ListView mListView;


    class UserViewHolder {
        public TextView date;
        public TextView cost;
        public TextView destination;
        public TextView time;

    }


    // UsageAdapter 초기화하기 위한 생성자로 아규먼트로 전달받은 context와 list를 내부 저장 공간에 저장합니다.
    public UsageAdapter(Context context, List<UsageList> list, ListView listview) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
        this.mListView = listview;
    }

    // ListView의  한 줄(row)이 렌더링(rendering)될 때 호출되는 메소드로 row를 위한 view를 리턴합니다.
    // 한 줄(Row)를 위한 뷰(View)를 재사용하여 ListIView의 효율성을 올립니다.
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {
        View rowView = convertView; // 코드 가독성을 위해서 rowView 변수를 사용합니다.
        UserViewHolder viewHolder;
        String Status;

        // 기존에 생성했던 뷰라면 재사용하고 그렇지 않으면 XML 파일을 새로 view 객체로 변환합니다.
        if (rowView == null) {

            // 레이아웃을 정의한 XML 파일(R.layout.list_item)을 읽어서 계층 구조의 뷰 객체(rowView)로 변환합니다.
            // rowView 객체는 3개의 TextView로 구성되어 있습니다.
            // rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parentViewGroup, false);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.group_list, parentViewGroup, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            //
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰를 찾습니다.
            // 찾는 뷰의 타입에 따라 findViewById 리턴 결과를 타입 변환해줘야 합니다.
            viewHolder = new UserViewHolder();
            viewHolder.date = (TextView) rowView.findViewById(R.id.date);
            viewHolder.cost = (TextView) rowView.findViewById(R.id.cost);
            viewHolder.destination = (TextView) rowView.findViewById(R.id.destination);
            viewHolder.time = (TextView) rowView.findViewById(R.id.time);

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


        //UsageList 객체 리스트의 position 위치에 있는 Voca 객체를 가져옵니다.
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


        //현재 선택된 UsageList 객체를 화면에 보여주기 위해서 앞에서 미리 찾아 놓은 뷰에 데이터를 집어넣습니다.
        viewHolder.date.setText(usage.getDate());
        viewHolder.destination.setText(usage.getDestination());
        viewHolder.time.setText(usage.getTime());

        if(usage.getUsage_type().equals("입금")){
            viewHolder.cost.setText("+ "+usage.getCost()+"원");
        }else {
            viewHolder.cost.setText("- "+usage.getCost()+"원");
        }

//        viewHolder.itemIndex.setText("Voca[" + position + "]");
//        viewHolder.itemTag.setText(tag);
//        viewHolder.itemStatus.setText(Status);


        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        // 화면에 보여질 뷰를 리턴하여 ListView의 특정 줄로 보여지게 합니다.
        //본 예제에서는 5개의 TextView 구성되어 있습니다.
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
