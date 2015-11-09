package com.srmarlins.thingstodo.Utils;

import android.animation.ObjectAnimator;
import android.widget.TextView;

/**
 * Created by jfowler on 11/9/15.
 */
public class UIUtils {

    /*
     * Method taken from http://stackoverflow.com/questions/15627530/android-expandable-textview-with-animation
     */
    private static void expandTextView(TextView tv){
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(200).start();
    }

    /*
     * Method taken from http://stackoverflow.com/questions/15627530/android-expandable-textview-with-animation
     */
    private static void collapseTextView(TextView tv, int numLines){
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", numLines);
        animation.setDuration(200).start();
    }
}
