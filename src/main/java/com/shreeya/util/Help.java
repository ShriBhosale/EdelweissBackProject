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
	
	public void digitConver(String number) {
		String a=" ";
		if(number.contains(",")) {
			String [] numberArray=number.split(",");
			number=numberArray[0];
		}
		char [] digitArray=number.toCharArray();
		if(digitArray.length>3){
			for(int i=0;i<digitArray.length;i++) {
				if(i==0) 
					a=digitArray[i]+",";
				else
					a=a+digitArray[i];
				
			}
		}
		
	}
	
	public String replaceCountNo(int count,String testcase) {
		String ans="Ans";
		if(testcase.contains("_")) {
			String [] array=testcase.split("_");
			ans=array[0]+"_"+count;
		}
		return ans;
	}
	
	public String tradeXpath(String scriptName) {
		String [] array=scriptName.split(" ");
		if(array[0].trim().equalsIgnoreCase("Hdfc")) {
			scriptName="Hdfc  Bank  Ltd";
		}
		else {
			scriptName=scriptName.replace(" ", "  ");
		}
		return scriptName;
	}
	
	public String absolutePathProvider(String inputPath) {
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
	
		String screenshotPath=inputPath.replace("../WorkingE2",path);
		  screenshotPath=screenshotPath.replace("/", "//");
		  return screenshotPath;
	}
}
