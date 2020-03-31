package com.shreeya.page;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.shreeya.model.TestDataModel;
import com.shreeya.page.LoginPage;
import com.shreeya.page.NewOrderPage;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class PartialOrderPage extends SeleniumCoder {
	
	WebDriver driver;
	
	public void partialOrderExecution(String scenario,TestDataModel model,int orderNo) throws InterruptedException, IOException {
		LoginPage loginObject=new LoginPage();
		NewOrderPage newObect=new NewOrderPage();
		driver=loginObject.loginExecution(scenario);
		newObect.newOrderExecution(model, driver, orderNo);
		loginObject.logout(driver);
		driver.close();
	}
	
	public void orderDetail(WebDriver driver,TestDataModel model,int orderNo) throws InterruptedException, IOException {
		HelperCode helperCodeObj=new HelperCode();
		helperCodeObj.outputProcessor(driver, "Partial Order", orderNo, "Open", model);
	}

}
