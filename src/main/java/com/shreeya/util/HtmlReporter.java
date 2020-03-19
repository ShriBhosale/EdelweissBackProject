package com.shreeya.util;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class HtmlReporter {
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test,test1,test2,test3;
	
	public HtmlReporter() {
		System.out.println("Execution Started.....");
		 htmlReporter = new ExtentHtmlReporter("D:\\TestData\\Report\\extentHtml.html");
		
		
		 extent = new ExtentReports();
		 extent.attachReporter(htmlReporter);
	}
	
	public void testCreator(String testName) {
		test = extent.createTest(testName);
	}

	public String reporter(String msg) {

		test.log(Status.INFO, msg);
		
		
		System.out.println("Execution Ended.....");
		return "D:\\TestData\\Report\\extentHtml.html";
	
	}
	
	public void decisionMaker(boolean result ) {
		if(result==true)
			test.pass("Result pass");
		else
			test.fail("Result fail");
	}
	
	public void flush() {
		test1 = extent.createTest("MyFirstTest22d");
		test2 = extent.createTest("MyFirstTest44");
		extent.flush();
	}
}
