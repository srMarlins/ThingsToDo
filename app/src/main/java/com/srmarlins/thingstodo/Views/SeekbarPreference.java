package com.srmarlins.thingstodo.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.srmarlins.thingstodo.Fragments.EventDisplayerFragment;
import com.srmarlins.thingstodo.R;

/**
 * Created by jfowler on 11/17/15.
 */
public class SeekbarPreference extends Preference {

    public static final String RADIUS = "radius";

    public SeekbarPreference(Context context) {
        super(context);
    }

    public SeekbarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SeekbarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SeekbarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.seekbar_layout, parent, false);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final TextView distanceText = (TextView) view.findViewById(R.id.distance_textview);
        AppCompatSeekBar seekBar = (AppCompatSeekBar) view.findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setProgress(prefs.getInt(RADIUS, EventDisplayerFragment.RADIUS));
        distanceText.setText(Integer.toString(prefs.getInt(RADIUS, EventDisplayerFragment.RADIUS)));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceText.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(RADIUS, seekBar.getProgress());
                editor.commit();
            }
        });
    }
}
