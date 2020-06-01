package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;
import com.shreeya.watchlistPages.WatchListCommon;

public class FundTransferPage extends SeleniumCoder {

	WebDriver driver;
	
	WebElement mtfRadioButton;
	WebElement eCollectRadionButton;
	WebElement upiViaORCodeRadioButton;
	WebElement amountToTransferTextField;
	WebElement submitButton;
	WebElement bankAccountRedionButton;
	WebElement internetBankingRadioButton;
	WebElement okButton;
	WebElement yesBankAlert;
	WebElement upiRadioButton;
	WebElement qickSelectAmountLabel;
	WebElement upiTextfield;
	WebElement upiCreateText;
	WebElement createUPILink;
	WebElement backtoFundTransferButton;
	WebElement addFundTab;
	WebElement balSummaryLabel;
	WebElement upiCreationPage;
	WebElement upiErrorMsg;
	WebElement upiServicePageLabel;
	
	Payment payment;
	FundTransferCommon fundTransferCommon;
	Help help;
	
	String upiCreateTextStr;
	String upiServiceProviderLabel;

	private WebElement amountErrorMsgLabel;
	
	public static List<String> detailList;

	private Object amountErrorMsgStr;

	private String upiErrorStr;

	private WebElement upiDropdownButton;

	private String upiDropdownText;

	private WebElement upiDropdownBelowLabel;
	public FundTransferPage() {
		
	}
	
	public FundTransferPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		help=new Help();
		payment=new Payment(driver);
		fundTransferCommon=new FundTransferCommon();
		detailList=new ArrayList<String>();
	}
	
	public void UPIRadioButtonTestCase() {
		upiRadioButton=fluentWaitCodeXpath(driver, "//label[@for='upi']","UPI via OR Code radio button");
		okButton=fluentWaitCodeXpath("//input[@value='OK']",10,"Ok button");
		if(okButton!=null) {
			clickElement(okButton , "Ok button");
		}
		clickElement(upiRadioButton, "UPI radioButton");
		okButton=fluentWaitCodeXpath("//input[@value='OK']",10,"Ok button");
		if(okButton!=null) {
			clickElement(okButton , "Ok button");
		}
		upiTextfield=fluentWaitCodeXpath("//input[@id='upiIdTxt']", "UPI texfield");
		
		
		clickUPICreatelink();
		
	}
	
	public void clickUPICreatelink() {
		detailList.add("@@>Verify for UPI id field for option available to create new UPI  id by clicking on Know how.<@@");
		detailList.add(ScreenshortProvider.captureScreen(driver, "CheckingUPICreateLink"));
		createUPILink=fluentWaitCodeXpath("//a[text()='Know how']", "create upi link");
		clickElement(createUPILink, "create upi link");
				detailList.add(ScreenshortProvider.captureScreen(driver, "UPIidCreationStepScreenshot"));
		detailList.add("@@> Verify when user clicks on back To fund Transfer while creating the UPI id from add funds page.<@@");
		backtoFundTransferButton=fluentWaitCodeXpath("//button[@gtmdir-text='Back to Fund Transfer | UPI']", "Back to fund transfer");
		clickElement(backtoFundTransferButton, "Back to fund transfer");
		
		
		
		
		
		eCollectBankVerification("HDFC BANK LTD.");
	}
	
	
	
	public void eCollectBankVerification(String bankName) {
		detailList.add("Verify in case user's bank is e-collect bank.");
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		clickElement(bankAccountRedionButton, "Bank acount radio button");
		eCollectRadionButton=fluentWaitCodeXpath("//label[text()='eCollect']", "eCollect radion button");
		if(eCollectRadionButton!=null) {
			detailList.add("eCollect payment mode option is available.");
			upiRadioButton=fluentWaitCodeXpath(driver, "//label[@for='upi']","UPI radio button");
			if(upiRadioButton.isSelected()) {
				detailList.add("UPI Payment Option already selected-PASS");
			}else {
				detailList.add("UPI Payment Option already selected-FAIL");
			}
		}
		
	}
	
	public void fillUpiTextfield(String upiId) {
		upiDropdownButton=fluentWaitCodeXpath("//label[@for='upi']//following::button[1]",10,"UPI dropdown button");
		if(upiDropdownButton==null) {
		upiTextfield=fluentWaitCodeXpath("//input[@id='upiIdTxt']", "UPI textfield");
		clearAndSendKey(upiTextfield,upiId, "UPI textfield");
		}else {
			upiDropdownText=getValueFromAttribute(upiDropdownButton, "value", "UPI drop down");
			upiDropdownBelowLabel=fluentWaitCodeXpath("//label[@for='upi']//following::p[1]", "UPI dropdown below label");
			detailList.add(fetchTextFromElement(upiDropdownBelowLabel));
		}
	}
	
	public void backToFundTransferModule() {
		redirectGivenUrl("https://ewuat.edelbusiness.in/ewhtml/");
		hoverAndClickOption(driver, "//*[@id='QuickSB']", "//a[text()='Transfer Funds']");
	}
	

	public void paymentModeSelect(String paymentMode,String bankName){
		try {
			eCollectRadionButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']//preceding::label[1]",15,"eCollect Radion Button");
			}catch(TimeoutException e) {
			internetBankingRadioButton=fluentWaitCodeXpath(driver, "//label[text()='Internet Banking']","internetBankingRadioButton");
			}
		if(!(bankName.equalsIgnoreCase("HDFC BANK LTD")||bankName.equalsIgnoreCase("Yes Bank"))){
			if(paymentMode.equalsIgnoreCase("Internet Banking")) 
			Reporter.log("Internet Banking radion button already selected ",true);
			else if(paymentMode.equalsIgnoreCase("eCollect")) {
				Reporter.log("eCollect Payment mode is not for "+bankName+" bank");
			}
			else if(paymentMode.equalsIgnoreCase("UPI via QR code")) {
				upiViaORCodeRadioButton=fluentWaitCodeXpath(driver, "//label[text()='UPI via QR code']","UPI via OR Code radio button");
				clickElement(upiViaORCodeRadioButton, "UPI via Or code radio button");
			}
			else if(paymentMode.equalsIgnoreCase("UPI")) {
				upiRadioButton=fluentWaitCodeXpath(driver,"//label[text()='UPI via QR code']//following::label[1]","upi Radio Button");
				clickElement(upiRadioButton, "UPI radio button");
			}
		}
		else if(bankName.equalsIgnoreCase("Yes Bank")) {
			yesBankAlert=fluentWaitCodeXpath(driver, "//span[text()=' Yes Bank']//following::span[6]","yesBank Alert");
			fetchTextFromElement(yesBankAlert, "yesBankAlert");
		}
			
			
	}
	
	public void bankAccountSelect(FundTransferModel model){
		staticWait(4000);
		String bankName=fundTransferCommon.bankNameGiver(model.getBank());
		bankAccountRedionButton=fluentWaitCodeXpath(driver, "//span[text()='"+bankName+"']",bankName);
		if(bankAccountRedionButton.isEnabled()==false)
			clickElement(bankAccountRedionButton, bankName+" Radio button");
		model.setBank(bankName);
	}
	
	public void fillAmount(String amount){
		String [] amountArray=fundTransferCommon.amountEnter(amount);
		
		amountToTransferTextField=fluentWaitCodeName(driver, "amt", 20,"Amount To Transfer TextField");
		clearAndSendKey(amountToTransferTextField, amount, "Amount To Transfer TextField");
		
	
		submitButton=fluentWaitCodeXpath(driver, "//input[@value='Submit']","submit button");
		clickElement(submitButton, "Submit button");
	}
	

	public void fundTransferexecute(FundTransferModel model) {
		Reporter.log(model.toString(), true);
		bankAccountSelect(model);
		paymentModeSelect(model.getPaymentMode(),model.getBank());
		if(model.getBank().equalsIgnoreCase("HDFC BANK LTD")) {
		okButton=fluentWaitCodeXpath(driver, "//input[@value='OK']","ok Button");
		clickElement(okButton, "Ok button");
		}
		UPIRadioButtonTestCase();
		fillAmount(model.getAmount());
		//payment.paymentCodeExecution(model);
	}
}
