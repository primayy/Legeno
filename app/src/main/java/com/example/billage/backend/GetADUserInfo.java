package com.example.billage.backend;

import com.example.billage.backend.common.AppData;

import org.json.JSONArray;
import org.json.JSONException;

public class GetADUserInfo {

    public String getUserInfo(String key) throws JSONException {
        JSONArray jarray=new JSONArray(AppData.getPref().getString("user_info",null));

        return jarray.getJSONObject(0).getString(key);
    }
}
