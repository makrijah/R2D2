package com.makrijah.geotrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * The map activity
 * @author makrijah
 *
 */
public class ShowMapActivity extends MapActivity{

	private String loc ="not null";
	private MapController controller;
	private double latitude;
	private double longitude;
	private String date = "";
	private Geocoder gc;
	private LocationsDbHandler dbHandler;
	private Locator locator;
	private List<Overlay> mapOverlays;
	private LocationItemOverlay itemizedoverlay;


	/**
	 * Extracts fields from the given bundle
	 * @param bundle Bundle as parameter to activity
	 */
	private void unbundle(Bundle bundle){
		if (bundle != null){
			latitude = bundle.getDouble("latitude", 0);
			longitude = bundle.getDouble("longitude", 0);
			if (bundle.get("date") != null)
				date = bundle.getString("date");
		}
	}

	/**
	 * Adds geopoints to overlays
	 */
	private void setGeopoints(){
		GeoPoint gp; 
		OverlayItem oi;

		for (LocationItem item: dbHandler.getAllLocationItems()){
			gp = new GeoPoint((int) (item.getLatitude() * 1E6),(int) (item.getLongitude() * 1E6));
			ArrayList<Address> locs;
			String s ="";
			try {
				locs = (ArrayList<Address>) gc.getFromLocation(latitude, longitude, 1);
				s = locs.get(0).getCountryName() + ", " + locs.get(0).getAddressLine(0);
			} catch (IOException e) {
				e.printStackTrace();
			}

			oi = new OverlayItem(gp, s, item.getDate());
			itemizedoverlay.addOverlay(oi);
			mapOverlays.add(itemizedoverlay);
		}
	}

	/**
	 * on create
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setTitle("GeoTrack- map view");
		unbundle(getIntent().getExtras());

		gc = new Geocoder(this);
		try {
			ArrayList<Address> locations = (ArrayList<Address>) gc.getFromLocation(latitude, longitude, 1);
			loc = locations.get(0).getCountryName() + ", " + locations.get(0).getAddressLine(0);

		}
		catch(IndexOutOfBoundsException i){
			loc = "No address found...";
		}
		catch(IllegalArgumentException e){
			loc = "Unknown location";
		}		
		catch (IOException e) {			
			loc = "IOException";			
		}
		dbHandler = new LocationsDbHandler(getApplicationContext());

		setContentView(R.layout.activity_show_map);
		final Button back = (Button) findViewById(R.id.goBackFromMapButton);
		back.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				ShowMapActivity.this.finish();				
			}
		});

		MapView mapView = (MapView) findViewById(R.id.mapView);

		mapView.setBuiltInZoomControls(true);

		controller = mapView.getController();

		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		mapOverlays = mapView.getOverlays();
		itemizedoverlay = new LocationItemOverlay(drawable, this);
		setGeopoints();

		Drawable d = this.getResources().getDrawable(R.drawable.location);
		LocationItemOverlay lio = new LocationItemOverlay(d, this);

		GeoPoint point = new GeoPoint((int) (latitude * 1E6),(int) (longitude * 1E6));

		OverlayItem overlayitem = new OverlayItem(point, loc, date);
		controller.animateTo(point);

		lio.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		mapOverlays.add(lio);        

	}

	/**
	 * Default menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_show_map, menu);
		return true;
	}

	/**
	 * No route is displayed
	 */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
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
