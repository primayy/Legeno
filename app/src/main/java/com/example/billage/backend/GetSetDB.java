package com.example.billage.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class GetSetDB {
    public String setCoin(int coin){
        try {
            JSONObject postdata = new JSONObject();
            postdata.accumulate("coin", Integer.toString(coin));
            JSONTask_Post jstask = new JSONTask_Post(postdata);
            GetSetADUserInfo getUserInfo=new GetSetADUserInfo();
            return jstask.execute("http://18.219.106.101/Update/Coin/"+getUserInfo.getUserInfo("user_id")).get();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }


    public int getCoin(){
        try{
            JSONTask_Get jstask=new JSONTask_Get();
            GetSetADUserInfo getUserInfo=new GetSetADUserInfo();
            return Integer.parseInt(jstask.execute("http://18.219.106.101/Read/Coin/"+getUserInfo.getUserInfo("user_id")).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
