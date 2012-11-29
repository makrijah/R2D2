package com.makrijah.geotrack;

import java.io.IOException;
import java.util.ArrayList;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

/**
 * Class for a custom list activity
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class LocationListActivity extends ListActivity{

	private ArrayList<LocationItem> list;
	private Geocoder gc;
	private LocationsDbHandler db;
	private Locator locator;
	private String[] items;
	
	/**
	 * On create
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("GeoTrack- location list");
		db = new LocationsDbHandler(getApplicationContext());
		list = (ArrayList<LocationItem>) db.getAllLocationItems();
		
		gc = new Geocoder(this);
		getStringsForItems();	

		LocationListAdapter adapter = new LocationListAdapter(this, items);
		setListAdapter(adapter);
	}
	
	/**
	 * Gets a String-array for the LocationItems of the list
	 */
	private void getStringsForItems(){
		items = new String[db.getLocationItemsCount()];
		Address a;
		String s;
		int i = 0;
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
	}

	/**
	 * Method called when a list item is clicked
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {

		LocationItem li = list.get(position);
		MapIntent maps = new MapIntent(LocationListActivity.this, ShowMapActivity.class);
		maps.setLocation(true, li.getLatitude(), li.getLongitude(), li.getDate());
		startActivity(maps);
		
	}

	/**
	 * Default menu. No menu available for this activity-
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
