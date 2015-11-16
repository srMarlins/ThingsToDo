package com.srmarlins.thingstodo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.srmarlins.thingstodo.Fragments.EventDisplayerFragment;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.CalendarManager;
import com.srmarlins.thingstodo.Utils.NavigationDrawerUtil;

public class HomeActivity extends AppCompatActivity {

    private NavigationDrawerUtil mNavUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CalendarManager calendarManager = new CalendarManager(this);
        mNavUtil = NavigationDrawerUtil.getInstance(this);
        mNavUtil.setupNavDrawer(this, savedInstanceState);
        mNavUtil.addCalendars(calendarManager.getCalendars());

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EventDisplayerFragment.newInstance(), EventDisplayerFragment.TAG)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
