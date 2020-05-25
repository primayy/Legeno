package com.example.billage.backend.api;

import android.os.Bundle;
import android.util.Log;

import com.example.billage.backend.common.ApiConst;
import com.example.billage.backend.common.Utils;


import java.util.HashMap;

public class Authorize {
    public static Bundle request_authorize(){
        String url ="https://testapi.openbanking.or.kr/oauth/2.0/authorize";
        //사용자 인증 후 발급 받은 토큰으로 변경해야함. 사용자별로 다름
        String state = Utils.numberGen(32);
        HashMap<String, String> paramMap = new HashMap<>();
        //요청시 필요한 parameters
        paramMap.put("response_type", "code");
        paramMap.put("client_id", ApiConst.CLIENT_ID);
        paramMap.put("redirect_uri", ApiConst.CALLBACK_URL);
        paramMap.put("scope", "login inquiry transfer");
        paramMap.put("client_info", "test"); //이용기관이 설정하는 사용자에 대한 임의의 정보
        paramMap.put("state", state); //32글자 난수 설정
        paramMap.put("auth_type", "0"); //최초인증 0, 재인증 2
        paramMap.put("lang", "kor");
        String parameters = "?"+Utils.convertMapToString(paramMap);
        Bundle args = new Bundle();

        args.putString("URL_TO_LOAD", url + parameters); // 호출 URL 전달
        args.putString("STATE", state);

        return args;
    }
}
