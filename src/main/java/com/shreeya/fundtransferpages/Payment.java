package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.seemarginpages.SeeMarginExecution;
import com.shreeya.util.DateFunction;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
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
	
	List<String> detailList;
	FundTransferCommon fundTransferCommon;
	
	Help help;
	DateFunction dateFunction;
	
	WebDriver driver;
	private WebElement cardCell;
	private WebElement crnNoTextfield;
	private WebElement mPinTextfield;
	private WebElement secureLoginButton;
	private WebElement confirmButton;
	private WebElement fundTransferSccessfullyMsg;
	private WebElement seeMarginButton;
	private WebElement placeOrderButton;
	private WebElement funtTransferTab;
	private WebElement transferStatusTab;
	private String transferStatusElementsStr;
	private String[] fundTransferDetailArray;
	private WebElement amountLabel;
	private String addFundAmountStr;
	private WebElement balanceSummary;
	private WebElement fundTransferFailMsg;
	private String fundTransferFai;
	public Payment(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		fundTransferCommon=new FundTransferCommon();
		detailList=new ArrayList<String>();
		dateFunction=new DateFunction();
	}
	
	public Payment() {}
	
	public void paymentForICICI(FundTransferModel model) {
		Reporter.log("===> paymentForICICI <===", true);
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
	
	public void atomPayment(String accountNo,FundTransferModel model) {
		Reporter.log("===> atomPayment <===", true);
		/*
		 * if(model.getNegative().equalsIgnoreCase("Yes")) {
		 * failureRadioButton=fluentWaitCodeXpath("//input[@value='F']",
		 * "Failure radio button"); clickElement(failureRadioButton,
		 * "Failure radio button"); }
		 */
		detailList.add(ScreenshortProvider.captureScreen(driver, "AtomPayment"));
		completTransaction=fluentWaitCodeXpath("//input[@value='Click To Complete Transaction']", "Complete Transaction button");
		clickElement(completTransaction, "Complete Transaction button");
		browserPopup(true);
		fundTransferResult(accountNo, model,"Atom");
	}
	
	public void razorPayment(String accountNo,FundTransferModel model) {
		Reporter.log("===> razorPayment <===", true);
		detailList=new ArrayList<String>();
		detailList.add("Transfer mode : Razor-PASS");
		/*if(model.getNegative().equalsIgnoreCase("Yes")) {
			failureButtonRazor=fluentWaitCodeXpath("//button[text()='Failure']", "Razor Failure button");
			clickElement(failureButtonRazor, "Razor Failure button");	
		}*/
		/* else { */
		detailList.add(ScreenshortProvider.captureScreen(driver, "RazorPayment"));
		successButtonRazor=fluentWaitCodeXpath("//button[text()='Success']", "Razor Success button");
		clickElement(successButtonRazor, "Razor Success button");
		fundTransferResult(accountNo, model,"Razor");
	}
	
	
	
	public void fundTransferFail(FundTransferModel model,String accountNo,String transferMode) {
		Reporter.log("====> fundTransferFail <====");
		detailList.add(ScreenshortProvider.captureScreen(driver, "FundTransferFail"));
		List<String> transferInfoList=new ArrayList<String>();
		fundTransferFailMsg=fluentWaitCodeXpath("//label[text()='Fund transfer failed']", "Fund transfer failed label");
		if(fundTransferFailMsg!=null) {
		 fundTransferFai=fetchTextFromElement(fundTransferFailMsg);
		 detailList.add(fundTransferFai+"-FAIL");
		 List<String> transferDetailList=multipleElementsTextProvider("//div[@class='impsBankDetails detSuces']//label", "Transfer detail");
			
		  for(String transferDetailStr:transferDetailList) {
			  transferInfoList.add(help.removeSpanHtmlTag(transferDetailStr)); 
		  }
		 String [] dateArray=help.commaSeparater(transferInfoList.get(0));
		detailList.add(help.separeComparePrintApp(dateArray[0], dateFunction.dataProvider()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(1), model.getAmount()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(2), fundTransferCommon.verifAccountNo(accountNo)));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(3), model.getBank()));
		detailList.add(transferInfoList.get(4));
		

		}
		
		verifyFundTransferStatus(model, accountNo, "no", "FAIL");
		
	}
	
	
	
	public void fundTransferResult(String accountNo,FundTransferModel model,String transferMode) {
		Reporter.log("===> fundTransferResult <===", true);
		staticWait(2000);
		fundTransferSccessfullyMsg=fluentWaitCodeXpath("//label[@class='successInfo']//label", "fund transfer successful msg");
		if(fundTransferSccessfullyMsg!=null) {
			fundTransferPagePASS(accountNo, model);
		}else {
			fundTransferFail(model, accountNo,transferMode);
		}
	}

	private void payementForKotak(FundTransferModel model,String accountNo) {
		Reporter.log("===> payementForKotak <====", true);
		detailList.add("Transcation mode : Native-PASS");
		crnNoTextfield=fluentWaitCodeXpath("//input[@id='crn']", "crnNoTextfield");
		clearAndSendKey(crnNoTextfield, model.getUserName(),"crnNoTextfield");
		mPinTextfield=fluentWaitCodeXpath("//input[@id='pswd']", "mPinTextfield");
		clearAndSendKey(mPinTextfield, model.getPassword(),"mPinTextfield");
		secureLoginButton=fluentWaitCodeXpath("//a[@id='secure-login01']", "Secure Login button");
		clickElement(secureLoginButton, "Secure Login button");
		detailList.add(ScreenshortProvider.captureScreen(driver, "KotakConfirmationPage"));
		confirmButton=fluentWaitCodeXpath("//h3[text()='Transaction Details']//preceding::a[@id='next-step2']", "confirmButton");
		clickElement(confirmButton, "confirm button");
		model.setBank("Kotak Mahindra");
		fundTransferResult(accountNo, model,"Native");
	}
	
	public void fundTransferPagePASS(String accountNo,FundTransferModel model) {
		Reporter.log("===> fundTransferPagePASS <===", true);
		staticWait(3000);
		
		List<String> transferInfoList=new ArrayList<String>();
		detailList.add(ScreenshortProvider.captureScreen(driver, "TranscationSuccessfullyPage"));		
		fundTransferSccessfullyMsg=fluentWaitCodeXpath("//label[@class='successInfo']//label", "fund transfer successful msg");
		detailList.add(help.commpareTwoString(fetchTextFromElement(fundTransferSccessfullyMsg), "Fund transferred successfully"));
		List<String> transferDetailList=multipleElementsTextProvider("//div[@class='impsBankDetails detSuces']//label", "Transfer detail");
		
		  for(String transferDetailStr:transferDetailList) {
			  transferInfoList.add(help.removeSpanHtmlTag(transferDetailStr)); 
		  }
		 String [] dateArray=help.commaSeparater(transferInfoList.get(0));
		detailList.add(help.separeComparePrintApp(dateArray[0], dateFunction.dataProvider()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(1), model.getAmount()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(2), fundTransferCommon.verifAccountNo(accountNo)));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(3), model.getBank()));
		detailList.add(transferInfoList.get(4));
		String [] referNoArray=help.separater(transferInfoList.get(4), ":");
		seeMarginButton=fluentWaitCodeXpath("//div[@class='impsBankDetails detSuces']//following::a[text()='See Margin']", "See margin button");
		clickElement(seeMarginButton, "see Margin Button");
		/*
		 * placeOrderButton=
		 * fluentWaitCodeXpath("//div[@class='impsBankDetails detSuces']//following::a[text()='Place an Order']"
		 * , "Place order button"); clickElement(placeOrderButton,
		 * "Place order button");
		 */
		verifyFundTransferStatus(model,accountNo,referNoArray[1],"PASS");
	}
	
	public void verifyFundTransferStatus(FundTransferModel model,String accountNo,String referNo,String fundTransferResult) {
		staticWait(4000);
		Reporter.log("===> verifyFundTransferStatus <===", true);
		detailList.add("====@@> Transfer status <@@======");
		List<String> transferStatusList=new ArrayList<String>();
		funtTransferTab=fluentWaitCodeXpath("//a[text()='Fund Transfer']",200, "fund transfer tag");
		clickElement(funtTransferTab, "Fund transfer tab");
		transferStatusTab=fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "transfer status tab");
		clickElement(transferStatusTab, "transfer status tab");
		addFundTransferAmount(model.getAmount(),fundTransferResult);
		staticWait(5000);
		detailList.add(ScreenshortProvider.captureScreen(driver, "TransferStatusPage"));
		transferStatusElementsStr="//a[text()='Apply']//following::div[@class='table-row ng-scope'][1]//div//div//span";
		List<WebElement> transferStatusElementList=multipleElementLocator(transferStatusElementsStr, "Transfer status element");
		for(WebElement element:transferStatusElementList) {
			transferStatusList.add(getValueFromAttribute(element, "value","Transer Status Element"));
		}
		String [] dateArray=help.commaSeparater(dateFunction.dataProvider());
		
		detailList.add("Date & time: "+help.commpareTwoString(transferStatusList.get(0), dateArray[0]));
		
		detailList.add("Amount : "+help.commpareTwoString(transferStatusList.get(2), model.getAmount()));
		detailList.add("bank name : "+help.commpareTwoString(transferStatusList.get(3), model.getBank()));
		detailList.add("Account no : "+help.commpareTwoString(transferStatusList.get(4), accountNo));
		detailList.add("Refer no : "+help.commpareTwoString(transferStatusList.get(5), referNo.trim()));
		detailList.add("Payment mode : "+help.commpareTwoString(transferStatusList.get(6), model.getPaymentMode()));
		if(fundTransferResult.equalsIgnoreCase("PASS"))
		detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Processed"));
		else
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Request Sent"));
		detailList.add("Ledger : "+help.commpareTwoString(transferStatusList.get(9), "NORMAL"));
		if(fundTransferResult.equalsIgnoreCase("PASS"))
			detailList.add("Remark : "+help.commpareTwoString(transferStatusList.get(10), "PROCESSED"));
		else
			detailList.add("Remark : "+help.commpareTwoString(transferStatusList.get(10), "Request Sent"));
	}
	
	public void addFundTransferAmount(String amount,String result) {
		Reporter.log("===> addFundTransferAmount <===", true);
		
		String addAmount=help.bigDataAddition(FundTransferPage.addFundAmountStr, amount);
		
		amountLabel=fluentWaitCodeXpath("//label[@class='amtBold ng-binding ng-scope']", "Add fund amount label");
		 addFundAmountStr=fetchTextFromElement(amountLabel).replace("Rs", "").trim();
		 balanceSummary=fluentWaitCodeXpath("//label[@class='balSumry ng-binding']", "Balance Summary");
		 if(result.equalsIgnoreCase("PASS"))
		 detailList.add(help.compareBigNo(addFundAmountStr, addAmount, help.removeHtmlText(fetchTextFromElement(balanceSummary))));
		 else
			 detailList.add(help.compareBigNo(FundTransferPage.addFundAmountStr, addAmount, help.removeHtmlText(fetchTextFromElement(balanceSummary)))); 
		 
	}
	
	

	private void payementForCitibank(String accountNo,FundTransferModel model) {
		Reporter.log("===> payementForCitibank <===", true);
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
		Reporter.log("===> cardEnterNo <===",true);
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
		Reporter.log("===> expiryDate <===", true);
		String [] expireDateArray=help.commaSeparater(expireDate);
		monthDropDownStr="//div[@id='CITI_CREDIT_DIV']//select[@name='HtmlMonth']//option[@value='0"+expireDateArray[0]+"']";
		yearDropDownStr="//div[@id='CITI_CREDIT_DIV']//select[@name='HtmlYear']//option[@value="+expireDateArray[1]+"]";
		monthDropDown=fluentWaitCodeXpath(monthDropDownStr, expireDateArray[0]+" month");
		clickElement(monthDropDown,expireDateArray[0]+" month");
		yearDropDown=fluentWaitCodeXpath(yearDropDownStr, expireDateArray[1]+"Year");
		clickElement(yearDropDown,expireDateArray[1]+"Year");
	}
	
	public String urlChecking() {
		String currentUrl=null;
		do {
			currentUrl=currentUrl();
			staticWait(1000);
		}while(currentUrl.contains("edelbusiness"));
			return currentUrl;
	}
	
	public List<String> paymentCodeExecution(FundTransferModel model,String accountNo) {
		Reporter.log("===> paymentCodeExecution <===", true);
		staticWait(5000);
		String currentUrl=urlChecking();
		ScreenshortProvider.captureScreen(driver, "PaymentUrlChecking");
		Reporter.log("Url : "+currentUrl, true);
		 if(currentUrl.contains("AtomBank")) {
			atomPayment(accountNo,model);
		}else if(currentUrl.contains("razorpay")) {
			razorPayment(accountNo,model);
		}else if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")) {
			paymentForICICI(model);
		}else if(model.getBank().equalsIgnoreCase("Citibank Na")) {
			payementForCitibank(accountNo,model);
		}else if(model.getBank().equalsIgnoreCase("Kotak Mahindra Bank")) {
			payementForKotak(model,accountNo);
		}
		 return detailList;
	}
}
