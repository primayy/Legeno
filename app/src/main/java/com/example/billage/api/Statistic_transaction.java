package com.example.billage.api;

import android.database.Cursor;
import android.util.Log;

import com.example.billage.common.DbOpenHelper;
import com.example.billage.common.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Statistic_transaction {

    //오늘 날짜를 기준으로 일주일(월~금)의 수입,지출 통계 반환
    //parameter: "입금", "츨금"

    public static ArrayList<Integer> daily_statistic(String inout) throws ParseException {
        Integer [] int_arr = new Integer[7];
        Arrays.fill(int_arr,0);

        ArrayList<Integer> daily_data = new ArrayList<Integer>(Arrays.asList(int_arr));
        String today = Utils.getDay();

        String monday = String.valueOf(Integer.parseInt(today) - (Utils.getDayOfWeek() - 2));

        String query = "SELECT date,sum(money) from 'transaction' where inout='" + inout
                +"' and date>=" + monday +" group by date order by date asc";

        Cursor c = DbOpenHelper.mDB.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                int idx = Integer.parseInt(c.getString(0)) - Integer.parseInt(monday);
                daily_data.set(idx,Integer.parseInt(c.getString(1)));
            }while (c.moveToNext());
        }

        //log찍기 위함
        for(int i=0;i<7;i++){
            Log.d("daily_data",i+" "+String.valueOf(daily_data.get(i)));
        }

        return daily_data;
    }

    //1년 수입, 지출 통계 반환
    public static ArrayList<Integer> monthly_statistic(String inout){
        Integer [] int_arr = new Integer[12];
        Arrays.fill(int_arr,0);

        ArrayList<Integer> monthly_data = new ArrayList<Integer>(Arrays.asList(int_arr));

        String year_start = Utils.getYear()+"0101";

        String query = "SELECT date,sum(money) from 'transaction' where inout='" + inout
                +"' and date>=" + year_start +" group by date order by date asc";

        Cursor c = DbOpenHelper.mDB.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                int idx = (Integer.parseInt(c.getString(0)) - Integer.parseInt(year_start))/100;
                monthly_data.set(idx, monthly_data.get(idx) + Integer.parseInt(c.getString(1)));
            }while (c.moveToNext());
        }

        //log찍기 위함
        for(int i=0;i<12;i++){
            Log.d("monthly_data",i+" "+String.valueOf(monthly_data.get(i)));
        }
        return monthly_data;
    }
}
