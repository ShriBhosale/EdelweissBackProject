package com.shreeya.alertandnotification;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.commonpage.CommonOrderDetail;
import com.shreeya.commonpage.PlaceOrder;
import com.shreeya.model.AlertAndNotificationModel;
import com.shreeya.model.OrderPlaceModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class AlertAndNotificationPage extends SeleniumCoder{
	WebDriver driver;
	
	WebElement addAlertButton;
	WebElement selectStockTextfield;
	WebElement nseOption;
	WebElement bseOption;
	WebElement stockOptionSelection;
	WebElement valuesIsDropdown;
	WebElement valueIsDropdownOption;
	WebElement valueTextfield;
	WebElement addButton;
	WebElement alertCountMsg;
	WebElement modifyButton;
	WebElement updateButton;
	String stockOptionSelectionStr;
	AlterAndNotificationCommon common;
	AlterTestCase testCase;
	PlaceOrder placeOrder;
	CommonOrderDetail orderDetail;
	Help help;
	List<String> detailList;

	private String selectStockTextfieldText;

	private WebElement msgCloseButton;

	private int scriptNo;

	private WebElement scriptNameCheckBox;

	private WebElement deleteButton;

	private String modifyButtonXpath;

	private WebElement greaterThanEqualButton;

	private String greaterTheanEqualStr;

	private String lessTheanEqualStr;

	private WebElement addAlterPopupCloseButton;

	private WebElement modificationTitle;

	private WebElement modifyAlertPopupCloseButton;

	private WebElement errorMsgLabel;

	private WebElement closeButton;

	private String[] orderDetailArray;
	
	public AlertAndNotificationPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		placeOrder=new PlaceOrder(driver);
		testCase=new AlterTestCase(driver);
		orderDetail=new CommonOrderDetail(driver);
		common=new AlterAndNotificationCommon(driver);
	}
	

	public void addAlter(AlertAndNotificationModel model,String action) {
		Reporter.log("====> addAlter <====", true);
		staticWait(2000);
		if(action.equalsIgnoreCase("add")) {
			
			detailList.add("@@> Verify the functionality of the ADD button. <@@");
			addAlertButton=fluentWaitCodeXpath("//a[text()='Add Alert']", "Add alert button");
			clickElement(addAlertButton, "Add alert button");
			help.checkEnterProperMsgInTextfield("//input[@placeholder='Search stock or derivative contract']", "select Stock Textfield", model.getStockName(),model.getSegment());
		}else {
			detailList.add("@@> Verify the functionality of the Modify Button. <@@");
			modifyButtonXpath="//*[@id=\"allRecosAccord\"]/tbody/tr["+common.scriptIndex(model.getStockName())+"]/td[6]/a";
			Reporter.log(modifyButtonXpath, true);
			modifyButton=fluentWaitCodeXpath(modifyButtonXpath, "Modify button");
			clickElement(modifyButton, "Modify button");
		}
		
		
		/*
		 * stockOptionSelectionStr="//span[text()='"+selectStockTextfieldText.trim()+
		 * "']"; stockOptionSelection=fluentWaitCodeXpath(stockOptionSelectionStr,
		 * model.getStockName()+" option selected"); clickElement(stockOptionSelection,
		 * model.getStockName()+" option selected");
		 */
		
		valueTextfield=fluentWaitCodeXpath("//input[@id='value']", "Value textfield");
		
		if(action.equalsIgnoreCase("add")) {
			valueIsDropdownSelection(model.getValueIs());
			clearAndSendKey(valueTextfield, model.getValue(), "Value textfield");
			addButton=fluentWaitCodeXpath("//input[@value='Add']", "Add button");
			clickElement(addButton, "Add Button");
			detailList.add(help.removeHtmlReporter(fetchTextFromElement("//label[@class='mbMsg ng-binding']", action+" Msg")));
			common.verifyAlert(model.getStockName(), model.getValue(), model.getValueIs(), detailList);
		}else {
			valueIsDropdownSelection(model.getModifyValueIs());
			clearAndSendKey(valueTextfield,model.getModifyValule(), "Value textfield");
			updateButton=fluentWaitCodeXpath("//input[@value='Update']", "Update button");
			clickElement(updateButton, "Update button");
			detailList.add(help.removeHtmlReporter(fetchTextFromElement("//label[@class='mbMsg ng-binding']", action+" Msg")));
			common.verifyAlert(model.getStockName(), model.getModifyValule(), model.getModifyValueIs(), detailList);
		}
		
		detailList.add(ScreenshortProvider.captureScreen(driver, "afterAddButton"));
		
		msgCloseButton=fluentWaitCodeXpath("//*[@id=\"msgblock\"]/a/i",50, "Msg Close button");
		if(msgCloseButton!=null)
		clickElement(msgCloseButton,"Msg Close button");
		
	}
	
	
	
	public void valueIsDropdownSelection(String valueIs) {
		valuesIsDropdown=fluentWaitCodeXpath("//label[text()='Value is']//following::button", "Value is dropdown");
		clickElement(valuesIsDropdown, "Value is dropdown");
		if(valueIs.toLowerCase().contains("less")) {
			valueIsDropdownOption=fluentWaitCodeXpath("//label[text()='Value is']//following::a[1]", "Less than or Equal to option");
			clickElement(valueIsDropdownOption, "Less than or Equal to option");
		}else if(valueIs.toLowerCase().contains("greater")) {
			valueIsDropdownOption=fluentWaitCodeXpath("//label[text()='Value is']//following::a[2]", "Greater than or Equal to option");
			clickElement(valueIsDropdownOption, "Greater than or Equal to option");
		}
	}
	
	public void deleteAlert(AlertAndNotificationModel model) {
		Reporter.log("=====> deleteAlert <=====", true);
		detailList.add("@@> Verify the functionality of the delete icon. <@@");
		alertCountMsg=fluentWaitCodeXpath("//label[@class='count ng-binding']", "Alert count msg");
		String alertCountString=fetchTextFromElement(alertCountMsg);
		String [] alertMsgArray=help.separater(help.removeHtmlReporter(alertCountString).trim()," ");
		int alterCount=Integer.valueOf(alertMsgArray[3]);
		scriptNo=common.scriptIndex(model.getStockName());
		common.deleteAlert(model.getStockName(), false);
		/*
		 * Reporter.log("Check box : "+"//*[@id=\"allRecosAccord\"]/tbody/tr["+
		 * alterCount+"]/td[1]/a[1]/label", true);
		 * scriptNameCheckBox=fluentWaitCodeXpath(
		 * "//*[@id=\"allRecosAccord\"]/tbody/tr["+alterCount+"]/td[1]/a[1]/label",
		 * model.getStockName()+" checkBox"); clickByActionClass(scriptNameCheckBox,
		 * model.getStockName()+" checkBox"); //clickElement(scriptNameCheckBox,
		 * model.getStockName()+" checkBox");
		 * deleteButton=fluentWaitCodeXpath("//a[text()='Delete Alert']",
		 * "Delete alert"); clickElement(deleteButton, "Delete alert");
		 * alertCountMsg=fluentWaitCodeXpath("//label[@class='count ng-binding']",
		 * "Alert count msg"); alertCountString=fetchTextFromElement(alertCountMsg);
		 * detailList.add("After delete : "+alertCountString);
		 */
		String [] deleteArray=AlterAndNotificationCommon.deletArray;
		for(String deleteStr:deleteArray)
			detailList.add(deleteStr);
		
	}
	
	public void modifyAlert(AlertAndNotificationModel model) {
		Reporter.log("=====> modifyAlert <=====", true);
		Reporter.log("//*[@id=\"allRecosAccord\"]/tbody/tr["+common.scriptIndex(model.getStockName())+"]/td[6]/a", true);
		
		addAlter(model, "mod");
	}
	
	public void testCaseExecute(AlertAndNotificationModel model,String segment) {
		Reporter.log("======> testCaseExecute <=====", true);
		
		alterTestCaseExecution(model,segment,detailList);
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
		//detailList.add(stockName+" is preselected-PASS");
		detailList.add("Track basis : "+fetchTextFromElement("//button[text()='LTP']", "LTP button"));
		detailList.add("Value is : "+help.removeHtmlReporter(fetchTextFromElement("//label[text()='Value is']//following::div//button", "value is")));
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
	
	public void checkmodifyAlertPage(String tickSize) {
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
		
		verifyEnterValueAnotherThenMultipleOfTickSize(tickSize);
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
	
	public void verifyAddAlertPopup(AlertAndNotificationModel model) {
		Reporter.log("======> verifyAddAlertPopup  <=====", true);
		detailList.add("@@> Verify if the scrip searched can be selected. On Selection of the scrip relevent Alerts page is opened  <@@");
		addAlertButton=fluentWaitCodeXpath("//a[text()='Add Alert']", "Add alert button");
		if(addAlertButton!=null) {
			if(addAlertButton.isDisplayed())
			clickElement(addAlertButton, "Add alert button");
		}
		help.checkEnterProperMsgInTextfield("//input[@placeholder='Search stock or derivative contract']", "select Stock Textfield",model.getStockName(),model.getSegment());
		detailList.add("Script Name : "+getValueFromAttribute("//input[@id='tocsearch']","value" , "Script name textfield"));
		//detailList.add(stockName+" is preselected-PASS");
		detailList.add("Track basis : "+fetchTextFromElement("//button[text()='LTP']", "LTP button"));
		detailList.add("Value is : "+help.removeHtmlReporter(fetchTextFromElement("//label[text()='Value is']//following::div//button", "value is")));
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
		detailList.add(ScreenshortProvider.captureScreen(driver, "AddAlertPage"));
	}
	
	public void verifyScripCriteria(AlertAndNotificationModel model,String segment) {
		Reporter.log("======> verifyScripCriteria <========");
		detailList.add("@@> Verify if the Alert is removed from the Alerts list once the Alert criteria is met and Alert is triggered. <@@");
		help.redirectToOrderDetailTabModule(false);
		String sellOrderStatus="no";
		OrderPlaceModel orderPlaceModel=new OrderPlaceModel(model.getReferNo(), model.getStockName(), model.getSegment(), "Buy", model.getValue(), model.getProductType(), model.getQty());
		String orderDetailScreenshot=placeOrder.orderPlace(orderPlaceModel);
		if(segment.equalsIgnoreCase("Equity"))
		 orderDetailArray=orderDetail.orderDetailProvider(driver, "new", "1", orderPlaceModel);
		else
		{
			List<String> orderDetailList=orderDetail.commodityorderDetail(orderPlaceModel);
			orderDetailArray=new String [5];
			orderDetailArray[2]=orderDetailList.get(0);
		}
		detailList.add(orderDetailScreenshot);
		if(orderDetailArray.length!=0) {
			if(orderDetailArray[2].contains("Open")) {
				
				sellOrderStatus=common.placeOrderWithDiffUserId(segment,model);
			if(sellOrderStatus.equalsIgnoreCase("Complete"))
				if(common.checkScriptPresent(model.getStockName()).contains("is not present"))
					detailList.add("Alert is remove from list-PASS");
				else
					detailList.add("Alert is not remove from list-PASS");
			}else {
				detailList.add(orderDetailArray[2]);
			}
		}
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",50,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
	}
	
	public void verifyEnterValueAnotherThenMultipleOfTickSize(String tickSize) {
		Reporter.log("======> verifyEnterValueAnotherThenMultipleOfTickSize <=====",true);
		detailList.add("@@> Verify if the Value entered in the Edit Alert screen is other than multiple of tick size. <@@");
		valueTextfield=fluentWaitCodeXpath("//input[@id='value']", "Value textfield");
		clearAndSendKey(valueTextfield,tickSize, "Value textfield");
		valuesIsDropdown=fluentWaitCodeXpath("//label[text()='Value is']//following::button", "Value is dropdown");
		doubleclickElement(valuesIsDropdown, "Value is dropdown");
		errorMsgLabel=fluentWaitCodeXpath("//input[@value='Update']//preceding::span[1]", "Error msg");
		if(elementPresentOrNot(errorMsgLabel))
			detailList.add(help.commpareTwoString(fetchTextFromElement(errorMsgLabel), "Please enter value more than 0 "));
		detailList.add(ScreenshortProvider.captureScreen(driver, "valueEnterWithNotMultipleTickSize"));
	}
	
	public List<String> alterTestCaseExecution(AlertAndNotificationModel model,String segment,List<String> detailList) {
		Reporter.log("=====@@> alterTestCaseExecution <@@=====", true);
		this.detailList=detailList;
		staticWait(300);
		 checkAlterList(model.getSegment()); 
		 
			
			  verifyAddAlertPopup(model);
			  
			  checkGreaterThenAndEqualDropdown();
			  checkmodifyAlertPage(model.getTickSize());
			  checkDeleteCancelButton(model.getStockName());
			 
		  verifyScripCriteria(model, segment);
			 
		 
		return detailList;
		
	}
	
	public void alertExecution(AlertAndNotificationModel model,ExtendReporter reporter,String segment) {
		Reporter.log("====> alertExecution <====", true);
		AlertAndNotificationKeyword keywordObj=new AlertAndNotificationKeyword();
		List<String> keywordList=keywordObj.keywordExecution();
		detailList=new ArrayList<String>();
		for(String keyword:keywordList) {
			switch(keyword) {
			case "addAlert":
				addAlter(model, "add");
				break;
				
			case "modifyAlert":
				modifyAlert(model);
				break;
				
			case "deleteAlert":
				deleteAlert(model);
				break;
			case "testCase":
				testCaseExecute(model,segment);
				break;
			case "report":
				reporter.alertRport(model.getStockName()+"_"+model.getSegment(),detailList);
				break;
			}
		}
	}
}
