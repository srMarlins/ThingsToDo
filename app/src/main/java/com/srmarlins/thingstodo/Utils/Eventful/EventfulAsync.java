package com.srmarlins.thingstodo.Utils.Eventful;

import android.os.AsyncTask;

import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.SearchResult;
import com.evdb.javaapi.operations.EventOperations;

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

        EventfulApi.EventfulResultsListener listener = params[0].getmListener();
        SearchResult searchResult = null;
        EventOperations eventOperations = new EventOperations();

        try {
           searchResult = eventOperations.search(params[0].getmSearchRequest());
        } catch (EVDBRuntimeException e) {
            listener.onEventfulError(e);
        } catch (EVDBAPIException e) {
            listener.onEventfulError(e);
        }

        return searchResult;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult) {
        mListener.onEventfulResults(searchResult);
    }
}
