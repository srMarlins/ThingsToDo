package com.srmarlins.thingstodo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.nfc.FormatException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.srmarlins.eventful_android.data.Calendar;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.eventful_android.data.SearchResult;
import com.srmarlins.thingstodo.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jfowler on 9/9/15.
 */
public class EventAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Event> mEvents;

    public EventAdapter(Context context, ArrayList<Event> events){
        mContext = context;
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        Event currentEvent = mEvents.get(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.event_list_view_item, parent, false);

            holder.location = (TextView) convertView.findViewById(R.id.txtLoc);
            holder.date = (TextView) convertView.findViewById(R.id.txtDate);
            holder.image = (ImageView) convertView.findViewById(R.id.ivEventImage);
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String dateString = "";
        DateFormat oldF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat newF = new SimpleDateFormat("MMMM dd, ''yy");

        try {
            if (currentEvent.getStartTime().compareTo(java.util.Calendar.getInstance().getTime()) >= 0) {
                Date date = oldF.parse(currentEvent.getStartTime().toString());
                dateString = newF.format(date).toString();
            } else {
                Date date = oldF.parse(currentEvent.getStopTime().toString());
                dateString = "Ends: " + newF.format(date).toString();
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date.setText(dateString);
        holder.location.setText(currentEvent.getVenueCity() + ", " + currentEvent.getVenueRegionAbbreviation());
        holder.title.setText(currentEvent.getTitle());
        Glide.with(mContext).load(currentEvent.getImages().get(currentEvent.getImages().size() - 1).getUrl()).into(holder.image);

        return convertView;
    }

    static class ViewHolder{
        TextView date;
        TextView location;
        ImageView image;
        TextView title;
    }

}



