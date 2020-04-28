package com.example.billage.ui.billage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class BillageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BillageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}