package com.shreeya.fundtransferpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.SeleniumCoder;

public class Payment extends SeleniumCoder{
	
	WebElement userIdTextField;
	WebElement passwordTextField;
	WebElement baranchFreeRadioButton;
	WebElement loginButton;
	WebElement remarksTextfield;
	WebElement debitCardNo;
	WebElement payButton;
	WebElement failureRadioButton;
	WebElement completTransaction;
	WebElement successButtonRazor;
	WebElement failureButtonRazor;
	
	WebDriver driver;
	public Payment(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public Payment() {}
	
	public void paymentForICICI(FundTransferModel model) {
		if(model.getAccountType().equalsIgnoreCase("branchfree")) {
			baranchFreeRadioButton=fluentWaitCodeXpath("//input[@title='b2 - branchfree banking A/c']", "Baranch free radio button");
			clickElement(baranchFreeRadioButton, "Baranch free radio button");
		}
		userIdTextField=fluentWaitCodeXpath("//input[@title='User Id']", "UserId Textfield");
		clearAndSendKey(userIdTextField, model.getUserName(), "UserId Textfield");
		passwordTextField=fluentWaitCodeXpath("//input[@title='Password']", "Password textfield");
		clearAndSendKey(passwordTextField, model.getPassword(),  "Password textfield");
		loginButton=fluentWaitCodeXpath("//input[@class='login_button']", "Login Button");
		clickElement(loginButton, "Login button");
		staticWait(200);
		remarksTextfield=fluentWaitCodeXpath("//input[@title='Remark']", "Remark textfield");
		clearAndSendKey(remarksTextfield, model.getRemarks(), "Remark textfield");
		debitCardNo=fluentWaitCodeXpath("//input[@id='TranRequestManagerFG.GRID_CARD_AUTH_VALUE_1__']", "Debit cardNo textfield");
		clearAndSendKey(debitCardNo, model.getDebitCardNo(),"Debit cardNo textfield");
		payButton=fluentWaitCodeXpath("//input[@value='Pay']", "Pay Button");
		clickElement(payButton, "Pay Button");
	}
	
	public void atomPayment(FundTransferModel model) {
		if(model.getNegative().equalsIgnoreCase("Yes")) {
		failureRadioButton=fluentWaitCodeXpath("//input[@value='F']", "Failure radio button");
		clickElement(failureRadioButton,  "Failure radio button");
		}
		completTransaction=fluentWaitCodeXpath("//input[@value='Click To Complete Transaction']", "Complete Transaction button");
		clickElement(completTransaction, "Complete Transaction button");
		browserPopup(true);
	}
	
	public void razorPayment(FundTransferModel model) {
		if(model.getNegative().equalsIgnoreCase("Yes")) {
			failureButtonRazor=fluentWaitCodeXpath("//button[text()='Failure']", "Razor Failure button");
			clickElement(failureButtonRazor, "Razor Failure button");	
		}
		else {
		successButtonRazor=fluentWaitCodeXpath("//button[text()='Success']", "Razor Success button");
		clickElement(successButtonRazor, "Razor Success button");
		}
	}
	
	public void paymentCodeExecution(FundTransferModel model) {
		if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")&&model.getTranscationMode().equalsIgnoreCase("Native")) {
			paymentForICICI(model);
		}else if(model.getTranscationMode().trim().equalsIgnoreCase("atom")) {
			atomPayment(model);
		}else if(model.getTranscationMode().trim().equalsIgnoreCase("Razor")) {
			razorPayment(model);
		}
	}
}
