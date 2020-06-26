package com.shreeya.alertandnotification;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.AlertAndNotificationModel;
import com.shreeya.util.Help;
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
	
	Help help;

	private String selectStockTextfieldText;
	
	public AlertAndNotificationPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
	}
	

	public void addAlter(AlertAndNotificationModel model,String action) {
		Reporter.log("====> addAlter <====", true);
		//hoverAndClickOption("//*[@id='QuickSB']", "//a[text()='My Alerts']");
		addAlertButton=fluentWaitCodeXpath("//a[text()='Add Alert']", "Add alert button");
		clickElement(addAlertButton, "Add alert button");
		/*
		 * selectStockTextfield=
		 * fluentWaitCodeXpath("//input[@placeholder='Search stock or derivative contract']"
		 * , "select Stock Textfield"); clearAndSendKey(selectStockTextfield,
		 * model.getStockName(), "select Stock Textfield");
		 * selectStockTextfieldText=getValueFromAttribute(selectStockTextfield, "value",
		 * "select Stock Textfield");
		 */
		help.checkEnterProperMsgInTextfield("//input[@placeholder='Search stock or derivative contract']", "select Stock Textfield", model.getStockName());
		segmentSelection(model.getSegment());
		stockOptionSelectionStr="//span[text()='"+selectStockTextfieldText.trim()+"']";
		stockOptionSelection=fluentWaitCodeXpath(stockOptionSelectionStr, model.getStockName()+" option selected");
		clickElement(stockOptionSelection, model.getStockName()+" option selected");
		valueIsDropdownSelection(model.getValueIs());
		valueTextfield=fluentWaitCodeXpath("//input[@id='value']", "Value textfield");
		clearAndSendKey(valueTextfield, model.getValue(), "Value textfield");
		if(action.equalsIgnoreCase("add")) {
			addButton=fluentWaitCodeXpath("//input[@value='Add']", "Add button");
			clickElement(addButton, "Add Button");
		}else {
			updateButton=fluentWaitCodeXpath("//input[@value='Update']", "Update button");
			clickElement(updateButton, "Update button");
		}
	}
	
	public void segmentSelection(String segmentName) {
		nseOption=fluentWaitCodeXpath("//*[@id=\"alertsModal\"]/div/div/form/div[2]/div/div/div/div/div/div/div/div/div/div[1]/ul/li[1]/a", "NSE segment");
		bseOption=fluentWaitCodeXpath("//*[@id=\"alertsModal\"]/div/div/form/div[2]/div/div/div/div/div/div/div/div/div/div[1]/ul/li[2]/a", "BSE segment");
		if(segmentName.equalsIgnoreCase("NSE")) {
			
		}else if(segmentName.equalsIgnoreCase("BSE")) {
			clickElement(bseOption, "BSE segement");
		}
	}
	
	public void valueIsDropdownSelection(String valueIs) {
		valuesIsDropdown=fluentWaitCodeXpath("//button[text()='Greater than or Equal to']", "Value is dropdown");
		clickElement(valuesIsDropdown, "Value is dropdown");
		if(valueIs.toLowerCase().contains("less")) {
			valueIsDropdownOption=fluentWaitCodeXpath("//button[text()='Greater than or Equal to']//following::a[1]", "Less than or Equal to option");
			clickElement(valueIsDropdownOption, "Less than or Equal to option");
		}else if(valueIs.toLowerCase().contains("greater")) {
			valueIsDropdownOption=fluentWaitCodeXpath("//button[text()='Greater than or Equal to']//following::a[2]", "Greater than or Equal to option");
			clickElement(valueIsDropdownOption, "Greater than or Equal to option");
		}
	}
	
	public void deleteAlert(AlertAndNotificationModel model) {
		alertCountMsg=fluentWaitCodeXpath("//label[@class='count ng-binding']", "Alert count msg");
		String alertCountString=fetchTextFromElement(alertCountMsg);
		String [] alertMsgArray=help.separater(alertCountString, " ");
		int alterCount=Integer.valueOf(alertMsgArray[3]);
		for(int i=0;i<alterCount;i++) {
			
		}
	}
	
	public void modifyAlert(AlertAndNotificationModel model) {
		modifyButton=fluentWaitCodeXpath("//a[text()='Modify']", "Modify button");
		addAlter(model, "mod");
	}
	
	public void alertExecution(AlertAndNotificationModel model) {
		Reporter.log("====> alertExecution <====", true);
		AlertAndNotificationKeyword keywordObj=new AlertAndNotificationKeyword();
		List<String> keywordList=keywordObj.keywordExecution();
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
			}
		}
	}
}
