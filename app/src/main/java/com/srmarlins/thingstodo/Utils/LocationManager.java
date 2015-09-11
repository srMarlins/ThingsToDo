package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by jfowler on 9/4/15.
 */
public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private static String LOCATION_MANAGER_TAG = "LocationManager";

    private static LocationManager mManagerInstance;

    private Context mContext;
    private LastLocationListener mListener;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    protected LocationManager(Context context, LastLocationListener listener) {
        mContext = context;
        mListener = listener;
        initGoogleApiClient();
    }

    public void initGoogleApiClient(){
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    public static LocationManager getInstance(Context context, LastLocationListener listener) {
        if (listener == null) {
            return null;
        }
        if (mManagerInstance == null) {
            mManagerInstance = new LocationManager(context, listener);
        }else{
            mManagerInstance.initGoogleApiClient();
            mManagerInstance.setListener(listener);
        }
        return mManagerInstance;
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void setListener(LastLocationListener listener){
        mListener = listener;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = getLastKnownLocation();
        if (mLastLocation != null) {
            mListener.onLocationReceived(mLastLocation);
        }else{
            mListener.onLocationNotReceived();
        }
    }

    private Location getLastKnownLocation() {
        android.location.LocationManager lm = (android.location.LocationManager) (mContext.getSystemService(Context.LOCATION_SERVICE));
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = null;
            try {
                l = lm.getLastKnownLocation(provider);
            }catch (SecurityException e){
                e.printStackTrace();
            }

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("d", connectionResult.toString());
    }

    public interface LastLocationListener{
        void onLocationReceived(Location location);
        void onLocationNotReceived();
    }
}
