package com.srmarlins.eventful_android.operations;

import com.srmarlins.eventful_android.EVDBAPIException;
import com.srmarlins.eventful_android.EVDBRuntimeException;
import com.srmarlins.eventful_android.data.Location;

import java.io.InputStream;
import java.util.HashMap;

public class LocationOperations extends BaseOperations {
    private static final String LOCALES_SEARCH = "/locales/search";

    public LocationOperations() {
    }

    public Location search(String location) throws EVDBRuntimeException, EVDBAPIException {
        HashMap params = new HashMap();
        params.put("l", location);
        InputStream is = this.serverCommunication.invokeMethod("/locales/search", params);
        Location l = (Location) this.unmarshallRequest(Location.class, is);
        return l.getPostalCode() == null && l.getCity() == null && l.getMetro() == null && l.getRegion() == null && l.getCountry() == null ? null : l;
    }
}
