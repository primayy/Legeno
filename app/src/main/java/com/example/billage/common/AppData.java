package com.example.billage.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.billage.api.data.token.AccessToken;

public class AppData extends Application {

    public static Context appContext;
    public static AccessToken accessToken;
    public static DbOpenHelper mdb;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        mdb = new DbOpenHelper(appContext);
        mdb.open();
        mdb.create();
//        accessToken;

    }

    public static SharedPreferences getPref() {
        return appContext.getSharedPreferences(appContext.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }
}
