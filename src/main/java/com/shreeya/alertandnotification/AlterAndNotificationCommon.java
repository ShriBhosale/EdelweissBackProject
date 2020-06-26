package com.shreeya.alertandnotification;

import java.util.List;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

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
	public static String[] deletArray;

	public AlterAndNotificationCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help(driver);
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
		 scriptNo=0;
		
		List<String> scriptNameList=multipleElementsTextProvider("//label[@class='ed-lbl reg scripnamenone ng-binding ng-scope']", "ScriptName");
		for(int i=0;i<scriptNameList.size();i++) {
			if(scriptNameList.get(i).equalsIgnoreCase(scripName)) {
				scripNo=i+1;
				break;
			}
		}
		Reporter.log("scripNo : "+scripNo, true);
		return scripNo;
	}
	
	public void afterDelete(String scripName,boolean cancelOrNot) {
		Reporter.log("======> afterDelete <======", true);
		
		int count=0;
		do {
			count++;
			staticWait(500);
		alterTitle=fluentWaitCodeXpath("//h1[text()='Alerts']", "alerts title");
		if(alterTitle!=null) {
			if(cancelOrNot) {
			deletArray[1]=scripName+" is not present-FAIL";
			if(scriptIndex(scripName)!=0) 
				deletArray[1]=scripName+" is present-PASS";
			}else {
				deletArray[1]=scripName+" is not present-FAIL";
			}
		}
		}while(alterTitle==null && count >5);
	}
	
	
	public void deleteAlert(String scripName,boolean cancelOrNot) {
		Reporter.log("=====> deleteAlert <=====", true);
		deletArray=new String[3];
		scripNo=scriptIndex(scripName);
		checkBoxXpath=help.xpathMaker("//*[@id=\"allRecosAccord\"]/tbody/tr["+scripNo+"]/td[1]/a[1]/label");
		scriptCheckBox=fluentWaitCodeXpath(checkBoxXpath, scripName+" check box");
		//clickElement(scriptCheckBox, "script checkbox");
		clickByActionClass(scriptCheckBox, "script checkbox");
		clickElement("//a[text()='Delete Alert']", "Delete button");
		deletePopupMsg=fluentWaitCodeXpath("//input[@value='Cancel']//preceding::label[1]", "Delete popup msg");
		deletArray[0]=fetchTextFromElement(deletePopupMsg);
		deletArray[2]=ScreenshortProvider.captureScreen(driver, "AlertDeletePopup");
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
}
