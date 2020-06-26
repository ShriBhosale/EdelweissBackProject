package com.shreeya.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.shreeya.experiment.Report;

public class Help extends SeleniumCoder{
	WebDriver driver;
	private String enterMsgInTextfield;
	private WebElement textfieldElement;
	ConfigReader config;
	public Help(){}
	
	public Help(WebDriver driver) {
		super(driver);
		this.driver=driver;
		config=new ConfigReader();
	}

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
		Reporter.log("*** separater ***", true);
		
		if(scriptName==null)
			Reporter.log("Script name is null", true);
		if(symbol==null)
			Reporter.log("symbol is null", true);
		
		String [] scriptArray= {scriptName};
		if(scriptName.contains(symbol)) {
			scriptArray=scriptName.split(symbol);
			for(String str:scriptArray) {
				Reporter.log(str, true);
			}
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
		if(applicationStr.trim().equalsIgnoreCase(testDataStr.trim())) {
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
		}else {
			if(testDataStr.equalsIgnoreCase(applicationStr)) {
				result=applicationStr+"-PASS";
				Reporter.log("Application text : "+applicationStr+"\nTest data text : "+testDataStr+"\nResult : "+result, true);
			}else {
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
	
	public String digitCoverts(String number,String StrIgnore) {
		if(number.contains(StrIgnore)) {
			number=number.replace(StrIgnore, "");
		}
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
		return number+StrIgnore;
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
	
	public float removeCommoConvertIntoFloat(String number) {
		float floatNo=0.0f;
		boolean negativeValueFlag=false;
		
		if (!number.matches(".*[a-z].*")) {
		if(number.contains("(") && number.contains(")")) {
			number=number.replace("(", "");
			number=number.replace(")", "");
		}
		
		if(number.contains(".")) {
		String [] digitArray=number.split("\\.");
		char [] charArray=digitArray[1].toCharArray();
		if(charArray.length==1) {
			number=number+"0";
		}}
		
		if(number.contains(","))
			number=number.replace(",", "");
		 
		
		if(!number.equalsIgnoreCase(""))
		
			floatNo=Float.valueOf(number);
		
		}
		
		return floatNo;
	}
	
	public List<String> removeLetterForArrayList(List<String> inputList) {
		List<String> output=new ArrayList<String>();
		for(String input:inputList) {
			if(!input.equalsIgnoreCase("--")) {
			if(!input.matches(".*[a-z].*")) {
				if(!input.equalsIgnoreCase("")) {
			if(input.contains("(") && input.contains(")")) {
				input=input.replace("(", "");
				input=input.replace(")", "");
				output.add(input);
			}else {
				output.add(input);
			}
			}
			}
			}
		}
		return output;
	}
	
	
	public List<Float> convertStringListToFloat(List<String> inputList) {
		List<Float> floatList=new ArrayList<Float>();
		for(String input:inputList) {
			if(!input.equalsIgnoreCase("--")) {
			float floatNo=removeCommoConvertIntoFloat(input);
			if(floatNo!=0.0)
			floatList.add(floatNo);
			}
		}
		return floatList;
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
	
	public String elementPresent(WebElement element,String passMsg,String failMsg) {
		String result="";
		if(element!=null) {
			result=passMsg+"-PASS";
		}else {
			result=failMsg+" is not present-FAIL";
		}
		return result;
	}
	
	public String elementPresent(WebElement element,String msg) {
		String result="";
		if(element!=null) {
			result=msg+"is present-PASS";
		}else {
			result=msg+"is not prese-FAIL";
		}
		return result;
	}
	
	public String elementPresent(String xpathStr,String elementName) {
		WebElement element=fluentWaitCodeXpath(xpathStr, elementName);
		String result="";
		if(element!=null) {
			result=elementName+"is present -PASS";
		}else {
			result=elementName+"is not present-FAIL";
		}
		return result;
	}
	
	public String elementPresent(String xpathStr,String elementName,String msg) {
		WebElement element=fluentWaitCodeXpath(xpathStr, elementName);
		String result="";
		if(element==null) {
			result=msg+"is display -PASS";
		}else {
			result=msg+"is not display-FAIL";
		}
		return result;
	}
	
	public String elementNotPresent(WebElement element,String passMsg,String failMsg) {
		String result="";
		if(element==null) {
			result=passMsg+"-PASS";
		}else {
			result=failMsg+"-FAIL";
		}
		return result;
	}
	
	public String removeHtmlReporter(String htmlStr) {
		Reporter.log("=== removeHtmlReporter ===", true);
		String ans="";
		String [] array,extraArray;
		if(htmlStr.contains("₹"))
			htmlStr=htmlStr.replace("₹", "");
		if(htmlStr.contains("\n"))
			htmlStr=htmlStr.replace("\n", "");
		if(htmlStr.contains("<"))
		{
			array=htmlStr.split("<");
			for(String str:array) {
				if(!(str.contains("<")||str.contains(">"))) 
					ans=ans+" "+str.trim();
				else if(str.contains(">")) {
					extraArray=str.split(">");
					if(extraArray.length>1)
						ans=ans+" "+extraArray[1].trim();		
				}
					
			}
			htmlStr=ans;
		}
		
		Reporter.log("ans : "+htmlStr, true);
		return htmlStr;
	}
	
	public void elementDisappear(String xpath,String elememntName,int timeOutSecount) {
		Reporter.log("*** elementDisAppear ***", true);
		Reporter.log("Wait for "+elememntName);
		WebDriverWait wait = new WebDriverWait(driver, timeOutSecount);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		staticWait(1000);
	}
	
	
	public List<String> removeDuplicateValueArrayList(List<String> inputList) {
		Reporter.log("==== duplicateValueArrayList =====", true);
		List<String> newList=new ArrayList<String>();
		for(String inputStr:inputList) {
		if(!newList.contains(inputStr.trim()))
			newList.add(inputStr);
		}
		return newList;
	}
	
	public boolean checkDuplicatePresentOrNot(List<String> inputList) {
		boolean duplicateFlag=false;
		List<String> newList=removeDuplicateValueArrayList(inputList);
		if(newList.size()<inputList.size()) {
			duplicateFlag=true;
		}
		return duplicateFlag;
	}
	public static void main(String[] args) {
		Help h=new Help();
		String a=h.bigDataAddition("1,00,00,04,449.00", "11");
		System.out.println("a : "+a);
	}

	public List<String> sortingString(boolean ascending,List<String> inputList) {
		if(ascending) {
			Collections.sort(inputList);
		}else {
			Collections.sort(inputList,Collections.reverseOrder());
		}
		return inputList;
	}
	
	public List<Integer> sortingInt(boolean ascending,List<Integer> inputList) {
		if(ascending) {
			Collections.sort(inputList);
		}else {
			Collections.sort(inputList,Collections.reverseOrder());
		}
		return inputList;
	}
	
	public List<String> sortingFloat(boolean ascending,List<Float> inputList) {
		List<String> outputList=new ArrayList<String>();
		if(ascending) {
			Collections.sort(inputList);
		}else {
			Collections.sort(inputList,Collections.reverseOrder());
		}
		
		for(Float input:inputList)
			outputList.add(convertTwoDecimalFloat(input));
		
		return outputList;
	}
	
	public String convertTwoDecimalFloat(float floatNo) {
		String floatNoStr=String.valueOf(floatNo);
		String [] decimalArray=floatNoStr.split("\\.");
		char [] charArray=decimalArray[1].toCharArray();
		if(charArray.length==1) {
			floatNoStr=floatNoStr+"0";
		}else {
			floatNoStr=String.valueOf(floatNo);
		}
		
		return floatNoStr;
	}
	
	public boolean compareTwoList(List<String> ApplicationList,List<String> sortedList) {
		Reporter.log("====> compareTwoList <===", true);
		boolean sortedFlag=true;
		
		if(ApplicationList.size()==sortedList.size())
		{
			for(int i=0;i<ApplicationList.size();i++) {
				if(!ApplicationList.get(i).replace(",", "").equalsIgnoreCase(sortedList.get(i))) {
					sortedFlag=false;
					break;
				}
			}
		}
		return sortedFlag;
	}
	
	public int converStringInt(String numberStr) {
		int number=-1;
		try {
			number=Integer.valueOf(numberStr);
		}catch(NumberFormatException e) {
			number=-1;
		}
		return number;
	}
	
	public List<String> removeHtmlTags(List<String> inputList) {
		List<String> ouputList=new ArrayList<String>();
		for(String input:inputList) {
			ouputList.add(removeHtmlReporter(input));
		}
		return ouputList;
	}

	public String digitConvert(String number, String StrIgnore) {
		if(number.contains(StrIgnore)) {
			number=number.replace(StrIgnore, "");
		}
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
		return number+StrIgnore;
	}
	
	public boolean checkElementEnable(WebElement element,String check,String elementName) {
		Reporter.log("===== checkElementEnable  =====", true);
		String classValue=getValueFromAttribute(element, "class", elementName);
		boolean containFoundFlag=false;
		if(classValue.contains(check))
			containFoundFlag=true;
		
		Reporter.log("containFoundFlag : "+containFoundFlag, true);
		return containFoundFlag;
	}
	
	
	public void enterTextfieldAndSelectOption(String textfieldXpath,String elementName) {
		Reporter.log("=====> enterTextfieldAndSelectOption <=====", true);
		WebElement textfieldElement=fluentWaitCodeXpath(textfieldXpath, elementName);
		String enterText=getValueFromAttribute(textfieldElement, "value", elementName);
		String dropdownXpath="//span[text()='"+enterText.trim()+"']";
		WebElement dropdown=fluentWaitCodeXpath(dropdownXpath, "dropdownClick");
		clickElement(dropdown, "dropdownClick");
	}
	
	public void checkEnterProperMsgInTextfield(String xpath,String elementName,String msg) {
		Reporter.log("=====> checkEnterProperMsgInTextfield <====", true);
		staticWait(500);
		 textfieldElement=fluentWaitCodeXpath(xpath, elementName);
		 boolean validMsgPrintOrNot=false;
		  int count=0;
		 do {
			 count++;
			 clearAndSendKey(textfieldElement,msg,elementName);
		 	enterMsgInTextfield=getValueFromAttribute(textfieldElement, "value", elementName);
		 	staticWait(200);
		 	clickElement("//span[text()='"+enterMsgInTextfield.trim()+"']", elementName);
		 	if(!msg.equalsIgnoreCase(enterMsgInTextfield)) {
		 		clearAndSendKey(textfieldElement,msg,elementName);
			validMsgPrintOrNot=true;
		 	}
		 }while(validMsgPrintOrNot==true && count<3);
	}
	
	public String [] removeHtmlAndSeparateWithChara(String htmlStr,String stringSeparate) {
		Reporter.log("====> removeHtmlAndSeparateWithChara <====", true);
		String remvoeHtmlStr=removeHtmlReporter(htmlStr);
		String [] array=separater(remvoeHtmlStr,stringSeparate);
		Reporter.log(remvoeHtmlStr, true);
		return array;
	}

	public String xpathMaker(String xpath) {
		Reporter.log(xpath, true);
		return xpath;
	}
}
