package com.example.billage.common;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Databases {
    public static SQLiteDatabase mDB;

    public static final class CreateDB implements BaseColumns {
        public static final String NAME = "name";
        public static final String DATE = "date";
        public static final String MONEY = "money";
        public static final String INOUT = "inout";
        public static final String _TABLENAME0 = "'transaction'";
        public static final String _CREATE0 = "CREATE TABLE IF NOT EXISTS "+_TABLENAME0+" (" +
                NAME         + " TEXT NOT NULL," +
                DATE         + " TEXT NOT NULL," +
                INOUT         + " TEXT NOT NULL," +
                MONEY        + " TEXT NOT NULL" + ")" ;
    }
}
