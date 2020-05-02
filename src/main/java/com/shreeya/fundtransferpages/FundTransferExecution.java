package com.shreeya.fundtransferpages;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.FunctionKeyword;
import com.shreeya.MyTestLauncher;
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
	FundTransferPage200 fundTranferPage;
	ExtendReporter report;
	String [] folderPathArray;
	String [] reportArray;
	WebElement seeMarginTab;
	String errorMsg;
	
	public FundTransferExecution(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
		CsvReaderCode csvReader=new CsvReaderCode();
		csvFundTransferIterator=csvReader.FundTransferDataProvider();
		report=new ExtendReporter();
		folderPathArray=MyTestLauncher.reportFolderPath;
		
	}
	
	public void fundTransferExecute() throws InterruptedException, IOException {
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']");
		 seeMarginTab=fluentWaitCodeXpath(driver, "//a[text()='See Margin']");
		fundTranferPage=new FundTransferPage200(driver);
		
		
		
		  while(csvFundTransferIterator.hasNext()){
			  clickElement(fundTransferTab, "Fund Transfer Tab");
		  fundTransferModel=csvFundTransferIterator.next();
		  errorMsg=fundTranferPage.fundTransferexecute(fundTransferModel);
		  fundTransferReport(fundTransferModel.getReferNo(),errorMsg);
		 
		  clickElement(seeMarginTab, "See Margin Tab");
		  }
		 
		  outputFileClose();
		
		Thread.sleep(3000);
	}
	
	public void fundTransferReport(String referNo,String errorMsg) throws IOException {
		int referenceNo=Integer.valueOf(referNo);
		if(referenceNo==1) {
			FunctionKeyword.folderCreationObj.copyFile(folderPathArray[0],"FundTransfer");
			FunctionKeyword.apacheCodeObj.outputFileWriterHeader(folderPathArray[0],"FundTransfer",4);
		}
		String reportPath=report.reporter(driver, "FundTransfer", folderPathArray, referNo,errorMsg);
		String screenShotStr=report.captureScreen(driver, folderPathArray[2], "FundTransfer", referenceNo);
		String [] reportArray= {"No","PASS",reportPath,screenShotStr};
		FunctionKeyword.apacheCodeObj.outputFileWriter(reportArray, referenceNo,4);
	}
	
	public void outputFileClose() throws IOException {
		FunctionKeyword.apacheCodeObj.outputExcelFileClose(MyTestLauncher.reportFolderPath[0]);
	}
}
