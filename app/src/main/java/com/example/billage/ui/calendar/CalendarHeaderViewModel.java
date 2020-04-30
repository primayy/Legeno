package com.example.billage.ui.calendar;

import com.example.billage.data.TSLiveData;

import androidx.lifecycle.ViewModel;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();

    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
}
