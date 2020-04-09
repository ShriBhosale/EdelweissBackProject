package com.shreeya.page;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
		
		driver=browserLaunch(loginModelObject.getExecutionType());
		//userIdAndPwd(scenario);
		
		popupButton=fluentWaitCodeXpath(driver, "//button[text()='No thanks']");
		clickElement(popupButton);
		
		loginButton=fluentWaitCodeXpath(driver, "//span[text()='Login']");
		clickElement(loginButton);
		
		
		buyAndSellButton=fluentWaitCodeXpath(driver, "//a[text()='Buy/Sell']");
		clickElement(buyAndSellButton);
		
		userIdTextField=fluentWaitCodeId(driver, "userID");
		clearAndSendKey(userIdTextField,loginModelObject.getUserId());
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		
		passwordTextField=fluentWaitCodeId(driver, "password");
		sendKey(passwordTextField, loginModelObject.getPassword());
		
		proceedButton=fluentWaitCodeXpath(driver, "//button[text()='Proceed']");
		clickElement(proceedButton);
		if(logError("//*[@id=\"loginModal\"]/div/div[1]/div/form/div[2]/div/div[1]/div[2]/div[5]/span",driver)) {
		yobTextField=fluentWaitCodeId(driver, "ans");
		sendKey(yobTextField, loginModelObject.getYob());
		
		continueButton=fluentWaitCodeXpath(driver,"//button[text()='Continue']");
		clickElement(continueButton);
		
		
		noLoginProccess=logError("//h3[text()='Updates on WhatsApp coming soon']", driver);
		if(noLoginProccess==true) {
		notNowButton=fluentWaitCodeXpath(driver,"//a[text()='Not now']");
		clickElement(notNowButton);
		
		/*if(scenario.equalsIgnoreCase("Partial Order")) {
			WebElement popUpButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(popUpButton);
		}*/
		popupOkButton=driver.findElement(By.xpath("//button[text()='Ok']"));
		clickElement(popupOkButton);
		}else {
			notNowButton=fluentWaitCodeXpath(driver, "//button[text()='Not Now']");
			clickElement(notNowButton);
		}
		}else {
			FolderStructure folderStructureObject=new FolderStructure();
			String [] folderPathArray = folderStructureObject.reportFolderCreator(1);
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
			System.out.println("Folder path ===> "+folderPathArray[0]);
			driver=null;
		}
		//headerInExcel(writer);
		return driver;
	}
	
	public void logout(WebDriver driver) throws InterruptedException {
		
		closeButton=fluentWaitCodeXpath(driver,"//*[@id=\"myModal\"]/div/div/div[1]/a");
		
		clickElement(closeButton);
		Thread.sleep(3000);
		logoutOption=fluentWaitCodeXpath(driver,"//*[@id='caUser']/span[1]");
		clickElement(logoutOption);
		
		logoutlink=fluentWaitCodeXpath(driver,"//a[text()=' Logout']");
		clickElement(logoutlink);
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
		boolean loginErrorFlag=false;
		WebElement loginErrorMsg=null;
		try {
		 loginErrorMsg=fluentWaitCodeXpath(driver,xapthString,10);
		 loginErrorStr=fetchTextFromElement(loginErrorMsg);
		 if(loginErrorStr.contains(">")) {
			 String [] logErrorArray=loginErrorStr.split("\\.");
			 loginErrorStr=logErrorArray[0].trim();
		 }
		 System.out.println("Login Error ===> "+loginErrorStr);
		 
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


	
	
	
}
