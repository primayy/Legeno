package com.example.billage.frontend.ui.addUsage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import com.example.billage.R;
import com.example.billage.backend.common.AppData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private String hour_string;
    private String minute_string;
    private String month_string;
    private String day_string;

    private String type;
    private String cost;
    private String date;
    private String time;
    private String dest;
    private String memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);

        if(!isDetailPage()){
            date_picker = findViewById(R.id.date_input);
            time_picker = findViewById(R.id.time_input);
            set_custom_actionbar_addpage();
            set_cancel_event();
            set_save_event();
            getDateToday();
            set_date_event();
            set_time_event();

        }


    }

    private boolean isDetailPage() {
        try{
            // 사용자가 직접 추가한 부분인지 확인한번 해야함 추가예정
            Intent intent = getIntent();

            String type_info = intent.getExtras().getString("cardTypeInfo");
            String cost_info = intent.getExtras().getString("cardCostInfo");
            String date_info = intent.getExtras().getString("cardDateInfo");
            String time_info = intent.getExtras().getString("cardTimeInfo");
            String dest_info = intent.getExtras().getString("cardDestInfo");
            String memo_info = intent.getExtras().getString("cardMemoInfo");
            String trans_type_info = intent.getExtras().getString("cardTransTypeInfo");

            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            EditText cost_input = findViewById(R.id.cost_input);
            EditText date_input = findViewById(R.id.date_input);
            EditText time_input = findViewById(R.id.time_input);
            EditText dest_input = findViewById(R.id.dest_input);
            EditText memo_input = findViewById(R.id.memo_input);
            cost_input.setText(cost_info);
            date_input.setText(date_info);
            time_input.setText(time_info);
            dest_input.setText(dest_info);
            memo_input.setText(memo_info);

            set_custom_actionbar_detailpage();
            set_cancel_event();
            set_save_event();
            if(trans_type_info.equals("user")) set_delete_event();

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setClickable(false);
            }
            cost_input.setFocusable(false);
            date_input.setFocusable(false);
            time_input.setFocusable(false);
            dest_input.setFocusable(false);

            if(type_info.equals("입금")){
                radioGroup.check(R.id.income_checkBox);
            }else{
                radioGroup.check(R.id.usage_checkBox);
            }

            return true;


        }catch (NullPointerException ignored){}

        return false;
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

            if (hourOfDay >= 10) {
                hour_string = String.valueOf(new_hour);
            } else {
                hour_string = "0"+String.valueOf(new_hour);
            }

            if (minute >= 10) {
                minute_string = String.valueOf(new_minute);
            } else {
                minute_string = "0"+String.valueOf(new_minute);
            }

            updateEditTime();
        }
    };

    private void updateEditTime(){
        StringBuffer stringBuffer = new StringBuffer();
        time_picker.setText(stringBuffer.append(hour_string).append(":").append(minute_string));
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        date_picker.setText(date.format(currentDate));
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){


        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            new_year = year;
            new_month = month;
            new_day = day;

            if (new_month >= 10) {
                month_string = String.valueOf(new_month+1);
            } else {
                month_string = "0"+String.valueOf(new_month+1);
            }

            if (new_day >= 10) {
                day_string = String.valueOf(new_day);
            } else {
                day_string = "0"+String.valueOf(new_day);
            }

            updateEditDate();
        }
    };

    private void updateEditDate(){
        StringBuffer stringBuffer = new StringBuffer();
        date_picker.setText(stringBuffer.append(new_year).append("-").append(month_string).append("-").append(day_string));
    }

    private void set_custom_actionbar_addpage() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_addpage);
    }
    private void set_custom_actionbar_detailpage() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_detailpage);
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
                try{
                    RadioGroup radioGroup = findViewById(R.id.radioGroup);
                    EditText cost_input = findViewById(R.id.cost_input);
                    EditText date_input = findViewById(R.id.date_input);
                    EditText time_input = findViewById(R.id.time_input);
                    EditText dest_input = findViewById(R.id.dest_input);
                    EditText memo_input = findViewById(R.id.memo_input);
                    RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    String date = "";
                    String time = "";
                    String[] date_arr = date_input.getText().toString().split("-");
                    String[] time_arr = time_input.getText().toString().split(":");

                    for(int i=0; i<date_arr.length;i++){
                        date += date_arr[i];
                    }
                    for(int i=0; i<time_arr.length;i++){
                        time += time_arr[i];
                    }
                    time+="00";
//                    Log.d("user_trans",cost_input.getText().toString()+" "+date+" "+time+" "+dest_input.getText().toString()+" "+memo_input.getText().toString()+" "+rb.getText().toString()+" ");
                    AppData.mdb.insertTransColumn(date,time,dest_input.getText().toString(),cost_input.getText().toString(),rb.getText().toString(),memo_input.getText().toString(),"","user");
                }catch(Exception e){
                    Log.d("user_trans_err", String.valueOf(e));
                }
                Toast save_text = Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT);
                save_text.show();
                finish();
            }
        });
    }

    private void set_delete_event() {
        Button delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setVisibility(View.VISIBLE);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //To-do
            }
        });
    }
}


