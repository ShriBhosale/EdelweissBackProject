package com.shreeya.experiment;

import org.testng.Reporter;

import com.shreeya.util.ConfigReader;

public class Report {
	
	ConfigReader configReader;
	private final boolean debugMode;
	public Report() {
		configReader=new ConfigReader();
		this.debugMode =Boolean.valueOf(configReader.configReader("DebugMode"));
	}
	
	public void printLog(String msg,boolean debugLevel) {
		//System.out.println("debugLevel : "+debugLevel+"\ndebugMode : "+debugMode);
		if(debugMode==false && debugLevel==false) 
			Reporter.log(msg, true);
		else if(debugMode==false && debugLevel==true) {
			
		}
		else
			Reporter.log(msg, true);
	}
	
	public void printBoldLog(String msg,boolean debugLevel) {
		if(debugMode==false && debugLevel==false) 
			Reporter.log("<b>"+msg+"</b>", true);
		else if(debugMode==false && debugLevel==true) {
			
		}
		else
			Reporter.log("<b>"+msg+"</b>", true);
	}
}
