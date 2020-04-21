package com.shreeya.mypositionspages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class MyPositionsExecution extends SeleniumCoder {

	WebDriver driver;
	private WebElement myPositionsTab;
	
	public void myPositionsExecute(LoginModel loginModel) throws InterruptedException, IOException {
		LoginPage loginPage=new LoginPage();
		driver=loginPage.loginExecution("normal", loginModel);
		myPositionsTab=fluentWaitCodeXpath(driver, "//*[@id=\"rightScroll\"]/div[1]/ul/li[3]/a");
		clickElement(myPositionsTab, "My Position Tab");
	}
}
