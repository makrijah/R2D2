package com.makrijah.geotrack;

import java.util.Calendar;
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

	private Button goToLocationListButton;
	private Button mapButton;
	private Settings settings;

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
				Intent maps = new Intent(MainActivity.this, ShowMapActivity.class);
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

	/**
	 * Inner class for storage of configuration settings
	 * @author makrijah
	 *
	 */
	private class Settings{
		public boolean started = false;
	}

}