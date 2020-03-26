package com.shreeya.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.shreeya.page.OrderDetail;

import net.bytebuddy.description.modifier.SynchronizationState;

public class HelperCode {
	private String reportPathString;
	ExtendReporter report;
	String resultString = "FAIL";
	String[] pathArray = { "Report path not found", "ScreenShot path not found" };
	static ApacheCode excelWriter = null;
	int noRowInTestData=0;
	private String [] folderPathArray= {"ReportFolder path","htmlFolder path","screenshort path"};
	String[] orderDetailArray= { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
		"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id", "Rejection Reason",
		"ScriptResult", "Report link", "Screenshot link" };
	private boolean reportFlag=false;
	static private int noRowInTestData1;
	public HelperCode() {

	}

	public String nestIdProvider(String strForNestId) {
		String arr[] = strForNestId.split(">");
		String[] nestIdArray = arr[1].split("<");
		/*
		 * for(String a:nestIdArray) { System.out.println(a);
		 * System.out.println("===================================="); }
		 * 
		 */
		if(nestIdArray[0].contains("|"))
		{
			nestIdArray[0]=nestIdArray[0].replace("|", "");
		}
		return nestIdArray[0];
	}

	public String timeStampGenerator() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp.getTime());
		return Long.toString(timestamp.getTime());
	}

	public String passFailResult(String[] orderDetail) {

		if (orderDetail[1].equalsIgnoreCase("New")) {
			if (orderDetail[2].equalsIgnoreCase("Open")) {
				resultString = "PASS";
			}
		} else if (orderDetail[1].equalsIgnoreCase("Mod")) {
			if (orderDetail[2].equalsIgnoreCase("modified")) {
				resultString = "PASS";
			}
		} else if (orderDetail[1].equalsIgnoreCase("Cxl")) {
			if (orderDetail[2].equalsIgnoreCase("cancelled")) {
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
	
	

	public String outputProcessor(WebDriver driver, String action, int orderNo,String newOrderStatus)
			throws InterruptedException, IOException {
		FolderStructure folderStructureObject=new FolderStructure();
		folderPathArray=folderStructureObject.reportFolderCreator(orderNo);
		System.out.println("New order status ===> "+newOrderStatus);
		report = new ExtendReporter(folderPathArray[1]);
		report.testCreation("Order Detail " + orderNo);
		if((action.equalsIgnoreCase("Mod")||action.equalsIgnoreCase("Cxl"))&&((newOrderStatus.equalsIgnoreCase("Open")||newOrderStatus.equalsIgnoreCase("after market order req received")))){
			
				reportFlag=true;
			
		} else if (action.equalsIgnoreCase("New")){
			reportFlag=true;
		}
		if(reportFlag) {
		System.out.println("Order1 no===========================================================> "+orderNo);
		//System.out.println("noRowInTestData : "+noRowInTestData+"\n folderPathArray[0] : "+folderPathArray[0]);
		if(orderNo==1) {
			CsvReaderCode reader=new CsvReaderCode();
			noRowInTestData1=reader.noRowInTestData();
			
		 excelWriter=new ApacheCode(folderPathArray[0]);
		}
		OrderDetail orderDetailObj = new OrderDetail();
		orderDetailArray = orderDetailObj.orderDetailProvider(driver, action);
		
		
		orderDetailArray[0] = String.valueOf(orderNo);
		orderDetailArray[1] = action;
		report.reportGenerator(orderDetailArray);
		resultString = passFailResult(orderDetailArray);
		orderDetailArray[13] = resultString;
		pathArray = screenShotAndReportPath(driver, report,folderPathArray[2]);
		orderDetailArray[14] = pathArray[0];
		orderDetailArray[15] = pathArray[1];
		excelWriter.excelWriter(orderDetailArray, orderNo);
		for(String orderDetail:orderDetailArray)
			System.out.println(orderDetail);
		
		}else {
		report.errroMsg();
		orderDetailArray[2]="New order reject1";
		//noRowInTestData=orderNo;
		}
		report.tearDown(resultString);
		report.logFlush();
		System.out.println("<<=================================== After if else =======================================>>");
		for(String orderDetail:orderDetailArray)
			System.out.println(orderDetail);
		System.out.println("Order no =====>  "+orderNo+"\noRowInTestData1 =======> "+noRowInTestData1);
		if(noRowInTestData1==orderNo) {
		excelWriter.closeExcelWriting();
		}
		return orderDetailArray[2];
	}
	
	public  String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = sdf.format(now);
	    return strDate;
	}

	public String statusRemoveBracket(Collection<String> str) {
		String name=str.toString();
		name=name.replace("[", "");
		name=name.replace("]", "");
		
		return name;
	}

	public static void main(String[] args) {

	}

}
