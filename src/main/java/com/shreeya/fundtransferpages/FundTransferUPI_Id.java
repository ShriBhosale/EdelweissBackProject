package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferUPI_Id extends SeleniumCoder{

	WebDriver driver;
	
	Help help;
	FundTransferCommon fundTransferCommon;
	FundTransferUPITextfield UPITextfieldObj;
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
	
	
	public FundTransferUPI_Id(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		fundTransferCommon=new FundTransferCommon();
		UPITextfieldObj=new FundTransferUPITextfield(driver);
		detailList=new ArrayList<String>();
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
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='HDFC BANK LTD.']",150,"HDFC BANK LTD.");
		if(bankAccountRedionButton.isSelected()==false)
		clickElement(bankAccountRedionButton, "Bank acount radio button");
		WebElement upiRadioButton = fluentWaitCodeXpath(driver, "//label[@for='upi']","UPI radio button");
		if(upiRadioButton.isEnabled()==false)
		clickElement(upiRadioButton, "UPI radio button");
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
		detailList.add("@@> Verify in case of eCollect bank when user selects eCollect option. <@@");
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='AXIS BANK']",150,"AXIS BANK");
		clickElement(bankAccountRedionButton, "Bank acount radio button");
		eCollectRadioButton=fluentWaitCodeXpath("//label[text()='eCollect']", "eCollect paymode");
		clickElement(eCollectRadioButton, "eCollect paymode");
		eCollectWarning=fluentWaitCodeXpath("//span[text()='AXIS BANK']//following::span[8]", "eCollect warning msg");
		detailList.add("Waning msg : "+fetchTextFromElement(eCollectWarning));
		neftLabel=fluentWaitCodeXpath("//Label[@class='ntimps']", "neft Label");
		neftStr=fetchTextFromElement(neftLabel);
		detailList.add(help.commpareTwoString(neftStr, "Add our bank account and transfer funds through NEFT/RTGS/IMPS"));
		eCollectBankDetail();
		detailList.add(ScreenshortProvider.captureScreen(driver, "eCollectBank"));
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
		detailList.add("@@> Verify when user initiate the UPI request and does not complete it within the specified time(5 min). <@@");
		//upiDropDown();
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
	}
	
	public void selectAnotherBank() {
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='AXIS BANK']",150,"AXIS BANK");
		clickElement(bankAccountRedionButton, "bankRadion button");
	}
	
	public void upi_idExecution(FundTransferReport report) {
		detailList=new ArrayList<String>();
		Reporter.log("===> UPI_idExecution <===", true);
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']",10,"fundTransferTab");
		if(fundTransferTab!=null) {
			clickElement(fundTransferTab, "FundTransferTab");
		}
		primaryUPIId();
		redriectToUPIServicesPage();
		upiIdTag();
		eCollectBank();
		deleteUPIExecution();
		//selectAnotherBank();
		
		report.upi_idReport(detailList);
		
	}
}
