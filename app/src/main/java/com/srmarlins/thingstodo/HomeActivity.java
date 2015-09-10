package com.srmarlins.thingstodo;

import android.app.Activity;
import android.os.Bundle;

import com.srmarlins.thingstodo.Fragments.EventDisplayerFragment;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EventDisplayerFragment.newInstance(), EventDisplayerFragment.TAG)
                .commit();

    }
}
