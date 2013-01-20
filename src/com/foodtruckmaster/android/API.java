/**
 * 
 */
package com.foodtruckmaster.android;

/**
 * @author siyusong
 *
 */
public class API {

//	private static String HOST = "http://158.130.104.180:9000";
	private static String HOST = "http://158.130.108.94:9000";
	
	public static String getNearby(String lat, String lng) {
		return String.format(HOST + "/trucks/nearby/%s/%s", lat, lng); 
	}
	
	public static String getGenre(String genre) {
		return HOST + "/trucks/" + genre;
	}
	
	public static String getTruck(String truckId) {
		return HOST + "/truck/" + truckId;
	}

	/**
	 * @param i
	 * @return
	 */
	public static String getTopTrucks(int limit) {
		return HOST + "/trucks/top/" + limit;
	}
}
