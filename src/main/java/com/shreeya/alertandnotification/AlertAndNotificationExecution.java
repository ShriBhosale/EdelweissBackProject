package com.shreeya.alertandnotification;

import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;
import com.shreeya.model.AlertAndNotificationModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class AlertAndNotificationExecution extends SeleniumCoder{
	
	WebDriver driver;
	AlertAndNotificationPage alertAndNotificationPage;
	CsvReaderCode csvReader;
	AlertAndNotificationModel model;
	Iterator<AlertAndNotificationModel> iterator;
	AlterAndNotificationCommon common;
	AlterTestCase alterTestCase;
	
	public AlertAndNotificationExecution(WebDriver driver) {
		super(driver);
		this.driver=driver;
		csvReader=new CsvReaderCode();
		alertAndNotificationPage=new AlertAndNotificationPage(driver);
		common=new AlterAndNotificationCommon(driver);
		alterTestCase=new AlterTestCase(driver);
	}

	public void alertAndNotificationExecute(String segment) {
		Reporter.log("=====> alertAndNotificationExecute <=====", true);
		ExtendReporter reporter=new ExtendReporter(MyTestLauncher.reportFolderPath[1], "Alert", 0);
		iterator=csvReader.alertAndNotificationTestDataProvider();
		common.redirectToAlterAndNotificationModule(true);
		while(iterator.hasNext()) {
			model=iterator.next();
			if(segment.equalsIgnoreCase("Commodity")) {
				if(model.getSegment().equalsIgnoreCase("NCDEX")||model.getSegment().equalsIgnoreCase("MCX"))
					alertAndNotificationPage.alertExecution(model,reporter,segment);
			 }else {
				 alertAndNotificationPage.alertExecution(model,reporter,segment);
			 }
		reporter.logFlush();
		}
	}
}
