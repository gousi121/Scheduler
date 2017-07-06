package com.itl.bot.job.latilongi.dao.impl;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.bot.job.latilongi.bean.LatitudeLongiBean;
import com.itl.bot.job.latilong.util.ApplicationConstant;
import com.itl.bot.job.latilong.util.ConversionUtilty;
import com.itl.bot.latilong.connection.ConnectionClass;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The Latitudelongitude class is used to get address values from Map API and update these values in database
 */
   public class Latitudelongitude implements Job   {
	   
		public static void main(String[] args) {
			/* Main method never get called*/
			
		}
		private Logger logger;
		public Latitudelongitude(){
			   logger = LoggerFactory.getLogger(Latitudelongitude.class);
		}
		
			/** This is overriden method get called by scheduler and used to retrieve completed address from Map API using latitude and longitude 
			 * 	and update in database*/
		
		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
				int record=0;
				Session session=null;
				try 
				 {
					ConnectionClass connectionclassobject = new ConnectionClass();
					ConversionUtilty conversionutlityobject = new ConversionUtilty();
					session=connectionclassobject.getConnection();
					Transaction transaction=session.beginTransaction();
					ScrollableResults sessionCursor = session.createQuery("from LatitudeLongiBean lb where  city='' OR city IS NULL OR country=''OR country IS NULL OR pincode='' OR pincode IS NULL OR state='' OR state IS NULL ").scroll();
					int count = 0;
					
					while(sessionCursor.next())
					 {
						 LatitudeLongiBean latilongibeanobject = (LatitudeLongiBean) sessionCursor.get(0);
						 record =conversionutlityobject.insertAddress(latilongibeanobject);
							
						 if(++count % ApplicationConstant.NUMBER_OF_OBJECT_PER_SESSION == 0 ) {
							session.flush();
							session.clear();
						  }
					 }
					transaction.commit();
					session.close();
					logger.info("Total  records updated = "+record);
					//System.out.println("Total  records updated = "+record);
					
					
				 } 
				catch(Exception e){
					logger.info("Exception while retrieving data from table "+e);
					//e.printStackTrace();
			
				}
			}
			
   		} 
