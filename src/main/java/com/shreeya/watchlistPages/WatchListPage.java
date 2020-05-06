package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class WatchListPage extends SeleniumCoder{

	WebElement newWatchListTab;
	WebElement watchListNameTextfield;
	WebElement defaultWatchListCheckBox;
	WebElement bseRadioButton;
	WebElement nseRadioButton;
	WebElement defaultWatchList;
	WebDriver driver;
	public static String createWatchListPath;
	public static String deleteWatchListPath;
	
	String deleteOption="present";

	public WatchListPage(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
	}
	
	
	
	public void createWatchList(WatchListModel model) throws InterruptedException {
		newWatchListTab=fluentWaitCodeXpath("//span[text()='New Watchlist']", "New Watchlist tab");
		clickElement(newWatchListTab, "New Watch list tab");
		Thread.sleep(5000);
		watchListNameTextfield=fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]", "WatchListName Textfield");
		
		if(watchListNameTextfield==null)
			watchListNameTextfield=fluentWaitCodeXpath("//label[text()='Create a Watchlist']//following::input[1]", "WatchListName Textfield");
		clearAndSendKey(watchListNameTextfield, model.getWatchListName(), "WatchListName Textfield");
		Thread.sleep(2000);
		if(model.getDafaultWatchList().equalsIgnoreCase("Yes")) {
			defaultWatchListCheckBox=fluentWaitCodeXpath("//label[@class='default-watchlist']", "Default WatchList CheckBox");
			clickElement(defaultWatchListCheckBox,  "Default WatchList CheckBox");
		}
		clickElement("//button[text()='Create']", "Create buttons");
		addScript(model);
		pageVerify(model);
		Thread.sleep(20000);
		createWatchListPath=ScreenshortProvider.captureScreen(driver,"WatchList-"+model.getReferNo());
	}
	
	
	public void pageVerify(WatchListModel model) throws InterruptedException {
		driver.navigate().refresh();
		if(!model.getKeyword().contains("Delete")) {
		String createdWatchlistTab="//span[text()='New Watchlist']//following::a[text()='"+model.getWatchListName()+"']";
		clickElement(createdWatchlistTab,model.getWatchListName()+" Watchlist Tab");
		}
		
		
		
	}
	
	public void selectExchange(String segement) throws InterruptedException {
		Thread.sleep(1000);
		nseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[1]/div/input",20, "NSE Radio button");
		bseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[2]/div/input",20, "BSE Radio button");
		if(nseRadioButton!=null||bseRadioButton!=null) {
			if(segement.equalsIgnoreCase("NSE"))
				clickElement(nseRadioButton, "NSE Radio button");
			else if(segement.equalsIgnoreCase("BSE"))
				clickElement(bseRadioButton, "BSE Radio button");
		}
		
	}
	
	public List<String> keywordProccess(String keyword){
		List<String> stepsList=null;
		Map<String,List<String>> keywordSteps=new HashMap<String,List<String>>();
		List<String> createSteps=new ArrayList<String>();
		createSteps.add("Create");
		List<String> createDeleteSteps=new ArrayList<String>();
		createDeleteSteps.add("Create");
		createDeleteSteps.add("Delete");
		List<String> createAddScriptSteps=new ArrayList<String>();
		createAddScriptSteps.add("Create");
		createAddScriptSteps.add("AddScript");
		List<String> createAddScriptDeleteSteps=new ArrayList<String>();
		createAddScriptDeleteSteps.add("Create");
		createAddScriptDeleteSteps.add("AddScript");
		createAddScriptDeleteSteps.add("Delete");
		
		keywordSteps.put("create", createSteps);
		keywordSteps.put("CreateDelete", createDeleteSteps);
		keywordSteps.put("CreateAddScript", createAddScriptSteps);
		keywordSteps.put("CreateAddScriptDelete", createAddScriptDeleteSteps);
		
		for (Map.Entry<String,List<String>> entry : keywordSteps.entrySet()) {
			String mapStep=entry.getKey();
			if(mapStep.equalsIgnoreCase(keyword)) {
				stepsList=entry.getValue();
				break;
			}
		}
		for(String steps:stepsList) {
			Reporter.log(steps, true);
		}
		return stepsList;
	}
	
	public void deleteWatchList(WatchListModel model) throws InterruptedException {
		
		Thread.sleep(2000);
		String deleteButtonxpath="//span[text()='New Watchlist']//following::li//a[text()='"+model.getWatchListName()+"']//following::ul//li//span[@class='fa fa-trash-o ng-scope']";
		String defaultWatchxpath="//span[text()='New Watchlist']//following::li//a[text()='"+model.getWatchListName()+"']//following::ul//li//span[@class='fa fa-star']";
		try {
		
			iconButton(deleteButtonxpath, "Delete button");
		}catch(TimeoutException e) {
			defaultWatchList=fluentWaitCodeXpath(defaultWatchxpath, "Default WatchList tab");
			if(defaultWatchxpath==null)
				deleteOption="not present";
			else
				deleteOption=model.getWatchListName()+" is default watchList you can not delete";
		}
		clickElement("//button[text()='Delete']", "delete button");
		clickElement("//button[text()='Ok']", "Ok button");
		pageVerify(model);
		Thread.sleep(20000);
			deleteWatchListPath=ScreenshortProvider.captureScreen(driver,"WatchList_"+model.getReferNo());
		
	}
	
	public String [] scriptNames(String scriptName) {
		String [] scriptArray= {scriptName};
		if(scriptName.contains(",")) {
			scriptArray=scriptName.split(",");
		}
		return scriptArray;
	}
	public void addScriptExecution(WatchListModel model) throws InterruptedException {
		String [] scriptArray=scriptNames(model.getScriptName());
		pageVerify(model);
		clickElement("//a[text()='Add Scrip']", "Add script button");
		addScript(model);
	}
	
	public void addScript(WatchListModel model) throws InterruptedException {
		String text="Add a Scrip to "+model.getWatchListName();
		clearAndSendKey("//h4[text()='"+text+"']//following::input[3]", model.getScriptName(), "Add Script textfield");
		String dropdownOptionStr="//*[@id='watchlist']/div/div/div[2]/div[2]/div/div/div/ul/li/a[text()='"+model.getScriptName()+"']";
		clickElement(dropdownOptionStr, model.getScriptName()+"Dropdown option");
		selectExchange(model.getExchange());
		clickElement("//h4[text()='"+text+"']//following::button[2]", "Add Script button");
		clickElement("//button[text()='Ok']", "OK button");
	}
	
	public void watchListExecution(WatchListModel model) throws InterruptedException {
		List<String> stepsList=keywordProccess(model.getKeyword());
		
		for(String steps:stepsList) {
			switch(steps) {
			
			case "Create":
			createWatchList(model);
			break;
			
			case "Delete":
				deleteWatchList(model);
			break;
			
			case "AddScript":
			createWatchList(model);
			break;
			}
		}
		
		
		
	}
}
