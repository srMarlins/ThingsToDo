package com.srmarlins.thingstodo.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jfowler on 11/16/15.
 */
public class AcceptedEventsFragment extends Fragment {
    public static final String TAG = "AcceptedEventsFragment";

    public static AcceptedEventsFragment newInstance() {
        return new AcceptedEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
