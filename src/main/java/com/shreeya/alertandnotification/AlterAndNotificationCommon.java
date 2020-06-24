package com.shreeya.alertandnotification;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.util.Help;
import com.shreeya.util.SeleniumCoder;

public class AlterAndNotificationCommon extends SeleniumCoder{
	WebDriver driver;
	private WebElement closePopupButton;
	Help help;

	public AlterAndNotificationCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
	}
	
	public void redirectToAlterAndNotificationModule(boolean pageRefershOrNot) {
		Reporter.log("====> redirectToAlterAndNotificationModule <====", true);
		staticWait(1000);
		closePopupButton = fluentWaitCodeXpath("//a[@class='ed-icon i-close lg']",50, "Close popup button");
		if(closePopupButton!=null)
		clickElement(closePopupButton,  "Close popup button");
		try {
		hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}catch(ElementNotInteractableException e) {
			help.pageRefresh();
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}
		WebElement alterTitle=fluentWaitCodeXpath("//h1[text()='Alerts']",30, "Alter title");
		if(alterTitle==null) {
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}
		if(pageRefershOrNot)
			pageRefresh();
	}
}
