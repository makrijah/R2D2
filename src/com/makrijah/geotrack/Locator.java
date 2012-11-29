package com.makrijah.geotrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * The BroadcastReceiver for location updates
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class Locator extends BroadcastReceiver{
	
	private double latitude;
	private double longitude;


	/**
	 * Called when a broadcast is received
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle msg = intent.getExtras();
		if (msg!=null){
			latitude = msg.getDouble("latitude");
			longitude = msg.getDouble("longitude");
		}
	}

}
