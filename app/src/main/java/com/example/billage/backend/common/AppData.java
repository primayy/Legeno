package com.example.billage.backend.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppData extends Application {

    public static Context appContext;
    public static DbOpenHelper mdb;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        mdb = new DbOpenHelper(appContext);
        mdb.open();
        mdb.create();

    }

    public static SharedPreferences getPref() {
        return appContext.getSharedPreferences(appContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }
}
