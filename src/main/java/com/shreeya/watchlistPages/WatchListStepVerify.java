package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListStepVerify extends SeleniumCoder {
	WebDriver driver;
	private static Map<String,ArrayList<String>> verfiyMap;
	private static int count=0;
	List<String> scriptList;
	List<String> exchangeList;
	ArrayList<String> detailList;
	
	public WatchListStepVerify() {
		
	}
	
	public WatchListStepVerify(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		verfiyMap=new HashMap<String,ArrayList<String>>();
		scriptList=new ArrayList<String>();
		exchangeList=new ArrayList<String>();
		
	}
	public void verfitySteps(WatchListModel model,String verifyNo,ArrayList<String> errorList) {
		count++;
		int verifyNumber=Integer.valueOf(verifyNo);
		switch (verifyNumber) {
		case 1:
			verifyCreateAdd(model,count);
			break;
		case 2:
			verifyDuplicateScriptWatchList(errorList);
		default:
			break;
		}
	}

	private void verifyDuplicateScriptWatchList(ArrayList<String> errorList) {
		detailList=new ArrayList<String>();
		detailList=errorList;
		verfiyMap.put("DuplicateScriptWatchList", detailList);
		
	}

	public String watchListCreateProve(WatchListModel model) {
		String watchListName=model.getWatchListName()+" not create";
		String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		WebElement watchList=fluentWaitCodeXpath(createdWatchlistTab, "Create Watch list name");
		if(watchList!=null) 
			watchListName=fetchTextFromElement(watchList);
		return "WatchList Name : "+watchListName;
	}
	
	private Map<String,ArrayList<String>> verifyCreateAdd(WatchListModel model,int count) {
		detailList=new ArrayList<String>();
		String watchName=watchListCreateProve(model);
		
		Reporter.log("VerifyCodeMethod::inside if else ", true);
		scriptList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::a","Script Names");
		scriptList=elementsTextFilter(scriptList);
		exchangeList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::span//small","Exchanges");
		exchangeList=elementsTextFilter(exchangeList);
		detailList.add(watchName);
		for(String scriptName:scriptList)
			detailList.add("ScriptName : "+scriptName);
		for(String exchange:exchangeList)
			detailList.add("Exchange : "+exchange);
		String screenshot=ScreenshortProvider.captureScreen(driver, "WatchList");
		detailList.add(screenshot);
		verfiyMap.put("CreateAddScript_"+count, detailList);
		return verfiyMap;
	}
	
	
}
