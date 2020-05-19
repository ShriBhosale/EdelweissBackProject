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
	
	private static boolean createWatchList=true;
	
	public WatchListStepVerify() {
		
	}
	
	public WatchListStepVerify(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		//verfiyMap=new HashMap<String,List<String>>();
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
			tradingWithWatchlist(errorList,count,model);
			break;
		case 3:
			verifyDuplicateScriptWatchList(errorList,count,model);
			break;
		case 4:
			verifyDeleteScriptWatchList(errorList,count,model);
			break;
		
		default:
			break;
		}
	}
	
	private void tradingWithWatchlist(ArrayList<String> errorList, int count2, WatchListModel model) {
		// TODO Auto-generated method stub
		verfiyMap.put("TradingWithWatchList "+model.getWatchListName()+"_"+count, errorList);
		errorList=new ArrayList<String>();
	}

	public void setVerifyMap(Map<String,List<String>> inputMap)
	{
		verfiyMap=inputMap;
	}
	
	public Map<String,List<String>>  verifyMapGiver() {
		Map<String,List<String>> outputMap=verfiyMap;
		return outputMap;
	}

	private void verifyDeleteScriptWatchList(ArrayList<String> errorList, int count2, WatchListModel model) {
		Reporter.log("========>> verifyDeleteScriptWatchList <<===========", true);
		boolean scriptDelete=true;
		detailList=new ArrayList<String>();
		watchListCommon.pageVerify(model,"Verify");
		scriptList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::a","Script Names");
		scriptList=elementsTextFilter(scriptList);
		for(String response:errorList) {
			Reporter.log(response, true);
			if(response.contains("TradingSysmbol")) {
				String [] array=help.separater(response,":");
				for(String script:scriptList) {
					if(script.trim().contains(array[1].trim())) {
						detailList.add(response+"-FAIL");
						scriptDelete=false;
						break;
					}
				}
				if(scriptDelete)
					detailList.add(response+"-PASS");
				
			}else if(response.contains("Check")) {
				String [] array=help.separater(response, "-");
				String [] array1=help.separater(response, " ");
				String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+array1[0]+"']";
				WebElement watchList=fluentWaitCodeXpath(createdWatchlistTab, "Create Watch list name");
				if(watchList==null)
					detailList.add(array[0]+"-PASS");
				else
					detailList.add(array[0]+"-FAIL");
			}else {
				detailList.add(response);
			}
			
		}
		verfiyMap.put("DeleteScriptWatchlist_"+count, detailList);
	}

	private void mergeAddScriptCreate() {
		verfiyMap.put("CreateAddScript_1", createAddDetailList);
		
	}

	private void verifyDuplicateScriptWatchList(ArrayList<String> errorList,int count,WatchListModel model) {
		Reporter.log("=============>> verifyDuplicateScriptWatchList <<===============",true);
		Reporter.log(model.toString(), true);
		detailList=new ArrayList<String>();
		Reporter.log("verifyDuplicateScriptWatchList", true);
		int addStart=0;
		
		
		for(int i=addStart; i<errorList.size();i++) {
			if(errorList.get(i).trim().equalsIgnoreCase("Multiple Watchlist")||errorList.get(i).trim().equalsIgnoreCase("Single Watchlist"))
			{
				continue;
			}
			Reporter.log("errorMsg : "+errorList.get(i),true);
			detailList.add(errorList.get(i));
			Reporter.log(errorList.get(i),true);
		}
		
		verfiyMap.put("DuplicateScriptWatchList_"+count, detailList);
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
		Reporter.log("===============>> verifyCreateAdd <<==================",true);
		count--;
		verifyScriptNames=help.commaSeparater(model.getVerifyScript());
		detailList=new ArrayList<String>();
		Reporter.log("verifyCreateAdd : count =====> "+count, true);
		if(createWatchList) {
		for(String input:inputList) {
			detailList.add(input);
		}
		createWatchList=false;
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
				detailList.add(watchName);
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
			Reporter.log(exchangeList.get(i), true);
		}
		
		String screenshot=ScreenshortProvider.captureScreen(driver, "WatchList");
		detailList.add(screenshot);
		
		for(String detail:detailList) {
			createAddDetailList.add(detail);
		}
		/* verfiyMap.put("CreateAddScript_"+count, detailList); */
		
	}
	
	
	public void predefineWatchListVerify(WatchListModel model,String verifyNo,List<String> predefindWatchListDetailList) {
		count++;
		switch(verifyNo) {
		case "5":
			simpleClickPredefineWatchList(model,predefindWatchListDetailList);
			break;
		case "6":
			tradingWithPredefineWatchList(model, predefindWatchListDetailList);
			break;
		}
	}

	private void simpleClickPredefineWatchList(WatchListModel model,List<String> predefindWatchListDetailList) {
		verfiyMap.put("Click on "+model.getWatchListName()+"_"+count, predefindWatchListDetailList);
		
	}
	private void tradingWithPredefineWatchList(WatchListModel model,List<String> predefindWatchListDetailList) {
		verfiyMap.put("Trade With "+model.getWatchListName()+"_"+count, predefindWatchListDetailList);
	}
	
	
}