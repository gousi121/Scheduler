package com.itl.bot.job.scheduler.TaskScheduler;

import java.text.ParseException;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.itl.bot.job.scheduler.bean.ApplicationConstant;
import com.itl.bot.job.scheduler.bean.TaskExecutorBean;

	/**
	 * The TaskExecutor class is used to schedules the jobs and trigger them to run at given specified time
	 */
	public class TaskExecutor {
		private static final Logger logger = LogManager.getLogger(TaskExecutor.class.getName());
		public static void main(String[] args) throws ParseException {
		TaskExecutor obj = new TaskExecutor();
		obj.triggerJob();
	   }
	
		
	/** This method is used to schedule the number of jobs and trigger them to run at specified time 
	 * */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void triggerJob() throws ParseException  {
    	   TaskExecutorBean taskexecutorbean = null;
    	   TaskExecutor taskexecutorobject = new TaskExecutor();
    	   ClassLoader classLoader=null;
    	   Class clas=null;
    	   try 
    	   	{
    		  
    		   	SchedulerFactory schedulerfactory = new StdSchedulerFactory();
    		   	Scheduler scheduler = schedulerfactory.getScheduler();
    		   	Session session=taskexecutorobject.getConnection();
    		   	ScrollableResults sessionCursor = session.createQuery("FROM TaskExecutorBean").scroll();
    		   	int count = 0;
    		   	while(sessionCursor .next())
    		   	 {
    		   		
    		   			
    		   			taskexecutorbean = (TaskExecutorBean) sessionCursor.get(0);
    		   			classLoader = TaskExecutor.class.getClassLoader();
    		   			clas=classLoader.loadClass(taskexecutorbean.getMain_class_name());
    		   			System.out.println(" "+clas.getSimpleName()+ " class  Loaded ");
    		   			logger.info(" "+clas.getSimpleName()+ " class  Loaded ");
    		   			if(taskexecutorbean.getIsactive()==ApplicationConstant.YES_VALUE && taskexecutorbean.getIsdeleted()==ApplicationConstant.NO_VALUE)
    		   				{
    		   					JobDetail job = JobBuilder.newJob(clas)
    		   							.withIdentity(taskexecutorbean.getApp_name(), "newGroup")
    		   							.build();
	 
    		   					Trigger trigger = TriggerBuilder.newTrigger()
    		   							.withIdentity(taskexecutorbean.getApp_name(), "group2")
    		   							.forJob(job)
    		   							.withSchedule(CronScheduleBuilder.cronSchedule(taskexecutorbean.getSeconds()+" "+taskexecutorbean.getMinutes()+" "+taskexecutorbean.getHours()+" "+taskexecutorbean.getDay_of_month()+" "+taskexecutorbean.getMonth()+" "+taskexecutorbean.getDay_of_week()+" "+taskexecutorbean.getYear_value()))
    		   							.build();
	 
    		   					scheduler.scheduleJob(job, trigger);
    		   					try{
    		   						scheduler.start();
    		   					}catch(Exception e){
    		   						e.printStackTrace();
    		   						logger.error(" Error while triggering job "+ e);
    		   					}
    		   					
    		   					System.out.println(" "+taskexecutorbean.getJob_name()+"job trigeered successfully");
    		   					logger.info(" "+taskexecutorbean.getJob_name()+"job trigeered successfully");
    		   				}
    		   			else{
    		   				System.out.println(" Unable to start "+taskexecutorbean.getJob_name()+ " job either job is deleted or it is InActive");
    		   				logger.info(" Unable to start "+taskexecutorbean.getJob_name()+ " job either job is deleted or it is InActive");
    		   			}	 
    		   	 }
    		 
    		   	if ( ++count % ApplicationConstant.NUMBER_OF_OBJECT_PER_SESSION  == 0) {
			      session.flush();
			      session.clear();
    		   		}
     	   	}
    	   catch (SchedulerException e)
        	{
    		  logger.error("  Error while triggering job "+e);
    		  
        	} 
    	   catch (ClassNotFoundException e) {
    		   logger.error("  Error while loading class" +e);
    		   
		}
         }
       
    	   
    	   /** 
    	    * This method is used to creation the session with database and return connection object
    	    * @return connectionobject
    	    */
       private Session getConnection()
   		{
    	   Session session = null;
    	   try
    	   {
   				Configuration configuration= new Configuration();
   				 configuration.configure("/hibernate.cfg.xml");
   				 SessionFactory sessionfactory = configuration.buildSessionFactory();
   		         session = sessionfactory.openSession();
   	   		}
			catch(Exception e){
				logger.error(" Error in creating connction"+e);
				System.out.println(" Error while creating sesions"+e);
				e.printStackTrace();
			}
    	
		return session;
   		}
	}