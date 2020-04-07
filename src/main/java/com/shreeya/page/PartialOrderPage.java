package com.shreeya.page;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.page.LoginPage;
import com.shreeya.page.NewOrderPage;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class PartialOrderPage extends SeleniumCoder {
	
	WebDriver driver;
	
	public void partialOrderExecution(TestDataModel model,int orderNo,LoginModel loginModel) throws InterruptedException, IOException {
		LoginPage loginObject=new LoginPage();
		NewOrderPage newObect=new NewOrderPage();
		driver=loginObject.loginExecution(loginModel);
		newObect.newOrderExecution(model, driver, orderNo);
		loginObject.logout(driver);
		driver.close();
	}
	
	public void orderDetail(WebDriver driver,TestDataModel model,int orderNo) throws InterruptedException, IOException {
		HelperCode helperCodeObj=new HelperCode();
		orderNo++;
		helperCodeObj.outputProcessor(driver, "Partial Order", orderNo, "Open", model);
	}

}
