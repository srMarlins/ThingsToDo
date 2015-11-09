package com.srmarlins.thingstodo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.srmarlins.thingstodo.Fragments.EventDisplayerFragment;
import com.srmarlins.thingstodo.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EventDisplayerFragment.newInstance(), EventDisplayerFragment.TAG)
                .commit();

    }
}
