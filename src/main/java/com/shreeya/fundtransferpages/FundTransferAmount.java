package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class FundTransferAmount extends SeleniumCoder{
	
	WebDriver driver;
	ExtendReporter htmlReporter;
	
	FundTransferCommon fundTransferCommon;
	Help help;
	Scroll scroll;
	
	String [] amountArray;
	
	List<String> detailList;
	
	private WebElement fundTransferTab;
	private WebElement submitButton;
	private WebElement amountToTransferTextField;


	private WebElement amountErrorMsgLabel;


	private String amountErrorMsgStr;


	private WebElement qickSelectAmountLabel;
	
	public FundTransferAmount(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		scroll=new Scroll(driver);
		fundTransferCommon=new FundTransferCommon();
		detailList=new ArrayList<String>();
		
	}
	
	public void elementLocator() {
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
	}
	
	public void blankAmountTextfield() {
		Reporter.log("=== blankAmountTextfield ===",true);
		detailList.add("@@> verify blank amount textfield <@@");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		if(submitButton.isSelected()) {
			detailList.add("User can leave amount texfield and submit-FAIL");
		}else
		{
			detailList.add("Please enter amount-PASS");
		}
	}
	
	public void verifyAmountTextfieldData(WebElement amountToTransferTextField,String msg) {
		Reporter.log("=== verifyAmountTextfieldData ===",true);
		clearAndSendKey(amountToTransferTextField, msg, "Amount textfield");
		String amountText=getValueFromAttribute(amountToTransferTextField, "value", "amountToTransferTextField");
		detailList.add("Enter amount : "+help.commpareTwoString(amountText, msg));
		amountErrorMsgLabel=fluentWaitCodeXpath("//label[text()='Quick select:']//preceding::span[@ng-bind='errorAmtMessage']",10, "Amount error msg label");
		if(amountErrorMsgLabel.isDisplayed()) {
		amountErrorMsgStr=fetchTextFromElement(amountErrorMsgLabel);
		detailList.add("Error msg : "+amountErrorMsgStr+"-PASS");
		detailList.add(amountTextfieldScreenshot("AmountTextfieldWith_"+msg));
		}else {
			detailList.add("Allow to enter "+msg+"-FAIL");
			detailList.add(amountTextfieldScreenshot("AmountTextfieldWith_"+msg));
		}
		
	}
	
	
	
	
	public void qickSelectAndEnter() {
		Reporter.log("=== qickSelectAndEnter ===", true);
		detailList.add("@@> Verify when user clicks on any of the amount from Quick select and some amount is already entered. <@@");
		clearAndSendKey(amountToTransferTextField, "20", "Amount textfield");
		detailList.add("Enter amount : "+amountToTransferTextField.getAttribute("value"));
		qickSelectOption("5000");
		
		String ans=help.commpareTwoString(help.addTwoStringNo("5000","20"),amountToTransferTextField.getAttribute("value"));
		detailList.add("QickSelect amount with enter amount : "+ans);
		detailList.add(amountTextfieldScreenshot("clickQickOptionAlreadyAmountEnter"));
	}
	
	public void qickSelectOption(String qickSelectAmout) {
		String quickSelectAmountStr="//a[@gtmdir-label='Quick Select | "+qickSelectAmout+"']";
		qickSelectAmountLabel=fluentWaitCodeXpath(quickSelectAmountStr, qickSelectAmout+" QickSelect ");
		clickElement(qickSelectAmountLabel,  qickSelectAmout+" QickSelect ");
		detailList.add("QickSelect option : "+qickSelectAmout);
	}
	
	public void verifyQickSelectAmount() {
		Reporter.log("============> verifyQickSelectAmount <==========", true);
		
		boolean match=true;
		String unMatchQickAmount="qickSelect";
		detailList.add("@@> Verify Quick select option <@@");
		String [] qickSelectArray= {"5000","10000","20000"};
		List<String> qickSelectList=multipleElementsTextProvider("//label[text()='Quick select:']//following::label[@class='val pd-l2']", "QickSelectAmount");
		for(int i=0; i< qickSelectList.size();i++) {
			if(!qickSelectArray[i].equalsIgnoreCase(help.commoRemove(qickSelectList.get(i)))) {
				unMatchQickAmount=qickSelectList.get(i);
				match=false;
				break;
				
			}
		}

		if(match) {
			detailList.add("All three Qick select amount (5000,10000,20000) are match-PASS");
		}else {
			detailList.add("Umatch qickSelect : "+unMatchQickAmount+"-FAIL");
		}
		detailList.add(amountTextfieldScreenshot("CheckQickSelectOption"));
	}
	
	
	
	public void enterAmountLessFifty() {
		Reporter.log("=== enterAmountLessFifty ===", true);
		detailList.add("@@> Verify amount field by entering amount less than 50  <@@");
		//amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
		verifyAmountTextfieldData(amountToTransferTextField,"20");
	}
	
	public void enterAmountGreaterthanOneMillion() {
		Reporter.log("=== enterAmountGreaterthanOneMillion ===", true);
		detailList.add("@@> Verify amount field by entering amount greater than 100000  <@@");
		verifyAmountTextfieldData(amountToTransferTextField,"2000000");
	}
	
	public void checkAmountTextfieldGuidText() {
		Reporter.log("=== checkAmountTextfieldGuidText ===", true);
		detailList.add("@@> Verify guiding text is present for amount field. <@@");
		detailList.add("guidline text : "+getValueFromAttribute(amountToTransferTextField,"placeholder","Amount To Transfer TextField"));
		detailList.add(amountTextfieldScreenshot("AmountTextfieldGuidLine"));
	}
	public void clickQickSelectOption() {
		Reporter.log("=== clickQickSelectOption ===", true);
		detailList.add("@@> Verify when user clicks on any Quick option and the amount field is empty. <@@");
		
		String amountStr=getValueFromAttribute(amountToTransferTextField,"value","amount textfield");
		if(amountStr==null||amountStr.equalsIgnoreCase("")) {
			qickSelectOption("5000");
			
		}else {
			clearTextfield(amountToTransferTextField, "amount textfield");
			qickSelectOption("5000");
		}
		detailList.add("Amount textfield is blank-PASS");
		amountStr=getValueFromAttribute(amountToTransferTextField,"value","amount textfield");
		detailList.add("Amount Enter : "+help.commpareTwoString(amountStr, "5000"));
		detailList.add(amountTextfieldScreenshot("AmountEnterByQickSelect"));
	}
	
	public void multipleTimeClickOnQickSelect() {
		Reporter.log("=== multipleTimeClickOnQickSelect ===", true);
		clearTextfield(amountToTransferTextField, "Amount textfield");
		detailList.add("@@> Verify when user selects multiple of the amount from Quick select. <@@");
		qickSelectOption("10000");
		String amountStr=getValueFromAttribute(amountToTransferTextField,"value","amount textfield");
		detailList.add("First amount enter by QickSelect : "+help.commpareTwoString(amountStr, "10000"));
		qickSelectOption("5000");
		amountStr=getValueFromAttribute(amountToTransferTextField,"value","amount textfield");
		detailList.add("Total amount : "+help.commpareTwoString(amountStr, "15000"));
		detailList.add(amountTextfieldScreenshot("MultipleTimeClickQickSelectOption"));
	}
	
	public String amountTextfieldScreenshot(String screenshortName) {
		scroll.scrollToSpecificLocation(0, 250);
		staticWait(200);
		return ScreenshortProvider.captureScreen(driver,screenshortName);
	}
	
	public void amountTextfieldExecute(FundTransferReport fundTransferReport) {
		Reporter.log("=== amountTextfieldExecute ===", true);
		fundTransferTab=fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']","fundTransferTab");
		clickElement(fundTransferTab, "FundTransferTab");
		 
		elementLocator();
		blankAmountTextfield();
		checkAmountTextfieldGuidText();
		enterAmountLessFifty();
		enterAmountGreaterthanOneMillion();
		verifyQickSelectAmount();
		clickQickSelectOption();
		qickSelectAndEnter();
		multipleTimeClickOnQickSelect();
		
		fundTransferReport.amountTextfieldReport(detailList);
	}
}
