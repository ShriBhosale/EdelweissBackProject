package com.shreeya;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ReporterDemo {

	@Test
	public void abc() {
		Reporter.log("Hello shreeya");
		Reporter.log("how you fill now...");
		Reporter.log("Hello shreeya");
		Reporter.log("how you fill now...");
		Reporter.log("Hello shreeya");
		Reporter.log("how you fill now...");
	}

	@Test
	public void abc1() {
		Reporter.log("Hello priyanka");
		Reporter.log("how you fill now...");
		Reporter.log("Hello priyanka");
		Reporter.log("how you fill now...");
		Reporter.log("Hello priyanka");
		Reporter.log("how you fill now...");
	}
	
	
}
