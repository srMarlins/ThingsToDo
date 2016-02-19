package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.location.Location;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;
import com.srmarlins.thingstodo.Models.EventCalendar;
import com.srmarlins.thingstodo.SQLite.EventContract;
import com.srmarlins.thingstodo.Utils.Eventful.EventfulApi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by jfowler on 9/14/15.
 */
public class EventManager implements EventfulApi.EventfulResultsListener, LocationManager.LastLocationListener {

    public static final int DEFAULT_CALENDAR_NUM = 0;
    public static final int DEFAULT_RESULTS_SHOWN_NUM = 20;
    public static final String[] PROJECTION = new String[]{CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION};

    private static EventManager mEventManager;
    private Hashtable<String, Event> mCurrentEvents;
    private Hashtable<String, Event> mDeclinedEvents;
    private Hashtable<String, Event> mAcceptedEvents;
    private EventfulApi mApi;
    private Location mLocation;
    private int mRadius;
    private EventListener mListener;
    private boolean mLoading = false;
    private CalendarManager mCalendar;
    private ArrayList<EventCalendar> mSelectedCalendars;
    private EventContractManager mEcManager;
    private boolean mIsCanceled = false;

    private int mPageCount = 1;
    private int mRequestNumber = 1;

    public EventManager(Context context, EventListener listener) {
        mApi = new EventfulApi(context, this);
        mListener = listener;
        mCurrentEvents = new Hashtable<>();
        mDeclinedEvents = new Hashtable<>();
        mAcceptedEvents = new Hashtable<>();
        mSelectedCalendars = new ArrayList<>();
        mCalendar = new CalendarManager(context);
        mEcManager = new EventContractManager(context);
        buildEvents();
        mEventManager = this;
    }

    public static EventManager getInstance() {
        return mEventManager;
    }

    public void buildEvents() {
        EventCalendar[] calendars = mCalendar.getCalendars();

        if (calendars != null && calendars.length > 0 && mSelectedCalendars.size() == 0) {
            mSelectedCalendars.add(mCalendar.getCalendars()[DEFAULT_CALENDAR_NUM]);
        }

        mAcceptedEvents.putAll(mEcManager.retrieveAllEvents(EventContract.EventEntry.TABLE_NAME_ACCEPTED));
        mDeclinedEvents.putAll(mEcManager.retrieveAllEvents(EventContract.EventEntry.TABLE_NAME_DECLINED));
    }

    public void addCalendar(EventCalendar calendar) {
        mSelectedCalendars.add(calendar);
    }

    public void removeCalendar(EventCalendar calendar) {
        mSelectedCalendars.remove(calendar);
    }

    public ArrayList<EventCalendar> getSelectedCalendars() {
        return mSelectedCalendars;
    }

    public void setContext(Context context) {
        mApi.setContext(context);
    }

    public boolean loadEvents(int radius) {
        if (mRequestNumber > mPageCount) {
            return false;
        }

        mIsCanceled = false;
        mLoading = true;
        mRadius = radius;

        if (mLocation == null) {
            LocationManager.getInstance(mApi.getmContext(), this);
        } else {
            mApi.requestEvents(mLocation, mRadius, EventSearchRequest.SortOrder.DATE, mRequestNumber);
            mRequestNumber++;
        }

        return true;
    }

    @Override
    public void onEventfulResults(SearchResult results) {
        mLoading = false;
        if (results != null) {
            mPageCount = results.getPageCount();
            Hashtable<String, Event> newEvents = new Hashtable<>(results.getEvents());
            newEvents = mergeEventsByTitle(newEvents, mAcceptedEvents);
            newEvents = removeEventsById(newEvents, mDeclinedEvents);
            mCurrentEvents = mergeEvents(mCurrentEvents, newEvents);
            if (!mIsCanceled) {
                mListener.onEventsChanged(mCurrentEvents);
            }
            if (mCurrentEvents.size() < DEFAULT_RESULTS_SHOWN_NUM) {
                loadEvents(mRadius);
            }
        }
    }

    public void cancelRequest() {
        mIsCanceled = true;
    }

    public boolean isLoading() {
        return mLoading;
    }

    private Hashtable<String, Event> mergeEvents(Hashtable<String, Event> oldEventMap, Hashtable<String, Event> newEventMap) {
        for(Event event : newEventMap.values()){
            oldEventMap.put(event.getSeid(), event);
        }
        return oldEventMap;
    }

    private Hashtable<String, Event> mergeEventsByTitle(Hashtable<String, Event> list1, Hashtable<String, Event> list2) {
        Iterator<Event> returnIter = list1.values().iterator();
        while (returnIter.hasNext()) {
            Event compEvent = returnIter.next();
            Iterator<Event> removeIter = list2.values().iterator();
            while (removeIter.hasNext()) {
                Event removeEvent = removeIter.next();
                if (removeEvent.getTitle().equals(compEvent.getTitle())) {
                    returnIter.remove();
                }
            }
        }

        return list1;
    }

    private Hashtable<String, Event> removeEventsById(Hashtable<String, Event> original, Hashtable<String, Event> toRemove) {
        for(Event event : toRemove.values()){
            original.remove(event.getSeid());
        }
        return original;
    }

    public void declineEvent(Event event) {
        mEcManager.insertEvent(event, 0, EventContract.EventEntry.TABLE_NAME_DECLINED); //TODO - Change this zero. Only for testing purposes.
        mCurrentEvents.remove(event.getSeid());
        mDeclinedEvents.put(event.getSeid(), event);
        mListener.onEventsChanged(mCurrentEvents);
    }

    public void acceptEvent(Event event) {
        for (EventCalendar calendar : mSelectedCalendars) {
            mCalendar.insertEvent(event, calendar.getId());
        }
        mEcManager.insertEvent(event, 0, EventContract.EventEntry.TABLE_NAME_ACCEPTED);
        mCurrentEvents.remove(event.getSeid());
        mAcceptedEvents.put(event.getSeid(), event);
        mListener.onEventsChanged(mCurrentEvents);
    }

    public Hashtable<String, Event> getAcceptedEvents() {
        return mAcceptedEvents;
    }

    public Hashtable<String, Event> getDeclinedEvents() {
        return mDeclinedEvents;
    }

    public Hashtable<String, Event> getCurrentEvents() {
        return mCurrentEvents;
    }

    public Location getLocation() {
        return mLocation;
    }

    @Override
    public void onEventfulError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onLocationReceived(Location location) {
        mLocation = location;
        mApi.requestEvents(mLocation, mRadius, EventSearchRequest.SortOrder.DATE, mRequestNumber);
        mRequestNumber++;
    }

    @Override
    public void onLocationNotReceived() {
        Toast.makeText(mApi.getmContext(), "Location not available", Toast.LENGTH_SHORT).show();
    }

    public interface EventListener {
        void onEventsChanged(Hashtable<String, Event> updatedEventList);
    }
}
