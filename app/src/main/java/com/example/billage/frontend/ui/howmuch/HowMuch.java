package com.example.billage.frontend.ui.howmuch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.billage.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class HowMuch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_much);

        IndicatorSeekBar seekBar = findViewById(R.id.seek_bar);


        TextView header = findViewById(R.id.how_much_header);

        seekBar.setIndicatorTextFormat("${TICK_TEXT}개월");


        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                header.setText(seekBar.getProgress()+"개월간 총 지출은 200000원 입니다.");
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

    }
}
