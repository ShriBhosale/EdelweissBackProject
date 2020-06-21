package com.shreeya.experiment;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.SeleniumCoder;

public class Report extends SeleniumCoder{
	
	static LoginPage loginPage;
	private static WebElement userId;
	WebDriver driver;
	
	public Report(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		BrowserLaunch launch=new BrowserLaunch();
		WebDriver driver=launch.browserLaunch("normal");
		Report r=new  Report(driver);
		try {
		loginPage=new LoginPage(driver);
		loginPage.clickOnLoginButton(driver);
		
		userId=driver.findElement(By.id("userID"));
		userId.sendKeys("OM10");
		driver.navigate().refresh();
		loginPage.clickOnLoginButton(driver);
		userId.sendKeys("OM10");
		}catch(NoSuchElementException e) {
			String exceptionName=e.toString();
			StackTraceElement [] locaString=e.getStackTrace();
			/*
			 * for(StackTraceElement location:locaString) {
			 * System.out.println("Location : "+location); }
			 */
			
			System.out.println("ElementName : "+exceptionName+"\nException Location : "+locaString[locaString.length-1]);
			
		}
	}
}
