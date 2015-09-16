package com.srmarlins.thingstodo.Utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

/**
 * Created by jfowler on 9/15/15.
 */
public class AsyncCalendarQuery extends AsyncQueryHandler {
    public AsyncCalendarQuery(ContentResolver cr) {
        super(cr);
    }
}
