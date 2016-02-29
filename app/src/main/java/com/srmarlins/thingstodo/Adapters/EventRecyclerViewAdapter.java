package com.srmarlins.thingstodo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.Activities.EventDetailsActivity;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.EventManager;
import com.srmarlins.thingstodo.Utils.LocationManager;
import com.srmarlins.thingstodo.Utils.RandomMaterialColorGenerator;
import com.srmarlins.thingstodo.Utils.UIUtils;

import java.util.ArrayList;
import java.util.Hashtable;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> implements CardSwipeHelper.CardSwipeHelperAdapter {

    private ArrayList<Event> mEventsList;
    private Context mContext;
    private EventManager mManager;

    public EventRecyclerViewAdapter(Context context, EventManager manager) {
        mContext = context;
        mEventsList = new ArrayList<>();
        mManager = manager;
    }

    public void updateEventList(Hashtable<String, Event> newList) {
        mEventsList.clear();
        mEventsList.addAll(newList.values());
        EventManager.sortEventsByDate(mEventsList);
        notifyDataSetChanged();
    }

    @Override
    public EventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_view_item, parent, false);
        return new ViewHolder(v);
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

        Glide.with(mContext).load(event.getImage().getUrl()).into(holder.logo);
        holder.date.setText(UIUtils.formatDate(event));
        holder.location.setText(event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());
        holder.title.setText(event.getTitle());
        holder.layout.setBackgroundColor(holder.color);
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
        if (direction == ItemTouchHelper.START) {
            mManager.declineEvent(mEventsList.get(position));
        } else if (direction == ItemTouchHelper.END) {
            mManager.acceptEvent(mEventsList.get(position));
        }
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements CardSwipeHelper.CardSwipeViewHolderAdapter {

        private static final float COLOR_THRESHOLD = 210.0f;
        public View mView;
        public ImageView logo;
        public TextView title;
        public TextView date;
        public TextView location;
        public TextView distance;
        public RelativeLayout layout;
        public int color;

        public ViewHolder(View cardView) {
            super(cardView);
            this.mView = cardView;
            this.logo = (ImageView) mView.findViewById(R.id.event_image);
            this.title = (TextView) mView.findViewById(R.id.txtTitle);
            this.date = (TextView) mView.findViewById(R.id.txtDate);
            this.location = (TextView) mView.findViewById(R.id.txtLoc);
            this.distance = (TextView) mView.findViewById(R.id.distance_text);
            this.layout = (RelativeLayout) mView.findViewById(R.id.card_view_layout);
        }

        @Override
        public void onSwiped(double x) {
            int layoutColor = 0;

            if (x > COLOR_THRESHOLD) {
                layoutColor = ContextCompat.getColor(mView.getContext(), R.color.card_accept);
            } else if (x == 0.0) {
                layoutColor = color;
            } else if (x < -COLOR_THRESHOLD) {
                layoutColor = ContextCompat.getColor(mView.getContext(), R.color.card_decline);
            }

            layout.setBackgroundColor(layoutColor);
        }

    }
}
