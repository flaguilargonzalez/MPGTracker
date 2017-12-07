package net.androidbootcamp.mpgtracker;

import android.provider.BaseColumns;

public final class SQLDB {

    private SQLDB(){}

    public static class DBTable implements BaseColumns
    {
        public static final String DATABASE_NAME = "MPGHist.db";
        public static final int DATABASE_VERSION = 1;
        public static final String MPGHist_TABLE_NAME = "MPGHist";
        public static final String MPGHist_COLUMN_DATE = "Date";
        public static final String MPGHist_COLUMN_PRICE = "Price";
        public static final String MPGHist_COLUMN_GALLONS = "NumGallons";
        public static final String MPGHist_COLUMN_MILES = "Miles";
        public static final String MPGHist_COLUMN_MPG = "MPG";
        public static final String[] Columns = {MPGHist_COLUMN_DATE,MPGHist_COLUMN_PRICE,
                MPGHist_COLUMN_GALLONS,MPGHist_COLUMN_MILES,MPGHist_COLUMN_MPG};
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBTable.MPGHist_TABLE_NAME + " (" +
                    DBTable._ID + " INTEGER PRIMARY KEY," +
                    DBTable.MPGHist_COLUMN_DATE + " TEXT," +
                    DBTable.MPGHist_COLUMN_PRICE + " REAL," +
                    DBTable.MPGHist_COLUMN_GALLONS + " REAL," +
                    DBTable.MPGHist_COLUMN_MILES + " INTEGER," +
                    DBTable.MPGHist_COLUMN_MPG + " REAL" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBTable.MPGHist_TABLE_NAME;

}
