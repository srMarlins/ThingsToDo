package com.srmarlins.thingstodo.Utils;

import android.animation.ObjectAnimator;
import android.widget.TextView;

import com.srmarlins.eventful_android.data.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jfowler on 11/9/15.
 */
public class UIUtils {

    /*
     * Method taken from http://stackoverflow.com/questions/15627530/android-expandable-textview-with-animation
     */
    public static void expandTextView(TextView tv) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(200).start();
    }

    /*
     * Method taken from http://stackoverflow.com/questions/15627530/android-expandable-textview-with-animation
     */
    public static void collapseTextView(TextView tv, int numLines) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }

    public static String formatDate(Event event) {
        String dateString = "";
        DateFormat oldF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        DateFormat newF = new SimpleDateFormat("EEE, MMM dd, h:mm a");

        try {
            if (event.getStartTime().compareTo(java.util.Calendar.getInstance().getTime()) >= 0) {
                Date date = oldF.parse(event.getStartTime().toString());
                dateString = newF.format(date);
            } else {
                Date date = oldF.parse(event.getStopTime().toString());
                dateString = "Ends: " + newF.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString.toUpperCase();
    }
}
