package com.shreeya.orderdetailpage;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.orderdetailpage.LoginPage;
import com.shreeya.orderdetailpage.NewOrderPage;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class PartialOrderPage extends SeleniumCoder {
	
	WebDriver driver;
	
	public void partialOrderExecution(TestDataModel model,int orderNo,LoginModel loginModel) throws InterruptedException, IOException {
		Reporter.log("Partial Order Execution Method",true);
		LoginPage loginObject=new LoginPage();
		NewOrderPage newObect=new NewOrderPage();
		//driver=loginObject.loginExecution(loginModel);
		newObect.newOrderExecution(model, driver, orderNo);
		loginObject.logout(driver);
		driver.close();
	}
	
	public void orderDetail(WebDriver driver,TestDataModel model,int orderNo) throws InterruptedException, IOException {
		Reporter.log("Partial Order : Order Detail ", true);
		HelperCode helperCodeObj=new HelperCode();
		orderNo++;
		helperCodeObj.outputProcessor(driver, "Partial Order", orderNo, "Open", model,0);
	}

}
