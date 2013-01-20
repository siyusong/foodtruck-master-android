/**
 * 
 */
package com.foodtruckmaster.android.model;

/**
 * @author siyusong
 *
 */
public class Truck {
	private String id;
	private String name;
	private double[] location;
	private String address;
	private String phone;
	private String genre;
	private String photo;
	private double averageStar;
	private int reviewCount;
	private Review[] reviews;
	
	public Truck() {
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the location
	 */
	public double[] getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(double[] location) {
		this.location = location;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	
	/**
	 * @return the averageStar
	 */
	public double getAverageStar() {
		return averageStar;
	}
	
	/**
	 * @param averageStar the averageStar to set
	 */
	public void setAverageStar(Double averageStar) {
		this.averageStar = averageStar;
	}
	
	/**
	 * @return the reviewCount
	 */
	public int getReviewCount() {
		return reviewCount;
	}
	
	/**
	 * @param reviewCount the reviewCount to set
	 */
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the reviews
	 */
	public Review[] getReviews() {
		return reviews;
	}
}
