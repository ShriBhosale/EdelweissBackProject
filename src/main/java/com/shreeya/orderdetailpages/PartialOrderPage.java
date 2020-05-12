package com.shreeya.orderdetailpages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.orderdetailpages.NewOrderPage;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class PartialOrderPage extends SeleniumCoder {
	
	WebDriver driver;
	
	public PartialOrderPage(WebDriver driver){
		super(driver);
		this.driver=driver;
	}
	
	public void partialOrderExecution(TestDataModel model,int orderNo,LoginModel loginModel) throws InterruptedException, IOException {
		Reporter.log("Partial Order Execution Method",true);
		LoginPage loginObject=new LoginPage(driver);
		NewOrderPage newObect=new NewOrderPage(driver);
	//	loginObject.loginExecution("Partial Order",loginModel);
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
