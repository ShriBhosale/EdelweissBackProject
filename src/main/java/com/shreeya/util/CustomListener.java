package com.shreeya.util;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.orderdetailpages.LoginPage;

public class CustomListener extends SeleniumCoder{
	WebDriver driver;
	public CustomListener(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	String screenShotPath;
	
	public void onTestFailure() {
		
		HelperCode helperObject=new HelperCode();
		int timeStamp=Integer.valueOf(helperObject.timeStampGenerator());
		Reporter.log("<========================@@@@@@@@@@@@@ Test Get Fail @@@@@@@@@@@=====================>", true);
		ExtendReporter reporter=new ExtendReporter(FunctionKeyword.folderPath[1],"AbnormalTermination",timeStamp);
		reporter.testCreation("Abnormal Termination");
		reporter.errorFail("Abnormal Termination");
		
		try {
			reporter.addScreenshotMethod(driver, FunctionKeyword.folderPath[2], "AbnormalTermination", 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reporter.logFlush();
		driver.close();
	}
	
	
	public void testResultChecker() {
		
	}
	
	 public void getResult(ITestResult result) throws IOException
	 {
		 if(result.getStatus()==ITestResult.SUCCESS)
		 {
			 
		 }
		 else if( result.getStatus()==ITestResult.SKIP)
		 {
			
			 
		 }
		 else if( result.getStatus()==ITestResult.FAILURE)
		 {
			 onTestFailure();
		 }
		
	 }
}
