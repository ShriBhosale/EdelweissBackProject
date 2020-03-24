package com.shreeya.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.shreeya.page.OrderDetail;

public class HelperCode {
	private String reportPathString;
	ExtendReporter report;
	String resultString = "FAIL";
	String[] pathArray = { "Report path not found", "ScreenShot path not found" };
	ApacheCode excelWriter = null;
	int noRowInTestData=0;
	String [] folderPathArray= {"ReportFolder path","htmlFolder path","screenshort path"};
	public HelperCode() {

	}

	public String nestIdProvider(String strForNestId) {
		String arr[] = strForNestId.split(">");
		String[] nestIdArray = arr[1].split("<");
		/*
		 * for(String a:nestIdArray) { System.out.println(a);
		 * System.out.println("===================================="); }
		 */
		return nestIdArray[0];
	}

	public String timeStampGenerator() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp.getTime());
		return Long.toString(timestamp.getTime());
	}

	public String passFailResult(String[] orderDetail) {

		if (orderDetail[0].equalsIgnoreCase("New")) {
			if (orderDetail[1].equalsIgnoreCase("Open")) {
				resultString = "PASS";
			}
		} else if (orderDetail[0].equalsIgnoreCase("Mod")) {
			if (orderDetail[1].equalsIgnoreCase("modify")) {
				resultString = "PASS";
			}
		} else if (orderDetail[0].equalsIgnoreCase("Cxl")) {
			if (orderDetail[1].equalsIgnoreCase("cancelled")) {
				resultString = "PASS";
			}
		}
		return resultString;
	}

	public String[] screenShotAndReportPath(WebDriver driver, ExtendReporter report,String folderPathStr) throws IOException {
		// report=new ExtendReporter();
		String[] pathArray = { report.getReportPathString(), report.captureScreen(driver,folderPathStr) };
		return pathArray;
	}

	public void outputProcessor(WebDriver driver, String action, int orderNo)
			throws InterruptedException, IOException {
		
		System.out.println("Order1 no===========================================================> "+orderNo);
		
		FolderStructure folderStructureObject=new FolderStructure();
		folderPathArray=folderStructureObject.reportFolderCreator(orderNo);
		//System.out.println("noRowInTestData : "+noRowInTestData+"\n folderPathArray[0] : "+folderPathArray[0]);
		if(orderNo==1) {
			CsvReaderCode reader=new CsvReaderCode();
			noRowInTestData=reader.noRowInTestData();
			
		 excelWriter=new ApacheCode(folderPathArray[0]);
		}
		OrderDetail orderDetailObj = new OrderDetail();
		String[] orderDetailArray = orderDetailObj.orderDetailProvider(driver, action);
		report = new ExtendReporter(folderPathArray[1]);
		report.testCreation("Order Detail0 " + orderNo);
		orderDetailArray[0] = String.valueOf(orderNo);
		orderDetailArray[1] = action;
		report.reportGenerator(orderDetailArray);
		resultString = passFailResult(orderDetailArray);
		report.tearDown(resultString);

		report.logFlush();
		pathArray = screenShotAndReportPath(driver, report,folderPathArray[2]);
		orderDetailArray[13] = resultString;
		orderDetailArray[14] = pathArray[0];
		orderDetailArray[15] = pathArray[1];
		excelWriter.excelWriter(orderDetailArray, orderNo);
		if(noRowInTestData==orderNo) {
		excelWriter.closeExcelWriting();
		}
	}
	
	public  String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}

	public static void main(String[] args) {

	}

}
