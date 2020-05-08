package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.Iterator;

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
			String msg=watchListPage.watchListExecution(model,reporter);
			//watchListPage.pageVerify(model.getWatchListName(),model.getKeyword());
			if(model.getKeyword().equalsIgnoreCase("ClickPredineWatchList")) {
				
				reporter=watchListReport.watchListReportExecution(model, reporter,driver);
			}else {
			reporter=reporter.watchListReport(model, reporter, driver,msg);
			}
		}
		reporter.logFlush();
	}
	
	

	
}
