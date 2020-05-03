package com.shreeya;

import java.io.IOException;
import java.util.ListIterator;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.fundtransferpages.FundTransferExecution;
import com.shreeya.model.LoginModel;
import com.shreeya.model.MasterTestModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.mypositionspages.MyPositionsExecution;
import com.shreeya.orderdetailpages.LoginExecution;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.orderdetailpages.OrderAction;
import com.shreeya.seeholdingspages.SeeHoldingsExecution;
import com.shreeya.seemarginpages.SeeMarginExecution;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.CustomListener;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.HelperCode;

@Listeners(CustomListener.class)
public class FunctionKeyword {

	public static String keyWord = "no Keyword in FunctionKeyWord";
	LoginPage login;
	public static WebDriver driver;
	OrderAction orderActioObj;
	HelperCode helperObject;
	TestDataModel testDataObject;
	MasterTestModel masterTestmodel;
	ExtendReporter htmlReport;
	BrowserLaunch browserLunch;
	private String step;
	
	

	
	public static ApacheCode apacheCodeObj;
	public static FolderStructure folderCreationObj;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		Reporter.log("Before suit", true);
		htmlReport=new ExtendReporter();
	}

	@BeforeTest
	public void executionBefore() throws IOException {

		Reporter.log("Execution Before ", true);
		 browserLunch = new BrowserLaunch();
		driver = browserLunch.browserLaunch("Normal");
		login = new LoginPage(driver);
		orderActioObj = new OrderAction(driver);
		testDataObject = new TestDataModel();
		helperObject = new HelperCode();

		
		apacheCodeObj = new ApacheCode();

		// apacheCodeObj.outputFileWriterHeader(folderPath[0]);

	}

	@BeforeMethod
	public void beforeTest() {
		Reporter.log("Before Test case...", true);
		Reporter.log("BeforeMethod", true);
	}

	@Parameters({ "Reference", "UserId", "Pwd", "Yob", "StartNo", "EndNo", "Module" })
	@Test
	public void executionWithKeyword(String referenceNo, String userId, String pwd, String yob, String startNo,
			String endNo, String module) throws InterruptedException, IOException {
		
		CsvReaderCode code = new CsvReaderCode();
		Reporter.log("<b><==================== Function KeyWord : executionWithKeyword ================></b>", true);
		Reporter.log("Before Iterator " + referenceNo);

		Reporter.log("After Iterator", true);

		LoginModel loginModelObj = new LoginModel(referenceNo, userId, pwd, yob, startNo, endNo, module);
		Reporter.log("Login Data ====> " + loginModelObj.toString(), true);

		Reporter.log("KeyWord before process  ====> " + module, true);

		ListIterator<String> csvMasterTestModelIterator = code.masterTestDataProvider(module);
		int count = 0;
		
		while (csvMasterTestModelIterator.hasNext()) {
			step = csvMasterTestModelIterator.next();
			System.out.println("count : " + count++);

			Reporter.log("Step ===> " + step, true);

			switch (KeywordStringProcess(step)) {

			case "login":
				LoginExecution loginPageObj = new LoginExecution(driver);
				Reporter.log("Login functionality", true);
				loginPageObj.loginExecution("normal", loginModelObj);
				break;

			case "orderdetail":
				OrderAction orderActionObj = new OrderAction(driver);
				Reporter.log("Order detail functionality", true);
				orderActionObj.orderActionStart(loginModelObj);
				break;

			case "fundtransfer":
				FundTransferExecution fundTransferObj = new FundTransferExecution(driver);
				fundTransferObj.fundTransferExecute();
				Reporter.log("fun transfer executin", true);
				break;
			case "mypositions":
				MyPositionsExecution myPositionObj = new MyPositionsExecution(driver);
				myPositionObj.myPositionsExecute(loginModelObj);
				Reporter.log("My Position Module", true);
				break;
			case "seemargin":
				SeeMarginExecution seeMarginObj = new SeeMarginExecution(driver);
				seeMarginObj.seeMarginExecute(loginModelObj);
				Reporter.log("See Margin Module", true);
				break;
			case "seeholdings":
				SeeHoldingsExecution seeHoldingsObj = new SeeHoldingsExecution(driver);
				seeHoldingsObj.seeHoldingsExecute(loginModelObj);
				Reporter.log("See Holdings Module", true);
				break;

			case "logout":
				terminateExecution(module, driver,referenceNo);
				break;
			/*
			 * default: Reporter.
			 * log("Please Enter follow mentioned keyword : \nlogin\norderdetail\nfundtransfer\nmypositions\nseemargin\nseeholdings\nlogout"
			 * , true);
			 */
			}

		}
		
		

	}

	public String KeywordStringProcess(String keywordString) {
		String keywordInLowerCase = keywordString.toLowerCase();
		Reporter.log("keyword =====> " + keywordInLowerCase.trim(), true);
		if (keywordInLowerCase.contains(" "))
			keywordInLowerCase = keywordInLowerCase.replace(" ", "");
		return keywordInLowerCase.trim();
	}

	public void terminateExecution(String module, WebDriver driver,String referNo) throws InterruptedException, IOException {
		Reporter.log("FunctionKeyword : TerminateExecution",true);
		if (driver != null) {
			if (!(module.equalsIgnoreCase("orderdetail")||module.equalsIgnoreCase("fundtransfer")||module.equalsIgnoreCase("login"))) {
				ExtendReporter reporter = new ExtendReporter();
				reporter.reporter(driver, module, MyTestLauncher.reportFolderPath,referNo);
				helperObject.outputProcessor(driver, "newOrder", 0, "Terminate", testDataObject, 0);
			}
			if(!module.equalsIgnoreCase("login"))
			login.logout(driver);
			
			Reporter.log("Execution Terminate.... :)", true);
		} else {

		}
	}

	@AfterMethod
	public void testAfter(ITestResult result) throws IOException {
		Reporter.log("End test case execution", true);
		browserLunch.driverClose();
	}

	@AfterTest
	public void endExecution() throws IOException {
		// apacheCodeObj.closeExcelWriting();
		
		Reporter.log("Folder Path ====> " + MyTestLauncher.reportFolderPath[0], true);
		
		//apacheCodeObj.outputExcelFileClose(folderPath[0]);

	}

	@AfterSuite
	public void afterSuite() throws IOException {
		/*
		 * Process pro =
		 * Runtime.getRuntime().exec("cmd.exe /k netstat -ano|findstr 5554");
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(pro.getInputStream())); String s = br.readLine();
		 * 
		 * String[] output = s.split("    ");
		 * 
		 * Runtime.getRuntime().exec("cmd.exe /k taskkill /f /pid " +
		 * output[output.length - 1]);
		 */
	}

}
