package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.annotation.meta.Exhaustive;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListCommon extends SeleniumCoder{
	
	WebDriver driver;
	String tabName;
	private WebElement addScriptTextfield;
	private WebElement addScriptDropDownOption;
	private WebElement okButton;
	private String errorMsg;
	private WebElement nseRadioButton;
	private WebElement bseRadioButton;
	private List<String> errorList;
	private String watchListCreateMsg;
	private WebElement defaultWatchList;
	private String deleteOption;
	private WebElement scriptNameLabel;
	private String scriptName;
	
	Help help;
	private Integer scriptCount;
	private String tradingSysmbol;
	private WebElement scriptCheckBox;
	private WebElement deleteScripButton;
	private String   deleteScriptButtonText;
	private WebElement deleteButton;
	private WebElement popupDeleteButton;
	private WebElement deleleokButton;
	private String text;
	private String dropdownOptionStr;
	private String xpathString;
	private WebElement watchListTab;
	private String SelectWatchListDropdown;
	private WebElement addScriptButton;
	private WebElement closePopupButton;
	private WebElement deleteWatchListButton;
	private WebElement okButtonDelete;
	private WebElement newWatchListTab;
	private WebElement createWatchListPopupTitle;
	private WebElement watchListTitle;
	private WebElement popupErrorMsg;
	private WebElement addScriptTitle;
	private WebElement scriptLink;
	private WebElement selectWatchListDropdown;
	
	public static List<String> deleteScriptArray;
	

	public WatchListCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		errorList=new ArrayList<String>();
		deleteScriptArray=new ArrayList<String>();
	}
	
	public void WatchListCommon() {}
	
	public String pageVerify(WatchListModel model,String step) {
		driver.navigate().refresh();
		String createdWatchlistTab=null;
		if(!(step.contains("Delete"))) {
		if(model.getPredefineWatchList().equalsIgnoreCase("No")) {	
		 createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		}else {
			 createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		}
		WebElement createWatchList=fluentWaitCodeXpath(createdWatchlistTab,10,"watchList");	
		if(createWatchList!=null) {
		clickElement(createWatchList,model.getWatchListName()+" Watchlist Tab");
		tabName=model.getWatchListName();
		}
		else {
			tabName=commodityTabClicker(model.getWatchListName(),model.getExchange());
			if(tabName.equalsIgnoreCase("tab")) 
				tabName=tabNotFound(model.getWatchListName(),model.getExchange());
			
			}
		}	
		return tabName;
		}
	
	public boolean selectWatchListDropdownNotFound() {
		boolean seletWatchListDropdownFlag=false;
		Reporter.log("====> selectWatchListDropdownNotFound <====", true);
	
		addScriptButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", 30, "Add script button");
		if(addScriptButton==null) {
			if(addScriptButton.isDisplayed()==false) {
				driver.navigate().refresh();
				staticWait(1000);
				selectWatchListDropdown=fluentWaitCodeXpath("//a[text()='Select Watchlist']", "Select watchList dropdown");
				if(SelectWatchListDropdown!=null) {
					if(selectWatchListDropdown.isDisplayed())
					clickElement(selectWatchListDropdown, "Select watchList button");
				}
		}else
				seletWatchListDropdownFlag=true;
			
		
		}
		return seletWatchListDropdownFlag;
	}
	
	public String tabNotFound(String watchListName,String exchange) {
		Reporter.log("=====> tabNotFound <====", true);
		if(exchange.equalsIgnoreCase("NSE")||exchange.equalsIgnoreCase("BSE")||
				exchange.equalsIgnoreCase("CDS")||exchange.equalsIgnoreCase("FNO")||exchange.equalsIgnoreCase("NFO")) {
			selectWatchListDropdown=fluentWaitCodeXpath("//a[text()='Select Watchlist']", "Select watchList dropdown");
			if(selectWatchListDropdown!=null) {
				if(selectWatchListDropdown.isDisplayed())
				clickElement(selectWatchListDropdown, "Select watchList button");
			}else
				selectWatchListDropdownNotFound();
		String watchListOptionxpath="//a[text()='"+watchListName+"']";
		staticWait(500);
		watchListTab=fluentWaitCodeXpath(watchListOptionxpath, 10,watchListName+" tab");
		
		if(watchListTab!=null) {
			if(watchListTab.isDisplayed())
		clickElement(watchListTab, watchListName+" option ");
			else {
				//some time watchListTab find two element 
				watchListOptionxpath="//span//a[text()='"+watchListName+"']";
				watchListTab=fluentWaitCodeXpath(watchListOptionxpath, 10,watchListName+" tab");
				clickElement(watchListTab, watchListName+" option ");
			}
		}else {
			
			watchListName="WatchList not found";
		}}else 
			watchListName="WatchList not found";
		
		return watchListName;
		
	}
	
	public String exchangeFilter(String exchange) {
		Reporter.log(exchange, true);
		String [] exchangeArray=exchange.split("amp;");
		exchange=exchangeArray[0]+exchangeArray[1];
		return exchange;
	}
	
	public String commodityTabClicker(String watchListName,String exchange) {
		String tabString="tab";
		if(exchange.equalsIgnoreCase("MCX")||exchange.equalsIgnoreCase("NCDEX")) {
		WebElement selectWatchList=fluentWaitCodeXpath("//a[text()='Select Watchlist']", "Select watchList");
		clickElement(selectWatchList, "Select WatchList tab");
		
			List<WebElement> elements=multipleElementLocator("//*[@id='contentCntr']/div/div/div[1]/ul/li[7]/ul/li/span", "User created watchList tab");
			for(int i=elements.size();i>2+1;i--) {
				tabString="//*[@id='contentCntr']/div/div/div[1]/ul/li[7]/ul/li/span["+i+"]/a";
				WebElement watchlistTab=fluentWaitCodeXpath(tabString, "WatchList Tab");
				tabString=fetchTextFromElement(watchlistTab);
				if(tabString.contains(watchListName)) {
					clickElement(watchlistTab, "WatchList tab");
					tabString=watchListName;
					break;
				}
			}
		}
		return tabString;
	}
	
	public String watchListtabNotFound(String watchListName,String step,String exchange) {
		driver.navigate().refresh();
		String createdWatchlistTab=null;
		if(!(step.contains("Delete"))) {
		if(watchListName.equalsIgnoreCase("No")) {	
		 createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+watchListName+"']";
		}else {
			 createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+watchListName+"']";
		}
		WebElement createWatchList=fluentWaitCodeXpath(createdWatchlistTab,10,"watchList");	
		if(createWatchList!=null) {
			if(createWatchList.isDisplayed()) {
				clickElement(createWatchList,watchListName+" Watchlist Tab");
				tabName=watchListName;
		}else {
			tabName=tabNotFound(watchListName,exchange);
		}
		}
		else {
			tabName=commodityTabClicker(watchListName,exchange);
			if(tabName.equalsIgnoreCase("tab")) 
				tabName=tabNotFound(watchListName,exchange);
			
			}
		}	
		
		return tabName;
	}
	
	public List<String> addScript(String watchListName,String scriptName,String tradingSymbol,String exchange,String step,List<String> errorList) {
		
		String xpathString = "no xpath for script";
		String dropdownOptionStr = null;
		String text = null;
		if (step.equalsIgnoreCase("Create")) {
		if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
			dropdownOptionStr = "//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/div/ul[1]/li/a[text()='"+tradingSymbol + " (" + exchange + ")" + "']";
			Reporter.log("DropDown Xpath : "+dropdownOptionStr, true);
		} else {
			dropdownOptionStr = "//*[@id='watchlist']/div/div/div[2]/div[2]/div/div/div/ul/li/a[text()='"+scriptName + "']";

		}

		
			text = "Add a Scrip to " +watchListName.trim();
			xpathString = "//h4[text()='" + text + "']//following::input[2]";

		} else if (step.equalsIgnoreCase("AddScript")) {

			if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
				text = "Add a Scrip to ";
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul[1]/li/a[text()='"+scriptName + " (" +exchange+ ")" + "']";
			} else {
				text = "Add a Scrip to " +watchListName.toUpperCase();
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul/li[1]/a[text()='"+scriptName+ "']";
			}
			xpathString = "//h4[text()='" + text + "']//following::input";
		}
		staticWait(600);
		Reporter.log("addScript textfield xpath : "+xpathString, true);
		addScriptTextfield = fluentWaitCodeXpath(xpathString, "Add Script textfield");
		staticWait(600);
		if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
			clearAndSendKey(addScriptTextfield, scriptName, "Add Script textfield");
		} else {
			clearAndSendKey(addScriptTextfield,scriptName, "Add Script textfield");
		}
		addScriptDropDownOption=fluentWaitCodeXpath(dropdownOptionStr, "Script dropdown Option");
		try {
		clickElement(addScriptDropDownOption,scriptName+ "Dropdown option");
		}catch(StaleElementReferenceException e) {
			
			clickElement(dropdownOptionStr,scriptName + "Dropdown option");
		}
		errorList=selectExchange(exchange, step,errorList);
		clickElement("//h4[text()='" + text + "']//following::button[2]", "Add Script button");
		staticWait(500);
		// clickElement("//button[text()='Ok']", "OK button");
		watchListCreateMsg=fetchTextFromElement("//button[text()='Ok']//preceding::p[1]", "watchCreate msg");
	
		okButton = fluentWaitCodeXpath("//button[text()='Ok']", 40, "Ok button");
		if (okButton != null) {
			clickElement(okButton, "Ok button");
		} else {
			if (step.equalsIgnoreCase("addScript")) {
				errorList.add("Trading Sysmbol : " + tradingSymbol);
				errorMsg = scriptName
						+ " script already present. User should not be allowed to add duplicate script.-PASS";
				errorList.add(errorMsg);
				// errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList"));
			} else {
				errorMsg = watchListName
						+ " watchList already present. User should not be allowed to create duplicate watchlist.-PASS";
				errorList.add(errorMsg);
				errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList"));
			}

			driver.navigate().refresh();
			staticWait(50);
			driver.navigate().refresh();
		}
		
		return errorList;
	}
	public List<String> selectExchange(String exchange, String step,List<String> errorList) {
		Reporter.log("=====> selectExchange <=====", true);
		Reporter.log("exchange : "+exchange, true);
		staticWait(500);
		if(exchange.equalsIgnoreCase("NSE")||exchange.equalsIgnoreCase("BSE")) {
		nseRadioButton = fluentWaitCodeXpathCheckElement(driver,
				"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[1]/div/input", 20,
				"NSE Radio button");
		bseRadioButton = fluentWaitCodeXpathCheckElement(driver,
				"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[2]/div/input", 20,
				"BSE Radio button");
		if (step.equalsIgnoreCase("AddScript")||step.equalsIgnoreCase("duplicateAddScript")) {
			nseRadioButton = fluentWaitCodeXpathCheckElement(driver,
					"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[1]/div/input", 20,
					"NSE Radio button");
			bseRadioButton = fluentWaitCodeXpathCheckElement(driver,
					"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[2]/div/input", 20,
					"BSE Radio button");
		}
		if (nseRadioButton != null || bseRadioButton != null) {
			if (exchange.equalsIgnoreCase("NSE"))
				clickElement(nseRadioButton, "NSE Radio button");
			else if (exchange.equalsIgnoreCase("BSE"))
				clickElement(bseRadioButton, "BSE Radio button");
		} else if (nseRadioButton != null) {
			errorList.add("NSE radio button not present");
			clickElement(bseRadioButton, "BSE Radio button");
		} else if (bseRadioButton != null) {
			errorList.add("BSE radio button not present");
			clickElement(nseRadioButton, "NSE Radio button");
		}
		}
			return errorList;
	}
	
	public void deleteWatchList(String watchListName,String exchange) {

		if (exchange.equalsIgnoreCase("NSE") ||exchange.equalsIgnoreCase("BSE")|| exchange.equalsIgnoreCase("CDS")||exchange.equalsIgnoreCase("FNO")) {
			watchListtabNotFound(watchListName.trim(), "abc",exchange);
		staticWait(500);
		errorList.add("Delete watchList");
		errorList.add("WatchList Name : " +watchListName);
		String deleteButtonxpath = "//span[text()='New Watchlist']//following::li//a[text()='"+ watchListName.trim() + "']//following::ul//li//span[@class='fa fa-trash-o ng-scope']";
		String defaultWatchxpath = "//span[text()='New Watchlist']//following::li//a[text()='"+watchListName.trim() + "']//following::ul//li//span[@class='fa fa-star']";
		defaultWatchList = fluentWaitCodeXpath(defaultWatchxpath, 30, "Default WatchList tab");
		if (defaultWatchList != null) {
			errorMsg = "Error msg : " + watchListName.trim() + " watchList is default watchList you cannot delete"+ "-PASS";
			errorList.add(errorMsg);
		} else {
			try {

				iconButton(deleteButtonxpath, "Delete button");
			} catch (TimeoutException e) {

				if (defaultWatchxpath == null)
					deleteOption = "not present";

			}
			staticWait(1000);
			/*deleteWatchListButton=fluentWaitCodeXpath("//button[text()='Delete']",200,"delete watchList button");
			clickElement(deleteWatchListButton, "delete button");*/
			
			checkElementPresentOrNotThenClick("//button[text()='Delete']", "delete watchList button", 200);
			staticWait(2000);
			/*okButtonDelete=fluentWaitCodeXpath("//button[text()='Ok']", 200,  "Ok button");
			clickElement(okButtonDelete, "Ok button");*/
			
			checkElementPresentOrNotThenClick("//button[text()='Ok']", "Ok button", 200);
			errorList.add(watchListName+ " watchList deleted sccessfully....." + "-Check");
		}

		errorList.add(ScreenshortProvider.captureScreen(driver, "AFterDeleteWatchList"));
		}

	
	}
	
	
	
	public void selectTradingSymbol(String tradingSymbol) {
		Reporter.log("======== selectTradingSymbol ========", true);
		String noOfScript=fetchTextFromElement("//a[text()='Add Scrip']//preceding::span[1]//strong", "Script name");
		
		String [] scriptArray=help.separater(noOfScript, " ");
		scriptCount=Integer.valueOf(scriptArray[0]);
		for (int i = 2; i < scriptCount+2; i++) {
			scriptNameLabel = fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[" + i+ "]/div[1]/div[1]/a","Script Name");
			scriptName = fetchTextFromElement(scriptNameLabel);
			if (scriptName.contains(tradingSymbol)) {
				Reporter.log("Script : " +tradingSymbol);
				
				tradingSysmbol = "TradingSysmbol : " +tradingSymbol;

				// errorList.add("TradingSysmbol : "+scriptNameArray[scriptNameArray.length-1]);

				String scriptBox = "//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[" +i+ "]/div[1]/div[1]/div[1]/input";

				scriptCheckBox = fluentWaitCodeXpath(scriptBox, "Script checkBox");
				if (scriptCheckBox == null) {
					scriptBox = "//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[" +i+ "]/div[1]/div[1]/div[2]/input";
					scriptCheckBox = fluentWaitCodeXpath(scriptBox, "Script checkBox");
				}
				clickElement(scriptCheckBox, "ScriptCheckBox");
				break;
			}

		}
	}
	
	public boolean checkDeletedScriptPresentOrNot(String tradingSymbol) {
		boolean tradingSymbolPresentFlag=false;
		Reporter.log("==== checkDeletedScriptPresentOrNot  =====", true);
		List<String> tradingSymbolStrList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//a", "tradingSymbol");
		for(String tradingSymbolStr:tradingSymbolStrList) {
			if(tradingSymbolStr.contains(tradingSymbol)) {
				tradingSymbolPresentFlag=true;
				break;
			}
			
		}
		return tradingSymbolPresentFlag;
	}
	
	public void deleteScriptButton(WebElement deleteScriptButton,String [] tradingSymbolArray) {
		if(tradingSymbolArray.length==1) {
			if(fetchTextFromElement(deleteScripButton).equalsIgnoreCase("Delete Scrip")) 
				deleteScriptArray.add("Delete script button is present-PASS");
			else
				deleteScriptArray.add("Delete script button is not present-FAIL");
		}else {
			if(fetchTextFromElement(deleteScripButton).equalsIgnoreCase("Delete Scrips")) 
				deleteScriptArray.add("Delete scrip button name now change to delete scrips-PASS");
			else
				deleteScriptArray.add("Delete scrip button name does not change to delete scrips-FAIL");
		}
			
	}
	
	public void deleteScript(String watchListName,String [] tradingSymbolArray,String step,String exchange) {
		Reporter.log("===== deleteScript =====", true);
		
		watchListtabNotFound(watchListName, step, exchange);
		int c=0;
		for(int i=0;i<tradingSymbolArray.length;i++) {
			c++;
			selectTradingSymbol(tradingSymbolArray[i]);
			deleteScriptArray.add("Trading Symbol : "+tradingSymbolArray[i]);
		}
		deleteScripButton=fluentWaitCodeXpath("//a[text()='Add Scrip']//following::a[1]", "delete Script button");
		
		
		deleteScriptButton(deleteScripButton, tradingSymbolArray);
		
		deleteScriptArray.add(ScreenshortProvider.captureScreen(driver, "deletedScript"));
		clickElement(deleteScripButton, "Delete script button");
		
		popupDeleteButton = fluentWaitCodeXpath("//button[text()='Delete']", "popup Delete Button");
		clickElement(popupDeleteButton, "popup Delete Button");
		deleleokButton = fluentWaitCodeXpath("//button[text()='Ok']", "Ok button");
		clickElement(deleleokButton, "Ok button");
		watchListtabNotFound(watchListName, step, exchange);
		for(String tradeSymbolStr:tradingSymbolArray) {
			if(checkDeletedScriptPresentOrNot(tradeSymbolStr))
			{
				Reporter.log(tradeSymbolStr+"is present afer delete-FAIL", true);
				deleteScriptArray.add(tradeSymbolStr+" is present after delete-FAIL");
				
			}else {
				Reporter.log(tradeSymbolStr+"is not present afer delete-PASS", true);
				deleteScriptArray.add(tradeSymbolStr+" is not present after delete-PASS");
			}
			
		}
	}
	
	public List<String> checkAddScripButton(String watchListName,String exchange,String scriptName,String step) {
		List<String> checkAddScriptList=new ArrayList<String>();
		Reporter.log("====> checkAddScripButton <====", true);
		
	if (step.equalsIgnoreCase("Create")) {
		if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
			dropdownOptionStr = "//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/div/ul[1]/li/a[text()='"+scriptName + " (" + exchange + ")" + "']";
			Reporter.log("DropDown Xpath : "+dropdownOptionStr, true);
		} else {
			dropdownOptionStr = "//*[@id='watchlist']/div/div/div[2]/div[2]/div/div/div/ul/li/a[text()='"+scriptName + "']";

		}

		
			text = "Add a Scrip to " +watchListName;
			xpathString = "//h4[text()='" + text + "']//following::input[2]";

		} else if (step.equalsIgnoreCase("AddScript")) {

			if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
				text = "Add a Scrip to ";
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul[1]/li/a[text()='"+scriptName + " (" +exchange+ ")" + "']";
			} else {
				text = "Add a Scrip to " +watchListName.toUpperCase();
				
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul/li[1]/a[text()='"+scriptName+ "']";
			}
			xpathString = "//h4[text()='" + text + "']//following::input";
		}
		//checking addScript button is enable or not when you enter leave script textfield blank
		if(watchListName.equalsIgnoreCase("NoWat")) {
			addScriptButton=fluentWaitCodeXpath("//h4[text()='" + text + "']//following::button[2]", "Add Script button");
			if(addScriptButton!=null) {
				if(!addScriptButton.isSelected()) 
					checkAddScriptList.add("initially in add scrip dialogue box add scrip button is disabled-PASS");
				else
					checkAddScriptList.add("initially in add scrip dialogue box add scrip button is enable-FAIL");
			}
			}
		
		//xpathString = "//h4[text()='" + text + "']//following::input";
		staticWait(600);
		Reporter.log(" addScriptTextfield xpath: "+xpathString, true);
		addScriptTextfield = fluentWaitCodeXpath(xpathString, "Add Script textfield");
		staticWait(600);
		if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
			clearAndSendKey(addScriptTextfield, scriptName, "Add Script textfield");
		} else {
			clearAndSendKey(addScriptTextfield,scriptName, "Add Script textfield");
		}
		addScriptDropDownOption=fluentWaitCodeXpath(dropdownOptionStr, "Script dropdown Option");
		try {
		clickElement(addScriptDropDownOption,scriptName+ "Dropdown option");
		}catch(StaleElementReferenceException e) {
			
			clickElement(dropdownOptionStr,scriptName + "Dropdown option");
		}
		errorList=selectExchange(exchange, step,errorList);
		if(watchListName.equalsIgnoreCase("NoWat")) {
			clearTextfield(addScriptTextfield, "Add script textfield");
		addScriptButton=fluentWaitCodeXpath("//h4[text()='" + text + "']//following::button[2]", "Add Script button");
		checkAddScriptList.add("After clear addScriptTextfield");
		if(addScriptButton!=null) {
			if(!addScriptButton.isSelected()) 
				checkAddScriptList.add("In add Script dialogue box,add script button is disable beacause scrip textfield is blank-PASS");
			else
				checkAddScriptList.add("In add Script dialogue box, scrip textfield is blank still add script button is enable-FAIL");
		}
		}else
		clickElement("//h4[text()='" + text + "']//following::button[2]", "Add Script button");
		
		return checkAddScriptList;
	}
	
	public String checkDuplicateInWatchList(String watchListName,String step,String exchange) {
		watchListtabNotFound(watchListName, step, exchange);
		List<String> scriptNameTextList=new ArrayList<String>();
		List<String> scripNameList=multipleElementsTextProvider("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div/div/div/div[1]/a", "Script name");
		for(String scrip:scripNameList) {
			scriptNameTextList.add(help.removeHtmlReporter(scrip));
		}
		String result;
		if(help.checkDuplicatePresentOrNot(scriptNameTextList)) {
			result=watchListName+" is contain duplicate trading symbol-FAIL";
		}else {
			result=watchListName+" is does not contain duplicate trading symbol-PASS";
		}
		
		return result;
		
	}
	
	public void redirectToWatchListModule(boolean pageRefershOrNot) {
		Reporter.log("====> redirectToWatchListModule <====", true);
		staticWait(1000);
		closePopupButton = fluentWaitCodeXpath("//a[@class='ed-icon i-close lg']",50, "Close popup button");
		if(closePopupButton!=null)
		clickElement(closePopupButton,  "Close popup button");
		try {
		hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Watchlist']");
		}catch(ElementNotInteractableException e) {
			help.pageRefresh();
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Watchlist']");
		}
		WebElement watchlistTitle=fluentWaitCodeXpath("//h3[text()='Watchlist']",30, "WatchList title");
		if(watchlistTitle==null) {
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Watchlist']");
		}
		if(pageRefershOrNot)
			pageRefresh();
	}

	public String segmentRandomExchange(String segment) {
		String exchange="MCX";
		if(segment.equalsIgnoreCase("Equity"))
			exchange="NSE";
		return exchange;
	}
	
	public void clickNewWatchListButton() {
		Reporter.log("===> clickNewWatchListButton <===", true);
		boolean createWatchPopupFlag=false;
		int newWatchListTabCount=0;
		do {
			newWatchListTabCount++;
			newWatchListTab=fluentWaitCodeXpath("//span[text()='New Watchlist']", "New watchList tab");	
			clickUsingAction(newWatchListTab, "New watchList tab");
			staticWait(3000);
			createWatchListPopupTitle=fluentWaitCodeXpath("//h4[text()='Create a Watchlist']",20, "create watchList popup title");
			if(createWatchListPopupTitle.isDisplayed())
				createWatchPopupFlag=true;
			Reporter.log("click on newWatchList createWatchPopupFlag : "+createWatchPopupFlag, true);
		}while(createWatchPopupFlag==false && newWatchListTabCount<10);
	}
	
	public void checkCureentPageWatchListOrNot() {
		watchListTitle=fluentWaitCodeXpath("//h3[text()='Watchlist']",30, "watchList title");
		if(watchListTitle==null) {
			redirectToWatchListModule(false);
		}
	}
	
	public void checkDuplicate(List<String> errorMsgList,String step) {
		Reporter.log("=====> checkDuplicate <=====", true);
		okButton = fluentWaitCodeXpath("//button[text()='Ok']", 40, "Ok button");
		if (okButton != null) {
			popupErrorMsg=fluentWaitCodeXpath("//button[text()='Ok']//preceding::p[1]",30,"Popup error msg");
		
		if(step.equalsIgnoreCase("duplicateAddScript")) 
			errorMsgList.add(help.commpareTwoString(help.removeHtmlReporter(fetchTextFromElement(popupErrorMsg)).trim(), "You are trying to add a symbol which already exists in your watchlist."));
		else if(step.equalsIgnoreCase("createDuplicate"))
			errorMsgList.add(help.commpareTwoString(help.removeHtmlReporter(fetchTextFromElement(popupErrorMsg)).trim(), "Watchlist Name Already Exists!"));
		
		errorMsgList.add(ScreenshortProvider.captureScreen(driver, step));
		clickElement(okButton, "Ok button");
		
		}
		
	}
	
	
	public boolean checkAddScriptDialogBox(String exchange,String watchListName,String scriptName) {
		Reporter.log("====> checkAddScriptDiaglogBox <====", true);
		staticWait(500);
		boolean addScriptDialogFlag=false;
		if (exchange.equalsIgnoreCase("NCDEX") || exchange.equalsIgnoreCase("MCX")) {
			text = "Add a Scrip to ";
			dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul[1]/li/a[text()='"+scriptName+ " (" + exchange+ ")" + "']";
		} else {
			text = "Add a Scrip to " + watchListName.toUpperCase();
			dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul/li[1]/a[text()='"+ scriptName+ "']";
		}
		xpathString = "//h4[text()='" + text + "']//following::input";
		addScriptTitle=fluentWaitCodeXpath(xpathString, "add Script title");
		if(addScriptTitle!=null) {
			if(addScriptTitle.isDisplayed())
				addScriptDialogFlag=true;
		}
		return addScriptDialogFlag;
	}
	
	public boolean checkScriptAddOrNot(String tradeSymbol,List<String> detailList) {
		Reporter.log("=====> checkScriptAddOrNot <=====", true);
		boolean scriptPresentFlg=false;
		Reporter.log("TradeSymbol : "+tradeSymbol, true);
		
		detailList.add(ScreenshortProvider.captureScreen(driver,tradeSymbol+"verify"));
		
		
		List<String> scriptNameList=multipleElementsTextProvider("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[1]/a", "Script name");
		
		for(String scriptNameStr:scriptNameList)
		{
			if(scriptNameStr.contains(tradeSymbol)) {
				scriptPresentFlg=true;
				break;
			}
		}
		
		if(scriptPresentFlg)
			detailList.add(tradeSymbol+" is present.");
		else
			detailList.add(tradeSymbol+" is not present.");
		return scriptPresentFlg;
	}
}
