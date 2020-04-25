package com.shreeya.orderdetailpages;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.FunctionKeyword;
import com.shreeya.model.LoginModel;
import com.shreeya.model.LoginTestModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.SeleniumCoder;

public class LoginPage extends SeleniumCoder{
	
	WebElement popupButton; 
	WebElement loginButton;
	WebElement buyAndSellButton;
	WebElement userIdTextField;
	WebElement proceedButton;
	WebElement passwordTextField;
	WebElement yobTextField;
	WebElement continueButton;
	WebElement notNowButton;
	public static WebDriver driver;
	WebElement popupOkButton;
	public static WebDriver driver1;
	boolean noLoginProccess=false;
	private WebElement closePopupButton;
	private WebElement closeButton;
	private WebElement logoutOption;
	private WebElement logoutlink;
	String userIdStr,passwordstr,yobstr;
	private String loginErrorMsg="no error";
	LoginTestModel loginTestModel;
	private String loginErrorStr="No Error";
	Iterator<LoginTestModel> csvLoginTestIterator;
	private WebElement closeLoginFrame;
	private boolean logErrorChecker;
	private WebElement editButton;
	static Logger log = Logger.getLogger(LoginPage.class.getName());
	public LoginPage() {
			
	}
	
	
	public WebDriver loginExecution(String scenario,LoginModel loginModelObject) throws InterruptedException, IOException {
		//driver=browserLaunch(scenario);
		if(!loginModelObject.getModule().equalsIgnoreCase("login")) {
			loginCodeExecution(scenario,loginModelObject);
		}else {
			ExtendReporter extend=new ExtendReporter(FunctionKeyword.folderPath[0],"LoginRegression",0);
			CsvReaderCode csvReader=new CsvReaderCode();
			csvLoginTestIterator=csvReader.loginTestDataProvider();
			 WebDriver driver=browserLaunch("normal");
			while(csvLoginTestIterator.hasNext()) {
			loginTestModel=csvLoginTestIterator.next();
			
			loginCodeExecution(driver,loginTestModel,extend);
			}
			driver.close();
			Reporter.log("Driver close",true);
			extend.logFlush();
		}
		return driver;
	}
	
	public WebDriver loginCodeExecution(String scenario,LoginModel loginModelObject) throws InterruptedException, IOException{
		
		Reporter.log("LoginPage : loginExecution ", true);
		driver=browserLaunch(scenario);
		clickOnLoginButton(driver);
		do {
		try {
			Reporter.log("Before userId", true);
			Reporter.log("LoginModel data "+loginModelObject.toString(), true);
		userIdTextField=fluentWaitCodeId(driver, "userID",100);
		Reporter.log("After locate userId", true);
		}catch(TimeoutException e) {
			Reporter.log("User id not found now again click onLogin button");
			clickOnLoginButton(driver);
		}
		}while(userIdTextField==null);
		try {
		clearAndSendKey(userIdTextField,loginModelObject.getUserId(),"User Id");
		}catch(Exception e) {
			editButton=fluentWaitCodeXpath(driver, "//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[1]/a/i");
			clickElement(editButton, "Edit button");
			clearAndSendKey(userIdTextField,loginModelObject.getUserId(),"User Id");
		}
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Procceed Button ");
		Reporter.log("LoginModel data "+loginModelObject.toString(), true);
		passwordTextField=fluentWaitCodeId(driver, "password");
		sendKey(passwordTextField, loginModelObject.getPassword(),"Password Textfield");
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Proceed Button");
		//if(logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span",driver)) {
			Thread.sleep(4000);
			
			  yobTextField=fluentWaitCodeXpath(driver, "//*[@id='ans']");
			  sendKey(yobTextField, loginModelObject.getYob(),"Yob TextField");
			 
		
		continueButton=fluentWaitCodeXpath(driver,"//button[text()='Continue']");
		clickElement(continueButton,"ContinueButton");
		
		
		noLoginProccess=logError("//h3[text()='Updates on WhatsApp coming soon']", driver);
		if(noLoginProccess==true) {
			
			
		notNowButton=fluentWaitCodeXpath(driver,"//a[text()='Not now']");
		if(notNowButton!=null) {
		clickElement(notNowButton,"Not now popup button");
		}
		
		/*if(scenario.equalsIgnoreCase("Partial Order")) {
			WebElement popUpButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(popUpButton);
		}*/
		popupOkButton=driver.findElement(By.xpath("//button[text()='Ok']"));
		if(popupOkButton!=null)
		clickElement(popupOkButton,"Ok popup button");
		}else {
			
			//notNowButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			notNowButton=fluentWaitCodeXpath(driver, "//*[@id=\"loginModal\"]/div/div[1]/div/div/div/div[2]/p[1]/a");
			clickElement(notNowButton,"Not now popup button");
		}
		/*
		 * }else { Reporter.log("Login Error fond", true); FolderStructure
		 * folderStructureObject=new FolderStructure(); String [] folderPathArray =
		 * folderStructureObject.reportFolderCreator(); ExtendReporter extend=new
		 * ExtendReporter(folderPathArray[1],"LoginError",0);
		 * extend.testCreation("Login error"); extend.addScreenshotMethod(driver,
		 * folderPathArray[2],"LoginError", 0);
		 * extend.errroMsg("User Id : "+loginModelObject.getUserId());
		 * extend.errroMsg("Password : "+loginModelObject.getPassword());
		 * extend.errroMsg("Yob : "+loginModelObject.getYob());
		 * extend.errroMsg(loginErrorStr); extend.tearDown("Fail"); extend.logFlush();
		 * 
		 * Reporter.log("Folder path ===> "+folderPathArray[0],true);
		 * 
		 * driver=null; }
		 */
		driver1=driver;
		setDriver(driver);
		Reporter.log("Driver object ====> "+driver1,true);
		return driver;
		
	}
	
	public void logout(WebDriver driver) throws InterruptedException {
		
		closeButton=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[1]/a");
		
		clickElement(closeButton,"Close order status popup");
		Thread.sleep(3000);
		logoutOption=fluentWaitCodeXpath(driver,"//*[@id='caUser']/span[1]");
		clickElement(logoutOption,"Logout option");
		
		logoutlink=fluentWaitCodeXpath(driver,"//a[text()=' Logout']");
		clickElement(logoutlink,"logout link");
		
		setDriver(driver);
		//logoutlink.click();
	}
	
	public void headerInExcel(CSVWriter writer) throws IOException {
		CsvReaderCode reader=new CsvReaderCode();
		String [] excelArray= {"Id","Action","Status","Order Action","Trading Symbol","Product Type","Order Price","Order Type","User id","Exchange","Validity","Nest Id"};
		reader.WriteFile(excelArray,writer);
	}
	

	
	public boolean logError(String xapthString,WebDriver driver) {
		Reporter.log("Checking login error ", true);
		boolean loginErrorFlag=false;
		WebElement loginErrorMsg=null;
		try {
		 loginErrorMsg=fluentWaitCodeXpath(driver,xapthString,10);
		 loginErrorStr=fetchTextFromElement(loginErrorMsg,"Login Error");
		 if(loginErrorStr.contains(">")) {
			 String [] logErrorArray=loginErrorStr.split("\\.");
			 loginErrorStr=logErrorArray[0].trim();
		 }
		 Reporter.log("Login Error ===> "+loginErrorStr,true);
		 
		}catch(TimeoutException e) {
			loginErrorFlag=true;
			
		}
		return loginErrorFlag;
	}

	

	public String getLoginErrorMsg() {
		return loginErrorMsg;
	}


	public void setLoginErrorMsg(String loginErrorMsg) {
		this.loginErrorMsg = loginErrorMsg;
	}


	public static  WebDriver getDriver() {
		return driver;
	}


	public  void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	public void clickOnLoginButton(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		try {
		popupButton=fluentWaitCodeXpath(driver, "//button[text()='No thanks']",20);
		clickElement(popupButton,"No thans popup button");
		}catch(Exception e) {
			Reporter.log("No thans popup not found", true);
		}
		
		loginButton=fluentWaitCodeXpath(driver, "//span[text()='Login']");
		clickElement(loginButton,"Login button");
		
		try {
		buyAndSellButton=fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']",30);
		}catch(Exception e) {
			Reporter.log("Again click on login button", true);
			clickElement(loginButton,"Login button");
		}
		clickElement(buyAndSellButton,"Buy/Sell link");
	}
	
public WebDriver loginCodeExecution(WebDriver driver,LoginTestModel loginModelObject,ExtendReporter extend) throws InterruptedException, IOException {
		
		Reporter.log("LoginPage : loginExecution ", true);
		
		clickOnLoginButton(driver);
		do {
		try {
			Reporter.log("Before userId", true);
			Reporter.log("LoginModel data "+loginModelObject.toString(), true);
		userIdTextField=fluentWaitCodeId(driver, "userID",30);
		Reporter.log("After locate userId", true);
		}catch(TimeoutException e) {
			Reporter.log("User id not found now again click onLogin button");
			clickOnLoginButton(driver);
		}
		}while(userIdTextField==null);
		
			String userId_Idvalue=userIdTextField.getAttribute("id");
		Reporter.log("userId_Idvalue ===> "+userId_Idvalue, true);
		if(elementPresentOrNot(driver, "//i[@class='glyphicon glyphicon-pencil editLoginID']", "xpath")) {
			editButton=fluentWaitCodeXpath(driver, "//i[@class='glyphicon glyphicon-pencil editLoginID']");
			clickElement(editButton, "Edit Button ");
		}
		clearAndSendKey(userIdTextField,loginModelObject.getUser_Id(),"User Id");
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Procceed Button ");
		logErrorChecker=logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[1]/span",driver);
		Reporter.log("After Userid and than processed "+logErrorChecker, true);
		if(logErrorChecker) {
		Reporter.log("LoginModel data "+loginModelObject.toString(), true);
		passwordTextField=fluentWaitCodeId(driver, "password");
		sendKey(passwordTextField, loginModelObject.getPassword(),"Password Textfield");
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Proceed Button");
		logErrorChecker=logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span",driver);
		if(logErrorChecker) {
		yobTextField=fluentWaitCodeId(driver, "ans");
		sendKey(yobTextField, loginModelObject.getYob(),"Yob TextField");
		
		continueButton=fluentWaitCodeXpath(driver,"//button[text()='Continue']");
		clickElement(continueButton,"ContinueButton");
		logErrorChecker=logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div/div[2]/div[3]/div[2]/span/span",driver);
		if(logErrorChecker) {
		noLoginProccess=logError("//h3[text()='Updates on WhatsApp coming soon']", driver);
		if(noLoginProccess==true) {
		notNowButton=fluentWaitCodeXpath(driver,"//a[text()='Not now']");
		clickElement(notNowButton,"Not now popup button");
		
		/*if(scenario.equalsIgnoreCase("Partial Order")) {
			WebElement popUpButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(popUpButton);
		}*/
		popupOkButton=driver.findElement(By.xpath("//button[text()='Ok']"));
		clickElement(popupOkButton,"Ok popup button");
		
		extend.loginReport(driver, extend, loginModelObject, loginErrorStr);
		logout(driver);
		}else {
			notNowButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(notNowButton,"Not now popup button");
			extend.loginReport(driver, extend, loginModelObject, loginErrorStr);
			logout(driver);
			
		}
		}
		}
		
		}
		if(logErrorChecker==false) {
			Reporter.log("Login Error fond", true);
			
			extend.loginReport(driver, extend, loginModelObject, loginErrorStr);
			
			Reporter.log("Folder path ===> "+FunctionKeyword.folderPath[0],true);
			closeLoginFrame=fluentWaitCodeXpath(driver, "//*[@id=\"loginModal\"]/div/div[1]/div/form/div[1]/button/span");
			clickElement(closeLoginFrame, "Login Detail Frame close button");
			
		}
		
		driver1=driver;
		setDriver(driver);
		Reporter.log("Driver object ====> "+driver1,true);
		return driver;
		
	}

	
	
}
