package com.example.billage.backend.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.billage.frontend.data.MonthUsageList;
import com.example.billage.frontend.data.UsageList;

import java.util.ArrayList;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "Billage.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private AppDataBaseManager mDBHelper;
    private Context mCtx;

    private class AppDataBaseManager extends SQLiteOpenHelper{

        public AppDataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(Databases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+Databases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new AppDataBaseManager(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    //transaction 데이터 삽입
    public long insertTransColumn(String date, String time, String name, String money,String inout){
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.NAME, name);
        values.put(Databases.CreateDB.TIME, time);
        values.put(Databases.CreateDB.DATE, date);
        values.put(Databases.CreateDB.MONEY, money);
        values.put(Databases.CreateDB.INOUT, inout);
        return mDB.insert(Databases.CreateDB._TABLENAME0, null, values);
    }

    //transaction 데이터 조회 후 UsageList로 반환
    public ArrayList<UsageList> getTransColumns(){
        ArrayList<UsageList> trans_data = new ArrayList<UsageList>();

        String query = "SELECT * from 'transaction' order by date desc";

        Cursor c = mDB.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
//                Log.d("trans_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2)+" "+c.getString(3)+" "+c.getString(4));
                trans_data.add(new UsageList(Utils.transformDate(c.getString(1)),c.getString(0),Utils.transformTime(c.getString(2)),c.getString(4),c.getString(3)));
            }while (c.moveToNext());
        }
        return trans_data;
    }

    //일별 수입,지출의 합을 리턴
    public ArrayList<UsageList> getTransDaysColumns(){
        ArrayList<UsageList> days_data = new ArrayList<UsageList>();

        String query = "SELECT date,inout,sum(money) from 'transaction' group by date, inout order by date asc";

        Cursor c = mDB.rawQuery(query,null);

        //idx 0: date, 1: inout, 2:sum
        if(c.moveToFirst()){
            do{
                Log.d("days_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2));
                days_data.add(new UsageList(Utils.transformDate(c.getString(0)),"","",c.getString(2),c.getString(1)));
            }while (c.moveToNext());
        }
        return days_data;
    }

    //이번 달을 제외한 이전 3개월의 평균 수입,지출값을 반환함
    //parameter: inout("입금","출금")
    public int getTransThreeMonthsAvg(String inout){
        int trans_avg=0;
        String today = Utils.getDay();
        String year = Utils.getYear() + "0000";
        int befor = ((Integer.parseInt(today) - Integer.parseInt(year))/100 - 3)*100 + 1;
        int cur = ((Integer.parseInt(today) - Integer.parseInt(year))/100)*100+1;
        String befor_month = Utils.getYear()+"0"+String.valueOf(befor);
        String cur_month = Utils.getYear()+"0"+String.valueOf(cur);

        String query = "SELECT avg(money) from 'transaction' where inout='"+ inout +"' and date >='" + befor_month + "' and date <'" + cur_month+"'";

        Cursor c = mDB.rawQuery(query,null);

        //idx 0: avg
        if(c.moveToFirst()){
            do{
                Log.d("avg_data",c.getString(0));
                trans_avg = (int) Double.parseDouble(c.getString(0));
            }while (c.moveToNext());
        }
        return trans_avg;
    }

    //달별 수입,지출에 대한 카운트,합계에 대한 리스트 반환
    //parameter: inout("입금","출금")
    public ArrayList<MonthUsageList> getTransMonthsColumns(String inout){
        ArrayList<MonthUsageList> months_data = new ArrayList<MonthUsageList>();

        String today = Utils.getDay();
        String year = Utils.getYear() + "0000";
        int befor = ((Integer.parseInt(today) - Integer.parseInt(year))/100 - 3)*100 + 1;
        String befor_month = Utils.getYear()+"0"+String.valueOf(befor);

        for (int i=0;i<6;i++){
            int cur = Integer.parseInt(befor_month)+i*100;
            int nxt = Integer.parseInt(befor_month)+(i+1)*100;
            months_data.add(new MonthUsageList(0,0,"",inout));

            String query = "SELECT date,sum(money),count() from 'transaction' where inout='" + inout +"' and date >='" + cur + "' and date <'"+ nxt +"' group by date<= '"+ nxt +"' order by date asc";
            Cursor c = mDB.rawQuery(query,null);

            //idx 0: date, 1: sum, 2: count
            MonthUsageList month_data = months_data.get(i);
            if(c.moveToFirst()){
                do{
                    month_data.setCount(c.getInt(2));
                    month_data.setCost(c.getString(1));

                    Log.d("month_data",c.getString(0)+ " " +c.getString(1) + " " + c.getCount());
                }while (c.moveToNext());
            }
        }

        //log용
        for(int i=0;i<months_data.size();i++){
            Log.d("months_data",i+" "+String.valueOf(months_data.get(i).getCount())+" "+String.valueOf(months_data.get(i).getCost()));
        }

        return months_data;
    }


}
