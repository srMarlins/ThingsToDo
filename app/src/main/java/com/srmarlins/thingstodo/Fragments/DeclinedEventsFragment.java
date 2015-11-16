package com.srmarlins.thingstodo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srmarlins.thingstodo.R;

public class DeclinedEventsFragment extends Fragment {

    public static final String TAG = "DeclinedEventsFragment";

    public static DeclinedEventsFragment newInstance() {
        return new DeclinedEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_declined_events, container, false);
    }

}
