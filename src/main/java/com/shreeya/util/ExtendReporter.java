package com.shreeya.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.experiment.Report;
import com.shreeya.model.TestDataModel;

public class ExtendReporter {

	public static ExtentHtmlReporter htmlextent = null;
	public static ExtentReports report = null;
	public static ExtentTest test = null;
	HelperCode helperObject;
	private String reportPathString;
	
	public  ExtendReporter(String folderPathString,String scenario,int orderNo) {
		helperObject=new HelperCode();
		reportPathString=folderPathString+"/"+helperObject.removeExtraString(scenario, " ")+"_"+orderNo+"_"+helperObject.timeStampGenerator()+".html";
		setReportPathString(reportPathString);
		htmlextent = new ExtentHtmlReporter(getReportPathString());
		
		report = new ExtentReports();
		htmlextent.config().setReportName(scenario);
		report.attachReporter(htmlextent);
		
	}
	
	public ExtendReporter() {
		// TODO Auto-generated constructor stub
	}

	public void tearDown(String output) {
		if(output.equalsIgnoreCase("PASS")) {
			test.log(Status.PASS,"Test Pass....");
		}else
			test.log(Status.FAIL, "Test Fail...");
	}
	
	public String getReportPathString() {
		return reportPathString;
	}



	public void setReportPathString(String reportPathString) {
		this.reportPathString = reportPathString;
	}



	public void testCreation(String testName) {
		test = report.createTest(testName);
	}
	
	public String addScreenshotMethod(WebDriver driver,String folderPathString,String scenario,int orderNo ) throws IOException {
		//testCreation("Login Error");
		String screenshotPath=captureScreen(driver,folderPathString,scenario,orderNo);
		 test.addScreenCaptureFromPath(screenshotPath);
		 return screenshotPath;
	}
	
	public String captureScreen(WebDriver driver,String folderPathString,String scenario,int orderNo) throws IOException {
		
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest =folderPathString+"/"+helperObject.removeExtraString(scenario, " ")+"_"+orderNo+"_"+helperObject.timeStampGenerator()+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}

	public void logsPrinter(ArrayList<String> msgs) {
		for(String msg:msgs) {
			test.log(Status.INFO, msg);
		}
	}
	
	public void reportGenerator(String [] orderDetailArray,String [] passArray,TestDataModel model) {
		
		test.log(Status.INFO, "Action : "+orderDetailArray[1]);
		test.log(Status.INFO, "Order Action :: "+orderDetailArray[3]);
		test.log(Status.INFO, "Trading Symbol :: "+orderDetailArray[4]);
		test.log(Status.INFO, "Product Type :: "+orderDetailArray[5]);
		if(model.getScenario().equalsIgnoreCase("Modification Price")){
			if(passArray[1].equalsIgnoreCase("PASS")) {
				test.log(Status.PASS, "Order Price :: "+orderDetailArray[6]);
			}else {
				test.log(Status.FAIL, "Order Price :: "+orderDetailArray[6]);
			}
			test.log(Status.INFO, "Order Qty :: "+orderDetailArray[12]);
		}else if(model.getScenario().equalsIgnoreCase("Modification Qty")) {
			test.log(Status.INFO, "Order Price :: "+orderDetailArray[6]);
			if(passArray[1].equalsIgnoreCase("PASS")) {
				test.log(Status.PASS, "Order Qty :: "+orderDetailArray[12]);
			}else {
				test.log(Status.FAIL, "Order Qty :: "+orderDetailArray[12]);
			}
		}else if(model.getAction().equalsIgnoreCase("Partial Order")) {
			test.log(Status.INFO, "Order Price :: "+orderDetailArray[6]);
			
			if(passArray[1].equalsIgnoreCase("PASS")) {
				test.log(Status.PASS, "Partial Qty :: "+orderDetailArray[13]);
				test.log(Status.PASS, "Order Qty :: "+orderDetailArray[12]);
			}else {
				test.log(Status.FAIL, "Partial Qty :: "+orderDetailArray[13]);
				test.log(Status.FAIL, "Order Qty :: "+orderDetailArray[12]);
			}
		}else {
			test.log(Status.INFO, "Order Price :: "+orderDetailArray[6]);
			test.log(Status.INFO, "Order Qty :: "+orderDetailArray[12]);
			}
		test.log(Status.INFO, "Order Type :: "+orderDetailArray[7]);
		test.log(Status.INFO, "User id :: "+orderDetailArray[8]);
		test.log(Status.INFO, "Exchange : "+orderDetailArray[9]);
		test.log(Status.INFO, "Validity :: "+orderDetailArray[10]);
		test.log(Status.INFO, "Exchange Order Numbe :: "+orderDetailArray[11]);
		test.log(Status.INFO, "Rejection Reason : "+orderDetailArray[14]);
		if(passArray[0].equalsIgnoreCase("PASS")) {
			test.log(Status.PASS, "Status : "+orderDetailArray[2]);
		}
		else {
			test.log(Status.FAIL, "Status : "+orderDetailArray[2]);
		}
	}
	
	public void report(String [] arry) {
		for(String str:arry) {
			test.log(Status.INFO, str);
		}
	}
	
	public void logFlush() {
		report.flush();
		
	}

	
	public void errroMsg(String msg) {
		test.log(Status.INFO, msg);
		
	}
	
	public void abnormalErrorHandling(WebDriver driver) throws IOException{
		System.out.println("Abnormal Error Handly");
		FolderStructure folderObject=new FolderStructure();
		String [] folderArray=folderObject.reportFolderCreator(1);
		ExtendReporter report=new  ExtendReporter(folderArray[1],"Abnormal Termination",1); 
		report.testCreation("Abnormal Termination");
		report.errroMsg("Abnormal Termination");
		String screenshotPath=report.addScreenshotMethod(driver,folderArray[1],"Abnormal Termination",1);
		report.logFlush();
		driver.close();
		System.out.println("Driver close");
		System.out.println("Screenshot path =======> "+screenshotPath);
		System.exit(0);
	}
	

}
