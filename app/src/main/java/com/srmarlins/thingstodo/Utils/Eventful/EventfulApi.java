package com.srmarlins.thingstodo.Utils.Eventful;

import android.content.Context;
import android.location.Location;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.SearchResult;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.data.request.SearchRequest;
import com.evdb.javaapi.operations.EventOperations;

import java.util.ArrayList;
import java.util.Formatter;


/**
 * Created by jfowler on 9/4/15.
 */
public class EventfulApi {
    //TODO - Place this into a secure location
    private static String EVENTFUL_KEY = "PJVD7NXGr7TKZnSN";

    private Context mContext;
    private EventfulAsync mEventAsync;
    private APIConfiguration mConfig;
    private EventfulResultsListener mListener;
    private EventSearchRequest mSearchRequest;
    private Location mLocation;
    private int mRange;

    public EventfulApi(Context context){
        mContext = context;
        mConfig = new APIConfiguration();
        mConfig.setEvdbPassword("");
        mConfig.setEvdbUser("");
        mConfig.setApiKey(EVENTFUL_KEY);
        mEventAsync = new EventfulAsync(mListener);
    }

    public void requestEvents(Location location, int range, EventSearchRequest.SortOrder order, final EventfulResultsListener listener){
        mSearchRequest = new EventSearchRequest();

        mSearchRequest.setSortOrder(order);
        mSearchRequest.setLocationRadius(range);
        mSearchRequest.setLocation(String.format("%s,%s", location.getLatitude(), location.getLongitude()));

        mEventAsync.execute(this);

    }

    public Context getmContext() {
        return mContext;
    }

    public EventfulAsync getmEventAsync() {
        return mEventAsync;
    }

    public APIConfiguration getmConfig() {
        return mConfig;
    }

    public EventfulResultsListener getmListener() {
        return mListener;
    }

    public EventSearchRequest getmSearchRequest() {
        return mSearchRequest;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public int getmRange() {
        return mRange;
    }

    public interface EventfulResultsListener {
        void onEventfulResults(SearchResult results);
        void onEventfulError(Exception e);
    }
}
