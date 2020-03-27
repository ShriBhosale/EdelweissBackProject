package com.shreeya.page;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;
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
	
	OrderDetail detail;
	private WebElement amoCheckBox;
	private boolean flag;
	
	private WebElement placeOrderButon;
	
	

	public HashMap newOrderExecution(TestDataModel model,WebDriver driver,int orderNo) throws InterruptedException, IOException {
	
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		ConfigReader configReader=new ConfigReader();
		String amoFlag=configReader.configReader("amoFlag");
		HelperCode helperObject=new HelperCode();
		CsvReaderCode csvReader=new CsvReaderCode();
		OrderDetail orderDetail=new OrderDetail();
		detail=new OrderDetail();
		//Thread.sleep(7000);
		System.out.println("New Order execution Started..........");
		if(orderNo!=1) {
			placeOrderButon=fluentWaitCodeXpath(driver,"//a[text()='Place Order']");
			clickElement(placeOrderButon);
		}
	
		placeOrderTextField=fluentWaitCodeXpath(driver,"//*[@id='tocsearch']");
		sendKey(placeOrderTextField,model.getScript());
		/*Thread.sleep(3000);*/
		nseLink=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]");
		clickElement(nseLink);
		//downErrorKeyEnter(placeOrderTextField);
		/*Thread.sleep(2000);*/
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
		buyButton=fluentWaitCodeXpath(driver,"//a[text()='Buy']");
		clickElement(buyButton);
		}else if(model.getOrderType().equalsIgnoreCase("Sell")) {
			buyButton=fluentWaitCodeXpath(driver, "//a[text()='Sell']");
			clickElement(buyButton);
		}
		/*Thread.sleep(4000);*/
		noOfSharesTextField=fluentWaitCodeXpath(driver,"//input[@placeholder='No. of Shares']");
		clearAndSendKey(noOfSharesTextField,model.getQty());
		/*Thread.sleep(2000);*/
		enterPriceTextField=fluentWaitCodeXpath(driver,"//input[@placeholder='Enter Price']");
		sendKey(enterPriceTextField, model.getOrderPrice());
		/*Thread.sleep(1000);*/
		if(model.getProductType().equalsIgnoreCase("CNC")) {
		cnsRadioButton=fluentWaitCodeXpath(driver,"//label[text()='Delivery CNC']");
		clickElement(cnsRadioButton);
		}
		OptionalFieldsLabel=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]");
		clickElement(OptionalFieldsLabel);
		/*Thread.sleep(1000);*/
		
		orderDetail.amoCheckbox(amoFlag, driver);
		placeOrderButton=fluentWaitCodeXpath(driver,"//input[@value ='Place Order']");
		clickElement(placeOrderButton);
		/*Thread.sleep(3000);*/
		confirmButton=fluentWaitCodeXpath(driver,"//input[@value='Confirm']");
		//confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
		clickElement(confirmButton);
		
		/*Thread.sleep(3000);*/
		
		
		String status=helperObject.outputProcessor(driver, "New", orderNo,"No status",model);
		mapObject.put(driver, status);
		return mapObject;
	}

	
}
