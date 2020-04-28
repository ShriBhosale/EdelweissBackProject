package com.shreeya;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ListIterator;

import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.WebDriver;
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
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.orderdetailpages.OrderAction;
import com.shreeya.seeholdingspages.SeeHoldingsExecution;
import com.shreeya.seemarginpages.SeeMarginExecution;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.BrowserLunch;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.CustomListener;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.HelperCode;

public class FunctionKeyword {

	public static String keyWord = "no Keyword in FunctionKeyWord";

	private static Hub hub;
	LoginPage login;
	WebDriver driver;
	OrderAction orderActioObj;
	HelperCode helperObject;
	TestDataModel testDataObject;
	MasterTestModel masterTestmodel;

	private String step;

	public static String folderPath[] = null;
	public static ApacheCode apacheCodeObj;

	public static Hub getHub() {
		return hub;
	}

	@BeforeSuite
	public void beforeSuite() throws IOException {
		GridHubConfiguration config = new GridHubConfiguration();
		config.host = "192.168.0.104";
		hub = new Hub(config);
		hub.start();
		System.out.println("java -jar selenium-server-standalone-3.141.59.jar -role node -host "
				+ hub.getConfiguration().host + " -port 5554 -hub http://" + hub.getConfiguration().host + ":"
				+ hub.getConfiguration().port + "/grid/register \n pause \n");
		Process pro = Runtime.getRuntime()
				.exec("cmd /c java -jar selenium-server-standalone-3.141.59.jar -role node -host "
						+ hub.getConfiguration().host + " -port 5554 -hub http://" + hub.getConfiguration().host + ":"
						+ hub.getConfiguration().port + "/grid/register", null,
						new File(System.getProperty("user.dir") + "\\grid"));
		BufferedReader br = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
		String s = "";
		System.out.println(System.getProperty("user.dir"));
		while ((s = br.readLine()) != null) {
			System.out.println(s);
			if (s.contains("The node is registered to the hub and ready to use"))
				break;
		}
	}

	@BeforeTest
	public void executionBefore() throws IOException {

		Reporter.log("Execution Before ", true);
		BrowserLunch browserLunch = new BrowserLunch();
		driver = browserLunch.browserLaunch("Normal");
		login = new LoginPage(driver);
		orderActioObj = new OrderAction(driver);
		testDataObject = new TestDataModel();
		helperObject = new HelperCode();

		FolderStructure folderCreationObj = new FolderStructure();
		Reporter.log(
				"Above folder Creation============================================================================&^*&^&*^&8686868688>>>>>>");
		folderPath = folderCreationObj.reportFolderCreator();
		apacheCodeObj = new ApacheCode(folderPath[0]);

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
		Thread.sleep(2000);
		CsvReaderCode code = new CsvReaderCode();
		Reporter.log("<==================== Function KeyWord : executionWithKeyword ================>", true);
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
				LoginPage loginPageObj = new LoginPage(driver);
				Reporter.log("Login functionality", true);
				loginPageObj.loginExecution("normal", loginModelObj);
				break;

			/*
			 * case "orderdetail": OrderAction orderActionObj = new OrderAction(driver);
			 * Reporter.log("Order detail functionality", true);
			 * orderActionObj.orderActionStart(loginModelObj); break;
			 * 
			 * case "fundtransfer": FundTransferExecution fundTransferObj = new
			 * FundTransferExecution(driver);
			 * fundTransferObj.fundTransferExecute(loginModelObj);
			 * Reporter.log("fun transfer executin", true); break; case "mypositions":
			 * MyPositionsExecution myPositionObj = new MyPositionsExecution(driver);
			 * myPositionObj.myPositionsExecute(loginModelObj);
			 * Reporter.log("My Position Module", true); break; case "seemargin":
			 * SeeMarginExecution seeMarginObj = new SeeMarginExecution(driver);
			 * seeMarginObj.seeMarginExecute(loginModelObj);
			 * Reporter.log("See Margin Module", true); break; case "seeholdings":
			 * SeeHoldingsExecution seeHoldingsObj = new SeeHoldingsExecution(driver);
			 * seeHoldingsObj.seeHoldingsExecute(loginModelObj);
			 * Reporter.log("See Holdings Module", true); break;
			 */

			case "logout":
				terminateExecution(masterTestmodel.getKeyword(), driver);
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

	public void terminateExecution(String module, WebDriver driver) throws InterruptedException, IOException {

		if (driver != null) {
			if (!module.equalsIgnoreCase("orderdetail")) {
				ExtendReporter reporter = new ExtendReporter();
				reporter.reporter(driver, module, folderPath);
				helperObject.outputProcessor(driver, "newOrder", 0, "Terminate", testDataObject, 0);
			}
			login.logout(driver);
			driver.close();
			Reporter.log("Execution Terminate.... :)", true);
		} else {

		}
	}

	@AfterMethod
	public void testAfter() {
		Reporter.log("End test case execution", true);
	}

	@AfterTest
	public void endExecution() throws IOException {
		// apacheCodeObj.closeExcelWriting();

		Reporter.log("Folder Path ====> " + folderPath[0], true);
		apacheCodeObj.outputExcelFileClose(folderPath[0]);

	}

	@AfterSuite
	public void afterSuite() throws IOException {
		hub.stop();
		Process pro = Runtime.getRuntime().exec("cmd.exe /k netstat -ano|findstr 5554");
		BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		String s = br.readLine();

		String[] output = s.split("    ");

		Runtime.getRuntime().exec("cmd.exe /k taskkill /f /pid " + output[output.length - 1]);

	}

}
