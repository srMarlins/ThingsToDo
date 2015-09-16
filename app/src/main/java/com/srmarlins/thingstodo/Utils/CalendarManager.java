package com.srmarlins.thingstodo.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Models.EventCalendar;

import java.util.Date;
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

    public void insertEvent(Event event, long calendarId){
        AsyncCalendarQuery async = new AsyncCalendarQuery(mContentResolver);
        ContentValues values = new ContentValues();

        Date start = event.getStartTime();
        Date end = event.getStopTime();

        if(start == null){
            return;
        }

        values.put(CalendarContract.Events.DTSTART, event.getStartTime().getTime());
        values.put(CalendarContract.Events.DTEND, (end != null) ? end.getTime() : start.getTime());
        values.put(CalendarContract.Events.TITLE, event.getTitle());
        values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        async.startInsert(EVENT_INSERT_TOKEN, null, CalendarContract.Events.CONTENT_URI, values);
    }

    public EventCalendar[] getCalendars() {
        String projection[] = {"_id", "calendar_displayName"};
        Uri calendars;
        calendars = Uri.parse("content://com.android.calendar/calendars");

        Cursor managedCursor = mContentResolver.query(calendars, projection, null, null, null);
        EventCalendar[] cals = new EventCalendar[managedCursor.getCount()];
        if (managedCursor.moveToFirst()){
            long calID;
            String calName;
            int cont= 0;
            int idCol = managedCursor.getColumnIndex(projection[0]);
            int nameCol = managedCursor.getColumnIndex(projection[1]);
            do {
                calID = managedCursor.getLong(idCol);
                calName = managedCursor.getString(nameCol);
                cals[cont] = new EventCalendar(calID, calName);
                cont++;
            } while(managedCursor.moveToNext());
            managedCursor.close();
        }
        return cals;
    }
}
