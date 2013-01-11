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
	private TextView timeText;
	private boolean gpsEnabled = false;
	private TextView gpsStatus;
	private MainLocator locator;
	private View view;
	private Handler timeHandler;
	private Runnable time;
	private boolean splashTried = false;
	private double latitude;
	private double longitude;

	/**
	 * onCreateView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){	
		if (savedInstance != null) {
			splashTried = savedInstance.getBoolean("tried");
			gpsEnabled = savedInstance.getBoolean("gps");
			latitude = savedInstance.getDouble("latitude");
			longitude = savedInstance.getDouble("longitude");
		}
		if (!gpsEnabled && !splashTried) startSplash();

		view = inflater.inflate(R.layout.main_layout_overview_fragment, container, false);

		gpsStatus = (TextView) view.findViewById(R.id.gpsStatus);
		gpsStatus.setText(getString(R.string.gpsNotOkText));
		latitudeField = (TextView) view.findViewById(R.id.latitudeTextfield);
		longitudeField = (TextView) view.findViewById(R.id.longitudeTextfield);
		timeText = (TextView) view.findViewById(R.id.timeText);

		if (gpsEnabled) gpsStatus.setText(getString(R.string.gpsOkText));
		else gpsStatus.setText(getString(R.string.gpsNotOkText));


		latitudeField.setText(getString(R.string.latitudeText) + latitude +".");
		longitudeField.setText(getString(R.string.longitudeText) + longitude +".");

		timeHandler = new Handler();	
		time = new Runnable() {	
			public void run() {
				timeText.setText(Calendar.getInstance().getTime().toLocaleString());
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
		bundle.putBoolean("gps", gpsEnabled);
		bundle.putDouble("latitude", latitude);
		bundle.putDouble("longitude", longitude);
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
				latitude = data.getDoubleExtra("latitude", 0);
				longitude = data.getDoubleExtra("longitude", 0);
				latitudeField.setText(getString(R.string.latitudeText) + latitude +".");
				longitudeField.setText(getString(R.string.longitudeText) + longitude +".");
				gpsStatus.setText(getString(R.string.gpsOkText));
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
			if (msg!=null){
				if (msg.getBoolean("gpsEnabled")){
					if (msg.getBoolean("gotGPS")){
						latitude = msg.getDouble("latitude");
						longitude = msg.getDouble("longitude");

					}
					gpsEnabled = true;
					latitudeField.setText("Current latitude is "+latitude +".");
					longitudeField.setText("Current longitude is "+longitude +".");
					gpsStatus.setText("GPS signal is ok...");
				}
				else {	
					gpsEnabled = false;
					gpsStatus.setText("GPS not enabled/no signal..?");
					latitudeField.setText("GPS tracking is currently disabled.");
					longitudeField.setText("GPS tracking is currently disabled.");
				}
			}
		}

	}

}