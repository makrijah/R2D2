package com.makrijah.geotrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.os.Bundle;

/**
 * BroadcastReceiver
 * @author makrijah
 *
 */
public class Locator extends BroadcastReceiver{
	
	private Criteria criteria;
	private double latitude;
	private double longitude;

	public void setCriteria(Criteria criteria){
		this.criteria = criteria;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle msg = intent.getExtras();
		if (msg!=null){
			latitude = msg.getDouble("latitude");
			longitude = msg.getDouble("longitude");
		}
	}

}
