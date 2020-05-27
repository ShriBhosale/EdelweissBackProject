package com.shreeya.util;

import org.testng.Reporter;

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
			scriptName="Hdfc  Bank  Ltd.";
		}
		else if(scriptName.equalsIgnoreCase("Aditya Birla Capital Ltd")) {
			scriptName=scriptName.replace(" ", "  ");
			scriptName=scriptName+".";
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

	public String removeHtmlCode(String ltp) {
		String [] htmlSpliter=ltp.split(">");
		String [] ltpArray=htmlSpliter[3].split("<");
		String ltpPrice=ltpArray[0];
		 ltpArray=htmlSpliter[5].split("<");
		ltpPrice=ltpPrice+ltpArray[0];
		Reporter.log("LTP price : "+ltpPrice, true);
		return ltpPrice;	
	}
	
	public String removeFutureFromScript(String scriptName) {
		if(scriptName.contains("Future")) {
		String [] scriptNameArray=separater(scriptName, " ");
		Reporter.log("ScriptName : "+scriptNameArray[0], true);
		scriptName=scriptNameArray[0];
		}
		return scriptName;
	}

	public String commpareTwoString(String applicationStr, String testDataStr) {
		String result;
		if(applicationStr.trim().equalsIgnoreCase(testDataStr)) {
			result=applicationStr+"-PASS";
			Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
		}
		else {
			result=applicationStr+"-FAIL";
			Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
		}
		return result;
	}
	
	public String commpareMultStrWithSinStr(String applicationStr, String [] testDataArray) {
		String result="No";
		for(String testDatastr:testDataArray) {
			if(applicationStr.trim().equalsIgnoreCase(testDatastr)) {
				result=applicationStr+"-PASS";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDatastr+"\nResult : "+result, true);
				break;
			}
			else {
				result=applicationStr+"-FAIL";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDatastr+"\nResult : "+result, true);
			}
		}
		return result;
	}
	
	public String testStrContainInAppliStr(String applicationStr,String testDataStr) {
		String result="No Result";
		char [] applicaitonCharArray=applicationStr.toCharArray();
		char [] testDataCharArray=testDataStr.toCharArray();
		if(applicaitonCharArray.length>testDataCharArray.length) {
			if(applicationStr.contains(testDataStr)) {
				result=applicationStr+"-PASS";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
			}else {
				result=applicationStr+"-FAIL";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
			}
			
		}else if(applicaitonCharArray.length<testDataCharArray.length) {
			if(testDataStr.contains(applicationStr)) {
				result=applicationStr+"-PASS";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
			}
		else {
				result=applicationStr+"-FAIL";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
			}
		}
		return result;
	}
	
	public String digitConvert(String number) {
		if(number.contains(".")) {
			String [] numberArray=number.split("\\.");
			char [] digtiArray=numberArray[1].toCharArray();
			if(digtiArray.length==1) {
				number=number+"0";
			}
		}else {
			number=number+".00";
		}
		number=commoRemove(number);
		return number;
	}
	
	public String commoRemove(String number) {
		String ans="";
		char [] numberArray=number.toCharArray();
		for(char a:numberArray) {
			if(a==',') {
				continue;
			}else {
				ans=ans+a;
			}
		}
		
		Reporter.log("Ans : "+ans, true);
		return ans;
	}
	
	
	
	public static void main(String[] args) {
		String number="2,015.00";
		
	}
}
