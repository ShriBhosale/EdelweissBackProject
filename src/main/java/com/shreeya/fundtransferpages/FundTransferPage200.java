package com.shreeya.fundtransferpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	String errorMsg;
	private WebElement errorMsgLable;
	
	public FundTransferPage200(WebDriver driver) {
		super(driver);
		this.driver=driver; 
	}
	
	public void paymentModeSelection(String bankName,String paymentMode) throws InterruptedException {
		if(bankName.equalsIgnoreCase("kotak bank")||bankName.equalsIgnoreCase("CANARA BANK")&&paymentMode.equalsIgnoreCase("Internet Banking")) {
			internetBankingRadionButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']");
			clickElement(internetBankingRadionButton, "Internet banking radio button");
		}else if(paymentMode.equalsIgnoreCase("eCollect")){
			eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='eCollect']");
			clickElement(eCollectRadionButton, "eCollect radio button");
		}
		upiViaQACodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']");
		clickElement(upiViaQACodeRadioButton, "UPI via QA code radio button");
		
		
	}
	
	public void amountFill(String bankName,String amount) throws InterruptedException {
		if(!(bankName.equalsIgnoreCase("kotak bank")||bankName.equalsIgnoreCase("CANARA BANK"))) {
			okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']");
			clickElement(okButton, "Ok button");
		}
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20);
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		if(elementPresentOrNot(driver, "//input[@name='amt']//following::span[1]","xpath")) {
			 errorMsgLable=fluentWaitCodeXpath(driver, "//input[@name='amt']//following::span[1]");
			 errorMsg=fetchTextFromElement(errorMsgLable,"error msg");
		}else {
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']");
		clickElement(submitButton, "Submit button");
		}
		
	}
	
	public void bankAccountSelect(String bankName) throws InterruptedException {
		if(!bankName.equalsIgnoreCase("HDFC BANK LTD.")) {
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']");
		}else {
			bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='HDFC BANK LTD.']//following::span[text()='50100101676033']");
		}
		clickElement(bankAccountRedionButton, bankName+" Radio button");
	}
	
	public String fundTransferexecute(FundTransferModel model) throws InterruptedException {
		bankAccountSelect(model.getBank());
		paymentModeSelection(model.getPaymentMode(), model.getPaymentMode());
		amountFill(model.getBank(),model.getAmount());
		return errorMsg;
	}
	
	
	
	
	
	

}
