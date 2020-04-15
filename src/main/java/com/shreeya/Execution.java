package com.shreeya;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.page.LoginPage;
import com.shreeya.page.OrderAction;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.HelperCode;

public class Execution {
	
	LoginPage login;
	WebDriver driver;
	OrderAction orderActioObj;
	HelperCode helperObject;
	TestDataModel testDataObject;
	
	public static String folderPath[]=null;
	public static ApacheCode apacheCodeObj;
	
	
	
	@BeforeTest
	public void executionBefore() throws IOException {
		login=new LoginPage();
		orderActioObj=new OrderAction();
		testDataObject=new TestDataModel();
		helperObject=new HelperCode();
		FolderStructure folderCreationObj=new FolderStructure();
		folderPath=folderCreationObj.reportFolderCreator(1);
		apacheCodeObj=new ApacheCode(folderPath[0]);
		
		//apacheCodeObj.outputFileWriterHeader(folderPath[0]);
		 
	}
	
	@BeforeMethod
	public void beforeTest() {
		System.out.println("Before Test case...");
	}

	@Parameters({"Reference","UserId","Pwd","Yob","StartNo","EndNo","ExecutionType"})
	@Test
	public void execution(String referenceNo,String userId,String pwd,String yob,String startNo,String endNo,String executionType) {
		System.out.println("*****************<<<<<<<<<<<<<<<Your Enter into TestCase>>>>>>>>>>>>>>>*********************");
		System.out.println("Before Iterator "+referenceNo);
		//Iterator<LoginModel> loginIteratior =  MyTestLauncher.loginData.iterator();
		System.out.println("After Iterator");
		//System.out.println(loginIteratior.toString());
		
		/* while(loginIteratior.hasNext()) { */
			
		/* LoginModel loginModelObj= loginIteratior.next(); */
		
			LoginModel loginModelObj =new LoginModel(referenceNo, userId, pwd, yob, startNo, endNo, executionType);
			System.out.println("Login Data ====> "+loginModelObj.toString());
			if(referenceNo.equals(loginModelObj.getReferNo())) {
				try {
					driver = login.loginExecution(loginModelObj);
					driver=orderActioObj.orderActionStart(driver,loginModelObj);
					terminateExecution(driver);
				} catch (InterruptedException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) { // TODO Auto-generated catch
					System.out.println(e);
				}
				/* } */
			
		}	
		System.out.println("Outside form execution............");
	}
	
	public void terminateExecution(WebDriver driver) throws InterruptedException, IOException {
		if(driver != null) {
		helperObject.outputProcessor(driver, "newOrder", 0, "Terminate", testDataObject,0);
		login.logout(driver);
		driver.close();
		}else {
			
		}
	}
	@AfterMethod
	public void testAfter() {
		System.out.println("End test case execution");
	}
	
	@AfterTest
	public void endExecution() throws IOException {
		//apacheCodeObj.closeExcelWriting();
		//apacheCodeObj.outputExcelFileClose(folderPath[0]);
	}
}
