package com.srmarlins.eventful_android;

import com.srmarlins.eventful_android.util.DateAdapter;

import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

public class APIConfiguration {
    private static String apiKey;
    private static String baseURL = "http://api.eventful.com/rest/";
    private static String evdbUser = "";
    private static String evdbPassword = "";

    public APIConfiguration() {
    }

    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
    }

    public static String getApiKey() throws EVDBRuntimeException {
        if (apiKey == null) {
            throw new EVDBRuntimeException("API Key not set");
        } else {
            return apiKey;
        }
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static String setBaseURL(String baseURL) {
        baseURL = baseURL;
        return baseURL;
    }

    public static String getEvdbPassword() throws EVDBRuntimeException {
        if (evdbPassword == null) {
            throw new EVDBRuntimeException("Eventful Password Not Set, see APIConfiguration.setEvdbPassword method");
        } else {
            return evdbPassword;
        }
    }

    public static void setEvdbPassword(String password) {
        evdbPassword = password;
    }

    public static String getEvdbUser() throws EVDBRuntimeException {
        if (evdbUser == null) {
            throw new EVDBRuntimeException("Eventful Username Not Set, see APIConfiguration.setEvdbUser method");
        } else {
            return evdbUser;
        }
    }

    public static void setEvdbUser(String user) {
        evdbUser = user;
    }

    public static RegistryMatcher getRegistryMatcher(){
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateAdapter());
        return m;
    }
}

