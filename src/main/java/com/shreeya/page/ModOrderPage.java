package com.shreeya.page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.TestDataModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class ModOrderPage extends SeleniumCoder {

	WebElement modifyLink;
	WebElement noOfSharesTextField;
	private WebElement placeOrderButton;
	private WebElement confirmButton;
	private WebElement enterPriceTextField;
	private WebElement cnsRadioButton;
	private WebElement OptionalFieldsLabel;
	
	OrderDetail detail;

	public WebDriver modExecution(TestDataModel model, WebDriver driver,ExtendReporter report) throws InterruptedException, IOException {
		detail=new OrderDetail();
		Thread.sleep(17000);
		modifyLink = driver
				.findElement(By.xpath("//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[6]/div/ul/li[1]/a"));
		clickElement(modifyLink);
		Thread.sleep(3000);
		noOfSharesTextField = driver.findElement(By.xpath("//input[@placeholder='No. of Shares']"));
		clearAndSendKey(noOfSharesTextField, model.getQty());
		Thread.sleep(3000);
		enterPriceTextField = driver.findElement(By.xpath("//input[@placeholder='Enter Price']"));
		clearAndSendKey(enterPriceTextField, model.getOrderPrice());
		Thread.sleep(3000);
		if (model.getProductType().equalsIgnoreCase("CNC")) {

			cnsRadioButton = driver.findElement(By.xpath("//label[text()='Delivery CNC']"));
			if (cnsRadioButton.isDisplayed() == false)
				clickElement(cnsRadioButton);
		}

		OptionalFieldsLabel = driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]"));
		clickElement(OptionalFieldsLabel);
		Thread.sleep(2000);
		placeOrderButton = driver.findElement(By.xpath("//input[@value ='Place Order']"));
		clickElement(placeOrderButton);
		Thread.sleep(2000);
		confirmButton = driver.findElement(By.xpath("//input[@value='Confirm']"));
		clickElement(confirmButton);
		Thread.sleep(5000);
		report.logsPrinter(detail.orderDetailProvider(driver));
		report.addScreenshotMethod(driver);
		return driver;
	}

}
