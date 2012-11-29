package com.makrijah.geotrack;

import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * The service that provides the application with GPS location data.
 * The service is also adding new LocationItem-objects to the database.
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class LocationService extends Service implements LocationListener {

    private LocationManager locationManager;
    private LocationsDbHandler dbHandler;
    private static final int MINTIME = 300000; 
    private static final int MINDISTANCE = 500;
    private boolean providerEnabled = false;
    public static final String PROVIDERSTRING = LocationManager.GPS_PROVIDER;
    public static final String INTENT_FILTER_STRING = "com.makrijah.geotrack.positions";

    /**
     * Called when the service is started.
     */
    @Override
    public void onStart(Intent intent, int startid){
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	if (locationManager.isProviderEnabled(PROVIDERSTRING)){
    		locationManager.requestLocationUpdates(PROVIDERSTRING, MINTIME, MINDISTANCE, this);
    	}
    	else onProviderDisabled(PROVIDERSTRING);
    	dbHandler = new LocationsDbHandler(getApplicationContext());
    }
    
    /**
     * Overrides default onDestroy() 
     */
    @Override
    public void onDestroy(){
    	locationManager.removeUpdates(this);
    	super.onDestroy();
    }

    /**
     * Called when the location is changed
     */
    public void onLocationChanged(final Location location) {
    	providerEnabled = true;
        final Intent msg = new Intent(INTENT_FILTER_STRING);
        Bundle b = new Bundle();
        
        //TODO: Change date-format
        dbHandler.addLocationItem(new LocationItem(location.getLatitude(), 
        		location.getLongitude(), Calendar.getInstance().getTime().toLocaleString()));
        
        b.putDouble("latitude", location.getLatitude());
        b.putDouble("longitude", location.getLongitude());
        b.putBoolean("gpsEnabled", providerEnabled);
        b.putBoolean("gotGPS", true);
        msg.putExtras(b);
        sendBroadcast(msg);
    }

    /**
     * Called when the provider is disabled
     */
    public void onProviderDisabled(final String provider) {
    	providerEnabled = false;
    	final Intent msg = new Intent(INTENT_FILTER_STRING);
    	Bundle b = new Bundle();
    	b.putBoolean("gpsEnabled", providerEnabled);
    	msg.putExtras(b);
        sendBroadcast(msg);
    }

    /**
     * Called when provider is enabled
     */
    public void onProviderEnabled(final String provider) {
    	providerEnabled = true;
    	final Intent msg = new Intent(INTENT_FILTER_STRING);
    	Bundle b = new Bundle();
    	b.putBoolean("gpsEnabled", providerEnabled);
    	b.putBoolean("gotGPS", false);
    	msg.putExtras(b);
        sendBroadcast(msg);
    }

    /**
     * Called when the status changes
     */
    public void onStatusChanged(final String arg0, final int arg1, final Bundle arg2) {}

    /**
     * Default onBind()
     */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}