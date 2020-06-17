package com.shreeya.fundtransferpages;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.MyTestLauncher;
import com.shreeya.model.FundTransferModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferBankExecution extends SeleniumCoder{

	WebDriver driver;
	private WebElement fundTransferTab;
	Iterator<FundTransferModel> csvFundTransferIterator;
	FundTransferModel fundTransferModel;
	FundTransferPage fundTranferPage;
	
	String [] folderPathArray;
	String [] reportArray;
	WebElement seeMarginTab;
	String errorMsg;
	public static String seeMarginBalanceString;
	FundTransferReport report;
	private WebElement seeMarginBalance;
	FundTransferCommon common;
	private String seeMarBalScreenshot;
	
	public FundTransferBankExecution(WebDriver driver){
		super(driver);
		this.driver=driver;
		CsvReaderCode csvReader=new CsvReaderCode();
		csvFundTransferIterator=csvReader.FundTransferDataProvider();
		fundTranferPage=new FundTransferPage(driver);
		folderPathArray=MyTestLauncher.reportFolderPath;
		common=new FundTransferCommon(driver);
	}
	
	
	
	public void fundTransferExecute(FundTransferReport report) {
		Reporter.log("<b>=======@@>  fundTransferExecute <@@=======</b>", true);
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']","fundTransferTab");
		 seeMarginTab=fluentWaitCodeXpath(driver, "//a[text()='See Margin']","seeMarginTab");
		 clickElement(seeMarginTab, "See margin tab");
		 seeMarginBalanceString=common.seeMarginClearCashBalance();
		
		 seeMarBalScreenshot=ScreenshortProvider.captureScreen(driver, "SeeMarginBeforeExecution");
		
		
		  while(csvFundTransferIterator.hasNext()){
			  
			  clickElement("//a[text()='Fund Transfer']", "Fund Transfer Tab");
		  fundTransferModel=csvFundTransferIterator.next();
		  
		  try {
		  fundTranferPage.fundTransferexecute(fundTransferModel,report,seeMarBalScreenshot);
		  }catch(ElementNotInteractableException e) {
			  fundTransferReport(fundTransferModel.getReferNo(),elementNameError,"FAIL");
			  backFundTransferPage();
			  continue;
		  }catch(NullPointerException e) {
			 
			  fundTransferReport(fundTransferModel.getReferNo(),elementNameError,"FAIL");
			  backFundTransferPage();
			  continue;
		  }catch(TimeoutException e) {
			  fundTransferReport(fundTransferModel.getReferNo(),elementNameError,"FAIL");
			  backFundTransferPage();
			  continue;
		  }
		  
		  backFundTransferPage();
			/*
			 * seeMarginTab=fluentWaitCodeXpath(driver,
			 * "//a[text()='See Margin']","seeMarginTab"); clickElement(seeMarginTab,
			 * "See Margin Tab");
			 */
		  
		  }
		
		  //outputFileClose();
		
		staticWait(3000);
	}
	
	public void fundTransferReport(String referNo,String errorMsg,String result) {
		errorMsg="no";
		int referenceNo=Integer.valueOf(referNo);
		if(referenceNo==1) {
			MyTestLauncher.folderCreationObj.copyFile(folderPathArray[0],"FundTransfer");
			FunctionKeyword.apacheCodeObj.outputFileWriterHeader(folderPathArray[0],"FundTransfer",4);
		}
		String reportPath=report.reporter(driver, "FundTransfer", folderPathArray, referNo,errorMsg);
		String screenShotStr=report.captureScreen(driver, folderPathArray[2], "FundTransfer", referenceNo);
		String [] reportArray= {"No",result,reportPath,screenShotStr};
		FunctionKeyword.apacheCodeObj.outputFileWriter(reportArray, referenceNo,6);
	}
	
	public void outputFileClose() {
		FunctionKeyword.apacheCodeObj.outputExcelFileClose(MyTestLauncher.reportFolderPath[0]);
	}
	
	
	
	public void backFundTransferPage(){
		staticWait(500);
		boolean flag=true;
		String currentUrl=driver.getCurrentUrl();
		driver.navigate().back();
		if(flag) {
		driver.get("https://ewuat.edelbusiness.in/ewhtml/");
		staticWait(600);
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id=\"headerCntr\"]/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[3]/ul/li[2]/a");
		/*
		 * fundTransferTab=fluentWaitCodeXpath(driver,
		 * "//a[text()='Fund Transfer']","fundTransferTab");
		 * clickElement(fundTransferTab, "Fund transfer tab");
		 */
		}
		}
	
	
}
