package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.shreeya.MyTestLauncher;
import com.shreeya.model.WatchListModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class WatchListReport extends ExtendReporter {

	WebDriver driver;
	public  ExtentHtmlReporter htmlextent = null;
	public  ExtentReports report = null;
	public  ExtentTest test = null;
	
	
	String [] orderDetailArray;
	private WebElement closeButton;
	public WatchListReport() {
		super();
		htmlextent=super.htmlextent;
		report=super.report;
		
		
		this.driver=driver;
	}
	
	public void predefineWatchListReport(WatchListModel model) {
		orderDetailArray=PredefineWatchList.orderDetailArray;
		test.log(Status.INFO, "Predefine WatchList : "+model.getWatchListName());
		test.log(Status.INFO, "Msg : "+WatchListPage.errorMsg);
		test.log(Status.INFO, "Order Action :: "+orderDetailArray[3]);
		if(model.getScriptName().replace(" ", "").equalsIgnoreCase(orderDetailArray[4].replace(" ", ""))) {
		test.log(Status.PASS, "Trading Symbol :: "+orderDetailArray[4]);
		}else {
			test.log(Status.FAIL, "Trading Symbol :: "+orderDetailArray[4]);
		}
		if(model.getProductType().equalsIgnoreCase(orderDetailArray[5])) {
		test.log(Status.PASS, "Product Type :: "+orderDetailArray[5]);
		}else {
			test.log(Status.FAIL, "Product Type :: "+orderDetailArray[5]);
		}
		test.log(Status.PASS, "Order Price :: "+orderDetailArray[6]);
		if(model.getQty().equalsIgnoreCase(orderDetailArray[12])) {
		test.log(Status.PASS, "Order Qty :: "+orderDetailArray[12]);
		}else {
			test.log(Status.FAIL, "Order Qty :: "+orderDetailArray[12]);
		}
		//test.log(Status.INFO, "Order Type :: "+orderDetailArray[7]);
		
		
		test.log(Status.INFO, "Status : "+orderDetailArray[2]);
		
	}
	
	public ExtendReporter watchListReportExecution(WatchListModel model,ExtendReporter htmlReport,WebDriver driver) throws InterruptedException {
		SeleniumCoder coder=new SeleniumCoder();
		int orderNo=0;
		try {
			orderNo=Integer.valueOf(model.getReferNo());
		}catch(NumberFormatException e) {
			
		}
		test=htmlReport.testCreation("Watchlist"+"_"+model.getReferNo());
		if("Watchlist".equalsIgnoreCase("TradeWithpredefineWatchList")) {
			predefineWatchListReport(model);
			htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2], "PredefineWatchList", orderNo);
			
		}
		closeButton = coder.fluentWaitCodeXpath(driver, "//*[@id=\"myModal\"]/div/div/div[1]/a",5,"Close Button (x)");
		if(closeButton!=null)
			coder.clickElement(closeButton, "Close order status popup");
		return htmlReport;
	}
	
	public ExtendReporter abnormalReport(WatchListModel model,ExtendReporter htmlReport,WebDriver driver) {
		int orderNo=0;
		try {
			orderNo=Integer.valueOf(model.getReferNo());
		}catch(NumberFormatException e) {
			
		}
		test=htmlReport.testCreation("Abnormal condition"+model.getReferNo());
		test.log(Status.INFO, "Abnormal condition");
		test.log(Status.FAIL, SeleniumCoder.elementNameError+"Element not found or element not interactable");
		htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2],"WatchListAbnormal", orderNo);
		return htmlReport;
	}

	public ExtendReporter watchListReportGenerator(WatchListModel model, ExtendReporter reporter) throws IOException {
		Map<String,List<String>> detailMap=WatchListStepVerify.verfiyMap;
		for (Map.Entry<String,List<String>> entry : detailMap.entrySet()) {
			ExtentTest test=reporter.testCreation(entry.getKey());
			int i=0;
			for(String str:entry.getValue()) {
				i++;
				if(str.contains("WorkingE2")) {
					test.log(Status.INFO,"screenshot : str"+test.addScreencastFromPath(str));
				}
				test.log(Status.INFO, str+" : "+i);
			}
		}
		return reporter;
	}
	
	
	
}
