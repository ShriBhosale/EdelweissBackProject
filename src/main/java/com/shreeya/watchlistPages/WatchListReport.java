package com.shreeya.watchlistPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
		test=htmlReport.testCreation(model.getTestCaseName()+"_"+model.getReferNo());
		if(model.getKeyword().equalsIgnoreCase("ClickPredineWatchList")) {
			predefineWatchListReport(model);
			htmlReport.addScreenshotMethod(driver, MyTestLauncher.reportFolderPath[2], "PredefineWatchList", orderNo);
			
		}
		closeButton = coder.fluentWaitCodeXpath(driver, "//*[@id=\"myModal\"]/div/div/div[1]/a",5,"Close Button (x)");
		if(closeButton!=null)
			coder.clickElement(closeButton, "Close order status popup");
		return htmlReport;
	}
	
	
}