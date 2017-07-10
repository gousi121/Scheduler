package com.itl.bot.job.scheduler.dao.impl;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.bot.job.scheduler.constant.ApplicationConstant;


	public class EmailUtil {
		String billername=null;
		ReconciliationJob conobject = new ReconciliationJob();
		private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	    public void sendEmail(String billerName, String filePath, String emailId) {

        try {
        	 
	          Properties props = new Properties();
	          props.put("mail.smtp.auth", PropertyUtil.getValue(ApplicationConstant.EMAIL_SMTP_AUTH));
	          props.put("mail.smtp.starttls.enable", PropertyUtil.getValue(ApplicationConstant.EMAIL_SMTP_STARTTLS_ENABLE));
	          props.put("mail.smtp.host", PropertyUtil.getValue(ApplicationConstant.EMAIL_SMTP_SERVER));
              props.put("mail.smtp.port", PropertyUtil.getValue(ApplicationConstant.EMAIL_SMTP_SERVER_PORT));
              props.put("mail.smtp.starttls.enable",PropertyUtil.getValue(ApplicationConstant.EMAIL_SMTP_STARTTLS_ENABLE));

              Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            	  protected PasswordAuthentication getPasswordAuthentication() 
            	  {
                    return new PasswordAuthentication(PropertyUtil.getValue(ApplicationConstant.EMAIL_SERVER_USERNAME),
                    								  PropertyUtil.getValue(ApplicationConstant.EMAIL_SERVER_PASSWORD));
            	  }

	                                   });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(PropertyUtil.getValue(ApplicationConstant.EMAIL_SERVER_FROM_ADDRESS)));
            String tomail=emailId;
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tomail));
            String subject=PropertyUtil.getValue(ApplicationConstant.SUBJECT)+"- "+billerName;
            message.setSubject(subject);
            String body=PropertyUtil.getValue(ApplicationConstant.BODY);
            message.setContent("Dear "+billerName+", \n\n"+body, "text/html; charset=utf-8");
            
            
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Dear "+billerName+" , \n \n"+PropertyUtil.getValue(ApplicationConstant.BODY));

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            //String filename = PropertyUtil.getValue(ApplicationConstant.PDFLOCATION)+ billername + ".pdf";
            DataSource source = new FileDataSource(filePath);
            
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
      
           Transport.send(message);
           System.out.println(" Mail sent");

	      } catch (Exception e) {
	    	  logger.error("Exception Occured while setting up Email :: ", e);
	        }
	 	
        }
	}
	


