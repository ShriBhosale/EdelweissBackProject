package com.shreeya.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.google.common.base.Function;

public class SeleniumCoder extends ExceptionHandler {

	static Logger log = Logger.getLogger(SeleniumCoder.class.getName());
	WebDriver driver = null;

	ExtendReporter report = new ExtendReporter();
	int maximumDelay = 150;
	private long explicityWaitCount = 20;
	public static String elementNameError = "no element";

	public SeleniumCoder(WebDriver driver) {

		this.driver = driver;
	}
	
	public SeleniumCoder() {
		
	}

	public void sendKey(WebElement element, String msg, String elementName) throws InterruptedException {
		/* Thread.sleep(2000); */

		element.sendKeys(msg);
		System.out.println("elementName : " + elementName + " msg : " + msg);

	}

	public void sendKeyClickOnDownArrow(WebElement element, String msg) {

		element.sendKeys(msg);
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		System.out.println("element : " + element + " msg : " + msg);

	}

	public void clickElement(WebElement element, String elementName)  {
		staticWait(500);
		try {
			if (element.isEnabled() == true) {
				element.click();
				Reporter.log(elementName + " Click ", true);
			} else {
				Reporter.log(elementName + " no present", true);
				WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
				Reporter.log("Checking element visible or not " + elementName, true);
				wait.until(ExpectedConditions.visibilityOf(element));
				Reporter.log("Checking element clickable or not " + elementName, true);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Reporter.log("After explicityWait : " + elementName, true);
				element.click();
			}
		} catch (ElementNotInteractableException e) {
			staticWait(700);
			System.out.println("Convert driver into javascript than click on element  " + elementName);
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			try {
			elementNotInteractableExceptionHandling(element, elementName);
			}catch(ElementClickInterceptedException e1) {
				staticWait(3000);
			}
			element.click();
		} catch (TimeoutException e) {
			Reporter.log("TimeoutException for this  " + elementName, true);
			ExtendReporter reporter = new ExtendReporter();
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} /*
			 * catch(NullPointerException e) { elementNameError=elementName;
			 * 
			 * StackTraceElement [] locaString=e.getStackTrace();
			 * Reporter.log("<b>Exception Name : </b>" + e.toString() +
			 * "<br><b>Element Name : </b>" + elementName);
			 * Reporter.log("<b>Exception location : </b>", true); for(StackTraceElement
			 * st:locaString) { if(st.toString().contains("com.shreeya")) {
			 * Reporter.log("<br>"+st.toString(), true); } } }
			 */

	}

	public void downErrorKeyEnter(WebElement element) {
		Reporter.log("Click Down Error Button\nClick Enter Button");
		element.sendKeys(Keys.DOWN);
		element.sendKeys(Keys.ENTER);
	}

	public void clearAndSendKey(WebElement element, String msg, String elementName) {

		try {
			element.clear();
			element.sendKeys(msg);
			Reporter.log("element : "+elementName+" msg : "+msg, true);
		} catch (ElementNotInteractableException e1) {
			elementNameError = elementName;
			try {
			elementNotInteractableExceptionHandling(element, elementName,msg);
			
			}catch(ElementNotInteractableException e) {
				StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			}
			
			
		}catch (StaleElementReferenceException e) {
			elementNameError = elementName;
			Reporter.log("<b>Exception Name : </b>" + "<br><b>Element Name : </b>" + elementName);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			
		}

	}

	public WebElement fluentWaitMethod(WebDriver driver, final String xpathStr)  {
		staticWait(3000);
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
				.pollingEvery(20, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		WebElement element = driver.findElement(By.xpath(xpathStr));
		String value = element.getAttribute("innerHTML");
		System.out.println("Innter html :: " + value);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(xpathStr));
			}
		});

		return element;

	}

	public WebElement fluentWaitMethodID(WebDriver driver, final String idString,String elementName ) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		WebElement element = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			System.out.println("fluentWaitMethodID :: ");
			element = driver.findElement(By.id(idString));
			String value = element.getAttribute("id");
			System.out.println("Innter html :: " + value);

			WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.id(idString));
				}
			});
		} catch (TimeoutException e) {
			Reporter.log("fluentWaitMethodID", true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}
		return element;

	}

	public String fetchTextFromElement(WebElement element, String elementName) {
		String elementText = "no element text";
		try {
			elementText = element.getAttribute("innerHTML");
			Reporter.log(elementName + " : " + elementText, true);

		} catch (Exception e) {
			Reporter.log(elementName + " not found", true);
			Reporter.log(e.getMessage(), true);
		}
		return elementText;
	}
	
	public String fetchTextFromElement(WebElement element) {
		String elementText = "no element text";
		try {
			elementText = element.getAttribute("innerHTML");
			

		} catch (Exception e) {
			
			Reporter.log(e.getMessage(), true);
		}
		return elementText;
	}

	public boolean elementPresentOrNot(WebDriver driver, String xpathString, String attributeForXpath,
			String elementName) {
		boolean displayFlag = false;
		WebElement element = null;
		try {
			if (attributeForXpath.equalsIgnoreCase("xpath"))
				element = fluentWaitCodeXpath(driver, xpathString, 10, elementName);
			if (element.isDisplayed())
				displayFlag = true;
		} catch (NoSuchElementException e) {
			System.out.println(e);
		} catch (TimeoutException e) {
			System.out.println(e);
		} catch (ElementNotVisibleException e) {
			System.out.println(e);
		}
		return displayFlag;

	}

	public boolean elementPresentOrNot(WebDriver driver, WebElement element) {
		boolean displayFlag = false;

		try {

			if (element.isDisplayed())
				displayFlag = true;
		} catch (NoSuchElementException e) {
			System.out.println(e);
		} catch (TimeoutException e) {
			System.out.println(e);
		} catch (ElementNotVisibleException e) {
			System.out.println(e);
		} catch (StaleElementReferenceException e) {
			System.out.println(e);
			displayFlag = false;
		}
		return displayFlag;

	}

	public WebElement fluentWaitCodeId(WebDriver driver, final String idString, String elementName) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		WebElement element = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		try {
			element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					// WebElement searchTextField =driver.findElement(By.name("q"));
					WebElement element = driver.findElement(By.id(idString));
					if (element.isEnabled()) {
						System.out.println("Element Found");

					}
					return element;
				}

			});
		} catch (TimeoutException e) {
			timeOutExceptionHandler(elementName);
			elementNameError = elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}

		}

		return element;

	}

	public WebElement fluentWaitCodeXpath(final String xpathString, String elementName) {
		WebElement element = null;
		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

			element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					// WebElement searchTextField =driver.findElement(By.name("q"));
					WebElement element = driver.findElement(By.xpath(xpathString));
					if (element.isEnabled()) {

					} else {
						System.out.println("Element not Foun");
					}
					return element;
				}

			});

		} catch (TimeoutException e) {
			Reporter.log("Timeout exception " + elementName, true); // timeOutExceptionHandler(elementName);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (ElementNotInteractableException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(NoSuchSessionException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}

		return element;

	}

	public WebElement fluentWaitCodeXpath(WebDriver driver, final String xpathString, int maxWaitTime,String elementName) {
		WebElement element=null;
		try {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxWaitTime, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		 element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.xpath(xpathString));
				if (element.isEnabled()) {
					System.out.println("Element Found");

				}
				return element;
			}

		});

		
		}catch(TimeoutException e) {
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}
		return element;
	}

	public void selectRadioButton(WebElement element, String nameElement) {
		if (element.isDisplayed()) {
			element.click();
		} else {
			System.out.println(nameElement + " is already selected");
		}
	}

	public void convertInJavaScriptAndClick(WebElement element,String action) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		if(action.equalsIgnoreCase("sendKey"))
			executor.executeScript("arguments[0].sendKey;", element);
		else if(action.equalsIgnoreCase("click"))
			executor.executeScript("arguments[0].click;", element);	
	}

	public void hoverAndClickOption(WebDriver driver, String parentElementStr, String childElementStr) {
		Reporter.log("Click on Buy/Sell button and click on place order link", true);
		WebElement childElement = null;
		WebElement parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).click().perform();
		childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");

		childElement.click();
	}

	protected List<WebElement> FluentWaitForElementList(final String xapthString, final WebDriver driverI) {
		final WebDriver driver = driverI;
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(maximumDelay, TimeUnit.SECONDS);
		fluentWait.pollingEvery(3, TimeUnit.SECONDS);
		fluentWait.ignoring(NoSuchElementException.class);

		List<WebElement> list = fluentWait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver input) {
				// TODO Auto-generated method stub
				List<WebElement> list = driver.findElements(By.xpath(xapthString));

				if (list.size() > 0)
					throw new NoSuchElementException("List is not loaded");
				else
					return list;
			}

		});
		return list;
	}

	public WebElement fluentWaitCodeId(WebDriver driver, final String idString, int maxDelay) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		WebElement element = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxDelay, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.id(idString));
				if (element.isEnabled()) {
					System.out.println("Element Found");

				}
				return element;
			}

		});

		return element;

	}

	public WebElement fluentWaitCodeName(WebDriver driver, final String idString, int maxDelay,String elementName) {
		WebElement element = null;
		try {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxDelay, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.name(idString));
				if (element.isEnabled()) {
					System.out.println("Element Found");

				}
				return element;
			}

		});
		}catch(NoSuchSessionException e) {

			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		
		}

		return element;

	}

	public void clickElementWithOutChecking(WebElement element, String elementName) throws InterruptedException {
		try {
			if (element.isSelected()) {
				Reporter.log(elementName + " is selected already...", true);
			} else {
				element.click();
				Reporter.log(elementName + " Click ", true);
			}

		} catch (ElementNotInteractableException e) {
			System.out.println("Convert driver into javascript than click on element  " + elementName);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			elementNotInteractableExceptionHandling(element, elementName);
			Reporter.log("After explicityWait : " + elementName, true);
			element.click();
		} catch (TimeoutException e1) {
			Reporter.log("TimeoutException for this  " + elementName, true);
			ExtendReporter reporter = new ExtendReporter();
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e1.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e1.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log(e.getMessage(), true);
			explicityWaitMethod(element, elementName,"No");
			elementNameError=elementName;
			Reporter.log(elementName + " is selected already...", true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			if (element.isSelected()) {
				Reporter.log(elementName + " is selected already...", true);
			} else {
				element.click();
				Reporter.log(elementName + " Click ", true);
			}
		}

	}

	public void explicityWaitMethod(WebElement element, String elementName,String msg) {
		if(!msg.equalsIgnoreCase("No")) {
		WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		wait.until(ExpectedConditions.textToBePresentInElementValue(element, msg));
		}else {
			WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		Reporter.log(elementName + " is selected already...", true);
	}
	
	public void elementNotInteractableExceptionHandling(WebElement element,String elementName,String msg) {
		try {
			convertInJavaScriptAndClick(element,"sendKey");
			element.clear();
			element.sendKeys(msg);
		}catch(ElementNotInteractableException e) {
			explicityWaitMethod(element, elementName,msg);
			element.clear();
			element.sendKeys(msg);
		}
	}
	public void elementNotInteractableExceptionHandling(WebElement element,String elementName) {
		try {
			convertInJavaScriptAndClick(element,"click");
			element.click();
		}catch(ElementNotInteractableException e) {
			explicityWaitMethod(element, elementName,"No");
			element.click();
		}
	}
	
	public void clickElement(String xpathString, String elementName) {
		staticWait(500);
		
		WebElement element=fluentWaitCodeXpath(driver, xpathString, elementName);
		try {
			staticWait(300);
			if (element.isEnabled() == true) {
				element.click();
				Reporter.log(elementName + " Click ", true);
			} else {
				Reporter.log(elementName + " no present", true);
				WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
				Reporter.log("Checking element visible or not " + elementName, true);
				wait.until(ExpectedConditions.visibilityOf(element));
				Reporter.log("Checking element clickable or not " + elementName, true);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Reporter.log("After explicityWait : " + elementName, true);
				element.click();
			}
		} catch (ElementNotInteractableException e) {
			System.out.println("Convert driver into javascript than click on element  " + elementName);
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			staticWait(1000);
			element=fluentWaitCodeXpath(driver, xpathString, elementName);
			//elementNotInteractableExceptionHandling(element, elementName);
			element.click();
		} catch (TimeoutException e) {
			Reporter.log("TimeoutException for this  " + elementName, true);
			ExtendReporter reporter = new ExtendReporter();
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(StaleElementReferenceException e) {
			Reporter.log("StateElementReferenceException", true);
			element=fluentWaitCodeXpath(driver, xpathString, elementName);
			element.click();
		}
			 

	}
	
	public void clearAndSendKey(String xpathString, String msg, String elementName) {
		WebElement element=fluentWaitCodeXpath(driver, xpathString, elementName);
		try {
			element.clear();
			element.sendKeys(msg);
			Reporter.log("element : "+elementName+" msg : "+msg, true);
		} catch (ElementNotInteractableException e1) {
			elementNameError = elementName;
			try {
			elementNotInteractableExceptionHandling(element, elementName,msg);
			
			}catch(ElementNotInteractableException e) {
				StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			}
			
			
		}catch (StaleElementReferenceException e) {
			elementNameError = elementName;
			Reporter.log("<b>Exception Name : </b>" + "<br><b>Element Name : </b>" + elementName);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			
		}

	}

	public WebElement fluentWaitCodeXpath(WebDriver driver, final String xpathString, String elementName) {
		WebElement element = null;
		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

			element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					// WebElement searchTextField =driver.findElement(By.name("q"));
					WebElement element = driver.findElement(By.xpath(xpathString));
					if (element.isEnabled()) {

					} else {
						System.out.println("Element not Foun");
					}
					return element;
				}

			});

		} catch (TimeoutException e) {
			Reporter.log("Timeout exception " + elementName, true); // timeOutExceptionHandler(elementName);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (ElementNotInteractableException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(NoSuchSessionException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}

		return element;

	}
	
	public void hoverAndClickOption(String parentElementStr, String childElementStr)  {
		Reporter.log("click on Buy/Sell button and click on place order link", true);
		WebElement childElement = null;
		WebElement parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).click().perform();
		childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");
		staticWait(200);
		clickElement(childElement,  "Child Element");
	}
	
	public void iconButton(String xpathString,String iconName) {
		
		WebElement childElement = null;
		WebElement parentElement = fluentWaitCodeXpath(driver, xpathString,iconName);
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).click().perform();
	}
	
	public void downErrorKeyEnter(String xpath,String elementName) {
		//WebElement element=fluentWaitCodeXpath(xpath, elementName);
		Reporter.log("Click Down Error Button\nClick Enter Button");
		/*
		 * element.sendKeys(Keys.END); Thread.sleep(1000); element.sendKeys(Keys.DOWN);
		 * Thread.sleep(3000);
		 */
		Actions action=new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ENTER).build().perform();
		staticWait(1000);
		//element.sendKeys(Keys.ENTER);
		
	}
	
	public WebElement fluentWaitCodeXpathCheckElement(WebDriver driver, final String xpathString, int maxWaitTime,String elementName) {
		WebElement element=null;
		try {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(3, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		 element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.xpath(xpathString));
				if (element.isEnabled()) {
					System.out.println("Element Found");

				}
				return element;
			}

		});

		
		}catch(TimeoutException e) {
			
		}
		return element;
	}
	
	public List<WebElement> multipleElementLocator(String xpathString,String groupNameElement){
		staticWait(2000);
		Reporter.log("multipleElementLocator : "+groupNameElement, true);
		List<WebElement> elements=driver.findElements(By.xpath(xpathString));
		return elements;
		
	}
	
	public List<String> multipleElementsTextProvider(String xpathString,String groupNameElement) {
		Reporter.log("multipleElementsTextProvider : "+groupNameElement, true);
		List<String> elementStringList=new ArrayList<String>();
		List<WebElement> elements=multipleElementLocator(xpathString,groupNameElement);
		Reporter.log("multipleElementsTextProvider : elementsList length : "+elements.size(), true);
		for(WebElement element:elements) {
			String elementString=fetchTextFromElement(element);
			elementStringList.add(elementString);
		}
		
		return elementStringList;
	}

	public WebElement fluentWaitCodeXpath(final String xpathString, int maxWaitTime,String elementName) {
		WebElement element=null;
		try {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxWaitTime, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		 element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.xpath(xpathString));
				if (element.isEnabled()) {
					System.out.println("Element Found");

				}
				return element;
			}

		});

		
		}catch(TimeoutException e) {
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}
		return element;
	}
	
	public void clickElementWithoutException(String xpathString, String elementName) throws InterruptedException{
		Thread.sleep(500);
		
		WebElement element=fluentWaitCodeXpath(driver, xpathString, elementName);
		try {
			Thread.sleep(500);
			if (element.isEnabled() == true) {
				element.click();
				Reporter.log(elementName + " Click ", true);
			} else {
				Reporter.log(elementName + " no present", true);
				WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
				Reporter.log("Checking element visible or not " + elementName, true);
				wait.until(ExpectedConditions.visibilityOf(element));
				Reporter.log("Checking element clickable or not " + elementName, true);
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Reporter.log("After explicityWait : " + elementName, true);
				element.click();
			}
		} /*catch (TimeoutException e) {
			Reporter.log("TimeoutException for this  " + elementName, true);
			ExtendReporter reporter = new ExtendReporter();
			elementNameError=elementName;
			
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}*/
		catch(StaleElementReferenceException e) {
			Reporter.log("StateElementReferenceException", true);
			element=fluentWaitCodeXpath(driver, xpathString, elementName);
			element.click();
		}
			 

	}
	
	public void staticWait(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
