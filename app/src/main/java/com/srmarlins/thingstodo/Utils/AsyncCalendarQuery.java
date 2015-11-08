package com.srmarlins.thingstodo.Utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.srmarlins.thingstodo.SQLite.QueryCompletionListener;

/**
 * Created by jfowler on 9/15/15.
 */
public class AsyncCalendarQuery extends AsyncQueryHandler {

    private QueryCompletionListener mQueryCompletionListener;

    public AsyncCalendarQuery(ContentResolver cr, QueryCompletionListener queryCompletionListener) {
        super(cr);
        mQueryCompletionListener = queryCompletionListener;
    }

    public AsyncCalendarQuery(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if(mQueryCompletionListener != null && token == CalendarManager.EVENT_QUERY_TOKEN){
            mQueryCompletionListener.onComplete(cursor);
        }
    }

    public void startQuery(int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy, QueryCompletionListener listener) {
        mQueryCompletionListener = listener;
        super.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
    }
}
