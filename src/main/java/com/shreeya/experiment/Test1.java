package com.shreeya.experiment;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.orderdetailpages.LoginPage;

public class Test1 {

	public WebDriver driver;
	public ExtentHtmlReporter htmlextent = null;
	public ExtentReports report = null;
	public ExtentTest log = null;

	public void beforetest() {
		LoginPage login = new LoginPage();

		driver = login.browserLaunch("abc");
		htmlextent = new ExtentHtmlReporter("E:\\EdelweissProject\\WorkingE\\Report_FailedReport\\extentdemo.html");
		report = new ExtentReports();
	}

	public void beforemethod() {
		report.attachReporter(htmlextent);
	}

	
	public void testcase2() {
		log = report.createTest("testcase2");
		driver.get("https://www.google.com/");
		try {
			log.log(Status.INFO, "Google Page opened" + log.addScreenCaptureFromPath(captureScreen()));
			log.log(Status.PASS, "Passed test 2" + log.addScreenCaptureFromPath(captureScreen()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void aftertest() {
		driver.quit();
		report.flush();
	}

	public String captureScreen() throws IOException {
			TakesScreenshot screen = (TakesScreenshot) driver;
			File src = screen.getScreenshotAs(OutputType.FILE);
			String dest ="E:\\EdelweissProject\\WorkingE\\Report_FailedReport\\Screenshots\\Screenshot"+".png";
			File target = new File(dest);
			FileUtils.copyFile(src, target);
			return dest;
			
	}
	
	public static void main(String[] args) {
		Test1 t1=new Test1();
		t1.beforetest();
		t1.beforemethod();
		t1.testcase2();
		t1.aftertest();
	}
}