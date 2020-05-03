package com.shreeya.fundtransferpages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.SeleniumCoder;

public class FundTransferPage extends SeleniumCoder {

	WebDriver driver;
	
	WebElement mtfRadioButton;
	WebElement eCollectRadionButton;
	WebElement upiViaORCodeRadioButton;
	WebElement amountToTransferTextField;
	WebElement submitButton;
	WebElement bankAccountRedionButton;
	WebElement internetBankingRadioButton;
	WebElement okButton;
	WebElement yesBankAlert;
	WebElement upiRadioButton;
	
	public FundTransferPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		//mtfRadioButton=fluentWaitCodeXpath(driver, "//label[text()='MTF']");
		upiViaORCodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']","UPI via OR Code radio button");
		try {
		eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']//preceding::label[1]",15,"eCollect Radion Button");
		}catch(TimeoutException e) {
		internetBankingRadioButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']","internetBankingRadioButton");
		}
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']","ok Button");
		upiRadioButton=fluentWaitCodeXpath(driver,"//label[text()='UPI via QR code']//following::label[1]","upi Radio Button");
		
	}
	
	public void paymentModeSelect(String paymentMode,String bankName) throws InterruptedException {
		if(!(bankName.equalsIgnoreCase("HDFC BANK LTD")||bankName.equalsIgnoreCase("Yes Bank"))){
			if(paymentMode.equalsIgnoreCase("Internet Banking")) 
			Reporter.log("Internet Banking radion button already selected ",true);
			else if(paymentMode.equalsIgnoreCase("eCollect"))
				Reporter.log("eCollect Payment mode is not for "+bankName+" bank");
			else if(paymentMode.equalsIgnoreCase("UPI via QR code"))
				clickElement(upiViaORCodeRadioButton, "UPI via Or code radio button");
			else if(paymentMode.equalsIgnoreCase("UPI"))
				clickElement(upiRadioButton, "UPI radio button");
		}
		else if(bankName.equalsIgnoreCase("Yes Bank")) {
			yesBankAlert=fluentWaitCodeXpath(driver, "//span[text()=' Yes Bank']//following::span[6]","yesBank Alert");
			fetchTextFromElement(yesBankAlert, "yesBankAlert");
		}
			
			
	}
	
	public void bankAccountSelect(String bankName) throws InterruptedException {
		
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		clickElement(bankAccountRedionButton, bankName+" Radio button");
	}
	
	public void fillAmount(String amount) throws InterruptedException {
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20);
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "Submit button");
	}
	
	
	
	public void fundTransferexecute(FundTransferModel model) throws InterruptedException {
		Reporter.log(model.toString(), true);
		bankAccountSelect(model.getBank());
		paymentModeSelect(model.getPaymentMode(),model.getBank());
		if(model.getBank().equalsIgnoreCase("HDFC BANK LTD")) {
		clickElement(okButton, "Ok button");
		}
		fillAmount(model.getAmount());
		
	}
}
