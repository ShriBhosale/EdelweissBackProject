package com.shreeya.seeholdingspages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class SeeHoldingsExecution extends SeleniumCoder{

	WebDriver driver;
	private WebElement seeHoldingsTab;
	
	public void seeHoldingsExecute(LoginModel loginModel) throws InterruptedException, IOException {
		LoginPage loginPage=new LoginPage();
		driver=loginPage.loginExecution("normal", loginModel);
		seeHoldingsTab=fluentWaitCodeXpath(driver, "//*[@id=\"rightScroll\"]/div[1]/ul/li[6]/a");
		clickElement(seeHoldingsTab, "See Holdings Tab");
	}
}
