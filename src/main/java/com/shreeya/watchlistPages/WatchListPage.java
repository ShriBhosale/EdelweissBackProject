package com.shreeya.watchlistPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.WatchListModel;
import com.shreeya.orderdetailpages.OrderDetail;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.Scroll;
import com.shreeya.util.SeleniumCoder;

public class WatchListPage extends SeleniumCoder {

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
	String[] scriptNameArray;
	String scriptName;
	WebElement scriptNameLabel;
	WebElement scriptCheckBox;
	WebElement deleteButton;
	WebElement popupDeleteButton;
	WebElement deleleokButton;
	WebElement orderPlaceSearchTextField;
	WebElement closeButton;
	WebElement addScriptDropDownOption;
		public static String createWatchListPath;
	public static String deleteWatchListPath;
	public static List<String> exchangeList;
	public static List<String> scriptList;
	public static String scriptLabelStr;
	public static String exchangeLabelStr;
	public static String[] scriptArray = { "Tata Consumer Products Ltd", "Adinath Textiles Ltd" };
	public static String[] exchangeArray = { "BSE", "BSE" };
	public static String[] applicationScriptArray;
	public static String errorMsg = "no";
	public static String predifineWatchMsg = "no predefine msg";
	String[] watchListNameArray = { "Def1" };
	String deleteOption = "present";
	private static int rowStartCount = 0;

	private static String scriptNames;
	private static String exchanges;
	private static String watchListName;
	private static List<String> errorList;
	private WebElement okButton;
	private String ErrorMsg;
	String scriptNametext;
	
	String sharePriceCompanyName;
	Scroll scroll;
	String[] orderDetailArray;
	String[] verifyScriptArray;

	List<String> predefineWatchListDetailList;
	List<String> predefineWatchListDetail;

	PredefineWatchList predefineWatchList;
	WatchListHelper watchListHelper;
	WatchListKeywords watchListKeyword;
	WatchListStepVerify watchListStepVerify;
	WatchListCommon watchListCommon;
	WatchListCodePage watchListCodePage;
	OrderDetail orderDetail;
	Help help;

	String[] verfiyArray = { "verify", "verifyCount" };
	private static int count = 0;

	public WatchListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		scroll = new Scroll(driver);
		predefineWatchList = new PredefineWatchList(driver);
		watchListHelper = new WatchListHelper(driver);
		watchListStepVerify = new WatchListStepVerify(driver);
		watchListKeyword = new WatchListKeywords();
		watchListCommon = new WatchListCommon(driver);
		orderDetail = new OrderDetail(driver);
		predefineWatchListDetailList = new ArrayList<String>();
		predefineWatchListDetail = new ArrayList<String>();
		errorList = new ArrayList<String>();
		help = new Help();
		orderDetailArray = null;
		watchListCodePage=new WatchListCodePage(driver);
	}

	public WatchListPage() {
	}

	public void createWatchList(WatchListModel model) throws InterruptedException {

		count++;
		Reporter.log(model.toString(), true);

		watchListName = model.getWatchListName();
		if (rowStartCount == 1) {
			scriptNames = model.getScriptName();
			exchanges = model.getExchange();
			rowStartCount++;
		}

		scriptArray = help.commaSeparater(scriptNames);
		exchangeArray = help.commaSeparater(exchanges);
		applicationScriptArray = help.commaSeparater(model.getVerifyScript());
		watchListNameArray = help.commaSeparater(watchListName);
		errorList = new ArrayList<String>();
		model.setScriptName(scriptArray[0]);
		model.setExchange(exchangeArray[0]);
		if (watchListNameArray.length > 1) {
			errorList.add("Multiple Watchlist");
		} else {
			errorList.add("Single Watchlist");
		}
		for (String watchListName : watchListNameArray) {
			// newWatchListTab=fluentWaitCodeXpath("//span[text()='New Watchlist']", "New
			// Watchlist tab");
			staticWait(1000);
			newWatchListTab = fluentWaitCodeXpath("//span[text()='New Watchlist']", "New Watchlist tab");

			// newWatchListTab=fluentWaitCodeXpath("//*[@id='contentCntr']/div/div/div[1]/ul/li[1]/a/span[2]",
			// "New Watchlist tab");
			// clickElement(newWatchListTab, "New Watch list tab");
			clickUsingAction(newWatchListTab, "New Watch list tab");
			staticWait(1000);
			watchListNameTextfield = fluentWaitCodeXpath("//label[text()='Name Your Watchlist']//following::input[1]",
					"WatchListName Textfield");

			if (watchListNameTextfield == null) {
				Reporter.log("watchListNameTextfield is null", true);
				clickUsingAction(newWatchListTab, "New Watch list tab");
				staticWait(500);
				watchListNameTextfield = fluentWaitCodeXpath(
						"//label[text()='Create a Watchlist']//following::input[1]", "WatchListName Textfield");
			}
			clearAndSendKey(watchListNameTextfield, watchListName, "WatchListName Textfield");
			Thread.sleep(1000);
			if (model.getReferNo().equalsIgnoreCase("1")) {
				defaultWatchListCheckBox = fluentWaitCodeXpath("//label[@class='default-watchlist']",
						"Default WatchList CheckBox");
				clickElement(defaultWatchListCheckBox, "Default WatchList CheckBox");
			}
			clickElement("//button[text()='Create']", "Create buttons");
			model.setWatchListName(watchListName);
			addScript(model, "create");
			watchListCommon.pageVerify(model, "Create");
		}
		/*
		 * Thread.sleep(20000);
		 * createWatchListPath=ScreenshortProvider.captureScreen(driver,"WatchList-"+
		 * model.getReferNo());
		 */
	}

	public void selectExchange(String segement, String step) {

		staticWait(500);

		nseRadioButton = fluentWaitCodeXpathCheckElement(driver,
				"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[1]/div/input", 20,
				"NSE Radio button");
		bseRadioButton = fluentWaitCodeXpathCheckElement(driver,
				"//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/label[2]/label[2]/div/input", 20,
				"BSE Radio button");
		if (step.equalsIgnoreCase("AddScript")) {
			nseRadioButton = fluentWaitCodeXpathCheckElement(driver,
					"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[1]/div/input", 20,
					"NSE Radio button");
			bseRadioButton = fluentWaitCodeXpathCheckElement(driver,
					"//*[@id=\"addScripPopup\"]/div/div/div[2]/div/div/div/label[2]/label[2]/div/input", 20,
					"BSE Radio button");
		}
		if (nseRadioButton != null || bseRadioButton != null) {
			if (segement.equalsIgnoreCase("NSE"))
				clickElement(nseRadioButton, "NSE Radio button");
			else if (segement.equalsIgnoreCase("BSE"))
				clickElement(bseRadioButton, "BSE Radio button");
		} else if (nseRadioButton != null) {
			errorList.add("NSE radio button not present");
			clickElement(bseRadioButton, "BSE Radio button");
		} else if (bseRadioButton != null) {
			errorList.add("BSE radio button not present");
			clickElement(nseRadioButton, "NSE Radio button");
		}

	}

	public void deleteWatchList(WatchListModel model) {

		/*
		 * for(String watchListName:watchListNameArray) {
		 * model.setWatchListName(watchListName);
		 */
		if (model.getExchange().equalsIgnoreCase("NSE") || model.getExchange().equalsIgnoreCase("BSE")|| model.getExchange().equalsIgnoreCase("CDS")||model.getExchange().equalsIgnoreCase("FNO")) {
		watchListCommon.pageVerify(model, "abc");
		staticWait(500);
		errorList.add("Delete watchList");
		errorList.add("WatchList Name : " + model.getWatchListName());
		String deleteButtonxpath = "//span[text()='New Watchlist']//following::li//a[text()='"
				+ model.getWatchListName() + "']//following::ul//li//span[@class='fa fa-trash-o ng-scope']";
		String defaultWatchxpath = "//span[text()='New Watchlist']//following::li//a[text()='"
				+ model.getWatchListName() + "']//following::ul//li//span[@class='fa fa-star']";
		defaultWatchList = fluentWaitCodeXpath(defaultWatchxpath, 30, "Default WatchList tab");
		if (defaultWatchList != null) {
			errorMsg = "Error msg : " + model.getWatchListName() + " watchList is default watchList you cannot delete"
					+ "-PASS";
			errorList.add(errorMsg);
		} else {
			try {

				iconButton(deleteButtonxpath, "Delete button");
			} catch (TimeoutException e) {

				if (defaultWatchxpath == null)
					deleteOption = "not present";

			}
			clickElement("//button[text()='Delete']", "delete button");
			clickElement("//button[text()='Ok']", "Ok button");
			errorList.add(model.getWatchListName() + " watchList deleted sccessfully....." + "-Check");
		}

		errorList.add(ScreenshortProvider.captureScreen(driver, "AFterDeleteWatchList"));
		}

	}

	public void addScriptExecution(WatchListModel model) throws InterruptedException {
		/*
		 * String [] scriptArray=scriptNames(scriptNames); String []
		 * exchangeArray=scriptNames(exchanges);
		 */
		// pageVerify(model);

		// pageVerify(model, "addScript");
		for (int i = 1; i < scriptArray.length; i++) {
			addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
			try {
				clickElement(addScriptButton, "Add script button");
			} catch (ElementClickInterceptedException e) {
				staticWait(1000);
				addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
				clickElement(addScriptButton, "Add script button");
			}
			model.setScriptName(scriptArray[i]);
			model.setExchange(exchangeArray[i]);
			addScript(model, "addScript");
		}

	}

	public void addScript(WatchListModel model, String step) {
		verifyScriptArray = help.commaSeparater(model.getVerifyScript());
		String xpathString = "no xpath for script";
		String dropdownOptionStr = null;
		String text = null;
		if (step.equalsIgnoreCase("Create")) {
		if (model.getExchange().equalsIgnoreCase("NCDEX") || model.getExchange().equalsIgnoreCase("MCX")) {
			dropdownOptionStr = "//*[@id=\"watchlist\"]/div/div/div[2]/div[2]/div/div/div/ul[1]/li/a[text()='"
					+ model.getScriptName() + " (" + model.getExchange() + ")" + "']";
			Reporter.log("DropDown Xpath : "+dropdownOptionStr, true);
		} else {
			dropdownOptionStr = "//*[@id='watchlist']/div/div/div[2]/div[2]/div/div/div/ul/li/a[text()='"
					+ model.getScriptName() + "']";

		}

		
			text = "Add a Scrip to " + model.getWatchListName();
			xpathString = "//h4[text()='" + text + "']//following::input[3]";

		} else if (step.equalsIgnoreCase("AddScript")) {

			if (model.getExchange().equalsIgnoreCase("NCDEX") || model.getExchange().equalsIgnoreCase("MCX")) {
				text = "Add a Scrip to ";
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul[1]/li/a[text()='"+ model.getScriptName() + " (" + model.getExchange() + ")" + "']";
			} else {
				text = "Add a Scrip to " + model.getWatchListName().toUpperCase();
				dropdownOptionStr = "//*[@id='addScripPopup']/div/div/div[2]/div/div/div/div/ul/li[1]/a[text()='"+ model.getScriptName() + "']";
			}
			xpathString = "//h4[text()='" + text + "']//following::input";
		}
		staticWait(600);
		addScriptTextfield = fluentWaitCodeXpath(xpathString, "Add Script textfield");
		staticWait(600);
		if (model.getExchange().equalsIgnoreCase("NCDEX") || model.getExchange().equalsIgnoreCase("MCX")) {
			clearAndSendKey(addScriptTextfield, model.getScriptName(), "Add Script textfield");
		} else {
			clearAndSendKey(addScriptTextfield, model.getScriptName(), "Add Script textfield");
		}
		addScriptDropDownOption=fluentWaitCodeXpath(dropdownOptionStr, "Script dropdown Option");
		try {
		clickElement(addScriptDropDownOption, model.getScriptName() + "Dropdown option");
		}catch(StaleElementReferenceException e) {
			
			clickElement(dropdownOptionStr, model.getScriptName() + "Dropdown option");
		}
		selectExchange(model.getExchange(), step);
		clickElement("//h4[text()='" + text + "']//following::button[2]", "Add Script button");
		staticWait(500);
		// clickElement("//button[text()='Ok']", "OK button");
		okButton = fluentWaitCodeXpath("//button[text()='Ok']", 40, "Ok button");
		if (okButton != null) {
			clickElement(okButton, "Ok button");
		} else {
			if (step.equalsIgnoreCase("addScript")) {
				errorList.add("Trading Sysmbol : " + verifyScriptArray[0]);
				errorMsg = model.getScriptName()
						+ " script already present. User should not be allowed to add duplicate script.-PASS";
				errorList.add(errorMsg);
				// errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList"));
			} else {
				errorMsg = model.getWatchListName()
						+ " watchList already present. User should not be allowed to create duplicate watchlist.-PASS";
				errorList.add(errorMsg);
				errorList.add(ScreenshortProvider.captureScreen(driver, "WatchList"));
			}

			driver.navigate().refresh();
			staticWait(50);
			driver.navigate().refresh();
		}
	}

	public void verifyCode(WatchListModel model, String verifyNo, int rowStartCount) {
		Reporter.log("VerifyCodeMethod::top if else ", true);

		if (model.getPredefineWatchList().equalsIgnoreCase("Yes")) {

			watchListStepVerify.predefineWatchListVerify(model, verifyNo, predefineWatchListDetailList);

		} else if (verifyNo.equalsIgnoreCase("1")) {
			for (int i = 0; i < watchListNameArray.length + 1; i++) {

				if (i == watchListNameArray.length) {

					watchListStepVerify.verfitySteps(model, "0", errorList, rowStartCount);
				} else {
					model.setWatchListName(watchListNameArray[i]);
					watchListStepVerify.verfitySteps(model, verifyNo, errorList, rowStartCount);
				}
			}
		} else {

			Reporter.log(model.toString(), true);
			watchListStepVerify.verfitySteps(model, verifyNo, errorList, rowStartCount);

		}

		/*
		 * if(verifyNo.equalsIgnoreCase("100")) { scriptList=new ArrayList<String>();
		 * exchangeList=new ArrayList<String>(); exchangeLabel=
		 * fluentWaitCodeXpath("//div[@class='ed-td ed-stock text-left']//following-sibling::span//small"
		 * , "Exchange label"); scriptLabel=
		 * fluentWaitCodeXpath("//div[@class='ed-td ed-stock text-left']//following-sibling::a"
		 * , "Segement label"); scriptLabelStr=fetchTextFromElement(scriptLabel,
		 * "Exchange label"); exchangeLabelStr=fetchTextFromElement(exchangeLabel,
		 * "Exchange label"); scriptList.add(scriptLabelStr);
		 * exchangeList.add(exchangeLabelStr);
		 * scriptList=elementsTextFilter(scriptList);
		 * exchangeList=elementsTextFilter(exchangeList); }
		 */
	}

	public void deleteScript(WatchListModel model) {
		String logScriptName = "No", exchangeName = "No", tradingSysmbol = "No";
		Reporter.log("deleteScript", true);
		errorList = new ArrayList<String>();

		watchListCommon.pageVerify(model, "AddScript");
		/* errorList.add("Before Delete Script and WatchList...."+"-"+watchListName); */
		errorList.add(ScreenshortProvider.captureScreen(driver, "BeforeDeleteScript"));
		scriptNameArray = help.commaSeparater(model.getVerifyScript());
		int scriptCount = scriptNameArray.length + 2;
		for (int i = 2; i < scriptCount; i++) {
			scriptNameLabel = fluentWaitCodeXpath(
					"//*[@id=\"contentCntr\"]/div/div/div[1]/div[4]/div/div/div/div/div[2]/div[" + i
							+ "]/div[1]/div[1]/a",
					"Script Name");
			scriptName = fetchTextFromElement(scriptNameLabel);
			if (scriptName.contains(scriptNameArray[scriptNameArray.length - 1])) {
				Reporter.log("Script : " + scriptArray[scriptArray.length - 1]);
				logScriptName = "Script Name : " + scriptArray[scriptArray.length - 1];
				exchangeName = "Exchange Name : " + exchangeArray[exchangeArray.length - 1];
				tradingSysmbol = "TradingSysmbol : " + scriptNameArray[scriptNameArray.length - 1];

				// errorList.add("TradingSysmbol : "+scriptNameArray[scriptNameArray.length-1]);

				String scriptBox = "//*[@id='contentCntr']/div/div/div[1]/div[4]/div/div/div/div/div[2]/div[" + i
						+ "]/div[1]/div[1]/div[1]/input";

				scriptCheckBox = fluentWaitCodeXpath(scriptBox, "Script checkBox");
				if (scriptCheckBox == null) {
					scriptBox = "//*[@id='contentCntr']/div/div/div[1]/div[4]/div/div/div/div/div[2]/div[" + i
							+ "]/div[1]/div[1]/div[2]/input";
					scriptCheckBox = fluentWaitCodeXpath(scriptBox, "Script checkBox");
				}
				clickElement(scriptCheckBox, "ScriptCheckBox");
				break;
			}

		}
		if (scriptNameArray.length == 1) {
			errorList.add("You can't delete beacause this watchList contain only single script -" + "PASS");
		} else {
			deleteButton = fluentWaitCodeXpath("//a[text()='Delete Scrip']", "Delete Button");
			clickElement(deleteButton, "Delete Button");
			popupDeleteButton = fluentWaitCodeXpath("//button[text()='Delete']", "popup Delete Button");
			clickElement(popupDeleteButton, "popup Delete Button");
			deleleokButton = fluentWaitCodeXpath("//button[text()='Ok']", "Ok button");
			clickElement(deleleokButton, "Ok button");
			watchListCommon.pageVerify(model, "AddScript");
			errorList.add(ScreenshortProvider.captureScreen(driver, "AfterScriptDelete"));
		}
		boolean scriptDelete = true;
		errorList.add(logScriptName);
		errorList.add(tradingSysmbol);
		scriptList = multipleElementsTextProvider("//div[@class='ed-td ed-stock text-left']//following-sibling::a",
				"Script Names");
		scriptList = elementsTextFilter(scriptList);
		for (String response : errorList) {
			Reporter.log(response, true);
			if (response.contains("TradingSysmbol")) {
				String[] array = help.separater(response, ":");
				for (String script : scriptList) {
					if (script.trim().contains(array[1].trim())) {
						errorList.set(3, response + "-FAIL");
						// errorList.add(response+"-FAIL");
						scriptDelete = false;
						break;
					}
				}
				if (scriptDelete) {
					errorList.set(3, response + "-PASS");
					// errorList.add(response+"-PASS");
				}
			}
		}

		errorList.add("Exchange Name : " + exchangeArray[exchangeArray.length - 1]);
	}

	public List<String> predefineWatchList(WatchListModel model, ExtendReporter reporter)
			throws InterruptedException, IOException {
		Reporter.log("predefineWatchList", true);
		int orderNo = 0;
		try {
			orderNo = Integer.valueOf(model.getReferNo());
		} catch (NumberFormatException e) {

		}
		Reporter.log(model.toString(), true);
		String predefineWatchXpath = "//a[text()='" + model.getWatchListName() + "']";
		try {
			WebElement predefineWatchTab = fluentWaitCodeXpath(predefineWatchXpath, "Predefine WatchList tab");
			clickElementWithOutChecking(predefineWatchTab, "Predefine watchList tab");
		} catch (TimeoutException e) {
			watchListCommon.tabNotFound(model.getWatchListName(), model.getExchange());
		}
		WebElement predifineWatchMsgLabel = fluentWaitCodeXpath("//span[text()='Your watchlist has ']",
				"Predefine WatchList massage");
		predifineWatchMsg = fetchTextFromElement(predifineWatchMsgLabel);
		predifineWatchMsg = removeExtrahmtlCode(predifineWatchMsg);
		errorMsg = predifineWatchMsg;

		return predefineWatchListDetailList;
	}

	public List<String> clickpredefineWatchList(WatchListModel model) {
		Reporter.log("predefineWatchList", true);
		Reporter.log(model.toString(), true);
		predefineWatchListDetailList = predefineWatchList.clickAnyOption(model);
		return predefineWatchListDetailList;
	}

	public List<String> tradingWithPredefineWatchList(WatchListModel model) {
		Reporter.log("tradingWithPredefineWatchList", true);
		Reporter.log(model.toString(), true);
		predefineWatchListDetailList = predefineWatchList.trading(model);
		return predefineWatchListDetailList;
	}

	public String[] verifyNo(String step) {

		if (step.contains("Verfiy")) {
			verfiyArray = step.split("_");

		} else {
			verfiyArray[0] = step;
		}
		return verfiyArray;
	}

	private void duplicateScript(WatchListModel model) {

		errorList.add("duplicateScript");
		watchListCommon.pageVerify(model, "AddScript");
		addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
		try {
			clickElement(addScriptButton, "Add script button");
		} catch (ElementClickInterceptedException e) {
			staticWait(600);
			addScriptButton = fluentWaitCodeXpath("//a[text()='Add Scrip']", "Add script button");
			clickElement(addScriptButton, "Add script button");
		}
		addScript(model, "addScript");

	}

	private List<String> tradingWatchList(WatchListModel model) {
		errorList = new ArrayList<String>();
		watchListCommon.pageVerify(model, "Trading");
		String tradeButtonxpath;
		// predefineWatchListDetail=new ArrayList<String>();
		// clickOnPredefineWatchList(model);
		String scriptName = help.tradeXpath(help.removeFutureFromScript(model.getScriptName()));
		if (model.getScriptName().contains("Future")) {
			tradeButtonxpath = "//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname='"+ help.removeFutureFromScript(model.getScriptName()) + "']";
		} else {
			if (model.getExchange().equalsIgnoreCase("MCX") || model.getExchange().equalsIgnoreCase("NCDEX")) {
				tradeButtonxpath = "//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname='"+ help.removeFutureFromScript(model.getScriptName()) + "']";
			} else {
				tradeButtonxpath = "//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname=' "
						+ help.removeFutureFromScript(model.getScriptName()) + " ']";
			}
		}
		try {

			WebElement tradeButton = fluentWaitCodeXpath(tradeButtonxpath, 30, "Trading button");
			if (tradeButton == null) {

				tradeButtonxpath = "//div[@class='ed-td hidden-xs text-right ed-action']//a[@toc-cname=' " + scriptName
						+ " ']";
				tradeButton = fluentWaitCodeXpath(tradeButtonxpath, "Trading button");
			}
			clickElement(tradeButton, model.getScriptName() + " Trade button");
			orderPlaceSearchTextField = fluentWaitCodeXpath("//input[@id='tocsearch']", "Order Place Textfield");
			scriptNametext = fetchTextFromElement(orderPlaceSearchTextField);
			Reporter.log("orderPlaceSearchTextField : " + scriptNametext, true);
			String orderPageScreenshot=predefineWatchList.placeOrder(model);
			errorList.add(orderPageScreenshot);
			if (!model.getExchange().equalsIgnoreCase("MCX") || model.getExchange().equalsIgnoreCase("NCDEX")) {
				orderDetailArray = orderDetail.orderDetailProvider(driver, "New", "NO order sheet");
				for (String orderDetail : orderDetailArray) {
					if (orderDetail.equalsIgnoreCase("no id") || orderDetail.equalsIgnoreCase("no Action")) {
						continue;
					} else {
						errorList.add(orderDetail);
					}
				}
			} else {
				errorList = orderDetail.commodityorderDetail(model);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		errorList.add(ScreenshortProvider.captureScreen(driver, "watchList"));
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a", 5, "Close Button (x)");
		if (closeButton != null)
			clickElement(closeButton, "Close order status popup");
		return errorList;

	}

	public void redirectTocodePage(WatchListModel model) {
		errorList=new ArrayList<String>();
		errorList=watchListCodePage.codePageExecution(errorList, model);
	}

	

	
	public Map<String, List<String>> watchListExecution(WatchListModel model, ExtendReporter reporter)
			throws InterruptedException, IOException {
		rowStartCount = 0;
		// model.setScriptName(model.getFullScriptName());
		List<String> stepsList = watchListKeyword.keywordProccess(model.getPredefineWatchList());
		errorMsg = "no";
		Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
		watchListStepVerify.setVerifyMap(inputMap);
		errorList = new ArrayList<String>();
		boolean newRowFlag = true;
		rowStartCount++;
		Reporter.log("Row Start count ======>> " + rowStartCount, true);
		/*
		 * model.setWatchListName("Auto3"); model.setExchange("BSE");
		 * model.setScriptName("Tata Consumer Products Ltd");
		 */

		for (String steps : stepsList) {

			verfiyArray = verifyNo(steps);
			steps = verfiyArray[0];
			Reporter.log("Step ===================================================>>> " + steps, true);
			switch (steps) {

			case "Create":
				createWatchList(model);
				break;

			case "Delete":
				deleteWatchList(model);

				break;

			case "AddScript":
				addScriptExecution(model);
				break;
			case "TradingWithWatchList":
				tradingWatchList(model);
				break;
			case "CodePage":
				redirectTocodePage(model);
				break;
			case "DeleteScript":
				deleteScript(model);
				break;
			case "DuplicateScript":
				duplicateScript(model);
				break;
			case "Verfiy":
				verifyCode(model, verfiyArray[1], rowStartCount);
				break;
			case "PredefineWatchList":
				predefineWatchListDetailList = clickpredefineWatchList(model);
				break;
			case "PredefineWatchListTrade":
				predefineWatchListDetailList = tradingWithPredefineWatchList(model);
				break;
			}
		}

		return watchListStepVerify.verifyMapGiver();

	}

}
