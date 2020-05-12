package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

public class WatchListKeywords {

	public List<String> keywordProccess(String keyword){
		List<String> stepsList=new ArrayList<String>();
		Map<String,List<String>> keywordSteps=new HashMap<String,List<String>>();
		List<String> createSteps=new ArrayList<String>();
		createSteps.add("Create");
		createSteps.add("Verfiy");
		
		List<String> createDeleteSteps=new ArrayList<String>();
		createDeleteSteps.add("Create");
		createDeleteSteps.add("Verfiy");
		createDeleteSteps.add("Delete");
	
		List<String> createAddScriptSteps=new ArrayList<String>();
		createAddScriptSteps.add("Create");
		createAddScriptSteps.add("AddScript");
		createAddScriptSteps.add("Verfiy");
		List<String> createAddScriptDeleteSteps=new ArrayList<String>();
		createAddScriptDeleteSteps.add("Create");
		createAddScriptDeleteSteps.add("AddScript");
		createAddScriptDeleteSteps.add("Verfiy");
		createAddScriptDeleteSteps.add("Delete");
		
		List<String> createDuplicateSteps=new ArrayList<String>();
		createDuplicateSteps.add("Create");
		createDuplicateSteps.add("Verfiy");
		createDuplicateSteps.add("Create");
		
		List<String> predefineWatchList=new ArrayList<String>();
		predefineWatchList.add("PredefineWatchList");
		predefineWatchList.add("Verfiy");
		
		List<String> createAddScriptDeleteScript=new ArrayList<String>();
		createAddScriptDeleteScript.add("Create");
		createAddScriptDeleteScript.add("AddScript");
		createAddScriptDeleteScript.add("DeleteScript");
		createAddScriptDeleteScript.add("Verfiy");
		
		List<String> tradeWithpredefineWatchList=new ArrayList<String>();
		tradeWithpredefineWatchList.add("PredefineWatchList");
		tradeWithpredefineWatchList.add("Verfiy");
		
		keywordSteps.put("create", createSteps);
		keywordSteps.put("CreateDelete", createDeleteSteps);
		keywordSteps.put("CreateAddScript", createAddScriptSteps);
		keywordSteps.put("CreateAddScriptDelete", createAddScriptDeleteSteps);
		keywordSteps.put("CreateDuplicate",createDuplicateSteps);
		keywordSteps.put("ClickPredineWatchList",predefineWatchList);
		keywordSteps.put("CreateAddScriptDeleteScript",createAddScriptDeleteScript);
		keywordSteps.put("TradeWithpredefineWatchList",tradeWithpredefineWatchList);
		
		
		for (Map.Entry<String,List<String>> entry : keywordSteps.entrySet()) {
			String mapStep=entry.getKey();
			Reporter.log("Keyword : "+mapStep, true);
			if(mapStep.equalsIgnoreCase(keyword.trim())) {
				stepsList=entry.getValue();
				break;
			}
		}
		for(String steps:stepsList) {
			Reporter.log(steps, true);
		}
		return stepsList;
	}
	
}
