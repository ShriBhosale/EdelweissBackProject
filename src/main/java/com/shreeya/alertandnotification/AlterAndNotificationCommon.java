package com.shreeya.alertandnotification;

import java.util.List;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.commonpage.CommonOrderDetail;
import com.shreeya.commonpage.PlaceOrder;
import com.shreeya.model.AlertAndNotificationModel;
import com.shreeya.model.LatestLoginModel;
import com.shreeya.model.OrderPlaceModel;
import com.shreeya.orderdetailpages.LoginPage;
import com.shreeya.util.BrowserLaunch;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class AlterAndNotificationCommon extends SeleniumCoder{
	WebDriver driver;
	private WebElement closePopupButton;
	Help help;
	private int scriptNo;
	private int scripNo;
	private String checkBoxXpath;
	private WebElement scriptCheckBox;
	private WebElement deletePopupMsg;
	private WebElement cancelButton;
	private WebElement deleteButton;
	private WebElement alterTitle;
	private String ltpNo;
	private String ltpNoLabel;
	private String valueLable;
	private String valueLabel;
	private String valueIsLabel;
	private String checkBoxClass;
	private String applicationValue;
	
	public static String[] deletArray;
	ConfigReader configReader;
	PlaceOrder placeOrder;
	CommonOrderDetail orderDetail;

	public AlterAndNotificationCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
		configReader=new ConfigReader();
		configReader.alertConfig();
		placeOrder=new PlaceOrder(driver);
		orderDetail=new CommonOrderDetail(driver);
	}
	
	public void redirectToAlterAndNotificationModule(boolean pageRefershOrNot) {
		Reporter.log("====> redirectToAlterAndNotificationModule <====", true);
		staticWait(1000);
		closePopupButton = fluentWaitCodeXpath("//a[@class='ed-icon i-close lg']",50, "Close popup button");
		if(closePopupButton!=null)
		clickElement(closePopupButton,  "Close popup button");
		try {
		hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}catch(ElementNotInteractableException e) {
			help.pageRefresh();
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}
		WebElement alterTitle=fluentWaitCodeXpath("//h1[text()='Alerts']",30, "Alter title");
		if(alterTitle==null) {
			hoverAndClickOption("//*[@id='QuickSB']", "//li//a[text()='My Alerts']");
		}
		if(pageRefershOrNot)
			pageRefresh();
		staticWait(700);
	}
	
	public String checkActiveOrNot(String xpathStr,String elementName) {
		Reporter.log("======> checkActiveOrNot <=====", true);
		String result="No";
		String classValue=getValueFromAttribute(xpathStr, "class", elementName);
		if(classValue.equalsIgnoreCase("active"))
			result=elementName+" button is active";
		else
			result=elementName+" button is not active";
		return result;
	}
	
	public String elementPresent(String xpath,String elementName) {
		Reporter.log("=====> elementPresent <====", true);
		String result="No";
		WebElement element=fluentWaitCodeXpath(xpath, elementName);
		if(element!=null)
			result=elementName+" is present";
		else
			result=elementName+" is not present";
		Reporter.log(result, true);
		return result;
	}
	
	public int scriptIndex(String scripName) {
		Reporter.log("======> scriptIndex <=====", true);
		 int scripIndex=0;
		 scripName=help.checkScripName(scripName);
		List<String> scriptNameList=multipleElementsTextProvider("//label[@class='ed-lbl reg scripnamenone ng-binding ng-scope']", "ScriptName");
		for(int i=0;i<scriptNameList.size();i++) {
			if(scriptNameList.get(i).equalsIgnoreCase(scripName)) {
				scripIndex=i+1;
				break;
			}
		}
		Reporter.log("scripNo : "+scripIndex, true);
		return scripIndex;
	}
	
	public void afterDelete(String scripName,boolean cancelOrNot) {
		Reporter.log("======> afterDelete <======", true);
		scripName=help.checkScripName(scripName);
		deletArray[3]=help.removeHtmlReporter(fetchTextFromElement("//label[@class='mbMsg ng-binding']", "Delete Msg"));
		deletArray[2]=ScreenshortProvider.captureScreen(driver, "AlertDeletePopup");
		int scripNo=scriptIndex(scripName);
		int count=0;
		do {
			count++;
			staticWait(500);
		alterTitle=fluentWaitCodeXpath("//h1[text()='Alerts']", "alerts title");
		if(alterTitle!=null) {
			if(cancelOrNot) {
			deletArray[1]=scripName+" is not present-FAIL";
			if(scripNo!=0) 
				deletArray[1]=scripName+" is present-PASS";
			}else {
				if(scripNo!=0) 
					deletArray[1]=scripName+" is  present-FAIL";
				else
				deletArray[1]=scripName+" is not present-PASS";
			}
		}
		}while(alterTitle==null && count >5);
	}
	
	public void clickDelete(String scripName) {
		int i=0;
		scripNo=scriptIndex(scripName);
		if(scripNo!=0) {
		do {
			i++;
			Reporter.log("clickDelete "+i);
			checkBoxXpath=help.xpathMaker("//*[@id=\"allRecosAccord\"]/tbody/tr["+scripNo+"]/td[1]/a[1]/label");
			scriptCheckBox=fluentWaitCodeXpath(checkBoxXpath, scripName+" check box");
			//clickElement(scriptCheckBox, "script checkbox");
			staticWait(5000);
			checkBoxClass=getValueFromAttribute("//*[@id=\"checkme0\"]", "class", "checkBox");
			//if(!checkBoxClass.contains("ng-not-empty")) {
				i=5;
			clickByActionClass(scriptCheckBox, "script checkbox");
			//}
		}while(i<5);
		}else {
			Reporter.log(scripName+" script not present.", true);
		}
	}
	
	
	public void deleteAlert(String scripName,boolean cancelOrNot) {
		Reporter.log("=====> deleteAlert <=====", true);
		deletArray=new String[5];
		
		clickDelete(scripName);
		staticWait(3000);
		clickElement("//a[text()='Delete Alert']", "Delete button");
		deletePopupMsg=fluentWaitCodeXpath("//input[@value='Cancel']//preceding::label[1]", "Delete popup msg");
		deletArray[0]=fetchTextFromElement(deletePopupMsg);
		
		cancelButton=fluentWaitCodeXpath("//input[@value='Cancel']", "Cancel button");
		deleteButton=fluentWaitCodeXpath("//input[@value='Delete']", "Delete button");
		if(cancelOrNot) {
			clickElement(cancelButton, "Cancel button");
			afterDelete(scripName, cancelOrNot);
		}else {
			clickElement(deleteButton, "Delete button");
			afterDelete(scripName, cancelOrNot);
		}
	}
	
	public String checkScriptPresent(String scripName) {
		Reporter.log("======> checkScriptPresent <======", true);
		int scriptNo=scriptIndex(scripName);
		Reporter.log("Script no ======> "+scriptNo, true);
		String result="No";
		if(scriptNo==0)
			result=scripName+" is not present";
		else
			result=scripName+" is present";
		return result;
	}
	
	public void verifyAlert(String scripName,String value,String valueIs,List<String> detailList) {
		Reporter.log("=====> verifyAlert <=====", true);
		Reporter.log("Script Name : "+scripName, true);
		int scripCount=scriptIndex(scripName);
		detailList.add(checkScriptPresent(scripName));
		if(scripCount!=0) {
			ltpNoLabel=fetchTextFromElement(makeXpath("//*[@id=\"allRecosAccord\"]/tbody/tr["+scripCount+"]/td[2]/label[1]"), scripName+" ltp no");
			valueLabel=fetchTextFromElement(makeXpath("//*[@id=\"allRecosAccord\"]/tbody/tr["+scripCount+"]/td[5]/label[1]"), "value");
			valueIsLabel=fetchTextFromElement(makeXpath("//*[@id=\"allRecosAccord\"]/tbody/tr["+scripCount+"]/td[5]/label[2]"), "value is");
			if(valueLabel.contains("≤"))
				applicationValue=help.removeHtmlReporter(valueLabel).replace("≤", "").trim();
			else if(valueLabel.contains("≥"))
				applicationValue=help.removeHtmlReporter(valueLabel).replace("≥", "").trim();
			detailList.add("LTP : "+ltpNoLabel);
			detailList.add("Value : "+help.commpareTwoString(applicationValue, value));
			detailList.add("Value is : "+help.commpareTwoString(valueIsLabel, valueIs));
		}
	}
	
	
	
	public String placeOrderWithDiffUserId(String segment,AlertAndNotificationModel model) {
		Reporter.log("======> placeOrderWithDiffUserId <======",true);
		BrowserLaunch launch=new BrowserLaunch();
		WebDriver driver=launch.browserLaunch("");
		String status="no";
		LoginPage login=new LoginPage(driver);
		LatestLoginModel loginModelObject;
		if(segment.equalsIgnoreCase("Equity")) {
		 loginModelObject=new LatestLoginModel(1, configReader.configReaderAN("UserIdEQ"), 
				configReader.configReaderAN("PasswordEQ"),  configReader.configReaderAN("YobEQ"), "Alert",segment);
		}else {
			 loginModelObject=new LatestLoginModel(1, configReader.configReaderAN("UserIdCo"), 
					configReader.configReaderAN("PasswordCo"),  configReader.configReaderAN("YobCo"), "Alert",segment);
		}
		login.loginExecution("", loginModelObject);
		OrderPlaceModel orderPlaceModel=new OrderPlaceModel(model.getReferNo(), model.getStockName(), model.getSegment(), "Sell", model.getValue(), model.getProductType(), model.getQty());
		String orderDetailScreenshot=placeOrder.orderPlace(orderPlaceModel);
		if(segment.equalsIgnoreCase("Equity")) {
		String [] orderDetailArray=orderDetail.orderDetailProvider(driver, "new", "1", orderPlaceModel);
		status=orderDetailArray[2];
		}else {
			List<String> orderDetailList=orderDetail.commodityorderDetail(orderPlaceModel);
			status=orderDetailList.get(0);
		}
		driver.close();
		return status;
			
	}
}
