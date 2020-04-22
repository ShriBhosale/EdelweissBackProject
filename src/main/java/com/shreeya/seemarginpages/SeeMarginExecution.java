package com.shreeya.seemarginpages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class SeeMarginExecution extends SeleniumCoder {
	WebDriver driver;
	private WebElement seeMarginTab;
	
	public void seeMarginExecute(LoginModel loginModel) throws InterruptedException, IOException {
		LoginPage loginPage=new LoginPage();
		
		 // driver=loginPage.loginExecution("normal", loginModel);
		  seeMarginTab=fluentWaitCodeXpath(driver, "//a[text()='See Margin']");
		  clickElement(seeMarginTab, "See Margin Tab");
		 
		Reporter.log("See Margin Execution driver =====> "+loginPage.getDriver(), true);
	}
}
