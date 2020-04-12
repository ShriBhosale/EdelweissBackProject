package com.shreeya.util;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.shreeya.page.LoginPage;

public class SeleniumCoder {

	static Logger log = Logger.getLogger(SeleniumCoder.class.getName());
	WebDriver driver=null;
	ExtendReporter report=new ExtendReporter();
	public SeleniumCoder() {
		
	}
	
	public WebDriver browserLaunch(String scenario) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		System.setProperty("webdriver.chrome.driver","E:\\EdelweissProject\\chromedriver.exe");
		if(!scenario.equalsIgnoreCase("Partial Order")) {
			
		 driver=new ChromeDriver(capabilities);
		}else {
			driver=new ChromeDriver(capabilities);
		}
		driver.manage().window().maximize();
		//driver.get("https://www.youtube.com/");
		driver.get("https://ewuat.edelbusiness.in/");
		
		log.info("Browser launch successfully.................");
		return driver;
	}
	
	public void sendKey(WebElement element,String msg) throws InterruptedException {
		/* Thread.sleep(2000); */
		try {
		element.sendKeys(msg);
		System.out.println("element "+element+" msg : "+msg);
		}catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	
	public void sendKeyClickOnDownArrow(WebElement element,String msg) {
		try {
			element.sendKeys(msg);
			element.sendKeys(Keys.ARROW_DOWN);
			element.sendKeys(Keys.ENTER);
			System.out.println("element "+element+" msg : "+msg);
			}catch(NullPointerException e) {
				System.out.println(e);
			}
	}
	
	public void clickElement(WebElement element) throws InterruptedException {
		try {
		if(element.isEnabled()==true) {
			element.click();
			System.out.println("Click "+element);
		}else {
			System.out.println("no present");
		}
		}catch(ElementNotInteractableException e) {
			System.out.println("Convert driver into javascript than click on element.... ");
			convertInJavaScriptAndClick(element);
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
		System.out.println("element "+element+" msg : "+msg);
		}catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	
	public WebElement fluentWaitMethod(WebDriver driver,final String xpathStr) throws InterruptedException {
		Thread.sleep(3000);
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver) 
		       .withTimeout(100, TimeUnit.SECONDS)
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
	

	
	public boolean elementPresentOrNot(WebDriver driver,String xpathString,String attributeForXpath) {
		boolean displayFlag=false;
		WebElement element = null;
		try {
			if(attributeForXpath.equalsIgnoreCase("xpath"))
				element=fluentWaitCodeXpath(driver,xpathString,10);
		if(element.isDisplayed())
			displayFlag=true;
		}catch(NoSuchElementException e) {
			System.out.println(e);
		}
		return displayFlag;
			
			
	}
	public  WebElement fluentWaitCodeId(WebDriver driver,final String idString) {
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		 WebElement element=null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			       .withTimeout(100, TimeUnit.SECONDS)
			       .pollingEvery(1, TimeUnit.SECONDS)
			       .ignoring(NoSuchElementException.class);
		try {
			    element = wait.until(new Function<WebDriver, WebElement>() {
			     public WebElement apply(WebDriver driver) {
			       //WebElement searchTextField =driver.findElement(By.name("q"));
			    	 WebElement element =driver.findElement(By.id(idString));
			       if(element.isEnabled()) {
			    	   System.out.println("Element Found");
			    	   
			       }
			       return element;
			     }
			     
			   });
		}catch(TimeoutException e) {
			System.out.println(e);
			
			/*try {
				//report.abnormalErrorHandling(driver);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
			   
			   return element;
		   
	}
	
	public  WebElement fluentWaitCodeXpath(WebDriver driver,final String xpathString) {
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		 WebElement element=null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			       .withTimeout(100, TimeUnit.SECONDS)
			       .pollingEvery(1, TimeUnit.SECONDS)
			       .ignoring(NoSuchElementException.class,StaleElementReferenceException.class);
		try {
			element = wait.until(new Function<WebDriver, WebElement>() {
			     public WebElement apply(WebDriver driver) {
			       //WebElement searchTextField =driver.findElement(By.name("q"));
			    	 WebElement element =driver.findElement(By.xpath(xpathString));
			       if(element.isEnabled()) {
			    	   System.out.println("Element Found");
			    	   
			       }
			       return element;
			     }
			     
			   });
		}catch(TimeoutException e) {
			System.out.println(e);
			ExtendReporter report=new ExtendReporter();
			/*try {
				report.abnormalErrorHandling(driver);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
			   
			   return element;
		   
	}
	
	public  WebElement fluentWaitCodeXpath(WebDriver driver,final String xpathString,int maxWaitTime) {
		// Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			       .withTimeout(maxWaitTime, TimeUnit.SECONDS)
			       .pollingEvery(1, TimeUnit.SECONDS)
			       .ignoring(NoSuchElementException.class);

			   WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			     public WebElement apply(WebDriver driver) {
			       //WebElement searchTextField =driver.findElement(By.name("q"));
			    	 WebElement element =driver.findElement(By.xpath(xpathString));
			       if(element.isEnabled()) {
			    	   System.out.println("Element Found");
			    	   
			       }
			       return element;
			     }
			     
			   });
			   
			   return element;
		   
	}
	
	public void selectRadioButton(WebElement element,String nameElement) {
		if(element.isDisplayed()) {
			element.click();
		}else {
			System.out.println(nameElement+" is already selected");
		}
	}
	
	
	public void convertInJavaScriptAndClick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}
			   
			  
	public void hoverAndClickOption(WebDriver driver,String parentElementStr,String childElementStr) {
		WebElement childElement=null;
		WebElement parentElement=fluentWaitCodeXpath(driver,parentElementStr);
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).click().perform();
		 childElement=fluentWaitCodeXpath(driver,childElementStr);
		
		childElement.click();
	}
	
	protected List<WebElement> FluentWaitForElementList(final String xapthString,final WebDriver driverI) 
    {
        final WebDriver driver=driverI;
        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(100, TimeUnit.SECONDS);
        fluentWait.pollingEvery(1, TimeUnit.SECONDS);
        fluentWait.ignoring(NoSuchElementException.class);
        

        List<WebElement> list= fluentWait.until(new Function<WebDriver,List<WebElement>>()
                {
                    public List<WebElement> apply(WebDriver input) {
                        // TODO Auto-generated method stub
                        List<WebElement> list = driver.findElements(By.xpath(xapthString));

                        if(list.size() > 0)
                            throw new NoSuchElementException("List is not loaded");
                        else
                            return list;
                    }

                });
        return list;
    }

	
	
}
