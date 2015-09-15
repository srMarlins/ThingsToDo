package com.srmarlins.thingstodo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.EventManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> implements CardSwipeHelper.CardSwipeHelperAdapter {

    private ArrayList<Event> mEventsList;
    private Context mContext;
    private EventManager mManager;

    public EventRecyclerViewAdapter(Context context, EventManager manager) {
        mContext = context;
        mEventsList = new ArrayList<>();
        mManager = manager;
    }

    public void updateEventList(ArrayList<Event> list){
        mEventsList = list;
        notifyDataSetChanged();
    }

    @Override
    public EventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventsList.get(position);

        Glide.with(mContext).load(event.getImages().get(event.getImages().size() - 1).getUrl()).into(holder.logo);
        holder.date.setText(formatDate(event));
        holder.location.setText(event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());
        holder.title.setText(event.getTitle());
        holder.description.setText(Html.fromHtml(event.getDescription()));
        holder.layout.setBackgroundColor(Color.WHITE);
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

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position, int direction) {
        if(direction == ItemTouchHelper.START){
            mManager.declineEvent(mEventsList.get(position));
        }else if(direction == ItemTouchHelper.END){
            mManager.acceptEvent(mEventsList.get(position));
        }
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements CardSwipeHelper.CardSwipeViewHolderAdapter {

        private static final float COLOR_THRESHOLD = 280.0f;
        public View mView;
        public RoundedImageView logo;
        public TextView title;
        public TextView date;
        public TextView location;
        public TextView description;
        public LinearLayout layout;

        public ViewHolder(View cardView) {
            super(cardView);
            mView = cardView;
            this.logo = (RoundedImageView) mView.findViewById(R.id.event_image);
            this.title = (TextView) mView.findViewById(R.id.txtTitle);
            this.date = (TextView) mView.findViewById(R.id.txtDate);
            this.location = (TextView) mView.findViewById(R.id.txtLoc);
            this.description = (TextView) mView.findViewById(R.id.txtDesc);
            this.layout = (LinearLayout) mView.findViewById(R.id.card_view_layout);
        }

        @Override
        public void onSwiped(double x) {
            int layoutColor = 0;

            if(x > COLOR_THRESHOLD){
                layoutColor = ContextCompat.getColor(mView.getContext(), R.color.card_accept);
            }else if(x == 0.0){
                layoutColor = Color.WHITE;
            }else if(x < -COLOR_THRESHOLD){
                layoutColor = ContextCompat.getColor(mView.getContext(), R.color.card_decline);
            }

            layout.setBackgroundColor(layoutColor);
        }

    }
}
