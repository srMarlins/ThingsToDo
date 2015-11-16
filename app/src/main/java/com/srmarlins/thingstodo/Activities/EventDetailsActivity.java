package com.srmarlins.thingstodo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.srmarlins.eventful_android.data.Event;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.EventManager;
import com.srmarlins.thingstodo.Utils.UIUtils;

public class EventDetailsActivity extends AppCompatActivity {

    public static final String EVENT_ID = "eventId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Event event = getEventFromIntent();

        if (event == null) {
            finish();
        }

        ImageView eventImage = (ImageView) findViewById(R.id.event_image);
        TextView titleText = (TextView) findViewById(R.id.txtTitle);
        TextView dateText = (TextView) findViewById(R.id.txtDate);
        TextView locationText = (TextView) findViewById(R.id.txtLoc);
        TextView descriptionText = (TextView) findViewById(R.id.txtDesc);
        AddFloatingActionButton fabAccept = (AddFloatingActionButton) findViewById(R.id.btnFabAccept);
        FloatingActionButton fabDecline = (FloatingActionButton) findViewById(R.id.btnFabDecline);

        Glide.with(this).load(event.getImages().get(event.getImages().size() - 1).getUrl()).into(eventImage);
        titleText.setText(event.getTitle());
        dateText.setText(UIUtils.formatDate(event));
        locationText.setText(event.getVenueCity() + ", " + event.getVenueRegionAbbreviation());
        descriptionText.setText(event.getDescription().trim());

        fabAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getInstance().acceptEvent(event);
                onEventAccepted();
            }
        });

        fabDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.getInstance().declineEvent(event);
                onEventDeclined();
            }
        });

    }

    private void onEventAccepted() {
        finish();
    }

    private void onEventDeclined() {
        finish();
    }

    private Event getEventFromIntent() {
        Event event = null;

        String eventId = getIntent().getStringExtra(EVENT_ID);

        EventManager manager = EventManager.getInstance();

        if (manager != null) {
            event = EventManager.findEventById(eventId, manager.getCurrentEvents());
        }

        return event;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
