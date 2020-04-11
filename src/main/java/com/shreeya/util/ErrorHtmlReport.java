package com.shreeya.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.page.LoginPage;

public class ErrorHtmlReport {
	public static ExtentHtmlReporter htmlextent = null;
	public static ExtentReports report = null;
	public static ExtentTest test = null;
	HelperCode helperObject;
	String reportPathString;
	ConfigReader reader;
	String [] pathArray;
	
	
	public ErrorHtmlReport() {
		reader=new ConfigReader();
	}
	
	public void createErrorReport(String msg,WebDriver driver) throws IOException {
		helperObject=new HelperCode();
		FolderStructure folder=new FolderStructure();
		String timeStamp=helperObject.timeStampGenerator();
		pathArray=folder.createFolderForFailReport(pathStrProcces(reader.configReader("Result"))+"FailReport111");
		System.out.println("Report path "+pathArray[0]+"/AbnormalTerminationReport.html");
		htmlextent = new ExtentHtmlReporter(pathArray[0]+"/AbnormalTerminationReport.html");
		report = new ExtentReports();
		report.attachReporter(htmlextent);
		test=report.createTest("testcase2");
		String screenshotPath="../ReportFailReport111/Screenshot/Screenshot.png";
		//=pathArray[1]+"/Screenshot.png";
		System.out.println("Screeenshot Path====> "+screenshotPath);
		try {
			test.log(Status.INFO, msg + test.addScreenCaptureFromPath(captureScreen(driver, screenshotPath)));
			//test.log(Status.PASS, "Passed test 2" + log.addScreenCaptureFromPath(captureScreen()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		report.flush();
	}

	public String captureScreen(WebDriver driver,String folderPathString) throws IOException {
		
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		//String dest =folderPathString;
		File target = new File(folderPathString);
		FileUtils.copyFile(src, target);
		System.out.println("F");
		//folderPathString=target.toString().replace("/", "\\");
		return folderPathString;
	}
	
	 public String pathStrProcces(String pathStr) {
			String str=null;
			
			pathStr=pathStr.replace("\\", "-");
					System.out.println(pathStr);
					pathArray=pathStr.split("-");
					for(String pathString:pathArray) {
						System.out.println(pathString);
					}
					String relativePath="../"+pathArray[pathArray.length-1]+"/Report";
					System.out.println(relativePath);
					return relativePath;

		}
	 
	 public static void main(String[] args) {
		ErrorHtmlReport error=new ErrorHtmlReport();
		LoginPage login=new LoginPage();
		WebDriver driver=login.browserLaunch("Abnormal termination");
		try {
			error.createErrorReport("Abnormal termination....", driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	}
}
