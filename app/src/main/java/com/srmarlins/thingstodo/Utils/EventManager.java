package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.location.Location;

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

    private int mRequestNumber = 0;

    public EventManager(Context context, EventListener listener){
        mApi = new EventfulApi(context, this);
        mListener = listener;
        mCurrentEvents = new ArrayList<>();
        mDeclinedEvents = new ArrayList<>();
        mAcceptedEvents = new ArrayList<>();
    }

    public void setContext(Context context){
        mApi.setContext(context);
    }

    public void loadEvents(int radius){
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
        if(results != null) {
            ArrayList<Event> newEvents = new ArrayList<>(results.getEvents());
            mCurrentEvents = mergeEvents(mCurrentEvents, newEvents);
            mListener.onEventsChanged(mCurrentEvents);
        }
    }

    private ArrayList<Event> mergeEvents(ArrayList<Event> list1, ArrayList<Event> list2){
        for(Event event1: list1){
            for(Event event2: list2){
                if(event1.getSeid() == event2.getSeid()){
                    list2.remove(event2);
                }
            }
        }
        list1.addAll(list2);
        return list1;
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
