package com.srmarlins.thingstodo.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.srmarlins.eventful_android.data.Event;

import java.util.TimeZone;

/**
 * Created by jfowler on 9/15/15.
 */
public class CalendarManager {

    public static final int EVENT_INSERT_TOKEN = 999;

    private ContentResolver mContentResolver;

    public CalendarManager(Context context){
        mContentResolver = context.getContentResolver();
    }

    public void insertEvent(Event event, int calendarId){
        AsyncCalendarQuery async = new AsyncCalendarQuery(mContentResolver);
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, event.getStartTime().getTime());
        values.put(CalendarContract.Events.DTEND, event.getStopTime().getTime());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        async.startInsert(EVENT_INSERT_TOKEN, null, CalendarContract.Events.CONTENT_URI, values);
    }

    public int[] getCalendarIds() {
        String projection[] = {"_id", "calendar_displayName"};
        Uri calendars;
        calendars = Uri.parse("content://com.android.calendar/calendars");

        Cursor managedCursor = mContentResolver.query(calendars, projection, null, null, null);
        int[] ids = new int[managedCursor.getCount()];
        if (managedCursor.moveToFirst()){
            String calID;
            int cont= 0;
            int idCol = managedCursor.getColumnIndex(projection[0]);
            do {
                calID = managedCursor.getString(idCol);
                ids[cont] = Integer.parseInt(calID.trim());
                cont++;
            } while(managedCursor.moveToNext());
            managedCursor.close();
        }
        return ids;
    }
}
