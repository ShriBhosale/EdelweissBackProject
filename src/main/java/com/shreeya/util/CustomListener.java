package com.shreeya.util;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.shreeya.FunctionKeyword;
import com.shreeya.MyTestLauncher;

public class CustomListener  implements ITestListener {

	

	int timeStamp;
	
	  @Override  
	    public void onTestStart(ITestResult result) {  
	        // TODO Auto-generated method stub  
	          
	    }  
	  
	    @Override  
	    public void onTestSuccess(ITestResult result) {  
	        // TODO Auto-generated method stub  
	        System.out.println("Success of test cases and its details are : "+result.getName());  
	    }  
	  
	    @Override  
	    public void onTestFailure(ITestResult result) {  
	        
			/*
			 * System.out.println("shreeya Failure of test cases and its details are : "
			 * +result.getName()); HelperCode helperObject=new HelperCode(); try {
			 * timeStamp=Integer.valueOf(helperObject.timeStampGenerator());
			 * }catch(NumberFormatException e) {
			 * 
			 * } Reporter.
			 * log("<========================@@@@@@@@@@@@@ Test Get Fail @@@@@@@@@@@=====================>"
			 * , true); ExtendReporter reporter=new
			 * ExtendReporter(MyTestLauncher.reportFolderPath[1],"AbnormalTermination",
			 * timeStamp); reporter.testCreation("Abnormal Termination");
			 * reporter.errorFail("Abnormal Termination"); String screenShotPath = null; try
			 * { screenShotPath=reporter.addScreenshotMethod(FunctionKeyword.driver,
			 * MyTestLauncher.reportFolderPath[2], "AbnormalTermination", 0); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 * reporter.logFlush(); Reporter.log("Customer Listernet class ......",true);
			 * //Reporter.
			 * log("<b>click on following link</b><a href='html-image-hyperlink.php'><img src='"
			 * +screenShotPath+"'/></a>");
			 * screenShotPath=HelperCode.replaceString(screenShotPath);
			 * Reporter.log("<br><img src='"
			 * +screenShotPath+"' height='700' width='1000' /></br>");
			 * FunctionKeyword.driver.close();
			 */
	    }  
	  
	    @Override  
	    public void onTestSkipped(ITestResult result) {  
	        // TODO Auto-generated method stub  
	        System.out.println("Skip of test cases and its details are : "+result.getName());  
	    }  
	  
	    @Override  
	    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {  
	        // TODO Auto-generated method stub  
	        System.out.println("Failure of test cases and its details are : "+result.getName());  
	    }  
	  
	    @Override  
	    public void onStart(ITestContext context) {  
	        // TODO Auto-generated method stub  
	          
	    }  
	  
	    @Override  
	    public void onFinish(ITestContext context) {  
	        // TODO Auto-generated method stub  
	          
	    }  
}
