package com.srmarlins.thingstodo.SQLite;

import android.provider.BaseColumns;

/**
 * Created by jfowler on 10/14/15.
 */
public final class EventContract {
    public EventContract() {
    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Declined_Events";
        public static final String EVENT_ID = "Event_Id";
        public static final String TITLE = "Title";
        public static final String DESCRIPTION = "Subtitle";
        public static final String START_DATE = "Start_Date";
        public static final String END_DATE = "End_Date";
        public static final String LOCATION = "Location";
        public static final String URL = "Url";
        public static final String EVENT_IMAGE = "Image";
        public static final String CALENDAR_ID = "Calendar_Id";
        public static final String EVENT_TIME_ZONE = "Event_Time_Zone";
    }
}
