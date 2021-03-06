package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class CxlOrderPage extends SeleniumCoder{
	
	WebElement cxlLink;
	private WebElement confirmButton;
	OrderDetail detail;
	WebDriver driver;
	 
	public CxlOrderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public HashMap<WebDriver, String> cxlExecution(WebDriver driver,int orderNo,String newOrderStatus,TestDataModel model) throws InterruptedException, IOException {
		Reporter.log("<===@@@ OrderNo in Sheet "+model.getOrderNo()+" Action : "+model.getAction()+" @@@@====>");
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		CsvReaderCode csvReader=new CsvReaderCode();
		HelperCode helperObject=new HelperCode();
		Reporter.log("New order status "+newOrderStatus,true);
		if(newOrderStatus.equalsIgnoreCase("Open")||newOrderStatus.equalsIgnoreCase("after market order req received")) {
			
		detail=new OrderDetail(driver);
		/*Thread.sleep(7000);*/
		cxlLink=fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[6]/div/ul/li[2]/a","CXL Link");
		clickElement(cxlLink,"CXL link");
		/*Thread.sleep(4000);*/
		confirmButton=fluentWaitCodeXpath(driver,"//button[text()='Confirm']","Confirm Button");
		clickElement(confirmButton,"Confirm Button");
		/*Thread.sleep(5000);*/
		
		}
		
		mapObject.put(driver, "dfsf");
		return mapObject;
	}

}
