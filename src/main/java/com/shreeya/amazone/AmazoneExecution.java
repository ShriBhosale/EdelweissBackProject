package com.shreeya.amazone;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.orderdetailpage.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class AmazoneExecution extends SeleniumCoder {
	ArrayList<AmazoneModel> model;
	AmazoneModel aModel;

	@BeforeMethod
	public void beforeExecution() {
		model = new ArrayList<AmazoneModel>();
	}

	@DataProvider(name = "getData", parallel = true)
	public Iterator<AmazoneModel> getData() {
		System.out.println("getData :: DataProvider :: ");
		AmazoneCsvReader reader = new AmazoneCsvReader();
		model = reader.LoginFileReader();
		Iterator<AmazoneModel> iterator = model.iterator();
		return iterator;
	}

	@Test
	@Parameters({"SerchItem","BuyName","PhoneNo","EmailId"})
	public void execution1(String searchItem,String buyName,String phoneNo,String emailID) throws InterruptedException {
		System.out.println("execution test............");
		LoginPage login = new LoginPage();
		WebDriver driver = login.browserLaunch("amazone");
		
		WebElement firstNameTextField=fluentWaitCodeId(driver, "//*[@id=\"u_0_q\"]");
		sendKey(firstNameTextField, searchItem,"firstNameTextField");
		WebElement sighNow = fluentWaitCodeXpath(driver, "//*[@id=\"u_0_s\"]");
		sendKey(firstNameTextField, buyName,"sighNow");
		WebElement nameTextfield=fluentWaitCodeXpath(driver, "//*[@id=\"u_0_v\"]");
		sendKey(nameTextfield,buyName,"nameTextfield");
		WebElement phoneTextfield=fluentWaitCodeXpath(driver, "//*[@id=\"u_0_10\"]");
		sendKey(phoneTextfield,phoneNo,"phoneTextfield");
		WebElement emailTextfield=fluentWaitCodeXpath(driver, "//*[@id=\"email\"]");
		sendKey(emailTextfield,emailID,"emailTextfield");

	}

	/*
	 * @Test (dataProvider = "getData") public void execution2(AmazoneModel aModel)
	 * { System.out.println("execution test............");
	 * 
	 * AmazoneCsvReader reader=new AmazoneCsvReader();
	 * model=reader.LoginFileReader(); Iterator<AmazoneModel> iterator=
	 * model.iterator(); aModel=iterator.next();
	 * 
	 * System.out.
	 * println("execution 2 =====================================================> "
	 * +aModel.toString()); }
	 */
}
