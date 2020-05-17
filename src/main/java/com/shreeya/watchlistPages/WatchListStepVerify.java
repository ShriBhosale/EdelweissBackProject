package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListStepVerify extends SeleniumCoder {
	WebDriver driver;
	public static Map<String,List<String>> verfiyMap;
	private static int count=0;
	List<String> scriptList;
	List<String> exchangeList;
	List<String> detailList;
	public static List<String> createAddDetailList;
	
	WatchListCommon watchListCommon;
	Help help;
	String [] scriptNames;
	String [] verifyScriptNames;
	String [] exchangeArray;
	
	public WatchListStepVerify() {
		
	}
	
	public WatchListStepVerify(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		verfiyMap=new HashMap<String,List<String>>();
		scriptList=new ArrayList<String>();
		exchangeList=new ArrayList<String>();
		watchListCommon=new WatchListCommon(driver);
		createAddDetailList=new ArrayList<String>();
		
		help=new Help();
	}
	public void verfitySteps(WatchListModel model,String verifyNo,ArrayList<String> errorList) {
		Reporter.log(model.toString(), true);
		count++;
		scriptNames=WatchListPage.scriptArray;
		exchangeArray=WatchListPage.exchangeArray;
		int verifyNumber=Integer.valueOf(verifyNo);
		switch (verifyNumber) {
		case 0:
			mergeAddScriptCreate();
		break;
		case 1:
			verifyCreateAdd(model,count,errorList);
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

	private void mergeAddScriptCreate() {
		verfiyMap.put("CreateAddScript_1", createAddDetailList);
		
	}

	private void verifyDuplicateScriptWatchList(ArrayList<String> errorList,int count,WatchListModel model,String keyName) {
		Reporter.log(model.toString(), true);
		detailList=new ArrayList<String>();
		Reporter.log("verifyDuplicateScriptWatchList", true);
		int addStart=0;
		
		
		for(int i=addStart; i<errorList.size();i++) {
			Reporter.log("errorMsg : "+errorList.get(i),true);
			String [] errorMsgArray=errorList.get(i).split("-");
			if(errorList.get(i).contains("-")) {
			if(errorMsgArray[1].equalsIgnoreCase(model.getWatchListName())) {
				
				detailList.add(errorMsgArray[0]);
				Reporter.log(errorMsgArray[0], true);
				
			}
			}else {
				detailList.add(errorList.get(i));
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
	
	private void verifyCreateAdd(WatchListModel model,int count,List<String> inputList) {
		verifyScriptNames=help.commaSeparater(model.getVerifyScript());
		detailList=new ArrayList<String>();
		Reporter.log("verifyCreateAdd : count =====> "+count, true);
		if(count==1) {
		for(String input:inputList) {
			detailList.add(input);
		}
		}
		String watchName=watchListCreateProve(model);
		Reporter.log("WAtchListStepVerify : WatchList Name : "+watchName, true);
		watchListCommon.pageVerify(model,"Verify");
		Reporter.log("VerifyCodeMethod::inside if else ", true);
		scriptList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::a","Script Names");
		scriptList=elementsTextFilter(scriptList);
		exchangeList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::span//small","Exchanges");
		exchangeList=elementsTextFilter(exchangeList);
		detailList.add(watchName);
		
		for(int i=0; i<scriptList.size();i++) {
			if(i>0) {
				detailList.add("add Script");
			}
			detailList.add("ScriptName : "+scriptNames[i]);
			if(scriptList.get(i).equalsIgnoreCase(verifyScriptNames[i])) {
				detailList.add("TradingSysmbol : "+scriptList.get(i)+"-PASS");
			}else {
				detailList.add("TradingSysmbol : "+scriptList.get(i)+"-FAIL");
			}
			if(exchangeList.get(i).equalsIgnoreCase(exchangeArray[i])) {
				detailList.add("Exchange : "+exchangeList.get(i)+"-PASS");
			}else {
				detailList.add("Exchange : "+exchangeList.get(i)+"-FAIL");
			}
		}
		
		String screenshot=ScreenshortProvider.captureScreen(driver, "WatchList");
		detailList.add(screenshot);
		
		for(String detail:detailList) {
			createAddDetailList.add(detail);
		}
		/* verfiyMap.put("CreateAddScript_"+count, detailList); */
		
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
