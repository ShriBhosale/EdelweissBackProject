package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.experiment.Report;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListTestcase extends SeleniumCoder{

	WebDriver driver;
	Help help;
	WatchListCommon common;
	List<String> detailList;
	private List<String> deviceWatchListNameList;
	private List<String> predefineWatchListNameList;
	private List<String> userDefineWatchListNameList;
	private WebElement noOfScriptLabel;
	private String noOfScripStr;
	private Map<String, String> noScriptMap;
	private String scriptCount;
	WatchListExtraScenario extraScenario;
	ConfigReader config;
	private String oppExchangeScriptName;
	private String oppExchange;
	private String oppExchangeTradingSymbol;
	
	public WatchListTestcase(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		common=new WatchListCommon(driver);
		extraScenario=new WatchListExtraScenario(driver);
		detailList=new ArrayList<String>();
		config=new ConfigReader();
	}
	
	public void checkWatchListPresence(String segment) {
		Reporter.log("====> checkWatchListPresence <====", true);
		detailList.add("@@> Verify WatchList tab <@@");
		deviceWatchListNameList=new ArrayList<String>();
		predefineWatchListNameList=new ArrayList<String>();
		userDefineWatchListNameList=new ArrayList<String>();
		List<String> watchListNameList=multipleElementsTextProvider("//a[@class='ng-binding']", "WatchList Name");
		watchListNameList=help.removeDuplicateValueArrayList(help.removeHtmlTags(watchListNameList));
		for(String watchListName:watchListNameList) {
			if((watchListName.equalsIgnoreCase("Nifty 50")||watchListName.equalsIgnoreCase("Sensex"))&&(segment.equalsIgnoreCase("Equity"))) {
				predefineWatchListNameList.add(watchListName);
			}else if((watchListName.equalsIgnoreCase("My Positions")||watchListName.equalsIgnoreCase("My Holdings"))&&(segment.equalsIgnoreCase("Equity"))) {
				deviceWatchListNameList.add(watchListName);
			}else {
				userDefineWatchListNameList.add(watchListName);
			}
		}
		detailList.add("<u>User created watchlist</u>");
		for(String userDefineStr:userDefineWatchListNameList)
			detailList.add(userDefineStr);
		if(segment.equalsIgnoreCase("Equity")) {
		detailList.add("<u>Device Watchlist</u>");
		for(String deviceWatchList:deviceWatchListNameList)
			detailList.add(deviceWatchList);
		detailList.add("<u>Predefined Watchlist</u>");
		for(String predefineWatchList: predefineWatchListNameList)
			detailList.add(predefineWatchList);
		}
	}
	
	public void checkAllScriptHaveTradeButton(String segment,String watchListName) {
		Reporter.log("====> checkAllScriptHaveTradeButton <====== ", true);
		detailList.add("@@> Verify Trade button is available beside every scrip <@@");
		//list which contain trade button which not clickable
	
		List<String> scriptList=new ArrayList<String>();
		boolean tradingButtonClickable=false;
		List<WebElement> tradingButtonList=multipleElementLocator("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[11]/a", "Trade button");
		for(int i=0;i<tradingButtonList.size();i++) {
			tradingButtonClickable=checkClickableOrNot(tradingButtonList.get(i), "Trading button");
			if(tradingButtonClickable==false)
			{
				String scriptHtmlTag=fetchTextFromElement("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+2+"]/div[1]/div[1]/a", "Scrip Label");
				scriptList.add(help.removeHtmlReporter(scriptHtmlTag));
			}
		}
		
		if(scriptList.size()>0) {
			for(String scrip:scriptList)
				detailList.add(scrip+" haven trade button is not clickable-FAIL");
		}else {
			detailList.add(watchListName+" having clickable trade button.-PASS");
		}
	}
	
	public  void noScriptInWatchList(List<String> watchListNameList,String segment) {
		Reporter.log("====> noScriptInWatchList <====", true);
		
		List<WebElement> scriptList;
		for(String watchListNameStr:watchListNameList) {
			common.watchListtabNotFound(watchListNameStr, "Checking",common.segmentRandomExchange(segment));
			staticWait(3000);
			noOfScripStr=fetchTextFromElement("//span[@class='count ng-scope']",300,"No of script label");
			scriptList=multipleElementLocator("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[1]/a", "Script list");
			scriptCount=String.valueOf(scriptList.size());
			Reporter.log("Script count : "+scriptCount, true);
			if(!scriptCount.equalsIgnoreCase("0"))
			detailList.add(watchListNameStr+" : "+help.testStrContainInAppliStr(help.removeHtmlReporter(noOfScripStr), scriptCount));
			else {
				detailList.add(watchListNameStr+" : "+help.commpareTwoString(help.removeHtmlReporter(noOfScripStr), "Your WatchList doesnt have any scrips"));
			}
			detailList.add(ScreenshortProvider.captureScreen(driver, watchListNameStr));
		}
		
		
	}
	
	public void checkNoOfScripInEveryWatchList(String segment) {
		Reporter.log("====> checkNoOfScripInEveryWatchList <====", true);
		detailList.add("@@> Verify Scrip number is displaying for every watchlist <@@");
		if(segment.equalsIgnoreCase("Equity")) {
			noScriptInWatchList(deviceWatchListNameList,segment);
			noScriptInWatchList(predefineWatchListNameList, segment);
		}else {
			
		}
		noScriptInWatchList(userDefineWatchListNameList, segment);
	}
	
	public void inputGiver(String segment) {
		Reporter.log("=====> inputGiver <=====", true);
		if(segment.equalsIgnoreCase("Equity")) {
			oppExchangeScriptName=config.configReaderWL("COScriptForEquityId");
			oppExchange=config.configReaderWL("COExchangeForEquityId");
			oppExchangeTradingSymbol=config.configReaderWL("COTradingSymbolForEquityId");
		}else {
			oppExchangeScriptName=config.configReaderWL("EQScriptForCommodityId");
			oppExchange=config.configReaderWL("EQExchangeForCommodityId");
			oppExchangeTradingSymbol=config.configReaderWL("EQTradingSymbolForCommodityId");
			
		}
	}
	
	public void createWatchList(String watchListName,String scriptName,String exchange,String tradingSymbol) {
		Reporter.log("=====> createWatchList <=====", true);
		common.clickNewWatchListButton();
		extraScenario.enterWatchListTextfield(watchListName);
		clickElement("//button[text()='Create']", "Create buttons");
		common.addScript(watchListName, scriptName, tradingSymbol, exchange, "Create", detailList);
		extraScenario.checkWatchListPresentOrNot(watchListName, "create");
		boolean flag=common.checkScriptAddOrNot(tradingSymbol, detailList);
		if(flag && watchListName.contains("CO")) 
			detailList.add("User can add commodity script in watchList still it is equity login-PASS");
		else if(flag && watchListName.contains("EQ"))
			detailList.add("User can add equity script in watchList still it is commodity login-PASS");
		else if(flag==false && watchListName.contains("CO"))
			detailList.add("User can not add commodity script in watchlist beacasue it is login with equity-FAIL");
		else
			detailList.add("User can not add equity script in watchlist beacasue it is login with commodity-FAIL");
	}
	
	public void checkCanSarchOppSegmentScript(String segment) {
		Reporter.log("=====> checkCanSarchOppSegmentScript <=====", true);
		if(segment.equalsIgnoreCase("Equity")) {
			detailList.add("@@> Verify When user is logged in with Eq id <@@");
			createWatchList("COWatchList", config.configReaderWL("ScriptNameCO"), config.configReaderWL("ExchangeCO"), config.configReaderWL("tradingSymbolCO"));
		}else {
			detailList.add("@@> Verify When user is logged in with Comm id <@@");
			createWatchList("EQWatchList", config.configReaderWL("ScriptNameEQ"), config.configReaderWL("ExchangeEQ"), config.configReaderWL("tradingSymbolEQ"));
		}
	}
	
	public ExtendReporter watchListTestcaseExecute(String segment,ExtendReporter repoter) {
		Reporter.log("<b>======@@> watchListTestcaseExecute <@@===========<</b>", true);
		config.watchListConfig();
		staticWait(1000);
		common.redirectToWatchListModule(true);
		checkWatchListPresence(segment);
		
		  checkAllScriptHaveTradeButton(segment, "MarketWatch");
		  checkNoOfScripInEveryWatchList(segment);
		 
		checkCanSarchOppSegmentScript(segment);
		repoter.watchListTestcaseRport(detailList);
		
		return repoter;
	}
}
