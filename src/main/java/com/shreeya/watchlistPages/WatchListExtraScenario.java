package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.fundtransferpages.FundTransferCommon;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListExtraScenario extends SeleniumCoder {
	
	WebDriver driver;
	private WebElement myPositionTab;
	List<String> detailList;
	
	Help help;
	WatchListCommon common;
	ConfigReader configReader;
	WatchListPage watchListPage;
	private WebElement newWatchListTab;
	private WebElement closePopupButton;
	private WebElement cancelButton;
	private WebElement createWatchListPopupTitle;
	private WebElement watchListNameTextfield;
	private WebElement defaultWatchListCheckBox;
	private String cancelButtonEnableStr;
	private String enterWatchListName;
	private WebElement element;
	private String afterClickCancelButton;
	private String alphWatchListName;
	private String specialWatchListName;
	private String numberInBetweenWatchList;
	private WebElement scriptNameTextfield;
	private String enterScriptName;
	private String watchListResult;
	private String watchListPresentScreenshot;
	private WebElement addScripButton;
	private boolean addScriptButtonFlag;
	private String[] scriptArray;
	private WebElement addScriptButton;
	private String[] exchangeArray;
	private String[] tradingSymbolArray;
	private WebElement deleteButton;
	private WebElement deleteScripButton;
	private String deleteScriptButtonStr;
	private String addScriptButtonStr;
	private boolean deleteScriptButtonEnableFlag;
	private String text;
	private String xpathString;
	private WebElement addScriptTitle;
	private WebElement cancelScriptButton;
	private String creWatchWitoutScriptScreenshot;
	private String noScriptInWatchListAns;
	private WebElement addScriptCancelButton;
	private String cancelButtonMsg;
	
	List<String> addScriptList;
	private WebElement delAndDefaStartOptInWatchList;
	

	public WatchListExtraScenario(WebDriver driver) {
		super(driver);
		this.driver=driver;
		detailList=new ArrayList<String>();
		addScriptList=new ArrayList<String>();
		help=new Help(driver);
		common=new WatchListCommon(driver);
		watchListPage=new WatchListPage(driver);
		configReader=new ConfigReader();
	}
	
	public void watchListTab(String segment) {
		Reporter.log("====> watchListTab  <====", true);
		detailList.add("@@> verify number of Tabs present in Watchlist page <@@");
		detailList.add(help.elementPresent("//span[text()='New Watchlist']", "New WatchList tab"));
		detailList.add(ScreenshortProvider.captureScreen(driver, "WatchListPage"));
		if(segment.equalsIgnoreCase("Equity")) {
			checkTabPresetOrNot("//a[text()='Nifty 50']//preceding::a[text()='My Positions'][1]", "My position ", segment);
			checkTabPresetOrNot("//a[text()='Nifty 50']//preceding::a[text()='My Holdings'][1]","My Holdings ", segment);	
			checkTabPresetOrNot("//a[text()='Nifty 50']","Nifty 50 ", segment);
			checkTabPresetOrNot("//a[text()='Sensex']", "Sensex ", segment);
		
		}
		
	}
	
	public void checkDeleteOptionForDeviceAndPredefineWatchList(String segment) {
		if(segment.equalsIgnoreCase("Equity")) {
		Reporter.log("====> checkDeleteOptionForDeviceAndPredefineWatchList <====", true);
		detailList.add("@@> Verify user is able to delete Device watchlist i.e my position, My holdings <@@");
		checkDeleteOptionForWatchList("My Positions");
		  checkDeleteOptionForWatchList("My Holdings");
		detailList.add("@@> Verify user is able to delete Pre defined watchlist i.e Nifty 50, Sensex <@@");
		 checkDeleteOptionForWatchList("Nifty 50"); 
		checkDeleteOptionForWatchList("Sensex");
		}
	}
	
	public void checkDeleteOptionForWatchList(String watchListName) {
		Reporter.log("=====> checkDeleteOptionForWatchList <=====", true);
		common.watchListtabNotFound(watchListName, "check", configReader.configReaderWL("Exchange"));
		detailList.add(ScreenshortProvider.captureScreen(driver, watchListName+"DeleteOptionPresentOrNot"));
		String xpath="//*[@id='contentCntr']/div/div/div[1]/ul/li/a[text()='"+watchListName+"']//following::ul[@class='action-button hidden-xs']";
		delAndDefaStartOptInWatchList=fluentWaitCodeXpath(xpath,20,"Delete and default option for watchList");
		if(delAndDefaStartOptInWatchList==null) 
			detailList.add(watchListName+" watchlist do not have delete option-PASS");
		else
			detailList.add(watchListName+ " watchlist have delete option-FAIL");
		
		
	}
	
	
	public String enterWatchListTextfield(String watchListName) {
		Reporter.log("===> enterWatchListTextfield <===", true);
		watchListNameTextfield = fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]",200,"WatchListName Textfield");
		if(watchListNameTextfield!=null) {
		staticWait(2000);
		clearAndSendKey(watchListNameTextfield, watchListName, "WatchList name");
		}else {
			staticWait(2000);
			watchListNameTextfield = fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]","WatchListName Textfield");
			clearAndSendKey(watchListNameTextfield, watchListName, "WatchList name");
		}
		enterWatchListName=getValueFromAttribute(watchListNameTextfield, "value", "watchList name textfield");
		Reporter.log("Enter watchList name : "+enterWatchListName, true);
		watchListResult=help.commpareTwoString(enterWatchListName, watchListName);
		return enterWatchListName;
	}
	
	public String enterScriptTextfield(String scriptName) {
		scriptNameTextfield = fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]","WatchListName Textfield");
		clearAndSendKey(scriptNameTextfield, scriptName, "script name");
		enterScriptName=getValueFromAttribute(scriptNameTextfield, "value", "script name textfield");
		Reporter.log("Enter watchList name : "+enterScriptName, true);
		return enterWatchListName;
	}
	
	public String elementEnableOrNot(String xpath,String elementName) {
		Reporter.log(elementName+" enable or not", true);
		String result = null;
		element=fluentWaitCodeXpath(xpath,elementName);
		if(element!=null) {
			if(element.isEnabled())
			{
				result=elementName+" is enable-PASS";
			}else
				result=elementName+" is disable-PASS";
		}
		Reporter.log(result, true);
		return result;
	}
	
	
	
	
	
	public void watchListCreate(String watchListName,String step,boolean clickCancelOrNot,boolean watchListDeleteOrNot) {
		Reporter.log("===> watchListCreate <===", true);
		common.clickNewWatchListButton();
		enterWatchListTextfield(watchListName);
		
		cancelButtonEnableStr=elementEnableOrNot("//h4[text()='Create a Watchlist']//following::a[1]","Cancel button");
		if(clickCancelOrNot) {
			clickElement("//h4[text()='Create a Watchlist']//following::a[1]","Cancel button");
			createWatchListPopupTitle=fluentWaitCodeXpath("//h4[text()='Create a Watchlist']//following::a[1]",20, "create watchList popup title");
			if(createWatchListPopupTitle.isDisplayed())
				afterClickCancelButton="Create watchlist Dialogue box  get closed-PASS";
			else
				afterClickCancelButton="Create watchlist Dialogue box is not get closed-FAIL";
		}else if(step.equalsIgnoreCase("No Scrip")) {
			noScriptInWatchList(watchListName);
		}else {
			clickElement("//button[text()='Create']", "Create buttons");
			common.addScript(enterWatchListName,configReader.configReaderWL("ScriptName"),configReader.configReaderWL("tradingSymbol"), configReader.configReaderWL("Exchange"), step, detailList);
		}
		
		 
		checkWatchListPresentOrNot(enterWatchListName, step);
		if(watchListDeleteOrNot)
		common.deleteWatchList(enterWatchListName,configReader.configReaderWL("Exchange"));
	}
	
	public void noScriptInWatchList(String watchListName) {
		Reporter.log("====> noScriptInWatchList <====", true);
		clickElement("//button[text()='Create']", "Create buttons");
		checkAddScriptButton();
		creWatchWitoutScriptScreenshot=ScreenshortProvider.captureScreen(driver, "createWatchListWitoutScriptScreenshot");
		text = "Add a Scrip to " +watchListName;
		xpathString = "//h4[text()='" + text + "']//following::a[1]";
		addScriptCancelButton=fluentWaitCodeXpath(xpathString, "Add script cancel button");
		if(addScriptCancelButton.isEnabled()) {
			cancelButtonMsg="In Add script Dialog box cancel button is present and clickable-PASS";
		clickElement(addScriptCancelButton, "Cancel script button");
		}else {
			cancelButtonMsg="In Add script Dialog box cancel button is present and disable-FAIL";
		}
		String ans=common.watchListtabNotFound(watchListName, "Checking", configReader.configReaderWL("Exchange"));
		Reporter.log("noScriptWatchList Name  : "+ans, true);
		if(ans.equalsIgnoreCase("WatchList not found")) {
			noScriptInWatchListAns="Without Script we can not create watchList-PASS";
		}else {
			noScriptInWatchListAns="We can create watchList without script-FAIL";
		}
	}
	
	public void checkAddScriptButton() {
		addScriptList=common.checkAddScripButton("Nowat",configReader.configReaderWL("Exchange"),configReader.configReaderWL("ScriptName"), "Create");
	}
	
	public void checkWatchListPresentOrNot(String watchListName,String step) {
		Reporter.log("===> checkWatchListPresentOrNot <====", true);
		if(watchListResult.contains("PASS")) {
			String watchList=common.watchListtabNotFound(watchListName, step, configReader.configReaderWL("Exchange"));
			watchListResult=help.commpareTwoString(watchList, watchListName);
			Reporter.log("<b>watchListResult : "+watchListResult+"</b>", true);
		}
		watchListPresentScreenshot=ScreenshortProvider.captureScreen(driver, watchListName+"watchListPresent");
	}
	
	public void checkTabPresetOrNot(String xpath,String elementName,String segment) {
		Reporter.log("====> checkTabPresetOrNot <===", true);
		WebElement element=fluentWaitCodeXpath(xpath,30,elementName);
		if(element!=null)
		detailList.add(help.elementPresent(element,elementName+"Tab "));
		else {
			String tabName=common.watchListtabNotFound(elementName, "Tab verify", segment);
			detailList.add(help.commpareTwoString(tabName, elementName));
		}
		
			
	}
	
	public void createAlphaNumericWatchList(String watchListName) {
		Reporter.log("====> createAlphaNumericWatchList <====", true);
		detailList.add("@@> Verify Alpha numeric is allowed in b/w char in Name field <@@");
		detailList.add("WatchList Name : "+watchListName);
		detailList.add("Enter watchList name : "+watchListResult);
		detailList.add(watchListPresentScreenshot);
	}
	
	public void createSpecialCharWatchList(String watchListName) {
		Reporter.log("====> createSpecialCharWatchList <====", true);
		detailList.add("@@> Verify Special char is allowed in b/w char in Name field <@@");
		detailList.add("WatchList Name : "+watchListName);
		detailList.add("Enter watchList name : "+enterWatchListName);
		if(watchListResult.contains("FAIL"))
			detailList.add("Special charater not allowed in watchlist name-PASS");
		else
			detailList.add("Special charater allowed in watchlist name-FAIL");
		detailList.add(watchListPresentScreenshot);
	}
	
	public void createNumberInBetWatchList(String watchListName) {
		Reporter.log("====> createNumberInBetWatchList <====", true);
		detailList.add("@@> Verify numeric is allowed in b/w char in Name field <@@");
		detailList.add("WatchList Name : "+watchListName);
		detailList.add("Enter watchList name : "+enterWatchListName);
		if(watchListResult.contains("FAIL"))
			detailList.add("In between number is not allow in watchList name-FAIL");
		else
			detailList.add("In between number allow in watchList name-PASS");
		
		detailList.add(watchListPresentScreenshot);
	}
	
	
	public void addScript(String watchListName,String step) {
		Reporter.log("=====> addScript <=====");
		scriptArray=help.commaSeparater(configReader.configReaderWL("AddScriptName"));
		exchangeArray=help.commaSeparater(configReader.configReaderWL("AddExchange"));
		tradingSymbolArray=help.commaSeparater(configReader.configReaderWL("AddTradingSymbol"));
		addScripButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add Script button");
		if(addScripButton!=null) {
			if(addScripButton.isEnabled())
				addScriptButtonStr="Add script button is present and enable";
			else
				addScriptButtonStr="Add script button is not present and enable";
		}
		
		for (int i = 0; i < scriptArray.length; i++) {
			addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
			try {
				clickElement(addScriptButton, "Add script button");
			} catch (ElementClickInterceptedException e) {
				
				text = "Add a Scrip to " +watchListName;
				xpathString = "//h4[text()='" + text + "']//following::input[3]";
				addScriptTitle=fluentWaitCodeXpath(xpathString,20, "Add script");
				if(addScriptTitle==null) {
				addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
				clickElement(addScriptButton, "Add script button");
				}
			}
			
			detailList=common.addScript(watchListName, scriptArray[i], tradingSymbolArray[i], exchangeArray[i], step, detailList);
		}
		
		
	}
	
	public void deleteScriptButtonChecker() {
		Reporter.log("deleteScriptButtonChecker",true);
		deleteScripButton=fluentWaitCodeXpath("//a[text()='Add Scrip']//following::a[1]", "delete script button");
		if(deleteScripButton!=null) {
			if(deleteScripButton.isDisplayed())
				deleteScriptButtonEnableFlag=true;
			else
				deleteScriptButtonEnableFlag=false;
		}
	}
	
	public void checkDelScrButOneScriptPresent(String watchListName) {
		Reporter.log("====> checkDelScrButOneScriptPresent <===", true);
		detailList.add("@@> Verify initially delete scrip button is disable <@@");
		checkWatchListPresentOrNot(watchListName, "Checking");
		detailList.add("WatchList name : "+watchListName);
		deleteScriptButtonChecker();
		if(deleteScriptButtonEnableFlag==false)
			detailList.add("Delete Script button is disable-PASS");
		else
			detailList.add("Delete script button is enable-FAIL");
		
	}
	
	public void deleteScript(String watchListName) {
		Reporter.log("====> deleteScript <====", true);
		deleteScripButton=fluentWaitCodeXpath("//a[text()='Add Scrip']//following::a[1]", "delete button");
		
	}
	
	public void deleteMultipleScript() {
		Reporter.log("====> deleteMultipleScript <======", true);
		detailList.add("@@> Verify when user select More than 1 scrip from Watchlist and click on delete scrip button  <@@");
		List<String> deleteScriptList=WatchListCommon.deleteScriptArray;
		for(String detail:deleteScriptList) {
			detailList.add(detail);
		}
	}
	
	public void withoutScripCreateWatchList() {
		Reporter.log("====> withoutScripCreateWatchList <====", true);
		detailList.add("@@> Verify user can create a watchlist without any scrip <@@");
		detailList.add(noScriptInWatchListAns);
		detailList.add(creWatchWitoutScriptScreenshot);
		detailList.add("@@> verify in add scrip dialogue box Cancel button is present <@@");
		detailList.add(cancelButtonMsg);
	}
	
	public void printAddScriptButton() {
	Reporter.log("===> printAddScriptButton <====", true);
		detailList.add("@@> verify initially in add scrip dialogue box add scrip button is disabled <@@");
		for(String str:addScriptList) {
			if(str.equalsIgnoreCase("After clear addScriptTextfield")) {
				str="@@> verify in add scrip dialogue box add scrip  button is disabled when add scrip field is blank <@@";
			}
			detailList.add(str);
		}
	}
	
	public void checkWatchListCancelButton() {
		Reporter.log("====> checkWatchListCancelButton  <====", true);
		detailList.add("@@> verify in dialogue box Cancel button is clickable <@@");
		if(afterClickCancelButton.contains("PASS"))
			detailList.add("Create wathclist dialogue box's Cancel button is clickable-PASS");
		else
			detailList.add("Create wathclist dialogue box's Cancel button not is clickable-FAIL");
		detailList.add("@@> Verify when user clicks on cancel button <@@");
		detailList.add(afterClickCancelButton);
	}
	
	public void checkDuplicateScripPresentOrNot(String segment) {
		if(segment.equalsIgnoreCase("Equity")) {
		Reporter.log("====> checkDuplicateScripPresentOrNot <====", true);
		detailList.add("@@> Verify Preset Watchlist is available for creating duplicate symbols <@@");
		detailList.add(common.checkDuplicateInWatchList("Nifty 50", "Checking", configReader.configReaderWL("Exchange")));
		detailList.add(common.checkDuplicateInWatchList("Sensex", "Checking", configReader.configReaderWL("Exchange")));
		}
	}
	
	
	
	public ExtendReporter watchListExtraScenarioExecute(String segment,ExtendReporter reporter) {
		Reporter.log("<b>=====@@> watchListExtraScenarioExecute <@@=====</b>", true);
		configReader.watchListConfig();
		common.redirectToWatchListModule(true);
		//watchListTab(segment);
		
		
		
		  watchListCreate("ABC3", "Create", true,false); checkWatchListCancelButton();
		  
		  watchListCreate("Auto10", "Create", false,false);
		  createAlphaNumericWatchList("Auto10");
		  
		  
		  watchListCreate("watch@L", "Create", false,true);
		  createSpecialCharWatchList("watch@L");
		  
		  watchListCreate("wat12L", "Create",false,true);
		  createNumberInBetWatchList("wat12L");

		  checkDelScrButOneScriptPresent("Auto10"); addScript("Auto10", "AddScript");
		  common.deleteScript("Auto10", tradingSymbolArray, "deleteScript", "NSE");
		  deleteMultipleScript();

		  watchListCreate("Nowat", "No Scrip", false, false);
		  withoutScripCreateWatchList(); printAddScriptButton();

		checkDeleteOptionForDeviceAndPredefineWatchList(segment);
		checkDuplicateScripPresentOrNot(segment);
		reporter.watchListExtraScenario(detailList);
		 
		return reporter;
	}
	
	
}
