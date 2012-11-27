package com.makrijah.geotrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

/**
 * Class for a custom list activity
 * @author makrijah
 *
 */
public class LocationListActivity extends ListActivity{

	private ArrayList<LocationItem> list;
	private Geocoder gc;
	private LocationsDbHandler db;
	private Locator locator;
	
	/**
	 * On create
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("GeoTrack- location list");
		db = new LocationsDbHandler(getApplicationContext());
		list = (ArrayList<LocationItem>) db.getAllLocationItems();
		String[] items = new String[db.getLocationItemsCount()];
		int i = 0;
		gc = new Geocoder(this);
		String s ="Location...";

		Address a;
		for (LocationItem item: list){
			try{
				a = gc.getFromLocation(item.getLatitude(), item.getLongitude(), 1).get(0);
				s = a.getCountryName() +", " + a.getAddressLine(0);
			
			}
			catch(IllegalArgumentException ex){
				s = "Unknown location";
			}
			catch(IOException e){
				s = "Not connected to server";
			}
		
			catch(IndexOutOfBoundsException ie){
				s = "Almost everything went wrong...";
			}
					
			items[i] = s +"|"+item.getDate();
			i++;
		}

		LocationListAdapter adapter = new LocationListAdapter(this, items);
		setListAdapter(adapter);
	}

	/**
	 * Method called when a list item is clicked
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {

		LocationItem li = list.get(position);
		
		Intent maps = new Intent(LocationListActivity.this, ShowMapActivity.class);
		Bundle params = new Bundle();
		params.putDouble("latitude", li.getLatitude());
		params.putDouble("longitude", li.getLongitude());
		params.putString("date", li.getDate());
		maps.putExtras(params);
		startActivity(maps);
		
	}

	/**
	 * Default menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_location_list, menu);
		return true;
	}

	/**
	 * Called when the activity is resumed
	 */
	public void onResume(){
		if (locator == null) locator = new Locator();
		final IntentFilter filter = new IntentFilter();
		filter.addAction("com.makrijah.geotrack.positions");
		registerReceiver(locator, filter);
		super.onResume();
	}
	
	/**
	 * Called when the activity is paused
	 */
	public void onPause(){
		unregisterReceiver(locator);
		super.onPause();
	}
}
