package com.example.billage.backend.api.data;

import android.os.Bundle;
import android.util.Log;

import com.example.billage.backend.common.ApiConst;
import com.example.billage.backend.common.AppData;
import com.example.billage.backend.common.Utils;
import com.example.billage.backend.http.ApiCallAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorize {
    public static Bundle request_authorize(){
        String url ="https://testapi.openbanking.or.kr/oauth/2.0/authorize";
        //사용자 인증 후 발급 받은 토큰으로 변경해야함. 사용자별로 다름

        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("response_type", "code");
        paramMap.put("client_id", ApiConst.CLIENT_ID);
        paramMap.put("redirect_uri", ApiConst.CLIENT_SECRET);
        paramMap.put("scope", "login inquiry transfer");
        paramMap.put("client_info", "test"); //이용기관이 설정하는 사용자에 대한 임의의 정보
        paramMap.put("state", "11111111222222223333333344444444"); //32글자 난수 설정
        paramMap.put("auth_type", "0"); //최초인증 0, 재인증 2
        paramMap.put("lang", "kor");
        String parameters = "?"+Utils.convertMapToString(paramMap);
        Bundle args = new Bundle();

        args.putString("URL_TO_LOAD", url + parameters); // 호출 URL 전달
        args.putString("STATE", "11111111222222223333333344444444");

        return args;
    }
}
