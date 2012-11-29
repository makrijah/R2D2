package com.makrijah.geotrack;

import java.util.Calendar;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment that keeps additional functionality
 * and information.
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class OverviewFragment extends Fragment{

	private TextView latitudeField;
	private TextView longitudeField;	
	private TextView GPSText;
	private boolean gpsEnabled = false;
	private TextView gpsError;
	private MainLocator locator;
	private View view;
	private Handler timeHandler;
	private Runnable time;
	private boolean splashTried = false;
	
	/**
	 * onCreateView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){	
		if (savedInstance != null) splashTried = savedInstance.getBoolean("tried");
		if (!gpsEnabled && !splashTried) startSplash();
		view = inflater.inflate(R.layout.main_layout_overview_fragment, container, false);
		
		gpsError = (TextView) view.findViewById(R.id.gpsErrorText);
		gpsError.setText(getString(R.string.gpsNotOkText));
		latitudeField = (TextView) view.findViewById(R.id.latitudeTextfield);
		longitudeField = (TextView) view.findViewById(R.id.longitudeTextfield);
		GPSText = (TextView) view.findViewById(R.id.getGPStext);
		
		timeHandler = new Handler();				
		time = new Runnable() {			
			public void run() {
				GPSText.setText(Calendar.getInstance().getTime().toLocaleString());
				timeHandler.postDelayed(this,1000);				
			}
		};		
		timeHandler.postDelayed(time, 1000);		
		return view;
	}

	/**
	 * Keeps track if the splashScreen has already been invoked
	 * and whether GPS signal has been received.
	 */
	@Override
	public void onSaveInstanceState(Bundle bundle){
		super.onSaveInstanceState(bundle);
		bundle.putBoolean("tried", true);
	}
	
	/**
	 * Starts the SplashScreen (for result)
	 */
	private void startSplash(){
		splashTried = true;
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
				gpsError.setText(getString(R.string.gpsOkText));
			}
			else{
				gpsEnabled = false;
			}
		}
	}

	/**
	 * Private inner BroadcastReceiver for LocationService
	 * @author Markus-Kristian Ahvenus
	 * @version Nov 29, 2012
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
