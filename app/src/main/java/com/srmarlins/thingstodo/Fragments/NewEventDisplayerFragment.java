package com.srmarlins.thingstodo.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.srmarlins.thingstodo.Views.SeekbarPreference;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by jfowler on 9/4/15.
 */
public class NewEventDisplayerFragment extends Fragment implements EventManager.EventListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "NewEventDisplayerFragment";
    public static final int RADIUS = 25;
    public static final int RELOAD_AT = 5;

    private Context mContext;
    private EventRecyclerViewAdapter mAdapter;
    private EventManager mEventManager;
    private SwipeRefreshLayout mRefreshLayout;
    private int mRadius;

    public static NewEventDisplayerFragment newInstance() {
        return new NewEventDisplayerFragment();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);

        mRadius = getRadiusFromPrefs();
        mEventManager = new EventManager(mContext, this);
        mRefreshLayout.setColorSchemeColors(R.color.primary_dark);
        mRefreshLayout.setRefreshing(mEventManager.loadEvents(mRadius));

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        mAdapter = new EventRecyclerViewAdapter(mContext, mEventManager);
        recList.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new CardSwipeHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recList);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    private int getRadiusFromPrefs(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getInt(SeekbarPreference.RADIUS, RADIUS);
    }

    public void cancelEventRequest(){
        mRefreshLayout.setRefreshing(false);
        mEventManager.cancelRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRadius = getRadiusFromPrefs();
    }

    @Override
    public void onEventsChanged(Hashtable<String, Event> updatedEventList) {
        mAdapter.updateEventList(updatedEventList);
        if (!mEventManager.isLoading() && updatedEventList.size() < RELOAD_AT) {
            mEventManager.loadEvents(mRadius);
        }
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(mEventManager.loadEvents(mRadius));
    }
}
