package com.shreeya.fundtransferpages;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.shreeya.FunctionKeyword;
import com.shreeya.MyTestLauncher;
import com.shreeya.model.FundTransferModel;
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
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']","fundTransferTab");
		 seeMarginTab=fluentWaitCodeXpath(driver, "//a[text()='See Margin']","seeMarginTab");
		fundTranferPage=new FundTransferPage200(driver);
		
		
		
		  while(csvFundTransferIterator.hasNext()){
			  clickElement(fundTransferTab, "Fund Transfer Tab");
		  fundTransferModel=csvFundTransferIterator.next();
		  try {
		  errorMsg=fundTranferPage.fundTransferexecute(fundTransferModel);
		  }catch(ElementNotInteractableException e) {
			  fundTransferReport(fundTransferModel.getReferNo(),elementNameError);
			  backFundTransferPage(fundTransferTab);
			  continue;
		  }catch(NullPointerException e) {
			 
			  fundTransferReport(fundTransferModel.getReferNo(),elementNameError);
			  backFundTransferPage(fundTransferTab);
			  continue;
		  }
		  fundTransferReport(fundTransferModel.getReferNo(),errorMsg);
		 
		  clickElement(seeMarginTab, "See Margin Tab");
		  }
		 
		  outputFileClose();
		
		Thread.sleep(3000);
	}
	
	public void fundTransferReport(String referNo,String errorMsg) throws IOException {
		int referenceNo=Integer.valueOf(referNo);
		if(referenceNo==1) {
			MyTestLauncher.folderCreationObj.copyFile(folderPathArray[0],"FundTransfer");
			FunctionKeyword.apacheCodeObj.outputFileWriterHeader(folderPathArray[0],"FundTransfer",4);
		}
		String reportPath=report.reporter(driver, "FundTransfer", folderPathArray, referNo,errorMsg);
		String screenShotStr=report.captureScreen(driver, folderPathArray[2], "FundTransfer", referenceNo);
		String [] reportArray= {"No","PASS",reportPath,screenShotStr};
		FunctionKeyword.apacheCodeObj.outputFileWriter(reportArray, referenceNo,6);
	}
	
	public void outputFileClose() throws IOException {
		FunctionKeyword.apacheCodeObj.outputExcelFileClose(MyTestLauncher.reportFolderPath[0]);
	}
	
	
	
	public void backFundTransferPage(WebElement fundTransferTab) throws InterruptedException {
		String currentUrl=driver.getCurrentUrl();
		driver.get("https://ewuat.edelbusiness.in/ewhtml/");
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[1]/ul/li/a/strong");
		clickElement(fundTransferTab, "Fund transfer tab");
		}
	
	
}
