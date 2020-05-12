package com.shreeya.seeholdingspages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.LoginModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.SeleniumCoder;

public class SeeHoldingsExecution extends SeleniumCoder {

	WebDriver driver;
	private WebElement seeHoldingsTab;

	public SeeHoldingsExecution(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void seeHoldingsExecute() throws InterruptedException, IOException {

		seeHoldingsTab = fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']//following::a[text()='See Holdings']", "See Holdings Tab");
		clickElement(seeHoldingsTab, "See Holdings Tab");

	}
}
