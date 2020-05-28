package com.example.billage.frontend.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.morphingbutton.MorphingButton;
import com.example.billage.R;
import com.example.billage.backend.GetSetADUserInfo;
import com.example.billage.backend.GetSetDB;
import com.example.billage.frontend.data.QuestList;
import com.example.billage.frontend.ui.addUsage.AddUsage;
import com.example.billage.frontend.ui.quest.QuestFragment;
import com.yy.mobile.rollingtextview.RollingTextView;

import java.text.DecimalFormat;
import java.util.List;

public class QuestAdapter extends ArrayAdapter<QuestList> {

    private Activity mActivity;
    private Context context;
    private List mList;
    private ListView mListView;
    RollingTextView coin;
    private GetSetADUserInfo rewardRefresher;

    static class QuestViewHolder {
         TextView name;
         TextView description;
         TextView reward;
         ImageView complete;
         Button complete2;
         CardView cardView;
         ConstraintLayout quest_detail;

    }

    public QuestAdapter(Context context, List<QuestList> list, ListView listview, Activity activity, RollingTextView coin) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
        this.mListView = listview;
        this.mActivity = activity;
        this.coin = coin;
        this.rewardRefresher = new GetSetADUserInfo();
    }

    // ListView의  한 줄(row)이 렌더링(rendering)될 때 호출되는 메소드로 row를 위한 view를 리턴.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {
        View rowView = convertView;
        QuestAdapter.QuestViewHolder viewHolder;
        String Status;

        if (rowView == null) {

            // 레이아웃을 정의한 XML 파일(R.layout.list_item)을 읽어서 계층 구조의 뷰 객체(rowView)로 변환.
            // rowView 객체는 3개의 TextView로 구성.
            // rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parentViewGroup, false);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.quest_list, parentViewGroup, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰 찾기.
            viewHolder = new QuestAdapter.QuestViewHolder();
            viewHolder.name = rowView.findViewById(R.id.quest_name);
            viewHolder.description = rowView.findViewById(R.id.quset_description);
            viewHolder.reward = rowView.findViewById(R.id.quest_reward);
            viewHolder.complete = rowView.findViewById(R.id.quest_complete);
            viewHolder.complete2 = rowView.findViewById(R.id.quest_complete2);
            viewHolder.cardView = rowView.findViewById(R.id.quest_cardview);
            viewHolder.quest_detail = rowView.findViewById(R.id.quest_detail);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (QuestAdapter.QuestViewHolder) rowView.getTag();
        }

        QuestList quest = (QuestList) mList.get(position);

        viewHolder.name.setText(quest.getName());
        viewHolder.description.setText(quest.getDescription());
        viewHolder.reward.setText(quest.getReward());

        if(quest.getComplete().equals("0")){
            viewHolder.complete.setImageResource(R.drawable.ellipsis);
            viewHolder.complete2.setText("미달성");
        }
        else{
            if(rewardRefresher.getRewardInfo(quest.getType(),quest.getId())==0){
                viewHolder.complete.setImageResource(R.drawable.close_gift);
                viewHolder.complete2.setText("보상받기");
            }
            else{
                //viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#E3DADA"));
                viewHolder.complete.setImageResource(R.drawable.open_gift);
                viewHolder.complete2.setText("획득완료");
                viewHolder.complete2.setFocusable(false);
            }


        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( viewHolder.quest_detail.getVisibility()==View.GONE){

                    viewHolder.quest_detail.setVisibility(View.VISIBLE);
                    viewHolder.quest_detail.setAlpha(0.0f);
                    viewHolder.quest_detail.animate().alpha(1.0f).setDuration(300).setListener(null);

                }
                else if( viewHolder.quest_detail.getVisibility()==View.VISIBLE){

                    viewHolder.quest_detail.animate().alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            viewHolder.quest_detail.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        setItemClickEvent(rowView);
        setRewardClickEvent(viewHolder,quest);

        return rowView;
    }

    private void setItemClickEvent(View rowView) {
        rowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    private void setRewardClickEvent(QuestViewHolder viewHolder, QuestList quest) {
        viewHolder.complete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.complete.setImageResource(R.drawable.open_gift);
                viewHolder.complete2.setText("획득완료");
                viewHolder.complete2.setFocusable(false);

                if(rewardRefresher.getRewardInfo(quest.getType(),quest.getId())==0){
                    DecimalFormat number_format = new DecimalFormat("###,###");
                    rewardRefresher.setRewardInfo(quest.getType(),quest.getId());
                    GetSetDB getSetDB = new GetSetDB();
                    int resultCoin = getSetDB.getCoin()+Integer.parseInt(quest.getReward());
                    getSetDB.setCoin(resultCoin);
                    coin.setText(String.valueOf(number_format.format(resultCoin)));
                }

            }
        });
    }


}