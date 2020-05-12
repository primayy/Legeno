package com.example.billage.frontend.ui.addUsage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import com.example.billage.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddUsage extends AppCompatActivity {

    private Date currentDate;
    private int year, month, day;
    private int new_year, new_month, new_day;
    private int hour, minute;
    private int new_hour, new_minute;
    private EditText date_picker, time_picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);

        date_picker = findViewById(R.id.date_input);
        time_picker = findViewById(R.id.time_input);

        set_custom_actionbar();
        set_cancel_event();
        set_save_event();

        getDateToday();
        set_date_event();

        set_time_event();

    }

    private void set_time_event() {
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddUsage.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,timeListener,0,0,false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            new_hour = hourOfDay;
            new_minute = minute;

            updateEditTime();
        }
    };

    private void updateEditTime(){
        StringBuffer stringBuffer = new StringBuffer();
        time_picker.setText(stringBuffer.append(new_hour).append("시 ").append(new_minute).append("분"));
    }


    private void set_date_event() {
        date_picker.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Calendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                Dialog dialog = new DatePickerDialog(AddUsage.this, dateSetListener,year,month,day);
                dialog.show();
            }
        });
    }

    private void getDateToday(){

        currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");

        date_picker.setText(date.format(currentDate));
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){


        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            new_year = year;
            new_month = month;
            new_day = day;

            updateEditDate();
        }
    };

    private void updateEditDate(){
        StringBuffer stringBuffer = new StringBuffer();
        date_picker.setText(stringBuffer.append(new_year).append("년 ").append(new_month+1).append("월 ").append(new_day).append("일"));
    }

    private void set_custom_actionbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
    }
    private void set_cancel_event(){
        Button cancel_btn = findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void set_save_event() {
        Button save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // To-do

            }
        });
    }
}


