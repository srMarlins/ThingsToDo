package com.srmarlins.thingstodo.Models;

/**
 * Created by jfowler on 9/16/15.
 */
public class EventCalendar {

    private long mId;
    private String mName;

    public EventCalendar(long id, String name){
        mId = id;
        mName = name;
    }

    public long getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }
}
