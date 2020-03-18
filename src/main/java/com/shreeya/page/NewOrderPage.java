package com.shreeya.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.model.TestDataModel;
import com.shreeya.util.SeleniumCoder;

public class NewOrderPage extends SeleniumCoder {
	
	WebElement placeOrderTextField;
	WebDriver driver;
	private WebElement buyButton;
	private WebElement noOfSharesTextField;
	private WebElement enterPriceTextField;
	private WebElement cnsRadioButton;
	private WebElement OptionalFieldsLabel;
	private WebElement placeOrderButton;
	private WebElement confirmButton;
	private WebElement nseLink;
	
	
	
	

	public WebDriver newOrderExecution(TestDataModel model,WebDriver driver) throws InterruptedException {
		System.out.println("New Order execution Started.........");
		Thread.sleep(20000);
		placeOrderTextField=driver.findElement(By.xpath("//*[@id='tocsearch']"));
		sendKey(placeOrderTextField,"Tide Water oil ltd");
		Thread.sleep(10000);
		nseLink=driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]"));
		clickElement(nseLink);
		//downErrorKeyEnter(placeOrderTextField);
		Thread.sleep(3000);
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
		buyButton=driver.findElement(By.xpath("//a[text()='Buy']"));
		clickElement(buyButton);
		}
		Thread.sleep(4000);
		noOfSharesTextField=driver.findElement(By.xpath("//input[@placeholder='No. of Shares']"));
		sendKey(noOfSharesTextField,model.getQty());
		Thread.sleep(3000);
		enterPriceTextField=driver.findElement(By.xpath("//input[@placeholder='Enter Price']"));
		sendKey(enterPriceTextField, model.getOrderPrice());
		Thread.sleep(3000);
		if(model.getProductType().equalsIgnoreCase("CNC")) {
		cnsRadioButton=driver.findElement(By.xpath("//label[text()='Delivery CNC']"));
		clickElement(cnsRadioButton);
		}
		OptionalFieldsLabel=driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]"));
		clickElement(OptionalFieldsLabel);
		Thread.sleep(2000);
		placeOrderButton=driver.findElement(By.xpath("//input[@value ='Place Order']"));
		clickElement(placeOrderButton);
		Thread.sleep(2000);
		confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
		clickElement(confirmButton);
		return driver;
	}

}
