package com.itl.bot.latilong.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	/**
	 * 
	 * @author Gaurav Sharma
	 * @version 1.0
	 * @since 13-6-2017
	 */

	/**
	 * The ConnectionClass class is used to create cooneection with database.
	 */
	public class ConnectionClass {
		private static final Logger logger = LoggerFactory.getLogger(ConnectionClass.class);
		/**
		 * This method is used to create session with hibernate
		 * @return session	It returns Session object
		 */
		public Session getConnection()
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
				logger.info(" Exception while creating session"+e);
				e.printStackTrace();
			 }
			return session;
		}
	}
