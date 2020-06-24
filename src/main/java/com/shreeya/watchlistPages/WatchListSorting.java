package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;



//When I click on sorting button
//1 sorting dsc
//again click it was sorting asc
//again click it was sorting dsc
public class WatchListSorting extends SeleniumCoder{

	WebDriver driver;
	Help help;
	WatchListCommon common;
	List<Float> floatList;
	List<String> strList;
	List<String> detailList;
	private String watchListName;
	private WebElement lastTradePriceAscAndDscButton;
	private WebElement ascendAndDscendButton;
	private WebElement sortingMsgLable;
	private String sortingMsg;
	private List<String> beforeClickSorting;
	private List<Float> backfloatList;
	private List<String> sortingElements;
	public WatchListSorting(WebDriver driver) {
		super(driver);
		this.driver=driver;
		common=new WatchListCommon(driver);
		help=new Help(driver);
		floatList=new ArrayList<Float>();
		strList=new ArrayList<String>();
		detailList=new ArrayList<String>();
		beforeClickSorting=new ArrayList<String>();
		backfloatList=new ArrayList<Float>();
	}
	
	public boolean listForSorting(String xpathStr,String groupNameElement,boolean ascending,String typeList,int noTimeSorting) {
		List<String> sortedList = null;
		if(typeList.equalsIgnoreCase("String"))
			sortingElements=multipleElementsTextProviderFilter(xpathStr, groupNameElement);
			else
				sortingElements=multipleElementsTextProvider(xpathStr, groupNameElement);
		if(typeList.equalsIgnoreCase("float")){
		floatList=help.convertStringListToFloat(sortingElements);
		sortingElements=help.removeLetterForArrayList(sortingElements);
		//replace 0 by 3 for need to discuss with abisheka and atriya
		if(noTimeSorting!=0)
		sortedList=help.sortingFloat(ascending, floatList);
		else
			sortedList=help.sortingFloat(ascending, backfloatList);
		}else if(typeList.equalsIgnoreCase("String")){
			if(noTimeSorting!=0)
				sortedList=help.sortingString(ascending, sortingElements);
				else
			sortedList=help.sortingString(ascending, beforeClickSorting);
		}
		return help.compareTwoList(sortingElements, sortedList);
	}
	
	public void listForSortingBeforeClick(String xpathStr,String groupNameElement,boolean ascending,String typeList) {
		List<String> sortedList = null;
		if(typeList.equalsIgnoreCase("String"))
		beforeClickSorting=multipleElementsTextProviderFilter(xpathStr, groupNameElement);
		else
			beforeClickSorting=multipleElementsTextProvider(xpathStr, groupNameElement);
		if(typeList.equalsIgnoreCase("float")){
		backfloatList=help.convertStringListToFloat(beforeClickSorting);
		beforeClickSorting=help.removeLetterForArrayList(beforeClickSorting);
		}
		
	}
	
	public void ascLastTradePrice(String watchListName) {
		Reporter.log("====> ascLastTradePrice <====", true);
		String lastTradePricexpath="//*[@id='contentCntr']/div/div/div[1]/div[4]/div/div/div/div/div[2]/div[1]/div/div[2]/span/div/i";
		lastTradePriceAscAndDscButton=fluentWaitCodeXpath(lastTradePricexpath, "Ascending and dscending button");
		clickUsingAction(lastTradePriceAscAndDscButton, "lastTradePriceAscDscbutton");
		//boolean sortedFlag=listForSorting("//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div/div/div[2]/span", "Last trade prices", false, "float");
//		if(sortedFlag)
//			detailList.add(watchListName+" script are sorted by Last trade price-PASS");
//		else
//			detailList.add(watchListName+" script not are sorted by Last trade price-FAIL");
	}
	
	public void checkSorting(String watchListName,String sortButtonXpath,String groupElementXpath,String groupElementName,String typeElement,boolean ascendingOrNot,int noTimeSorting) {
		Reporter.log("====> checkAscendingSorting <====",true);
		Reporter.log("noTimeSorting : "+noTimeSorting,true);
		staticWait(5000);
		String sorting="decending order";
		//discuss with abisheka and atriya
		/*
		 * if(noTimeSorting==1) listForSortingBeforeClick(groupElementXpath,
		 * groupElementName, ascendingOrNot, typeElement);
		 */
		
		ascendAndDscendButton=fluentWaitCodeXpath(sortButtonXpath, "Ascending and dscending button");
		
		clickElementUsingJavaScript(ascendAndDscendButton, "AscDscbutton");
		String sortClassValue=getValueFromAttribute(ascendAndDscendButton, "class", "sort Class value");
		if(ascendingOrNot) {
			if(!sortClassValue.contains("-up")) {
				clickElementUsingJavaScript(ascendAndDscendButton, "AscDscbutton");
			}
		}else {
			if(!sortClassValue.contains("-down")) {
				clickElementUsingJavaScript(ascendAndDscendButton, "AscDscbutton");
			}
		}
		if(noTimeSorting==1) {
			//discuss with abisheka and atriya
			/*
			 * staticWait(20000); clickElementUsingJavaScript(ascendAndDscendButton,
			 * "AscDscbutton");
			 */
			
		}
			
		
		boolean sortedFlag=listForSorting(groupElementXpath, groupElementName, ascendingOrNot, typeElement,noTimeSorting);
		if(ascendingOrNot) {
			sorting="ascending order";
		}
		Reporter.log("Sorting result : "+sorting, true);
		if(sortedFlag)
			detailList.add(watchListName+" script are sorted by "+sorting+" on "+groupElementName+"-PASS");
		else
			detailList.add(watchListName+" script not are sorted by "+sorting+" on "+groupElementName+"-FAIL");
		
		detailList.add(ScreenshortProvider.captureScreen(driver, groupElementName));
	}
	
	
	
	public void sorting(String watchListName,String sortButtonXpath,String groupElementXpath,String groupElementName,String typeElement) {
		Reporter.log("====> sorting <====", true);
		detailList.add("@@> Verify sorting of scrips when user selects "+groupElementName+". <@@");
		checkSorting(watchListName, sortButtonXpath, groupElementXpath, groupElementName, typeElement, true,1);
		
		detailList.add("@@> Verify sorting of scrips when user selects "+groupElementName+" again. <@@");
		checkSorting(watchListName, sortButtonXpath, groupElementXpath, groupElementName, typeElement, false,2);
		
		detailList.add("@@> Verify sorting of scrips when user selects "+groupElementName+" 3rd time. <@@");
		checkSorting(watchListName, sortButtonXpath, groupElementXpath, groupElementName, typeElement, true, 3);
		fetchsortingMsg(groupElementName);
	}
	
	public void fetchsortingMsg(String sortedOptin) {
		Reporter.log("====> fetchsortingMsg <====",true);
		if(sortedOptin.equalsIgnoreCase("Last Trade Price (Rs.)."))
			sortedOptin="LTP";
		sortingMsgLable=fluentWaitCodeXpath("//span[@class='count ng-scope']", "sorting msg");
		sortingMsg=help.removeHtmlReporter(fetchTextFromElement(sortingMsgLable));
		String [] array=sortingMsg.split("  ");
		if(help.testStrContainInAppliStr(array[1], sortedOptin).contains("PASS"))
			detailList.add(sortingMsg+"-PASS");
		else
			detailList.add(sortingMsg+"-FAIL");
		//detailList.add(ScreenshortProvider.captureScreen(driver, sortedOptin));
		
	}
	
	public void lastTradePriceSorting(String watchString){
		Reporter.log("===> lastTradePriceSorting <===", true);
		String lastTradePricexpath="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[2]/span/div/i";
		String lastPriceGropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div/div[2]/span";
		sorting(watchListName, lastTradePricexpath, lastPriceGropXpath, "Last Trade Price (Rs.).", "float");
	}
	
	public void volumeSorting(String watchListName) {
		Reporter.log("====> volumeSorting <====", true);
		String volumeSortingButton="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[4]/span/div/i";
		String volumeGropXpath="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div/div[4]/span";
		sorting(watchListName, volumeSortingButton, volumeGropXpath, "Volume", "float");
	}
	
	public void lowPriceSorting(String watchListName) {
		Reporter.log("====> lowPriceSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[7]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div/div[7]/span";
		sorting(watchListName, sortingButton, gropXpath, "Low Price", "float");
	}
	
	public void hightPriceSorting(String watchListName) {
		Reporter.log("====> hightPriceSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[8]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div/div[8]/span";
		sorting(watchListName, sortingButton, gropXpath, "High Price", "float");
	}
	
	public void changePercentageSorting(String watchListName) {
		Reporter.log("====> ChangePercentageSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[3]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[3]/span[2]";
		sorting(watchListName, sortingButton, gropXpath, "Change Percentage", "float");
	}
	
	public void tradingSymbolSorting(String watchListName) {
		Reporter.log("====> tradingSymbolSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[1]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[1]/a";
		sorting(watchListName, sortingButton, gropXpath, "Symbol", "String");
	}
	
	public void bidPriceSorting(String watchListName) {
		Reporter.log("====> bidPriceSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[5]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[5]/span[1]";
		sorting(watchListName, sortingButton, gropXpath, "Bid Price", "float");
	}
	
	public void askPriceSorting(String watchListName) {
		Reporter.log("====> askPriceSorting <====", true);
		String sortingButton="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[1]/div/div[6]/span/div/i";
		String gropXpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[6]/span[1]";
		sorting(watchListName, sortingButton, gropXpath, "Ask Price", "float");
	}
	
	public ExtendReporter sortingScenarioExecute(String segment,ExtendReporter report) {
		Reporter.log("<b>====> sortingScenarioExecute <=====</b>", true);
		common.redirectToWatchListModule(true);
		if(segment.equalsIgnoreCase("Equity")) {
			watchListName="MarketWatch";
			
			  common.watchListtabNotFound("MarketWatch", "check","BSE");
			  
			  tradingSymbolSorting(watchListName);
				 lastTradePriceSorting(watchListName); 
			 
			  changePercentageSorting(watchListName);
				
				
				  volumeSorting(watchListName);
				  
				  bidPriceSorting(watchListName); 
				  askPriceSorting(watchListName);
				  
				  lowPriceSorting(watchListName);
				  hightPriceSorting(watchListName);
				 

		}
		report.watchListSorting(detailList);
		return report;
	}
}
