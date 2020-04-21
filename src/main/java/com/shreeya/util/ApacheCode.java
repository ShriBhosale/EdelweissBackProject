package com.shreeya.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class ApacheCode {

	static XSSFWorkbook workbook;
	static Workbook wb;
	static FileOutputStream out;
	static XSSFSheet sheet;
	static Sheet outputsheet;
	public static FileOutputStream fileOut=null;
	public static boolean closeOutputExcel=false;

	HelperCode helper=new HelperCode();
	

	public ApacheCode(String folderPathString) throws EncryptedDocumentException, IOException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Orders Details");
		out = new FileOutputStream(new File(folderPathString+"/ExcelReport"+helper.timeStampGenerator()+".xlsx"),true);
		String[] headerArray = { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
				"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id","Qty","Partial Qty","Rejection Reason",
				"ScriptResult Pass/fail", "Report link", "Screenshot link" };

		Row row = sheet.createRow(0);
		for (int i = 0; i < headerArray.length; i++) {

			Cell cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
		}
		

		 
	}
	


	public ApacheCode() {
		
	}

	public void fileCopyOutputWrite(String folderPathString) throws IOException {
		FileInputStream file = new FileInputStream(new File(folderPathString)); 
		  
        // Create Workbook instance holding reference to .xlsx file 
         workbook = new XSSFWorkbook(file); 

        // Get first/desired sheet from the workbook 
        XSSFSheet sheet = workbook.getSheetAt(0);
		String[] headerArray = {"Rejection Reason",
				"ScriptResult Pass/fail", "Report link", "Screenshot link" };

		Row row = sheet.getRow(0);
		for (int i = 15; i < headerArray.length; i++) {

			Cell cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
		}

	}
	

	public void excelFileCreator() throws FileNotFoundException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Orders Details");
		out = new FileOutputStream(new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\demo1.xlsx"));

	}

	public void closeExcelWriting() {
		try {
			// this Writes the workbook gfgcontribute

			workbook.write(out);
			out.close();
			Reporter.log("Close excel file.",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ExcelWriter() {

	}


	public void excelWriter(String[] orderDetails,int rowNo) {
		String hyperLinkName="hyperLinkName not found";
		Row row = sheet.createRow(rowNo);
		
		for(int i=0;i<orderDetails.length;i++) {
		Cell cell = row.createCell(i);

		
		if(i==16||i==17) {
		 if(i==16)
			 hyperLinkName="HtmlReport";
		 else if(i==17)
			 hyperLinkName="Screenshot";
		 cell.setCellValue(hyperLinkName);
		 Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			Reporter.log("HyperLink Path ======> "+pathStrProcces(orderDetails[i]),true);
		 href.setAddress(pathStrProcces(orderDetails[i]));
			cell.setHyperlink(href);
		}else {
			cell.setCellValue(orderDetails[i]);
		}
		}
	}
	
	public String pathStrProcces(String pathStr) {
		String str=null;
		char [] c=pathStr.toCharArray();
		/*for(int i=0;i<pathStr.length();i++) {
			Reporter.log(c[i]+" count "+i);
		}*/
		
		String abc=pathStr.substring(11);
		
		abc=".."+abc;
		Reporter.log(abc,true);
		return abc;

	}

	public static void main(String[] args) {
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Orders Details");
		// This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		data.put("2", new Object[] { 1, "Pankaj", "Kumar" });
		data.put("3", new Object[] { 2, "Prakashni", "Yadav" });
		data.put("4", new Object[] { 3, "Ayan", "Mondal" });
		data.put("5", new Object[] { 4, "Virat", "kohli" });

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet.createRow(1);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			/*
			 * for (Object obj : objArr) { // this line creates a cell in the next column of
			 * that row Cell cell = row.createCell(cellnum++); if (obj instanceof String)
			 * cell.setCellValue((String)obj); else if (obj instanceof Integer)
			 * cell.setCellValue((Integer)obj); }
			 */
			Cell cell = row.createCell(2);
			cell.setCellValue("report");

			Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			href.setAddress("file:///E://EdelweissProject//Reports//Report1584943724486.html");
			cell.setHyperlink(href);

			Cell cell1 = row.createCell(3);
			cell1.setCellValue("Screenshot");

			Hyperlink href1 = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			href1.setAddress("file:///E://EdelweissProject//Reports//ScreenShot//1584629156634.png");
			cell1.setHyperlink(href1);
		}
		try {
			// this Writes the workbook gfgcontribute
			FileOutputStream out = new FileOutputStream(
					new File("E:\\EdelweissProject\\Reports\\ReportInExcel\\demo1.xlsx"));
			workbook.write(out);
			out.close();
			Reporter.log("gfgcontribute.xlsx written successfully on disk.",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excelWriter1(String[] orderDetails,int rowNo) {
		String hyperLinkName="hyperLinkName not found";
		Row row = sheet.createRow(rowNo);
		
		for(int i=0;i<orderDetails.length;i++) {
		Cell cell = row.createCell(i);

		
		if(i==0||i==1) {
		 if(i==0)
			 hyperLinkName="HtmlReport";
		 else if(i==1)
			 hyperLinkName="Screenshot";
		 cell.setCellValue(hyperLinkName);
		 Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
		 /*if(i==10) {*/
		
			href.setAddress(removeReportStr(orderDetails[i]));
		/*	
		 }else {
			 href.setAddress("file:///"+pathStrProcces(orderDetails[i]));
		 }*/
		 cell.setHyperlink(href);
		}else {
			cell.setCellValue(orderDetails[i]);
		}
		}
	
	}
	
	public String removeReportStr(String reportPath) {
		String output=null;
		String [] reportArray=reportPath.split("/");
		for(String str:reportArray) {
			if(!str.contains("Report")) {
				output=str+"/";
			}
			
			
		}
		return output;
	}
	
	public void excelFile() throws FileNotFoundException {

		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Orders Details");
		out = new FileOutputStream(new File("../ReportABC/excelFile"+".xlsx"),true);
		/*String[] headerArray = { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
				"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id","Qty","Partial Qty","Rejection Reason",
				"ScriptResult Pass/fail", "Report link", "Screenshot link" };

		Row row = sheet.createRow(0);
		for (int i = 0; i < headerArray.length; i++) {

			Cell cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
		}*/
	
	}
	
	 public void outputFileWriterHeader(String folderPathString) throws IOException {
		 int counter=15;
		 Reporter.log("Start Write in output excel file",true);
		 InputStream inp = new FileInputStream(folderPathString+"/OutputFile.xlsx");
			String[] headerArray = {"Rejection Reason",
					"ScriptResult Pass/fail", "Report link", "Screenshot link" };
			wb= WorkbookFactory.create(inp);
		
			 outputsheet = wb.getSheetAt(0);
			Row row = outputsheet.getRow(0);
			for(int i=0;i<headerArray.length;i++)
			{
			Cell cell = row.getCell(counter);
			
			if (cell == null)
			    cell = row.createCell(counter);
			
			cell.setCellValue(headerArray[i]);
			 
			counter++;
			} 
			//return fileOut;
	 }
	 
	 public void outputFileWriter(String [] orderDetailArray,int rowNo) throws IOException {
		 try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 Reporter.log("Writer Order detail with link in outputExcel",true);
		 int counter=15;
		 Reporter.log("after count variable "+counter,true);
		 String hyperLinkName = null;
		 Reporter.log("hyper link Name "+hyperLinkName,true);
		 Reporter.log("OutputSheet object ==========>>> "+outputsheet,true);
			Row row = outputsheet.getRow(rowNo);
			 Reporter.log("After row "+row,true);
			if(outputsheet==null)
				Reporter.log("Null=====================================================================$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44>>>>>>");
			Reporter.log("Row Object ====> "+row+"\nOutputSheet Object ====> "+outputsheet,true);
			for(int i=14;i<orderDetailArray.length;i++)
			{
			Cell cell = row.getCell(counter);
			
			if (cell == null)
			    cell = row.createCell(counter);
			
			 if(i==16||i==17) {
				 
				if(i==16)
					 hyperLinkName="HtmlReport";
				 else if(i==17)
					 hyperLinkName="Screenshot";
				 cell.setCellValue(hyperLinkName);
				 Hyperlink href = wb.getCreationHelper().createHyperlink(HyperlinkType.URL);
					Reporter.log("HyperLink Path ======> "+pathStrProcces(orderDetailArray[i]),true);
				 href.setAddress(pathStrProcces(orderDetailArray[i]));
					cell.setHyperlink(href);
				}else {
					
					cell.setCellValue(orderDetailArray[i]);
					Reporter.log(orderDetailArray[i],true);
				}
			 
			 //wb.write(fileOut);
			 
			
			counter++;
			}
			
	 }
	 public void outputExcelFileClose(String folderPathString) throws IOException {
		 if(closeOutputExcel==false) {
			fileOut = new FileOutputStream(folderPathString+"/OutputFile.xlsx");
			Reporter.log("OutputReporter file close", true);
			wb.write(fileOut);
			fileOut.close();
			closeOutputExcel=true;
		 }
		}

}
