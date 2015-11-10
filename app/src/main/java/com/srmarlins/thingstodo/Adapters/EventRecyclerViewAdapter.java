package com.srmarlins.thingstodo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Activities.EventDetailsActivity;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.EventManager;
import com.srmarlins.thingstodo.Utils.UIUtils;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Event event = mEventsList.get(position);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailsActivity.class);
                intent.putExtra(EventDetailsActivity.EVENT_ID, event.getSeid());
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(event.getImages().get(event.getImages().size() - 1).getUrl()).into(holder.logo);
        holder.date.setText(UIUtils.formatDate(event));
        holder.location.setText(event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());
        holder.title.setText(event.getTitle());
        holder.layout.setBackgroundColor(Color.WHITE);
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
        public ImageView logo;
        public TextView title;
        public TextView date;
        public TextView location;
        public LinearLayout layout;

        public ViewHolder(View cardView) {
            super(cardView);
            mView = cardView;
            this.logo = (ImageView) mView.findViewById(R.id.event_image);
            this.title = (TextView) mView.findViewById(R.id.txtTitle);
            this.date = (TextView) mView.findViewById(R.id.txtDate);
            this.location = (TextView) mView.findViewById(R.id.txtLoc);
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
