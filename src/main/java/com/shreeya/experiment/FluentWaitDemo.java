package com.shreeya.experiment;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class FluentWaitDemo {
	private static WebElement SearchLink;

	 protected WebDriver driver;
	    FluentWait<WebDriver> fluentWait;

	    protected FluentWaitDemo(WebDriver driver) {
	        this.driver = driver;
	        fluentWait = new FluentWait<WebDriver>(this.driver);
	    }

	    @SuppressWarnings("deprecation")
	    protected WebElement waitUntil(By elementToWaitFor, int timeout, int pollTimeout) {
	        fluentWait.withTimeout(timeout, TimeUnit.SECONDS);
	        fluentWait.pollingEvery(pollTimeout, TimeUnit.SECONDS);
	        fluentWait.ignoring(NoSuchElementException.class);
	        fluentWait.until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
	        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='ui active loader loader']")));
	        return driver.findElement(elementToWaitFor);
	    }

	    protected WebElement waitUntil(By elementToWaitFor) {
	        return this.waitUntil(elementToWaitFor, 45, 1);
	    }

	    protected List<WebElement> waitUntilList(final List<WebElement> elementsToWaitFor, Duration interval) { 
	        fluentWait.withTimeout(interval); 
	        fluentWait.pollingEvery(interval);
	        fluentWait.ignoring(NoSuchElementException.class);
	        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='ui active loader loader']")));
	        return (elementsToWaitFor);
	    }

	    protected List<WebElement> waitUntilList(By elementsToWaitFor) throws InterruptedException {
	        // loop until element list value is not 0
	        while (driver.findElements(elementsToWaitFor).size() == 0) {
	            for (int i = 0; i < 10; i++) {
	                if (i > 5) {
	                    break;
	                }
	                System.out.println("Waiting for list element...");
	                Thread.sleep(1000);
	            }
	        }

	        return (driver.findElements(elementsToWaitFor));
	    }
	

	

	public static void main(String[] args) throws InterruptedException {
		

	}

}
