package com.shreeya.experiment;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.SeleniumCoder;

public class TestNgTag extends SeleniumCoder{
	WebDriver driver;
	
	WebElement googleSerchTextfield;
	BrowserLaunch browserLunch;
	public TestNgTag(WebDriver driver) {
		super(driver);
		this.driver=driver;
		browserLunch=new BrowserLaunch();
	}
	@BeforeMethod
	public void beforExecution() {
		Reporter.log("Before execution method is execute!!",true);
	}
	
	@Parameters("SarchItem")
	@Test
	public void shreeyaExecution(String sarchItem) throws MalformedURLException {
		Reporter.log("Shreeya execution method execute !!!",true);
		driver=browserLunch.browserLaunch("normal");
		googleSerchTextfield=fluentWaitCodeXpath(driver, "//input[@name='q']");
		
			Reporter.log("shreeya search item "+sarchItem,true);
			sendKeyClickOnDownArrow(googleSerchTextfield, sarchItem);
		
	}
	
	@Parameters("SarchItem")
	@Test
	public void apurvaExecution(String sarchItem) throws MalformedURLException {
		Reporter.log("apurva execution method execute !!!",true);
		driver=browserLunch.browserLaunch("normal");
		googleSerchTextfield=fluentWaitCodeXpath(driver, "//input[@name='q']");
		sendKeyClickOnDownArrow(googleSerchTextfield, sarchItem);
	}
	
	@Parameters("SarchItem")
	@Test
	public void pravinaExecution(String sarchItem) throws MalformedURLException {
		Reporter.log("pravina execution method execute !!!",true);
		driver=browserLunch.browserLaunch("normal");
		Reporter.log("pravina search item "+sarchItem,true);
		googleSerchTextfield=fluentWaitCodeXpath(driver, "//input[@name='q']");
		sendKeyClickOnDownArrow(googleSerchTextfield, sarchItem);
	}
	
	@AfterMethod
	public void afterExecution() {
		Reporter.log("After execution this method is execute !!",true);
	}
}
