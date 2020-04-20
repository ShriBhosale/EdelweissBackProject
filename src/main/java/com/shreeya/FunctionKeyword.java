package com.shreeya;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.orderdetailpage.LoginPage;
import com.shreeya.orderdetailpage.OrderAction;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.HelperCode;

public class FunctionKeyword {
	
	public static String keyWord="no Keyword in FunctionKeyWord";
	

	LoginPage login;
	WebDriver driver;
	OrderAction orderActioObj;
	HelperCode helperObject;
	TestDataModel testDataObject;
	
	public static String folderPath[]=null;
	public static ApacheCode apacheCodeObj;
	
	
	
	@BeforeTest
	public void executionBefore() throws IOException {
		Reporter.log("Execution Before ", true);
		login=new LoginPage();
		orderActioObj=new OrderAction();
		testDataObject=new TestDataModel();
		helperObject=new HelperCode();
		
		FolderStructure folderCreationObj=new FolderStructure();
		Reporter.log("Above folder Creation============================================================================&^*&^&*^&8686868688>>>>>>");
		folderPath=folderCreationObj.reportFolderCreator();
		apacheCodeObj=new ApacheCode(folderPath[0]);
		
		//apacheCodeObj.outputFileWriterHeader(folderPath[0]);
		 
	}
	
	@BeforeMethod
	public void beforeTest() {
		Reporter.log("Before Test case...",true);
		Reporter.log("BeforeMethod",true);
	}

	@Parameters({"Reference","UserId","Pwd","Yob","StartNo","EndNo","ExecutionType"})
	@Test
	public void executionWithKeyword(String referenceNo,String userId,String pwd,String yob,String startNo,String endNo,String executionType) throws InterruptedException, IOException {
		Reporter.log("<==================== Function KeyWord : executionWithKeyword ================>",true);
		Reporter.log("Before Iterator "+referenceNo);
		
		Reporter.log("After Iterator",true);
		
		
			LoginModel loginModelObj =new LoginModel(referenceNo, userId, pwd, yob, startNo, endNo, executionType);
			Reporter.log("Login Data ====> "+loginModelObj.toString(),true);
			
		ConfigReader configReaderObj=new ConfigReader();
		keyWord=configReaderObj.configReader("KeyWord");
		Reporter.log("KeyWord before process  ====> "+keyWord,true);
		switch(KeywordStringProcess(keyWord)) {
		
		case "login":
			LoginPage loginPageObj=new LoginPage();
			Reporter.log("Login functionality", true);
			loginPageObj.loginExecution(loginModelObj);
			break;
		case "orderdetail":
			OrderAction orderActionObj=new OrderAction();
			Reporter.log("Order detail functionality", true);
			orderActionObj.orderActionStart(loginModelObj);
			break;
			
		case "fundtransfer":
			Reporter.log("fun transfer executin", true);
			break;
		}
		terminateExecution();
	}
	
	public String KeywordStringProcess(String keywordString) {
		String keywordInLowerCase=keywordString.toLowerCase();
		Reporter.log("keyword =====> "+keywordInLowerCase.trim(), true);
		return keywordInLowerCase.trim();
	}
	
	public void terminateExecution() throws InterruptedException, IOException {
		WebDriver driver=LoginPage.getDriver();
		if(driver != null) {
		
		helperObject.outputProcessor(driver, "newOrder", 0, "Terminate", testDataObject,0);
		login.logout(driver);
		driver.close();
		Reporter.log("Execution Terminate.... :)",true);	
		}else {
			
		}
	}
	@AfterMethod
	public void testAfter() {
		Reporter.log("End test case execution",true);
	}
	
	@AfterTest
	public void endExecution() throws IOException {
		//apacheCodeObj.closeExcelWriting();
		Reporter.log("Folder Path ====> "+folderPath[0], true);
		apacheCodeObj.outputExcelFileClose(folderPath[0]);
	}


	

}
