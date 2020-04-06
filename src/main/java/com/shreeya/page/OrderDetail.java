package com.shreeya.page;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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
	private WebElement qtyLabel;
	private WebElement buyAndSellButton;
	private WebElement placeOrderLink;
	private WebElement orderStatusLink;
	private String rowNestIdString;
	private WebElement partialQtyLabel;
	private List<WebElement> orderInfoList;
	private List<WebElement> listForNestId;

	public String[] orderDetailProvider(WebDriver driver, String action) throws InterruptedException {
		boolean rejectionFlag=false;
		HelperCode helper = new HelperCode();
		String[] orderDetailList = { "no id", "no Action", "no Status", "no Order Action", "no Trading Symbol",
				"no Product Type", "no Order Price", "no Order Type", "no User id", "no Exchange", "no Validity",
				"no Nest Id","no qty","Partial Qty","Rejection Reason",
				"ScriptResult", "Report link", "Screenshot link1"};
		Thread.sleep(3000);
		if(action.equalsIgnoreCase("Partial Order")) {
			driver.navigate().refresh();
			afterRefreshPage(driver);
		}
		try {
		detailsTab = fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[7]/div/a");
		System.out.println("Click on details button");

		clickElement(detailsTab);
		}catch(StaleElementReferenceException e) {
			Thread.sleep(3000);
			detailsTab = fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[7]/div/a");
			clickElement(detailsTab);
			System.out.println("Click on 2nd time details button");
			Thread.sleep(2000);
		}
		ConfigReader configReader=new ConfigReader();
		String amoFlag=configReader.configReader("amoFlag");
		Thread.sleep(3000);
		if(action.equalsIgnoreCase("Mod")) {
			List<WebElement> statusList=driver.findElements(By.xpath("//span[@class='order-name ng-binding ng-scope']"));
			Thread.sleep(4000);
			for(WebElement statusElement:statusList) {
				System.out.println(fetchTextFromElement(statusElement));
			}
			if(fetchTextFromElement(statusList.get(1)).equalsIgnoreCase("modified")) {
			orderDetailList[2]=fetchTextFromElement(statusList.get(1));
			}else {
				orderDetailList[2]=fetchTextFromElement(statusList.get(0));
			}
			
		}else {
		try {
			status=fluentWaitCodeXpath(driver, "//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[4]/div/span[1]");
		//status = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding']");
		
		}catch(NoSuchElementException e) {
			try {
			status =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding reject']");
			rejectionFlag=true;
			}catch(NoSuchElementException e1) {
				status =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding traded']");
				rejectionFlag=true;
			}
		}
		orderDetailList[2] = fetchTextFromElement(status);
		}
		
		buyAndSell = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='action ng-binding']");
		orderDetailList[3] = fetchTextFromElement(buyAndSell);
		tradingSymbol = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='comp-name ng-binding']");
		orderDetailList[4] = fetchTextFromElement(tradingSymbol);
		productType =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='mis ng-binding']");
		orderDetailList[5] = fetchTextFromElement(productType);
		orderPrice = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='fixed-price ng-binding']");
		Thread.sleep(2000);
		try {
			//orderInfoList=FluentWaitForElementList("//span[@class='value ng-binding']", driver);
		orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		}catch(StaleElementReferenceException e) {
			Thread.sleep(3000);
			orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		}

		orderDetailList[6] = fetchTextFromElement(orderPrice);
		try {
			Thread.sleep(2000);
		orderDetailList[7] = fetchTextFromElement(orderInfoList.get(2));
		}catch(IndexOutOfBoundsException e) {
			//clickElement(detailsTab);
			Thread.sleep(3000);
			orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
			orderDetailList[7] = fetchTextFromElement(orderInfoList.get(2));
		}
		orderDetailList[8] = fetchTextFromElement(orderInfoList.get(3));

		orderDetailList[9] = fetchTextFromElement(orderInfoList.get(4));

		orderDetailList[10] = fetchTextFromElement(orderInfoList.get(5));
		if(orderDetailList[2].equalsIgnoreCase("Rejected")) {
		/*if(rejectionFlag==true) {*/
			//orderDetailList[11]=fetchTextFromElement(orderInfoList.get(7));
			orderDetailList[14]=fetchTextFromElement(orderInfoList.get(6));
		}
		if(!(orderDetailList[2].equalsIgnoreCase("Complete")||(orderDetailList[2].equalsIgnoreCase("Rejected"))||(orderDetailList[2].equalsIgnoreCase("Cancelled"))||(action.equalsIgnoreCase("Partial Order")))) {
		try {
			qtyLabel = fluentWaitCodeXpath(driver,"//*[@id=\"ordertree\"]/ul/li[1]/span[3]/span[5]/span");
			orderDetailList[12]=fetchTextFromElement(qtyLabel);
		}catch(TimeoutException e) {
			qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]");
			orderDetailList[12]=fetchTextFromElement(qtyLabel);
		}
		try {
			Thread.sleep(2000);
		listForNestId = driver.findElements(By.xpath("//span[@class='ng-scope'][2]"));
		}catch(StaleElementReferenceException e) {
			Thread.sleep(3000);
			listForNestId = driver.findElements(By.xpath("//span[@class='ng-scope'][2]"));
		}
		WebElement abc = listForNestId.get(0);
		text = abc.getAttribute("innerHTML");
		orderDetailList[11] = helper.nestIdProvider(text);
		}else {
			WebElement nestIdLabel=fluentWaitCodeXpath(driver, "//*[@id=\"ordertree\"]/ul/li[2]/span[3]/span[2]/span");
			rowNestIdString=fetchTextFromElement(nestIdLabel);
			System.out.println("Row Nestid string ===> "+rowNestIdString);
			orderDetailList[11] = helper.removeExtraString(rowNestIdString,"|");
			 if(action.equalsIgnoreCase("Partial Order")) {
				 
				 qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]");
				 
			 }else {
				 qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]");
			 }
			
			 orderDetailList[12]=fetchTextFromElement(qtyLabel);

			if(!orderDetailList[2].equalsIgnoreCase("Rejected")) {
			WebElement executedSharesLable=fluentWaitCodeXpath(driver, "//*[@id=\"ordertree\"]/ul/li[2]/span[3]/span[2]/span");
			System.out.println("Executed Shares =====>  "+fetchTextFromElement(executedSharesLable));
			}
		}
		
		 partialQtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[1]");
		 orderDetailList[13]=fetchTextFromElement(partialQtyLabel);
		System.out.println("Nest id : "+orderDetailList[11]);
		return orderDetailList;
	}
	
	public void amoCheckbox(String checked,WebDriver driver) throws InterruptedException {
		System.out.println("AMO CheckBox checking....");
		//Thread.sleep(3000);
		if(checked.equalsIgnoreCase("true")) {
			try {
		boolean flag = elementPresentOrNot(driver,"//label[@class='amo-text rect-label']","xpath");
		System.out.println("After Checking amo checkbox present....");
		if(flag) {
			WebElement amoCheckBox =fluentWaitCodeXpath(driver,"//label[@class='amo-text rect-label']");
			boolean amoFlag = amoCheckBox.isEnabled();
			if(amoFlag)
			{
				clickElement(amoCheckBox);
			}
		}
		}catch(TimeoutException e) {
			System.out.println("AMO CheckBox does not present");
		}
		}
		
	}
	
	public void afterRefreshPage(WebDriver driver) throws InterruptedException {
		/*buyAndSellButton=fluentWaitCodeXpath(driver, "//*[@id=\"QuickSB\"]",60);
		clickElement(buyAndSellButton);
		placeOrderLink=fluentWaitCodeXpath(driver, "//*[@id=\"headerCntr\"]/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[1]/ul/li/a/strong");
		clickElement(placeOrderLink);*/
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[1]/ul/li/a/strong");
		orderStatusLink=fluentWaitCodeXpath(driver, "//a[text()='Order Status' and @class='toc-tab-link']");
		clickElement(orderStatusLink);
	}

	public void printElement(WebDriver driver) throws InterruptedException {
		String[] orderDetailArray = null;
		orderDetailArray = orderDetailProvider(driver, "New");
		for (String element : orderDetailArray) {
			System.out.println(element);
		}
	}

}
