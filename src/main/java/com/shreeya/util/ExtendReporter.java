package com.shreeya.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.FunctionKeyword;
import com.shreeya.experiment.Report;
import com.shreeya.model.LoginTestModel;
import com.shreeya.model.TestDataModel;

public class ExtendReporter {

	public  ExtentHtmlReporter htmlextent = null;
	public  ExtentReports report = null;
	public  ExtentTest test = null;
	HelperCode helperObject;
	private String reportPathString;
	static int count=0;
	
	public  ExtendReporter(String folderPathString,String scenario,int orderNo) {
		
		Reporter.log("<====ExtendReporter Constructor===>",true);
		Reporter.log("Scenario : "+scenario,true);
		helperObject=new HelperCode();
		String timestamp=helperObject.timeStampGenerator();
		if(orderNo!=0)
		reportPathString=folderPathString+"/"+helperObject.removeExtraString(scenario, " ")+"_"+timestamp+".html";
		else
			reportPathString=folderPathString+"/"+helperObject.removeExtraString(scenario, " ")+"_"+timestamp+".html";
		Reporter.log("Report Path String "+reportPathString,true);
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
		Reporter.log("Extend Report Test Name : "+testName,true);
		test = report.createTest(testName);
		
	}
	
	public String addScreenshotMethod(WebDriver driver,String folderPathString,String scenario,int orderNo ) throws IOException {
		//testCreation("Login Error");
		String screenshotPath=captureScreen(driver,folderPathString,scenario,orderNo);
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
		
		screenshotPath=screenshotPath.replace("../WorkingE2",path);
		screenshotPath=screenshotPath.replace("/", "//");
		System.out.println(screenshotPath);
		 test.log(Status.FAIL,""+test.addScreenCaptureFromPath(screenshotPath));
		 return screenshotPath;
	}
	
	public String addScreenshotMethodInfo(WebDriver driver,String folderPathString,String scenario,int orderNo) throws IOException {
		//testCreation("Login Error");
		String screenshotPath=captureScreen(driver,folderPathString,scenario,orderNo);
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
		
		screenshotPath=screenshotPath.replace("../WorkingE2",path);
		screenshotPath=screenshotPath.replace("/", "//");
		System.out.println(screenshotPath);
		 test.log(Status.INFO,""+test.addScreenCaptureFromPath(screenshotPath));
		 return screenshotPath;
	}
	
	public String captureScreen(WebDriver driver,String folderPathString,String scenario,int orderNo) throws IOException {
		Reporter.log("Capture Screenshot",true);
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
			Reporter.log(msg,true);
		}
	}
	
	public void reportGenerator(String [] orderDetailArray,String [] passArray,TestDataModel model) throws InterruptedException {
		Reporter.log("<=== ExtendReporter : Report Start generate "+model.getAction()+" "+model.getOrderNo()+" ===>",true);
		
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
		test.log(Status.INFO, "NestId Order Numbe :: "+orderDetailArray[11]);
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
		
		Reporter.log("Extend Log Flush",true);
		Reporter.log("Reporter path ===> "+reportPathString,true);
		report.flush();
		Reporter.log("Report Object ==================================================================> "+report,true);
	}

	
	public void errroMsg(String msg) {
		test.log(Status.INFO, msg);
		
	}
	
	public void errorFail(String msg) {
		test.log(Status.FAIL, msg);
	}
	
	public void abnormalErrorHandling(WebDriver driver) throws IOException{
		Reporter.log("Abnormal Error Handly",true);
		HelperCode helperCode=new HelperCode();
		int timestamp=Integer.valueOf(helperCode.timeStampGenerator());
		ExtendReporter report=new  ExtendReporter(FunctionKeyword.folderPath[0],"Abnormal Termination",timestamp); 
		report.testCreation("Abnormal Termination");
		report.errroMsg("Abnormal Termination");
		String screenshotPath=report.addScreenshotMethod(driver,FunctionKeyword.folderPath[2],"Abnormal Termination",1);
		report.logFlush();
		driver.close();
		Reporter.log("Driver close",true);
		Reporter.log("Screenshot path =======> "+screenshotPath,true);
		System.exit(0);
	}
	
	public void reporter(WebDriver driver,String moduleName,String [] folderArray) throws IOException{
		Reporter.log(moduleName,true);
		
		ExtendReporter report=new  ExtendReporter(folderArray[1],moduleName,1); 
		report.testCreation(moduleName);
		report.errroMsg(moduleName+" this executed....");
		String screenshotPath=report.addScreenshotMethod(driver,folderArray[2],moduleName,1);
		report.logFlush();
		
		Reporter.log("Driver close",true);
		Reporter.log("Screenshot path =======> "+screenshotPath,true);
		
	}
	
	public void loginReport(WebDriver driver,ExtendReporter extend,LoginTestModel loginModelObject,String loginErrorStr) throws IOException {
		extend.testCreation(loginModelObject.getTestScenario()+"_"+loginModelObject.getReference_no());
		int orderNo=Integer.valueOf(loginModelObject.getReference_no());
		extend.addScreenshotMethodInfo(driver, FunctionKeyword.folderPath[2],"LoginError",orderNo);
		extend.errroMsg("User Id : "+loginModelObject.getUser_Id());
		extend.errroMsg("Password : "+loginModelObject.getPassword());
		extend.errroMsg("Yob : "+loginModelObject.getYob());
		
		
		if(!loginErrorStr.equalsIgnoreCase("No Error")) {
			extend.errroMsg(loginErrorStr);
			extend.tearDown("Fail");
		}else {
			extend.errroMsg("Login Successfully");
			extend.tearDown("Pass");
		}
	}
}
