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

public class ExtendReporter {

	public static ExtentHtmlReporter htmlextent = null;
	public static ExtentReports report = null;
	public static ExtentTest test = null;
	HelperCode helperObject;
	
	public  ExtendReporter() {
		helperObject=new HelperCode();
		htmlextent = new ExtentHtmlReporter("E:\\EdelweissProject\\Reports\\"+helperObject.timeStampGenerator()+".html");
		report = new ExtentReports();
		report.attachReporter(htmlextent);
		
	}
	
	public void testCreation(String testName) {
		test = report.createTest(testName);
	}
	
	public void addScreenshotMethod(WebDriver driver) throws IOException {
		 test.addScreenCaptureFromPath(captureScreen(driver));
		 
	}
	
	public String captureScreen(WebDriver driver) throws IOException {
		
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest ="E:\\EdelweissProject\\Reports\\ScreenShot\\"+helperObject.timeStampGenerator()+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}

	public void logsPrinter(ArrayList<String> msgs) {
		for(String msg:msgs) {
			test.log(Status.INFO, msg);
		}
	}
	
	public void logFlush() {
		report.flush();
		
	}
	

}
