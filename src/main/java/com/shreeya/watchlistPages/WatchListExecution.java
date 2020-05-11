package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	public WatchListExecution(WebDriver driver) throws IOException {
		super(driver);
		CsvReaderCode csvReader=new CsvReaderCode();
		csvLoginTestIterator=csvReader.WatchListTestDataProvider();
		watchListPage=new WatchListPage(driver);
		this.driver=driver;
		watchListReport=new WatchListReport();
	}
	
	public void watchListExecute() throws InterruptedException, IOException {
		closePopupButton = fluentWaitCodeXpath("//a[@class='ed-icon i-close lg']", "Close popup button");
		clickElement(closePopupButton,  "Close popup button");
		hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Watchlist']");
		ExtendReporter reporter=new ExtendReporter(MyTestLauncher.reportFolderPath[1], "WatchList", 0);
		while(csvLoginTestIterator.hasNext()){
			model=csvLoginTestIterator.next();
			List<String> watchListDetail=new ArrayList<String>();
			try {
			watchListDetail=watchListPage.watchListExecution(model,reporter);
			}catch(NullPointerException e) {
				reporter=continueExecution(reporter,model,driver);
				continue;
			}catch(ElementClickInterceptedException e) {
				reporter=continueExecution(reporter,model,driver);
				continue;
			}catch(TimeoutException e) {
				reporter=continueExecution(reporter,model,driver);
				continue;
			}
			//watchListPage.pageVerify(model.getWatchListName(),model.getKeyword());
			if(model.getKeyword().equalsIgnoreCase("TradeWithpredefineWatchList")) {
				
				reporter=watchListReport.watchListReportExecution(model, reporter,driver);
			}else {
			reporter=reporter.watchListReport(model, reporter, driver,watchListDetail);
			}
		}
		reporter.logFlush();
	}

	private ExtendReporter continueExecution(ExtendReporter htmlReport,WatchListModel mdoel,WebDriver driver) {
		watchListReport.abnormalReport(mdoel, htmlReport,driver);
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",10,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		return htmlReport;
	}
	
	

	
}
