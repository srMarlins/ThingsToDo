package com.srmarlins.thingstodo.Utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.srmarlins.eventful_android.data.Calendar;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Models.EventCalendar;
import com.srmarlins.thingstodo.SQLite.QueryCompletionListener;

import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by jfowler on 9/15/15.
 */
public class CalendarManager {

    public static final int EVENT_INSERT_TOKEN = 999;
    public static final int EVENT_QUERY_TOKEN = 1234;
    public static final String BASE_CALENDAR_URI = "content://com.android.calendar";
    public static final String CALENDAR_URI = BASE_CALENDAR_URI + "/calendars";
    public static final String EVENT_URI = BASE_CALENDAR_URI + "/events";

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
        String projection[] = {"_id", "calendar_displayName", "calendar_color"};
        Uri calendars;
        calendars = Uri.parse(CALENDAR_URI);

        Cursor managedCursor = mContentResolver.query(calendars, projection, null, null, null);
        EventCalendar[] cals = new EventCalendar[managedCursor.getCount()];
        if (managedCursor.moveToFirst()){
            long calID;
            String calName;
            int calColor;

            int cont= 0;
            int idCol = managedCursor.getColumnIndex(projection[0]);
            int nameCol = managedCursor.getColumnIndex(projection[1]);
            int colorCol = managedCursor.getColumnIndex(projection[2]);
            do {
                calID = managedCursor.getLong(idCol);
                calName = managedCursor.getString(nameCol);
                calColor = managedCursor.getInt(colorCol);

                cals[cont] = new EventCalendar(calID, calName, calColor);
                cont++;
            } while(managedCursor.moveToNext());
            managedCursor.close();
        }
        return cals;
    }

    public void getEventsFromCalendar(EventCalendar calendar, String[] fields, QueryCompletionListener listener){
        AsyncCalendarQuery asyncCalendarQuery = new AsyncCalendarQuery(mContentResolver);
        long calendarId = calendar.getId();
        Uri calendarUri = Uri.parse(EVENT_URI);

        asyncCalendarQuery.startQuery(EVENT_QUERY_TOKEN, null, calendarUri, fields, "calendar_id=" + calendarId, null, null, listener);
    }

    public Event[] parseEventResultCursor(Cursor cursor){
        if(cursor == null || !cursor.moveToFirst()){
            return null;
        }

        int cursorCount = cursor.getCount();
        Event[] events = new Event[cursorCount];
        int eventCount = 0;
        do{
            events[eventCount] = new Event();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String data = cursor.getString(i);

                if(data != null && !"".equals(data)) {
                    switch (columnName) {
                        case CalendarContract.Events.DESCRIPTION:
                            events[eventCount].setDescription(data);
                            break;
                        case CalendarContract.Events.TITLE:
                            events[eventCount].setTitle(data);
                            break;
                        case CalendarContract.Events.DTSTART:
                            events[eventCount].setStartTime(new Date(Long.parseLong(data)));
                            break;
                        case CalendarContract.Events.DTEND:
                            events[eventCount].setStopTime(new Date(Long.parseLong(data)));
                            break;
                    }
                }
            }
            eventCount++;
        }while(cursor.moveToNext());

        return events;
    }
}
