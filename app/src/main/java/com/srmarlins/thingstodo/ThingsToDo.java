package com.srmarlins.thingstodo;

import android.app.Application;
import android.graphics.Typeface;

import java.lang.reflect.Field;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jfowler on 12/2/15.
 */
public class ThingsToDo extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/quicksandbold.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
