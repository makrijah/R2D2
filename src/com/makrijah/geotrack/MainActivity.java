package com.makrijah.geotrack;

import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * The main activity for Geotrack-project
 * @author makrijah
 *
 */
public class MainActivity extends FragmentActivity {

	//private Location locationNow;
	//private MainLocator locator;
	private Button goToLocationListButton;
	private Button mapButton;
	private Settings settings;
	//private LocationsDbHandler db;


	/**
	 * On create
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = (Settings) getLastCustomNonConfigurationInstance();
		if (settings==null) settings = new Settings();
		settings.started = true;
		setContentView(R.layout.activity_main);
		setTitle("GeoTrack- main");

		//db = new LocationsDbHandler(getApplicationContext());
		addButtons();
	}

	/**
	 * override
	 */
	@Override
	public Object onRetainCustomNonConfigurationInstance(){
		return settings;
	}

	/**
	 * Method for adding buttons
	 */
	private void addButtons(){

		goToLocationListButton = (Button) findViewById(R.id.goToLocationListButton);
		goToLocationListButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent locationListIntent = new Intent(MainActivity.this, LocationListActivity.class);
				MainActivity.this.startActivity(locationListIntent);
			}
		});

		mapButton = (Button) findViewById(R.id.goToMapButton);
		mapButton.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				double lat = 0;
				double lon = 0;
				//List<LocationItem> locations = db.getAllLocationItems();
				//LocationItem item = locations.get(locations.size());
				//if (locationNow != null){
				//	lat = locationNow.getLatitude();
				//	lon = locationNow.getLongitude();
				//}
				//if (item != null){
				//	lat = item.getLatitude();
				//	lon = item.getLongitude();
				//}
				Bundle params = new Bundle();
				params.putDouble("latitude", lat);
				params.putDouble("longitude", lon);
				params.putString("date", Calendar.getInstance().getTime().toLocaleString());
				Intent maps = new Intent(MainActivity.this, ShowMapActivity.class);
				//maps.putExtras(params);
				MainActivity.this.startActivity(maps);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.main_menu, menu);
		return true;
	}

	/**
	 * Menu items
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		case R.id.menu_exit: 
			stopService(new Intent(getApplicationContext(), LocationService.class));
			this.finish();
			return true;
		case R.id.menu_about: showAboutDialog();
		return true;
		}
		return false;

	}

	/**
	 * Dialog for "about"
	 */
	private void showAboutDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.about_dialog_title);
		builder.setMessage(R.string.about_dialog_text)
		.setCancelable(true)
		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	//	/**
	//	 * Called when the called activity returns a result
	//	 */
	//	protected void onActivityResult(int requestCode, int resultCode, Intent data){
	//		if (requestCode == 1){
	//			if (resultCode == SplashActivity.RETURN_GPS_OK){
	//				gpsEnabled = true;
	//			}
	//			else{
	//				gpsEnabled = false;
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * Registers the GPS LocationService BroadcastReceiver 
	//	 */
	//	private void registerGPS(){
	//		if (locator == null) locator = new MainLocator();
	//		final IntentFilter filter = new IntentFilter();
	//		filter.addAction(LocationService.INTENT_FILTER_STRING);
	//		registerReceiver(locator, filter);
	//	}

	/**
	 * Called onResume()
	 */
	@Override
	public void onResume(){
		super.onResume();
	}

	/**
	 * Called onPause()
	 */
	@Override
	public void onPause(){
		super.onPause();
	}

	//
	//	/**
	//	 * Private inner BroadcastReceiver for LocationService
	//	 * Can access members of outer class
	//	 * @author makrijah
	//	 *
	//	 */
	//	private class MainLocator extends Locator{
	//
	//		/**
	//		 * Called on receiving a broadcast
	//		 */
	//		@Override
	//		public void onReceive(Context context, Intent intent){
	//			Bundle msg = intent.getExtras();
	//			double latitude = 0;
	//			double longitude = 0;
	//			if (msg!=null){
	//				if (msg.getBoolean("gpsEnabled")){
	//					latitude = msg.getDouble("latitude");
	//					longitude = msg.getDouble("longitude");
	//					gpsEnabled = true;
	//					//latitudeField.setText("Current latitude is "+latitude +".");
	//					//longitudeField.setText("Current longitude is "+longitude +".");
	//					//gpsError.setVisibility(View.INVISIBLE);
	//				}
	//				else {					
	//					gpsEnabled = false;
	//					//gpsError.setVisibility(View.VISIBLE);
	//					//latitudeField.setText("GPS tracking is currently disabled.");
	//					//longitudeField.setText("GPS tracking is currently disabled.");
	//				}
	//			}
	//		}
	//
	//	}

	/**
	 * Inner class for storage of configuration settings
	 * @author makrijah
	 *
	 */
	private class Settings{
		public boolean started = false;
	}

}