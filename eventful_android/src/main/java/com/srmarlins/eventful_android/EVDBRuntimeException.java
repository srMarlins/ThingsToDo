package com.srmarlins.eventful_android;

public class EVDBRuntimeException extends Exception {
    private static final long serialVersionUID = -3997854470176178872L;

    public EVDBRuntimeException(String msg) {
        super(msg);
    }

    public EVDBRuntimeException(String string, Exception e) {
        super(string, e);
    }
}
