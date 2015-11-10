package com.srmarlins.thingstodo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Models.Query;
import com.srmarlins.thingstodo.SQLite.EventContract;
import com.srmarlins.thingstodo.SQLite.EventDbHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by jfowler on 11/2/15.
 */
public class EventContractManager {

    private static EventDbHelper mEventDb;

    public EventContractManager(Context context){
        mEventDb = new EventDbHelper(context);
    }

    public void insertEvent(Event event, long calendarId){
        final SQLiteDatabase eventDb = mEventDb.getWritableDatabase();
        AsyncTask<Query, Void, Void> async = new AsyncTask<Query, Void, Void>() {
            @Override
            protected Void doInBackground(Query... params) {
                for(Query query : params){
                    long code = eventDb.insert(query.table, null, query.values);
                }
                return null;
            }
        };
        Query query = new Query();
        ContentValues values = new ContentValues();

        Date start = event.getStartTime();
        Date end = event.getStopTime();

        if(start == null){
            return;
        }

        values.put(EventContract.EventEntry.START_DATE, event.getStartTime().getTime());
        values.put(EventContract.EventEntry.END_DATE, (end != null) ? end.getTime() : start.getTime());
        values.put(EventContract.EventEntry.TITLE, event.getTitle());
        values.put(EventContract.EventEntry.DESCRIPTION, event.getDescription());
        values.put(EventContract.EventEntry.CALENDAR_ID, calendarId);
        values.put(EventContract.EventEntry.EVENT_TIME_ZONE, TimeZone.getDefault().getID());
        values.put(EventContract.EventEntry.EVENT_ID, event.getSeid());
        values.put(EventContract.EventEntry.EVENT_IMAGE, event.getImages().get(event.getImages().size() - 1).getUrl());
        values.put(EventContract.EventEntry.URL, event.getURL());
        values.put(EventContract.EventEntry.LOCATION, event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());

        query.values = values;
        query.table = EventContract.EventEntry.TABLE_NAME;

        async.execute(query);
    }

    public void deleteEvent(Event event){
        SQLiteDatabase eventDb = mEventDb.getWritableDatabase();
        eventDb.delete(EventContract.EventEntry.TABLE_NAME, EventContract.EventEntry.EVENT_ID + "=" + event.getSeid(), null);
    }

    public Event retrieveEvent(String eventId){
        SQLiteDatabase eventDb = mEventDb.getWritableDatabase();
        String query = "SELECT  * FROM " + EventContract.EventEntry.TABLE_NAME + " WHERE "
                + EventContract.EventEntry.EVENT_ID + " = " + eventId;

        Cursor cursor = eventDb.rawQuery(query, null);

        return cursorToEvents(cursor).get(eventId);
    }

    public HashMap<String, Event> retrieveAllEvents(){
        SQLiteDatabase eventDb = mEventDb.getWritableDatabase();
        String query = "SELECT * FROM " + EventContract.EventEntry.TABLE_NAME;

        Cursor cursor = eventDb.rawQuery(query, null);

        return cursorToEvents(cursor);
    }

    public static HashMap<String, Event> cursorToEvents(Cursor cursor){
        HashMap<String, Event> map = new HashMap<>();

        if(!cursor.moveToFirst()){
            return map;
        }

        do{
            Event event = new Event();
            event.setSeid(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.EVENT_ID)));
            event.setStartTime(new Date(cursor.getLong(cursor.getColumnIndex(EventContract.EventEntry.START_DATE))));
            event.setStopTime(new Date(cursor.getLong(cursor.getColumnIndex(EventContract.EventEntry.END_DATE))));
            event.setTitle(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.TITLE)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.DESCRIPTION)));
            map.put(event.getSeid(), event);
        }while(cursor.moveToNext());

        return map;
    }
}
