package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.commonpage.CommonOrderDetail;
import com.shreeya.commonpage.PlaceOrder;
import com.shreeya.model.FundTransferModel;
import com.shreeya.model.OrderPlaceModel;
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
	public static List<String> transferDetailList;
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
	private WebElement seeMarginBalance;
	private WebElement fundTransferTab;
	
	PlaceOrder placeOrder;
	CommonOrderDetail orderDetail;
	private String orderDetialList;
	private String orderDetialScreenshot;
	private String[] orderDetialArray;
	private WebElement closeButton;
	private WebElement cancelButton;
	private String screenshotStr;
	private WebElement logIdTextfield;
	private WebElement passwordTextfield;
	
	private WebElement errorMsgLabel;
	private String transferStatusPageScreenshot;
	
	public static String iciciFundtransferStatus;
	public static String iciciFundtransferRemark;
	public static List<String> screenshotList;
	
	public Payment(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		fundTransferCommon=new FundTransferCommon();
		detailList=new ArrayList<String>();
		dateFunction=new DateFunction();
		placeOrder=new PlaceOrder(driver);
		orderDetail=new CommonOrderDetail(driver);
		screenshotList=new ArrayList<String>();
	}
	
	public Payment() {}
	
	public void paymentForICICI(FundTransferModel model,String optionAfterTransfer) {
		Reporter.log("===> paymentForICICI <===", true);
		detailList.add(ScreenshortProvider.captureScreen(driver, "NativeICICIBank"));
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
	
	public List<String> atomPayment(String accountNo,FundTransferModel model,String optionAfterTransfer,Boolean failureOrSuccess) {
		Reporter.log("===> atomPayment <===", true);
		
		  if(failureOrSuccess==false) {
			  failureRadioButton=fluentWaitCodeXpath("//input[@value='F']", "Failure radio button");
			  clickElement(failureRadioButton, "Failure radio button"); 
			  }
		 
		detailList.add(ScreenshortProvider.captureScreen(driver, "AtomPayment"));
		screenshotList.add(ScreenshortProvider.captureScreen(driver, "AtomPayment"));
		completTransaction=fluentWaitCodeXpath("//input[@value='Click To Complete Transaction']", "Complete Transaction button");
		clickElement(completTransaction, "Complete Transaction button");
		if(!accountNo.equalsIgnoreCase("No")) {
			browserPopup(true);
		
		fundTransferResult(accountNo, model,"Atom",optionAfterTransfer);
		}
		return detailList;
	}
	
	public void razorPayment(String accountNo,FundTransferModel model,String optionAfterTransfer,Boolean failureOrSuccess) {
		Reporter.log("===> razorPayment <===", true);
		
		detailList.add("Transfer mode : Razor-PASS");
		if(failureOrSuccess==false) {
			failureButtonRazor=fluentWaitCodeXpath("//button[text()='Failure']", "Razor Failure button");
			clickElement(failureButtonRazor, "Razor Failure button");	
		}
		 else { 
		detailList.add(ScreenshortProvider.captureScreen(driver, "RazorPayment"));
		successButtonRazor=fluentWaitCodeXpath("//button[text()='Success']", "Razor Success button");
		clickElement(successButtonRazor, "Razor Success button");
		 }
		fundTransferResult(accountNo, model,"Razor",optionAfterTransfer);
	}
	
	
	
	public void fundTransferFail(FundTransferModel model,String accountNo,String transferMode) {
		Reporter.log("====> fundTransferFail <====");
		detailList.add(ScreenshortProvider.captureScreen(driver, "FundTransferFail"));
		List<String> transferInfoList=new ArrayList<String>();
		fundTransferFailMsg=fluentWaitCodeXpath("//label[text()='Fund transfer failed']", "Fund transfer failed label");
		if(fundTransferFailMsg!=null) {
		 fundTransferFai=fetchTextFromElement(fundTransferFailMsg);
		 if(!model.getReferNo().equalsIgnoreCase("-1"))
		 detailList.add(fundTransferFai+"-FAIL");
		 else
			 detailList.add(fundTransferFai+"-PASS"); 
		 List<String> transferDetailList=multipleElementsTextProvider("//div[@class='impsBankDetails detSuces']//label", "Transfer detail");
			
		  for(String transferDetailStr:transferDetailList) {
			  transferInfoList.add(help.removeSpanHtmlTag(transferDetailStr)); 
		  }
		 String [] dateArray=help.commaSeparater(transferInfoList.get(0));
		detailList.add(help.separeComparePrintApp(dateArray[0], dateFunction.dataProvider()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(1), model.getAmount()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(2), fundTransferCommon.verifAccountNo(accountNo,model.getBank())));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(3), fundTransferCommon.transferSuccessullBank(model.getBank())));
		detailList.add(transferInfoList.get(4));
		

		}
		if(!model.getReferNo().equalsIgnoreCase("-1"))
		verifyFundTransferStatus(model, accountNo, "no", "FAIL");
		
	}
	
	
	
	public void fundTransferResult(String accountNo,FundTransferModel model,String transferMode,String optionAfterTransfer) {
		Reporter.log("===> fundTransferResult <===", true);
		staticWait(2000);
		fundTransferSccessfullyMsg=fluentWaitCodeXpath("//label[@class='successInfo']//label", "fund transfer successful msg");
		if(fundTransferSccessfullyMsg!=null) {
			fundTransferPagePASS(accountNo, model,optionAfterTransfer);
		}else {
			fundTransferFail(model, accountNo,transferMode);
		}
	}
	
	public String enterKotakCredential(String crnStr,String pinStr,Boolean confirmOrNot) {
		crnNoTextfield=fluentWaitCodeXpath("//input[@id='crn']", "crnNoTextfield");
		clearAndSendKey(crnNoTextfield, crnStr,"crnNoTextfield");
		mPinTextfield=fluentWaitCodeXpath("//input[@id='pswd']", "mPinTextfield");
		clearAndSendKey(mPinTextfield, pinStr,"mPinTextfield");
		secureLoginButton=fluentWaitCodeXpath("//a[@id='secure-login01']", "Secure Login button");
		clickElement(secureLoginButton, "Secure Login button");
		screenshotStr=ScreenshortProvider.captureScreen(driver, "KotakConfirmationPage");
		confirmButton=fluentWaitCodeXpath("//h3[text()='Transaction Details']//preceding::a[@id='next-step2']", "confirm Button");
		cancelButton=fluentWaitCodeXpath("//h3[text()='Transaction Details']//preceding::a[@id='cancelEnable']", "Cancel Button");
		if(confirmOrNot)
		clickElement(confirmButton, "confirm button");
		else
			clickElement(cancelButton, "cancel button");
		return screenshotStr;
	}
	
	public String enterEquitasCredential() {
	
		return null;
	}

	private void payementForKotak(FundTransferModel model,String accountNo,String optionAfterTransfer) {
		Reporter.log("===> payementForKotak <====", true);
		detailList.add("Transcation mode : Native-PASS");
		enterKotakCredential(model.getUserName(),model.getPassword(),true);
		detailList.add(screenshotStr);
		//model.setBank("Kotak Mahindra");
		fundTransferResult(accountNo, model,"Native",optionAfterTransfer);
	}
	
	public void fundTransferPagePASS(String accountNo,FundTransferModel model,String optionAfterTransfer) {
		Reporter.log("===> fundTransferPagePASS <===", true);
		staticWait(3000);
		
		List<String> transferInfoList=new ArrayList<String>();
		detailList.add(ScreenshortProvider.captureScreen(driver, "TranscationSuccessfullyPage"));	
		screenshotList.add(ScreenshortProvider.captureScreen(driver, "TranscationSuccessfullyPage"));
		fundTransferSccessfullyMsg=fluentWaitCodeXpath("//label[@class='successInfo']//label", "fund transfer successful msg");
		detailList.add(help.commpareTwoString(fetchTextFromElement(fundTransferSccessfullyMsg), "Fund transferred successfully"));
		List<String> transferDetailList=multipleElementsTextProvider("//div[@class='impsBankDetails detSuces']//label", "Transfer detail");
		
		  for(String transferDetailStr:transferDetailList) {
			  transferInfoList.add(help.removeSpanHtmlTag(transferDetailStr)); 
		  }
		 String [] dateArray=help.commaSeparater(transferInfoList.get(0));
		detailList.add(help.separeComparePrintApp(dateArray[0], dateFunction.dataProvider()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(1), model.getAmount()));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(2), fundTransferCommon.verifAccountNo(accountNo,model.getBank())));
		detailList.add(help.separeComparePrintApp(transferInfoList.get(3),fundTransferCommon.transferSuccessullBank(model.getBank())));
		detailList.add(transferInfoList.get(4));
		String [] referNoArray=help.separater(transferInfoList.get(4), ":");
		
		AfterFundTransferPlaceOrderOrSeeMargin(optionAfterTransfer, model, accountNo, referNoArray);
		
	}
	
	
	
	public void AfterFundTransferPlaceOrderOrSeeMargin(String redirectOption,FundTransferModel model,String accountNo,String [] referNoArray) {
		if(redirectOption.equalsIgnoreCase("See Margin")) {
			seeMarginButton=fluentWaitCodeXpath("//div[@class='impsBankDetails detSuces']//following::a[text()='See Margin']", "See margin button");
			clickElement(seeMarginButton, "see Margin Button");
			compareBalanceWithSeeMargin(model.getAmount(),"PASS");
			
			//double
			//verifyFundTransferStatus(model,accountNo,referNoArray[1],"PASS");
		}else if(redirectOption.equalsIgnoreCase("Place order")) {
			 placeOrderButton= fluentWaitCodeXpath("//div[@class='impsBankDetails detSuces']//following::a[text()='Place an Order']" , "Place order button");
			 clickElement(placeOrderButton, "Place order button");
			 OrderPlaceModel orderPlaceModel=new OrderPlaceModel(model.getReferNo(), model.getBank(), model.getExchange(), model.getOrderType(), model.getOrderPrice(), model.getProductType(), model.getQty());
			 orderDetialScreenshot=placeOrder.orderPlace(orderPlaceModel);
			 detailList.add("<@@ Order Placement Report @@>");
			 orderDetialArray=orderDetail.orderDetailProvider(driver, "NEW", orderPlaceModel.getReferNo(), orderPlaceModel);
			 for(String orderDetail:orderDetialArray) {
					if(!(orderDetail.equalsIgnoreCase("no id")||orderDetail.contains("no")||
					orderDetail.equalsIgnoreCase("ScriptResult")||orderDetail.equalsIgnoreCase("Report link")||
					orderDetail.equalsIgnoreCase("Screenshot link1")||orderDetail.equalsIgnoreCase("Partial Qty")))
					detailList.add(orderDetail);
				}
			 detailList.add(ScreenshortProvider.captureScreen(driver, "PlaceOrderInFundTransfer"));
			 screenshotList.add(ScreenshortProvider.captureScreen(driver, "PlaceOrderInFundTransfer"));
		}
		
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",5,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[3]/ul/li[2]/a");
		verifyFundTransferStatus(model, accountNo, referNoArray[1], "PASS");
	}
	
	
	
	public String verifyFundTransferStatus(FundTransferModel model,String accountNo,String referNo,String fundTransferResult) {
		staticWait(1000);
		Reporter.log("===> verifyFundTransferStatus <===", true);
		detailList.add("====@@> Transfer status <@@======");
		List<String> transferStatusList=new ArrayList<String>();
		funtTransferTab=fluentWaitCodeXpath("//a[text()='Fund Transfer']",200, "fund transfer tag");
		if(funtTransferTab!=null)
		clickElement(funtTransferTab, "Fund transfer tab");
		transferStatusTab=fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "transfer status tab");
		clickElement(transferStatusTab, "transfer status tab");
		//addFundTransferAmount(model.getAmount(),fundTransferResult);
		staticWait(5000);
		transferStatusPageScreenshot=ScreenshortProvider.captureScreen(driver, "TransferStatusPage");
		detailList.add(transferStatusPageScreenshot);
		screenshotList.add(ScreenshortProvider.captureScreen(driver, "TransferStatusPage"));
		transferStatusElementsStr="//a[text()='Apply']//following::div[@class='table-row ng-scope'][1]//div//div//span";
		List<WebElement> transferStatusElementList=multipleElementLocator(transferStatusElementsStr, "Transfer status element");
		for(WebElement element:transferStatusElementList) {
			transferStatusList.add(getValueFromAttribute(element, "value","Transer Status Element"));
		}
		String [] dateArray=help.commaSeparater(dateFunction.dataProvider());
		if(!referNo.equalsIgnoreCase("Not")) {
		detailList.add("Date & time: "+help.commpareTwoString(transferStatusList.get(0), dateArray[0]));
		
		detailList.add("Amount : "+help.commpareTwoString(transferStatusList.get(2), model.getAmount()));
		detailList.add("bank name : "+help.commpareTwoString(transferStatusList.get(3), fundTransferCommon.transferSuccessullBank(model.getBank())));
		detailList.add("Account no : "+help.commpareTwoString(transferStatusList.get(4).trim(), accountNo));
		detailList.add("Refer no : "+help.commpareTwoString(transferStatusList.get(5), referNo.trim()));
		detailList.add("Payment mode : "+help.commpareTwoString(transferStatusList.get(6), model.getPaymentMode()));
		detailList.add("Ledger : "+help.commpareTwoString(transferStatusList.get(9), "NORMAL"));
		}
		if(fundTransferResult.equalsIgnoreCase("PASS")) {
		
		 if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")) {
			Reporter.log("Checking for ICIC status",true);
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Pending"));
			iciciFundtransferStatus="Status : "+help.commpareTwoString(transferStatusList.get(8), "Pending");
		}else  if(referNo.equalsIgnoreCase("not")) {
		 if(model.getPaymentMode().equalsIgnoreCase("UPI")) {
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Failed"));
			iciciFundtransferStatus="Status : "+help.commpareTwoString(transferStatusList.get(8), "Failed");
		}else
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Request Sent"));
		}
		else {
		
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Processed"));
		}
		}
		else
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(8), "Request Sent"));
		
		if(fundTransferResult.equalsIgnoreCase("PASS")) {
			
		 if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")) {
			detailList.add("Remark : "+help.commpareTwoString(transferStatusList.get(10), "Pending"));
			iciciFundtransferRemark="Remark : "+help.commpareTwoString(transferStatusList.get(10), "Pending");
		}else  if(referNo.equalsIgnoreCase("not"))
			
		 if(model.getPaymentMode().equalsIgnoreCase("UPI")) {
			detailList.add("Status : "+help.commpareTwoString(transferStatusList.get(10), "UPI Int Failed"));
			iciciFundtransferRemark="Remark : "+help.commpareTwoString(transferStatusList.get(10), "UPI Int Failed");
		}else
			detailList.add("Remark : "+help.commpareTwoString(transferStatusList.get(10), "Request Sent"));
		}else
			detailList.add("Remark : "+help.commpareTwoString(transferStatusList.get(10), "PROCESSED"));
		
		return transferStatusPageScreenshot;
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
			 detailList.add(help.compareBigNo(FundTransferPage.addFundAmountStr, addFundAmountStr, help.removeHtmlText(fetchTextFromElement(balanceSummary)))); 
		 
	}
	
	

	private void payementForCitibank(String accountNo,FundTransferModel model,String optionAfterTransfer) {
		Reporter.log("===> payementForCitibank <===", true);
		String [] exprieDate=help.commaSeparater(model.getExpireDate());
		cardEnterNo(model.getDebitCardNo());
		expiryDate(model.getExpireDate());
		detailList.add(ScreenshortProvider.captureScreen(driver, "NativeCitiBank"));
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
	
	public void compareBalanceWithSeeMargin(String amount,String result) {
		Reporter.log("===> addFundTransferAmount <===", true);
		detailList.add(ScreenshortProvider.captureScreen(driver, "SeeMarginTabCompareBalance"));
		seeMarginBalance=fluentWaitCodeXpath("//*[@id='myModal']/div/div/div[3]/div[2]/div[4]/div/div[2]/div[1]/div[2]/div/div[2]/div[2]/label/span", "See margin balance");
		String seeMarginUpdateBalance=fetchTextFromElement(seeMarginBalance);
		String addAmount=help.bigDataAddition(FundTransferBankExecution.seeMarginBalanceStr, amount);
		
		
		 if(result.equalsIgnoreCase("PASS"))
		 detailList.add(help.compareBigNo(seeMarginUpdateBalance, addAmount, "See margin cash balance : "+seeMarginUpdateBalance));
		 else
			 detailList.add(help.compareBigNo(seeMarginUpdateBalance, addAmount,"See margin cash balance : "+seeMarginUpdateBalance)); 
		 
		 fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']","fundTransferTab");
		 clickElement(fundTransferTab, "Fund transfer tab");
	}
	
	public String enterAxisBank(String logId,String pwd,boolean loginOrNot) {
		String errorMsg="No error";
		logIdTextfield=fluentWaitCodeXpath("//input[@name='FORM_LOGINID']","Login id");
		clearAndSendKey(logIdTextfield, logId, "Login textfield");
		passwordTextfield=fluentWaitCodeXpath("//input[@name='pwd']", "password textfield");
		clearAndSendKey(passwordTextField, pwd, "password");
		submitButton=fluentWaitCodeXpath("//input[@name='SMsubmit']", "submit button");
		clickElement(submitButton, "Submit button");
		if(loginOrNot==false)
		{
			errorMsgLabel=fluentWaitCodeXpath("//div[@class='error-text']", "error msg");
			errorMsg=fetchTextFromElement(errorMsgLabel);
		}
		return errorMsg;
	}
	
	public List<String> paymentCodeExecution(FundTransferModel model,String accountNo,String optionAfterTransfer,String screenshot) {
		Reporter.log("===> paymentCodeExecution <===", true);
		detailList=new ArrayList<String>();
		staticWait(5000);
		String currentUrl=urlChecking();
		detailList.add(screenshot);
		Reporter.log("Url : "+currentUrl, true);
		 if(currentUrl.contains("AtomBank")) {
			atomPayment(accountNo,model,optionAfterTransfer,true);
		}else if(currentUrl.contains("razorpay")) {
			razorPayment(accountNo,model,optionAfterTransfer,true);
		}else if(model.getBank().equalsIgnoreCase("ICICI BANK LTD")) {
			paymentForICICI(model,optionAfterTransfer);
		}else if(model.getBank().equalsIgnoreCase("Citibank Na")) {
			payementForCitibank(accountNo,model,optionAfterTransfer);
		}else if(model.getBank().equalsIgnoreCase("Kotak Mahindra Bank")) {
			payementForKotak(model,accountNo,optionAfterTransfer);
		}
		 return detailList;
	}
}
