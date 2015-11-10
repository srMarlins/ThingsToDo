package com.srmarlins.thingstodo.Models;

/**
 * Created by jfowler on 9/16/15.
 */
public class EventCalendar {

    private long mId;
    private String mName;
    private int mColor;

    public EventCalendar(long id, String name, int color){
        mId = id;
        mName = name;
        mColor = color;
    }

    public long getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public int getColor(){
        return mColor;
    }

    public void setId(long id){
        mId = id;
    }

    public void setName(String name){
        mName = name;
    }

    public void setColor(int color){
        mColor = color;
    }
}
