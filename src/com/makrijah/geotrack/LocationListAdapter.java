package com.makrijah.geotrack;

import java.util.StringTokenizer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom list adapter
 * @author makrijah
 *
 */
public class LocationListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	/**
	 * Constructor
	 * @param context context
	 * @param values String[]
	 */
	public LocationListAdapter(Context context, String[] values) {	  
		super(context, R.layout.listitemlayout, values);
		this.context = context;
		this.values = values;
	}



	/**
	 * Gets the requested view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listitemlayout, parent, false);
		TextView textLocation = (TextView) rowView.findViewById(R.id.location);
		TextView textDate = (TextView) rowView.findViewById(R.id.date);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		StringTokenizer st = new StringTokenizer(values[position],"|");
		textLocation.setText(st.nextToken());
		textDate.setText(st.nextToken());

		imageView.setImageResource(R.drawable.earth);
		return rowView;
	}
	
	
} 