package com.makrijah.geotrack;

import android.location.Location;

/**
 * Class that holds information of accessed locations
 * Latitude, longitude and date.
 * @author Markus-Kristian Ahvenus
 * @version Nov 29, 2012
 */
public class LocationItem {
 
    int _id;
    private double latitude;
    private double longitude;
    private String date;
    
    /**
     * Default constructor
     */
    public LocationItem(){}
    
    /**
     * Constructor
     * @param id Id
     * @param lat latitude
     * @param lon longitude
     * @param date date when accessed
     */
    public LocationItem(int id, double lat, double lon, String date){
    	_id = id;
    	latitude = lat;
    	longitude = lon;
    	this.date = date;
    }

    /**
     * Constructor
     * @param lat latitude
     * @param lon longitude
     * @param date date
     */
    public LocationItem(double lat, double lon, String date){
    
    	latitude = lat;
    	longitude = lon;
    	this.date = date;
    }
    
    /**
     * Constructor
     * @param id ID
     * @param location Location
     * @param date date
     */
    public LocationItem(int id, Location location, String date){
    	_id = id;
    	latitude = location.getLatitude();
    	longitude = location.getLongitude();
    	this.date = date;    	
    }

    /**
     * Constructor
     * @param location Location
     * @param date date
     */
    public LocationItem(Location location, String date){    	
    	latitude = location.getLatitude();
    	longitude = location.getLongitude();
    	this.date = date;    	
    }

    //getters and setters
    public int getID(){
        return this._id;
    }
 
    public void setID(int id){
        this._id = id;
    }
 
    public double getLatitude(){
    	return latitude;
    }
    
    public void setLatitude(double l){
    	latitude = l;
    }
    
    public void setLongitude(double l){
    	longitude = l;
    }
    
    public double getLongitude(){
    	return longitude;    	
    }
    
    public String getDate(){
    	return date;
    }
    
    public void setDate(String d){
    	date = d;
    }
    
    public String getLocationAndDate(){
    	return ""+latitude+"|"+longitude+"|"+date;
    }

}