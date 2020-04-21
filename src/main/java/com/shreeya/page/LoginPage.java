package com.shreeya.page;

import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.opencsv.CSVWriter;
import com.shreeya.model.LoginModel;
import com.shreeya.model.TestDataModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.CsvReaderCode;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.FolderStructure;
import com.shreeya.util.SeleniumCoder;

import net.bytebuddy.description.modifier.SynchronizationState;

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
	WebDriver driver;
	WebElement popupOkButton;
	
	boolean noLoginProccess=false;
	private WebElement closePopupButton;
	private WebElement closeButton;
	private WebElement logoutOption;
	private WebElement logoutlink;
	String userIdStr,passwordstr,yobstr;
	private String loginErrorMsg="no error";
	WebDriver driver1;
	private String loginErrorStr;
	
	static Logger log = Logger.getLogger(LoginPage.class.getName());
	public LoginPage() {
			
	}
	
	
	public WebDriver loginExecution(LoginModel loginModelObject) throws InterruptedException, IOException {
		
		
		driver=browserLaunch(loginModelObject.getModule());
		
		//userIdAndPwd(scenario);
		
		clickOnLoginButton(driver);
		
		try {
		userIdTextField=fluentWaitCodeId(driver, "userID",20);
		}catch(Exception e) {
			clickOnLoginButton(driver);
		}
		clearAndSendKey(userIdTextField,loginModelObject.getUserId(),"User Id");
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Procceed Button ");
		
		passwordTextField=fluentWaitCodeId(driver, "password");
		sendKey(passwordTextField, loginModelObject.getPassword(),"Password Textfield");
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton,"Proceed Button");
		if(logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span",driver)) {
		yobTextField=fluentWaitCodeId(driver, "ans");
		sendKey(yobTextField, loginModelObject.getYob(),"Yob TextField");
		
		continueButton=fluentWaitCodeXpath(driver,"//button[text()='Continue']");
		clickElement(continueButton,"ContinueButton");
		
		
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
		}else {
			notNowButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(notNowButton,"Not now popup button");
		}
		}else {
			Reporter.log("Login Error fond", true);
			FolderStructure folderStructureObject=new FolderStructure();
			String [] folderPathArray = folderStructureObject.reportFolderCreator();
			ExtendReporter extend=new ExtendReporter(folderPathArray[1],"LoginError",0);
			extend.testCreation("Login error");
			extend.addScreenshotMethod(driver, folderPathArray[2],"LoginError", 0);
			extend.errroMsg("User Id : "+userIdStr);
			extend.errroMsg("Password : "+passwordstr);
			extend.errroMsg("Yob : "+yobstr);
			extend.errroMsg(loginErrorStr);
			extend.tearDown("Fail");
			extend.logFlush();
			driver.close();
			Reporter.log("Driver close ", true);
			Reporter.log("Folder path ===> "+folderPathArray[0],true);
			
			driver=null;
		}
		
		
		//headerInExcel(writer);
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
		//logoutlink.click();
	}
	
	public void headerInExcel(CSVWriter writer) throws IOException {
		CsvReaderCode reader=new CsvReaderCode();
		String [] excelArray= {"Id","Action","Status","Order Action","Trading Symbol","Product Type","Order Price","Order Type","User id","Exchange","Validity","Nest Id"};
		reader.WriteFile(excelArray,writer);
	}
	

	public void userIdAndPwd(String scenario) {
		String[] userIdArray=null,passwordArray=null,yobArray=null;
		ConfigReader configReader=new ConfigReader();
		String userId=configReader.configReader("userId");
		String password=configReader.configReader("password");
		String yob=configReader.configReader("yob");
		if(userId.contains(",")) {
			userIdArray=userId.split(",");
		}
		if(password.contains(",")) {
			passwordArray=password.split(",");
		}
		if(yob.contains(",")) {
			yobArray=yob.split(",");
		}
		
		if(scenario.equalsIgnoreCase("Partial Order")) {
			userIdStr=userIdArray[1];
			passwordstr=passwordArray[1];
			yobstr=yobArray[1];
		}else {
			userIdStr=userIdArray[0];
			passwordstr=passwordArray[0];
			yobstr=yobArray[0];
		}
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


	public void clickOnLoginButton(WebDriver driver) throws InterruptedException {
		popupButton=fluentWaitCodeXpath(driver, "//button[text()='No thanks']");
		clickElement(popupButton,"No thans popup button");
		
		loginButton=fluentWaitCodeXpath(driver, "//span[text()='Login']");
		clickElement(loginButton,"Login button");
		
		
		buyAndSellButton=fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']");
		clickElement(buyAndSellButton,"Buy/Sell link");
	}
	
	
}
