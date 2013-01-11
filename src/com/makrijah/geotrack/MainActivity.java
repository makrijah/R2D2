package com.makrijah.geotrack;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The main activity for Geotrack-project.
 * It contains the two fragments and the "menu"
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class MainActivity extends FragmentActivity{

	private Settings settings;

	/**
	 * On create (override)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		settings = (Settings) getLastCustomNonConfigurationInstance();
		if (settings==null) settings = new Settings();
		settings.started = true;
		setContentView(R.layout.activity_main);
		setTitle("GeoTrack- main");
	}

	/**
	 * Keeps the configuration
	 */
	@Override
	public Object onRetainCustomNonConfigurationInstance(){
		return settings;
	}

	/**
	 * Inflates the menu
	 * @param menu The menu to be inflated
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.main_menu, menu);
		return true;
	}

	/**
	 * Menu items
	 * @param item Which item is selected
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
	 * Dialog for "about". Shows basic information about the application.
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
	 * @author Markus-Kristian Ahvenus
	 * @version Nov 29, 2012
	 */
	private class Settings{
		public boolean started = false;
	}

}