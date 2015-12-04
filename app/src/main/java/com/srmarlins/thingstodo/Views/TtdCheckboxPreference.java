package com.srmarlins.thingstodo.Views;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.srmarlins.thingstodo.R;

/**
 * Created by jfowler on 11/19/15.
 */
public class TtdCheckboxPreference extends CheckBoxPreference {
    public TtdCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TtdCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TtdCheckboxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TtdCheckboxPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView title = (TextView) view.findViewById(android.R.id.title);
        title.setTextColor(ContextCompat.getColor(getContext(),R.color.primary_dark));
    }
}
