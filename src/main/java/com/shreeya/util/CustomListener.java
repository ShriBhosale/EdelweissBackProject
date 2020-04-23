package com.shreeya.util;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.orderdetailpages.LoginPage;

public class CustomListener extends SeleniumCoder implements ITestListener {
String screenShotPath;
	public void onTestFailure(ITestResult result) {
		HelperCode helperObject=new HelperCode();
		int timeStamp=Integer.valueOf(helperObject.timeStampGenerator());
		Reporter.log("<========================@@@@@@@@@@@@@ Test Get Fail @@@@@@@@@@@=====================>", true);
		ExtendReporter reporter=new ExtendReporter(FunctionKeyword.folderPath[1],"AbnormalTermination",timeStamp);
		reporter.testCreation("Abnormal Termination");
		reporter.errorFail("Abnormal Termination");
		
		try {
			reporter.addScreenshotMethod(LoginPage.driver, FunctionKeyword.folderPath[2], "AbnormalTermination", 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reporter.logFlush();
		driver.close();
	}
}
