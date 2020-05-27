package com.shreeya.fundtransferpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.Help;
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
	WebElement monthDropDown;
	WebElement yearDropDown;
	WebElement CardSecurityCodeTextfield;
	WebElement submitButton;
	WebElement otpTexfield;
	WebElement nextButton;
	
	String monthDropDownStr;
	String yearDropDownStr;
	
	FundTransferCommon fundTransferCommon;
	
	Help help;
	
	WebDriver driver;
	private WebElement cardCell;
	public Payment(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		fundTransferCommon=new FundTransferCommon();
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
		/*
		 * if(model.getNegative().equalsIgnoreCase("Yes")) {
		 * failureRadioButton=fluentWaitCodeXpath("//input[@value='F']",
		 * "Failure radio button"); clickElement(failureRadioButton,
		 * "Failure radio button"); }
		 */
		completTransaction=fluentWaitCodeXpath("//input[@value='Click To Complete Transaction']", "Complete Transaction button");
		clickElement(completTransaction, "Complete Transaction button");
		browserPopup(true);
	}
	
	public void razorPayment(FundTransferModel model) {
		/*if(model.getNegative().equalsIgnoreCase("Yes")) {
			failureButtonRazor=fluentWaitCodeXpath("//button[text()='Failure']", "Razor Failure button");
			clickElement(failureButtonRazor, "Razor Failure button");	
		}*/
		/* else { */
		successButtonRazor=fluentWaitCodeXpath("//button[text()='Success']", "Razor Success button");
		clickElement(successButtonRazor, "Razor Success button");
		/* } */
	}
	
	public void paymentCodeExecution(FundTransferModel model) {
		String currentUrl=currentUrl();
		 if(currentUrl.contains("AtomBank")) {
			atomPayment(model);
		}else if(currentUrl.contains("razorpay")) {
			razorPayment(model);
		}else if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")) {
			paymentForICICI(model);
		}else if(model.getBank().equalsIgnoreCase("Citibank Na")) {
			payementForCitibank(model);
		}
	}

	private void payementForCitibank(FundTransferModel model) {
		String [] exprieDate=help.commaSeparater(model.getExpireDate());
		cardEnterNo(model.getDebitCardNo());
		expiryDate(model.getExpireDate());
		CardSecurityCodeTextfield=fluentWaitCodeXpath("//div[@id='CITI_CREDIT_DIV']//input[@name='HtmlCVVNum']", "card security code");
		clearAndSendKey(CardSecurityCodeTextfield, model.getCardSecurityCode(), "Card security code");
		submitButton=fluentWaitCodeXpath("//input[@id='submitciti']", "Submit button");
		clickElement(submitButton, "Submit button");
		
		  otpTexfield=fluentWaitCodeXpath("//input[@id='otpcode']",150, "OTP texfield");
		  clearAndSendKey(otpTexfield, model.getOtp(), "Otp texfield");
		 nextButton=fluentWaitCodeXpath("//a[@id='next']", "next button");
		 clickElement(nextButton, "Next");
	}
	
	public void cardEnterNo(String cardNo) {
		String cardCellxapth;
		Reporter.log("Card No : "+cardNo, true);
		int cellNo=0;
		String [] cardArray=fundTransferCommon.cardNumberArray(cardNo);
		for(int i=0;i<cardArray.length;i++) {
			cellNo=i+1;
			cardCellxapth="//input[@name='cardNum"+cellNo+"']";
			cardCell=fluentWaitCodeXpath(cardCellxapth,120,"Card cell "+i+1);
			clearAndSendKey(cardCellxapth,cardArray[i],"Card cell "+i+1);
		}
	}
	
	public void expiryDate(String expireDate) {
		String [] expireDateArray=help.commaSeparater(expireDate);
		monthDropDownStr="//div[@id='CITI_CREDIT_DIV']//select[@name='HtmlMonth']//option[@value='0"+expireDateArray[0]+"']";
		yearDropDownStr="//div[@id='CITI_CREDIT_DIV']//select[@name='HtmlYear']//option[@value="+expireDateArray[1]+"]";
		monthDropDown=fluentWaitCodeXpath(monthDropDownStr, expireDateArray[0]+" month");
		clickElement(monthDropDown,expireDateArray[0]+" month");
		yearDropDown=fluentWaitCodeXpath(yearDropDownStr, expireDateArray[1]+"Year");
		clickElement(yearDropDown,expireDateArray[1]+"Year");
	}
}
