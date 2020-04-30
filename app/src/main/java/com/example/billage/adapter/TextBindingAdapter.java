package com.example.billage.adapter;

import android.widget.TextView;


import com.example.billage.utils.DateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.databinding.BindingAdapter;
public class TextBindingAdapter {

    @BindingAdapter({"setCalendarHeaderText"})
    public static void setCalendarHeaderText(TextView view, Long date) {
        try {
            if (date != null) {
                view.setText(DateFormat.getDate(date, DateFormat.CALENDAR_HEADER_FORMAT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"setDayText"})
    public static void setDayText(TextView view, Calendar calendar) {
        try {
            if (calendar != null) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                view.setText(DateFormat.getDate(gregorianCalendar.getTimeInMillis(), DateFormat.DAY_FORMAT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @BindingAdapter({"setDayEarnText"})
    public static void setDayEarnText(TextView view, String earn) {
        try {
            if (earn != null) {
                view.setText(earn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"setDayUsageText"})
    public static void setDayUsageText(TextView view, String usage) {
        try {
            if (usage != null) {
                view.setText(usage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
