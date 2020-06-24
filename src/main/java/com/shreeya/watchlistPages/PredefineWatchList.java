package com.shreeya.watchlistPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.orderdetailpages.OrderDetail;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
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
	private WebElement lastTradePriceLabel; 
	private WebElement changeLabel;
	private WebElement volumeLabel;
	private WebElement bidPriceLabel;
	private WebElement askPriceLabel;
	private WebElement lowLabel;
	private WebElement hightabel;
	private WebElement exchangeLabel;
	private WebElement closeButton;
	private WebElement tradingSymbolLabel;
	
	
	String lastTradePriceText;
	String changeText;
	String volumeText;
	String bidPriceText;
	String askPriceText;
	String lowText;
	String hightText;
	String tradingSymbolStr;
	String tradingSymbolXpath;
	String scriptName="ScriptName:PredefineWatchList";
	String exchange;
	String [] predefineWatchListArray;
	List<String> predefineWatchListDetail;
	
	OrderDetail orderDetail;
	ConfigReader configReader;
	WatchListPage watchListPage;
	Help help;
	String predifineWatchMsg;
	String errorMsg;

	private int numberScript;

	private String[] scriptNameArray;

	private WebElement scriptNameLabel;

	private WebElement tradeButton;

	private String noOfScripStr;

	private List<WebElement> scriptList;

	private String scriptCount;

	private int scriptCountInt;
	
	public static String [] orderDetailArray;
	
	public PredefineWatchList(WebDriver driver) {
		super(driver);
		this.driver=driver;
		orderDetail=new OrderDetail(driver);
		help=new Help();
		configReader=new ConfigReader();
		watchListPage=new WatchListPage();
		predefineWatchListDetail=new ArrayList<String>();
	}
	
	public PredefineWatchList() {}
	
	public String placeOrder(WatchListModel model)  {
		String amoFlag=configReader.configReader("amoFlag");
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
	
	public List<String> trading(WatchListModel model)  {
		String screenshot = null;
		predefineWatchListDetail=new ArrayList<String>();
		clickOnPredefineWatchList(model);
		//String scriptName=model.getScriptName().trim().replace(" ", "  ");
		/*
		 * String
		 * tradeButtonxpath="//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname=' "
		 * +model.getScriptName()+" ']";
		 * 
		 * WebElement
		 * tradeButton=fluentWaitCodeXpath(tradeButtonxpath,30,"Trading button");
		 * /if(tradeButton==null) {
		 * tradeButtonxpath="//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname=' "
		 * +scriptName+" ']"; tradeButton=fluentWaitCodeXpath(tradeButtonxpath,
		 * "Trading button"); }
		 * clickElement(tradeButton,model.getScriptName()+" Trade button");
		 */
		staticWait(2000);
		noOfScripStr=fetchTextFromElement("//span[@class='count ng-scope']",300,"No of script label");
		scriptList=multipleElementLocator("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div/div[1]/div[1]/a", "Script list");
		scriptCount=String.valueOf(scriptList.size());
		Reporter.log("Script count : "+scriptCount, true);
		scriptNameArray = help.commaSeparater(model.getVerifyScript());
		 scriptCountInt = scriptList.size() + 2;
		for (int i = 2; i < scriptCountInt; i++) {
			scriptNameLabel = fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a","Script Name");
			scriptName = fetchTextFromElement(scriptNameLabel);
			if (scriptName.contains(scriptNameArray[scriptNameArray.length - 1])) {
				Reporter.log("Script : " + scriptNameArray[scriptNameArray.length - 1]);
				tradeButton=fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[11]/a", "trade button");
				clickElement(tradeButton, "ScriptCheckBox");
				break;
			}

		}
			orderPlaceSearchTextField=fluentWaitCodeXpath("//input[@id='tocsearch']", "Order Place Textfield");
			scriptNametext=fetchTextFromElement(orderPlaceSearchTextField);
			Reporter.log("orderPlaceSearchTextField : "+scriptNametext , true);
			 screenshot=placeOrder(model);
			orderDetailArray=orderDetail.orderDetailProvider(driver, "New", "NO order sheet",model);
			
		
		predefineWatchListDetail.add(screenshot);
		for(String orderDetail:orderDetailArray) {
			if(!(orderDetail.equalsIgnoreCase("no id")||orderDetail.contains("no")||
			orderDetail.equalsIgnoreCase("ScriptResult")||orderDetail.equalsIgnoreCase("Report link")||
			orderDetail.equalsIgnoreCase("Screenshot link1")||orderDetail.equalsIgnoreCase("Partial Qty")))
			predefineWatchListDetail.add(orderDetail);
		}
		predefineWatchListDetail.add(ScreenshortProvider.captureScreen(driver, "watchList"));
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",5,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		return predefineWatchListDetail;
	}
	
	public void clickOnPredefineWatchList(WatchListModel model) {
		Reporter.log(model.toString(), true);
		String predefineWatchXpath="//a[text()='"+model.getWatchListName()+"']";
		try {
			WebElement predefineWatchTab=fluentWaitCodeXpath(predefineWatchXpath, "Predefine WatchList tab");
			clickElementWithOutChecking(predefineWatchTab, "Predefine watchList tab");
		}catch(TimeoutException e) {
			tabNotFound(model.getWatchListName());
		}
	}
	
	public void tabNotFound(String watchListName) {
		clickElement("//a[text()='Select Watchlist']", "Select watchList button");
		String watchListOptionxpath="//a[text()='"+watchListName+"']";
		clickElement(watchListOptionxpath, watchListName+" option ");
	}
	
	public int predefineWatchListScript() {
		int numberScript=0;
		do {
		List<WebElement> predefineScript=multipleElementLocator("//div[@class='ed-td ed-stock text-left']", "Scripts");
		 numberScript=predefineScript.size();
		 staticWait(100);
		}while(numberScript==0);
		return numberScript;
	}
	
	public int compareNoScriptAndNoScriptLable() {
		Reporter.log("====> compareNoScriptAndNoScriptLable <====", true);
		
		numberScript=predefineWatchListScript();
		Reporter.log("numberScript : "+numberScript, true);
		WebElement predifineWatchMsgLabel=fluentWaitCodeXpath("//span[text()='Your watchlist has ']", "Predefine WatchList massage");
		predifineWatchMsg=fetchTextFromElement(predifineWatchMsgLabel);
		predifineWatchMsg=removeExtrahmtlCode(predifineWatchMsg);
		Reporter.log("predifineWatchMsg : "+predifineWatchMsg, true);
		errorMsg=predifineWatchMsg;
		String [] numberScriptArray=errorMsg.split(" ");
		int totalScript=Integer.valueOf(numberScriptArray[3]);
		if(numberScript==totalScript) {
			errorMsg=errorMsg+"-PASS";
			predefineWatchListDetail.add(errorMsg);
		}else {
			errorMsg=errorMsg+"-FAIL";
			predefineWatchListDetail.add(errorMsg);
		}
		Reporter.log("numberScript : "+numberScript, true);
		return numberScript;
	}
	
	public void checkExchangeInPreWatchList(String watchListName,int noScript) {
		Reporter.log("=============> checkExchangeInPreWatchList <============");
		
		boolean matchExchage=true;
		String exchange = null;
		if(watchListName.equalsIgnoreCase("Nifty 50")) {
			exchange="NSE";
		}else if(watchListName.equalsIgnoreCase("Sensex")){
			exchange="BSE";
		}
		predefineWatchListDetail.add("============@@> Verify "+watchListName+" Contain "+exchange+"  <@@============");
		for(int i=2;i<noScript+2;i++) {
			String exchangeXapth="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a/span/small";
			WebElement exchangeLabel=fluentWaitCodeXpath(exchangeXapth, "Exchange Label");
			String exchangeStr=fetchTextFromElement(exchangeLabel);
			tradingSymbolXpath="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a";
			tradingSymbolLabel=fluentWaitCodeXpath(tradingSymbolXpath, "Trading symbol");
			if(tradingSymbolLabel==null) {
				int unMatchScriptNo=i-2;
				predefineWatchListDetail.add("Script "+unMatchScriptNo+" no not found."+watchListName+" does't contain "+noScript+" script");
			}
			if(!exchangeStr.equalsIgnoreCase(exchange)) {
				
				tradingSymbolXpath="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a";
				tradingSymbolLabel=fluentWaitCodeXpath(tradingSymbolXpath, "Trading symbol");
				tradingSymbolStr=elementsTextFilter(fetchTextFromElement(tradingSymbolLabel));
				
				predefineWatchListDetail.add(exchange+" trading symbol : "+tradingSymbolStr+"-FAIL");
				predefineWatchListDetail.add(ScreenshortProvider.captureScreen(driver,"UnmatchExchangeInPreWatch"));
				Reporter.log("Unmatch exchange script name  : "+tradingSymbolStr, true);
				matchExchage=false;
				break;
			}
			
			
		}
		if(matchExchage) {
			predefineWatchListDetail.add(watchListName+" contain "+noScript+" "+exchange+" exchange script...-PASS");
		}
	}
	
	public List<String> clickAnyOption(WatchListModel model) {
		Reporter.log("====> clickAnyOption  <=====", true);
		predefineWatchListArray=help.commaSeparater(model.getWatchListName());
		
		for(String predefineWatchList:predefineWatchListArray) {
			
			model.setWatchListName(predefineWatchList);
		predefineWatchListDetail.add("PredefineWatchList Name : "+model.getWatchListName());
		
		clickOnPredefineWatchList(model);
		staticWait(2000);
		int totalScript=compareNoScriptAndNoScriptLable();
		Reporter.log("Outside : totalScript : "+totalScript, true); 
		//String [] scriptCountArray=watchListPage.predifineWatchMsg.split(" ");
		int noScript=totalScript;
		for(int i=2;i<noScript;i++) {
			String xpath="//*[@id=\"contentCntr\"]/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a";
			WebElement scriptLable=fluentWaitCodeXpath(xpath, "scriptLabel");
			 scriptName=fetchTextFromElement(scriptLable);
			if(i==3){
				noScript=i;
				break;
			}
		}
		Reporter.log("noScript : "+noScript,true);
		Reporter.log("PredefineWatchList : scriptName : "+scriptName, true);
		//String [] scriptArray=help.removeHtmlReporter(scriptName)
		String [] scriptArray=help.removeHtmlAndSeparateWithChara(scriptName, " ");
		predefineWatchListDetail.add("Script Name : "+scriptArray[1]);
		
		exchangeLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div[2]/div[1]/div[1]/a/span/small", "Exchange");
		predefineWatchListDetail.add("Exchange : "+fetchTextFromElement(exchangeLabel));
		
		lastTradePriceLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[2]/span", "Last trade price");
		if(lastTradePriceLabel!=null) {
			lastTradePriceText=fetchTextFromElement(lastTradePriceLabel);
			predefineWatchListDetail.add("Last Traded Price : "+lastTradePriceText);
		}
		
		changeLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[3]/span", "change");
		if(changeLabel!=null) {
			changeText=fetchTextFromElement(changeLabel);
			predefineWatchListDetail.add("Change : "+changeText);
		}
		
		volumeLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[4]/span", "volume");
		if(volumeLabel!=null) {
			volumeText=fetchTextFromElement(volumeLabel);
			predefineWatchListDetail.add("Volume : "+volumeText);
		}
		bidPriceLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[5]/span", "Bid Price");
		if(bidPriceLabel!=null) {
			bidPriceText=fetchTextFromElement(bidPriceLabel);
			predefineWatchListDetail.add("Bid Price : "+bidPriceText);
		}
		askPriceLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[6]/span", "Ask Price");
		if(askPriceLabel!=null) {
			askPriceText=fetchTextFromElement(askPriceLabel);
			predefineWatchListDetail.add("ask Price : "+askPriceText);
		}
		
		lowLabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[7]/span", "Low");
		if(lowLabel!=null) {
			lowText=fetchTextFromElement(lowLabel);
			predefineWatchListDetail.add("Low : "+lowText);
		}
		
		hightabel=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[8]/span", "hight");
		if(hightabel!=null) {
			hightText=fetchTextFromElement(hightabel);
			predefineWatchListDetail.add("hight : "+hightText);
		}
		String xpath="//*[@id='contentCntr']/div/div/div[1]/div[3]/div/div/div/div/div[2]/div["+noScript+"]/div[1]/div[1]/a";
		WebElement scriptLable=fluentWaitCodeXpath(xpath, "scriptLabel");
		//clickElement(xpath, "Script link");
		predefineWatchListDetail.add(ScreenshortProvider.captureScreen(driver, "WatchList"));
		
		checkExchangeInPreWatchList(model.getWatchListName(),totalScript);
		}
		model.setWatchListName(predefineWatchListArray[0]);
		return predefineWatchListDetail;
	}
	
	
	
	
}
