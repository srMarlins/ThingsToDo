package com.srmarlins.thingstodo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Event> mEventsList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public RoundedImageView logo;
        public TextView title;
        public TextView date;
        public TextView location;
        public TextView description;

        public ViewHolder(View cardView) {
            super(cardView);
            mView = cardView;
            this.logo = (RoundedImageView) mView.findViewById(R.id.event_image);
            this.title = (TextView) mView.findViewById(R.id.txtTitle);
            this.date = (TextView) mView.findViewById(R.id.txtDate);
            this.location = (TextView) mView.findViewById(R.id.txtLoc);
            this.description = (TextView) mView.findViewById(R.id.txtDesc);
        }
    }

    public EventRecyclerViewAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        mEventsList = events;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventsList.get(position);

        Glide.with(mContext).load(event.getImages().get(event.getImages().size() - 1).getUrl()).into(holder.logo);
        holder.date.setText(formatDate(event));
        holder.location.setText(event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());
        holder.title.setText(event.getTitle());
        holder.description.setText(Html.fromHtml(event.getDescription()));
    }

    public String formatDate(Event event){
        String dateString = "";
        DateFormat oldF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat newF = new SimpleDateFormat("MMMM dd, ''yy");

        try {
            if (event.getStartTime().compareTo(java.util.Calendar.getInstance().getTime()) >= 0) {
                Date date = oldF.parse(event.getStartTime().toString());
                dateString = newF.format(date).toString();
            } else {
                Date date = oldF.parse(event.getStopTime().toString());
                dateString = "Ends: " + newF.format(date).toString();
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mEventsList.size();
    }
}
