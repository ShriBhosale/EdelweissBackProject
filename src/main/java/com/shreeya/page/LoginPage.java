package com.shreeya.page;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;
import com.shreeya.util.CsvReaderCode;
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
	
	WebDriver driver1;
	static Logger log = Logger.getLogger(LoginPage.class.getName());
	public LoginPage() {
			
	}
	
	public WebDriver loginExecution(CSVWriter writer) throws InterruptedException, IOException {
		driver=browserLaunch();
		
		//popupButton=driver.findElement(By.xpath("//button[text()='No thanks']"));
		popupButton=fluentWaitCodeXpath(driver, "//button[text()='No thanks']");
		clickElement(popupButton);
		//loginButton=driver.findElement(By.xpath("//span[text()='Login']"));
		loginButton=fluentWaitCodeXpath(driver, "//span[text()='Login']");
		clickElement(loginButton);
		
		//buyAndSellButton=driver.findElement(By.xpath("//a[text()='Buy/Sell']"));
		buyAndSellButton=fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']");
		clickElement(buyAndSellButton);
		 /*Thread.sleep(5000); */
		//userIdTextField=driver.findElement(By.id("userID"));
		userIdTextField=fluentWaitCodeId(driver, "userID");
		clearAndSendKey(userIdTextField,"60003800");
		//proceedButton=driver.findElement(By.xpath("//button[text()='Proceed']"));
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		// Thread.sleep(8000); 
		//passwordTextField=driver.findElement(By.id("password"));
		passwordTextField=fluentWaitCodeId(driver, "password");
		sendKey(passwordTextField, "abc123");
		//proceedButton=driver.findElement(By.xpath("//button[text()='Proceed']"));
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		 //Thread.sleep(7000); 
		//yobTextField=driver.findElement(By.id("ans"));
		yobTextField=fluentWaitCodeId(driver, "ans");
		sendKey(yobTextField, "2000");
		//continueButton=driver.findElement(By.xpath("//button[text()='Continue']"));
		continueButton=fluentWaitCodeXpath(driver,"//button[text()='Continue']");
		clickElement(continueButton);
		 //Thread.sleep(15000); 
		if(noLoginProccess==false) {
		notNowButton=fluentWaitCodeXpath(driver,"//a[text()='Not now']");
		clickElement(notNowButton);
			// Thread.sleep(5000); 
		popupOkButton=driver.findElement(By.xpath("//button[text()='Ok']"));
		clickElement(popupOkButton);
		}else {
			//closePopupButton=driver.findElement(By.xpath("//span[text()='×']"));
		}
		headerInExcel(writer);
		return driver;
	}
	
	public void logout(WebDriver driver) throws InterruptedException {
		/*Thread.sleep(3000);*/
		closeButton=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[1]/a");
		closeButton.click();
		Thread.sleep(3000);
		logoutOption=fluentWaitCodeXpath(driver,"//*[@id='caUser']/span[1]");
		
		logoutOption.click();
		
		
		/*Thread.sleep(3000);*/
		logoutlink=fluentWaitCodeXpath(driver,"//a[text()=' Logout']");
		logoutlink.click();
	}
	
	public void headerInExcel(CSVWriter writer) throws IOException {
		CsvReaderCode reader=new CsvReaderCode();
		String [] excelArray= {"Id","Action","Status","Order Action","Trading Symbol","Product Type","Order Price","Order Type","User id","Exchange","Validity","Nest Id"};
		reader.WriteFile(excelArray,writer);
	}
	
	
}
