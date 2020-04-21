package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.TestDataModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
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
	private WebElement productTypeRadioButton;
	private WebElement bseLink;
	
	

	public HashMap newOrderExecution(TestDataModel model,WebDriver driver,int orderNo) throws InterruptedException, IOException {
		Reporter.log("<====@@@@ OrderNo in Sheet "+model.getOrderNo()+" Action : "+model.getAction()+" @@@@===>",true);	
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		ConfigReader configReader=new ConfigReader();
		String amoFlag=configReader.configReader("amoFlag");
		HelperCode helperObject=new HelperCode();
		CsvReaderCode csvReader=new CsvReaderCode();
		OrderDetail orderDetail=new OrderDetail();
		detail=new OrderDetail();
		//Thread.sleep(7000);
		Reporter.log("New Order execution Started..........",true);
		if(orderNo!=1) {
			placeOrderButon=fluentWaitCodeXpath(driver,"//a[text()='Place Order']");
			clickElement(placeOrderButon,"Place order Link");
		}
	
		placeOrderTextField=fluentWaitCodeXpath(driver,"//*[@id='tocsearch']");
		sendKey(placeOrderTextField,model.getScript(),"Place Order Textfield");
		//sendKeyClickOnDownArrow(placeOrderTextField,model.getScript());
		/*Thread.sleep(3000);*/
		if(model.getSegment().equalsIgnoreCase("NSE")) {
		nseLink=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[1]/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]");
		clickElement(nseLink,"NSE Link");
		}else if(model.getSegment().equalsIgnoreCase("BSE")) {
		bseLink=fluentWaitCodeXpath(driver, "//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[1]/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]");
		clickElement(bseLink,"BSE Link");
		}
		
		
		
		//downErrorKeyEnter(placeOrderTextField);
		/*Thread.sleep(2000);*/
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
		buyButton=fluentWaitCodeXpath(driver,"//a[text()='Buy']");
		clickElement(buyButton,"Buy button");
		}else if(model.getOrderType().equalsIgnoreCase("Sell")) {
			buyButton=fluentWaitCodeXpath(driver, "//a[text()='Sell']");
			clickElement(buyButton,"Sell button");
		}
		/*Thread.sleep(4000);*/
		
		Thread.sleep(2000);
		productType(driver, model.getProductType());
		noOfSharesTextField=fluentWaitCodeXpath(driver,"//input[@placeholder='No. of Shares']");
		if(model.getScenario().equalsIgnoreCase("Partial Order")) {
			clearAndSendKey(noOfSharesTextField,model.getQtyMod(),"No of shares Textfield (Mod Qty)");
		}else
		clearAndSendKey(noOfSharesTextField,model.getQty(),"No of shares Textfield ");
		/*Thread.sleep(2000);*/
		enterPriceTextField=fluentWaitCodeXpath(driver,"//input[@placeholder='Enter Price']");
		sendKey(enterPriceTextField, model.getOrderPrice(),"Enter Price TextField");
		
		OptionalFieldsLabel=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]");
		clickElement(OptionalFieldsLabel,"OptionalFields Label");
		/*Thread.sleep(1000);*/
		
		orderDetail.amoCheckbox(amoFlag, driver);
		placeOrderButton=fluentWaitCodeXpath(driver,"//input[@value ='Place Order']");
		clickElement(placeOrderButton,"Place Order Button");
		confirmButton=fluentWaitCodeXpath(driver,"//input[@value='Confirm']");

		//confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
		clickElement(confirmButton,"Confirm Button");
		
		/*Thread.sleep(3000);*/
		
		
		/*String status=helperObject.outputProcessor(driver, "New", orderNo,"No status",model);*/
		mapObject.put(driver, "dfs");
		return mapObject;
	}

	public void productType(WebDriver driver,String productTypeStr) {
		Reporter.log("This is product type ====> "+productTypeStr,true);
		if(productTypeStr.equalsIgnoreCase("CNC")) {
			productTypeRadioButton=fluentWaitCodeXpath(driver,"//label[text()='Delivery CNC']");
			selectRadioButton(productTypeRadioButton, "CNS Product type");
		}else if(productTypeStr.equalsIgnoreCase("MTF")) {
			productTypeRadioButton=fluentWaitCodeXpath(driver,"//label[text()='Margin Trading MTF']");
			selectRadioButton(productTypeRadioButton, "MTF Product type");
		}else if(productTypeStr.endsWith("NRML")) {
			productTypeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='Delivery Plus -  NRML']");
			selectRadioButton(productTypeRadioButton, "NRML Product type");
		}
	}
	
}
