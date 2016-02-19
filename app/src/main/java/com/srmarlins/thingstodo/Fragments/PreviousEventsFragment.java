package com.srmarlins.thingstodo.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by jfowler on 11/16/15.
 */
public class PreviousEventsFragment extends Fragment implements EventManager.EventListener{
    public static final String TAG = "PreviousEventsFragment";
    public static final String TYPE = "type";
    public static final int ACCEPTED = 0;
    public static final int DECLINED = 1;

    private Context mContext;
    private EventRecyclerViewAdapter mAdapter;
    private int mType;

    public static PreviousEventsFragment newInstance(int listType) {
        PreviousEventsFragment fragment = new PreviousEventsFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        EventManager eventManager = EventManager.getInstance();
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);

        mType = getArguments().getInt(TYPE);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        mAdapter = new EventRecyclerViewAdapter(mContext, eventManager);
        recList.setAdapter(mAdapter);


        switch (mType){
            case ACCEPTED: mAdapter.updateEventList(eventManager.getAcceptedEvents()); break;
            case DECLINED: mAdapter.updateEventList(eventManager.getDeclinedEvents()); break;
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onEventsChanged(Hashtable<String, Event> updatedEventList) {
        mAdapter.updateEventList(updatedEventList);
    }
}
