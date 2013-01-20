/**
 * 
 */
package com.foodtruckmaster.android.model;

/**
 * @author siyusong
 *
 */
public class Review {

	public static String BLACK_STAR = "\u2605";
	public static String WHITE_STAR = "\u2606";

	private String id;
	private long timestamp;
	private String userId;
	private String name;
	private String comments;
	private int star;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @return the star
	 */
	public int getStar() {
		return star;
	}
	
	public static String getStarString(double stars) {
		double wholeStars = Math.floor(stars);
		String starString = new String(new char[(int) wholeStars]).replace(
				"\0", BLACK_STAR);
		if (Double.compare(stars - wholeStars, 0) != 0) {
			starString += WHITE_STAR;
		}
		return starString;
	}

}
