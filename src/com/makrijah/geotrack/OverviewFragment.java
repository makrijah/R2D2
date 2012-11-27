package com.makrijah.geotrack;

import java.util.Calendar;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OverviewFragment extends Fragment{

	private TextView latitudeField;
	private TextView longitudeField;	
	private TextView GPSText;
	private boolean gpsEnabled = false;
	private TextView gpsError;
	private MainLocator locator;
	private View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){		
		if (!gpsEnabled) startSplash();
		view = inflater.inflate(R.layout.main_layout_overview_fragment, container, false);

		gpsError = (TextView) view.findViewById(R.id.gpsErrorText);
		gpsError.setText("No GPS signal received (GPS disabled?)");
		latitudeField = (TextView) view.findViewById(R.id.latitudeTextfield);
		longitudeField = (TextView) view.findViewById(R.id.longitudeTextfield);
		GPSText = (TextView) view.findViewById(R.id.getGPStext);
		GPSText.setText(Calendar.getInstance().getTime().toLocaleString());

		return view;
	}

	/**
	 * Starts the SplashScreen (for result)
	 */
	private void startSplash(){
		Intent intent = new Intent(getActivity(), SplashActivity.class);
		startActivityForResult(intent, 1);
	}

	/**
	 * Called when resuming
	 */
	@Override
	public void onResume(){
		if (locator == null) locator = new MainLocator();
		final IntentFilter filter = new IntentFilter();
		filter.addAction(LocationService.INTENT_FILTER_STRING);
		getActivity().registerReceiver(locator, filter);
		super.onResume();
	}

	/**
	 * Called when pausing
	 */
	@Override
	public void onPause(){
		getActivity().unregisterReceiver(locator);
		super.onPause();
	}

	/**
	 * Called when the called activity returns a result
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == 1){
			if (resultCode == SplashActivity.RETURN_GPS_OK){
				gpsEnabled = true;

				latitudeField.setText("Current latitude is "+data.getDoubleExtra("latitude", 0) +".");
				longitudeField.setText("Current longitude is "+data.getDoubleExtra("longitude", 0) +".");
			}
			else{
				gpsEnabled = false;
			}
		}
	}

	/**
	 * Private inner BroadcastReceiver for LocationService
	 * Can access members of outer class
	 * @author makrijah
	 *
	 */
	private class MainLocator extends Locator{

		/**
		 * Called on receiving a broadcast
		 */
		@Override
		public void onReceive(Context context, Intent intent){
			Bundle msg = intent.getExtras();
			double latitude = 0;
			double longitude = 0;
			if (msg!=null){
				if (msg.getBoolean("gpsEnabled")){
					if (msg.getBoolean("gotGPS")){
						latitude = msg.getDouble("latitude");
						longitude = msg.getDouble("longitude");

					}
					gpsEnabled = true;
					latitudeField.setText("Current latitude is "+latitude +".");
					longitudeField.setText("Current longitude is "+longitude +".");
					gpsError.setText("GPS signal is ok...");
				}
				else {					
					gpsEnabled = false;
					gpsError.setText("GPS not enabled/no signal..?");
					latitudeField.setText("GPS tracking is currently disabled.");
					longitudeField.setText("GPS tracking is currently disabled.");
				}
			}
		}

	}

}
