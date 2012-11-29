package com.makrijah.geotrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * The fragment that includes the buttons of the main activity
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class ButtonsFragment extends Fragment{
	
	private Button goToLocationListButton;
	private Button mapButton;
	private View view;
	private Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
		view = inflater.inflate(R.layout.main_buttons_fragment, container, false);
		activity = getActivity();
		addButtons();
				
		return view;
	}
	

	/**
	 * Method for adding (the two) buttons
	 */
	private void addButtons(){

		goToLocationListButton = (Button) view.findViewById(R.id.goToLocationListButton);
		goToLocationListButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent locationListIntent = new Intent(activity.getApplicationContext(), LocationListActivity.class);
				activity.startActivity(locationListIntent);
			}
		});

		mapButton = (Button) view.findViewById(R.id.goToMapButton);
		mapButton.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View v) {
				//Intent maps = new Intent(activity.getApplicationContext(), ShowMapActivity.class);
				MapIntent maps = new MapIntent(activity.getApplicationContext(), ShowMapActivity.class);
				maps.setLocation(false, 0, 0, "");
				activity.startActivity(maps);
			}
		});

	}

}
