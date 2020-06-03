package com.shreeya.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;

public class Help {

	public String [] commaSeparater(String scriptName) {
		Reporter.log("== commaSeparater ===", true);
		String [] scriptArray= {scriptName};
		if(scriptName.contains(",")) {
			scriptArray=scriptName.split(",");
		}
		for(String text:scriptArray)
			Reporter.log(text, true);
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
	
	public String randomNo() {
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
		
	}
	
	public String screenshotFullPath(String screenshotPath,ExtentTest test) {
		
		ConfigReader reader=new ConfigReader();
		String path=reader.configReader("Result");
	
		 screenshotPath=screenshotPath.replace("../WorkingE2",path);
		  screenshotPath=screenshotPath.replace("/", "//");
		Reporter.log("Screenshot : "+screenshotPath, true);
		try {
			
			 test.addScreenCaptureFromPath(screenshotPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshotPath;
	}
	
	
	
	public String addTwoStringNo(String number1, String number2) {
		int ans=0;
		try {
		int no1=Integer.valueOf(number1);
		int no2=Integer.valueOf(number2);
		 ans=no1+no2;
		 Reporter.log("Number 1 : "+no1+"\nNumber 2 : "+no2+"\nAns : "+ans,true);
		}catch (Exception e) {
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString(),true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}
		return String.valueOf(ans);
	}

	public String removeHtmlText(String fetchTextFromElement) {
		String ans="";
		if(fetchTextFromElement.contains("\n")) {
			fetchTextFromElement=fetchTextFromElement.replace("\n", "");
		}
		String [] elementArray=fetchTextFromElement.split(">");
		
		String [] textArray=elementArray[0].split("<");
		ans=ans+textArray[0].trim();
		textArray=elementArray[2].split("<");
		ans=ans+" "+textArray[0].trim();
		textArray=elementArray[6].split("<");
		ans=ans+" "+textArray[0].trim();
		
		Reporter.log(ans, true);
		return ans;
	}
	
	public String removeSpanHtmlTag(String fetchTextFromElement) {
		String ans="";
		if(fetchTextFromElement.contains("\n")) {
			fetchTextFromElement=fetchTextFromElement.replace("\n", "");
		}
		String [] elementArray=fetchTextFromElement.split(">");
		for(String elementText:elementArray) {
			if(elementText.contains("</")) {
				String [] eleArray=elementText.split("</");
				if(!ans.equalsIgnoreCase("")) {
					ans=ans+" : ";
				}
				ans=ans+eleArray[0];
			}
		}
		Reporter.log("ans : "+ans, true);
		return ans;
	}
	
	public String separeComparePrintApp(String applicationStr,String testDataStr) {
		Reporter.log("===> separeComparePrintApp <===", true);
		String result="";
	   String [] colonArray=applicationStr.split(":");
		if(colonArray[1].contains("₹")) {
			colonArray[1]=colonArray[1].replace("₹", "");
		}
		Reporter.log("Application str: "+applicationStr,true);
		Reporter.log("colonArray[1] : "+colonArray[1].trim()+"\nTestData : "+testDataStr, true);
		if(colonArray[1].trim().equalsIgnoreCase(testDataStr)) {
			result=applicationStr+"-PASS";
		}else {
			result=applicationStr+"-FAIL";
		}
		Reporter.log("Result : "+result, true);
		return result;
	}
	
	public String bigDataAddition(String bigNo,String noWantToAdd) {
		Reporter.log("===> bigDataAddition <===", true);
		Reporter.log("bigNo : "+bigNo+"\nnoWantToAdd : "+noWantToAdd, true);
		String number="1,00,00,04,449.00";
		String no1="0";
		if(bigNo.contains(",")) 
		 no1=bigNo.replace(",", "");
		else
			no1=bigNo;
		BigDecimal bd1 =new BigDecimal(no1); 
		BigDecimal bd2 =new BigDecimal(noWantToAdd);
		System.out.println("no1 : "+bd1);
		
		Reporter.log("Addition : "+bd1.add(bd2),true);
		return bd1.add(bd2).toEngineeringString();
	}
	
	public String compareBigNo(String applicationNo,String testDataNo,String ansStr) {
		Reporter.log("=== compareBigNo ===", true);
		String result;
		if(applicationNo.contains(",")) 
			applicationNo=applicationNo.replace(",", "");
		if(testDataNo.contains(",")) 
			testDataNo=testDataNo.replace(",", "");
		if(applicationNo.equalsIgnoreCase(testDataNo)) {
			result=ansStr+"-PASS";
			
		 }else {
			result=ansStr+"-FAIL";
		 }
		Reporter.log("applicationNo : "+applicationNo+"\ntestDataNo : "+testDataNo+"\nansStr : "+ansStr,true);
		Reporter.log("Result : "+result);
		return result;
	}
	
	public static void main(String[] args) {
		Help h=new Help();
		String a=h.bigDataAddition("1,00,00,04,449.00", "11");
		System.out.println("a : "+a);
	}

	
}
