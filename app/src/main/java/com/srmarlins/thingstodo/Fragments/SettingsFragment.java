package com.srmarlins.thingstodo.Fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.srmarlins.thingstodo.R;

public class SettingsFragment extends PreferenceFragment {

    public static final String TAG = "SettingsFragment";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
