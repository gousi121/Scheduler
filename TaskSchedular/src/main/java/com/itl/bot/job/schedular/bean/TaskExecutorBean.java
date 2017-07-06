package com.itl.bot.job.schedular.bean;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The TaskExecutorBean class is bean class which sets and gets the members value
 */
public class TaskExecutorBean {
	private int id;
	private String year_value;
	private int month;
	private int hours;
	private int minutes;
	private int seconds;
	private String job_name;
	private String main_class_name;
	private String app_type;
	private String app_name;
	private String jar_name;
	private String day_of_month;
	private String day_of_week;
	private char isdeleted;
	private char isactive;
	
	/**
     * This is a getter which gets the isdeleted value
	 * @return isdeleted The isdeleted value to be get
	*/
	public char getIsdeleted() {
		return isdeleted;
	}
	/**
     * This is a setter which sets the Template Id
	 * @param template_id The template_id to be set
	*/
	public void setIsdeleted(char isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	/**
     * This is a getter which gets the Isactive value
	 * @return isactive The isactive value to be get
	*/
	public char getIsactive() {
		return isactive;
	}
	/**
     * This is a setter which sets the isactive value
	 * @param isactive The isactive to be set
	*/
	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}
	
	/**
     * This is a getter which gets the  ID
	 * @return id The id value to be get
	*/
	public int getId() {
		return id;
	}
	/**
     * This is a setter which sets the isactive value
	 * @param id The id to be set
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
     * This is a getter which gets the Month value
	 * @return month The month value to be get
	*/
	public int getMonth() {
		return month;
	}
	/**
     * This is a setter which sets the Month value
	 * @param month The month to be set
	*/
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
     * This is a getter which gets the Hours value
	 * @return hours The hours value to be get
	*/
	public int getHours() {
		return hours;
	}
	/**
     * This is a setter which sets the Hours Value
	 * @param hours The hours to be set
	*/
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	/**
     * This is a getter which gets the Minutes value
	 * @return minutes The minutes value to be get
	*/
	public int getMinutes() {
		return minutes;
	}
	/**
     * This is a setter which sets the Minutes value
	 * @param minutes The minutes to be set
	*/
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	
	/**
     * This is a getter which gets the seconds value
	 * @return seconds The seconds value to be get
	*/
	public int getSeconds() {
		return seconds;
	}
	/**
     * This is a setter which sets the seconds value
	 * @param seconds The seconds to be set
	*/
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	/**
     * This is a getter which gets the Job Name
	 * @return job_name The job_name value to be get
	*/
	public String getJob_name() {
		return job_name;
	}
	/**
     * This is a setter which sets the Template Id
	 * @param job_name The job_name to be set
	*/
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	/**
     * This is a getter which gets the Main Class Name
	 * @return main_class_name The main_class_name value to be get
	*/
	public String getMain_class_name() {
		return main_class_name;
	}
	/**
     * This is a setter which sets the Main Class Name
	 * @param main_class_name The main_class_name to be set
	*/
	public void setMain_class_name(String main_class_name) {
		this.main_class_name = main_class_name;
	}
	
	/**
     * This is a getter which gets the Application Type 
	 * @return app_type The app_type value to be get
	*/
	public String getApp_type() {
		return app_type;
	}
	/**
     * This is a setter which sets the Application Type 
	 * @param app_type The app_type to be set
	*/
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	
	
	/**
     * This is a getter which gets the Application Name
	 * @return app_name The app_name value to be get
	*/
		public String getApp_name() {
		return app_name;
	}
	/**
	     * This is a setter which sets the Application Name
		 * @param app_name The app_name to be set
	*/
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	
	/**
     * This is a getter which gets the jar name
	 * @return jar_name The jar_name value to be get
	*/
	public String getJar_name() {
		return jar_name;
	}
	/**
     * This is a setter which sets the Jar name
	 * @param jar_name The jar_name to be set
	*/
	public void setJar_name(String jar_name) {
		this.jar_name = jar_name;
	}
	
	/**
     * This is a getter which gets the Day of Month value
	 * @return day_of_month The day_of_month value to be get
	*/
	public String getDay_of_month() {
		return day_of_month;
	}
	/**
     * This is a setter which sets the Day of Month value
	 * @param day_of_month The day_of_month to be set
	*/
	public void setDay_of_month(String day_of_month) {
		this.day_of_month = day_of_month;
	}
	
	/**
     * This is a getter which gets the Day of Week
	 * @return day_of_week The day_of_week value to be get
	*/
	public String getDay_of_week() {
		return day_of_week;
	}
	/**
     * This is a setter which sets the the Day of Week
	 * @param day_of_week The day_of_week to be set
	*/
	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}
	
	/**
     * This is a getter which gets the Year Value
	 * @return year_value The year_value value to be get
	*/
	public String getYear_value() {
		return year_value;
	}
	/**
     * This is a setter which sets the Year Value
	 * @param year_value The year_value to be set
	*/
	public void setYear_value(String year_value) {
		this.year_value = year_value;
	}
	
	
}
