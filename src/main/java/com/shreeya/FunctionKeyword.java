package com.shreeya;

import java.io.IOException;

import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.page.LoginPage;
import com.shreeya.page.OrderAction;
import com.shreeya.util.ConfigReader;

public class FunctionKeyword {
	
	public static String keyWord="no Keyword in FunctionKeyWord";
	

	
	public void executionWithKeyword(LoginModel loginModelObj) throws InterruptedException, IOException {
		ConfigReader configReaderObj=new ConfigReader();
		keyWord=configReaderObj.configReader("KeyWord");
		switch(KeywordStringProcess(keyWord)) {
		
		case "login":
			LoginPage loginPageObj=new LoginPage();
			loginPageObj.loginExecution(loginModelObj);
			break;
		case "order detail":
			Execution orderActionObj=new Execution();
			orderActionObj.orderDetailExecution();
			break;
			
		case "fund transfer":
			Reporter.log("fun transfer executin", true);
			break;
		}
		
	}
	
	public String KeywordStringProcess(String keywordString) {
		String keywordInLowerCase=keywordString.toLowerCase();
		return keywordInLowerCase.trim();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
