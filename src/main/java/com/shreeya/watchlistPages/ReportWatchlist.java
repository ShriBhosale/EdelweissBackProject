package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shreeya.model.WatchListModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;

public class ReportWatchlist extends ExtendReporter{
	
	Map<String,List<String>> verifyMap;
	ExtentTest test;
	
	String screenshotPath="Screenshot";
	String [] scriptNameArray;
	Help help;
	int count=0;
	
	public ReportWatchlist() {
		
		help=new Help();
	}

	public void report(WatchListModel model,ExtendReporter htmlReporter,Map<String,List<String>> verfiyMap) {
		this.verifyMap=verfiyMap;
		scriptNameArray=WatchListPage.scriptArray;
		for(Map.Entry<String,List<String>> entry : verfiyMap.entrySet()) {
			count++;
			test=htmlReporter.testCreation(help.replaceCountNo(count, entry.getKey()));
			//test=report.createTest(entry.getKey());
			if(entry.getKey().contains("Trade With")) {
				predefineTrading(model,entry.getValue(),test);
			}
			else if(entry.getKey().contains("Click on")){
				clickOnPredefineWatchList(model,entry.getValue(),test);
			}else if(entry.getKey().contains("CreateAddScript")){
				createWatchList(model,entry.getValue(),test);
			}else if(entry.getKey().contains("DeleteScriptWatchlist")){
				deleteScriptWatchList(model,entry.getValue(),test, entry.getKey());
			}else if(entry.getKey().contains("DuplicateScriptWatchList")){
				duplicateWatchList(model,entry.getValue(),test,entry.getKey());
			}else if(entry.getKey().contains("TradingWithWatchList")) {
				tradingWithWatchList(model, entry.getValue(), test);
			}else if(entry.getKey().contains("QuotePage")) {
				codePage(model, entry.getValue(), test);
			}else if(entry.getKey().contains("WatchListTextfield")) {
				watchlistTextfield(entry.getValue(),test);
			}
		}
	}
	
	
	
	

	private void watchlistTextfield(List<String> detailList, ExtentTest test2) {
		String [] result;
		
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL,result[0]);
			}else if(detailList.get(i).contains("=====@@>")) {
				test.log(Status.INFO, "<b>"+detailList.get(i)+"</b>");
			}else if(detailList.get(i).toLowerCase().contains("delete")||detailList.get(i).equalsIgnoreCase("Single Watchlist")) {
				continue;
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}
		
	}

	private void codePage(WatchListModel model, List<String> detailList, ExtentTest test2) {
		String [] result;
		test.log(Status.INFO, "<b>============@@> Verify Code Page <@@============</b>");
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				if(result.length>2)
					test.log(Status.FAIL,result[0]+result[1]);
				else
					test.log(Status.FAIL,result[0]);
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}

	}

	private void duplicateWatchList(WatchListModel model, List<String> detailList, ExtentTest test2, String key) {
		Reporter.log("=========>> duplicateWatchList <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		String [] result;
		test.log(Status.INFO, "<b>============@@> Verify duplicate watchlist <@@============</b>");
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL,result[0]);
			}else if(detailList.get(i).contains("duplicateScript")) {
				test.log(Status.INFO, "<b>============@@> Verify duplicate Script <@@============</b>");
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}
		
	}

	private void deleteScriptWatchList(WatchListModel model, List<String> detailList, ExtentTest test2, String key) {
		Reporter.log("=========>> deleteScriptWatchList <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		String [] result;
		test.log(Status.INFO, "<b>============@@> Verify Script delete <@@============</b>");
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL, result[0]);
			}else if(detailList.get(i).contains("Delete watchList")) {
				test.log(Status.INFO, "<b>============@@> Verify WatchList delete <@@============</b>");
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}
		
	}

	private void predefineTrading(WatchListModel model, List<String> detailList, ExtentTest test2) {
		Reporter.log("=========>> predefineTrading <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		String [] result;
		test.log(Status.INFO, "<b>============@@> PredefineWatchList Trading <@@============</b>");
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL, result[0]);
			} 
			else if(detailList.get(i).contains("Rejection Reason")) {
				  continue;
				  }
				 
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}
		
	}
	
	private void createWatchList(WatchListModel model, List<String> detailList, ExtentTest test2) {
		Reporter.log("=========>> createWatchList <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		 Reporter.log(detailList.get(0), true);
		  if(detailList.get(0).contains("Multiple")) { 
			  test.log(Status.INFO,"<b>============@@> Verify Creation of Multiple WatchList  <@@============</b>");
			 
		  }else/* if(detailList.get(0).equalsIgnoreCase("Single")) {*/
		  test.log(Status.INFO,"<b>============@@> Verify Create WatchList  <@@============</b>"); 
		  
		 
			for(int i=1;i<detailList.size();i++) {
				if(detailList.get(i).contains("WorkingE2")) {
					help.screenshotFullPath(detailList.get(i),test);
				}else {
					if(detailList.get(i).contains("TradingSysmbol")||detailList.get(i).contains("Exchange")) {
						Reporter.log("TradingSysmbol : "+detailList.get(i), true);
						String [] array=help.separater(detailList.get(i),"-");
						if(array[1].equalsIgnoreCase("PASS")) {
							test.log(Status.PASS, array[0]);
						}else {
							test.log(Status.FAIL, array[0]);
						}
						
					}else if(detailList.get(i).equalsIgnoreCase("add Script")) {
						test.log(Status.INFO,"<b>============@@> Verify Script addition  <@@============</b>");
						
					}else {
					Reporter.log(detailList.get(i), true);
					test.log(Status.INFO, detailList.get(i));
					}
				}
			}
			
			/* } */
		
	}

	private void clickOnPredefineWatchList(WatchListModel model, List<String> detailList, ExtentTest test2) {
		String [] result= {"",""};
		Reporter.log("=========>> clickOnPredefineWatchList <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		
		
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL, result[0]);
			}else if(detailList.get(i).equalsIgnoreCase("Rejection Reason")) {
				continue;
			}else if(detailList.get(i).contains("==========@@>")) {
				test.log(Status.INFO, "<b>"+detailList.get(i)+"</b>");
			}else if(detailList.get(i).contains("PredefineWatchList Name")) {
				String [] predefineWatchList=help.separater(detailList.get(i), " : ");
				test.log(Status.INFO, "<b>============@@> Click On "+predefineWatchList[1]+" PredefineWatchList  <@@============</b>");
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}

		
	}


	public void predefineTrading1(WatchListModel model,List<String> detail,ExtentTest test) {
		Reporter.log("=========>> predefineTrading <<============", true);
		test.log(Status.INFO, "<b>============@@> Trading With PredefineWatchList <@@============</b>");
		if(detail.get(1).equalsIgnoreCase("Open")||detail.get(1).equalsIgnoreCase("Complete")) {
			test.log(Status.PASS, "Order Status : "+detail.get(1));
		}else if(detail.get(1).equalsIgnoreCase("rejected")) {
			test.log(Status.PASS, "Order Status : "+detail.get(1));
			test.log(Status.PASS, "Rejection reason : "+detail.get(13));

		}else {
			test.log(Status.FAIL, "Order Status : "+detail.get(1));
		}
		test.log(Status.INFO, "Order action : "+detail.get(2));
		if(detail.get(3).equalsIgnoreCase(model.getScriptName().trim())) {
			test.log(Status.PASS, "Script Name : "+detail.get(3));
		}else {
			test.log(Status.FAIL, "Script Name : "+detail.get(3));
		}
		if(detail.get(4).equalsIgnoreCase(model.getProductType().trim())) {
		test.log(Status.PASS, "Product Name : "+detail.get(4));
		}else {
			test.log(Status.FAIL, "Product Name : "+detail.get(4));
		}
		if(detail.get(5).equalsIgnoreCase(model.getOrderPrice().trim())) {
			test.log(Status.PASS, "Order Price : "+detail.get(5));
			}else {
				test.log(Status.FAIL, "Order price : "+detail.get(5));
			}
		test.log(Status.INFO, "Order type : "+detail.get(6));
		test.log(Status.INFO, "User id : "+detail.get(7));
		if(detail.get(8).equalsIgnoreCase(model.getExchange().trim())) {
			test.log(Status.PASS, "Exchange : "+detail.get(8));
			}else {
				test.log(Status.FAIL, "Exchange : "+detail.get(8));
			}

		help.screenshotFullPath(detail.get(14),test);
		help.screenshotFullPath(detail.get(0),test);
		for(String detailStr:detail) {
			Reporter.log(detailStr, true);
		}

	}
	
	
	
	
	
	public void tradingWithWatchList1(WatchListModel model,List<String> detail,ExtentTest test) {
		String [] fullScriptName=help.commaSeparater(model.getFullScriptName());
		Reporter.log("=========>> tradingWithWatchList <<============", true);
		String [] ScriptNameArray=WatchListPage.scriptArray;
		test.log(Status.INFO, "<b>============@@> Trading With Normal WatchList <@@============</b>");
		test.log(Status.INFO, "WatchName : "+model.getWatchListName());
		if(detail.get(1).equalsIgnoreCase("Open")||detail.get(1).equalsIgnoreCase("Complete")||detail.get(1).equalsIgnoreCase("Cancelled")) {
			test.log(Status.PASS, "Order Status : "+detail.get(1));
		}else if(detail.get(1).equalsIgnoreCase("rejected")) {
			test.log(Status.PASS, "Order Status : "+detail.get(1));
			test.log(Status.PASS, "Rejection reason : "+detail.get(12));
		}else {
			test.log(Status.FAIL, "Order Status : "+detail.get(0));
		}
		test.log(Status.INFO, "Order action : "+detail.get(1));
		if(fullScriptName[fullScriptName.length-1].trim().contains(detail.get(2))) {
			test.log(Status.PASS, "Script Name : "+detail.get(2));
		}else {
			test.log(Status.PASS, "Script Name : "+detail.get(2));
		}
		if(detail.get(3).equalsIgnoreCase(model.getProductType().trim())) {
		test.log(Status.PASS, "Product Name : "+detail.get(3));
		}else {
			test.log(Status.FAIL, "Product Name : "+detail.get(3));
		}
		if(detail.get(4).equalsIgnoreCase(model.getOrderPrice().trim())) {
			test.log(Status.PASS, "Order Price : "+detail.get(4));
			}else {
				test.log(Status.PASS, "Order price : "+detail.get(4));
			}
		test.log(Status.INFO, "Order type : "+detail.get(5));
		test.log(Status.INFO, "User id : "+detail.get(6));
		if(detail.get(7).equalsIgnoreCase(model.getExchange().trim())) {
			test.log(Status.PASS, "Exchange : "+detail.get(7));
			}else {
				test.log(Status.PASS, "Exchange : "+detail.get(7));
			}
		help.screenshotFullPath(detail.get(16),test);
		for(String detailStr:detail) {
			Reporter.log(detailStr, true);
		}
	}
	
	private void tradingWithWatchList(WatchListModel model, List<String> detailList, ExtentTest test2) {
		Reporter.log("=========>> tradingWithWatchList <<============", true);
		for(String detailStr:detailList) {
			Reporter.log(detailStr, true);
		}
		String [] result;
		test.log(Status.INFO, "<b>============@@> Trading With Normal WatchList <@@============</b>");
		for(int i=0;i<detailList.size();i++) {
			if(detailList.get(i).contains("WorkingE2")) {
				help.screenshotFullPath(detailList.get(i),test);
			}else if(detailList.get(i).contains("PASS")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.PASS, result[0]);
			}else if(detailList.get(i).contains("FAIL")) {
				result=help.separater(detailList.get(i),"-");
				test.log(Status.FAIL, result[0]);
			}else if(detailList.get(i).equalsIgnoreCase("Rejection Reason")) {
				continue;
			}
			else {
				test.log(Status.INFO,detailList.get(i));
			}
		}
		
	}
	
}
