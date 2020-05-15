package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class WatchListPage extends SeleniumCoder{

	WebDriver driver;
	
	WebElement newWatchListTab;
	WebElement watchListNameTextfield;
	WebElement defaultWatchListCheckBox;
	WebElement bseRadioButton;
	WebElement nseRadioButton;
	WebElement defaultWatchList;
	WebElement scriptLabel;
	WebElement exchangeLabel;
	WebElement addScriptButton;
	WebElement addScriptTextfield;
	String [] scriptNameArray;
	String scriptName;
	WebElement scriptNameLabel;
	WebElement scriptCheckBox;
	WebElement deleteButton;
	WebElement popupDeleteButton;
	WebElement deleleokButton;

	public static String createWatchListPath;
	public static String deleteWatchListPath;
	public static List<String> exchangeList;
	public static List<String> scriptList;
	public static String scriptLabelStr;
	public static String exchangeLabelStr;
	public static  String [] scriptArray;
	public static  String [] exchangeArray;
	public static  String [] applicationScriptArray;
	public static String errorMsg="no";
	public static String predifineWatchMsg="no predefine msg";
	String [] watchListNameArray= {"Def1"};
	String deleteOption="present";
	
	private static String scriptNames;
	private static String exchanges;
	private static String watchListName;
	private static ArrayList<String> errorList;
	private WebElement okButton;
	private String ErrorMsg;
	Scroll scroll;
	
	List<String> predefineWatchListDetailList;
	
	PredefineWatchList predefineWatchList;
	WatchListHelper watchListHelper;
	WatchListKeywords watchListKeyword;
	WatchListStepVerify watchListStepVerify;
	String [] verfiyArray= {"verify","verifyCount"};
	private static int count=0;

	public WatchListPage(WebDriver driver) throws IOException {
		super(driver);
		this.driver=driver;
		scroll=new Scroll(driver);
		predefineWatchList=new PredefineWatchList(driver);
		watchListHelper=new WatchListHelper(driver);
		watchListStepVerify=new WatchListStepVerify(driver);
		watchListKeyword=new WatchListKeywords();
		predefineWatchListDetailList=new ArrayList<String>();
		errorList=new ArrayList<String>();
	}
	
	public WatchListPage() {}
	
	
	public void createWatchList(WatchListModel model) throws InterruptedException {
		count++;
		Reporter.log(model.toString(), true);
		if(count==1) {
			watchListName=model.getWatchListName();
			scriptNames=model.getScriptName();
			exchanges=model.getExchange();
		}
		scriptArray=scriptNames(scriptNames);
		exchangeArray=scriptNames(exchanges);
		applicationScriptArray=scriptNames(model.getVerifyScript());
		watchListNameArray=scriptNames(watchListName);
		model.setScriptName(scriptArray[0]);
		model.setExchange(exchangeArray[0]);
		
		for(String watchListName:watchListNameArray) {
		newWatchListTab=fluentWaitCodeXpath("//span[text()='New Watchlist']", "New Watchlist tab");
		clickElement(newWatchListTab, "New Watch list tab");
		Thread.sleep(1000);
		watchListNameTextfield=fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]", "WatchListName Textfield");
		
		if(watchListNameTextfield==null)
			watchListNameTextfield=fluentWaitCodeXpath("//label[text()='Create a Watchlist']//following::input[1]", "WatchListName Textfield");
		clearAndSendKey(watchListNameTextfield, watchListName, "WatchListName Textfield");
		Thread.sleep(1000);
		if(count==1) {
			defaultWatchListCheckBox=fluentWaitCodeXpath("//label[@class='default-watchlist']", "Default WatchList CheckBox");
			clickElement(defaultWatchListCheckBox,  "Default WatchList CheckBox");
		}
		clickElement("//button[text()='Create']", "Create buttons");
		model.setWatchListName(watchListName);
		addScript(model,"create");
		pageVerify(model,"Create");
		}
		/*
		 * Thread.sleep(20000);
		 * createWatchListPath=ScreenshortProvider.captureScreen(driver,"WatchList-"+
		 * model.getReferNo());
		 */
	}
	
	
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
	
	public void selectExchange(String segement,String step) {
		
		staticWait(500);
		nseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[1]/div/input",20, "NSE Radio button");
		bseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[2]/div/input",20, "BSE Radio button");
		if(step.equalsIgnoreCase("AddScript")) {
			nseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[1]/div/input",20, "NSE Radio button");
			bseRadioButton=fluentWaitCodeXpathCheckElement(driver,"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[2]/div/input",20, "BSE Radio button");
		}
		if(nseRadioButton!=null||bseRadioButton!=null) {
			if(segement.equalsIgnoreCase("NSE"))
				clickElement(nseRadioButton, "NSE Radio button");
			else if(segement.equalsIgnoreCase("BSE"))
				clickElement(bseRadioButton, "BSE Radio button");
		}
		
	}
	
	
	public void deleteWatchList(WatchListModel model)  {
		
		for(String watchListName:watchListNameArray) {
			model.setWatchListName(watchListName);
			pageVerify(model,"abc");
		staticWait(500);
		errorList.add("Delete WatchListName : "+watchListName+"-"+watchListName);
		String deleteButtonxpath="//span[text()='New Watchlist']//following::li//a[text()='"+watchListName+"']//following::ul//li//span[@class='fa fa-trash-o ng-scope']";
		String defaultWatchxpath="//span[text()='New Watchlist']//following::li//a[text()='"+watchListName+"']//following::ul//li//span[@class='fa fa-star']";
		defaultWatchList=fluentWaitCodeXpath(defaultWatchxpath,30,"Default WatchList tab");
		if(defaultWatchList!=null) {
			errorMsg="Error msg : "+model.getWatchListName()+" watchList is default watchList you cannot delete";
			errorList.add(errorMsg+"-"+watchListName);
		}else {
		try {
		
			iconButton(deleteButtonxpath, "Delete button");
		}catch(TimeoutException e) {
			
			if(defaultWatchxpath==null)
				deleteOption="not present";
			
		}
		clickElement("//button[text()='Delete']", "delete button");
		clickElement("//button[text()='Ok']", "Ok button");
		}
		
		
		errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList")+"-"+watchListName);
		}
		
		
		/*
		 * Thread.sleep(2000);
		 * deleteWatchListPath=ScreenshortProvider.captureScreen(driver,"WatchList_"+
		 * model.getReferNo());
		 */
		
	}
	
	public String [] scriptNames(String scriptName) {
		String [] scriptArray= {scriptName};
		if(scriptName.contains(",")) {
			scriptArray=scriptName.split(",");
		}
		return scriptArray;
	}
	public void addScriptExecution(WatchListModel model) throws InterruptedException {
		/*
		 * String [] scriptArray=scriptNames(scriptNames); String []
		 * exchangeArray=scriptNames(exchanges);
		 */
		//pageVerify(model);
		for(String watchListName:watchListNameArray) {
			model.setWatchListName(watchListName);
			pageVerify(model, "addScript");
		for(int i=1;i<scriptArray.length;i++) {
			addScriptButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
			try {
			clickElement(addScriptButton, "Add script button");
			}catch(ElementClickInterceptedException e) {
				staticWait(600);
				addScriptButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
				clickElement(addScriptButton, "Add script button");
			}
			model.setScriptName(scriptArray[i]);
			model.setExchange(exchangeArray[i]);
			addScript(model,"addScript");
		}
		}
	}
	
	public void addScript(WatchListModel model,String step) {
		
		String xpathString="no xpath for script";
		String dropdownOptionStr="//*[@id='watchlist']/div/div/div[2]/div[2]/div/div/div/ul/li/a[text()='"+model.getScriptName()+"']";
		String text="Add a Scrip to "+model.getWatchListName();
		if(step.equalsIgnoreCase("Create"))
		 xpathString="//h4[text()='"+text+"']//following::input[3]";
		else if(step.equalsIgnoreCase("AddScript")) {
			text="Add a Scrip to "+model.getWatchListName().toUpperCase();
			xpathString="//h4[text()='"+text+"']//following::input";
			dropdownOptionStr="//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/div/ul/li[1]/a[text()='"+model.getScriptName()+"']";
		}
		
		addScriptTextfield=fluentWaitCodeXpath(xpathString, "Add Script textfield");
		staticWait(600);
		clearAndSendKey(addScriptTextfield, model.getScriptName(), "Add Script textfield");
		
		clickElement(dropdownOptionStr, model.getScriptName()+"Dropdown option");
		selectExchange(model.getExchange(),step);
		clickElement("//h4[text()='"+text+"']//following::button[2]", "Add Script button");
		staticWait(500);
		//clickElement("//button[text()='Ok']", "OK button");
		okButton=fluentWaitCodeXpath("//button[text()='Ok']",40,"Ok button");
		if(okButton!=null) {
			clickElement(okButton, "Ok button");
		}else {
			if(step.equalsIgnoreCase("addScript")) {
				errorMsg=model.getScriptName()+" script already present. User should not be allowed to add duplicate script.-"+model.getWatchListName();
				errorList.add(errorMsg);
				errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList")+"-"+model.getWatchListName());
			}else {
			errorMsg=model.getWatchListName()+" watchList already present. User should not be allowed to create duplicate watchlist.-"+model.getWatchListName();
			errorList.add(errorMsg);
			errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList")+"-"+model.getWatchListName());
			}
			
			driver.navigate().refresh();
			staticWait(50);
			driver.navigate().refresh();
			
			
	
		}
	}
	
	
	
	
	
	public void verifyCode(WatchListModel model,String verifyNo) {
		Reporter.log("VerifyCodeMethod::top if else ", true);
		if(model.getPredefineWatchList().equalsIgnoreCase("Yes")) {
			
			watchListStepVerify.predefineWatchListVerify(model, verifyNo, predefineWatchListDetailList);
			
		}else {
		for(String watchLsitName:watchListNameArray) {	
			model.setWatchListName(watchLsitName);
			watchListStepVerify.verfitySteps(model, verifyNo,errorList);
		}
		}
		
			
		if(verifyNo.equalsIgnoreCase("100")) {
			scriptList=new ArrayList<String>();
			exchangeList=new ArrayList<String>();
		exchangeLabel=fluentWaitCodeXpath("//div[@class='ed-td ed-stock text-left']//following-sibling::span//small", "Exchange label");
		scriptLabel=fluentWaitCodeXpath("//div[@class='ed-td ed-stock text-left']//following-sibling::a", "Segement label");
		scriptLabelStr=fetchTextFromElement(scriptLabel, "Exchange label");
		exchangeLabelStr=fetchTextFromElement(exchangeLabel, "Exchange label");
		scriptList.add(scriptLabelStr);
		exchangeList.add(exchangeLabelStr);
		scriptList=elementsTextFilter(scriptList);
		exchangeList=elementsTextFilter(exchangeList);
		}
	}
	
	public void deleteScript(WatchListModel model) {
		Reporter.log("deleteScript", true);
		errorList=new ArrayList<String>();
		for(String watchListName:watchListNameArray) {
			model.setWatchListName(watchListName);
			pageVerify(model,"AddScript");
			errorList.add("Before Delete Script and WatchList...."+"-"+watchListName);
			errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList")+"-"+watchListName);
		scriptNameArray=scriptNames(model.getVerifyScript());
		int scriptCount=scriptNameArray.length+2;
		for(int i=2;i<scriptCount;i++) {
			scriptNameLabel=fluentWaitCodeXpath("//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/a", "Script Name");
			scriptName=fetchTextFromElement(scriptNameLabel);
			if(scriptName.contains(scriptNameArray[scriptNameArray.length-1])) {
				errorList.add("Deleted Script Name : "+scriptNameArray[scriptNameArray.length-1]+"-"+watchListName);
				String scriptBox="//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div["+i+"]/div[1]/div[1]/div[1]/input";
				scriptCheckBox=fluentWaitCodeXpath(scriptBox, "Script checkBox");
					clickElement(scriptCheckBox, "ScriptCheckBox");
					break;
			}
		}
		if(scriptNameArray.length<2) {
			errorList.add("You can't delete beacause this watchList contain only single script -"+watchListName);
		}
		deleteButton=fluentWaitCodeXpath("//a[text()='Delete Scrip']", "Delete Button");
		clickElement(deleteButton, "Delete Button");
		popupDeleteButton=fluentWaitCodeXpath("//button[text()='Delete']", "popup Delete Button");
		clickElement(popupDeleteButton, "popup Delete Button");
		deleleokButton=fluentWaitCodeXpath("//button[text()='Ok']", "Ok button");
		clickElement(deleleokButton, "Ok button");
		errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList")+"-"+watchListName);
		}
	}
	
	public List<String> predefineWatchList(WatchListModel model,ExtendReporter reporter) throws InterruptedException, IOException {
		Reporter.log("predefineWatchList", true);
		int orderNo = 0;
		try {
		 orderNo=Integer.valueOf(model.getReferNo());
		}catch(NumberFormatException e) {
			
		}
		Reporter.log(model.toString(), true);
		String predefineWatchXpath="//a[text()='"+model.getWatchListName()+"']";
		try {
			WebElement predefineWatchTab=fluentWaitCodeXpath(predefineWatchXpath, "Predefine WatchList tab");
			clickElementWithOutChecking(predefineWatchTab, "Predefine watchList tab");
		}catch(TimeoutException e) {
			tabNotFound(model.getWatchListName());
		}
		WebElement predifineWatchMsgLabel=fluentWaitCodeXpath("//span[text()='Your watchlist has ']", "Predefine WatchList massage");
		predifineWatchMsg=fetchTextFromElement(predifineWatchMsgLabel);
		predifineWatchMsg=removeExtrahmtlCode(predifineWatchMsg);
		errorMsg=predifineWatchMsg;
		
		
		
		return predefineWatchListDetailList;
	}
	
	public List<String> clickpredefineWatchList(WatchListModel model) {
		Reporter.log("predefineWatchList", true);
		Reporter.log(model.toString(), true);
		predefineWatchListDetailList=predefineWatchList.clickAnyOption(model);
		return predefineWatchListDetailList;
	}
	
	public List<String> tradingWithPredefineWatchList(WatchListModel model) {
		Reporter.log("tradingWithPredefineWatchList", true);
		predefineWatchListDetailList=predefineWatchList.trading(model);
		return predefineWatchListDetailList;
	}
	
	public void tabNotFound(String watchListName) {
		clickElement("//a[text()='Select Watchlist']", "Select watchList button");
		String watchListOptionxpath="//a[text()='"+watchListName+"']";
		clickElement(watchListOptionxpath, watchListName+" option ");
	}
	
	public String [] verifyNo(String step) {
		
		if(step.contains("Verfiy")) {
			verfiyArray=step.split("_");
			
		}else {
			verfiyArray[0]=step;
		}
		return verfiyArray;
	}
	
	private void duplicateScript(WatchListModel model) {
		model.setScriptName(scriptArray[scriptArray.length-1]);
		model.setExchange(exchangeArray[exchangeArray.length-1]);
		for(String watchListName:watchListNameArray) {
			model.setWatchListName(watchListName);
			pageVerify(model,"AddScript");
			addScriptButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
			try {
			clickElement(addScriptButton, "Add script button");
			}catch(ElementClickInterceptedException e) {
				staticWait(600);
				addScriptButton=fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
				clickElement(addScriptButton, "Add script button");
			}
		addScript(model,"addScript");
		}
	}
	
	public List<String> watchListExecution(WatchListModel model,ExtendReporter reporter) throws InterruptedException, IOException {
		List<String> stepsList=watchListKeyword.keywordProccess(model.getPredefineWatchList());
		errorMsg="no";
		
		for(String steps:stepsList) {
			verfiyArray=verifyNo(steps);
			steps=verfiyArray[0];
			Reporter.log("Step ===================================================>>> "+steps, true);
			switch(steps) {
			
			case "Create":
				createWatchList(model);
			break;
			
			case "Delete":
				deleteWatchList(model);

				break;
			
			case "AddScript":
				addScriptExecution(model);
			break;
			
			case "DeleteScript":
				deleteScript(model);
				break;
			case "DuplicateScript":
				duplicateScript(model);
				break;
			case "Verfiy":
				verifyCode(model,verfiyArray[1]);
				break;
			case "PredefineWatchList":
				predefineWatchListDetailList=clickpredefineWatchList(model);
				break;
			case "PredefineWatchListTrade":
				predefineWatchListDetailList=tradingWithPredefineWatchList(model);
				break;
			}
		}
		
		return predefineWatchListDetailList;
		
	}

	
}
