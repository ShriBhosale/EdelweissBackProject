package com.shreeya.fundtransferpages;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.FunctionKeyword;
import com.shreeya.model.FundTransferModel;
import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class FundTransferExecution extends SeleniumCoder{

	WebDriver driver;
	private WebElement fundTransferTab;
	Iterator<FundTransferModel> csvFundTransferIterator;
	FundTransferModel fundTransferModel;
	FundTransferPage fundTranferPage;
	ExtendReporter report;
	
	public FundTransferExecution(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
		CsvReaderCode csvReader=new CsvReaderCode();
		csvFundTransferIterator=csvReader.FundTransferDataProvider();
		report=new ExtendReporter();
	}
	
	public void fundTransferExecute() throws InterruptedException {
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']");
		clickElement(fundTransferTab, "Fund Transfer Tab");
		fundTranferPage=new FundTransferPage(driver);
		
		/*
		 * while(csvFundTransferIterator.hasNext()){
		 * fundTransferModel=csvFundTransferIterator.next();
		 * fundTranferPage.fundTransferexecute(fundTransferModel);
		 * 
		 * }
		 */
		 
		
		Thread.sleep(3000);
	}
}
