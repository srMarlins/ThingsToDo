package com.srmarlins.thingstodo.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.srmarlins.thingstodo.Fragments.NewEventDisplayerFragment;
import com.srmarlins.thingstodo.R;
import com.srmarlins.thingstodo.Utils.CalendarManager;
import com.srmarlins.thingstodo.Utils.NavigationDrawerUtil;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
                .add(R.id.fragment_container, NewEventDisplayerFragment.newInstance(), com.srmarlins.thingstodo.Fragments.NewEventDisplayerFragment.TAG)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
