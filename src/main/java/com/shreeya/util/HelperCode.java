package com.shreeya.util;

import java.io.IOException;
import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;

import com.shreeya.page.OrderDetail;

public class HelperCode {
	private String reportPathString;
	ExtendReporter report;
	String  resultString="FAIL";
	String [] pathArray= {"Report path not found","ScreenShot path not found"};
	public HelperCode() {
	
		
	}
	



	public  String nestIdProvider(String strForNestId) {
		String arr[]=strForNestId.split(">");
		String [] nestIdArray=arr[1].split("<");
		/*for(String a:nestIdArray) {
			System.out.println(a);
			System.out.println("====================================");
		}*/
		return nestIdArray[0];
	}
	
	public  String timeStampGenerator() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp.getTime());
		return Long.toString(timestamp.getTime());
	}
	
	public String passFailResult(String [] orderDetail) {
		
		if(orderDetail[0].equalsIgnoreCase("New")) {
			if(orderDetail[1].equalsIgnoreCase("Open"))
			{
				resultString="PASS";
			}
		}else if(orderDetail[0].equalsIgnoreCase("Mod")) {
			if(orderDetail[1].equalsIgnoreCase("modify"))
			{
				resultString="PASS";
			}
		}
		else if(orderDetail[0].equalsIgnoreCase("Cxl")) {
			if(orderDetail[1].equalsIgnoreCase("cancelled"))
			{
				resultString="PASS";
			}
		}
		return resultString;
	}
	
	
	
	public String[] screenShotAndReportPath(WebDriver driver,ExtendReporter report) throws IOException {
		//report=new ExtendReporter();
		String [] pathArray= {report.getReportPathString(),report.captureScreen(driver)};
		return pathArray;
	}
	
	public ApacheCode outputProcessor(WebDriver driver, String action,int orderNo,ApacheCode excelWriter) throws InterruptedException, IOException {
		
		
		OrderDetail orderDetailObj=new OrderDetail();
		String [] orderDetailArray=orderDetailObj.orderDetailProvider(driver,action);
		report=new ExtendReporter();
		report.testCreation("Order Detail0 "+orderNo);
		orderDetailArray[0]=String.valueOf(orderNo);
		orderDetailArray[1]=action;
		report.reportGenerator(orderDetailArray);
		resultString=passFailResult(orderDetailArray);
		report.tearDown(resultString);
		
		
		report.logFlush();
		pathArray=screenShotAndReportPath(driver,report);
		orderDetailArray[13]=resultString;
		orderDetailArray[14]=pathArray[0];
		orderDetailArray[15]=pathArray[1];
		excelWriter.excelWriter(orderDetailArray,orderNo);
		return excelWriter;
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
