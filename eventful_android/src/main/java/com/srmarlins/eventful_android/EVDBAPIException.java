package com.srmarlins.eventful_android;

import com.srmarlins.eventful_android.data.response.GenericErrorResponse;

public class EVDBAPIException extends Exception {
    private static final long serialVersionUID = -8235407395551567671L;

    public EVDBAPIException(String msg) {
        super(msg);
    }

    public EVDBAPIException(GenericErrorResponse response) {
        super(response.getDescription());
    }

    public EVDBAPIException(String string, Exception e) {
        super(string, e);
    }
}
