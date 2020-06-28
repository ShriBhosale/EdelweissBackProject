package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class FundTransferProfile extends SeleniumCoder{

	WebDriver driver;
	WebElement profileImage;
	WebElement seeProfileLink;
	List<String> detailList;
	Help help;
	FundTransferCommon common;
	private WebElement tradingAccountLabel;
	private String tradingAccountStr;
	private WebElement panLabel;
	private String panStr;
	private WebElement dpName;
	private String dpStr;
	private WebElement dpAccountLabel;
	private String dpAccountStr;
	private WebElement dpIdLabel;
	private String dpIdStr;
	private WebElement dpNameLabel;
	private String dpNameStr;
	
	Scroll scroll;
	private WebElement closeButton;
	private WebElement addNewUPIIdLink;
	private WebElement bankAccountLabel;
	private WebElement bankAccountRedionButton;
	private WebElement upiIdTextfield;
	private String fityRsMsg;
	private WebElement upiErrorMsgLabel;
	private String upiErrorMsg;
	private WebElement submitButton;
	private WebElement closeAddNewUpiPopup;
	private WebElement upiDetailLabel;
	
	FundTransferUPI_Id fundTransferUPI_Id;
	private String payAmount;
	private WebElement popupTitle;
	private WebElement verifyUPIIdButton;
	private boolean verifyUPIIdButtonFlag;
	private WebElement upiIdTitle;
	private WebElement payMsgLabel;

	public FundTransferProfile() {}

	public FundTransferProfile(WebDriver driver) {
		super(driver);
		this.driver=driver;
		detailList=new ArrayList<String>();
		help=new Help(driver);
		common=new FundTransferCommon(driver);
		scroll=new Scroll(driver);
		fundTransferUPI_Id=new FundTransferUPI_Id(driver);
	}
	
	public void redirectToProfile() {
		Reporter.log("===> redirectToProfile <===", true);
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",15,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		profileImage=fluentWaitCodeXpath("//a[@id='caUser']", "Profile image");
		clickElement(profileImage, "Profile image");
		seeProfileLink=fluentWaitCodeXpath("//a[text()=' See Profile']", "See profile link");
		clickElement(seeProfileLink,"See profile link");
		staticWait(2000);
	}
	
	public void checkDematTradingAccountBankDetail() {
		Reporter.log("===> checkDematTradingAccountBankDetail <===", true);
		detailList.add("@@> Verify when user visit Bank,Demat & Trading account detail through Profile & Settings. <@@");
		redirectToProfile();
		
		detailList.add(screenshotWithElement("//span[text()='Mobile Number']","Mobile Number label","TradingDematBankDetail"));
		tradingAccountLabel=fluentWaitCodeXpath("//span[text()='Trading A/C No.']//following::span[1]", "Trading account");
		tradingAccountStr=fetchTextFromElement(tradingAccountLabel);
		panLabel=fluentWaitCodeXpath("//span[text()='PAN']//following::span[1]", "Pan label");
		panStr=fetchTextFromElement(panLabel);
		dpNameLabel=fluentWaitCodeXpath("//span[text()='DP Name']//following::span[1]", "Dp name");
		dpNameStr=fetchTextFromElement(dpNameLabel);
		dpAccountLabel=fluentWaitCodeXpath("//span[text()='DP Account']//following::span[1]", "dp account");
		dpAccountStr=fetchTextFromElement(dpAccountLabel);
		dpIdLabel=fluentWaitCodeXpath("//span[text()='DP ID']//following::span[1]", "DP id");
		dpIdStr=fetchTextFromElement(dpIdLabel);
		detailList.add("== Trading Account ==");
		detailList.add("Tading Account : "+help.elementPresent(tradingAccountLabel,tradingAccountStr,"Tading Account"));
		detailList.add("Pan : "+help.elementPresent(panLabel, panStr,"Pan"));
		detailList.add("== Demat Account ==");
		detailList.add("DP name : "+help.elementPresent(dpNameLabel, dpNameStr,"DP name"));
		detailList.add("DP Account : "+help.elementPresent(dpAccountLabel, dpAccountStr,"DP account"));
		detailList.add("Dp ID : "+help.elementPresent(dpIdLabel, dpIdStr,"DP id"));
		
	}
	
	public void addNewUPILink() {
		staticWait(1000);
		screenshotWithElement("//h5[text()='Advisor Details']", "Advisor detail Label", "AddNewUPILable");
		addNewUPIIdLink=fluentWaitCodeXpath("//a[@gtmdir-label='Add New UPI ID']", "add new UPI id");
		clickElement(addNewUPIIdLink, "Add new UPI id link");
		upiIdTitle=fluentWaitCodeXpath("//h5[text()='Add New UPI ID']",60, "Add upi title");
		Reporter.log("UpiIdTitle element : "+upiIdTitle, true);
		if(upiIdTitle==null) {
			closeAddNewUpiPopup=fluentWaitCodeXpath("//*[@id='addNewUpiId']/div/div/div[1]/a", "close add new upi popup");
			clickElement(closeAddNewUpiPopup, "close add new upi popup");
			addNewUPIIdLink=fluentWaitCodeXpath("//a[@gtmdir-label='Add New UPI ID']", "add new UPI id");
			clickElement(addNewUPIIdLink, "Add new UPI id link");
		}
	}
	
	public void checkClickOnNewUPIid() {
		Reporter.log("===> checkClickOnNewUPIid <===", true);
		detailList.add("@@> Verify pop up is getting displayed after clicking on Add new UPI id through profile=>bank details. <@@");
		addNewUPILink();
		popupTitle=fluentWaitCodeXpath("//h5[text()='Add New UPI ID']", "Popup title");
		if(popupTitle!=null)
			detailList.add("Add new UPI id popup display");
		detailList.add("Popup Titile :  "+help.elementPresent(popupTitle, "Add new UPI id popup"));
		detailList.add(ScreenshortProvider.captureScreen(driver, "AddNewPopupUPIid"));
	}
	
	public boolean fillAddNewUPIForm(String bankName,String upiStr,boolean failCase) {
		Reporter.log("===> fillAddNewUPIForm  <===", true);
		upiErrorMsg=null;
		boolean verifyUPIIdButtonFlag=false;
		bankAccountLabel=fluentWaitCodeXpath(driver, "//label[@title='"+bankName+"']//span[1]",bankName);
		bankAccountRedionButton=fluentWaitCodeXpath("//span[text()='"+bankName+"']//preceding::input[1]",bankName+ "radio button");
		String selectedBankStr=getValueFromAttribute(bankAccountRedionButton, "gtmdir-text", "bank Radio button");
		Reporter.log("bank selected bank : "+selectedBankStr, true);
		if(!selectedBankStr.contains(bankName)) {
			
				clickElement(bankAccountLabel, bankName+"radio button");
		}
		
		upiIdTextfield=fluentWaitCodeXpath("//div[@class='ib mxlimit']//input", "UPI id textfield");
		clearAndSendKey(upiIdTextfield, upiStr, "UPI id textfield");
		fityRsMsg=fetchTextFromElement("//div[@class='demat_lbl']//p","50Msg");
		verifyUPIIdButton=fluentWaitCodeXpath("//input[@value='Verify UPI ID']", "Verify UPI id");
		if(verifyUPIIdButton.isEnabled()) {
			verifyUPIIdButtonFlag=true;
		clickElement(verifyUPIIdButton, "Verify UPI id button");
		}
		if(failCase) {
		staticWait(2000);	
		upiErrorMsgLabel=fluentWaitCodeXpath("//span[@ng-bind='upiInvalidMsg']",100, "UPI error msg");
		if(upiErrorMsgLabel!=null)
			if(upiErrorMsgLabel.isDisplayed())
			upiErrorMsg=fetchTextFromElement(upiErrorMsgLabel);
		}
		return verifyUPIIdButtonFlag;
	}
	
	public void addNewUPIFormWithInvalid() {
		Reporter.log("===> addNewUPIFormWithInvalid <===", true);
		detailList.add("@@> Verify UPI id field by entering the invalid(incomplete ID) UPI id in add funds page and click on submit. <@@");
		fillAddNewUPIForm("HDFC BANK LTD.", "ABC@", true);
		detailList.add("Error msg : "+upiErrorMsg+"-PASS");
		detailList.add(ScreenshortProvider.captureScreen(driver, "AddNewUPIFormWithInvalidInProfile"));
	}
	
	public void closeAddNewUPIForm() {
		Reporter.log("===> closeAddNewUPIForm <===", true);
		detailList.add("@@> Verify when user clicks on close button in add new UPI id window through profile=>bank details. <@@");
		closeAddNewUpiPopup=fluentWaitCodeXpath("//*[@id='addNewUpiId']/div/div/div[1]/a", "close add new upi popup");
		clickElement(closeAddNewUpiPopup, "close add new upi popup");
		upiDetailLabel=fluentWaitCodeXpath("//h5[text()='UPI Details']", "UPI detail label");
		detailList.add(help.elementPresent(upiDetailLabel, "popup close","popu does not close"));
		detailList.add(ScreenshortProvider.captureScreen(driver, "closeAddNewUPIForm"));
	}
	
	
	public void addNewUPIFormWithvalid() {
		Reporter.log("===> addNewUPIFormWithvalid <===", true);
		detailList.add("@@> Verify when user clicks verify ID in add new UPI id window through profile=>bank details. <@@");
		addNewUPILink();
		verifyUPIIdButtonFlag=fillAddNewUPIForm("HDFC BANK LTD.", "qrtest1@hdfcbank", false);
		help.elementDisappear("//h5[text()='Add New UPI ID']", "New Upi id title", 6);
		payAmount=fundTransferUPI_Id.verifyPayAmountWithAmout("50");
		detailList.add(payAmount+"-PASS");
		detailList.add(ScreenshortProvider.captureScreen(driver, "ServiceProviderPage"));
		detailList.add("@@> Verify message id displayed for verifying the UPI id on Add new UPI id window. <@@");
		detailList.add(help.commpareTwoString(fityRsMsg, "To verify UPI ID you need to transfer ₹50 to your demat account"));
		detailList.add("@@> Verify after entering the UPI id which is linked to bank account in add new UPI id. <@@");
		if(verifyUPIIdButtonFlag)
			detailList.add("verify UPI id button get enable-PASS");
		else
			detailList.add("verify UPI id button get disable-FAIL");
	}
	
	public void validUPIidWithDiffBank() {
		Reporter.log("===> validUPIidWithDiffBank1 <===", true);
		detailList.add("@@> Verify when user enters UPI id such that the bank account selected and the UPI id entered contains different bank name and click on submit. <@@");
		common.checkThenBackFundTransfer();
		redirectToProfile();
		addNewUPILink();
		fillAddNewUPIForm("Kotak Mahindra Bank", "qrtest1@hdfcbank", true);
		detailList.add(ScreenshortProvider.captureScreen(driver, "ValidUPIidWithDiffBank"));
		if(upiErrorMsg==null)
		{
			staticWait(3000);
			payMsgLabel=elementLocateBytag("h5");
			if(payMsgLabel!=null)
				detailList.add("It is redirect to service provider page");
			detailList.add("Error message not displayed as 'UPI handle entered is not correct. Kindly check'-FAIL");
		}else {
			detailList.add(upiErrorMsg+"-PASS");
		}
	}
	
	
	
	public void profileExecution(FundTransferReport report) {
		try {
		Reporter.log("<b>=====@@> profileExecution <@@======</b>");
		
		  checkDematTradingAccountBankDetail();
		  checkClickOnNewUPIid();
		  addNewUPIFormWithInvalid();
		  //hdfc bank was not add here
		 // closeAddNewUPIForm(); 
		  addNewUPIFormWithvalid();
		 
		validUPIidWithDiffBank();
		report.profileReport(detailList);
		}catch (NullPointerException e) {
		  report.fundTransferFailReport("Profile", driver, detailList);
		}catch(NoSuchElementException e) {
			report.fundTransferFailReport("Profile", driver, detailList);
		}
	}
	
	public String screenshotWithElement(String xpathString,String elementName,String screenshotName) {
		staticWait(1000);
		Reporter.log("===> screenshotWithElement <===", true);
		WebElement element=fluentWaitCodeXpath(xpathString, elementName);
		scroll.scrollAndPointToElement(element);
		return ScreenshortProvider.captureScreen(driver,screenshotName);
	}
	
}
