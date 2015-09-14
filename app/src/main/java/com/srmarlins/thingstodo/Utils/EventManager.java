package com.srmarlins.thingstodo.Utils;

import com.srmarlins.eventful_android.data.Event;

import java.util.ArrayList;

/**
 * Created by jfowler on 9/14/15.
 */
public class EventManager {

    private static EventManager mEventManager;

    private ArrayList<Event> mNewEvents;
    private ArrayList<Event> mCurrentEvents;
    private ArrayList<Event> mDeclinedEvents;
    private ArrayList<Event> mAcceptedEvents;

    public static EventManager getInstance(){
        if(mEventManager == null){
            mEventManager = new EventManager();
        }
        return mEventManager;
    }
}
