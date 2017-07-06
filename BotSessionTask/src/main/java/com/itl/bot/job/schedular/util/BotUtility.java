package com.itl.bot.job.schedular.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	/**
	 * 
	 * @author Gaurav Sharma
	 * @version 1.0
	 * @since 13-6-2017
	 */
	/**
	 * The BotUtility class is a utility class which performs various utilities operation
	 */
	public class BotUtility 
	  {
		private static final Logger logger = LoggerFactory.getLogger(BotUtility.class);
		/**
		 * This method is used to find difference between two dates and return difference in seconds
		 * @param dateStart  as Start date
		 * @param dateStop	 as End date
		 * @return finaltime  It returns difference in seconds
		 */
		public long timeDiffrence(String dateStart, String dateStop)
		 {
			long finaltime = 0;
			long difference_in_Seconds;
			long difference_in_Minutes;
			long difference_in_Hours;
			long difference_in_Days;
			long diffdays2;
			long diffHours2;
			long diffMinutes2;
			long diff;
			try
			 {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1 = null;
				Date d2 = null;
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);
				
				// Find difference in milliseconds
				diff = d2.getTime() - d1.getTime();
				difference_in_Seconds = diff / 1000 % 60;
				difference_in_Minutes = diff / (60 * 1000) % 60;
				difference_in_Hours = diff / (60 * 60 * 1000) % 24;
				difference_in_Days = diff / (24 * 60 * 60 * 1000);
		 		diffMinutes2=(difference_in_Minutes*60);
		 		diffHours2=(difference_in_Hours*60*60);
		 		diffdays2=(difference_in_Days*60*60*60*24);
		 		finaltime= diffdays2+diffHours2+diffMinutes2+difference_in_Seconds;
		    }
			catch(Exception e){
				logger.error("Error while manipulating time difference "+e);
				}
			return finaltime;
	  }
		/**
		 * This method is used to get Current system date and time
		 * @return date  It returns date in "yyyy-MM-dd HH:mm:ss" format
		 */
	  public String getCurrentDateTime()
		{
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date dt = new Date();
		  return df.format(dt);
		 }	
  }
