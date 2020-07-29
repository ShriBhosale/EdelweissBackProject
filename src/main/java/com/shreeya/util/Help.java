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

}
