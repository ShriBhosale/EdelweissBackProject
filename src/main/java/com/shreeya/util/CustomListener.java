package com.shreeya.util;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.MyTestLauncher;
import com.shreeya.orderdetailpages.LoginPage;

public class CustomListener extends SeleniumCoder implements ITestListener {
	WebDriver driver;
	public CustomListener(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	String screenShotPath;
	
	public void onTestFailure(ITestResult result) {
		HelperCode helperObject=new HelperCode();
		int timeStamp=Integer.valueOf(helperObject.timeStampGenerator());
		Reporter.log("<========================@@@@@@@@@@@@@ Test Get Fail @@@@@@@@@@@=====================>", true);
		ExtendReporter reporter=new ExtendReporter(MyTestLauncher.folderPath[1],"AbnormalTermination",timeStamp);
		reporter.testCreation("Abnormal Termination");
		reporter.errorFail("Abnormal Termination");
		
		try {
			reporter.addScreenshotMethod(driver, MyTestLauncher.folderPath[2], "AbnormalTermination", 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reporter.logFlush();
		driver.close();
	}
}
