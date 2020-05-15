package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shreeya.model.WatchListModel;
import com.shreeya.util.ExtendReporter;

public class ReportWatchlist extends ExtendReporter{
	
	Map<String,List<String>> verifyMap;
	ExtentTest test;
	
	public ReportWatchlist() {
		this.verifyMap=WatchListStepVerify.verfiyMap;
	}

	public void report(WatchListModel model,ExtendReporter htmlReporter) {
		for(Map.Entry<String,List<String>> entry : verifyMap.entrySet()) {
			test=htmlReporter.testCreation(entry.getKey());
			//test=report.createTest(entry.getKey());
			if(entry.getKey().contains("Trade With")) {
				predefineTrading(model,entry.getValue(),test);
			}
		}
	}
	
	

	public void predefineTrading(WatchListModel model,List<String> detail,ExtentTest test) {
		test.log(Status.INFO, "<b>============@@> Trading With PredefineWatchList <@@============</b>");
		if(detail.get(2).equalsIgnoreCase("Open")) {
			test.log(Status.PASS, "Order Status : "+detail.get(2));
		}else if(detail.get(2).equalsIgnoreCase("rejected")) {
			test.log(Status.FAIL, "Order Status : "+detail.get(2));
			test.log(Status.FAIL, "Rejection reason : "+detail.get(14));
		}
		test.log(Status.INFO, "Order action : "+detail.get(3));
		if(detail.get(4).equalsIgnoreCase(model.getScriptName().trim())) {
			test.log(Status.PASS, "Script Name : "+detail.get(4));
		}else {
			test.log(Status.FAIL, "Script Name : "+detail.get(4));
		}
		if(detail.get(5).equalsIgnoreCase(model.getProductType().trim())) {
		test.log(Status.PASS, "Product Name : "+detail.get(5));
		}else {
			test.log(Status.FAIL, "Product Name : "+detail.get(5));
		}
		if(detail.get(6).equalsIgnoreCase(model.getOrderPrice().trim())) {
			test.log(Status.PASS, "Order Price : "+detail.get(6));
			}else {
				test.log(Status.FAIL, "Order price : "+detail.get(6));
			}
		test.log(Status.INFO, "Order type : "+detail.get(7));
		test.log(Status.INFO, "User id : "+detail.get(8));
		if(detail.get(9).equalsIgnoreCase(model.getExchange().trim())) {
			test.log(Status.PASS, "Order Price : "+detail.get(9));
			}else {
				test.log(Status.FAIL, "Order price : "+detail.get(9));
			}
		try {
			Reporter.log("Screenshot : "+detail.get(18), true);
			test.addScreencastFromPath(detail.get(18));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
