package com.makrijah.geotrack;
//
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.ArrayList;
//
//public class LocationListItems {
//	
//	private ArrayList<LocationListItem> items = new ArrayList<LocationListItem>();
//	private static LocationListItems instance;
//	private File target;
//
//	
//	public static LocationListItems getInstance(){
//		if (instance == null) {
//			instance = new LocationListItems();
//			
//		}
//		return instance;
//	}
//
//	
//	public void addLocationListItem(LocationListItem item, File file){
//		items.add(item);
//		target = file;
//		writeToFile(item, file);
//	}
//	
//	public String[] getStrings(){
//		int i = 0;
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(target);
//			DataInputStream dis = new DataInputStream(fis);
//			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
//			String line;
//			while ((line = br.readLine()) != null) {
//				  // Print the content on the console
//				  System.out.println (line);
//				  i++;
//				  }
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private synchronized void writeToFile(LocationListItem item, File file){
//		try {
//			OutputStream os = new FileOutputStream(file, true);
//			String s = ""+item.getLatitude()+item.getLongitude()+"|"+item.getDate()+"\n";
//			os.write(s.getBytes());
//			os.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//}
