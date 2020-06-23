package com.shreeya.commonpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.OrderPlaceModel;
import com.shreeya.orderdetailpages.OrderDetail;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class PlaceOrder extends SeleniumCoder {

	WebDriver driver;
	ConfigReader configReader;
	private WebElement buyButton;
	private WebElement placeOrderButton;
	private WebElement confirmButton;
	private WebElement noOfSharesTextField;
	private WebElement enterPriceTextField;
	private WebElement productTypeRadioButton;
	
	OrderDetail orderDetail;
	private WebElement placeOrderTextField;
	private WebElement bseLink;
	private WebElement nseLink;
	
	public PlaceOrder(WebDriver driver) {
		super(driver);
		this.driver=driver;
		configReader=new ConfigReader();
		orderDetail=new OrderDetail(driver);
	}
	
	public String orderPlace(OrderPlaceModel model) {
		Reporter.log("====> orderPlace <====", true);
		String amoFlag=configReader.configReader("amoFlag");
		placeOrderTextField=fluentWaitCodeXpath(driver,"//*[@id='tocsearch']","Place Order Textfield");
		sendKey(placeOrderTextField,model.getScriptName(),"Place Order Textfield");
		clickOnExchageScript(model.getExchange());
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
			buyButton=fluentWaitCodeXpath("//a[text()='Buy']","Buy button");
			clickElement(buyButton,"Buy button");
			}else if(model.getOrderType().equalsIgnoreCase("Sell")) {
				buyButton=fluentWaitCodeXpath("//a[text()='Sell']","Sell button");
				clickElement(buyButton,"Sell button");
			}
		
			String screenshot=ScreenshortProvider.captureScreen(driver, "OrderPlacementPage");
			staticWait(1000);
			productType(model.getProductType(),model.getExchange());
			qtyTextfield(model.getQty(),model.getExchange());
			priceTextField(model.getOrderPrice(), model.getExchange());
			
			
			orderDetail.amoCheckbox(amoFlag);
			placeOrderButton=fluentWaitCodeXpath("//input[@value ='Place Order']","Place Order Button");
			clickElement(placeOrderButton,"Place Order Button");
			
			try {
			confirmButton=fluentWaitCodeXpath("//input[@value='Confirm']",30,"Place Order Button");
			}catch(Exception e) {
				clickElement(placeOrderButton,"Place Order Button");
			}

			//confirmButton=driver.findElement(By.xpath("//input[@value='Confirm']"));
			clickElement(confirmButton,"Confirm Button");
			
			return screenshot;
	}
	
	public void qtyTextfield(String qty,String exchange) {
		
	Reporter.log("<========== qtyTextfield =========>", true);
		if(exchange.contains("CDS")||exchange.contains("NFO")||exchange.contains("FNO")||exchange.contains("NCDEX")||exchange.contains("MCX")) 
			noOfSharesTextField=fluentWaitCodeXpath("//input[@id='tocshare']", "Quantity");
		else 
			noOfSharesTextField=fluentWaitCodeXpath("//input[@placeholder='No. of Shares']","NO of shares textfield");
		
		clearAndSendKey(noOfSharesTextField,qty,"No of shares Textfield ");
	}
	
	public void priceTextField(String price,String exchange) {
		Reporter.log("<========== priceTextField =========>", true);
	
		if(exchange.contains("CDS")||exchange.contains("NFO")||exchange.contains("FNO")||exchange.contains("NCDEX")||exchange.contains("MCX")) 
			enterPriceTextField=fluentWaitCodeXpath("//input[@name='pricePerShare']","Enter Price TextField");
		else
			enterPriceTextField=fluentWaitCodeXpath("//input[@placeholder='Enter Price']","Enter Price TextField");
		
		sendKey(enterPriceTextField,price,"Enter Price TextField");
		
	}
	
	public void productType(String productTypeStr,String exchange) {
		Reporter.log("This is product type ====> "+productTypeStr,true);
		if(exchange.contains("CDS")||exchange.contains("NFO")||exchange.contains("FNO")||
				exchange.contains("NCDEX")||exchange.contains("MCX")) {
			if(productTypeStr.equalsIgnoreCase("NRML")) {
				productTypeRadioButton=fluentWaitCodeXpath("//label[text()='FNO Plus -  NRML']", "FNO NRML");
				selectRadioButton(productTypeRadioButton, "FNO NRML");
			}else if(productTypeStr.equalsIgnoreCase("Intraday MIS")) {
				productTypeRadioButton=fluentWaitCodeXpath("//label[text()='Intraday MIS']", "Intraday MIS");
				selectRadioButton(productTypeRadioButton, "Intraday MIS");
			}
		}else {
		if(productTypeStr.equalsIgnoreCase("CNC")) {
			productTypeRadioButton=fluentWaitCodeXpath("//label[text()='Delivery CNC']", "CNS Product type");
			selectRadioButton(productTypeRadioButton, "CNS Product type");
		}else if(productTypeStr.equalsIgnoreCase("MTF")) {
			productTypeRadioButton=fluentWaitCodeXpath("//label[text()='Margin Trading MTF']", "MTF Product type");
			selectRadioButton(productTypeRadioButton, "MTF Product type");
		}else if(productTypeStr.endsWith("NRML")) {
			productTypeRadioButton=fluentWaitCodeXpath("//label[text()='Delivery Plus -  NRML']", "NRML Product type");
			selectRadioButton(productTypeRadioButton, "NRML Product type");
		}
		}
	}
	
	public void clickOnExchageScript(String exchange) {
		if(exchange.equalsIgnoreCase("NSE")) {
			nseLink=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div[1]/div/div[1]/div[1]/div/div/div/div[1]/div/div/ul/li[1]/a/span[2]","NSE Link");
			clickElement(nseLink,"NSE Link");
			}else if(exchange.equalsIgnoreCase("BSE")) {
			bseLink=fluentWaitCodeXpath(driver, "//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div[1]/div/div[1]/div[1]/div/div/div/div[1]/div/div/ul/li[2]/a/span[2]","BSE Link");
			clickElement(bseLink,"BSE Link");
			}
	}
}
