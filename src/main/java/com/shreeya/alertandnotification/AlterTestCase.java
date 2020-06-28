package com.shreeya.alertandnotification;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.AlertAndNotificationModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class AlterTestCase extends SeleniumCoder{

	WebDriver driver;
	Help help;
	AlterAndNotificationCommon common;
	List<String> detailList;
	private WebElement addAlertButton;
	private String ltpButtonText;
	private WebElement valueTextfield;
	private boolean activeFlag;
	private WebElement greaterThanEqualButton;
	private String greaterTheanEqualStr;
	private String lessTheanEqualStr;
	private WebElement addAlterPopupCloseButton;
	private WebElement modifyButton;
	private WebElement modificationTitle;
	private WebElement modifyAlertPopupCloseButton;
	public AlterTestCase(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		common=new AlterAndNotificationCommon(driver);
		detailList=new ArrayList<String>();
	}
	
	public void checkAlterList(String exchange) {
		Reporter.log("=====> checkAlterList <=====", true);
		detailList.add("@@> The List of Alerts, if already present, should be displayed. <@@");
		List<String> alterList=multipleElementsTextProvider("//label[@class='ed-lbl reg scripnamenone ng-binding ng-scope']", "Alter");
		for(String alterStr:alterList)
			detailList.add(alterStr.trim());
	}
	
	public void checkGreaterThenAndEqualDropdown() {
		Reporter.log("=====> checkGreaterThenAndEqualDropdown <====", true);
		detailList.add("@@> Verify if there is a dropdown box with Greater or Equal and Lesser or Equal options. <@@");
		greaterThanEqualButton=fluentWaitCodeXpath("//label[text()='Value is']//following::div//button", "Greater than equal button");
		clickElement(greaterThanEqualButton,  "Greater than equal button");
		detailList.add(ScreenshortProvider.captureScreen(driver, "GreaterThanEqualDropDown"));
		greaterTheanEqualStr=help.commpareTwoString(fetchTextFromElement("//*[@id=\"alertsModal\"]/div/div/form/div[2]/div/div[3]/div/div/a[1]", "Less than and equal"), "Less than or Equal to");
		detailList.add(greaterTheanEqualStr);
		lessTheanEqualStr=help.commpareTwoString(fetchTextFromElement("//*[@id=\"alertsModal\"]/div/div/form/div[2]/div/div[3]/div/div/a[2]", "Greater than or Equal to"), "Greater than or Equal to");
		detailList.add(lessTheanEqualStr);
		addAlterPopupCloseButton=fluentWaitCodeXpath("//*[@id=\"alertsModal\"]/div/div/form/div[1]/a/i", "Add alter popup cloase button");
		clickElement(addAlterPopupCloseButton, "Add alter popup cloase button");
	}
	
	public void checkAlterPopup(String stockName,String segment) {
		Reporter.log("=====> checkAlterPopup <=====",true);
		detailList.add("@@> Verify if the scrip searched can be selected. On Selection of the scrip relevent Alerts page is opened <@@");
		addAlertButton=fluentWaitCodeXpath("//a[text()='Add Alert']", "Add alert button");
		clickElement(addAlertButton, "Add alert button");
		help.checkEnterProperMsgInTextfield("//input[@placeholder='Search stock or derivative contract']", "select Stock Textfield",stockName,segment);
		detailList.add("Script Name : "+getValueFromAttribute("//input[@id='tocsearch']","value" , "Script name textfield"));
		detailList.add("Script is preselected-PASS");
		detailList.add("Track basis : "+fetchTextFromElement("//button[text()='LTP']", "LTP button"));
		detailList.add("Value is : "+fetchTextFromElement("//label[text()='Value is']//following::div//button", "value is"));
		detailList.add("LTP Msg : "+fetchTextFromElement("//input[@id='value']//following::span[1]", "LTP Msg"));
		valueTextfield=fluentWaitCodeXpath("//label[text()='Value']//following::input[1]", "input value");
		if(valueTextfield!=null) 
			detailList.add("Value textfield is present-PASS");
		else
			detailList.add("Value textfield does not present-FAIL");
		detailList.add(common.checkActiveOrNot("//ul[@class='alertvia list-inline list-unstyled']//li[1]", "Email alter"));
		detailList.add(common.checkActiveOrNot("//ul[@class='alertvia list-inline list-unstyled']//li[2]", "Mobile alter"));
		detailList.add(common.checkActiveOrNot("//input[@type='submit']", "Add button"));
		detailList.add(ScreenshortProvider.captureScreen(driver, "AddAlter"));
	}
	
	public void checkmodifyAlertPage() {
		Reporter.log("======> modifyAlertPage <=====", true);
		detailList.add("@@> Verify the functionality of the Modify Button to the right side of the Alert. <@@");
		modifyButton=fluentWaitCodeXpath("//*[@id='allRecosAccord']/tbody/tr[1]/td[6]/a", "modify button");
		clickElement(modifyButton, "Modify button");
		staticWait(500);
		modificationTitle=fluentWaitCodeXpath("//label[text()='Modify Alert']", "Modify Alter page");
		if(modificationTitle!=null) {
			detailList.add("Modify alter page is open-PASS");
			
		}else
			detailList.add("Modify alter page does not open-FAIL");
		detailList.add(ScreenshortProvider.captureScreen(driver, "ModifyPopup"));
		modifyAlertPopupCloseButton=fluentWaitCodeXpath("//*[@id='alertsModal']/div/div/form/div[1]/a/i", "modify Alert Popup Close button");
		if(modifyAlertPopupCloseButton!=null)
			clickElement(modifyAlertPopupCloseButton, "modify Alert Popup Close button");
	}
	
	public void checkDeleteCancelButton(String stockName) {
		Reporter.log("=====> checkDeleteCancelButton <====", true);
		detailList.add("@@> Verify alter list after click cancel button in delete popup <@@");
		common.deleteAlert(stockName, true);
		String [] deleteArray=common.deletArray;
		detailList.add(deleteArray[0]);
		detailList.add(deleteArray[1]);
		detailList.add(deleteArray[2]);
	}
	
	
	
	public List<String> alterTestCaseExecution(AlertAndNotificationModel model,List<String> detailList) {
		Reporter.log("=====@@> alterTestCaseExecution <@@=====", true);
		this.detailList=detailList;
		staticWait(300);
		checkAlterList(model.getSegment());
		checkAlterPopup(model.getStockName(),model.getSegment());
		checkGreaterThenAndEqualDropdown();
		checkmodifyAlertPage();
		checkDeleteCancelButton(model.getStockName());
		
		return detailList;
		
	}
}
