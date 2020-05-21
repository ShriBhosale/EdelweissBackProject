package com.shreeya.orderdetailpages;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

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
	private WebElement userIdLable;
	private WebElement detailsTab;
	private WebElement nestIdLable;
	private WebElement TriggerPriceLable;
	private WebElement orderTypeLable;
	private WebElement exchangeLable;
	private WebElement validityLable;
	private WebElement orderLogsLabel;
	private WebElement tradingSymbolLabel;
	private WebElement disclosedLabel;
	private WebElement triggerPriceLabel;
	private WebElement orderTypeLabel;
	private WebElement userIdLabelCo;
	private WebElement exchangeLabel;
	private WebElement validityLabel;
	private WebElement rejectReasonLable;
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
	private WebElement scriptNameLabel;
	
	
	public OrderDetail(WebDriver driver) {
		super(driver);
		this.driver=driver;
		
	}

	public String[] orderDetailProvider(WebDriver driver, String action,String orderNoSheet) throws InterruptedException {
		Reporter.log("*<==== orderDetailProvider Method Start ====>*",true);
		Reporter.log("===<<<<<*** OrderNo in Sheet "+orderNoSheet+" Action : "+action+" ***>>>>>===>",true);
		boolean rejectionFlag=false;
		HelperCode helper = new HelperCode();
		String[] orderDetailList = { "no id", "no Action", "no Status", "no Order Action", "no Trading Symbol",
				"no Product Type", "no Order Price", "no Order Type", "no User id", "no Exchange", "no Validity",
				"no Nest Id","no qty","Partial Qty","Rejection Reason",
				"ScriptResult", "Report link", "Screenshot link1"};
		Thread.sleep(3000);
		if(action.equalsIgnoreCase("Partial Order")) {
			Reporter.log("Partial Order\nRefresh page",true);
			driver.navigate().refresh();
			afterRefreshPage(driver);
		}
		try {
			staticWait(1000);
		detailsTab = fluentWaitCodeXpath(driver,"//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[7]/div/a","Details tab");
		

		clickElement(detailsTab,"Details tab");
		}catch(StaleElementReferenceException e) {
			Thread.sleep(3000);
			detailsTab = fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[7]/div/a","Details tab");
			clickElement(detailsTab,"Details tab");
			Reporter.log("Click on 2nd time details button",true);
			Thread.sleep(2000);
		}
		ConfigReader configReader=new ConfigReader();
		String amoFlag=configReader.configReader("amoFlag");
		Thread.sleep(3000);
		if(action.equalsIgnoreCase("Mod")) {
			Reporter.log("Action : "+action,true);
			List<WebElement> statusList=driver.findElements(By.xpath("//span[@class='order-name ng-binding ng-scope']"));
			Thread.sleep(6000);
			for(WebElement statusElement:statusList) {
				Reporter.log(fetchTextFromElement(statusElement,"Mod Status"),true);
			}
			String statusForMod=fetchTextFromElement(statusList.get(1),"Mod Status");
			if(statusForMod.equalsIgnoreCase("modified")) {
			orderDetailList[2]=statusForMod;
			}else {
				orderDetailList[2]=fetchTextFromElement(statusList.get(0),"Status Not modified");
			}
			Reporter.log("Status for Mod : "+orderDetailList[2],true);
		}else {
		try {
			
			status=fluentWaitCodeXpath(driver, "//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[4]/div/span[1]","Status");
		//status = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding']");
		
		}catch(NoSuchElementException e) {
			try {
			status =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding reject']","Status");
			rejectionFlag=true;
			}catch(NoSuchElementException e1) {
				status =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='inprogress ng-binding traded']","Status");
				rejectionFlag=true;
			}
		}
		orderDetailList[2] = fetchTextFromElement(status,"Status");
		}
		
		buyAndSell = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='action ng-binding']","Buy or Sell");
		orderDetailList[3] = fetchTextFromElement(buyAndSell,"Buy or Sell");
		tradingSymbol = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='comp-name ng-binding']","Trading symbol");
		orderDetailList[4] = fetchTextFromElement(tradingSymbol,"Trading symbol");
		productType =fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='mis ng-binding']","Product type");
		orderDetailList[5] = fetchTextFromElement(productType,"Product type");
		orderPrice = fluentWaitCodeXpath(driver,"//div[@class='table-row ng-scope'][1]//parent::span[@class='fixed-price ng-binding']","Order parice");
		Thread.sleep(2000);
		try {
			//orderInfoList=FluentWaitForElementList("//span[@class='value ng-binding']", driver);
		orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		}catch(StaleElementReferenceException e) {
			Thread.sleep(3000);
			orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
		}
		orderPrice=fluentWaitCodeXpath("//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[5]/div/span[2]", "Order price");
		orderDetailList[6] =fetchTextFromElement(orderPrice,"Order Price");
		try {
			Thread.sleep(2000);
			orderTypeLable=fluentWaitCodeXpath("//*[@id=\"accitem\"]/div/div/div[1]/div[3]/div/span[2]", "Order Type");
		orderDetailList[7] = fetchTextFromElement(orderTypeLable,"Order Type");
		}catch(IndexOutOfBoundsException e) {
			//clickElement(detailsTab);
			Thread.sleep(7000);
			//orderInfoList = driver.findElements(By.xpath("//span[@class='value ng-binding']"));
			orderDetailList[7] = fetchTextFromElement(orderInfoList.get(2),"Order Type");
		}
		userIdLable=fluentWaitCodeXpath("//*[@id=\"accitem\"]/div/div/div[1]/div[4]/div/span[2]", "UserId");
		orderDetailList[8] = fetchTextFromElement(userIdLable,"User Id");

		exchangeLable=fluentWaitCodeXpath("//*[@id=\"accitem\"]/div/div/div[1]/div[5]/div/span[2]", "Exchange");
		orderDetailList[9] = fetchTextFromElement(exchangeLable,"Exchange");

		validityLable=fluentWaitCodeXpath("//*[@id=\"accitem\"]/div/div/div[1]/div[6]/div/span[2]", "Validity");
		orderDetailList[10] = fetchTextFromElement(exchangeLable,"Validity");
		if(orderDetailList[2].equalsIgnoreCase("Rejected")) {
		/*if(rejectionFlag==true) {*/
			WebElement rejectReasoneLabel=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[2]/div/span[2]", "rejection reasone");
			orderDetailList[14]=fetchTextFromElement(rejectReasoneLabel,"Rejection Reasone");
		}
		if(!(orderDetailList[2].equalsIgnoreCase("Complete")||(orderDetailList[2].equalsIgnoreCase("Rejected"))||(orderDetailList[2].equalsIgnoreCase("Cancelled"))||(action.equalsIgnoreCase("Partial Order")))) {
		try {
			qtyLabel = fluentWaitCodeXpath(driver,"//*[@id=\"ordertree\"]/ul/li[1]/span[3]/span[5]/span","Order Qty");
			orderDetailList[12]=fetchTextFromElement(qtyLabel,"Order Qty");
		}catch(TimeoutException e) {
			qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]","Order qty");
			orderDetailList[12]=fetchTextFromElement(qtyLabel,"Order qty");
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
		Reporter.log("Nest id : "+orderDetailList[11], true);
		}else {
			WebElement nestIdLabel=fluentWaitCodeXpath(driver, "//*[@id=\"ordertree\"]/ul/li[2]/span[3]/span[2]/span","NestId");
			rowNestIdString=fetchTextFromElement(nestIdLabel,"NestId");
			Reporter.log("Row Nestid string ===> "+rowNestIdString,true);
			orderDetailList[11] = helper.removeExtraString(rowNestIdString,"|");
			Reporter.log("Nest id : "+orderDetailList[11], true);
			 if(action.equalsIgnoreCase("Partial Order")) {
				
				 qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]","Order Qty");
				 
			 }else {
				 qtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[2]","Order Qty");
			 }
			
			 orderDetailList[12]=fetchTextFromElement(qtyLabel,"Order Qty");

			if(!orderDetailList[2].equalsIgnoreCase("Rejected")) {
			WebElement executedSharesLable=fluentWaitCodeXpath(driver, "//*[@id=\"ordertree\"]/ul/li[2]/span[3]/span[2]/span","executed Shares Lable");
			Reporter.log("Executed Shares =====>  "+fetchTextFromElement(executedSharesLable,"executed Shares Lable"),true);
			}
		}
		Thread.sleep(4000);
		 //uncoment in partial order scenario 2 below line
		 //partialQtyLabel=fluentWaitCodeXpath(driver, "//*[@id='rightScroll1']/div[6]/div[1]/div[2]/div[4]/div/span[2]/span[1]");
		/* orderDetailList[13]=fetchTextFromElement(partialQtyLabel); */
		Reporter.log("Nest id : "+orderDetailList[11],true);
		Reporter.log("Inside orderDetailProvider method......",true);
		return orderDetailList;
	}
	
	public void amoCheckbox(String checked) throws InterruptedException {
		Reporter.log("AMO CheckBox checking....",true);
		//Thread.sleep(3000);
		if(checked.equalsIgnoreCase("true")) {
			try {
		boolean flag = elementPresentOrNot(driver,"//label[@class='amo-text rect-label']","xpath","AMO check box");
		Reporter.log("After Checking amo checkbox present....",true);
		if(flag) {
			WebElement amoCheckBox =fluentWaitCodeXpath(driver,"//label[@class='amo-text rect-label']","AMO check box");
			boolean amoFlag = amoCheckBox.isEnabled();
			if(amoFlag)
			{
				clickElement(amoCheckBox,"AMO check box");
			}
		}
		}catch(TimeoutException e) {
			Reporter.log("AMO CheckBox does not present",true);
		}
		}
		
	}
	
	public void afterRefreshPage(WebDriver driver) throws InterruptedException {
		
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[1]/ul/li/a/strong");
		orderStatusLink=fluentWaitCodeXpath(driver, "//a[text()='Order Status' and @class='toc-tab-link']","Order Status Tab");
		clickElement(orderStatusLink,"Order Status Tab");
	}

	/*
	 * public void printElement(WebDriver driver) throws InterruptedException {
	 * String[] orderDetailArray = null; orderDetailArray =
	 * orderDetailProvider(driver, "New"); for (String element : orderDetailArray) {
	 * Reporter.log(element); } }
	 */
	
	public void commodityorderDetail() {
		List<String> orderDetailList=new ArrayList<String>();
		orderLogsLabel=fluentWaitCodeXpath("//*[@id='rightScroll1']/div[5]/div[1]/div[2]/div[5]/div/a", "Order Logs link");
		clickElement(orderLogsLabel,  "Order Logs link");
		orderStatusLink=fluentWaitCodeXpath("//*[@id='rightScroll1']/div[5]/div[1]/div[2]/div[5]/div/span[1]", "Order status");
		String status=fetchTextFromElement(orderStatusLink);
		orderDetailList.add("Order status : "+status);
		if(status.equalsIgnoreCase("rejected")) {
			rejectReasonLable=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[3]/ul/li/span[2]", "Reject reason");
			orderDetailList.add("Rejection reason : "+fetchTextFromElement(rejectReasonLable));
		}
		disclosedLabel=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[1]/ul/li[1]/span[2]", "Disclosed");
		orderDetailList.add("Disclosed Qty : "+fetchTextFromElement(disclosedLabel));
		triggerPriceLabel=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[1]/ul/li[2]/span[2]", "Trigger price");
		orderDetailList.add("Trigger Price : "+fetchTextFromElement(triggerPriceLabel));
		orderTypeLabel=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[1]/ul/li[3]/span[2]", "Order type");
		orderDetailList.add("Order type : "+fetchTextFromElement(orderTypeLabel));
		exchangeLabel=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[2]/ul/li[2]/span[2]", "Exchange");
		orderDetailList.add("Exchange : "+fetchTextFromElement(exchangeLabel));
		userIdLabelCo=fluentWaitCodeXpath("//*[@id='accitem']/div/div/div[2]/ul/li[1]/span[2]", "Order placed by");
		orderDetailList.add("User id : "+fetchTextFromElement(userIdLabelCo));
		scriptNameLabel=fluentWaitCodeXpath("//*[@id='rightScroll1']/div[5]/div[1]/div[2]/div[2]/div/span[1]", "Script Name");
		orderDetailList.add("Script Name : "+fetchTextFromElement(scriptNameLabel));
		tradingSymbol=fluentWaitCodeXpath("//*[@id='rightScroll1']/div[5]/div[1]/div[2]/div[2]/div/span[2]", "Trading Symbol");
		orderDetailList.add("Trading symbol : "+fetchTextFromElement(tradingSymbol));
		productType=fluentWaitCodeXpath("//*[@id='rightScroll1']/div[5]/div[1]/div[2]/div[4]/div/span[2]", "Product type");
		orderDetailList.add("Product type : "+fetchTextFromElement(productType));
	}

}
