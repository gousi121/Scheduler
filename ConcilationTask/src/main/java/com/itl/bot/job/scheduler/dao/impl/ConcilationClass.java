package com.itl.bot.job.scheduler.dao.impl;

import java.awt.Color;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itl.bot.job.scheduler.constant.ApplicationConstant;
import com.itl.bot.job.scheduler.model.BillerDetails;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;
 
public class ConcilationClass  implements Job
	{
		private static final Logger logger = LoggerFactory.getLogger(ConcilationClass.class);
		private static final CharSequence NEW_LINE_SEPARATOR = null;
		String connect_string=PropertyUtil.getValue("connect_string");
		String username=PropertyUtil.getValue("username");
		String password=PropertyUtil.getValue("password");
		Connection connection=null;
		/*public static void main(String args[]){
			ConcilationClass obj = new ConcilationClass();
			obj.execute();
		}*/
		public void execute(JobExecutionContext context) throws JobExecutionException {
			
			String billerName=null;
			try 
			{
				Map<String, List<BillerDetails>> billerDetailsMap = new HashMap<String, List<BillerDetails>>();
				connection=getConnection();
				//PreparedStatement preparestatement1=connection.prepareStatement("SELECT bd.BILLERNAME AS 'Biller Name', bpd.SUBSCRIBERACCNO AS 'Subscriptino Account No', bpd.ACCOUNT_NAME AS 'Account Name', bpd.ATM_PHONEREFNO AS 'Phone Number', bpd.BILLAMOUNT AS 'Bill Amount', bpd.BILLMONTH AS 'Bill Month', bpd.FEES, bpd.TOTALAMOUNT AS 'Total Amount',bpd.MOBILENUMBER, bpd.EMAILADDRESS, bpd.REMARKS, bpd.TRANSACTIONDATE AS 'Collection Date', bpd.TRANSACTIONREFNO, bpd.BILLERID, bpd.RECONSTATUS, bpd.RECONDATE AS 'Reconciliation DATE', IS_ACTIVE FROM dbo.BILLERPAYMENTDETAILS bpd INNER JOIN BILLERDETAILS bd ON bpd.BILLERID = bd.UBPBILLERID WHERE convert(VARCHAR(10),bpd.TRANSACTIONDATE,103)=convert(VARCHAR(10),getdate()-3,111)");  
				PreparedStatement preparestatement1=connection.prepareStatement("SELECT bpd.SUBSCRIBERACCNO AS 'Biller Name', bpd.ACCOUNT_NAME AS 'Account Name', bpd.ATM_PHONEREFNO AS 'Phone Number', bpd.BILLAMOUNT AS 'Bill Amount', bpd.BILLMONTH AS 'Bill Month', bpd.FEES, bpd.TOTALAMOUNT AS 'Total Amount',bpd.MOBILENUMBER, bpd.EMAILADDRESS as 'Email Address', bpd.REMARKS, bpd.TRANSACTIONDATE AS 'Collection Date', bpd.TRANSACTIONREFNO, bpd.BILLERID, bpd.RECONSTATUS, bpd.RECONDATE AS 'Reconciliation DATE', IS_ACTIVE FROM dbo.BILLERPAYMENTDETAILS bpd");
				ResultSet rs1=preparestatement1.executeQuery(); 
				while(rs1.next())
				{
					BillerDetails bd = new BillerDetails();
					billerName = rs1.getString("Biller Name");
					bd.setBillAmount(rs1.getBigDecimal("Bill Amount"));
					bd.setBillerName(billerName);
					bd.setAccountName(rs1.getString("Account Name"));
					bd.setFees(rs1.getBigDecimal("FEES"));
					bd.setTotalAmount(rs1.getBigDecimal("Total Amount"));
					bd.setMobileNumber(rs1.getLong("MOBILENUMBER"));
					bd.setBillerID(rs1.getString("BILLERID"));
					bd.setBillMonth(rs1.getString("Bill Month"));
					bd.setEmailId(rs1.getString("Email Address"));
					logger.info(billerName);
					if(billerDetailsMap.get(billerName)!= null){
						List<BillerDetails> list = billerDetailsMap.get(billerName);
						list.add(bd);
					}
					else{
						List<BillerDetails> bdList = new ArrayList<BillerDetails>();
						bdList.add(bd);
						billerDetailsMap.put(billerName, bdList);
					}
		       }
				Set<Entry<String, List<BillerDetails>>> entrySet = billerDetailsMap.entrySet();
				for (Entry<String, List<BillerDetails>> entry : entrySet) 
				{
					List<BillerDetails> list = entry.getValue();
					Document document = new Document(PageSize.A4);
					File csvfile = new File("");
					File excel = new File("");
					generatePdf(document, list);
					generateCSV(csvfile,list);
					generateExcel(excel,list);
					
				}
					
		   }
			catch (FileNotFoundException e) {
				 logger.error("File not Error" +e);
			} 
			catch (DocumentException e) {
				logger.error("Document Error" +e);
			}
			catch (Exception e) {
				logger.error("Error" +e);
				e.printStackTrace();
			 }
			logger.info("******** CSV Created ***************");
			logger.info("******** PDF Created ***************");
			logger.info("******** Excel Created ***************");
			//return billerName;
	

		}
		private void generateExcel(File workbook2, List<BillerDetails> billerDetailList) {
			logger.info(" Inside Excel generation");
			try
			{
				String excelpath = null;
				String billerName = billerDetailList.get(0) != null ? billerDetailList.get(0).getBillerName() : null;
				String sendEmailTO = billerDetailList.get(0) != null ? billerDetailList.get(0).getEmailId() : null;
			
				XSSFWorkbook workbook = new XSSFWorkbook(); 
				XSSFSheet spreadsheet = workbook.createSheet(billerName);
	   			XSSFRow row;
	   			int rowid = 0;
	   			
	   			//creating headers
	   			row = spreadsheet.createRow(rowid++);
	   			Cell cellHeader;
	   			int cellidheader=0;
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Biller ID");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Biller Amount");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Biller Month");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Account Name");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Fees");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Total Amount");
	   			cellHeader = row.createCell(cellidheader++);
	   			cellHeader.setCellValue("Mobile Number");
	   			spreadsheet.autoSizeColumn(1);
	   			spreadsheet.autoSizeColumn(2);
	   			spreadsheet.autoSizeColumn(3);
	   			spreadsheet.autoSizeColumn(4);
	   			spreadsheet.autoSizeColumn(5);
	   			spreadsheet.autoSizeColumn(6);
	   			
	   			
				if(billerName != null) 
				{
					excelpath=PropertyUtil.getValue(ApplicationConstant.PDFLOCATION)+ billerName + ".xlsx";
					
					for(BillerDetails bdList : billerDetailList)
					 {
						row = spreadsheet.createRow(rowid++);
						Cell cell;
						int cellid=0;
						if(null!= bdList.getBillerID())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getBillerID())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getBillAmount())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getBillAmount())));
						}
						else{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getBillMonth())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue(((bdList.getBillMonth())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getAccountName())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getAccountName())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getFees())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getFees())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getTotalAmount())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getTotalAmount())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
						if(null!= bdList.getMobileNumber())
						{
							cell = row.createCell(cellid++);
							cell.setCellValue((String.valueOf(bdList.getMobileNumber())));
						}else
						{
							cell = row.createCell(cellid++);
							cell.setCellValue("");
						}
					 }
		   		}
				FileOutputStream out = new FileOutputStream( 
					      new File(excelpath));
					      workbook.write(out);
					     
			 out.close();
	    	 if(null != sendEmailTO){
				    	  EmailUtil emailutil = new EmailUtil();
				    	  emailutil.sendEmail(billerName, excelpath, sendEmailTO);
				      }
			}catch(Exception e)	{
				logger.info(" Error while creating Excel file"+e);
				e.printStackTrace();
			}
			
		}
		@SuppressWarnings("resource")
		public void generateCSV(File csvfile, List<BillerDetails> billerDetailList )
		{
			logger.info(" Inside CSV generation");
			final String COMMA_DELIMITER = ",";
			final String NEW_LINE_SEPARATOR = "\n";
			FileWriter fileWriter = null;

			try
			{
				String csvpath = null;
				String FILE_HEADER = "Biller ID,Bill Amount,Bill Month,Account Name,Fees,Total amount,Mobile Number";
				String billerName = billerDetailList.get(0) != null ? billerDetailList.get(0).getBillerName() : null;
				String sendEmailTO = billerDetailList.get(0) != null ? billerDetailList.get(0).getEmailId() : null;
				if(billerName != null) 
				{
					csvpath=PropertyUtil.getValue(ApplicationConstant.PDFLOCATION)+ billerName + ".csv";
					fileWriter = new FileWriter(csvpath);
					fileWriter.append("\t");
					fileWriter.append(billerName);
					fileWriter.append(NEW_LINE_SEPARATOR);
					fileWriter.append(FILE_HEADER);
					fileWriter.append(NEW_LINE_SEPARATOR);
					for(BillerDetails bdList : billerDetailList)
					 {
						if(null!= bdList.getBillerID()){
							fileWriter.append(String.valueOf(bdList.getBillerID()));
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
		        		  fileWriter.append("");
						}
						if(null != bdList.getBillAmount()){
							fileWriter.append(String.valueOf(bdList.getBillAmount()));
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
		        		  fileWriter.append("");
						}
		    	 
						if(null != bdList.getBillMonth()){
							fileWriter.append(bdList.getBillMonth());
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
							fileWriter.append("");
						}
		        	  
						if(null != bdList.getAccountName()){
							fileWriter.append(bdList.getAccountName());
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
		        		  fileWriter.append("");
						}
		        	  
						if(null != bdList.getFees()){
							fileWriter.append(String.valueOf(bdList.getBillerID()));
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
		        		  fileWriter.append("");
						}
		        	  
						if(null != bdList.getTotalAmount()){
							fileWriter.append(String.valueOf(bdList.getTotalAmount()));
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
							fileWriter.append("");
						}
		        	  
						if(null != bdList.getMobileNumber()){
							fileWriter.append(String.valueOf(bdList.getMobileNumber()));
							fileWriter.append(COMMA_DELIMITER);
						}
						else{
		        		  fileWriter.append("");
		    	      }
						fileWriter.append(NEW_LINE_SEPARATOR);

				}
			  }
				  fileWriter.close();
			 if(null != sendEmailTO){
			    	  EmailUtil emailutil = new EmailUtil();
			    	  emailutil.sendEmail(billerName, csvpath, sendEmailTO);
			      }
				
	       }
			catch (Exception e)
			{
				logger.info("Error in CsvFileWriter !!!");
	           e.printStackTrace();
			}
		}

		public void generatePdf(Document document, List<BillerDetails> billerDetailList ) throws DocumentException, FileNotFoundException, BadElementException,
			MalformedURLException, IOException, SQLException 
		{
			logger.info(" Inside document genertaion");
			try{
				Font ffont = new Font(Font.UNDEFINED, 8, Font.ITALIC );
				Font headerfont = new Font(Font.UNDEFINED, 14, Font.BOLD);
				String billerName = billerDetailList.get(0) != null ? billerDetailList.get(0).getBillerName() : null;
				String sendEmailTO = billerDetailList.get(0) != null ? billerDetailList.get(0).getEmailId() : null;
				if(billerName != null) {
					String pdfpath=PropertyUtil.getValue(ApplicationConstant.PDFLOCATION)+ billerName + ".pdf";
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfpath));
					document.open();
					PdfContentByte cb = writer.getDirectContent(); 
					Phrase header = new Phrase(billerName, headerfont);
					Phrase footer = new Phrase("Copyright �2016 � Infrasoft Technologies Ltd. All rights reserved.",ffont);
					ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
							header,
							(document.right() - document.left()) / 2 + document.leftMargin(),
							 document.top() + 10, 0);
					ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
							footer,
							(document.right() - document.left()) / 2 + document.leftMargin(),
							 document.bottom() - 10, 0);

   
					document.addCreator("Gaurav by Infrasoft");
					document.addAuthor("Gaurav Sharma");
					document.addTitle("Billing PDF By Gaurav");
		
					Image image = Image.getInstance("images/InfraBank_Logo.png");
					image.scaleAbsoluteHeight(20);
					image.scaleAbsoluteWidth(100);
					document.add(image);
    
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					Phrase phrase = new Phrase("Date : "+ sdf.format(new Date()),FontFactory.getFont(FontFactory.TIMES, 8));
					Chunk glue = new Chunk(new VerticalPositionMark());
					Paragraph p = new Paragraph("");
					p.add(new Chunk(glue));
					p.add(phrase);
					document.add(p);
    
					LineSeparator ls = new LineSeparator();
					document.add(new Chunk(ls));
	
					Font f2=new Font(Font.TIMES_ROMAN,10.0f,Font.UNDERLINE,Color.BLACK);
					Paragraph para3 = new Paragraph("Biller Fees Report",f2);
					para3.setAlignment(Paragraph.ALIGN_CENTER);
					document.add(para3);
     	
					document.add( Chunk.NEWLINE );
     
     
					PdfPTable table = new PdfPTable(7);
					table.setWidthPercentage(100);

					PdfPCell sn = new PdfPCell(new Phrase("Biller ID",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn.setBackgroundColor(Color.GRAY);
					table.addCell(sn);
      
					PdfPCell sn2 = new PdfPCell(new Phrase("Bill Amount",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn2.setBackgroundColor(Color.GRAY);
					table.addCell(sn2);
      
					PdfPCell sn3 = new PdfPCell(new Phrase(" Bill Month",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn3.setBackgroundColor(Color.GRAY);
					table.addCell(sn3);
      
					PdfPCell sn4 = new PdfPCell(new Phrase("Account Name",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn4.setBackgroundColor(Color.GRAY);
					table.addCell(sn4);
      
					PdfPCell sn5 = new PdfPCell(new Phrase("Fees",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn5.setBackgroundColor(Color.GRAY);
					table.addCell(sn5);
      
					PdfPCell sn6 = new PdfPCell(new Phrase("Total amount",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn6.setBackgroundColor(Color.GRAY);
					table.addCell(sn6);
      
					PdfPCell sn7 = new PdfPCell(new Phrase("Mobile Number",FontFactory.getFont(FontFactory.TIMES, 10)));
					sn7.setBackgroundColor(Color.GRAY);
					table.addCell(sn7);
      
					for(BillerDetails bdList : billerDetailList)
					{
						if(null!= bdList.getBillerID()){
							table.addCell(new Phrase(String.valueOf(bdList.getBillerID()), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
        	  
						if(null != bdList.getBillAmount()){
							table.addCell(new Phrase(bdList.getBillAmount().toString(), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
    	 
						if(null != bdList.getBillMonth()){
							table.addCell(new Phrase(bdList.getBillMonth().toString(), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
        	  
						if(null != bdList.getAccountName()){
							table.addCell(new Phrase(bdList.getAccountName().toString(), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
        	  
						if(null != bdList.getFees()){
							table.addCell(new Phrase(bdList.getFees().toString(), FontFactory.getFont(FontFactory.TIMES, 8)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
        	  
						if(null != bdList.getTotalAmount()){
								table.addCell(new Phrase(bdList.getTotalAmount().toString(), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
        	  
						if(null != bdList.getMobileNumber()){
							table.addCell(new Phrase(bdList.getMobileNumber().toString(), FontFactory.getFont(FontFactory.TIMES, 6)));
						}
						else{
							table.addCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES, 8)));
						}
  
					}
					document.add(table);
					document.add(new Chunk(ls));
      
					Font f=new Font(Font.TIMES_ROMAN,8.0f,Font.ITALIC,Color.BLACK);
					Paragraph para = new Paragraph("This is computer generated invoice and do not require any stamp or signature",f);
					document.add(para);
      
					Paragraph para2 = new Paragraph(PropertyUtil.getValue(ApplicationConstant.DISCLAIMER),f);
					document.add(para2);
					document.close();
      
					// Send mail
					if(null != sendEmailTO){
						EmailUtil emailutil = new EmailUtil();
						emailutil.sendEmail(billerName, pdfpath, sendEmailTO);
					}
				}
			}
			catch(Exception e){
				System.out.println(" Error in PDF creation"+ e);
			}
    	}
 
	
		private Connection getConnection()
			{
				try{
					Class.forName("net.sourceforge.jtds.jdbc.Driver");
					connection = DriverManager.getConnection(connect_string, username, password);
					}
				catch(Exception e){
					logger.error(" Error while creating connecion");
				}
				return connection;
			}
			
			
		}
		
	
	
	