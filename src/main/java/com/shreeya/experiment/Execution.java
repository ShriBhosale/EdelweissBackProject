package com.shreeya.experiment;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.shreeya.util.SeleniumCoder;

public class Execution {
	
	
	List<WebElement> webElemets=null;
	Report log;
	@BeforeTest
	public void beforeTest() {
		 log=new Report();
		log.printLog("Before test  will be execute", true);
	}
	
	@BeforeMethod
	public void beforeTestMethod() {
		log.printLog("Before test method will be execute", true);
		
	}
	@Test
	public  void testMethod() throws IOException, InterruptedException {
		log.printLog("shreeya",false);
	}
	
	@AfterMethod
	public void AfterMethodTest() {
		log.printLog("After test method", true);
	}
	@AfterTest
	public void afterTest() {
		log.printLog("After test  will be execute", true);
	}
}
