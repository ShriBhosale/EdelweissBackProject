package com.shreeya.util;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.shreeya.page.LoginPage;

public class SeleniumCoder {

	static Logger log = Logger.getLogger(SeleniumCoder.class.getName());
	WebDriver driver=null;
	public SeleniumCoder() {
		
	}
	
	public WebDriver browserLaunch() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver","E:\\EdelweissProject\\chromedriver.exe");
		WebDriver driver=new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		driver.get("https://ewuat.edelbusiness.in");
		
		log.info("Browser launch successfully.................");
		return driver;
	}
	
	public void sendKey(WebElement element,String msg) throws InterruptedException {
		/* Thread.sleep(2000); */
		try {
		element.sendKeys(msg);
		}catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	
	public void clickElement(WebElement element) throws InterruptedException {
		
		if(element.isEnabled()==true) {
			element.click();
			System.out.println("Click "+element);
		}else {
			System.out.println("no present");
		}
		
		
	}
	
	public void downErrorKeyEnter(WebElement element) {
		
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.ENTER);
	}
	
	public void clearAndSendKey(WebElement element,String msg) {
		try {
		element.clear();
		element.sendKeys(msg);
		}catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	
	public WebElement fluentWaitMethod(WebDriver driver,final String xpathStr) throws InterruptedException {
		Thread.sleep(3000);
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) 
		       .withTimeout(60, TimeUnit.SECONDS)
		       .pollingEvery(20, TimeUnit.SECONDS)
		       .ignoring(NoSuchElementException.class);
		   WebElement element=driver.findElement(By.xpath(xpathStr));
		   String value=element.getAttribute("innerHTML");
		   System.out.println("Innter html :: "+value);

		   WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		     public WebElement apply(WebDriver driver) {
		       return driver.findElement(By.xpath(xpathStr));
		     }
		   });
		 
		return element;

	}
	
	public WebElement fluentWaitMethodID(WebDriver driver,final String idString) {
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       .withTimeout(200, TimeUnit.SECONDS)
		       .pollingEvery(1, TimeUnit.SECONDS)
		       .ignoring(NoSuchElementException.class);
		   System.out.println("fluentWaitMethodID :: ");
		   WebElement element=driver.findElement(By.id(idString));
		   String value=element.getAttribute("id");
		   System.out.println("Innter html :: "+value);

		   WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		     public WebElement apply(WebDriver driver) {
		       return driver.findElement(By.id(idString));
		     }
		   });
		 
		return element;

	}
	
	public String fetchTextFromElement(WebElement element) {
		String elementText="no element text";
		try {
			elementText=element.getAttribute("innerHTML");
		}catch(Exception e) {
			System.out.println(e);
		}
		return  element.getAttribute("innerHTML");
	}
	
	/*public WebElement xpathCreator(String atttributeName,String attributeValue,WebDriver driver) throws IOException {
		WebElement element=null;
		ExtendReporter reporter=new ExtendReporter();
		try {
		if(atttributeName.equalsIgnoreCase("xpath"))
			element=driver.findElement(By.xpath(attributeValue));
		else if(atttributeName.equalsIgnoreCase("id"))
			element=driver.findElement(By.id(attributeValue));
		}catch(NoSuchElementException e) {
			reporter.captureScreen(driver);
		}
		return element;
	}*/
	
	public boolean elementPresentOrNot(WebDriver driver,String xpathString,String attributeForXpath) {
		boolean displayFlag=false;
		WebElement element = null;
		try {
			if(attributeForXpath.equalsIgnoreCase("xpath"))
				element=driver.findElement(By.xpath(xpathString));
		if(element.isDisplayed())
			displayFlag=true;
		}catch(NoSuchElementException e) {
			System.out.println(e);
		}
		return displayFlag;
			
			
	}
}
