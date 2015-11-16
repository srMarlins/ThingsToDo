package com.srmarlins.thingstodo.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Adapters.CardSwipeHelper;
import com.srmarlins.thingstodo.Adapters.EventRecyclerViewAdapter;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.EventManager;

import java.util.ArrayList;

/**
 * Created by jfowler on 9/4/15.
 */
public class EventDisplayerFragment extends Fragment implements EventManager.EventListener {

    public static final String TAG = "EventDisplayerFragment";
    public static final int RADIUS = 15;
    public static final int RELOAD_AT = 5;

    private Context mContext;
    private EventRecyclerViewAdapter mAdapter;
    private RecyclerView mRecList;
    private EventManager mEventManager;

    public static EventDisplayerFragment newInstance() {
        return new EventDisplayerFragment();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mEventManager = new EventManager(mContext, this);
        mEventManager.loadEvents(RADIUS);

        mRecList = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        mRecList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);
        mAdapter = new EventRecyclerViewAdapter(mContext, mEventManager);
        mRecList.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new CardSwipeHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecList);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onEventsChanged(ArrayList<Event> updatedEventList) {
        mAdapter.updateEventList(updatedEventList);
        if (!mEventManager.isLoading() && updatedEventList.size() < RELOAD_AT) {
            mEventManager.loadEvents(RADIUS);
        }
    }
}
