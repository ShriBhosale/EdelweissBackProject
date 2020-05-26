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
	private static boolean newRowFlag;
	private static int createCount=2;
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
	public void verfitySteps(WatchListModel model,String verifyNo,List<String> errorList,int rowStartCount) {
		Reporter.log(model.toString(), true);
		//this.newRowFlag=newRowFlag;
		count++;
		scriptNames=WatchListPage.scriptArray;
		exchangeArray=WatchListPage.exchangeArray;
		int verifyNumber=Integer.valueOf(verifyNo);
		switch (verifyNumber) {
		case 0:
			mergeAddScriptCreate();
		break;
		case 1:
			verifyCreateAdd(model,count,errorList,rowStartCount);
			break;
		case 2:
			tradingWithWatchlist(errorList,count,model);
			break;
		case 3:
			verifyCodePage(errorList,count,model);
			break;
		case 4:
			verifyDuplicateScriptWatchList(errorList,count,model);
			break;
		case 5:
			verifyDeleteScriptWatchList(errorList,count,model);
			break;
		
		default:
			break;
		}
	}
	
	public void verifyCodePage(List<String> errorList, int count2, WatchListModel model) {
		verfiyMap.put("CodePage_3", errorList);
	}
	
	private void verifyCodePage1(List<String> errorList, int count2, WatchListModel model) {
		detailList=new ArrayList<String>();
		Reporter.log("<========== verifyCodePage ==============>", true);
		String [] scritNameArr=help.separater(errorList.get(0),"&");
		String [] scriptNameArray=help.commaSeparater(model.getFullScriptName());
		String [] tradingSymbolArray=help.commaSeparater(model.getVerifyScript());
		String [] exchangeArray=WatchListPage.exchangeArray;
		if(scritNameArr[0].equalsIgnoreCase(scriptNameArray[1])){
			detailList.add("Script Name : "+scritNameArr[0]+"-PASS");
		}else {
			detailList.add("Script Name : "+scritNameArr[0]+"-FAIL");
		}
		if(errorList.get(1).equalsIgnoreCase(tradingSymbolArray[1])) {
			detailList.add("Trading symbol : "+errorList.get(1)+"-PASS");
		}else {
			detailList.add("Trading symbol : "+errorList.get(1)+"-FAIL");
		}
		if(model.getExchange().contains("CDS")||model.getExchange().contains("NFO")||model.getExchange().contains("FNO")) {
		if(exchangeArray[1].equalsIgnoreCase(watchListCommon.exchangeFilter(errorList.get(2)))){
			detailList.add("Exchange : "+watchListCommon.exchangeFilter(errorList.get(2))+"-PASS");
		}else {
			detailList.add("Exchange : "+watchListCommon.exchangeFilter(errorList.get(2))+"-FAIL");
		}
		}else {
			if(exchangeArray[1].equalsIgnoreCase(errorList.get(2))){
				detailList.add("Exchange : "+errorList.get(2)+"-PASS");
			}else {
				detailList.add("Exchange : "+errorList.get(2)+"-FAIL");
			}
		}
		detailList.add("Ltp : "+errorList.get(3));
		for(int i=4;i<errorList.size();i++) {
			detailList.add(errorList.get(i));
		}
		
		
		verfiyMap.put("CodePage_"+model.getWatchListName()+"_"+count, detailList);
	}

	private void tradingWithWatchlist(List<String> errorList, int count2, WatchListModel model) {
		// TODO Auto-generated method stub
		verfiyMap.put("TradingWithWatchList "+model.getWatchListName()+"_"+count, errorList);
		/* errorList=new ArrayList<String>(); */
	}

	public void setVerifyMap(Map<String,List<String>> inputMap)
	{
		verfiyMap=inputMap;
	}
	
	public Map<String,List<String>>  verifyMapGiver() {
		Map<String,List<String>> outputMap=verfiyMap;
		return outputMap;
	}

	private void verifyDeleteScriptWatchList(List<String> errorList, int count2, WatchListModel model) {
		Reporter.log("========>> verifyDeleteScriptWatchList <<===========", true);
		boolean scriptDelete=true;
		detailList=new ArrayList<String>();
		watchListCommon.pageVerify(model,"Delete");
		scriptList=multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::a","Script Names");
		scriptList=elementsTextFilter(scriptList);
		for(String response:errorList) {
			Reporter.log(response, true);
			/*
			 * if(response.contains("TradingSysmbol")) { String []
			 * array=help.separater(response,":"); for(String script:scriptList) {
			 * if(script.trim().contains(array[1].trim())) {
			 * detailList.add(response+"-FAIL"); scriptDelete=false; break; } }
			 * if(scriptDelete) detailList.add(response+"-PASS");
			 * 
			 * }else
			 */if(response.contains("Check")) {
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

	private void verifyDuplicateScriptWatchList(List<String> errorList,int count,WatchListModel model) {
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
		String [] watchListArray = {"Watchlist"};
		/*
		 * String watchListName=model.getWatchListName()+" not create"; String
		 * createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"
		 * +model.getWatchListName()+"']"; WebElement
		 * watchList=fluentWaitCodeXpath(createdWatchlistTab,20,
		 * "Create Watch list name"); if(watchList!=null) {
		 * watchListName=fetchTextFromElement(watchList);
		 * clickElement(watchList,watchListName+ " WatchList tab"); } else {
		 */
			String watchListName=watchListCommon.pageVerify(model, "watchList Checker");
			/* } */
		
		if(watchListName.contains("<")) {
			watchListArray=watchListName.split("<");
		}else {
			watchListArray[0]= watchListName;
		}
		return "WatchList Name : "+watchListArray[0];
	}
	
	private void verifyCreateAdd(WatchListModel model,int count,List<String> inputList,int rowStartCount) {
		Reporter.log("===============>> verifyCreateAdd <<==================",true);
		count--;
		//createCount=2;
		createAddDetailList=new ArrayList<String>();
		verifyScriptNames=help.commaSeparater(model.getVerifyScript());
		detailList=new ArrayList<String>();
		Reporter.log("verifyCreateAdd : count =====> "+count, true);
		Reporter.log("createCount=================> "+createCount+"\nrowStartCount===============> "+rowStartCount, true);
		if(rowStartCount==2) {
			
		for(String input:inputList) {
			detailList.add(input);
			
		}
		createAddDetailList=new ArrayList<String>();
		rowStartCount++;
		}
		String watchName=watchListCreateProve(model);
		Reporter.log("WAtchListStepVerify : WatchList Name : "+watchName, true);
		//watchListCommon.pageVerify(model,"Verify");
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
			detailList.add("TradingSysmbol : "+help.commpareTwoString(scriptList.get(i), verifyScriptNames[i]));
			detailList.add("Exchange : "+help.commpareTwoString(exchangeList.get(i),exchangeArray[i]));
			/*
			 * if(scriptList.get(i).equalsIgnoreCase(verifyScriptNames[i])) {
			 * detailList.add("TradingSysmbol : "+scriptList.get(i)+"-PASS"); }else {
			 * detailList.add("TradingSysmbol : "+scriptList.get(i)+"-FAIL"); }
			 * if(exchangeList.get(i).equalsIgnoreCase(exchangeArray[i])) {
			 * detailList.add("Exchange : "+exchangeList.get(i)+"-PASS"); }else {
			 * detailList.add("Exchange : "+exchangeList.get(i)+"-FAIL"); }
			 */
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
		case "6":
			simpleClickPredefineWatchList(model,predefindWatchListDetailList);
			break;
		case "7":
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
