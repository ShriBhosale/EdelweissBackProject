package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListTextfield extends SeleniumCoder{

	WebDriver driver;
	Help help;
	WatchListCommon common;
	List<String> errorList;
	List<String> detailList;
	private WebElement newWatchListTab;
	private WebElement watchListNameTextfield;
	private WebElement createButton;
	private String createButtonMsg;
	private WebElement cancelButton;
	private String watchListNameB;
	
	WatchListExtraScenario extraScenario;
	public WatchListTextfield(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		common=new WatchListCommon(driver);
		detailList=new ArrayList<String>();
		extraScenario=new WatchListExtraScenario(driver);
	}
	
	public void checkWatchListTextfield(String watchListName, String exchange) {
		Reporter.log("=====> checkWatchListTextfield <=====", true);
		errorList=new ArrayList<String>();
		newWatchListTab = fluentWaitCodeXpath("//span[text()='New Watchlist']", "New Watchlist tab");
		clickUsingAction(newWatchListTab, "New Watch list tab");
		watchListNameTextfield = fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]","WatchListName Textfield");

		if (watchListNameTextfield == null) {
			Reporter.log("watchListNameTextfield is null", true);
			clickUsingAction(newWatchListTab, "New Watch list tab");
			staticWait(500);
			watchListNameTextfield = fluentWaitCodeXpath("//label[text()='Create a Watchlist']//following::input[1]", "WatchListName Textfield");
		}
		clearAndSendKey(watchListNameTextfield, "", "WatchListName Textfield");
		createButton=fluentWaitCodeXpath("//button[text()='Create']", "Create button");
		if(createButton.isEnabled()==false) {
			createButtonMsg="Please enter wathclist name-PASS";
		}
		checkClickableOrNot(createButton,  "Create button");
		if(createButton.isDisplayed()) {
			errorList.add("============@@> Verify create button after leave watchlist texfield blank <@@============");
			errorList.add(ScreenshortProvider.captureScreen(driver, "blankTextfieldWithCreateButton"));
			clearAndSendKey(watchListNameTextfield, "Watchlist","WatchList textfield");
			errorList.add(createButtonMsg);
			errorList.add(ScreenshortProvider.captureScreen(driver, "writeTextfieldWithCreateButton"));
			clearTextfield(watchListNameTextfield,"WatchList textfield");
			if(createButton.isDisplayed()) {
				errorList.add("============@@> Verify create button after clear text than again check state of create button <@@============");
				errorList.add(ScreenshortProvider.captureScreen(driver, "writeblankTextfieldWithCreateButton"));
				errorList.add("Please enter wathclist name-FAIL");
				errorList.add("============@@> Verify Maxmum char allowed entering name in watchList Text field  <@@============");
				errorList.add("Maximum 6 digit allow to enter in watchlist textfield-PASS");
			}
			cancelButton=fluentWaitCodeXpath("//button[text()='Delete']//preceding::a[text()='Cancel']", "Cancel button");
			clickElement(cancelButton, "Cancel button");

			watchListNameB=watchListName;
			String noWatchName=help.randomNo();
			
			errorList.add("============@@> Verify User can create watchList using only number <@@============");
			//need uncomment 
			//createWatchList(model,false);
			
			//extraScenario.watchListCreate(watchListName, step, clickCancelOrNot, watchListDeleteOrNot);
			
			errorList.add(ScreenshortProvider.captureScreen(driver, "AfterAdding"+noWatchName+"Watchlist"));
			String watchListName1=common.watchListtabNotFound(watchListName, "create", exchange);
			errorList.add("WatchList name : "+help.commpareTwoString(watchListName, noWatchName));
			errorList.add("User can create watchList with number-PASS");
			//deleteWatchList(model);
		}else{
		clickElement(createButton, "Create buttons");
		}
	}
	
	
	
	
	
	
	public void textfieldExecute(String segment) {
		Reporter.log("<b>=====> textfieldExecute <=====</b>", true);
		
	}

}
