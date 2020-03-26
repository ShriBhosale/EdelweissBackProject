package com.shreeya.page;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.shreeya.util.ConfigReader;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class OrderDetail extends SeleniumCoder {

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
	private WebElement nestIdLable;
	String text;
	private WebElement QtyLabel;

	public String[] orderDetailProvider(WebDriver driver, String action) throws InterruptedException {
		boolean rejectionFlag=false;
		HelperCode helper = new HelperCode();
		String[] orderDetailList = { "no id", "no Action", "no Status", "no Order Action", "no Trading Symbol",
				"no Product Type", "no Order Price", "no Order Type", "no User id", "no Exchange", "no Validity",
				"no Nest Id","no qty", "Rejection Reason",
				"ScriptResult", "Report link", "Screenshot link1"};
		Thread.sleep(5000);
		detailsTab = driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::a[text()='Details']"));
		clickElement(detailsTab);
		ConfigReader configReader=new ConfigReader();
		boolean amoFlag=Boolean.getBoolean(configReader.configReader("amoFlag"));
		Thread.sleep(9000);
		if(action.equalsIgnoreCase("Mod")) {
			List<WebElement> statusList=driver.findElements(By.xpath("//span[@class='order-name ng-binding ng-scope']"));
			for(WebElement statusElement:statusList) {
				System.out.println(fetchTextFromElement(statusElement));
			}
			if(amoFlag==false) {
			orderDetailList[2]=fetchTextFromElement(statusList.get(1));
			}else {
				orderDetailList[2]=fetchTextFromElement(statusList.get(0));
			}
		}else {
		try {
		status = driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding']"));
		
		}catch(NoSuchElementException e) {
			try {
			status = driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding reject']"));
			rejectionFlag=true;
			}catch(NoSuchElementException e1) {
				status = driver.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding traded']"));
				rejectionFlag=true;
			}
		}
		orderDetailList[2] = fetchTextFromElement(status);
		}
		
		buyAndSell = driver.findElement(
				By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='action ng-binding']"));
		orderDetailList[3] = fetchTextFromElement(buyAndSell);
		tradingSymbol = driver.findElement(
				By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='comp-name ng-binding']"));
		orderDetailList[4] = fetchTextFromElement(tradingSymbol);
		productType = driver
				.findElement(By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='mis ng-binding']"));
		orderDetailList[5] = fetchTextFromElement(productType);
		orderPrice = driver.findElement(
				By.xpath("//div[@class='table-row ng-scope'][1]//parent::span[@class='fixed-price ng-binding']"));
		Thread.sleep(2000);
		List<WebElement> orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		

		orderDetailList[6] = fetchTextFromElement(orderPrice);
		try {
		orderDetailList[7] = fetchTextFromElement(orderInfoList.get(2));
		}catch(IndexOutOfBoundsException e) {
			clickElement(detailsTab);
			Thread.sleep(3000);
			orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
			orderDetailList[7] = fetchTextFromElement(orderInfoList.get(2));
		}
		orderDetailList[8] = fetchTextFromElement(orderInfoList.get(3));

		orderDetailList[9] = fetchTextFromElement(orderInfoList.get(4));

		orderDetailList[10] = fetchTextFromElement(orderInfoList.get(5));
		
		if(rejectionFlag==true) {
			//orderDetailList[11]=fetchTextFromElement(orderInfoList.get(7));
			orderDetailList[12]=fetchTextFromElement(orderInfoList.get(6));
		}
		QtyLabel=driver.findElement(By.xpath("//*[@id=\"ordertree\"]/ul/li[1]/span[3]/span[5]/span"));
		orderDetailList[12]=fetchTextFromElement(QtyLabel);
		List<WebElement> listForNestId = driver.findElements(By.xpath("//span[@class='ng-scope'][2]"));
		WebElement abc = listForNestId.get(0);
		text = abc.getAttribute("innerHTML");
		orderDetailList[11] = helper.nestIdProvider(text);

		System.out.println(text);
		return orderDetailList;
	}
	
	public void amoCheckbox(boolean checked,WebDriver driver) throws InterruptedException {
		System.out.println("AMO CheckBox checking....");
		boolean flag = elementPresentOrNot(driver,"//label[@class='amo-text rect-label']","xpath");
		if(flag) {
			WebElement amoCheckBox = driver.findElement(By.xpath("//label[@class='amo-text rect-label']"));
			boolean amoFlag = amoCheckBox.isEnabled();
			if(amoFlag && checked==false)
			{
				clickElement(amoCheckBox);
			}
		}
	}

	public void printElement(WebDriver driver) throws InterruptedException {
		String[] orderDetailArray = null;
		orderDetailArray = orderDetailProvider(driver, "New");
		for (String element : orderDetailArray) {
			System.out.println(element);
		}
	}

}
