package com.srmarlins.thingstodo.Utils.Eventful;

import android.os.AsyncTask;
import android.text.Html;

import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.operations.EventOperations;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by jfowler on 9/4/15.
 */

public class EventfulAsync extends AsyncTask<EventfulApi, Void, SearchResult> {
    private EventfulApi.EventfulResultsListener mListener;

    public EventfulAsync(EventfulApi.EventfulResultsListener listener) {
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

        if (searchResult != null) {
            searchResult.setEvents(removeImagelessResult(searchResult));
        }

        return searchResult;
    }

    private Hashtable<String, Event> removeImagelessResult(SearchResult result) {
        Hashtable<String, Event> events = new Hashtable<>(result.getEvents());
        Hashtable<String, Event> parsedEvents = new Hashtable<>();

        for (Event event : events.values()) {
            if (event.getImages() != null && !event.getImages().isEmpty()) {
                String desc = event.getDescription();
                if (desc != null) {
                    event.setDescription(Html.fromHtml(desc).toString());
                }
                parsedEvents.put(event.getSeid(), event);
            }
        }

        return parsedEvents;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult) {
        mListener.onEventfulResults(searchResult);
    }
}
