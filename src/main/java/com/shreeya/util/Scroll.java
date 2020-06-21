package com.shreeya.util;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class Scroll extends SeleniumCoder{

	WebDriver driver;
	JavascriptExecutor jse;
	public Scroll(WebDriver driver) {
		super(driver);
		this.driver=driver;
		jse=(JavascriptExecutor)driver;
	}

	public void scrollToSpecificLocation(int x,int y) {
		Reporter.log("scrollToSpecificLocation ", true);
		jse.executeScript("window.scrollBy("+x+","+y+")", "");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void scrollAndPointToElement(WebElement element) {
		jse.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
}
