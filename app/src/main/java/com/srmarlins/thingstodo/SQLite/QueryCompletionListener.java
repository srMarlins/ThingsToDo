package com.srmarlins.thingstodo.SQLite;

import android.database.Cursor;

/**
 * Created by jfowler on 11/6/15.
 */
public interface QueryCompletionListener {
    public void onComplete(Cursor result);
}
