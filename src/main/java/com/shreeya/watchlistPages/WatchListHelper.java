package com.shreeya.watchlistPages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.SeleniumCoder;

public class WatchListHelper extends SeleniumCoder{
	
	
	WebDriver driver;
	WatchListPage watchListPage;
	public WatchListHelper(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
		watchListPage=new WatchListPage();
	}
	
	String [] scriptNameArray;
	String scriptName;
	WebElement scriptNameLabel;
	WebElement scriptCheckBox;
	WebElement deleteButton;
	WebElement popupDeleteButton;
	WebElement okButton;

	public void deleteScript(WatchListModel model) {
		Reporter.log("deleteScript", true);
		scriptNameArray=watchListPage.scriptNames(model.getVerifyScript());
		int scriptCount=scriptNameArray.length+2;
		for(int i=2;i<scriptCount;i++) {
			scriptNameLabel=fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a", "Script Name");
			scriptName=fetchTextFromElement(scriptNameLabel);
			if(scriptName.contains(model.getDeleteScript())) {
				
				String scriptBox="//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/div[1]/input";
				scriptCheckBox=fluentWaitCodeXpath(scriptBox, "Script checkBox");
					clickElement(scriptCheckBox, "ScriptCheckBox");
					break;
			}
		}
		deleteButton=fluentWaitCodeXpath("//a[text()='Delete Scrip']", "Delete Button");
		clickElement(deleteButton, "Delete Button");
		popupDeleteButton=fluentWaitCodeXpath("//button[text()='Delete']", "popup Delete Button");
		clickElement(popupDeleteButton, "popup Delete Button");
		okButton=fluentWaitCodeXpath("//button[text()='Ok']", "Ok button");
		clickElement(okButton, "Ok button");
	}
}
