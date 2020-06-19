package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PageRanges;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;
import com.shreeya.model.WatchListModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class WatchListExecution extends SeleniumCoder{
	
	WebElement watchListLink;
	WebElement closePopupButton;
	WebElement newWatchListTab;
	WebElement closeButton;
	WebDriver driver;
	
	Iterator<WatchListModel> csvLoginTestIterator;
	WatchListModel model;
	WatchListPage watchListPage;
	WatchListReport watchListReport;
	ReportWatchlist watchListReport1;
	WatchListCommon common;
	public WatchListExecution(WebDriver driver){
		super(driver);
		CsvReaderCode csvReader=new CsvReaderCode();
		csvLoginTestIterator=csvReader.WatchListTestDataProvider();
		watchListPage=new WatchListPage(driver);
		this.driver=driver;
		watchListReport=new WatchListReport();
		watchListReport1=new ReportWatchlist();
		common=new WatchListCommon(driver);
	}
	
	public ExtendReporter watchListExecute(ExtendReporter reporter)  {
		closePopupButton = fluentWaitCodeXpath("//a[@class='ed-icon i-close lg']", "Close popup button");
		clickElement(closePopupButton,  "Close popup button");
		common.redirectToWatchListModule(true);
		
		
		while(csvLoginTestIterator.hasNext()){
			model=csvLoginTestIterator.next();
			List<String> watchListDetail=new ArrayList<String>();
			Map<String,List<String>> verfiyMap=new HashMap<String,List<String>>();
			try {
				Reporter.log("<b>ExcelRow"+model.getReferNo()+" Start Execution</b>", true);
				Reporter.log("<b>"+model.toString()+"</b>", true);
				verfiyMap=watchListPage.watchListExecution(model,reporter);
			}catch(NullPointerException e) {
				reporter=continueExecution(reporter,model,driver);
				StackTraceElement [] s=e.getStackTrace();
				for(StackTraceElement s1:s) {
					Reporter.log(s1.toString(), true);
				}
				continue;
			}catch(ElementClickInterceptedException e) {
				reporter=continueExecution(reporter,model,driver);
				continue;
			}catch(TimeoutException e) {
				reporter=continueExecution(reporter,model,driver);
				continue;
			}
			 
			//reporter=watchListReport.watchListReportGenerator(model,reporter);
			watchListReport1.report(model,reporter,verfiyMap);
			 
		}
		return reporter;
	}

	private ExtendReporter continueExecution(ExtendReporter htmlReport,WatchListModel mdoel,WebDriver driver) {
		watchListReport.abnormalReport(mdoel, htmlReport,driver);
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",10,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		return htmlReport;
	}
	
	

	
}
