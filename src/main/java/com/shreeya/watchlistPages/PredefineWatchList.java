package com.shreeya.watchlistPages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.orderdetailpages.OrderDetail;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class PredefineWatchList extends SeleniumCoder{
	WebDriver driver;
	
	String scriptNametext;
	private WebElement orderPlaceSearchTextField;
	private WebElement buyButton;
	private WebElement productTypeRadioButton;
	private WebElement noOfSharesTextField;
	private WebElement enterPriceTextField;
	private WebElement OptionalFieldsLabel;
	private WebElement placeOrderButton;
	private WebElement confirmButton;
	
	OrderDetail orderDetail;
	ConfigReader configReader;
	WatchListPage watchListPage;
	
	public static String [] orderDetailArray;
	
	public PredefineWatchList(WebDriver driver) {
		super(driver);
		this.driver=driver;
		orderDetail=new OrderDetail(driver);
		configReader=new ConfigReader();
		watchListPage=new WatchListPage();
	}
	
	public PredefineWatchList() {}
	
	public void placeOrder(WatchListModel model) throws InterruptedException {
		String amoFlag=configReader.configReader("amoFlag");
		if(model.getOrderType().equalsIgnoreCase("Buy")) {
			buyButton=fluentWaitCodeXpath("//a[text()='Buy']","Buy button");
			clickElement(buyButton,"Buy button");
			}else if(model.getOrderType().equalsIgnoreCase("Sell")) {
				buyButton=fluentWaitCodeXpath("//a[text()='Sell']","Sell button");
				clickElement(buyButton,"Sell button");
			}
			/*Thread.sleep(4000);*/
			
			Thread.sleep(1000);
			productType(model.getProductType());
			noOfSharesTextField=fluentWaitCodeXpath("//input[@placeholder='No. of Shares']","NO of shares textfield");
			
			clearAndSendKey(noOfSharesTextField,model.getQty(),"No of shares Textfield ");
			/*Thread.sleep(2000);*/
			enterPriceTextField=fluentWaitCodeXpath("//input[@placeholder='Enter Price']","Enter Price TextField");
			sendKey(enterPriceTextField, model.getOrderPrice(),"Enter Price TextField");
			
			OptionalFieldsLabel=fluentWaitCodeXpath("//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]","OptionalFields Label");
			clickElement(OptionalFieldsLabel,"OptionalFields Label");
			/*Thread.sleep(1000);*/
			
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
			

	}
	
	public void productType(String productTypeStr) {
		Reporter.log("This is product type ====> "+productTypeStr,true);
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
	
	public void trading(WatchListModel model,ExtendReporter reporter) throws InterruptedException {
		
		
		String tradeButtonxpath="//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname=' "+model.getScriptName()+" ']";
		try {
			clickElement(tradeButtonxpath,model.getScriptName()+" Trade button");
			orderPlaceSearchTextField=fluentWaitCodeXpath("//input[@id='tocsearch']", "Order Place Textfield");
			scriptNametext=fetchTextFromElement(orderPlaceSearchTextField);
			Reporter.log("orderPlaceSearchTextField : "+scriptNametext , true);
			placeOrder(model);
			orderDetailArray=orderDetail.orderDetailProvider(driver, "New", "NO order sheet");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String orderDetail:orderDetailArray) {
			Reporter.log(orderDetail, true);
		}
		
	}
	
	
}
