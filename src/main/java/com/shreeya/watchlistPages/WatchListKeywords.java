package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

public class WatchListKeywords {

	public List<String> keywordProccess(String predefineWatchList) {
		List<String> stepsList = new ArrayList<String>();
		List<String> normalWatchList = new ArrayList<String>();

		
		  normalWatchList.add("Create"); 
		  normalWatchList.add("AddScript");
		  normalWatchList.add("Verfiy_1");
		  
		  normalWatchList.add("TradingWithWatchList"); 
		  normalWatchList.add("Verfiy_2");
		  
		  normalWatchList.add("QuotePage"); 
		  normalWatchList.add("Verfiy_3");
		  
		  normalWatchList.add("Create"); 
		  normalWatchList.add("DuplicateScript");
		  normalWatchList.add("Verfiy_4");
		  
		  normalWatchList.add("DeleteScript"); 
		  normalWatchList.add("Delete");
		  normalWatchList.add("Verfiy_5");
		 
		
		normalWatchList.add("WatchListTextfield");
		normalWatchList.add("Verfiy_6");

		List<String> predWatchList = new ArrayList<String>();

		predWatchList.add("PredefineWatchList");
		predWatchList.add("Verfiy_7");

		predWatchList.add("PredefineWatchListTrade");
		predWatchList.add("Verfiy_8");

		if (predefineWatchList.equalsIgnoreCase("Yes")) {
			stepsList = predWatchList;
		} else {

			stepsList = normalWatchList;
		}

		for (String steps : stepsList) {
			Reporter.log(steps, true);
		}

		return stepsList;
	}

}
