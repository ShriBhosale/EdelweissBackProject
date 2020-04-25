package com.shreeya;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.fundtransferpages.FundTransferExecution;
import com.shreeya.model.LoginModel;
import com.shreeya.model.MasterTestModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.mypositionspages.MyPositionsExecution;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.orderdetailpages.OrderAction;
import com.shreeya.seeholdingspages.SeeHoldingsExecution;
import com.shreeya.seemarginpages.SeeMarginExecution;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.CustomListener;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.HelperCode;

@Listeners(CustomListener.class)
public class FunctionKeyword {
	
	public static String keyWord="no Keyword in FunctionKeyWord";
	

	LoginPage login;
	WebDriver driver;
	OrderAction orderActioObj;
	HelperCode helperObject;
	TestDataModel testDataObject;
	MasterTestModel masterTestmodel;
	
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

	@Parameters({"Reference","UserId","Pwd","Yob","StartNo","EndNo","Module"})
	@Test
	public void executionWithKeyword(String referenceNo,String userId,String pwd,String yob,String startNo,String endNo,String module) throws InterruptedException, IOException {
		Thread.sleep(2000);
		CsvReaderCode code=new CsvReaderCode();
		Reporter.log("<==================== Function KeyWord : executionWithKeyword ================>",true);
		Reporter.log("Before Iterator "+referenceNo);
		
		Reporter.log("After Iterator",true);
		
		
			LoginModel loginModelObj =new LoginModel(referenceNo, userId, pwd, yob, startNo, endNo, module);
			Reporter.log("Login Data ====> "+loginModelObj.toString(),true);
	
		Reporter.log("KeyWord before process  ====> "+module,true);
		
		Iterator<MasterTestModel> csvMasterTestModelIterator=code.masterTestDataProvider();
		while(csvMasterTestModelIterator.hasNext()) {
			masterTestmodel=csvMasterTestModelIterator.next();
			if(masterTestmodel.getKeyword().equalsIgnoreCase(loginModelObj.getModule())) {
				Reporter.log("Steps =================================================================================>>> "+masterTestmodel.getSteps(), true);
			
		
		
		
		switch(KeywordStringProcess(masterTestmodel.getSteps())) {
		
		case "login":
			LoginPage loginPageObj=new LoginPage();
			Reporter.log("Login functionality", true);
			loginPageObj.loginExecution("normal",loginModelObj);
			break;
		case "orderdetail":
			OrderAction orderActionObj=new OrderAction();
			Reporter.log("Order detail functionality", true);
			orderActionObj.orderActionStart(loginModelObj);
			break;
			
		case "fundtransfer":
			FundTransferExecution fundTransferObj=new FundTransferExecution();
			fundTransferObj.fundTransferExecute(loginModelObj);
			Reporter.log("fun transfer executin", true);
			break;
		case "mypositions":
			MyPositionsExecution myPositionObj=new MyPositionsExecution();
			myPositionObj.myPositionsExecute(loginModelObj);
			Reporter.log("My Position Module", true);
			break;
		case "seemargin":
			SeeMarginExecution seeMarginObj=new SeeMarginExecution();
			seeMarginObj.seeMarginExecute(loginModelObj);
			Reporter.log("See Margin Module",true);
			break;
		case "seeholdings":
			SeeHoldingsExecution seeHoldingsObj=new SeeHoldingsExecution();
			seeHoldingsObj.seeHoldingsExecute(loginModelObj);
			Reporter.log("See Holdings Module", true);
				break;
		case "logout" :
			terminateExecution(masterTestmodel.getKeyword());
			break;
		/*
		 * default: Reporter.
		 * log("Please Enter follow mentioned keyword : \nlogin\norderdetail\nfundtransfer\nmypositions\nseemargin\nseeholdings\nlogout"
		 * , true);
		 */
		}
			
		
			}
		}
		
	}
	
	public String KeywordStringProcess(String keywordString) {
		String keywordInLowerCase=keywordString.toLowerCase();
		Reporter.log("keyword =====> "+keywordInLowerCase.trim(), true);
		if(keywordInLowerCase.contains(" "))
			keywordInLowerCase=keywordInLowerCase.replace(" ", "");
		return keywordInLowerCase.trim();
	}
	
	public void terminateExecution(String module) throws InterruptedException, IOException {
		WebDriver driver=LoginPage.getDriver();
		if(driver != null) {
		if(!module.equalsIgnoreCase("orderdetail")) {
		ExtendReporter reporter=new ExtendReporter();
		reporter.reporter(driver,module,folderPath);
		helperObject.outputProcessor(driver, "newOrder", 0, "Terminate", testDataObject,0);
		}
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
