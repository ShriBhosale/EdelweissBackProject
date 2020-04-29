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
	WebElement internetBankingRedioButton;
	WebElement okButton;
	
	public FundTransferPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		//mtfRadioButton=fluentWaitCodeXpath(driver, "//label[text()='MTF']");
		upiViaORCodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']");
		try {
		eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']//preceding::label[1]",15);
		}catch(TimeoutException e) {
		internetBankingRedioButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']");
		}
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']");
		
		
	}
	
	public void paymentModeSelect(String paymentMode) throws InterruptedException {
		if(paymentMode.equalsIgnoreCase("eCollect")) {
			if(eCollectRadionButton==null) {
				clickElement(internetBankingRedioButton,eCollectRadionButton.getText()+" Radio Button");
			}else {
				clickElementWithOutChecking(eCollectRadionButton, "eCollect Radio Button");
			}
		}
		else if(paymentMode.equalsIgnoreCase("UPI via QR code"))
			clickElement(upiViaORCodeRadioButton, "UPI Via or code Radio Button");
			
	}
	
	public void bankAccountSelect(String bankName) throws InterruptedException {
		
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']");
		clickElement(bankAccountRedionButton, bankName+" Radio button");
	}
	
	public void fillAmount(String amount) throws InterruptedException {
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20);
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']");
		clickElement(submitButton, "Submit button");
	}
	
	public void fundTransferexecute(FundTransferModel model) throws InterruptedException {
		Reporter.log(model.toString(), true);
		bankAccountSelect(model.getBank());
		paymentModeSelect(model.getPaymentMode());
		clickElement(okButton, "Ok button");
		fillAmount(model.getAmount());
		
	}
}
