package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.DatePickers;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferUPI_Id extends SeleniumCoder{

	WebDriver driver;
	
	Help help;
	FundTransferCommon fundTransferCommon;
	FundTransferUPITextfield UPITextfieldObj;
	Payment payment;
	DatePickers datePickers;
	List<String> detailList;

	private WebElement bankAccountRedionButton;

	private WebElement okButton;

	private WebElement upiDropdownButton;

	private WebElement fundTransferTab;

	private WebElement manageOptionLink;

	private WebElement deleteOptionLink;

	private WebElement deletePopupMsg;

	private WebElement deleteYesButton;

	private WebElement deleteNoButton;

	private String deletePopupMsgStr;

	private String yesButton;

	private String noButton;

	private WebElement upiIdLabel;

	private String upiDeleteScreenshot;

	private String deleteOrNotScreenshot;

	private String upideleteOrNotScreenshot;

	private String DeleteYesNoButtonStr;

	private String upiDropDowntext;

	private WebElement upiDropdownBelowLabel;

	private WebElement upiTag;

	private WebElement eCollectRadioButton;

	private WebElement eCollectWarning;

	private WebElement neftLabel;

	private String neftStr;

	private WebElement manageOptionTitle;

	private WebElement manageBackButton;

	private WebElement amountToTransferTextField;

	private WebElement submitButton;

	private WebElement upiServicePageLabel;

	private String upiServiceProviderLabel;

	private WebElement upiTextfield;

	private WebElement existingUPIidLink;

	private String upiIdStr;

	private WebElement accountNoLabel;

	private String accountNoStr;

	private WebElement bankNameLable;

	private String bankNameStr;

	private WebElement dateToTextbox;

	private WebElement dateFromTextbox;

	private WebElement transferStatusLabel;

	private WebElement transferStatusTab;

	private WebElement payMsgLabel;

	private WebElement paymentTimerLabel;

	private WebElement upiLableTradeMsg;

	private WebElement retryButton;

	private FundTransferModel model;

	private String transferStatusScreenshot;
	
	ConfigReader configReader;

	private WebElement upiRadioButton;

	private WebElement bankAccountLabel;

	private WebElement bankRedionButton;

	private WebElement bankLabel;

	private WebElement upiRadioLabel;

	private boolean timerLableFlag;

	private WebElement upiTimerDoneMsg;

	private String upiTimerDoneMsgStr;

	private WebElement upiTimerDoneSubMsg;

	private String upiTimerDoneSubMsgStr;

	private String upiTimerDoneScreenshot;

	private boolean upiTimerDonePageFlag;

	private String upiTimerMsg;
	
	public FundTransferUPI_Id(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		fundTransferCommon=new FundTransferCommon(driver);
		UPITextfieldObj=new FundTransferUPITextfield(driver);
		detailList=new ArrayList<String>();
		datePickers=new DatePickers(driver);
		payment=new Payment(driver);
		model=new FundTransferModel();
		configReader=new ConfigReader();
	}
	
	

	public void primaryUPIId() {
		Reporter.log("===> primaryUPIId <===",true);
		detailList.add("@@> Verify Primary ID which is set as default is displayed in drop down. <@@");
		upiDropDown();
		detailList.add(ScreenshortProvider.captureScreen(driver, "PrimaryUPI_id"));
		upiDropDowntext=getValueFromAttribute(upiDropdownButton, "value", "UPI dropdow button");
		upiDropdownBelowLabel=fluentWaitCodeXpath("//label[@for='upi']//following::p[1]", "UPI dropdown below label");
		detailList.add(help.commpareTwoString(fetchTextFromElement(upiDropdownBelowLabel),"PRIMARY ID"));
		detailList.add("Primary UPI id : "+upiDropDowntext);
	}
	
	public void upiDropDown() {
		Reporter.log("===> upiDropDown <===",true);
		staticWait(500);
		bankRedionButton=fluentWaitCodeXpath("//span[text()='HDFC BANK LTD.']//preceding::input[1]", "Bank Radio button");
		bankLabel=fluentWaitCodeXpath(driver, "//span[text()='HDFC BANK LTD.']",150,"HDFC BANK LTD.");
		if(bankRedionButton.isSelected()==false) {
			
		clickElement(bankLabel, "Bank acount radio button");
		}
		 upiRadioLabel = fluentWaitCodeXpath(driver, "//label[@for='upi']","UPI radio button");
		upiRadioButton=fluentWaitCodeXpath("//input[@gtmdir-text='Payment mode | UPI']", 30, "UPI radio button");
		
			if(upiRadioButton.isSelected()==false)
		clickElement(upiRadioLabel, "UPI radio button");
		
		okButton=fluentWaitCodeXpath("//input[@value='OK']",10,"Ok button");
		if(okButton!=null) {
			clickElement(okButton , "Ok button");
		}
		upiDropdownButton=fluentWaitCodeXpath("//label[@for='upi']//following::button[1]",10,"UPI dropdown button");
		if(upiDropdownButton==null) {
			existingUPIidLink=fluentWaitCodeXpath("//a[text()='existing UPI IDs']", "existing UPI IDs");
			if(existingUPIidLink!=null)
			clickElement(existingUPIidLink, "existing UPI IDs");
		}
	}
	

	public void deleteUPIExecution() {
		Reporter.log("===> deleteUPIExecution <===", true);
		detailList.add("@@> Verify when user selects 'NO' while deleting a UPI id through manage option. <@@");
		upiDropDown();
		selectUPIidForDelete("cup@hdfcbank",false);
		detailList.add(upideleteOrNotScreenshot);
		detailList.add("@@> Verify when user delete any UPI id through manage option(Check popMsg). <@@");
		detailList.add(help.commpareTwoString(deletePopupMsgStr, "Are you sure you want to delete?"));
		detailList.add(DeleteYesNoButtonStr);
		detailList.add(upiDeleteScreenshot);
		detailList.add("@@> Verify Bank Account No should be displayed in Manage Upi id  <@@");
		detailList.add(upiIdStr);
		detailList.add(accountNoStr);
		detailList.add(bankNameStr);
	}
	
	public void verifyDeletedUPI(String upiId,boolean deleteFlag) {
		Reporter.log("===> verifyDeletedUPI <===", true);
		String upiStr="//span[text()='"+upiId+"']";
		upiIdLabel=fluentWaitCodeXpath(upiStr, "UPI id label");
		if(deleteFlag) {
		
			if(upiIdLabel!=null) {
				detailList.add(upiId+" is not deleted...-FAIL");
			}else {
				detailList.add(upiId+" is  deleted successfully...-PASS");
			}
		}else {
			if(upiIdLabel==null) {
				detailList.add(upiId+" is  deleted successfully...-FAIL");
			}else {
				detailList.add(upiId+" is not deleted...-PASS");
			}
		}
		
		
	}
	
	public void selectUPIidForDelete(String upiId,boolean deleteFlag) {
		Reporter.log("===> selectUPIidForDelete <===", true);
		clickUPIMangeOption();
		String deleteOptionStr="//span[text()='"+upiId+"']//following::label[text()='Delete']";
		deleteOptionLink=fluentWaitCodeXpath(deleteOptionStr, "delete optin link");
		clickElement(deleteOptionLink, "delete optin link");
		deletePopupMsg=fluentWaitCodeXpath("//label[text()='Delete']//following::span[1]", "delete Popup Msg");
		deletePopupMsgStr=fetchTextFromElement(deletePopupMsg);
		deleteYesButton=fluentWaitCodeXpath("//label[text()='Delete']//following::button[1]", "Delete Yes button");
		deleteNoButton=fluentWaitCodeXpath("//label[text()='Delete']//following::button[2]", "Delete No button");
		yesButton=getValueFromAttribute(deleteYesButton,"value", "Delete Yes buttton");
		noButton=getValueFromAttribute(deleteNoButton,"value", "Delete No buttton");
		upiDeleteScreenshot=ScreenshortProvider.captureScreen(driver, "UPIDeleteScreenshot");
		if(yesButton.equalsIgnoreCase("Yes")&&noButton.equalsIgnoreCase("No")) {
			DeleteYesNoButtonStr="Yes and No button is present...";
		}else {
			DeleteYesNoButtonStr="Yes and No button is not present...";
		}
		if(deleteFlag) {
			clickElement(deleteYesButton, "Yes button from delete popup");
			verifyDeletedUPI("cup@hdfcbank",deleteFlag);
		}else {
			clickElement(deleteNoButton, "No button from delete popup");
			verifyDeletedUPI("cup@hdfcbank",deleteFlag);
		}
		upideleteOrNotScreenshot=ScreenshortProvider.captureScreen(driver, "UPIDeletedOrNot");
		manageOptionTitle=fluentWaitCodeXpath("//h5[text()='Manage UPI IDs']", "Manage Upi title");
		if(fetchTextFromElement(manageOptionTitle).equalsIgnoreCase("Manage UPI IDs")) {
			manageBackButton=fluentWaitCodeXpath("//a[text()='Back']", "UPI manage back button");
			clickElement(manageBackButton, "UPI manage back button");
		}
	}
	
	public void clickUPIMangeOption() {
		Reporter.log("===> clickUPIMangeOption <===", true);
		clickElement(upiDropdownButton, "UPI dropdown button");
		manageOptionLink=fluentWaitCodeXpath("//a[text()='Manage UPI IDs']", "Manage option link");
		clickElement(manageOptionLink, "Manage option link");
		upiIdLabel=fluentWaitCodeXpath("//h5[text()='Manage UPI IDs']//following::span[2]", "UPI id");
		upiIdStr="UPI id : "+fetchTextFromElement(upiIdLabel);
		accountNoLabel=fluentWaitCodeXpath("//h5[text()='Manage UPI IDs']//following::span[3]", "Account No");
		accountNoStr="Account no : "+fetchTextFromElement(accountNoLabel);
		bankNameLable=fluentWaitCodeXpath("//h5[text()='Manage UPI IDs']//following::span[4]", "bank name");
		bankNameStr=fetchTextFromElement(bankNameLable);
	}
	
	public void upiIdTag() {
		Reporter.log("===> upiIdTag <===", true);
		detailList.add("@@> Verify UPI has a tag of new. <@@");
		upiTag=fluentWaitCodeXpath("//i[@class='ic-help help1 mg-r10']", "UPI tag");
		if(upiTag!=null) {
		if(upiTag.isDisplayed()) {
			detailList.add("UPI id tag display-PASS");
		}
		}
	}
	
	public void eCollectBank() {
		Reporter.log("===> eCollectBank <===", true);
		String bankName=configReader.configReaderFM("UpieCollectBank");
		if(!bankName.equalsIgnoreCase("No")) {
		detailList.add("@@> Verify in case of eCollect bank when user selects eCollect option. <@@");
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",150,bankName);
		clickElement(bankAccountRedionButton, "Bank acount radio button");
		eCollectRadioButton=fluentWaitCodeXpath("//label[text()='eCollect']", "eCollect paymode");
		clickElement(eCollectRadioButton, "eCollect paymode");
		eCollectWarning=fluentWaitCodeXpath("//span[text()='"+bankName+"']//following::span[8]", "eCollect warning msg");
		detailList.add("Waning msg : "+fetchTextFromElement(eCollectWarning));
		neftLabel=fluentWaitCodeXpath("//Label[@class='ntimps']", "neft Label");
		neftStr=fetchTextFromElement(neftLabel);
		detailList.add(help.commpareTwoString(neftStr, "Add our bank account and transfer funds through NEFT/RTGS/IMPS"));
		eCollectBankDetail();
		detailList.add(ScreenshortProvider.captureScreen(driver, "eCollectBank"));
		}
	}
	
	public void eCollectBankDetail() {
		Reporter.log("===> eCollectBankDetail <===", true);
		List<String>bankDetailList=multipleElementsTextProvider("//div[@class='impsBankDetails']//label", "eCollect(NEFT/RTGS/IMPS) bank detail");
		for(String bankDetail:bankDetailList) {
			detailList.add(help.removeSpanHtmlTag(bankDetail));
		}
	}
	
	public void redriectToUPIServicesPage() {
		Reporter.log("===> redriectToUPIServicesPage <==", true);
		//detailList.add("@@> Verify when user initiate the UPI request and does not complete it within the specified time(5 min). <@@");
		upiDropDown();
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
		clearAndSendKey(amountToTransferTextField, "200", "Amount To Transfer TextField");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "submit button");
		upiServicePageLabel=elementLocateBytag("h5");
		upiServiceProviderLabel=fetchTextFromElement(upiServicePageLabel);
		detailList.add(UPITextfieldObj.errorChecker(false));
		if(false) {
			detailList.add("This error msg come after enter amount and click on submit button-FAIL");
		}else {
			if(!upiServiceProviderLabel.equalsIgnoreCase("Manage UPI IDs")) {
			detailList.add(fundTransferCommon.removeHtmlTextCheckText(upiServiceProviderLabel,"200"));
			
			}
		}
		detailList.add(ScreenshortProvider.captureScreen(driver, "redriectToUPIService"));
		fundTransferCommon.backFundTransferPage();
	}
	
	public void selectAnotherBank() {
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='AXIS BANK']",150,"AXIS BANK");
		clickElement(bankAccountRedionButton, "bankRadion button");
	}
	
	public void historyStatusChecking() {
		Reporter.log("===> historyStatusChecking <===", true);
		detailList.add("@@> Verify historical data is available when user selects the calendar date accordingly. <@@");
		String dateTo="3-June-2020";
		String dateFrom="4-June-2020";
		transferStatusLabel=fluentWaitCodeXpath("//h5[text()='Transfer Status']", "Transfer status label");
		if(transferStatusLabel==null) {
			transferStatusTab=fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "Transfer Status Tab");
			clickElement(transferStatusTab, "Transfer Status Tab");
		}
		dateToTextbox=fluentWaitCodeXpath("//input[@name='fundStatusToDt']", "date to textbox");
		dateFromTextbox=fluentWaitCodeXpath("//input[@name='fundStatusFromDt']", "date from textbox");
		clickElement(dateToTextbox, "Data to textbox");
		datePickers.dataPickersExecuter(dateTo);
		clickElement(dateFromTextbox, "Data From textbox");
		datePickers.dataPickersExecuter(dateFrom);
		comparetDatePresentOrNot(dateTo,dateFrom);
		
	}
	
	public void comparetDatePresentOrNot(String dateOne,String dateTwo) {
		boolean match=true;
		List<String> TransferFundDateList=multipleElementsTextProvider("//*[@id='sub-tab4']/div/div[3]/div[1]/div/div[1]/div/span[1]", "Fund Transfer dates");
		if(dateOne.equalsIgnoreCase(dateTwo)) {
			
			for(int i=2;i<TransferFundDateList.size();i++) {
				if(!TransferFundDateList.get(i).equalsIgnoreCase(dateTwo)) {
					bankNameLable=fluentWaitCodeXpath("//*[@id='sub-tab4']/div/div[3]/div[1]/div["+i+"]/div[3]/div/span[1]", "Bank name");
					//detailList.add("The transfer status is not as selected calender-FAIL");
					match=false;
					break;
				}
			}
		}else {
			for(int i=2;i<TransferFundDateList.size();i++) {
				if(!(TransferFundDateList.get(i).equalsIgnoreCase(dateTwo)||TransferFundDateList.get(i).equalsIgnoreCase(dateOne))) {
					bankNameLable=fluentWaitCodeXpath("//*[@id='sub-tab4']/div/div[3]/div[1]/div["+i+"]/div[3]/div/span[1]", "Bank name");
					//detailList.add("The transfer status is not as selected calender-FAIL");
					match=false;
					break;
				}
			}
		}
		
		if(match) {
			detailList.add("The transfer status is not as selected calender-FAIL");
		}else {
			detailList.add("The transfer status as per calender-PASS");
		}
	}
	
	public String verifyPayAmountWithAmout(String amount) {
		String payInSentances;
		int withOutAmountLenght;
		do {
		payMsgLabel=elementLocateBytag("h5");
		payInSentances=help.removeHtmlReporter(fetchTextFromElement(payMsgLabel));
		 withOutAmountLenght=payInSentances.length();
		}while(withOutAmountLenght==22);
		
		return verifyPayAmount(amount,payInSentances);
	}
	
	
	
	public void upiTimerPage() {
		String paymentTimer;
		detailList.add("@@> Verify when user initiate the UPI request and does not complete it within the specified time(5 min).  <@@");
		Reporter.log("===> upiTimerPage <===", true);
		fundTransferCommon.disappearRedirectionMsg();
		fundTransferCommon.checkThenBackFundTransfer();
		fundTransferCommon.fillFormForUPI("HDFC BANK LTD.", "60", "");
		staticWait(4000);
		fundTransferCommon.elementDisappear("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']");
		detailList.add(verifyPayAmountWithAmout("60"));
		detailList.add(ScreenshortProvider.captureScreen(driver, "UPITimer"));
		do {
			staticWait(5000);
		paymentTimerLabel=fluentWaitCodeXpath("//p[text()='Approve the payment within']//following::p[1]", 30, "payment timer");
		if(paymentTimerLabel.isDisplayed()) {
			timerLableFlag=true;
		}else {
			detailList.add("5 minite timer is complete-PASS");
			detailList.add(ScreenshortProvider.captureScreen(driver, "UPITimerDone"));
			upiTimerDoneMsg=fluentWaitCodeXpath("//label[@class='redirectInfo']",30,"UPI timer done");
			if(upiTimerDoneMsg!=null) {
				//need to check xpath
				//detailList.add(fetchTextFromElement(upiLableTradeMsg));
			upiTimerDoneSubMsg=fluentWaitCodeXpath("//label[@class='redirectInfo']//following::label[1]", "UPI timer done sumsg");
			if(upiTimerDoneSubMsg !=null)
				detailList.add(fetchTextFromElement(upiTimerDoneSubMsg));
			
			}
			staticWait(1000);
			upiTimerDonePageFlag=true;
			break;
		}
		Reporter.log("paymentTimerLabel : "+paymentTimerLabel, true);
		paymentTimer=fetchTextFromElement(paymentTimerLabel);	
		Reporter.log(paymentTimer, true);
		
		}while(!paymentTimer.equalsIgnoreCase("00.00")&& timerLableFlag);
		if(upiTimerDonePageFlag==false) {
		upiLableTradeMsg=fluentWaitCodeXpath("//span[text()='UPI ID']//following::span[1]", "upiLableTradeMsg");
		retryButton=fluentWaitCodeXpath("//a[text()='Retry']", "retry button");
		clickElementWithOutChecking(retryButton, "Retry button");
		model.setBank("HDFC BANK LTD.");
		model.setPaymentMode("UPI");
		transferStatusScreenshot=payment.verifyFundTransferStatus(model, "", "Not", "PASS");
		detailList.add(transferStatusScreenshot);
		detailList.add(Payment.iciciFundtransferStatus);
		detailList.add(Payment.iciciFundtransferRemark);
		}else {
			fundTransferCommon.backFundTransferPage();
		}
	}
	
	public String verifyPayAmount(String amount,String msg) {
		String result;
		String [] payArray=help.separater(msg, " ");
		if(amount.equalsIgnoreCase(payArray[4])) {
			result=msg+"-PASS";
		}else {
			result=msg+"-FAIL";
		}
		return result;
	}
	
	
	public void upi_idExecution(FundTransferReport report) {
		detailList=new ArrayList<String>();
		Reporter.log("<b>========@@> UPI_idExecution <@@========</b>", true);
		configReader.fundTransferConfig();
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']",10,"fundTransferTab");
		if(fundTransferTab!=null) {
			clickElement(fundTransferTab, "FundTransferTab");
		}
		
		
		  primaryUPIId(); 
		  redriectToUPIServicesPage(); 
		  upiIdTag();
		  eCollectBank();
		  deleteUPIExecution();
		  
		  historyStatusChecking();
		 
		upiTimerPage();
		report.upi_idReport(detailList);
		
	}
}
