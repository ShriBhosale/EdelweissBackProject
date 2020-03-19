package com.shreeya.page;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class OrderDetail extends SeleniumCoder{
	
	WebDriver driver;
	private WebElement buyAndSell;
	private WebElement tradingSymbol;
	private WebElement productType;
	private WebElement status;
	private WebElement orderPrice;
	private WebElement orderType;
	private WebElement exchange;
	private WebElement validity;
	private WebElement detailsTab;
	
	
	
	public ArrayList<String> orderDetailProvider(WebDriver driver) throws InterruptedException {
		HelperCode helper=new HelperCode();
		ArrayList<String> orderDetailList=new ArrayList<String>();
		
		detailsTab=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::a[text()='Details']"));
		clickElement(detailsTab);
		Thread.sleep(3000);
		status=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding']"));
		orderDetailList.add("Status : "+fetchTextFromElement(status));
		buyAndSell=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='action ng-binding']"));
		orderDetailList.add("Order Action :: "+fetchTextFromElement(buyAndSell));
		tradingSymbol=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='comp-name ng-binding']"));
		orderDetailList.add("Trading Symbol :: "+fetchTextFromElement(tradingSymbol));
		productType=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='mis ng-binding']"));
		orderDetailList.add("Product Type :: "+fetchTextFromElement(productType));
		orderPrice=driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='fixed-price ng-binding']"));
		
		List<WebElement> orderInfoList=driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		
		
		orderDetailList.add("Order Price :: "+fetchTextFromElement(orderPrice));
		//orderType=driver.findElement(By.xpath("//*[@id=\"accitem\"]/div/div/div[1]/div[3]/div/span[2]"));
		orderDetailList.add("Order Type :: "+fetchTextFromElement(orderInfoList.get(2)));
		orderDetailList.add("User id :: "+fetchTextFromElement(orderInfoList.get(3)));
		//exchange=driver.findElement(By.xpath("//*[@id=\"accitem\"]/div/div/div[1]/div[5]/div/span[2]"));
		orderDetailList.add("Exchange : "+fetchTextFromElement(orderInfoList.get(4)));
		//validity=driver.findElement(By.xpath("//*[@id=\"accitem\"]/div/div/div[1]/div[6]/div/span[2]"));
		orderDetailList.add("Validity :: "+fetchTextFromElement(orderInfoList.get(5)));
		List<WebElement> listForNestId=driver.findElements(By.xpath("//span[@class='ng-scope'][2]"));
		WebElement abc=listForNestId.get(0);
		String text=abc.getAttribute("innerHTML");
		orderDetailList.add("Nest Id :: "+helper.nestIdProvider(text));
		System.out.println(text);
		return orderDetailList;
	}
	
	public void printElement(WebDriver driver) throws InterruptedException {
		ArrayList<String> orderDetailList=new ArrayList<String>();
		orderDetailList=orderDetailProvider(driver);
		for(String element:orderDetailList) {
			System.out.println(element);
		}
	}
	

}
