package com.shreeya.experiment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

import com.shreeya.util.ConfigReader;

public class Tet {

	 static String number="880.00";
	 
	 static Sheet sheet=null;
	 String[] headerArray=null;
	 static FileOutputStream fileOut=null;
	 static Workbook wb ;
	 String inputFile="E:\\EdelweissProject\\WorkingE\\Report1587042243321\\OutputFile.xlsx";
	 
	 public FileOutputStream outputFileWriterHeader() throws IOException {
		 int counter=15;
		 
		 InputStream inp = new FileInputStream(inputFile);
			String[] headerArray = {"Rejection Reason",
					"ScriptResult Pass/fail", "Report link", "Screenshot link" };
			wb= WorkbookFactory.create(inp);
		
			 sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			for(int i=0;i<headerArray.length;i++)
			{
			Cell cell = row.getCell(counter);
			
			if (cell == null)
			    cell = row.createCell(counter);
			
			cell.setCellValue(headerArray[i]);
			 
			counter++;
			} 
			return fileOut;
	 }
	 
	 public FileOutputStream outputFileWriter(String [] orderDetailArray,int rowNo) throws IOException {
		 int counter=15;
		 String hyperLinkName = null;
			Row row = sheet.getRow(rowNo);
			for(int i=17;i<orderDetailArray.length;i++)
			{
			Cell cell = row.getCell(counter);
			
			if (cell == null)
			    cell = row.createCell(counter);
			
			
			
			
			 if(i==19||i==20) {
				 
				if(i==19)
					 hyperLinkName="HtmlReport";
				 else if(i==20)
					 hyperLinkName="Screenshot";
				 cell.setCellValue(hyperLinkName);
				 Hyperlink href = wb.getCreationHelper().createHyperlink(HyperlinkType.URL);
					System.out.println("HyperLink Path ======> "+pathStrProcces(orderDetailArray[i]));
				 href.setAddress(pathStrProcces(orderDetailArray[i]));
					cell.setHyperlink(href);
				}else {
			
					cell.setCellValue(orderDetailArray[i]);
				}
			 
			//wb.write(fileOut);
			 
			
			counter++;
			}
			return fileOut;
	 }
	 
	 public String pathStrProcces(String pathStr) {
			String str=null;
			char [] c=pathStr.toCharArray();
			/*for(int i=0;i<pathStr.length();i++) {
				System.out.print(c[i]+" "+i);
			}*/
			
			String abc=pathStr.substring(11);
			
			abc=".."+abc;
			System.out.println(abc);
			return abc;

		}
	public static void main1(String[] args) throws IOException {
		
		  Tet t=new Tet(); String [] orderDetailArray=
		  {"19","New","rejected","BUY","Accelya","Soln","India","Ltd","NRML","892.30",
		  "LIMIT","60003800","NSE","DAY","200410000000007","1",
		  "0","16387 : Security is not allowed to trade in this market","FAIL\r\n" +
		  "","../WorkingE/Report_FailedReport/FailReport_1586592711291.html",
		  "../WorkingE/Report_FailedReport/Screenshot/Screenshot.png"};
		  
		  t.outputFileWriterHeader(); 
		  t.outputFileWriter(orderDetailArray, 4);
		  t.outputFileWriter(orderDetailArray, 5);
		  t.outputFileWriter(orderDetailArray,6);
		  
		  t.outputExcelFileClose();
		
	}
	
	
	
	public void outputExcelFileClose() throws IOException {
		
		  fileOut = new FileOutputStream(inputFile);
		  
		  wb.write(fileOut); fileOut.close();
		 
		
		
		
	}
	
	public static void main(String[] args) {
		
	}
	
	
}
