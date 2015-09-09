package com.srmarlins.thingstodo.Utils.Eventful;

import android.content.Context;
import android.location.Location;

import com.srmarlins.eventful_android.APIConfiguration;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;
import com.srmarlins.eventful_android.data.request.SearchRequest;


/**
 * Created by jfowler on 9/4/15.
 */
public class EventfulApi {
    //TODO - Place this into a secure location
    private static String EVENTFUL_KEY = "PJVD7NXGr7TKZnSN";
    private static final int NUM_RESULTS = 15;

    private Context mContext;
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

    }

    public void requestEvents(Location location, int range, EventSearchRequest.SortOrder order, int pageNum){
        requestEvents(location, range, order, pageNum, "this weekend");
    }

    public void requestEvents(Location location, int range, EventSearchRequest.SortOrder order, int pageNum, String dateRange){
        buildRequest(location, range, order, pageNum, dateRange);
        (new EventfulAsync(mListener)).execute(this);
    }

    private SearchRequest buildRequest(Location location, int range, EventSearchRequest.SortOrder order, int pageNum, String dateRange){
        mSearchRequest = new EventSearchRequest();
        mSearchRequest.setDateRange(dateRange);
        mSearchRequest.setSortOrder(order);
        mSearchRequest.setLocationRadius(range);
        mSearchRequest.setLocation(String.format("%s,%s", location.getLatitude(), location.getLongitude()));
        mSearchRequest.setPageSize(NUM_RESULTS);
        mSearchRequest.setPageNumber(pageNum);
        return mSearchRequest;
    }

    public Context getmContext() {
        return mContext;
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
