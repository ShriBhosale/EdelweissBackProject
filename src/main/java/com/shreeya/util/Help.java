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
	
	public String removeComma(String number) {
		String ans="";
		if(number.contains(",")) {
			char [] ditgitArray=number.toCharArray();
			for(char c:ditgitArray) {
				if(c==',')
					continue;
				else
				ans=ans+c;	
			}
		}
		System.out.println("Ans : "+ans);
		return ans;
	}
	
	public String ditgitConverter(String number) {
		String ans="";
		if(!number.contains(".")) {
			ans=number+".00";
		}
		return ans;
	}
	public static void main(String[] args) {
		Help help=new Help();
		String price="2,456.00";
		String sheetPrice="2456";
		if(help.ditgitConverter(sheetPrice).equalsIgnoreCase(help.removeComma(price))) {
			System.out.println("Price match");
		}else {
			System.out.println("Does not match...");
		}
	}
}
