package com.shreeya.seeholdingspages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class SeeHoldingsExecution extends SeleniumCoder{

	 WebDriver driver;
	private WebElement seeHoldingsTab;
	
	public SeeHoldingsExecution(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public void seeHoldingsExecute(LoginModel loginModel) throws InterruptedException, IOException {
	
		  seeHoldingsTab=fluentWaitCodeXpath(driver,
		  "//*[@id=\"rightScroll\"]/div[1]/ul/li[6]/a"); clickElement(seeHoldingsTab,
		  "See Holdings Tab");
		 
		
		
	}
}
