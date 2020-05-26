package com.shreeya.watchlistPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.SeleniumCoder;

public class WatchListCommon extends SeleniumCoder{
	
	WebDriver driver;
	String tabName;

	public WatchListCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public void WatchListCommon() {}
	
	public String pageVerify(WatchListModel model,String step) {
		driver.navigate().refresh();
		if(!(step.contains("Delete"))) {
		String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		
		WebElement createWatchList=fluentWaitCodeXpath(createdWatchlistTab,10,"watchList");	
		if(createWatchList!=null) {
		clickElement(createWatchList,model.getWatchListName()+" Watchlist Tab");
		tabName=model.getWatchListName();
		}
		else {
			tabName=commodityTabClicker(model.getWatchListName(),model.getExchange());
			if(tabName.equalsIgnoreCase("tab")) 
				tabName=tabNotFound(model.getWatchListName(),model.getExchange());
			
			}
		}	
		return tabName;
		}
	
	public String tabNotFound(String watchListName,String exchange) {
		if(exchange.equalsIgnoreCase("NSE")||exchange.equalsIgnoreCase("BSE")||
				exchange.equalsIgnoreCase("CDS")||exchange.equalsIgnoreCase("FNO")||exchange.equalsIgnoreCase("NFO")) {
		clickElement("//a[text()='Select Watchlist']", "Select watchList button");
		String watchListOptionxpath="//a[text()='"+watchListName+"']";
		clickElement(watchListOptionxpath, watchListName+" option ");
		}
		return watchListName;
	}
	
	public String exchangeFilter(String exchange) {
		Reporter.log(exchange, true);
		String [] exchangeArray=exchange.split("amp;");
		exchange=exchangeArray[0]+exchangeArray[1];
		return exchange;
	}
	
	public String commodityTabClicker(String watchListName,String exchange) {
		String tabString="tab";
		if(exchange.equalsIgnoreCase("MCX")||exchange.equalsIgnoreCase("NCDEX")) {
		WebElement selectWatchList=fluentWaitCodeXpath("//a[text()='Select Watchlist']", "Select watchList");
		clickElement(selectWatchList, "Select WatchList tab");
		
			List<WebElement> elements=multipleElementLocator("//*[@id='contentCntr']/div/div/div[1]/ul/li[7]/ul/li/span", "User created watchList tab");
			for(int i=elements.size();i>2+1;i--) {
				tabString="//*[@id='contentCntr']/div/div/div[1]/ul/li[7]/ul/li/span["+i+"]/a";
				WebElement watchlistTab=fluentWaitCodeXpath(tabString, "WatchList Tab");
				tabString=fetchTextFromElement(watchlistTab);
				if(tabString.contains(watchListName)) {
					clickElement(watchlistTab, "WatchList tab");
					tabString=watchListName;
					break;
				}
			}
		}
		return tabString;
	}
}
