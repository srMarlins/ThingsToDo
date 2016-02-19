package com.srmarlins.thingstodo.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "eventManager";
    private static final String SQL_CREATE_ACCEPTED =
            "CREATE TABLE IF NOT EXISTS " + EventContract.EventEntry.TABLE_NAME_ACCEPTED + " (" +
                    EventContract.EventEntry.EVENT_ID + " TEXT PRIMARY KEY," +
                    EventContract.EventEntry.CALENDAR_ID + " INTEGER," +
                    EventContract.EventEntry.DESCRIPTION + " TEXT," +
                    EventContract.EventEntry.EVENT_IMAGE + " TEXT," +
                    EventContract.EventEntry.LOCATION + " TEXT," +
                    EventContract.EventEntry.START_DATE + " TEXT," +
                    EventContract.EventEntry.END_DATE + " TEXT," +
                    EventContract.EventEntry.EVENT_TIME_ZONE + " TEXT," +
                    EventContract.EventEntry.TITLE + " TEXT," +
                    EventContract.EventEntry.URL + " TEXT)";
    private static final String SQL_CREATE_DECLINED =
            "CREATE TABLE IF NOT EXISTS " + EventContract.EventEntry.TABLE_NAME_DECLINED + " (" +
                    EventContract.EventEntry.EVENT_ID + " TEXT PRIMARY KEY," +
                    EventContract.EventEntry.CALENDAR_ID + " INTEGER," +
                    EventContract.EventEntry.DESCRIPTION + " TEXT," +
                    EventContract.EventEntry.EVENT_IMAGE + " TEXT," +
                    EventContract.EventEntry.LOCATION + " TEXT," +
                    EventContract.EventEntry.START_DATE + " TEXT," +
                    EventContract.EventEntry.END_DATE + " TEXT," +
                    EventContract.EventEntry.EVENT_TIME_ZONE + " TEXT," +
                    EventContract.EventEntry.TITLE + " TEXT," +
                    EventContract.EventEntry.URL + " TEXT)";

    private static final String SQL_DELETE_DECLINED =
            "DROP TABLE IF EXISTS " + EventContract.EventEntry.TABLE_NAME_DECLINED;
    private static final String SQL_DELETE_ACCEPTED =
            "DROP TABLE IF EXISTS " + EventContract.EventEntry.TABLE_NAME_ACCEPTED;

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCEPTED);
        db.execSQL(SQL_CREATE_DECLINED);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCEPTED);
        db.execSQL(SQL_DELETE_DECLINED);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}