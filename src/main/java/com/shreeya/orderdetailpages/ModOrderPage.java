package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ApacheCode;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.HelperCode;
import com.shreeya.util.SeleniumCoder;

public class ModOrderPage extends SeleniumCoder {

	WebElement modifyLink;
	WebElement noOfSharesTextField;
	private WebElement placeOrderButton;
	private WebElement confirmButton;
	private WebElement enterPriceTextField;
	private WebElement cnsRadioButton;
	private WebElement OptionalFieldsLabel;
	
	OrderDetail detail;
	private WebElement reinvestLink;
	private boolean rejectFlag=false;

	public HashMap<WebDriver,String> modExecution(TestDataModel model, WebDriver driver,int orderNo,String newOrderStatus) throws InterruptedException, IOException {
		Reporter.log("<===@@@ OrderNo in Sheet "+model.getOrderNo()+" Action : "+model.getAction()+" @@@@===>",true);
		HashMap<WebDriver,String> mapObject=new HashMap<WebDriver,String>();
		CsvReaderCode csvReader=new CsvReaderCode();
		HelperCode helperObject=new HelperCode();
		ConfigReader configReader=new ConfigReader();
		String amoFlag=configReader.configReader("amoFlag");
		detail=new OrderDetail();
		Reporter.log("New Order Status ====> "+newOrderStatus,true);
		if(newOrderStatus.equalsIgnoreCase("Open")||newOrderStatus.equalsIgnoreCase("after market order req received")) {
		//Thread.sleep(7000);
		try {
		reinvestLink=fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[6]/div/ul/li/a");
		Reporter.log("ReinvestLink is present",true);
		}catch(NoSuchElementException e) {

		}
		
		modifyLink =fluentWaitCodeXpath(driver,"//*[@id=\"rightScroll1\"]/div[6]/div[1]/div[2]/div[6]/div/ul/li[1]/a");
		clickElement(modifyLink,"Modify link");
		//Thread.sleep(5000);
		if(model.getScenario().equalsIgnoreCase("Modification Qty")) {
		noOfSharesTextField =fluentWaitCodeXpath(driver,"//input[@placeholder='No. of Shares']");
		clearAndSendKey(noOfSharesTextField, model.getQtyMod(),"No of shares textfield");
		}
		//Thread.sleep(3000);
		if(model.getScenario().equalsIgnoreCase("Modification Price")) {
		enterPriceTextField =fluentWaitCodeXpath(driver,"//input[@placeholder='Enter Price']");
		clearAndSendKey(enterPriceTextField, model.getPartialPrice(),"enter Price TextField");
		}
		//Thread.sleep(3000);
		if (model.getProductType().equalsIgnoreCase("CNC")) {

			cnsRadioButton =fluentWaitCodeXpath(driver,"//label[text()='Delivery CNC']");
			if (cnsRadioButton.isSelected() == true)
				clickElement(cnsRadioButton,"CNC Radio Button");
		}

		OptionalFieldsLabel =fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[3]/div[2]/div/div[2]/div/form/div[2]/div[3]/div[1]/div[1]");
		clickElement(OptionalFieldsLabel,"Optional Fields Label");
		//Thread.sleep(2000);
		detail.amoCheckbox(amoFlag, driver);
		placeOrderButton =fluentWaitCodeXpath(driver,"//input[@value ='Place Order']");
		clickElement(placeOrderButton,"Place Order Button");
		//Thread.sleep(2000);
		confirmButton =fluentWaitCodeXpath(driver,"//input[@value='Confirm']");
		clickElement(confirmButton,"Confirm Button");
		
		
		}
		/*String status=helperObject.outputProcessor(driver, "Mod", orderNo,newOrderStatus,model);*/
		mapObject.put(driver, "dfsd");
		return mapObject;
	}

}
