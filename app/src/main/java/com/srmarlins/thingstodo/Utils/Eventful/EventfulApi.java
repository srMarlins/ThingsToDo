package com.srmarlins.thingstodo.Utils.Eventful;

import android.content.Context;
import android.location.Location;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;

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

    public EventfulApi(Context context, EventfulResultsListener listener){
        mContext = context;
        mConfig = new APIConfiguration();
        mConfig.setEvdbPassword("");
        mConfig.setEvdbUser("");
        mConfig.setApiKey(EVENTFUL_KEY);
        mListener = listener;
        mEventAsync = new EventfulAsync(mListener);
    }

    public void requestEvents(Location location, int range, EventSearchRequest.SortOrder order){
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

    public interface EventfulResultsListener {
        void onEventfulResults(SearchResult results);
        void onEventfulError(Exception e);
    }
}
