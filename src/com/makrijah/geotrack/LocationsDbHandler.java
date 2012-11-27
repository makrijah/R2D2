
package com.makrijah.geotrack;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A database handler
 * @author makrijah
 *
 */
public class LocationsDbHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "locations";
	private static final String TABLE_LOCATIONS = "locationItems";
	private static final String KEY_ID = "id";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_DATE = "date";

	/**
	 * Constructor
	 * @param context the context associated with the object
	 */
	public LocationsDbHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * On create
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LATITUDE + " INTEGER,"
				+ KEY_LONGITUDE + " INTEGER,"
				+ KEY_DATE + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Not implemented
	}


	/**
	 * Adds a LocationItem to the database
	 * @param item LocationItem to be added
	 */
	public void addLocationItem(LocationItem item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LATITUDE, item.getLatitude());
		values.put(KEY_LONGITUDE, item.getLongitude());
		values.put(KEY_DATE, item.getDate());

		db.insert(TABLE_LOCATIONS, null, values);
		db.close(); 
	}

	/**
	 * Gets a certain LocationItem
	 * @param id the (unique) ID of the LocationItem
	 * @return the LocationItem associated with the ID
	 */
	public LocationItem getLocationItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID,
				KEY_LATITUDE, KEY_LONGITUDE,  KEY_DATE }, KEY_ID + "=?",
						new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		LocationItem contact = null;

		contact= new LocationItem(Integer.parseInt(cursor.getString(0)),
				Integer.parseInt(cursor.getString(1)), 
				Integer.parseInt(cursor.getString(2)), 
				cursor.getString(3));

		return contact;
	}


	/**
	 * Gets a list of all LocationItems in the database
	 * @return List(ArrayList)<LocationItem>
	 */
	public List<LocationItem> getAllLocationItems() {
		List<LocationItem> locations = new ArrayList<LocationItem>();

		String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				LocationItem item = new LocationItem();
				item.setID(Integer.parseInt(cursor.getString(0)));
				item.setLatitude(Double.parseDouble(cursor.getString(1)));
				item.setLongitude(Double.parseDouble(cursor.getString(2)));
				item.setDate(cursor.getString(3));
				locations.add(item);
			} while (cursor.moveToNext());
		}
		return locations;
	}


	
//	public void deleteLocationItem(LocationItem item) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
//				new String[] { String.valueOf(item.getID()) });
//		db.close();
//	}


	/**
	 * Get the overall number of entries in the database
	 * @return number of entries
	 */
	public int getLocationItemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int ret = cursor.getCount();
		cursor.close();
		return ret;
	}

}
