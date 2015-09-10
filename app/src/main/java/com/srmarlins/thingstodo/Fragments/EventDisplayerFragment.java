package com.srmarlins.thingstodo.Fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;


import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.eventful_android.data.request.EventSearchRequest;
import com.srmarlins.thingstodo.Adapters.EventAdapter;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.Eventful.EventfulApi;
import com.srmarlins.thingstodo.Utils.LocationManager;

import java.util.ArrayList;

/**
 * Created by jfowler on 9/4/15.
 */
public class EventDisplayerFragment extends Fragment implements EventfulApi.EventfulResultsListener{

    private Context mContext;
    private EventfulApi mApi;
    private Location mLocation;
    private ArrayList<Event> mEvents = new ArrayList<>();
    private int pageCount = 1;
    private ListView mListView;
    private EventAdapter mAdapter;

    public static EventDisplayerFragment newInstance() {
        EventDisplayerFragment frag = new EventDisplayerFragment();
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationManager locationManager = LocationManager.getInstance(mContext, new LocationManager.LastLocationListener() {
            @Override
            public void onLocationReceived(Location location) {
                EventDisplayerFragment.this.mLocation = location;
                mApi.requestEvents(mLocation, 10, EventSearchRequest.SortOrder.DATE, pageCount);
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mApi = new EventfulApi(mContext, EventDisplayerFragment.this);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListView = (ListView) rootView.findViewById(R.id.lv_events);
        mAdapter = new EventAdapter(mContext, mEvents);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(onScrollListener());

        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onEventfulResults(SearchResult results) {
        if(results != null) {
            mEvents.addAll(results.getEvents());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onEventfulError(Exception e) {
        e.printStackTrace();
    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int pos = mListView.getLastVisiblePosition();
                    int count = mListView.getCount();
                    if(pos + 5 >= count){
                        if(mLocation != null) {
                            mApi.requestEvents(mLocation, 10, EventSearchRequest.SortOrder.DATE, pageCount);
                            pageCount++;
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

            }

        };
    }
}
