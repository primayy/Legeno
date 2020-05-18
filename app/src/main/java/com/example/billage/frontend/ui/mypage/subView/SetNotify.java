package com.example.billage.frontend.ui.mypage.subView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.billage.R;
public class SetNotify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_set_notify);

        set_switch_event();
    }

    private void set_switch_event() {
        Switch quest_notify = findViewById(R.id.quest_notify);
        Switch like_notify = findViewById(R.id.like_notify);
        Switch comment_notify = findViewById(R.id.comment_notify);

        quest_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    // on 되었을 때
                    Log.d("test","chedked");
                }else{
                    // off 되었을 때
                    Log.d("test","unchedked");
                }
            }
        });
        like_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    // on 되었을 때
                    Log.d("test","chedked");
                }else{
                    // off 되었을 때
                    Log.d("test","unchedked");
                }
            }
        });
        comment_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    // on 되었을 때
                    Log.d("test","chedked");
                }else{
                    // off 되었을 때
                    Log.d("test","unchedked");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        super.onBackPressed();
    }
}
