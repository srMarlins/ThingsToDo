package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.location.Location;

import com.srmarlins.eventful_android.data.Calendar;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;
import com.srmarlins.thingstodo.Utils.Eventful.EventfulApi;

import java.util.ArrayList;

/**
 * Created by jfowler on 9/14/15.
 */
public class EventManager implements EventfulApi.EventfulResultsListener, LocationManager.LastLocationListener{

    private ArrayList<Event> mCurrentEvents;
    private ArrayList<Event> mDeclinedEvents;
    private ArrayList<Event> mAcceptedEvents;
    private EventfulApi mApi;
    private Location mLocation;
    private int mRadius;
    private EventListener mListener;
    private boolean mLoading = false;
    private CalendarManager mCalendar;

    private int mPageCount = 1;
    private int mRequestNumber = 1;

    public EventManager(Context context, EventListener listener){
        mApi = new EventfulApi(context, this);
        mListener = listener;
        mCurrentEvents = new ArrayList<>();
        mDeclinedEvents = new ArrayList<>();
        mAcceptedEvents = new ArrayList<>();
        mCalendar = new CalendarManager(context);
    }

    public void setContext(Context context){
        mApi.setContext(context);
    }

    public void loadEvents(int radius){
        if(mRequestNumber > mPageCount){
            return;
        }

        mLoading = true;
        mRadius = radius;

        if(mLocation == null) {
            LocationManager.getInstance(mApi.getmContext(), this);
        }else{
            mApi.requestEvents(mLocation, mRadius, EventSearchRequest.SortOrder.DATE, mRequestNumber);
            mRequestNumber++;
        }
    }

    @Override
    public void onEventfulResults(SearchResult results) {
        mPageCount = results.getPageCount();
        mLoading = false;
        if(results != null) {
            ArrayList<Event> newEvents = new ArrayList<>(results.getEvents());
            mCurrentEvents = mergeEvents(mCurrentEvents, newEvents);
            mListener.onEventsChanged(mCurrentEvents);
        }
    }

    public boolean isLoading(){
        return mLoading;
    }

    private ArrayList<Event> mergeEvents(ArrayList<Event> list1, ArrayList<Event> list2){
        for(Event event1: list1){
            list2.remove(event1);
        }
        if(!list2.isEmpty()) {
            list1.addAll(list2);
        }
        return list1;
    }

    public void declineEvent(Event event){
        mCurrentEvents.remove(event);
        mDeclinedEvents.add(event);
        mListener.onEventsChanged(mCurrentEvents);
    }

    public void acceptEvent(Event event){
        mCalendar.insertEvent(event, mCalendar.getCalendars()[0].getId());
        mCurrentEvents.remove(event);
        mAcceptedEvents.add(event);
        mListener.onEventsChanged(mCurrentEvents);
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
    }

    public interface EventListener{
        void onEventsChanged(ArrayList<Event> updatedEventList);
    }
}