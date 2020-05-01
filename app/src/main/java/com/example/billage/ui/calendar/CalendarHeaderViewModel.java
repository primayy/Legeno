package com.example.billage.ui.calendar;

import com.example.billage.data.TSLiveData;

import androidx.lifecycle.ViewModel;

public class CalendarHeaderViewModel extends ViewModel {
    public TSLiveData<Long> mHeaderDate = new TSLiveData<>();
    public TSLiveData mHeaderEarn = new TSLiveData<>();
    public TSLiveData mHeaderUsage = new TSLiveData<>();
    public TSLiveData mHeaderCount = new TSLiveData<>();


    public void setHeaderDate(long headerDate) {
        this.mHeaderDate.setValue(headerDate);
    }
    public void setHeaderEarn(String headerDate) {
        this.mHeaderEarn.setValue(headerDate);
    }
    public void setHeaderUsage(String mHeaderUsage) {
        this.mHeaderUsage.setValue(mHeaderUsage);
    }
    public void setHeaderCount(String mHeaderCount) {
        this.mHeaderCount.setValue(mHeaderCount);
    }
}
