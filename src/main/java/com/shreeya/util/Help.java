package com.shreeya.util;

public class Help {

	public String [] commaSeparater(String scriptName) {
		String [] scriptArray= {scriptName};
		if(scriptName.contains(",")) {
			scriptArray=scriptName.split(",");
		}
		return scriptArray;
	}
	
	public String [] separater(String scriptName,String symbol) {
		String [] scriptArray= {scriptName};
		if(scriptName.contains(symbol)) {
			scriptArray=scriptName.split(symbol);
		}
		return scriptArray;
	}
}
