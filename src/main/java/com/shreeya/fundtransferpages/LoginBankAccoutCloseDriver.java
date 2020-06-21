package com.shreeya.fundtransferpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.model.FundTransferModel;
import com.shreeya.model.LatestLoginModel;
import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class LoginBankAccoutCloseDriver extends SeleniumCoder {

	WebDriver driver;
	LoginPage loginPage;
	BrowserLaunch launch;
	FundTransferCommon common;
	Payment payment;
	FundTransferModel fundTransferModel;
	private String transferModeStr;
	private String bankLoginPageScreenshot;
	private WebElement loginButton;
	private WebElement transferStatusTab;
	private WebElement accountNoLabel;
	private String accountNoStr;
	private WebElement popupButton;
	
	
	public LoginBankAccoutCloseDriver(WebDriver driver) {
		super(driver);
		//this.driver=driver;
		launch=new BrowserLaunch();
		common=new FundTransferCommon(driver);
		payment=new Payment(driver);
		fundTransferModel=new FundTransferModel();
		loginPage=new LoginPage(driver);
	}
	
	public List<String> loginBankAcountCloseDriver(List<String> detailList,LatestLoginModel model) {
		detailList.add("@@> Verify payin for atom PG flow when we close the browser during the process. <@@");
		//common.backFundTransferPage();
		accountNoLabel = fluentWaitCodeXpath("//span[text()='ICICI BANK LTD']//following::span[1]","ICIC bank accountNo");
		accountNoStr=fetchTextFromElement(accountNoLabel);
		common.submitAddFundForm("ICICI BANK LTD", "101");
		transferModeStr=common.checkingTransferMode();
		
		/*
		 * if(transferModeStr.equalsIgnoreCase("Atom")) {
		 * 
		 * payment.atomPayment("No",fundTransferModel, "", true); }
		 */
		 
		
		
		//detailList.add(ScreenshortProvider.captureScreen(driver, "AtomicPage"));
			Reporter.log("Driver ===> "+driver, true);
		  closeBrowser();
		
		  this.driver=launch.browserLaunch("");
		  
		  FunctionKeyword.driver=driver;
		  LoginBankAccoutCloseDriver loginBankAccoutCloseDriver=new LoginBankAccoutCloseDriver(driver);
		  Reporter.log("After launch Driver ===> "+driver, true);
			/*
			 * popupButton = fluentWaitCodeXpath(driver,
			 * "//button[text()='No thanks']",40,"No thans popup button");
			 * clickElement(popupButton,"No thans popup button");
			 */
		 
		loginButton = fluentWaitCodeXpath(driver, "//span[text()='Login']","Login button");
		if(loginButton==null) {
			detailList.add("Account successfully logout-PASS");
		}else {
			detailList.add("Account still login after browser close-FAIL");
		}
		
		loginPage.loginCodeExecution("normal", model);
		common.backFundTransferPage();
		transferStatusTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "transfer status tab");
		clickElement(transferStatusTab, "transfer status tab");
		payment.verifyFundTransferStatus(fundTransferModel, accountNoStr, "not", "PASS");
		return detailList;
	}

	
}
