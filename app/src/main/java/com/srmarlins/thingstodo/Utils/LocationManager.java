package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

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
    private LatLng mLatLng;

    private LocationManager(Context context, LastLocationListener listener){
        mContext = context;
        mListener = listener;
        buildGoogleApiClient();
    }

    public static LocationManager getInstance(Context context, LastLocationListener listener){
        if(listener == null){
            return null;
        }
        if(mManagerInstance == null){
            mManagerInstance = new LocationManager(context, listener);
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

    public static void writeLatLngToPrefs(SharedPreferences sharedPreferences, Location location){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LATITUDE, Double.toString(location.getLatitude()));
        editor.putString(LONGITUDE, Double.toString(location.getLongitude()));
        editor.apply();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation= LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mListener.onLocationReceived(mLatLng);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface LastLocationListener{
        LatLng onLocationReceived(LatLng latLng);
    }
}
