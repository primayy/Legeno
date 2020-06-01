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

import org.jetbrains.annotations.NotNull;

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
    public void insertTransColumn(String date, String time, String name, String money,String inout,String bank_code,String type){
        ContentValues values = getTransTable(date, time, name, money, inout, bank_code, type);
        String where_str = "name='"+name+"' and date='"+date+"' and time='"+ time +"' and money='"+money+"' and inout='"+inout+"' and type='"+type+"' and bank_code='"+bank_code+"'";

        checkTransInsertOrUpdate(values, where_str);
    }

    //transaction 데이터 삽입, memo포함
    public void insertTransColumn(String date, String time, String name, String money,String inout,String memo,String bank_code,String type){
        ContentValues values = getTransTable(date, time, name, money, inout, bank_code, type);
        values.put(Databases.CreateDB.MEMO, memo);

        String where_str = "name='"+name+"' and date='"+date+"' and time='"+ time +"' and money='"+money+"' and inout='"+inout+"' and type='"+type+"' and bank_code='"+bank_code+"'";

        checkTransInsertOrUpdate(values, where_str);
    }

    //transaciont tale에 insert할지 update할지 결정
    private void checkTransInsertOrUpdate(ContentValues values, String where_str) {
        Cursor c = mDB.rawQuery("select * from 'transaction' where " + where_str, null);
        System.out.println(c.getCount());
        if (c.getCount() == 0) {
            mDB.insert(Databases.CreateDB._TABLENAME0, null, values);
        } else {
            mDB.update(Databases.CreateDB._TABLENAME0, values, where_str, null);
        }
    }

    //transaction table 구조에 맞춰 값 입력
    @NotNull
    private ContentValues getTransTable(String date, String time, String name, String money, String inout, String bank_code, String type) {
        ContentValues values = new ContentValues();
        values.put(Databases.CreateDB.NAME, name);
        values.put(Databases.CreateDB.TIME, time);
        values.put(Databases.CreateDB.DATE, date);
        values.put(Databases.CreateDB.MONEY, money);
        values.put(Databases.CreateDB.INOUT, inout);
        values.put(Databases.CreateDB.TYPE, type);
        values.put(Databases.CreateDB.BANK_CODE, bank_code);
        return values;
    }

    //transaction 데이터 조회 후 UsageList로 반환
    public ArrayList<UsageList> getTransColumns(){
        ArrayList<UsageList> trans_data = new ArrayList<UsageList>();

        String query = "SELECT * from 'transaction' order by date desc";

        Cursor c = mDB.rawQuery(query,null);
        //분당정자점 20190101 010101 입금 1000000 097 "" api 170
        if(c.moveToFirst()){
            do{
                Log.d("trans_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+c.getString(5)+" "+c.getString(6)+" "+c.getString(7) + " "+c.getString(8));
                trans_data.add(new UsageList(Utils.transformDate(c.getString(1)),c.getString(0),Utils.transformTime(c.getString(2)),c.getString(4),c.getString(3),c.getString(5),c.getString(6),c.getInt(8),c.getString(7)));
            }while (c.moveToNext());
        }
        return trans_data;
    }

    //일별 수입,지출의 합을 리턴
    public ArrayList<UsageList> getTransDaysColumns(){
        ArrayList<UsageList> days_data = new ArrayList<UsageList>();

        String query = "SELECT date,inout,sum(money),id,type from 'transaction' group by date, inout order by date asc";

        Cursor c = mDB.rawQuery(query,null);

        //idx 0: date, 1: inout, 2:sum, 3:id, 4:type
        if(c.moveToFirst()){
            do{
                Log.d("days_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2));
                days_data.add(new UsageList(Utils.transformDate(c.getString(0)),"","",c.getString(2),c.getString(1),"","",c.getInt(3),c.getString(4)));
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

        String query = "SELECT sum(money) from 'transaction' where inout='"+ inout +"'and type= 'api'" + " and date >='" + befor_month + "' and date <'" + cur_month+"'";

        Cursor c = mDB.rawQuery(query,null);

        //idx 0: avg
        Log.d("avg_test", String.valueOf(c.getCount()));
        if(c.moveToFirst()){
            do{
                try{
                    trans_avg = (int) Double.parseDouble(c.getString(0))/90;
                } catch(Exception e){
                    Log.d("trans_avg_err", String.valueOf(e));
                    trans_avg = 0;
                }
                Log.d("avg_data", String.valueOf(trans_avg));
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

    public void delUserTrans(Integer id){
        String query = "DELETE from 'transaction' where id='"+id+"'";
        mDB.execSQL(query);
    }

    public ArrayList<UsageList> getAbnormalTrans(){
        int avg_money = getTransThreeMonthsAvg("출금");
        ArrayList<UsageList> abnormal_data = new ArrayList<UsageList>();

        String query = "SELECT date,time,inout,money,id,type,name,bank_code from 'transaction' where money >= '" + avg_money + "' and type ='api' and inout='출금' group by date, inout order by date asc";

        Cursor c = mDB.rawQuery(query,null);

        //idx 0: date, 1: time 2: inout, 3:sum, 4:id, 5:type , 6:name, 7:bank_code
        if(c.moveToFirst()){
            do{
                Log.d("abnormal_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2)+" "+  c.getString(3)+" "+  c.getString(4)+" "+  c.getString(5));
                abnormal_data.add(new UsageList(Utils.transformDate(c.getString(0)),c.getString(6),c.getString(1),c.getString(3),c.getString(2),c.getString(7),"",c.getInt(4),c.getString(5)));
            }while (c.moveToNext());
        }
        return abnormal_data;
    }

    public int getAbnormalTransSum(ArrayList<UsageList> trans){
        int sum =0;
        for(int i=0; i<trans.size();i++){
            sum += Integer.parseInt(trans.get(i).getCost());
        }
        return sum;
    }
}
