package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.MyTestLauncher;
import com.shreeya.model.LatestLoginModel;
import com.shreeya.model.LoginModel;
import com.shreeya.model.LoginTestModel;
import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.SeleniumCoder;

public class LoginPage extends SeleniumCoder {

	WebElement popupButton;
	WebElement loginButton;
	WebElement buyAndSellButton;
	WebElement userIdTextField;
	WebElement proceedButton;
	WebElement passwordTextField;
	WebElement yobTextField;
	WebElement continueButton;
	WebElement notNowButton;
	public WebDriver driver;
	WebElement popupOkButton;
	public static WebDriver driver1;
	boolean noLoginProccess = false;
	private WebElement closePopupButton;
	private WebElement closeButton;
	private WebElement logoutOption;
	private WebElement logoutlink;
	String userIdStr, passwordstr, yobstr;
	private String loginErrorMsg = "no error";
	LoginTestModel loginTestModel;
	private String loginErrorStr = "No Error";
	Iterator<LoginTestModel> csvLoginTestIterator;
	private WebElement closeLoginFrame;
	private boolean logErrorChecker;
	private WebElement editButton;
	static Logger log = Logger.getLogger(LoginPage.class.getName());
	BrowserLaunch browserLunch;
	public  boolean popupFlag=false;
	private WebElement notNowWhatsApp;

	public LoginPage(WebDriver driver) {

		super(driver);
		this.driver = driver;

	}

	public void loginExecution(String scenario, LatestLoginModel loginModelObject) throws InterruptedException, IOException {
		// driver=browserLaunch(scenario);
		LoginTestModel loginTestModel=null;
		if (!loginModelObject.getModule().equalsIgnoreCase("login")) {
			popupFlag=loginCodeExecution(scenario, loginModelObject);
		} else {
			ExtendReporter extend = new ExtendReporter(MyTestLauncher.reportFolderPath[1], "LoginRegression", 0);
			CsvReaderCode csvReader = new CsvReaderCode();
			Iterator<LoginTestModel> csvLoginTestIterator = csvReader.loginTestDataProvider();
			while (csvLoginTestIterator.hasNext()) {
				try {
				 loginTestModel = csvLoginTestIterator.next();
				Reporter.log(loginTestModel.toString(), true);
				loginCodeExecution(driver, loginTestModel, extend);
				}catch(NullPointerException e) {
					extend.loginReport(driver, extend, loginTestModel, "Error",elementNameError);
					pageRefresh();
					continue;
				}catch(NoSuchElementException e1) {
					extend.loginReport(driver, extend, loginTestModel, "Error",elementNameError);
					pageRefresh();
					continue;
				}
			}
			driver.close();
			Reporter.log("Driver close", true);
			extend.logFlush();
		}
		// return driver;
	}

	public boolean loginCodeExecution(String scenario, LatestLoginModel loginModelObject)throws InterruptedException, IOException {

		Reporter.log("LoginPage : loginExecution ", true);
		clickOnLoginButton(driver);
		if (equityCommodityPoppup(driver,loginModelObject.getSegment())) {
			Reporter.log("Popup handlyed", true);
		}
		do {
			try {
				Reporter.log("Before userId", true);
				Reporter.log("LoginModel data " + loginModelObject.toString(), true);
				
				userIdTextField = fluentWaitCodeId(driver, "userID", 50);
				
				Reporter.log("After locate userId", true);
			} catch (TimeoutException e) {
				Reporter.log("User id not found now again click onLogin button");
				clickOnLoginButton(driver);
			}
		} while (userIdTextField == null);
		try {
			Reporter.log("User id : " + loginModelObject.getUserId(), true);
			
			clearAndSendKey(userIdTextField, loginModelObject.getUserId(), loginModelObject.getUserId());
		} catch (Exception e) {
			editButton = fluentWaitCodeXpath(driver,
					"//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[1]/a/i","Edit Button");
			clickElement(editButton, "Edit button");
			clearAndSendKey(userIdTextField, loginModelObject.getUserId(), "User Id");
		}
		if (equityCommodityPoppup(driver,loginModelObject.getSegment()))
			Reporter.log("Handly semement popup!!", true);
		else
			Reporter.log("NO Handly semement popup!!", true);
		proceedButton = fluentWaitCodeXpath(driver, "//button[text()='Proceed']","Proceed Button");
		clickElement(proceedButton, "Procceed Button ");
		Reporter.log("LoginModel data " + loginModelObject.toString(), true);
		try {
			
			passwordTextField = fluentWaitCodeId(driver, "password", 30);
		} catch (TimeoutException e) {
			if (equityCommodityPoppup(driver,loginModelObject.getSegment())) {

				Reporter.log("Segement Poppup handly", true);
				passwordTextField = fluentWaitCodeId(driver, "password", 30);
			}
		}
		sendKey(passwordTextField, loginModelObject.getPassword(), "Password Textfield");

		proceedButton = fluentWaitCodeXpath(driver, "//button[text()='Proceed']","Proceed Button");
		clickElement(proceedButton, "Proceed Button");
		if (logError("//*[@id='loginModal']/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span", driver)) {
			//Thread.sleep(4000);

			yobTextField = fluentWaitCodeXpath(driver, "//*[@id='ans']","Yob textfield");
			sendKey(yobTextField, loginModelObject.getYob(), loginModelObject.getYob());

			continueButton = fluentWaitCodeXpath(driver, "//button[text()='Continue']","Continue Button");
			clickElement(continueButton, "ContinueButton");

			notNowButton = fluentWaitCodeXpath(driver, "//a[text()='Not now']",40,"Not now button");
			if (notNowButton != null) {
				popupFlag=true;
				clickElement(notNowButton, "Not now popup button");
			}else {
				notNowWhatsApp=fluentWaitCodeXpath("//button[text()='Not Now']", "Not now whats app");
				if(notNowWhatsApp!=null) {
					popupFlag=true;
					clickElement(notNowWhatsApp,"Not now whats app");
				}
			}

			/*
			 * if(scenario.equalsIgnoreCase("Partial Order")) { WebElement
			 * popUpButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			 * clickElement(popUpButton); }
			 */

			popupOkButton = fluentWaitCodeXpath(driver,"//button[text()='Ok']",20,"Ok popup button");
			if (popupOkButton != null) {
				popupFlag=true;
				clickElement(popupOkButton, "Ok popup button");
			}
		} else {

			// notNowButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			notNowButton = fluentWaitCodeXpath(driver, "//*[@id=\"loginModal\"]/div/div[1]/div/div/div/div[2]/p[1]/a",40,"Not now popup button");
			clickElement(notNowButton, "Not now popup button");
			popupFlag=true;
		}
		
			popupButton = fluentWaitCodeXpath(driver, "//button[text()='No thanks']", 5,"No thans popup button");
			if (popupButton != null) {
				clickElement(popupButton, "No thans popup button");
			}
		Reporter.log("PopupError flag================================================> "+popupFlag, true);
		return popupFlag;
		
	}

	private boolean equityCommodityPoppup(WebDriver driver,String segment) {
		boolean elementFlag = false;
		WebElement segmentRadioButton = null;
		
		try {
			if (segment.equalsIgnoreCase("Equity")) {
				segmentRadioButton = fluentWaitCodeXpathCheckElement(driver,
						"//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[1]/div[1]/label", 1,"Eq segement Button");
			} else if (segment.equalsIgnoreCase("Commodity")) {
				segmentRadioButton = fluentWaitCodeXpathCheckElement(driver,
						"//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[1]/div[2]/label", 1,"CO segement Button");
			}
		} catch (NullPointerException e) {
			//Check after
		}
		if (segmentRadioButton != null) {
			elementFlag = true;
			WebElement proceedButton = fluentWaitCodeXpath(driver, "//button[text()='Proceed']","Procced Button for segement info popup");
			
				clickElement(segmentRadioButton, segment + " RadioButton");
				clickElement(proceedButton, "Proceed Button");
			
		}
		return elementFlag;

	}

	public void logout(WebDriver driver) throws InterruptedException {
		int noTab=noTabs(driver);
//		for(int i=0;i<noTab;i++) {
			
		closeButton = fluentWaitCodeXpath(driver, "//*[@id='myModal']/div/div/div[1]/a",5,"Close Button (x)");
		if(closeButton!=null)
		clickElement(closeButton, "Close order status popup");
		staticWait(3000);
		driver.navigate().refresh();
		staticWait(200);
		logoutOption = fluentWaitCodeXpath(driver, "//*[@id='caUser']/span[1]","Logout option");
		if(logoutOption!=null) {
		clickElement(logoutOption, "Logout option");
		}else {
			driver.navigate().refresh();
		}
		logoutlink = fluentWaitCodeXpath(driver, "//a[text()=' Logout']","Logout link");
		clickElement(logoutlink, "logout link");
		/*
		 * if(noTab==2) { driver.close(); }
		 */
		/* } */
		// logoutlink.click();
	}
	
	

	public void headerInExcel(CSVWriter writer) throws IOException {
		CsvReaderCode reader = new CsvReaderCode();
		String[] excelArray = { "Id", "Action", "Status", "Order Action", "Trading Symbol", "Product Type",
				"Order Price", "Order Type", "User id", "Exchange", "Validity", "Nest Id" };
		reader.WriteFile(excelArray, writer);
	}

	public boolean logError(String xapthString, WebDriver driver) {
		Reporter.log("Checking login error ", true);
		boolean loginErrorFlag = false;
		WebElement loginErrorMsg = null;
		
			loginErrorMsg = fluentWaitCodeXpathCheckElement(driver, xapthString, 1,"Login Error msg");
			loginErrorStr = fetchTextFromElement(loginErrorMsg, "Login Error");
			if (loginErrorStr.contains(">")) {
				String[] logErrorArray = loginErrorStr.split("\\.");
				loginErrorStr = logErrorArray[0].trim();
			}
			if(loginErrorMsg==null) {
				loginErrorFlag = true;
			}
			Reporter.log("Login Error ===> " + loginErrorStr, true);

		return loginErrorFlag;
	}

	public String getLoginErrorMsg() {
		return loginErrorMsg;
	}

	public void setLoginErrorMsg(String loginErrorMsg) {
		this.loginErrorMsg = loginErrorMsg;
	}

	public void clickOnLoginButton(WebDriver driver) throws InterruptedException {

		try {
			popupButton = fluentWaitCodeXpath(driver, "//button[text()='No thanks']", 40,"No thans popup button");
			clickElement(popupButton, "No thans popup button");
		} catch (Exception e) {
			Reporter.log("No thanks popup not found", true);
		}

//		if(elementPresentOrNot(driver, popupButton)) {
//			clickElement(popupButton, "No thans popup button");
//		}
		loginButton = fluentWaitCodeXpath(driver, "//span[text()='Login']","Login button");
		clickElement(loginButton, "Login button");

		try {
			buyAndSellButton = fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']", 50,"Buy/Sell link");
		} catch (Exception e) {
			Reporter.log("Again click on login button", true);
			clickElement(loginButton, "Login button");
		}
		try {
		clickElement(buyAndSellButton, "Buy/Sell link");
		}catch(StaleElementReferenceException e) {
			buyAndSellButton = fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']", 50,"Buy/Sell link");
			clickElement(buyAndSellButton, "Buy/Sell link");
		}
	}

	public WebDriver loginCodeExecution(WebDriver driver, LoginTestModel loginModelObject, ExtendReporter extend)
			throws InterruptedException, IOException {

		Reporter.log("LoginPage : loginExecution ", true);
		Reporter.log(loginModelObject.toString(), true);
		clickOnLoginButton(driver);
		do {
			try {
				Reporter.log("Before userId", true);
				Reporter.log("LoginModel data " + loginModelObject.toString(), true);
				userIdTextField = fluentWaitCodeId(driver, "userID", 30);
				Reporter.log("After locate userId", true);
			} catch (TimeoutException e) {
				Reporter.log("User id not found now again click onLogin button");
				clickOnLoginButton(driver);
			}
		} while (userIdTextField == null);

		String userId_Idvalue = userIdTextField.getAttribute("id");
		Reporter.log("userId_Idvalue ===> " + userId_Idvalue, true);

		if (elementPresentOrNot(driver, "//i[@class='glyphicon glyphicon-pencil editLoginID']", "xpath","Edit Button")) {
			editButton = fluentWaitCodeXpath(driver, "//i[@class='glyphicon glyphicon-pencil editLoginID']","Edit Button");
			clickElement(editButton, "Edit Button ");
		}

		clearAndSendKey(userIdTextField, loginModelObject.getUser_Id(), "User Id");
		proceedButton = fluentWaitCodeXpath(driver, "//button[text()='Proceed']","Procceed Button");
		clickElement(proceedButton, "Procceed Button ");

		logErrorChecker = logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[1]/span",
				driver);
		if (equityCommodityPoppup(driver,"Equity")) {
			Reporter.log("Segement Poppup", true);
		}
		Reporter.log("After Userid and than processed " + logErrorChecker, true);
		/*if (logErrorChecker) {*/
			Reporter.log("LoginModel data " + loginModelObject.toString(), true);
			passwordTextField = fluentWaitCodeId(driver, "password","Password Textfield");
			sendKey(passwordTextField, loginModelObject.getPassword(), "Password Textfield");

			proceedButton = fluentWaitCodeXpath(driver, "//button[text()='Proceed']","Proceed Button");
			clickElement(proceedButton, "Proceed Button");
			logErrorChecker = logError(
					"//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span", driver);
			/*if (logErrorChecker) {*/
				yobTextField = fluentWaitCodeId(driver, "ans", "Yob TextField");
				sendKey(yobTextField, loginModelObject.getYob(), "Yob TextField");

				continueButton = fluentWaitCodeXpath(driver, "//button[text()='Continue']", "ContinueButton");
				clickElement(continueButton, "ContinueButton");
				logErrorChecker = logError(
						"//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div/div[2]/div[3]/div[2]/span/span",
						driver);
//				if (logErrorChecker) {
					noLoginProccess = logError("//h3[text()='Updates on WhatsApp coming soon']", driver);
					if (noLoginProccess == true) {
						if (elementPresentOrNot(driver, "//a[text()='Not now']", "xpath", "Not now popup button")) {
						notNowButton = fluentWaitCodeXpath(driver, "//a[text()='Not now']", "Not now popup button");
						clickElement(notNowButton, "Not now popup button");
						}

						
						 
						if (elementPresentOrNot(driver, "//button[text()='Ok']", "xpath", "Ok popup button")) {
							popupOkButton = driver.findElement(By.xpath("//button[text()='Ok']"));
							clickElement(popupOkButton, "Ok popup button");
							}
						

						extend.loginReport(driver, extend, loginModelObject, loginErrorStr,elementNameError);
						if(elementPresentOrNot(driver, "//button[text()='No thanks']", "xpath", "No thans popup button")) {
							popupButton = fluentWaitCodeXpath(driver, "//button[text()='No thanks']", 20, "No thans popup button");
							clickElement(popupButton, "No thans popup button");
						}
						logout(driver);
					} else {
						notNowButton = fluentWaitCodeXpath(driver, "//button[text()='Not Now']", "Not now popup button");
						clickElement(notNowButton, "Not now popup button");
						extend.loginReport(driver, extend, loginModelObject, loginErrorStr,elementNameError);
						logout(driver);

					}
					/* } */
				/* } */

			/* } */
		if (logErrorChecker == false) {
			Reporter.log("Login Error fond", true);

			extend.loginReport(driver, extend, loginModelObject, loginErrorStr,elementNameError);

			Reporter.log("Folder path ===> " + MyTestLauncher.reportFolderPath[0], true);
			closeLoginFrame = fluentWaitCodeXpath(driver,"//button[@class='close ng-scope']", "Login Detail Frame close button");
			clickElement(closeLoginFrame, "Login Detail Frame close button");

		}

		driver1 = driver;
		Reporter.log("Driver object ====> " + driver1, true);
		return driver;

	}

	public void pageRefresh() {
		try {
		driver.navigate().refresh();
		}catch(NoSuchSessionException e) {
			
		}
	}
	
}
