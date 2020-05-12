package com.shreeya.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.MyTestLauncher;
import com.shreeya.model.LoginTestModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.model.WatchListModel;
import com.shreeya.watchlistPages.WatchListPage;

public class ExtendReporter {

	public  ExtentHtmlReporter htmlextent = null;
	public  ExtentReports report = null;
	public  ExtentTest test = null;
	HelperCode helperObject;
	private String reportPathString;
	static int count=0;
	int timestamp;
	
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
		helperObject=new HelperCode();
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



	public ExtentTest testCreation(String testName) {
		Reporter.log("Extend Report Test Name : "+testName,true);
		test = report.createTest(testName);
		return test;
	}
	
	public String addScreenshotMethod(WebDriver driver,String folderPathString,String scenario,int orderNo ){
		String screenshotPath=null;
		 try {
		 screenshotPath=captureScreen(driver,folderPathString,scenario,orderNo);
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
		
		screenshotPath=screenshotPath.replace("../WorkingE2",path);
		screenshotPath=screenshotPath.replace("/", "//");
		System.out.println(screenshotPath);
		
			test.log(Status.INFO,""+test.addScreenCaptureFromPath(screenshotPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return screenshotPath;
	}
	
	public String addScreenshotMethod(String screenshotPath) throws IOException {
		//testCreation("Login Error");
		
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
		
		screenshotPath=screenshotPath.replace("../WorkingE2",path);
		screenshotPath=screenshotPath.replace("//", "-");
		//screenshotPath=screenshotPath.replace("-", "\");
		System.out.println(screenshotPath);
		 test.addScreenCaptureFromPath(screenshotPath);
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
		try {
		 timestamp=Integer.valueOf(helperCode.timeStampGenerator());
		}catch(NumberFormatException e) {
			
		}
		ExtendReporter report=new  ExtendReporter(MyTestLauncher.reportFolderPath[1],"Abnormal Termination",timestamp); 
		report.testCreation("Abnormal Termination");
		report.errroMsg("Abnormal Termination");
		String screenshotPath=report.addScreenshotMethod(driver,MyTestLauncher.reportFolderPath[2],"Abnormal Termination",1);
		report.logFlush();
		//driver.close();
		Reporter.log("Driver close",true);
		Reporter.log("Screenshot path =======> "+screenshotPath,true);
		//System.exit(0);
	}
	
	public void reporter(WebDriver driver,String moduleName,String [] folderArray,String referNO) throws IOException{
		Reporter.log(moduleName,true);
		String screenshotPath;
		ExtendReporter report=new  ExtendReporter(folderArray[1],moduleName,1); 
		report.testCreation(moduleName);
		report.errroMsg(moduleName+" this executed....");
		report.errroMsg("ReferNo from execution file : "+referNO);
		if(!moduleName.equalsIgnoreCase("FundTransfer"))
		 screenshotPath=report.addScreenshotMethod(driver,folderArray[2],moduleName,1);
		report.logFlush();
		
		Reporter.log("Driver close",true);
		
		
	}
	public String reporter(WebDriver driver,String moduleName,String [] folderArray,String referNO,String errorMsg) throws IOException{
		Reporter.log(moduleName,true);
		String screenshotPath;
		ExtendReporter report=new  ExtendReporter(folderArray[1],moduleName,1); 
		report.testCreation(moduleName);
		report.errroMsg(moduleName+" this executed....");
		report.errorFail(errorMsg);
		report.errroMsg("ReferNo from execution file : "+referNO);
		if(!moduleName.equalsIgnoreCase("FundTransfer"))
		 screenshotPath=report.addScreenshotMethod(driver,folderArray[2],moduleName,1);
		report.logFlush();
		
		Reporter.log("Driver close",true);
		
		return report.getReportPathString();
	}
	
	public void loginReport(WebDriver driver,ExtendReporter extend,LoginTestModel loginModelObject,String loginErrorStr,String elementName) throws IOException {
		extend.testCreation(loginModelObject.getTestScenario()+"_"+loginModelObject.getReference_no());
		int orderNo=Integer.valueOf(loginModelObject.getReference_no());
		extend.addScreenshotMethodInfo(driver, MyTestLauncher.reportFolderPath[2],"LoginError",orderNo);
		extend.errroMsg("User Id : "+loginModelObject.getUser_Id());
		extend.errroMsg("Password : "+loginModelObject.getPassword());
		extend.errroMsg("Yob : "+loginModelObject.getYob());
		
		
		if(!loginErrorStr.equalsIgnoreCase("No Error")) {
			extend.errorFail(elementName+" element not found.....");
			
			extend.tearDown("Fail");
		}else {
			extend.errroMsg("Login Successfully");
			extend.tearDown("Pass");
		}
	}
	
	public void abnormalErrorHandling(WebDriver driver,String elementName,String moduleName) throws IOException{
		Reporter.log("Abnormal Reporter creator",true);
		HelperCode helperCode=new HelperCode();
		try {
		 timestamp=Integer.valueOf(helperCode.timeStampGenerator());
		}catch(NumberFormatException e) {
			
		}
		ExtendReporter report=new  ExtendReporter(MyTestLauncher.reportFolderPath[1],"Abnormal Termination",timestamp); 
		report.testCreation("Abnormal Termination");
		report.errroMsg("Abnormal Termination");
		report.errorFail(elementName + " not found");
		report.errroMsg("Module Name : "+moduleName);
		String screenshotPath=report.addScreenshotMethod(driver,MyTestLauncher.reportFolderPath[2],"Abnormal Termination",1);
		report.logFlush();
		//driver.close();
		Reporter.log("Driver close",true);
		Reporter.log("Screenshot path =======> "+screenshotPath,true);
		//System.exit(0);
	}
	
	public ExtendReporter watchListReport(WatchListModel model,ExtendReporter htmlReport,WebDriver driver,List<String> detailList) throws IOException, InterruptedException {
		Reporter.log("watchListReport start", true);
		int orderNo=0;
		try {
			orderNo=Integer.valueOf(model.getReferNo());
		}catch(NumberFormatException e) {
			
		}
		htmlReport.testCreation(model.getTestCaseName()+"_"+model.getReferNo());
		if(model.getKeyword().equalsIgnoreCase("TradeWithpredefineWatchList")) {
			predefineWatchReport(model, htmlReport, driver, orderNo,WatchListPage.predifineWatchMsg);
		}else if(model.getKeyword().equalsIgnoreCase("ClickPredineWatchList")) {
			predefineWatchDetaiReport(model, htmlReport, driver, orderNo,detailList);
		}else{
		test.log(Status.INFO, "WatchList name : "+model.getWatchListName());
		if(model.getDafaultWatchList().equalsIgnoreCase("Yes"))
			test.log(Status.INFO, "With Deafault mode");
		test.log(Status.INFO, "Exchange : "+model.getExchange());
		Reporter.log("Before compareScriptAndExchange method", true);
		compareScriptAndExchange(model);
		Thread.sleep(3000);
		
		htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2], "WatchList", orderNo);
		}
		return htmlReport;
	}
	
	private void predefineWatchDetaiReport(WatchListModel model, ExtendReporter htmlReport, WebDriver driver,
			int orderNo, List<String> detailList) {
		for(String detail:detailList)
		test.log(Status.INFO, detail);
		
		htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2], "WatchList", orderNo);
	}

	public void compareScriptAndExchange(WatchListModel model) {
		Reporter.log("Inside compareScriptAndExchange ", true);
		
		String [] scriptArray=WatchListPage.applicationScriptArray;
		String [] exchangeArray=WatchListPage.exchangeArray;
		String [] sheetSriptNameArray=WatchListPage.scriptArray;
		List<String> scriptList=WatchListPage.scriptList;
		List<String> exchangeList=WatchListPage.exchangeList;
		//String scriptZero=scriptList.get(0);
		if(model.getKeyword().trim().equalsIgnoreCase("CreateAddScript")||model.getKeyword().trim().equalsIgnoreCase("CreateAddScriptDelete")) {
			
			for(int i=0;i<scriptList.size();i++) {
				test.log(Status.INFO, "Script : "+sheetSriptNameArray[i]);
				String script=scriptList.get(i).replace("\n", "");
				Reporter.log(scriptList.get(i), true);
				if(script.equalsIgnoreCase(scriptArray[i]) && exchangeList.get(i).equalsIgnoreCase(exchangeArray[i])) {
					test.log(Status.PASS, "ScriptName : "+scriptList.get(i)+" Exchange : "+exchangeList.get(i));
				}else {
					test.log(Status.FAIL, "ScriptName : "+scriptList.get(i)+" Exchange : "+exchangeList.get(i));
				}
			}
			if(!WatchListPage.errorMsg.equalsIgnoreCase("no"))
			{
				test.log(Status.PASS,"MSg : "+WatchListPage.errorMsg);
			}
			defaultWatchList(model.getDafaultWatchList());
		}else if(model.getKeyword().equalsIgnoreCase("CreateDuplicate")) {
			test.log(Status.INFO, "Script : "+sheetSriptNameArray[0]);
			if(scriptList.get(0).equalsIgnoreCase(scriptArray[0])&& exchangeList.get(0).equalsIgnoreCase(exchangeArray[0])) {
				test.log(Status.PASS, "Script Name : "+scriptArray[0]+" Exchange : "+exchangeArray[0]);
			}
			if(!WatchListPage.errorMsg.equalsIgnoreCase("no"))
			{
				test.log(Status.PASS,WatchListPage.errorMsg);
			}else {
				
				test.log(Status.FAIL, "User can create duplicate watchlist.");
			
			}
		}else if(model.getKeyword().equalsIgnoreCase("CreateDelete")) {
			if(scriptList.get(0).equalsIgnoreCase(scriptArray[0])&& exchangeList.get(0).equalsIgnoreCase(exchangeArray[0])) {
				test.log(Status.PASS, "Script Name : "+scriptArray[0]+" Exchange : "+exchangeArray[0]);
			}
			if(!WatchListPage.errorMsg.equalsIgnoreCase("no"))
			{
				test.log(Status.FAIL,WatchListPage.errorMsg);
			}
		}
		else {
			int a=0;
			if(scriptArray.length!=1) {
				for(int i=0;i<scriptArray.length;i++) {
					if(!scriptArray[i].equalsIgnoreCase(model.getDeleteScript())) {
						a=i;
					}
				}
			}
			test.log(Status.INFO, "Script : "+sheetSriptNameArray[0]);
			if(scriptList.get(0).equalsIgnoreCase(scriptArray[a])&& exchangeList.get(0).equalsIgnoreCase(exchangeArray[a])) {
				test.log(Status.PASS, "Script Name : "+scriptArray[a]+" Exchange : "+exchangeArray[a]);
			}else
			{
				test.log(Status.FAIL, "Script Name : "+scriptArray[a]+" Exchange : "+exchangeArray[a]);
			}
			defaultWatchList(model.getDafaultWatchList());
		}
		
	}

	public void defaultWatchList(String defaultWList) {
		if(defaultWList.equalsIgnoreCase("Yes")) {
			if(!WatchListPage.errorMsg.equalsIgnoreCase("no"))
			{
				test.log(Status.PASS,WatchListPage.errorMsg);
			}else { 
					test.log(Status.FAIL, "Default watchList delete.");
			}
			}
	}
	
	public void predefineWatchReport(WatchListModel model,ExtendReporter htmlReport,WebDriver driver,int orderNo,String msg) {
		
		test.log(Status.INFO, "Predefine WatchList : "+model.getWatchListName());
		if(msg.equalsIgnoreCase("Your watchlist has 50 scrips")) {
		test.log(Status.PASS, msg);
		}else {
			test.log(Status.FAIL, msg);
		}
		
			htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2], "WatchList",orderNo);
		
	}
}
