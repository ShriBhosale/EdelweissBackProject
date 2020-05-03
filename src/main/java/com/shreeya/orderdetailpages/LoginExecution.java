package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;
import com.shreeya.model.LoginModel;
import com.shreeya.model.LoginTestModel;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class LoginExecution extends SeleniumCoder{

	LoginPage loginPage;
	WebDriver driver;
	LoginTestModel loginTestModel=null;
	
	public LoginExecution(WebDriver driver) {
		super(driver);
		this.driver=driver;
		loginPage=new LoginPage(driver);
	}
	
	public void loginExecution(String scenario, LoginModel loginModelObject) throws InterruptedException, IOException {

		// driver=browserLaunch(scenario);
		
		if (!loginModelObject.getModule().equalsIgnoreCase("login")) {
			loginPage.loginCodeExecution(scenario, loginModelObject);
		} else {
			loginRegressionExecution(loginModelObject);
		}
		// return driver;
	
	}
	
	public void loginRegressionExecution(LoginModel loginModelObject) throws IOException, InterruptedException {

		ExtendReporter extend = new ExtendReporter(MyTestLauncher.reportFolderPath[1], "LoginRegression", 0);
		CsvReaderCode csvReader = new CsvReaderCode();
		Iterator<LoginTestModel> csvLoginTestIterator = csvReader.loginTestDataProvider();
		while (csvLoginTestIterator.hasNext()) {
			try {
			 loginTestModel = csvLoginTestIterator.next();
			Reporter.log(loginTestModel.toString(), true);
			loginPage.loginCodeExecution(driver, loginTestModel, extend);
			}catch(NullPointerException e) {
				extend.loginReport(driver, extend, loginTestModel, "Error",elementNameError);
				loginPage.pageRefresh();
				continue;
			}catch(NoSuchElementException e1) {
				extend.loginReport(driver, extend, loginTestModel, "Error",elementNameError);
				loginPage.pageRefresh();
				continue;
			}
		}
		driver.close();
		Reporter.log("Driver close", true);
		extend.logFlush();
	
	}
	
	public void loginStepExecution(String scenario, LoginModel loginModelObject) throws InterruptedException, IOException {
		try {
		loginPage.loginCodeExecution(scenario, loginModelObject);
		}catch(NullPointerException e) {
			
			loginPage.pageRefresh();
		}
	}
}
