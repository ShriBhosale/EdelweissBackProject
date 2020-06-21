package com.shreeya.fundtransferpages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferCommon extends SeleniumCoder {
	

	WebDriver driver;
	private WebElement bankAccountRedionButton;
	private WebElement internetBankingRadioButton;
	private WebElement okButton;
	private WebElement amountToTransferTextField;
	private WebElement submitButton;
	private String addFundforScreenshot;
	private WebElement bankAccountLabel;
	private WebElement paymentModeLabel;
	private WebElement upiRadioButton;
	private WebElement upiDropdownButton;
	public static String upiDropdownText;
	private WebElement addNewUPIidLink;
	private WebElement upiTextfield;
	private WebElement redirectMsgLabel1;
	private WebElement seeMarginBalance;
	
	
	public FundTransferCommon(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public FundTransferCommon() {}

	public String [] amountEnter(String amount) {
		String [] ansArray= {"QickSelectAmount","EnterAmount"};
		
		int qickAmount=0,enterAmount=0;
		int amountInt=Integer.valueOf(amount);
		int [] QuickSelectAmountArray= {5000,10000,20000};
		for(int i=QuickSelectAmountArray.length-1;i>-1;i--) {
			if(QuickSelectAmountArray[i]==amountInt) {
				ansArray[0]=String.valueOf(QuickSelectAmountArray[i]);
				break;
			}else if(QuickSelectAmountArray[i]<amountInt){
				
				ansArray[0]=String.valueOf(QuickSelectAmountArray[i]);
				qickAmount=Integer.valueOf(ansArray[0]);
				ansArray[1]=String.valueOf(amountInt-qickAmount);
				enterAmount=Integer.valueOf(ansArray[1]);
				for(int amountI:QuickSelectAmountArray)
				{
					if(enterAmount==amountI)
					{
						ansArray[0]=ansArray[0]+","+enterAmount;
						ansArray[1]="EnterAmount";
						break;
					}
				}
				
				
				break;
			}else if(QuickSelectAmountArray[0]>amountInt){
				ansArray[1]=String.valueOf(amountInt);
			}else {
				continue;
			}
		}
		return ansArray;
	
		
	}
	
	public String bankNameGiver(String bankName) {
		String bank="No bank";
		Map<String,String> bankMap=new HashMap<String,String>();
		bankMap.put("citibank", "Citibank Na");
		bankMap.put("kotak", "Kotak Mahindra Bank");
		bankMap.put("hdfc", "HDFC BANK LTD.");
		bankMap.put("icici", "ICICI BANK LTD");
		bankMap.put("axis", "AXIS BANK");
		bankMap.put("yes", " Yes Bank");
		bankMap.put("andhra","ANDHRA BANK");
		bankMap.put("state bank", "State bank of India");
		
		for(Map.Entry<String,String> entry:bankMap.entrySet()) {
			if(bankName.toLowerCase().contains(entry.getKey())) {
				bank=entry.getValue();
				break;
			}
		}
		return bank;
	}
	
	public String [] cardNumberArray(String number) {
		System.out.println("Number : "+number);
		String [] numberArray= {"","","",""};
		char [] digitArray=number.toCharArray();
		int count=0,a=0,j=0;
		for(int i=0;i<digitArray.length;i++) {
			
			count++;
			if(count<5) {
				numberArray[a]=numberArray[a]+digitArray[i];
				j=i;
			}else if(count==5) {
				a++;
				count=0;
				i=j;
			}
		}
		
		return numberArray;
	}
	
	public String removeHtmlTextCheckText(String text,String checkText) {
		String msg="";
		String result="FAIL";
		if(text.contains("\n"))
			text=text.replace("\n", "").trim();
		String [] textArray=text.split("<");
		 //msg=textArray[0];
		for(String textStr:textArray) {
			if(textStr.contains(checkText)) {
				result="PASS";
			}
		 textArray=textStr.trim().split(">");
		 Reporter.log(textStr, true);
		 msg=msg+" "+textArray[textArray.length-1];
		}
		Reporter.log("removeHtmlText msg : "+msg+"-"+result, true);
		return msg+"-"+result;
	}
	
	public String verifAccountNo(String accountNo,String bank) {
		int xNo=0;
		String [] accountNoArray= {"","",""};
		int count;
		//String account="12345678901234";
		char [] accountNoCharArray=accountNo.toCharArray();
		/*
		 * if(bank.equalsIgnoreCase("ICICI BANK LTD"))
		 * count=accountNoCharArray.length-4; else
		 */
		 count=accountNoCharArray.length-5;
		for(int i=0;i<accountNoCharArray.length;i++) {
			if(i==0 || i==1) {
				accountNoArray[0]=accountNoArray[0]+accountNoCharArray[i];
			}
			else if(i>count) {
				accountNoArray[2]=accountNoArray[2]+accountNoCharArray[i];
			}else {
				
				if(xNo>0) 
				accountNoArray[1]=accountNoArray[1]+"X";
				
				xNo++;
			}
		}
		System.out.println("Account no : "+accountNoArray[0]+accountNoArray[1]+accountNoArray[2]);
		return accountNoArray[0]+accountNoArray[1]+accountNoArray[2];
	}
	
	
	public String submitAddFundForm(String bankName,String amount) {
		Reporter.log("===> submitAddFundForm <====", true);
		String msg="No";
		//String bankName="Kotak Mahindra Bank";
		bankAccountLabel=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		bankAccountRedionButton=fluentWaitCodeXpath("//span[text()='"+bankName+"']//preceding::input[1]",bankName+ "radio button");
		String selectedBankStr=getValueFromAttribute(bankAccountRedionButton, "gtmdir-text", "bank Radio button");
		Reporter.log("bank selected bank : "+selectedBankStr, true);
		if(!selectedBankStr.contains(bankName)) {
			
				clickElement(bankAccountLabel, bankName+"radio button");
		}
		
		
		Reporter.log("After click on bank acount radio button", true);
		staticWait(200);
		internetBankingRadioButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']","internetBankingRadioButton");
		if(internetBankingRadioButton==null)
			msg="Internet banking option not present...";
		staticWait(1000);
		Reporter.log("Before okButton.", true);
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']",10,"ok Button");
		if(okButton!=null)
		clickElement(okButton, "Ok button");
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
		//enterLargeText(amount, amountToTransferTextField);
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		addFundforScreenshot=ScreenshortProvider.captureScreen(driver, "AddFundScreenshot");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "Submit button");
		return msg+"-"+addFundforScreenshot;
	}
	
	public String checkingTransferMode() {
		staticWait(1000);
		int counter=0;
		String currentUrl=null;
		String transferMode="Native";
		do {
		 currentUrl=currentUrl();
		if(currentUrl.contains("AtomBank")) {
			transferMode="Atom";
		}else if(currentUrl.contains("razorpay")) {
			transferMode="Razor";
		}
		counter++;
		Reporter.log("checkingTansfermode do while loop "+counter);
		}while(transferMode=="Native" && counter<15);
		Reporter.log("Transfer mode : "+transferMode, true);
		return transferMode;
	}
	
	public String transferSuccessullBank(String bank) {
		String bankName="";
		Map<String,String> bankMap=new HashMap<String,String>();
		bankMap.put("Kotak Mahindra Bank", "Kotak Mahindra");
		bankMap.put("State bank of India", "STATEBANKOFINDIA");
		bankMap.put("ICICI BANK LTD", "ICICI BANK LTD");
		bankMap.put("ANDHRA BANK", "Andhra Bank");
		
		for(Map.Entry<String,String> entry:bankMap.entrySet()) {
			if(bank.toLowerCase().equalsIgnoreCase(entry.getKey())) {
				bankName=entry.getValue();
				break;
			}
		}
		return bankName;
	}
	
	public String romoveBrSpanFromText(String fetchTextFromElement) {
		String result="";
		Reporter.log("=== romoveBrSpanFromText ====", true);
		if(fetchTextFromElement.contains("\n"))
		fetchTextFromElement=fetchTextFromElement.replace("\n", "");
		String [] array=fetchTextFromElement.split("span");
		String [] primaryArray=array[0].split("<br>                        &nbsp;&nbsp;");
		for(String text:primaryArray) {
			Reporter.log(text,true);
			if(text.contains("<"))
				text=text.replace("<", "");
			result=result+" "+text.trim();
		}
		return result.trim();
	}
	
	public void backFundTransferPage(){
		Reporter.log("===> backFundTransferPage <===", true);
		staticWait(500);
		boolean flag=true;
		String currentUrl=driver.getCurrentUrl();
		driver.navigate().back();
		if(flag) {
				driver.get("https://ewuat.edelbusiness.in/ewhtml/");
				staticWait(1000);
				hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[3]/ul/li[2]/a");
				
		}
		staticWait(500);
	}
	
	
	public void checkThenBackFundTransfer() {
		paymentModeLabel=fluentWaitCodeXpath("//label[text()='Payment Mode']",10, "Payment mode");
		if(paymentModeLabel==null) {
		staticWait(500);
		boolean flag=true;
		String currentUrl=driver.getCurrentUrl();
		driver.navigate().back();
		if(flag) {
				driver.get("https://ewuat.edelbusiness.in/ewhtml/");
				staticWait(1000);
				try {
				hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[3]/ul/li[2]/a");
				}catch(JavascriptException e) {
					staticWait(3000);
					hoverAndClickOption(driver, "//*[@id='QuickSB']", "//*[@id='headerCntr']/nav/div/div[1]/div[2]/div[2]/ul/li[1]/div[1]/div/div[3]/ul/li[2]/a");
				}
				
		}
		}
		staticWait(500);
	}
	
	public String fillFormForUPI(String bankName, String amount,String upiId) {
		Reporter.log("===> submitAddFundForm <====", true);
		String msg="No";
		//String bankName="Kotak Mahindra Bank";
		bankAccountLabel=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		bankAccountRedionButton=fluentWaitCodeXpath("//span[text()='"+bankName+"']//preceding::input[1]",bankName+ "radio button");
		String selectedBankStr=getValueFromAttribute(bankAccountRedionButton, "gtmdir-text", "bank Radio button");
		Reporter.log("bank selected bank : "+selectedBankStr, true);
		if(!selectedBankStr.contains(bankName)) {
			
				clickElement(bankAccountLabel, bankName+"radio button");
		}
		
		
		Reporter.log("After click on bank acount radio button", true);
		staticWait(200);
		upiRadioButton=fluentWaitCodeXpath("//label[@for='upi']","UPI radio button");
		if(upiRadioButton!=null)
			clickElement(upiRadioButton, "UPI radio button");
		
		upiTextfiedOrDropdown(upiId);
		staticWait(1000);
		Reporter.log("Before okButton.", true);
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']",10,"ok Button");
		if(okButton!=null)
		clickElement(okButton, "Ok button");
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		addFundforScreenshot=ScreenshortProvider.captureScreen(driver, "AddFundScreenshot");
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "Submit button");
		return msg+"-"+addFundforScreenshot;
	}
	
	public void upiTextfiedOrDropdown(String upiId) {
		upiDropdownButton=fluentWaitCodeXpath("//label[@for='upi']//following::button[1]",10,"UPI dropdown button");
		if(upiId.equalsIgnoreCase("")) {
			
			upiDropdownText=getValueFromAttribute(upiDropdownButton, "text", "UPI drop down");
		}else {
			if(upiDropdownButton!=null) {

				clickElement(upiDropdownButton, "UPI dropdown");
			addNewUPIidLink=fluentWaitCodeXpath("//a[text()='Add New UPI ID']", "Add new UPI id link");
			clickElement(addNewUPIidLink,"Add new UPI id link");
			okButton=fluentWaitCodeXpath("//input[@value='OK']",10,"Ok button");
			if(okButton!=null) {
				clickElement(okButton , "Ok button");
			}
			upiTextfield=fluentWaitCodeXpath("//input[@id='upiIdTxt']", "UPI textfield");
			clearAndSendKey(upiTextfield,upiId, "UPI textfield");

			}
		}
		
	}
	
	public void disappearRedirectionMsg() {	
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='bank']//label[@class='redirectInfo']")));
		staticWait(1000);
	}
	
	public void elementDisappear(String xpathStr) {
		WebDriverWait wait = new WebDriverWait(driver, 8);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpathStr)));
		staticWait(1000);
	}
	
	
	public String seeMarginClearCashBalance() {
		Reporter.log("===> seeMarginClearCashBalance <===", true);
		String seeMarginBalanceStr="";
		do {
		seeMarginBalance=fluentWaitCodeXpath("//*[@id='myModal']/div/div/div[3]/div[2]/div[4]/div/div[2]/div[1]/div[1]/label[2]/span", "See margin balance");
		seeMarginBalanceStr=fetchTextFromElement(seeMarginBalance);
		staticWait(100);
		}while(seeMarginBalanceStr.equalsIgnoreCase(""));
		Reporter.log("seeMarginBalanceStr : "+seeMarginBalanceStr, true);
		return seeMarginBalanceStr;
	}
	
	public static void main(String[] args) {
		FundTransferCommon c=new FundTransferCommon();
		
	}
		
	}

	
