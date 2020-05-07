package com.example.billage.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.billage.UsageList;

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

        String query = "SELECT * from 'transaction' order by date asc";

        Cursor c = mDB.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
//                Log.d("trans_data",c.getString(0)+ " " +c.getString(1)+" "+  c.getString(2)+" "+c.getString(3)+" "+c.getString(4));
                trans_data.add(new UsageList(Utils.transformDate(c.getString(1)),c.getString(0),Utils.transformTime(c.getString(2)),c.getString(4),c.getString(3)));
            }while (c.moveToNext());
        }

        return trans_data;
    }
}
