package com.shreeya.page;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ApacheCode;
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
	
	

	public HashMap newOrderExecution(TestDataModel model,WebDriver driver,int orderNo) throws InterruptedException, IOException {
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		HelperCode helperObject=new HelperCode();
		CsvReaderCode csvReader=new CsvReaderCode();
		detail=new OrderDetail();
		System.out.println("New Order execution Started.........");
		Thread.sleep(30000);
		placeOrderTextField=driver.findElement(By.xpath("//*[@id='tocsearch']"));
		sendKey(placeOrderTextField,model.getScript());
		Thread.sleep(30000);
		nseLink=driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]"));
		clickElement(nseLink);
		//downErrorKeyEnter(placeOrderTextField);
		Thread.sleep(5000);
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
		buyButton=driver.findElement(By.xpath("//a[text()='Buy']"));
		clickElement(buyButton);
		}
		Thread.sleep(7000);
		noOfSharesTextField=driver.findElement(By.xpath("//input[@placeholder='No. of Shares']"));
		sendKey(noOfSharesTextField,model.getQty());
		Thread.sleep(4000);
		enterPriceTextField=driver.findElement(By.xpath("//input[@placeholder='Enter Price']"));
		sendKey(enterPriceTextField, model.getOrderPrice());
		Thread.sleep(3000);
		if(model.getProductType().equalsIgnoreCase("CNC")) {
		cnsRadioButton=driver.findElement(By.xpath("//label[text()='Delivery CNC']"));
		clickElement(cnsRadioButton);
		}
		OptionalFieldsLabel=driver.findElement(By.xpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]"));
		clickElement(OptionalFieldsLabel);
		Thread.sleep(3000);
		placeOrderButton=driver.findElement(By.xpath("//input[@value ='Place Order']"));
		clickElement(placeOrderButton);
		Thread.sleep(3000);
		confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
		//confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
		clickElement(confirmButton);
		
		Thread.sleep(5000);
		/*String [] orderDetailArray=detail.orderDetailProvider(driver,"New");
		orderDetailArray[0]=String.valueOf(orderNo);
		orderDetailArray[1]="New";
		report.reportGenerator(orderDetailArray);
		report.addScreenshotMethod(driver);
		csvReader.WriteFile(orderDetailArray,writer);*/
		
		String status=helperObject.outputProcessor(driver, "New", orderNo,"No Status");
		mapObject.put(driver, status);
		return mapObject;
	}

}
