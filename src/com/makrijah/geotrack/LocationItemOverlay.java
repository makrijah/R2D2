package com.makrijah.geotrack;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * Custom class for Overlay
 * @author makrijah
 *
 */
@SuppressWarnings("rawtypes")
public class LocationItemOverlay extends ItemizedOverlay{
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	/**
	 * Constructor
	 * @param arg0 Drawable associated to the overlay
	 */
	public LocationItemOverlay(Drawable arg0) {
		super(boundCenterBottom(arg0));
	}

	/**
	 * Constructor
	 * @param defaultMarker
	 * @param context
	 */
	public LocationItemOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		}
	
	/**
	 * Adds an overlay
	 * @param overlay overlay to be added
	 */
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlays.get(arg0);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	  builder.setTitle(item.getTitle());
	  builder.setMessage(item.getSnippet()).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
	});
	  AlertDialog dialog = builder.create();
	  dialog.show();
	  return true;
	}

}
