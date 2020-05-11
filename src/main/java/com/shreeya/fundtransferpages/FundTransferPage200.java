package com.shreeya.fundtransferpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.SeleniumCoder;

public class FundTransferPage200 extends SeleniumCoder{
	WebDriver driver;
	WebElement mtfRadionButton;
	WebElement bankAccountRadionButton;
	WebElement internetBankingRadionButton;
	WebElement eCollectRadionButton;
	WebElement upiViaQACodeRadioButton;
	WebElement okButton;
	WebElement amountToTransferTextField;
	WebElement submitButton;
	WebElement bankAccountRedionButton;
	String errorMsg="error msg";
	private WebElement errorMsgLable;
	private WebElement userNameButton;
	private WebElement userNameTextfield;
	private WebElement secureLoginButton;
	
	public FundTransferPage200(WebDriver driver) {
		super(driver);
		this.driver=driver; 
	}
	
	public void paymentModeSelection(String bankName,String paymentMode) throws InterruptedException {
		if(bankName.equalsIgnoreCase("kotak bank")||bankName.equalsIgnoreCase("CANARA BANK")&&paymentMode.equalsIgnoreCase("Internet Banking")) {
			internetBankingRadionButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']","Internet Banking Radion Button");
			clickElement(internetBankingRadionButton, "Internet banking radio button");
		}else if(paymentMode.equalsIgnoreCase("eCollect")){
			eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='eCollect']","eCollect Radion Button");
			clickElement(eCollectRadionButton, "eCollect radio button");
		}
		if(paymentMode.equalsIgnoreCase("UPI via QR code")) {
		upiViaQACodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']","upi Via QA Code Radio Button");
		clickElement(upiViaQACodeRadioButton, "UPI via QA code radio button");
		}
		
		
	}
	
	public void amountFill(String bankName,String amount) throws InterruptedException {
		if(!(bankName.equalsIgnoreCase("Kotak Mahindra Bank")||bankName.equalsIgnoreCase("CANARA BANK")||bankName.equalsIgnoreCase("YES Bank")||bankName.contains("ICICI BANK"))) {
			okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']","Ok button");
			clickElement(okButton, "Ok button");
		}
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount to transfer textfield");
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		Thread.sleep(500);
		if(elementPresentOrNot(driver, "//input[@name='amt']//following::span[1]","xpath","Amount textfield")) {
			 errorMsgLable=fluentWaitCodeXpath(driver, "//input[@name='amt']//following::span[1]","Amount textfield");
			 errorMsg=fetchTextFromElement(errorMsgLable,"error msg");
		}else {
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","Submit Button");
		clickElement(submitButton, "Submit button");
		}
		
	}
	
	public void bankAccountSelect(String bankName) throws InterruptedException {
		if(!bankName.equalsIgnoreCase("HDFC BANK LTD.")) {
		bankAccountRadionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName+" Radion button");
		}else {
			bankAccountRadionButton=fluentWaitCodeXpath(driver, "//span[text()='HDFC BANK LTD.']//following::span[text()='50100101676033']",bankName+" Radion button");
		}
		if(!bankName.equalsIgnoreCase("Kotak Mahindra Bank"))
		clickElement(bankAccountRadionButton, bankName+" Radio button");
	}
	
	public String paymentMod(String paymentMode,String bankName) throws InterruptedException {
		String amountVerify=null;
		if(paymentMode.equalsIgnoreCase("UPI via QR code")&& bankName.equalsIgnoreCase("YES Bank")) {
			WebElement amountVerifyLable=fluentWaitCodeXpath(driver, "//span[@class='rsSym']//following::label[1]", "amount verfiy");
			 amountVerify=fetchTextFromElement(amountVerifyLable, "amount verfiy");
			//clickElement(amountVerifyLable, "amount verify label");
		}
		
		
		return amountVerify;
	}
	
	public void nextPage(FundTransferModel model) throws InterruptedException {
		Reporter.log("Current url: "+driver.getCurrentUrl(),true);
		if(model.getPaymentMode().equalsIgnoreCase("Internet Banking")&& model.getBank().equalsIgnoreCase("Kotak Mahindra Bank")) {
			userNameButton=fluentWaitCodeXpath(driver, "//a[text()='Username']", "Username button");
			clickElement(userNameButton, "User name button");
			userNameTextfield=fluentWaitCodeXpath(driver, "//a[text()='Username']//following::input[@id='crnAlias']", "Username textfield");
			clearAndSendKey("//a[text()='Username']//following::input[@id='crnAlias']", model.getUserName(), "Username textfield");
			userNameTextfield=fluentWaitCodeXpath(driver, "//a[text()='Username']//following::input[@id='pswd1']", "Password textfield");
			clearAndSendKey("//a[text()='Username']//following::input[@id='pswd1']", model.getPassword(), "Password textfield");
			secureLoginButton=fluentWaitCodeXpath(driver, "//a[text()='SECURE 	LOGIN']", "SECURE 	LOGIN button");
			clickElement(secureLoginButton, "SECURE 	LOGIN button");
		}
	}
	
	public String fundTransferexecute(FundTransferModel model) throws InterruptedException {
		bankAccountSelect(model.getBank());
		paymentModeSelection(model.getPaymentMode(), model.getPaymentMode());
		amountFill(model.getBank(),model.getAmount());
		paymentMod(model.getPaymentMode(), model.getBank());
		nextPage(model);
		
		return errorMsg;
	}
	
	
	
	
	
	

}
