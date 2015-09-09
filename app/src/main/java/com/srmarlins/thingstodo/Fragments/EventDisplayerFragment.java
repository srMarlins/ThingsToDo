package com.srmarlins.thingstodo.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.srmarlins.thingstodo.R;

/**
 * Created by jfowler on 9/4/15.
 */
public class EventDisplayerFragment extends Fragment {

    public static EventDisplayerFragment newInstance() {
        EventDisplayerFragment frag = new EventDisplayerFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
