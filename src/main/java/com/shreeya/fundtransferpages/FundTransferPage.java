package com.shreeya.fundtransferpages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.Help;
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
	WebElement qickSelectAmountLabel;
	
	Payment payment;
	FundTransferCommon fundTransferCommon;
	Help help;
	public FundTransferPage() {
		
	}
	
	public FundTransferPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		payment=new Payment(driver);
		fundTransferCommon=new FundTransferCommon();
	}
	
	public void paymentModeSelect(String paymentMode,String bankName) throws InterruptedException {
		try {
			eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']//preceding::label[1]",15,"eCollect Radion Button");
			}catch(TimeoutException e) {
			internetBankingRadioButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']","internetBankingRadioButton");
			}
		if(!(bankName.equalsIgnoreCase("HDFC BANK LTD")||bankName.equalsIgnoreCase("Yes Bank"))){
			if(paymentMode.equalsIgnoreCase("Internet Banking")) 
			Reporter.log("Internet Banking radion button already selected ",true);
			else if(paymentMode.equalsIgnoreCase("eCollect")) {
				Reporter.log("eCollect Payment mode is not for "+bankName+" bank");
			}
			else if(paymentMode.equalsIgnoreCase("UPI via QR code")) {
				upiViaORCodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']","UPI via OR Code radio button");
				clickElement(upiViaORCodeRadioButton, "UPI via Or code radio button");
			}
			else if(paymentMode.equalsIgnoreCase("UPI")) {
				upiRadioButton=fluentWaitCodeXpath(driver,"//label[text()='UPI via QR code']//following::label[1]","upi Radio Button");
				clickElement(upiRadioButton, "UPI radio button");
			}
		}
		else if(bankName.equalsIgnoreCase("Yes Bank")) {
			yesBankAlert=fluentWaitCodeXpath(driver, "//span[text()=' Yes Bank']//following::span[6]","yesBank Alert");
			fetchTextFromElement(yesBankAlert, "yesBankAlert");
		}
			
			
	}
	
	public void bankAccountSelect(FundTransferModel model) throws InterruptedException {
		staticWait(4000);
		String bankName=fundTransferCommon.bankNameGiver(model.getBank());
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		if(bankAccountRedionButton.isEnabled()==false)
			clickElement(bankAccountRedionButton, bankName+" Radio button");
		model.setBank(bankName);
	}
	
	public void fillAmount(String amount) throws InterruptedException {
		String [] amountArray=fundTransferCommon.amountEnter(amount);
		if(!amountArray[1].equalsIgnoreCase("EnterAmount")) {
			amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
			clearAndSendKey(amountToTransferTextField,amountArray[1] , "Amount To Transfer TextField");
			}
		if(!amountArray[0].equalsIgnoreCase("QickSelectAmount")) {
			String [] qickSelectAmountArray=help.commaSeparater(amountArray[0]);
			for(String qickSelect:qickSelectAmountArray) {
			String quickSelectAmountStr="//a[@gtmdir-label='Quick Select | "+qickSelect+"']";
			qickSelectAmountLabel=fluentWaitCodeXpath(quickSelectAmountStr, qickSelect+" QickSelect ");
			clickElement(qickSelectAmountLabel,  qickSelect+" QickSelect ");
			}
		}
		
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "Submit button");
	}
	
	
	
	
	
	public void fundTransferexecute(FundTransferModel model) throws InterruptedException {
		Reporter.log(model.toString(), true);
		bankAccountSelect(model);
		paymentModeSelect(model.getPaymentMode(),model.getBank());
		if(model.getBank().equalsIgnoreCase("HDFC BANK LTD")) {
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']","ok Button");
		clickElement(okButton, "Ok button");
		}
		fillAmount(model.getAmount());
		payment.paymentCodeExecution(model);
	}
}
