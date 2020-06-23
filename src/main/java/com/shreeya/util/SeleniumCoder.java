package com.shreeya.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
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
	int maximumDelay = 120;
	private long explicityWaitCount = 20;
	public static String elementNameError = "no element";
	Help help;
	ConfigReader configReader;

	public SeleniumCoder(WebDriver driver) {

		this.driver = driver;
		help=new Help();
		configReader=new ConfigReader();
	}
	
	public SeleniumCoder() {
		
	}

	public void sendKey(WebElement element, String msg, String elementName){
		/* Thread.sleep(2000); */
		System.out.println("elementName : " + elementName + " msg : " + msg);
		element.sendKeys(msg);
		

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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			  Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true); try
			  { elementNotInteractableExceptionHandling(element, elementName,msg);
			  
			  }catch(ElementNotInteractableException e) { StackTraceElement []
			  locaString=e.getStackTrace(); Reporter.log("<b>Exception Name : </b>" +
			  e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			  Reporter.log("<b>Exception location : </b>", true); for(StackTraceElement
			  st:locaString) { if(st.toString().contains("com.shreeya")) {
			  Reporter.log("<br>"+st.toString(), true); } } }
			  
			  
			 }catch (StaleElementReferenceException e) {
			elementNameError = elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			Reporter.log("<b>Exception Name : </b>" + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
		Reporter.log("===> fetchTextFromElement <===", true);
		String elementText = "no element text";
		try {
			elementText = element.getAttribute("innerHTML");
			Reporter.log("elementText : "+elementText, true);
		} catch (Exception e) {
			
			Reporter.log(e.getMessage(), true);
		}
		return elementText.trim();
	}
	
	public String fetchTextFromElement(String xpath,String elementName) {
		Reporter.log("===> fetchTextFromElement <===", true);
		WebElement element=fluentWaitCodeXpath(xpath, elementName);
		String elementText = "no element text";
		try {
			elementText = element.getAttribute("innerHTML");
			Reporter.log("elementText : "+elementText, true);
		} catch (Exception e) {
			
			Reporter.log(e.getMessage(), true);
		}
		return elementText.trim();
	}
	
	public String fetchTextFromElement(String xpath,int maxTime,String elementName) {
		Reporter.log("===> fetchTextFromElement <===", true);
		WebElement element=fluentWaitCodeXpath(xpath,maxTime,elementName);
		
		String elementText = "no element text";
		try {
			elementText = element.getAttribute("innerHTML");
			Reporter.log("elementText : "+elementText, true);
		} catch (Exception e) {
			
			Reporter.log(e.getMessage(), true);
		}
		return elementText.trim();
	}

	public boolean elementPresentOrNot(WebDriver driver, String xpathString, String attributeForXpath,
			String elementName) {
		boolean displayFlag = false;
		WebElement element = null;
		try {
			if (attributeForXpath.equalsIgnoreCase("xpath"))
				element = fluentWaitCodeXpath(driver, xpathString, 10, elementName);
			
		} catch (NoSuchElementException e) {
			System.out.println(e);
		} catch (TimeoutException e) {
			System.out.println(e);
		} catch (ElementNotVisibleException e) {
			System.out.println(e);
		}
		if(element!=null) {
		if (element.isDisplayed())
			displayFlag = true;
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (ElementNotInteractableException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(NoSuchSessionException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}

		return element;

	}

	public WebElement fluentWaitCodeXpath(WebDriver driver, final String xpathString, int maxWaitTime,final String elementName) {
		WebElement element=null;
		try {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maxWaitTime, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		
		 element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				// WebElement searchTextField =driver.findElement(By.name("q"));
				WebElement element = driver.findElement(By.xpath(xpathString));
				if (element.isEnabled()) {
					Reporter.log(elementName+" found", true);
				}
				return element;
			}

		});

		 
		}catch(TimeoutException e) {
			StackTraceElement [] locaString=e.getStackTrace();
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:elementNameError : "+elementNameError, true);
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
		Reporter.log("====  convertInJavaScriptAndClick  =====", true);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		if(action.equalsIgnoreCase("sendKey"))
			executor.executeScript("arguments[0].sendKey;", element);
		else if(action.equalsIgnoreCase("click"))
			executor.executeScript("arguments[0].click;", element);	
	}

	public void hoverAndClickOption(WebDriver driver, String parentElementStr, String childElementStr) {
		staticWait(500);
		Reporter.log("== hoverAndClickOption ==", true);
		WebElement childElement = null;
		WebElement parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).click().perform();
		childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");
		if(childElement.isDisplayed()) {
			Reporter.log("Child element is display", true);
		childElement.click();
		
		}else {
			Reporter.log("Child element is not display", true);
			 parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
			 action = new Actions(driver);
			 action.moveToElement(parentElement).click().perform();
		}
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		
		}

		return element;

	}

	public void clickElementWithOutChecking(WebElement element, String elementName){
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e1.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e1.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			Reporter.log(elementName + " is selected already...", true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
		wait.until(ExpectedConditions.textToBePresentInElementValue(element, msg));
		}else {
			WebDriverWait wait = new WebDriverWait(driver, explicityWaitCount);
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
	
	public void clickByActionClass(WebElement element,String elementName) {
		Reporter.log("====  clickByActionClass =====", true);
		Actions action=new Actions(driver);
		action.moveToElement(element).click().perform();
	}
	
	public void clickElement(String xpathString, String elementName) throws ElementClickInterceptedException {
		staticWait(1000);
		
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			try {
			elementNotInteractableExceptionHandling(element, elementName,msg);
			
			}catch(ElementNotInteractableException e) {
				StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
			}
			
			
		}catch (StaleElementReferenceException e) {
			elementNameError = elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			Reporter.log("<b>Exception Name : </b>" + "<br><b>Element Name : </b>" + elementName,true);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (ElementNotInteractableException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(NoSuchSessionException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}

		return element;

	}
	
	public WebElement fluentWaitCodeXpath(final By by, String elementName) {
		WebElement element = null;
		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(maximumDelay, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

			element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					// WebElement searchTextField =driver.findElement(By.name("q"));
					WebElement element = driver.findElement(by);
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
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (StaleElementReferenceException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		} catch (ElementNotInteractableException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(NoSuchSessionException e) {
			Reporter.log("Timeout exception " + elementName, true);
			elementNameError=elementName;
			Reporter.log("SeleniumCoder:ElementNameError : "+elementNameError, true);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
		Reporter.log("click on ParentElement", true);
		childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");
		staticWait(500);
		try {
		clickElementWithoutException(childElementStr,  "Child Element");
		}catch(ElementClickInterceptedException e) {
			parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
			Actions action1 = new Actions(driver);
			action1.moveToElement(parentElement).click().perform();
			childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");
			clickElement(childElementStr, "Again chile link");
		}catch(ElementNotInteractableException e) {
			staticWait(300);
			parentElement = fluentWaitCodeXpath(driver, parentElementStr, "ParentElement");
			Actions action1 = new Actions(driver);
			action1.moveToElement(parentElement).click().perform();
			childElement = fluentWaitCodeXpath(driver, childElementStr, "Child Element");
			clickElement(childElementStr, "Again chile link");
		}
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
		Reporter.log("*** multipleElementLocator ***", true);
		staticWait(2000);
		Reporter.log("groupNameElement : "+groupNameElement, true);
		List<WebElement> elements=driver.findElements(By.xpath(xpathString));
		return elements;
		
	}
	
	public List<String> multipleElementsTextProvider(String xpathString,String groupNameElement) {
		Reporter.log("*** multipleElementsTextProvider ***"+groupNameElement, true);
		List<String> elementStringList=new ArrayList<String>();
		List<WebElement> elements=multipleElementLocator(xpathString,groupNameElement);
		Reporter.log("elementsList length : "+elements.size(), true);
		for(WebElement element:elements) {
			String elementString=fetchTextFromElement(element);
			Reporter.log("elementString : "+elementString, true);
			if(!elementString.equalsIgnoreCase("Click here")) 
			elementStringList.add(elementString);
			
		}
		
		return elementStringList;
	}
	
	public List<String> multipleElementsTextProviderFilter(String xpathString,String groupNameElement) {
		Reporter.log("*** multipleElementsTextProvider ***"+groupNameElement, true);
		List<String> elementStringList=new ArrayList<String>();
		List<WebElement> elements=multipleElementLocator(xpathString,groupNameElement);
		Reporter.log("elementsList length : "+elements.size(), true);
		for(WebElement element:elements) {
			String elementString=fetchTextFromElement(element);
			Reporter.log("elementString : "+elementString, true);
			if(!elementString.equalsIgnoreCase("Click here")) {
				elementString=help.removeHtmlReporter(elementString);
			elementStringList.add(elementString);
			}
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
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}catch(StaleElementReferenceException e) {
			handlyStaleElementsException(xpathString);
			StackTraceElement [] locaString=e.getStackTrace();
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
			Reporter.log("<b>Exception location : </b>", true);
			for(StackTraceElement st:locaString) {
				if(st.toString().contains("com.shreeya")) {
					Reporter.log("<br>"+st.toString(), true);
				}
			}
		}
		return element;
	}
	
	public void clickElementWithoutException(String xpathString, String elementName) {
		staticWait(500);
		
		WebElement element=fluentWaitCodeXpath(driver, xpathString, elementName);
		try {
			staticWait(500);
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
			Reporter.log("<b>Exception Name : </b>" + e.toString() + "<br><b>Element Name : </b>" + elementName,true);
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
			int gobalWait=Integer.valueOf(configReader.configReader("StaticWait"));
			Thread.sleep(timeout+gobalWait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int noTabs(WebDriver driver) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("No. of tabs: " + tabs.size());
		return tabs.size();
	}
	
	public WebDriver switchTab(int windowNo) {
		 Set<String> tabName=driver.getWindowHandles();
		 
		 int counter=0;
		for(String tab:tabName) {
			counter++;
			if(counter==windowNo) {
			driver.switchTo().window(tab);
			}
		}
		return driver;
	}
	
	public WebDriver switchTabAction() {
		Actions action= new Actions(driver);
		action.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();
		return driver;
	}
	
	public void browserPopup(boolean flag) {
		if(flag) {
			driver.switchTo().alert().accept();
		}else if(!flag) {
			driver.switchTo().alert().dismiss();
		}
	}
	public List<String> elementsTextFilter(List<String> listObject) {
		Reporter.log("elementsTextFilter : listObject length : "+listObject.size(), true);
		List<String> fiterList=new ArrayList<String>(); ;
		for(String elementString:listObject) {
			
			String [] arr=elementString.trim().split(" ");
			String ans=arr[0].replace("\n", "");
			Reporter.log(ans.trim(), true);
			fiterList.add(ans.trim());
		}
		
		return fiterList;
	}
	
	public String elementsTextFilter(String scriptName) {
			String [] arr=scriptName.trim().split(" ");
			String ans=arr[0].replace("\n", "");
			Reporter.log(ans.trim(), true);
		return ans;
	}
	
	public static void staticWaitStatic(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String removeExtrahmtlCode(String text) {
		String [] textArray=text.split("<");
		String msg=textArray[0];
		 textArray=textArray[1].split(">");
		 msg=msg+textArray[1];
		Reporter.log("Predefine msg : "+msg, true);
		return msg;
	}
	
	public void clickUsingAction(WebElement element,String elementName) {
		Actions action=new Actions(driver);
		action.moveToElement(element).build().perform();
		staticWait(500);
		action.click(element).build().perform();
		Reporter.log(elementName+" click", true);
	}
	
	public void screenshortAttach(String screeenshotName) {
		String screenshot=ScreenshortProvider.captureScreen(driver, screeenshotName);
		screenshot=help.absolutePathProvider(screenshot);
		String pathAttach="\"<img src=\"\\\"file://\"\" alt=\"\\\"\\\"/\" />\";";
	}
	
	public void closeTab(int tabNo) {
		Set<String> tabName=driver.getWindowHandles();
		 
		 int counter=0;
		for(String tab:tabName) {
			counter++;
			if(counter==tabNo) {
			driver.close();
			}
		}
	}
	
	public String currentUrl() {
		staticWait(100);
		String currentUrl=driver.getCurrentUrl();
		Reporter.log("Cureent Url : "+currentUrl, true);
		return currentUrl;
	}
	
	public void clearTextfield(WebElement element,String elementName) {
		
		element.clear();
		Reporter.log(elementName+" clear textfield", true);
		
	}
	
	public void pageRefresh() {
		driver.navigate().refresh();
		staticWait(600);
	}
	
	public String getValueFromAttribute(WebElement element,String attributeName,String elementName) {
		
		String elementStr=element.getAttribute(attributeName);
		if(elementStr==null||elementStr.equalsIgnoreCase("")) {
			elementStr=element.getText();
		}
		Reporter.log("Element Name : "+elementName+"  Attribute name : "+attributeName+"  Element str : "+elementStr,true);
		return elementStr;
	}
	
	public String removeHtmlText(String text) {
		String msg="";
		if(text.contains("\n"))
			text=text.replace("\n", "").trim();
		String [] textArray=text.split("<");
		 //msg=textArray[0];
		for(String textStr:textArray) {
		 textArray=textStr.trim().split(">");
		 Reporter.log(textStr, true);
		 msg=msg+" "+textArray[textArray.length-1];
		}
		Reporter.log("removeHtmlText msg : "+msg, true);
		return msg;
	}
	
	public WebElement elementLocateBytag(String tagName) {
		WebElement element=null;
		do {
		staticWait(20);
		element=driver.findElement(By.tagName(tagName));
		Reporter.log("elementLocateBytag do while", true);
		}while(element==null);
		return element;
	}
	
	public void redirectGivenUrl(String url) {
		Reporter.log("Hit this Url : "+url);
		driver.get(url);
	}
	
	protected void closeBrowser() {

		driver.close();
		
	}
	
	public void moveGivenUrl(String urlStr) {
		driver.get(urlStr);
	}
	
	public void checkPopupPresentIfYesHandly(boolean acceptOrNot) {
		WebDriverWait wait = new WebDriverWait(driver, 3 /*timeout in seconds*/);
        if(wait.until(ExpectedConditions.alertIsPresent())==null){
              System.out.println("alert was not present");
        }
        else
        {
         Alert alert = driver.switchTo().alert();
         if(acceptOrNot) {
        	 alert.accept();
        	 System.out.println("alert was present and accepted");
         }else {
        	 alert.dismiss();
        	 System.out.println("alert was present and rejected");
         }
        }
	}
	
	public void clickElementUsingJavaScript(WebElement element,String elementName) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		Reporter.log("click on "+elementName, true);
	}
	
	public boolean checkClickableOrNot(String xpathString,String elementName) {
		Reporter.log("====> checkClickableOrNot <====", true);
		boolean elementClick=false;
		WebElement element=fluentWaitCodeXpath(xpathString, elementName);
		try {
		WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        elementClick=true;
		}catch(Exception e) {
			Reporter.log(e.getLocalizedMessage());
			Reporter.log("Element name : "+elementName);
		}
		
		return elementClick;
	}
	
	public boolean checkClickableOrNot(WebElement element,String elementName) {
		Reporter.log("====> checkClickableOrNot <====", true);
		boolean elementClick=false;
		
		try {
		WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        elementClick=true;
		}catch(Exception e) {
			Reporter.log(e.getLocalizedMessage());
			Reporter.log("Element name : "+elementName);
			 elementClick=false;
		}
		
		return elementClick;
	}
	
	public void checkElementPresentOrNotThenClick(String xpathStr,String elementName,int maxTime) {
		Reporter.log("===== checkElementPresentOrNotThenClick =====", true);
		int counter=0;
		WebElement element;
		do {
			counter++;
			staticWait(2000);
		 element=fluentWaitCodeXpath(xpathStr,maxTime,elementName);
		if(element.isEnabled()) {
			Reporter.log(elementName+" is enable", true);
			clickElement(element, elementName);
		}
		}while(element.isEnabled()==false && counter<8);
	}
	
	public void explicityWaitSendKey(String xpathStr,String elementName,String msg) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
		element.clear();
		element.sendKeys(msg);
		staticWait(1000);
	}
	
	public void senkeyJavaScript(WebElement element,String msg) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("arguments[0].value='"+msg+"';", element);
		element.sendKeys(msg);
	}
	
	public void handlyStaleElementsException(String xpathStr) {
		Reporter.log("=====> handlyStaleElements  <=====", true);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathStr)));	
	}
	
	 public boolean waitForNewWindow(WebDriver driver, int timeout){
         boolean flag = false;
         int counter = 0;
         while(!flag){
             try {
                 Set<String> winId = driver.getWindowHandles();
                 if(winId.size() > 1){
                     flag = true;
                     return flag;
                 }
                 Thread.sleep(1000);
                 counter++;
                 if(counter > timeout){
                     return flag;
                 }
             } catch (Exception e) {
                 System.out.println(e.getMessage());
                 return false;
             }
         }
         return flag;
     }
	 
	 public void waitTillNewTabUpload(String xpathStr,String elementName,int timeout,int timeoutForElemement) {
		 Reporter.log("=====> waitTillNewTabUpload  <======", true);
		 boolean newTabOpenFlag=false;
		 newTabOpenFlag=waitForNewWindow(driver, timeout);
		
		 WebElement element;
		 if(newTabOpenFlag) {
			 switchTab(2);
			 element=fluentWaitCodeXpath(xpathStr,timeoutForElemement,elementName);
			 if(element!=null) {
				 if(element.isDisplayed()) {
					 Reporter.log(elementName+"tab open", true);
				 }
			 }else {
				 Reporter.log(elementName+"tab not open", true);
			 }
		 }
	 }
	 
	 
}
