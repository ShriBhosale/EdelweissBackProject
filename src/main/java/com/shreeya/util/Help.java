package com.shreeya.util;

import org.testng.Reporter;

public class Help {
	
	public void help() {}
	
	public String [] commonSeparater(String input) {
		Reporter.log("commonSeparater");
		
		String [] inputArray= {input};
		if(input.contains(","))
		inputArray=input.split(",");
			
		return inputArray;
	}
	
	public String replaceActualPath(String path) {
		Reporter.log("replaceActualPath", true);
		Reporter.log("Old Path name : "+path, true);
		path.replace("../WorkingE2", "E:\\EdelweissProject\\WorkingE2");
		Reporter.log("New Path name : "+path, true);
		return path;
	}

}
