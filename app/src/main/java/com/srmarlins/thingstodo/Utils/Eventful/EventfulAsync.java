package com.srmarlins.thingstodo.Utils.Eventful;

import android.os.AsyncTask;

import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.operations.EventOperations;


/**
 * Created by jfowler on 9/4/15.
 */

public class EventfulAsync extends AsyncTask<EventfulApi, Void, SearchResult> {
    private EventfulApi.EventfulResultsListener mListener;

    public EventfulAsync(EventfulApi.EventfulResultsListener listener){
        mListener = listener;
    }

    @Override
    protected SearchResult doInBackground(EventfulApi... params) {

        SearchResult searchResult = null;
        EventOperations eventOperations = new EventOperations();

        try {
           searchResult = eventOperations.search(params[0].getmSearchRequest());
        } catch (EVDBRuntimeException e) {
            mListener.onEventfulError(e);
        } catch (EVDBAPIException e) {
            mListener.onEventfulError(e);
        }

        return searchResult;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult) {
        mListener.onEventfulResults(searchResult);
    }
}
