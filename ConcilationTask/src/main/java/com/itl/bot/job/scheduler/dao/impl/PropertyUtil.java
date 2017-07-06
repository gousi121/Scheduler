package com.itl.bot.job.scheduler.dao.impl;

import java.util.ResourceBundle;

import javax.mail.Session;

/**
 * 
 * @author Gaurav Sharma
 */

/**
 * To get the property information
 */
public class PropertyUtil {
	
	private final static String PROPERTY_FILE_NAME = "connection";
	private static ResourceBundle bundle = null;
	
	/**
	 * Prop file will load only during server startup.These configuration reside in buffer for further request
	 * processing.Any change in configuration require server restart to get reflected.
	 * 
	 */
	
	static{
		load();
	}
	
	/**
	 * to Initialize the bundle
	 */
	private static void load(){
		try{
			bundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * to get single prop value
	 * @param propName
	 * @return
	 */
	public static String getValue(String propName){
		String propVal = null;
		
		return (propVal = bundle.getString(propName)) == null ? null : propVal.trim();
		
		

	}
}