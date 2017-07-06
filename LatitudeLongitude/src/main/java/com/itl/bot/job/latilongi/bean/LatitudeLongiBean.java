package com.itl.bot.job.latilongi.bean;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The LatitudeLongiBean class is bean class which sets and gets the members value
 */
public class LatitudeLongiBean {
    private int id;
	private double lng;
	private double lat;
	private String status;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pincode;
	
	/**
     * This is a getter which gets the isdeleted value
	 * @return isdeleted The isdeleted value to be get
	*/
	public int getId() {
		return id;
	}
	/**
     * This is a setter which sets the Id
	 * @param id The id to be set
	*/
	public void setId(int id) {
		this.id = id;
	}
	/**
     * This is a getter which gets the Longitude
	 * @return lng The lng value to be get
	*/
	public double getLng() {
		return lng;
	}
	/**
     * This is a setter which sets the the Longitude
	 * @param lng The lng to be set
	*/
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	/**
     * This is a getter which gets Latitude
	 * @return lat The lat value to be get
	*/
	public double getLat() {
		return lat;
	}
	/**
     * This is a setter which sets the Latitude
	 * @param lat The lat to be set
	*/
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	/**
     * This is a getter which gets the streets
	 * @return street The street value to be get
	*/
	public String getStreet() {
		return street;
	}
	/**
     * This is a setter which sets the street
	 * @param street The street to be set
	*/
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
     * This is a getter which gets the City
	 * @return city The city value to be get
	*/
	public String getCity() {
		return city;
	}
	/**
     * This is a setter which sets the City
	 * @param city The city to be set
	*/
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
     * This is a getter which gets the State
	 * @return state The state value to be get
	*/
	public String getState() {
		return state;
	}
	/**
     * This is a setter which sets the state
	 * @param state The state to be set
	*/
	public void setState(String state) {
		this.state = state;
	}
	
	/**
     * This is a getter which gets the Country 
	 * @return country The country value to be get
	*/
	public String getCountry() {
		return country;
	}
	/**
     * This is a setter which sets the Country
	 * @param country The country to be set
	*/
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
     * This is a getter which gets the Pincode
	 * @return pincode The pincode value to be get
	*/
	public String getPincode() {
		return pincode;
	}
	/**
     * This is a setter which sets the Pincode
	 * @param pincode The pincode to be set
	*/
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
 }

