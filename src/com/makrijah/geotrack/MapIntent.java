package com.makrijah.geotrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Simple helper class; eases the creation of intents for 
 * calling the ShowMapActivity
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class MapIntent extends Intent{
	
	private Bundle parameters;
	
	/**
	 * Constructor
	 * @param context The (application) context
	 * @param c The class an instance will be called
	 */
	public MapIntent(Context context, Class<?> c){
		super(context, c);	
		parameters = new Bundle();
	}
	
	/**
	 * Sets the extras-bundle of the intent
	 * @param located Whether any actual location was acquired
	 * @param latitude latitude of the location
	 * @param longitude longitude of the location
	 * @param date date when the location was acquired
	 */
	public void setLocation(boolean located, double latitude, double longitude, String date){
		parameters.putBoolean("located", located);
		parameters.putDouble("latitude", latitude);
		parameters.putDouble("longitude", longitude);
		parameters.putString("date", date);
		this.putExtras(parameters);
	}

}
