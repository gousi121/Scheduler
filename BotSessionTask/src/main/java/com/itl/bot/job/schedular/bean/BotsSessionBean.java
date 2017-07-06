package com.itl.bot.job.schedular.bean;
/**
 * 
 * @author Gaurav Sharma
 * @version 1.0
 * @since 13-6-2017
 */

/**
 * The BotsSessionBean class is bean class which sets and gets the members value
 */
	public class BotsSessionBean 
	{
	    private int id;  
	    private int bot_master_id;
	    private int channel_id;
	    private String customerid;
	    private String starttime;
	    private String endtime;
	    private String	status;
    	private char isSessionActive;
    
    	/**
         * This is a getter which gets the isdeleted value
    	 * @return isdeleted The isdeleted value to be get
    	*/
    	public int getId() {
    		return id;
    	}
    	/**
         * This is a setter which sets the Template Id
    	 * @param template_id The template_id to be set
    	*/
    	public void setId(int id) {
    		this.id = id;
    	}
    	/**
         * This is a getter which gets the Bot Master Id
    	 * @return bot_master_id The bot_master_id value to be get
    	*/
    	public int getBot_master_id() {
    		return bot_master_id;
    	}
    	/**
         * This is a setter which sets the  Bot Master Id
    	 * @param bot_master_id The bot_master_id to be set
    	*/
    	public void setBot_master_id(int bot_master_id) {
    		this.bot_master_id = bot_master_id;
    	}
    	
    	/**
         * This is a getter which gets the Channel Id
    	 * @return channel_id The channel_id value to be get
    	*/
    	public int getChannel_id() {
    		return channel_id;
    	}
    	/**
         * This is a setter which sets the Channel Id
    	 * @param channel_id The channel_id to be set
    	*/
    	public void setChannel_id(int channel_id) {
    		this.channel_id = channel_id;
    	}

    	/**
         * This is a getter which gets the Customer ID
    	 * @return customerid The customerid value to be get
    	*/
    	public String getCustomerid() {
    		return customerid;
    	}
    	/**
         * This is a setter which sets the Customer Id
    	 * @param customerid The customerid to be set
    	*/
    	public void setCustomerid(String customerid) {
    		this.customerid = customerid;
    	}
    	
    	/**
         * This is a getter which gets the Start Time
    	 * @return starttime The starttime value to be get
    	*/
    	public String getStarttime() {
    		return starttime;
    	}
    	/**
         * This is a setter which sets the Start Time
    	 * @param starttime The starttime to be set
    	*/
    	public void setStarttime(String starttime) {
    		this.starttime = starttime;
    	}
    	
    	/**
         * This is a getter which gets the End time
    	 * @return endtime The endtime value to be get
    	*/
    	public String getEndtime() {
    		return endtime;
    	}
    	/**
         * This is a setter which sets the End Time
    	 * @param endtime The endtime to be set
    	*/
    	public void setEndtime(String endtime) {
    		this.endtime = endtime;
    	}
    	public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
    	/**
         * This is a getter which gets the IsSessionActive value
    	 * @return isSessionActive The isSessionActive value to be get
    	*/
		public char getIsSessionActive() {
			return isSessionActive;
		}
		public void setIsSessionActive(char isSessionActive) {
			this.isSessionActive = isSessionActive;
		}
   
    	/**
         * This is a setter which sets the IsSessionActive value
    	 * @param isSessionActive The isSessionActive to be set
    	*/
   
	

	}
