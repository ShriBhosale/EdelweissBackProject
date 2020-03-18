package com.shreeya.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.util.SeleniumCoder;

public class LoginPage extends SeleniumCoder{
	
	WebElement popupButton; 
	WebElement loginButton;
	WebElement buyAndSellButton;
	WebElement userIdTextField;
	WebElement proceedButton;
	WebElement passwordTextField;
	WebElement yobTextField;
	WebElement continueButton;
	WebElement notNowButton;
	WebDriver driver;
	WebElement popupOkButton;
	
	boolean noLoginProccess=false;
	private WebElement closePopupButton;
	private WebElement closeButton;
	private WebElement logoutOption;
	private WebElement logoutlink;
	
	
	static Logger log = Logger.getLogger(LoginPage.class.getName());
	public LoginPage() {
		
	}
	
	public WebDriver loginExecution() throws InterruptedException {
		driver=browserLaunch();
		
		//popupButton=driver.findElement(By.xpath("//button[text()='No thanks']"));
		popupButton=fluentWaitMethod(driver, "//button[text()='No thanks']");
		clickElement(popupButton);
		//loginButton=driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton=fluentWaitMethod(driver, "//span[text()='Login']");
		clickElement(loginButton);
		
		//buyAndSellButton=driver.findElement(By.xpath("//a[text()='Buy/Sell']"));
		buyAndSellButton=fluentWaitMethod(driver, "//a[text()='Buy/Sell']");
		clickElement(buyAndSellButton);
		 Thread.sleep(3000); 
		//userIdTextField=driver.findElement(By.id("userID"));
		userIdTextField=fluentWaitMethodID(driver, "userID");
		sendKey(userIdTextField,"60003800");
		//proceedButton=driver.findElement(By.xpath("//button[text()='Proceed']"));
		proceedButton=fluentWaitMethod(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		 Thread.sleep(8000); 
		//passwordTextField=driver.findElement(By.id("password"));
		passwordTextField=fluentWaitMethodID(driver, "password");
		sendKey(passwordTextField, "abc123");
		//proceedButton=driver.findElement(By.xpath("//button[text()='Proceed']"));
		proceedButton=fluentWaitMethod(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		 Thread.sleep(5000); 
		//yobTextField=driver.findElement(By.id("ans"));
		yobTextField=fluentWaitMethodID(driver, "ans");
		sendKey(yobTextField, "2000");
		//continueButton=driver.findElement(By.xpath("//button[text()='Continue']"));
		continueButton=fluentWaitMethod(driver, "//button[text()='Continue']");
		clickElement(continueButton);
		 Thread.sleep(15000); 
		if(noLoginProccess==false) {
		notNowButton=driver.findElement(By.xpath("//a[text()='Not now']"));
		clickElement(notNowButton);
			 Thread.sleep(5000); 
		popupOkButton=driver.findElement(By.xpath("//button[text()='Ok']"));
		clickElement(popupOkButton);
		}else {
			//closePopupButton=driver.findElement(By.xpath("//span[text()='×']"));
		}
		return driver;
	}
	
	public void logout(WebDriver driver) throws InterruptedException {
		Thread.sleep(3000);
		closeButton=driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[1]/a"));
		closeButton.click();
		Thread.sleep(3000);
		logoutOption=driver.findElement(By.xpath("//*[@id=\"caUser\"]/span[1]"));
		logoutOption.click();
		Thread.sleep(3000);
		logoutlink=driver.findElement(By.xpath("//a[text()=' Logout']"));
		logoutlink.click();
	}
	
	
}
