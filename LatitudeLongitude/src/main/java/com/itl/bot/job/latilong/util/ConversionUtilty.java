package com.itl.bot.job.latilong.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.bot.job.latilongi.bean.LatitudeLongiBean;
import com.itl.bot.latilong.connection.ConnectionClass;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The ConversionUtility class is used to get the complete Address from Google API using latitude and longitude values 
 * and sets these address values in database
 */
	public class ConversionUtilty {
		
		private static final Logger logger = LoggerFactory.getLogger(ConversionUtilty.class);
		private HttpURLConnection httpurlconnection;
	
		ConnectionClass connectionclassobject=null;
		int count=0;
		
		
		/**This method is used to get address from API and insert into database
		 * @param latilongibeanobject
		 * @return count The number of records updated
		 */
		public int insertAddress(LatitudeLongiBean latilongibeanobject)
			{
				Session session ;
				connectionclassobject = new ConnectionClass();
				logger.info("Updating record");
				try
				  {
				  	session=connectionclassobject.getConnection();
				  	Transaction transaction=session.beginTransaction();
				  	httpurlconnection= getGoogleMapApiHttpConnection(latilongibeanobject.getLat(),latilongibeanobject.getLng());
				  	if (httpurlconnection.getResponseCode() != 200) 
				  	 {
				  		throw new RuntimeException("Failed : HTTP error code : "+ httpurlconnection.getResponseCode());
				  	 }
				  	String formatted_address=StringToJsonConverter();
				   	setAddress(latilongibeanobject, formatted_address);
				  	transaction.commit();
				  	//System.out.println("city="+latilongibeanobject.getCity()+"State ="+latilongibeanobject.getState()+" Country= "+latilongibeanobject.getCountry()+"  Pincode ="+latilongibeanobject.getPincode()+" record successfully update for ID"+latilongibeanobject.getId());
				  				  	
				  }
				catch(Exception e){
					logger.debug("Error while inserting record "+e);
			 	 }
				httpurlconnection.disconnect();
				return count;
			} 
		
		/**
		 * This method is used to split full address into values and sets these values in database.
		 * @param latilongibeanobject  Object of LatitudeLongiBean class
		 * @param full_address Full address 
		 * @return latilongibeanobject It returns object of LatilongiBean class
		 */
		private  LatitudeLongiBean setAddress(LatitudeLongiBean latilongibeanobject, String full_address)
		 {
			String[] address=full_address.split(",");
			try
			 {
				for(String w: address)
					{  
						latilongibeanobject.setCountry((address[address.length-1]));
						String []temppin = address[address.length-2].split("\\s");
						latilongibeanobject.setPincode(temppin[temppin.length-1]);
						StringBuilder bld= new StringBuilder();
						for(int i=0;i<temppin.length-1;i++)
							{
								bld.append( "" +temppin[i]);
							}
						String state=bld.toString();
						latilongibeanobject.setState(state);
						latilongibeanobject.setCity((address[address.length-3]));
					}
				count++;
			
			 }
			catch(Exception e){
				logger.error("Error while inserting record"+e);
			 }
		return latilongibeanobject;
	}
	/**
	 * This method is used to get complete address from Google Map API using latitude and longitude value
	 * @param latitude Latitude value
	 * @param longitude Longitude Value
	 * @return httpurlconnection It returns the HttpUrl Connection Object
	 */
		private HttpURLConnection getGoogleMapApiHttpConnection(double latitude,double longitude)
			{
				try{
					 URL url = new URL(ApplicationConstant.MAP_API+latitude+","+longitude+"&sensor="+ApplicationConstant.SENSOR_VALUE_FOR_API);
					 httpurlconnection = (HttpURLConnection) url.openConnection();
					 httpurlconnection.setRequestMethod(ApplicationConstant.REQUEST_METHOD_TYPE);
					 httpurlconnection.setRequestProperty("Accept", "application/json");
					}
				 catch(Exception e){
					 logger.error("Exception in GoogleMapApiHttpConnection "+e);
				
					}
				return httpurlconnection;
			}
	
	/**
	 * This method is used to convert the given string into Jsonobject and 
	 * return the formatted address retrieved from json object
	 * @return formatted_address It returns formatted aadress in String type
	 */
	private String StringToJsonConverter()
	{
		JSONObject rec = null;
		String formatted_address = null;
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader((httpurlconnection.getInputStream())));
			String output;
			StringBuilder sbld = new StringBuilder();
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				sbld.append(output);
			 }
			String out = sbld.toString();
			JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
			JSONArray results=json.getJSONArray("results");
			rec = results.getJSONObject(0);
			formatted_address=rec.getString("formatted_address");
		}
		catch(Exception e){
			logger.error("Error while converting String to Json "+e);
			e.printStackTrace();
		}
		return  formatted_address;
	}
}
