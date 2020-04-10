package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ErrorHtmlReport {
	public static ExtentHtmlReporter htmlextent = null;
	public static ExtentReports report = null;
	public static ExtentTest test = null;
	HelperCode helperObject;
	String reportPathString;
	
	public void createErrorReport(String folderPathString,String msg,WebDriver driver) {
		helperObject=new HelperCode();
		FolderStructure folder=new FolderStructure();
		//folder.reportFolderCreator(orderNo);
		reportPathString=folderPathString+"/FailReport"+"_"+helperObject.timeStampGenerator()+".html";
		report = new ExtentReports();
		htmlextent.config().setReportName(msg);
		report.attachReporter(htmlextent);
		test=report.createTest(msg);
		test.log(Status.FAIL, msg);
		
	}

	public String captureScreen(WebDriver driver,String folderPathString,String scenario,int orderNo) throws IOException {
		
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest =folderPathString+"/"+helperObject.removeExtraString(scenario, " ")+"_"+orderNo+"_"+helperObject.timeStampGenerator()+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}
}
