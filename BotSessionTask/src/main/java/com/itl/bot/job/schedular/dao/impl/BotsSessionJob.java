package com.itl.bot.job.schedular.dao.impl;
import java.util.Date;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.bot.job.schedular.bean.BotsSessionBean;
import com.itl.bot.job.schedular.util.ApplicationConstant;
import com.itl.bot.job.schedular.util.BotUtility;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The BotsSessionJob class is used to perform the timestamp opertaion and update the database
 */

	public class BotsSessionJob  implements Job
	 {
		private static final Logger logger = LoggerFactory.getLogger(BotsSessionJob.class);
		public static void main(String []args){
			/* Main method never get called*/
		
		
			
		 }
		/** This is overriden method get called by scheduler and used to perform timestamp operation
		 */
		@Override
		public void execute(JobExecutionContext arg0) throws JobExecutionException {
			
				BotsSessionJob botsessionobject = new BotsSessionJob();
				int record=0;
				BotUtility botutilityobject = new BotUtility();
				try
				 {
					Date date = new Date();
					logger.info("BotSessionJob Execution Started "+date);
					Session session= botsessionobject.getSession();
					Transaction transaction=session.beginTransaction();
					ScrollableResults sessionCursor = session.createQuery("FROM BotsSessionBean").scroll();
					int count = 0;
					while(sessionCursor .next())
					 {
						
						BotsSessionBean botsessionsbean = (BotsSessionBean) sessionCursor.get(0);
						String currentdate=botutilityobject.getCurrentDateTime();
						long timediffrence=botutilityobject.timeDiffrence(botsessionsbean.getEndtime(), currentdate);
						char result=timediffrence/60>ApplicationConstant.TIME_DIFFRENCE ?ApplicationConstant.SET_YES_VALUE: ApplicationConstant.SET_NO_VALUE;
						botsessionsbean.setIsSessionActive(result);
						session.update(botsessionsbean); 
						logger.info("Record updated Successfully for  ID "+botsessionsbean.getId() );
						record++;
						if ( ++count % ApplicationConstant.NUMBER_OF_OBJECT_PER_SESSION== 0 ) {
							session.flush();
							session.clear();
						 }
					 }
				transaction.commit();
				session.close();
		 	 }
			 catch(Exception e)
				{
				 logger.info("Error while connecting databse"+e);
				 }
			logger.info("Total updated records "+record);
				
	    }

	   /**
	    * This method is used to create session with database
	    * @return sessionobject It returns session object
	    */
			private Session getSession() 
			{
				
				Session session = null;
				try
				 {
					Configuration congiguration= new Configuration();
					congiguration.configure("/hibernate.cfg.xml");
					SessionFactory sessionfactory= congiguration.buildSessionFactory();
					session=sessionfactory.openSession();
				 }
				catch(Exception e){
					logger.info(" Exception while creating session  "+e);
					e.printStackTrace();
				 }
				return session;
			}

			
	 }


