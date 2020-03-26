package com.shreeya.page;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class CxlOrderPage extends SeleniumCoder{
	
	WebElement cxlLink;
	private WebElement confirmButton;
	OrderDetail detail;
	 
	
	
	public HashMap<WebDriver, String> cxlExecution(WebDriver driver,int orderNo,String newOrderStatus) throws InterruptedException, IOException {
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		CsvReaderCode csvReader=new CsvReaderCode();
		HelperCode helperObject=new HelperCode();
		if(newOrderStatus.equalsIgnoreCase("Open")||newOrderStatus.equalsIgnoreCase("after market order req received")) {
		
		detail=new OrderDetail();
		Thread.sleep(17000);
		cxlLink=driver.findElement(By.xpath("//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[6]/div/ul/li[2]/a"));
		clickElement(cxlLink);
		Thread.sleep(4000);
		confirmButton=driver.findElement(By.xpath("//button[text()='Confirm']"));
		clickElement(confirmButton);
		Thread.sleep(5000);
		/*String [] orderDetailArray=detail.orderDetailProvider(driver,"CXL");
		orderDetailArray[0]=String.valueOf(orderNo);
		orderDetailArray[1]="CXL";
		report.reportGenerator(orderDetailArray);
		report.addScreenshotMethod(driver);
		csvReader.WriteFile(orderDetailArray,writer);*/
		}
		String status=helperObject.outputProcessor(driver, "CXL", orderNo,newOrderStatus);
		mapObject.put(driver, status);
		return mapObject;
	}

}
