package com.srmarlins.thingstodo.Utils.Eventful;

import android.os.AsyncTask;
import android.text.Html;

import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.operations.EventOperations;

import java.util.ArrayList;


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

    private ArrayList<Event> removeImagelessResult(SearchResult result) {
        ArrayList<Event> events = new ArrayList<>(result.getEvents());
        ArrayList<Event> parsedEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getImages() != null && !event.getImages().isEmpty()) {
                String desc = event.getDescription();
                if (desc != null) {
                    event.setDescription(Html.fromHtml(desc).toString());
                }
                parsedEvents.add(event);
            }
        }

        return parsedEvents;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult) {
        mListener.onEventfulResults(searchResult);
    }
}
