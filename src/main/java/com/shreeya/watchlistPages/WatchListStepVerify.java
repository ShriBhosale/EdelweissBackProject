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
	public static Map<String,List<String>> verfiyMap;
	private static int count=0;
	List<String> scriptList;
	List<String> exchangeList;
	List<String> detailList;
	
	public WatchListStepVerify() {
		
	}
	
	public WatchListStepVerify(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		verfiyMap=new HashMap<String,List<String>>();
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
			verifyDuplicateScriptWatchList(errorList,count,model,"DuplicateScriptWatchList");
			break;
		case 3:
			verifyDuplicateScriptWatchList(errorList,count,model,"DeleteScriptWatchList");
			break;
		default:
			break;
		}
	}

	private void verifyDuplicateScriptWatchList(ArrayList<String> errorList,int count,WatchListModel model,String keyName) {
		
		detailList=new ArrayList<String>();
		Reporter.log("verifyDuplicateScriptWatchList", true);
		for(String errorMsg:errorList) {
			String [] errorMsgArray=errorMsg.split("-");
			if(errorMsgArray[1].equalsIgnoreCase(model.getWatchListName())) {
				
				detailList.add(errorMsgArray[0]);
				Reporter.log(errorMsgArray[0], true);
			}
			
		
		}
		verfiyMap.put(keyName+count, detailList);
	}

	public String watchListCreateProve(WatchListModel model) {
		String watchListName=model.getWatchListName()+" not create";
		String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		WebElement watchList=fluentWaitCodeXpath(createdWatchlistTab, "Create Watch list name");
		if(watchList!=null) 
			watchListName=fetchTextFromElement(watchList);
		return "WatchList Name : "+watchListName;
	}
	
	private Map<String,List<String>> verifyCreateAdd(WatchListModel model,int count) {
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
	
	
	public void predefineWatchListVerify(WatchListModel model,String verifyNo,List<String> predefindWatchListDetailList) {
		switch(verifyNo) {
		case "4":
			simpleClickPredefineWatchList(model,predefindWatchListDetailList);
			break;
		case "5":
			tradingWithPredefineWatchList(model, predefindWatchListDetailList);
			break;
		}
	}

	private void simpleClickPredefineWatchList(WatchListModel model,List<String> predefindWatchListDetailList) {
		verfiyMap.put("Click on "+model.getWatchListName(), predefindWatchListDetailList);
		
	}
	private void tradingWithPredefineWatchList(WatchListModel model,List<String> predefindWatchListDetailList) {
		verfiyMap.put("Trade With "+model.getWatchListName(), predefindWatchListDetailList);
	}
}
