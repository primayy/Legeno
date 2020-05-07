package com.example.billage.ui.calendar;

import com.example.billage.data.TSLiveData;

import java.util.Calendar;

import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {
    public TSLiveData<Calendar> mCalendar = new TSLiveData<>();
    public TSLiveData mCalendarEarn = new TSLiveData<>();
    public TSLiveData mCalendarUsage = new TSLiveData<>();

    public void setCalendar(Calendar calendar) {
        this.mCalendar.setValue(calendar);
    }
    public void setCalendarEarn(String earn) {
        this.mCalendarEarn.setValue(earn);
    }
    public void setCalendarUsage(String usage) {
        this.mCalendarUsage.setValue(usage);
    }


}
