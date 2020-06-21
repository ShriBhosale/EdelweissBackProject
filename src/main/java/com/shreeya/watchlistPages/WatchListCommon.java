package com.shreeya.watchlistPages;

import org.openqa.selenium.WebDriver;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.SeleniumCoder;

public class WatchListCommon extends SeleniumCoder{
	
	WebDriver driver;

	public WatchListCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public void WatchListCommon() {}
	
	public void pageVerify(WatchListModel model,String step) {
		driver.navigate().refresh();
		if(!(step.contains("Delete"))) {
		String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		try {
		clickElement(createdWatchlistTab,model.getWatchListName()+" Watchlist Tab");
		}catch(NullPointerException e) {
			tabNotFound(model.getWatchListName());
		}
		}
		}
	
	public void tabNotFound(String watchListName) {
		clickElement("//a[text()='Select Watchlist']", "Select watchList button");
		String watchListOptionxpath="//a[text()='"+watchListName+"']";
		clickElement(watchListOptionxpath, watchListName+" option ");
	}
	
	
}
