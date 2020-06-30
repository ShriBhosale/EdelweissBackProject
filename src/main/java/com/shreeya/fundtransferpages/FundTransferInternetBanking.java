package com.shreeya.fundtransferpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.model.LatestLoginModel;
import com.shreeya.util.ConfigReader;
import com.shreeya.util.Help;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferInternetBanking extends SeleniumCoder {

	WebDriver driver;
	List<String> detailList;
	private WebElement fundTransferTab;
	private WebElement transferStatusTab;
	private WebElement addFundTab;
	private WebElement withDrawTab;
	private WebElement transferStatusLabel;
	private WebElement bankNameLabel;
	private WebElement bankAccountRedionButton;
	private WebElement accountNoLabel;
	private WebElement amountToTransferTextField;

	Help help;
	ConfigReader cofigReader;
	FundTransferCommon common;
	private WebElement internetBankingRadioButton;
	private WebElement okButton;
	private WebElement submitButton;
	private WebElement redirectMsgLabel;
	private WebElement fromBankAccountLabel;
	private WebElement paragraph;
	private WebElement primaryLabel;
	private String primaryAcStr;
	private WebElement crnNoTextfield;

	Payment payment;
	private WebElement bankLabel;
	private WebElement failMsg;
	private String cancelKotakTransactionScreenshot;
	private WebElement retryButton;
	FundTransferModel model;
	private String accoutnNoStr;
	private List<String> screenshotList;
	private WebElement redirectMsgLabel1;
	private WebElement redirectMsgLabel2;
	
	LoginBankAccoutCloseDriver loginBankAccoutCloseDriver;
	private String bankForAtomic;
	private String equitaScreenshot;
	private String accountNoStr;
	private WebElement amountLabel;
	private String addFundAmountStr;

	public FundTransferInternetBanking(WebDriver driver) {
		super(driver);
		this.driver = driver;
		detailList = new ArrayList<String>();
		help = new Help();
		common = new FundTransferCommon(driver);
		payment = new Payment(driver);
		model = new FundTransferModel();
		loginBankAccoutCloseDriver=new LoginBankAccoutCloseDriver(driver);
		cofigReader=new ConfigReader();
	}

	public void verifyFundTransferTabs() {
		Reporter.log("===> verifyFundTransferTabs <===", true);
		detailList.add("@@> Verify user is able to switch between the tabs in fundtransfer page. <@@");
		detailList.add(ScreenshortProvider.captureScreen(driver, "checkFundTransferAllTabs"));

		addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']","add fund tab");
		clickElement(addFundTab, "Add fund tab");
		fromBankAccountLabel = fluentWaitCodeXpath("//*[@id='addFunds']/ng-include/label", "Transfer Status");
		tabVerify(fromBankAccountLabel, "Add fund tab");

		withDrawTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Withdraw Funds']","with draw tab");
		clickElement(withDrawTab, "transfer status tab");
		paragraph = fluentWaitCodeXpath("//*[@id='sub-tab3']/div/label", "Transfer Status");
		tabVerify(paragraph, "with draw tab");

		transferStatusTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "transfer status tab");
		clickElement(transferStatusTab, "transfer status tab");
		transferStatusLabel = fluentWaitCodeXpath("//h5[text()='Transfer Status']", "Transfer Status");
		tabVerify(transferStatusLabel, "transfer status tab");
	}
	
	private void threeTabPresentOrNot() {
		Reporter.log("===> threeTabPresentOrNot <===", true);
		detailList.add("@@> Verify the Add funds,withdraw funds,transfer status tabs are present in fund transfer tab. <@@");
		addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']","add fund tab");
		if(addFundTab!=null)
			detailList.add("Add FundTransfer tab is present-PASS");
		else
			detailList.add("Add FundTransfer tab not is present-FAIL");
		withDrawTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Withdraw Funds']","with draw tab");
		if(withDrawTab!=null)
			detailList.add("withDraw tab is present-PASS");
		else
			detailList.add("withDraw tab is not present-FAIL");
		transferStatusTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']", "transfer status tab");
		if(transferStatusTab!=null)
			detailList.add("TransferStatus Tab is present-PASS");
		else
			detailList.add("TransferStatus Tab is not present-FAIL");
	}

	public void tabVerify(WebElement element, String tabName) {
		Reporter.log("===> tabVerify <===", true);
		if (element != null) {
			detailList.add("User able to switch on " + tabName + "-PASS");
			Reporter.log("User able to switch on " + tabName, true);
		} else {
			detailList.add("User does not switch on " + tabName + "-FAIL");
			Reporter.log("User does not switch on " + tabName, true);
		}
	}

	public void bankNameAccountNoDisplay() {
		Reporter.log("===> bankNameAccountNoDisplay <====", true);
		detailList.add("@@> Verify the details for bank accounts available in add funds page <@@");
		List<String> bankNameList = multipleElementsTextProvider(
				"//*[@id='addFunds']/ng-include/div[1]/ul//li//div//label//span[@class='bnkName ng-binding']",
				"Bank radio buttons text");
		if (bankNameList.size() == 0) {
			addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']",
					"add fund tab");
			clickElement(addFundTab, "Add fund tab");
			bankNameList = multipleElementsTextProvider(
					"//*[@id='addFunds']/ng-include/div[1]/ul//li//div//label//span[@class='bnkName ng-binding']",
					"Bank radio buttons text");
		}
		List<String> accountNoList = multipleElementsTextProvider("//*[@id='addFunds']/ng-include/div[1]/ul//li//div//label//span[@class='accNo ng-binding']",
				"accountNoLabels text");
		for (int i = 0; i < bankNameList.size(); i++) {
			detailList.add("Bank name : " + bankNameList.get(i) + " AccountNo : " + accountNoList.get(i) + "-PASS");
		}

	}

	public void bankSelectionAndPrimaryBank() {
		Reporter.log("===> bankSelectionAndPrimaryBank <====", true);
		detailList.add("@@> Verify bank selection in add funds tab. <@@");
		List<WebElement> bankNameList = multipleElementLocator("", "Bank radio button elemnemt");
		int primaryBankNo = 0;
		for (int i = 0; i < bankNameList.size(); i++) {

		}
	}

	public void checkAmountAfterTwoDecimal() {
		Reporter.log("===> checkAmountAfterTwoDecimal <====", true);
		detailList.add("@@> Verify the amount validation when amount entered after decimal is more than 2 digits. <@@");
		okButton = fluentWaitCodeXpath(driver, "//input[@value='OK']",10, "ok Button");
		if (okButton != null)
			clickElement(okButton, "Ok button");
		amountToTransferTextField = fluentWaitCodeName(driver, "amt", 20, "Amount To Transfer TextField");
		String decimalAmout = "50.2036";
		detailList.add("Enter amount : " + decimalAmout);
		clearAndSendKey(amountToTransferTextField, decimalAmout, "Amount To Transfer TextField");
		detailList.add(ScreenshortProvider.captureScreen(driver, "AmountMoreThenTwoDecimal"));
		if (getValueFromAttribute(amountToTransferTextField, "value", "amount").equalsIgnoreCase(decimalAmout)) {
			detailList.add("Amount textfield  allow to enter more than two digits after decimal-FAIL");
		} else {
			detailList.add("Amount textfield does not allow to enter more than two digits after decimal-PASS");
		}
	}

	public void checkRedirectBankMsg() {
		Reporter.log("===> checkRedirectBankMsg <===", true);
		detailList.add("@@> Verify the redirection for Add funds after entering the amount and clicked on submit. <@@");
		common.backFundTransferPage();
		String bankName=cofigReader.configReaderFM("IBRedirectMsBank");
		String intenetBankMode = common.submitAddFundForm(bankName, "51",false);
		String[] separeArray = help.separater(intenetBankMode, "-");
		if (separeArray[0].equalsIgnoreCase("No")) {
			String transferMode = common.checkingTransferMode();
			/* if(transferMode.equalsIgnoreCase("Native")) { */
			redirectMsgLabel1 = fluentWaitCodeXpath("//div[@id='bank']//label[@class='redirectInfo']",
					"redirect msg label1");
			redirectMsgLabel2 = fluentWaitCodeXpath(
					"//div[@id='bank']//label[@class='redirectInfo']//following::label[1]", "redirect msg label2");
			if (redirectMsgLabel1 != null) {
				Reporter.log("redirectMsgLabel1 is found", true);
				detailList.add(fetchTextFromElement(redirectMsgLabel1) + "-PASS");
				detailList.add(fetchTextFromElement(redirectMsgLabel2) + "-PASS");
			} else
				detailList.add("It is redirect to " + common.currentUrl() + " page-FAIL");

			detailList.add(ScreenshortProvider.captureScreen(driver, "AfterAddFundRedirectPage"));
			common.backFundTransferPage();
		}
	}

	public void primaryAccountLabelOnBank() {
		Reporter.log("===> primaryAccountLabelOnBank <===", true);
		detailList.add("@@> Verify the Label is displayed for the primary bank account <@@");
		primaryLabel = fluentWaitCodeXpath("//a[@class='primaTooltip']", "Primary Label");
		primaryAcStr = common.romoveBrSpanFromText(fetchTextFromElement(primaryLabel));
		List<String> bankNameAccountNo = multipleElementsTextProvider(
				"//a[@class='primaTooltip']//preceding::label//span", "Bank name and account no");
		if (primaryLabel != null)
		{
			if(primaryLabel.isDisplayed())
			detailList.add(primaryAcStr + "-PASS");
			else
				detailList.add( "Primary ac label not present-FAIL");
		}
		else
			detailList.add("Primary Label is not present-FAIL");
		detailList.add("bank Name : " + bankNameAccountNo);
		detailList.add(ScreenshortProvider.captureScreen(driver, "PrimaryAcLabelOnBank"));
	}

	public void checkBankSelection() {
		int selectBank = 0;
		String selectedBank = " ";
		Reporter.log("===> checkBankSelection <===", true);
		detailList.add("@@> Verify bank selection in add funds tab. <@@");

		List<WebElement> bankRadioButton = multipleElementLocator(
				"//span[@class='bnkName ng-binding']//preceding::input", "bank radio button");
		List<String> bankNameList = multipleElementsTextProvider("//span[@class='bnkName ng-binding']", "bank name");
		Reporter.log("Selected bank name : " + bankNameList.get(3), true);
		bankLabel = fluentWaitCodeXpath("//span[text()='" + bankNameList.get(3) + "']", bankNameList.get(3));
		clickElement(bankLabel, "Thrid bank selected");
		for (int i = 1; i < bankRadioButton.size(); i++) {
			if (bankRadioButton.get(i).isSelected()) {
				Reporter.log("Bank Checked : " + bankNameList.get(i - 1), true);
				selectedBank = selectedBank + bankNameList.get(i - 1);
				selectBank++;
			}

		}
		if (selectBank == 1) {
			detailList.add("Only" + selectedBank + " is selected -PASS");
		} else {
			String[] bankArray = help.separater(selectedBank, "");
			for (String bank : bankArray)
				detailList.add("Selected bank name : " + bank);

			detailList.add("Multiple bank selected -FAIL");
		}
		detailList.add(ScreenshortProvider.captureScreen(driver, "OneBankSelected"));
	}

	public void cancelFundTransferFromBankDetailPage() {
		Reporter.log("===> cancelFundTransferFromBankDetailPage <===", true);
		detailList.add("@@> Verify after clicking on cancel when user is redirected to Add Fund  Page. <@@");
		String bankName=cofigReader.configReaderFM("IBCancelFdBank");
		common.backFundTransferPage();
		common.submitAddFundForm(bankName, "60",false);
		String transferMode = common.checkingTransferMode();
		if (transferMode.equalsIgnoreCase("Native")) {
			if (bankName.equalsIgnoreCase("Kotak Mahindra Bank")) {
				cancelKotakTransactionScreenshot = payment.enterKotakCredential("10003540", "101010","123456",false);
				browserPopup(true);
			}
		}
		detailList.add(cancelKotakTransactionScreenshot);
		staticWait(2000);
		failMsg = fluentWaitCodeXpath("//label[text()='Fund transfer failed']", 50, "Fund transfer failed");

		if (failMsg == null) {
			addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']",
					"add fund tab");
			if (addFundTab != null)
				detailList.add("Now we redirect to fund transfer page-PASS");
			else
				detailList.add("Now we not present on fund transfer page-FAIL");
		} else {
			detailList.add(ScreenshortProvider.captureScreen(driver, "Addfundfailed"));
			detailList.add(fetchTextFromElement(failMsg) + "-PASS");
			retryButton = fluentWaitCodeXpath("//a[text()='Retry']", "Retry button");
			clickElement(retryButton, "Retry button");
			addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']", 50,"add fund tab");
			if (addFundTab != null) {
				detailList.add("Now we redirect to add fund transfer page-PASS");
				detailList.add(ScreenshortProvider.captureScreen(driver, "AddFundTransferPage"));
			} else {
				detailList.add("We not able to redirect to add FundTransfer page-FAIL");
			}

		}
	}

	public void checkRetryInUnsuccessfullyTransaction() {
		List<String> failureTranscationDetailList = new ArrayList<String>();
		Reporter.log("===> checkRetryInUnsuccessfullyTransaction <===", true);
		 bankForAtomic=cofigReader.configReaderFM("IBAtomicBank");
		detailList.add("@@> Verify payin when user selects Failure option through atom PG page. <@@");
		accountNoLabel = fluentWaitCodeXpath("//span[text()='"+bankForAtomic+"']//following::span[1]",bankForAtomic+" accountNo");
		accoutnNoStr = fetchTextFromElement(accountNoLabel);
		String addFundScreeshot = common.submitAddFundForm(bankForAtomic, "55",false);
		String[] separeArray = help.separater(addFundScreeshot, "-");
		Reporter.log(separeArray[separeArray.length - 1], true);
		detailList.add(separeArray[separeArray.length - 1]);
		staticWait(2000);
		String transferStatus = common.checkingTransferMode();
		model.setAmount("55");
		model.setBank(bankForAtomic);
		model.setPaymentMode("INTERNET BANKING");
		model.setReferNo("-1");
		if (transferStatus.equalsIgnoreCase("Atom")) {
			failureTranscationDetailList = payment.atomPayment(accoutnNoStr, model, "See Margin", false);

		}
		addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']",
				"add fund tab");
		for (String failureStr : failureTranscationDetailList) {
			detailList.add(failureStr);
		}
		detailList.add("@@> Verify after unsuccessful transaction when user click on 'Retry' <@@");
		retryButton = fluentWaitCodeXpath("//a[text()='Retry']", "Retry button");
		clickElement(retryButton, "Retry button");
		addFundTab = fluentWaitCodeXpath("//a[text()='TRANSFER STATUS']//preceding::a[text()='Add Funds']", 50,
				"add fund tab");
		if (addFundTab != null) {
			detailList.add("Now we redirect to add fund transfer page-PASS");
			detailList.add(ScreenshortProvider.captureScreen(driver, "AddFundTransferPage"));
		} else {
			detailList.add("We not able to redirect to add FundTransfer page-FAIL");
		}
	}

	public List<String> verifyStatusFundTransferICIC() {
		Reporter.log("===> verifyStatusFundTransferICIC <===", true);
		detailList.add("@@> Verify the status of payin request placed for ICIC <@@");
		accountNoLabel = fluentWaitCodeXpath("//span[text()='ICICI BANK LTD']//following::span[1]","ICIC bank accountNo");
		accoutnNoStr = fetchTextFromElement(accountNoLabel);
		String addFundScreeshot = common.submitAddFundForm("ICICI BANK LTD", "55",false);
		String[] separeArray = help.separater(addFundScreeshot, "-");
		Reporter.log(separeArray[separeArray.length - 1], true);
		detailList.add(separeArray[separeArray.length - 1]);
		detailList.add(separeArray[0]);
		String transferStatus = common.checkingTransferMode();
		model.setAmount("55");
		model.setBank("ICICI BANK LTD");
		model.setPaymentMode("INTERNET BANKING");

		if (transferStatus.equalsIgnoreCase("Atom")) {
			payment.atomPayment(accoutnNoStr, model, "See Margin", true);
		}
		detailList.add(Payment.iciciFundtransferStatus);
		detailList.add(Payment.iciciFundtransferRemark);
		screenshotList = Payment.screenshotList;
		for (String screenshot : screenshotList) {
			detailList.add(screenshot);
			Reporter.log(screenshot, true);
		}
		return detailList;
	}
	
	public void loginInvalidInfo() {
		Reporter.log("===> loginInvalidInfo <===", true);
		common.checkThenBackFundTransfer();
		String errorMsg=null;
		String bankName=cofigReader.configReaderFM("IBLoginInvalidBank");
		detailList.add("@@> Verify by entering invalid details on bank page and clicked on submit. <@@");
		if(bankName.equalsIgnoreCase("AXIS BANK"))
		 errorMsg=payment.enterAxisBank("SHOPTEST332", "d#demo123", false);
		else if(bankName.equalsIgnoreCase("ICICI BANK LTD"))
			errorMsg=payment.loginICICBank("testinfi8", "test$2018", "branchfree",true);
		
		detailList.add("Bank name : "+bankName);
		detailList.add(errorMsg+"-PASS");
		detailList.add(ScreenshortProvider.captureScreen(driver, "AxisBankPageWithError"));
		common.checkThenBackFundTransfer();
	}
	
	public void equitasBank() {
		Reporter.log("===> equitasBank <===",true);
		String bankName="Equitas Bank";
		common.backFundTransferPage();
		accountNoLabel=fluentWaitCodeXpath("//span[text()='"+bankName+"']//following::span[1]",bankName+" accountNo");
		accountNoStr=fetchTextFromElement(accountNoLabel);
		common.submitAddFundForm(bankName, "60",false);
		String transferMode = common.checkingTransferMode();
		if (transferMode.equalsIgnoreCase("Native")) {
			if (bankName.equalsIgnoreCase("Equitas Bank")) {
				equitaScreenshot = payment.enterEquitasCredential();
				browserPopup(true);
			}
		}else if(transferMode.equalsIgnoreCase("Atom")) {
			payment.atomPayment(accountNoStr, model, "See margin", true);
		}else if(transferMode.equalsIgnoreCase("Razor")) {
			payment.razorPayment(accountNoStr, model, "See margin", true);
		}
		detailList.add(cancelKotakTransactionScreenshot);
	}

	public void amountEnterGreaterThenAccountBalance() {
		Reporter.log("===> amountEnterGreaterThenAccountBalance <===", true);
		detailList.add("@@> Verify payin request when amount entered is greater than the amount available in bank balance. <@@");
		common.backFundTransferPage();
		amountLabel=fluentWaitCodeXpath("//label[@class='amtBold ng-binding ng-scope']",100,"Add fund amount label");
		 addFundAmountStr=fetchTextFromElement(amountLabel).replace("Rs", "").trim(); 
		 
		String bankName=cofigReader.configReaderFM("EnterAmountGreaterThenBalance");
		String intenetBankMode = common.submitAddFundForm(bankName,addFundAmountStr,true);
		String urlStr=common.currentUrl();
		if(urlStr.contains("razorcallback")) {
			WebElement errorMsg=fluentWaitCodeXpath("//p[@jsselect='summary']", "ewaut error msg label");
			detailList.add(help.removeHtmlReporter(fetchTextFromElement(errorMsg))+"-FAIL");
		}
		detailList.add("Account balance : "+addFundAmountStr);
		if(convertNumber(FundTransferCommon.maxAmountEnter, addFundAmountStr))
			detailList.add("Enter amount : "+FundTransferCommon.maxAmountEnter+"-PASS");
		else
			detailList.add("Enter amount : "+FundTransferCommon.maxAmountEnter+"-FAIL");
		if(!FundTransferCommon.amountTextfieldErrorMsg.equalsIgnoreCase("No"))
		detailList.add(FundTransferCommon.amountTextfieldErrorMsg);
		detailList.add(FundTransferCommon.addFundforScreenshot);
		
	}
	public void internetBankingExecute(FundTransferReport report,LatestLoginModel loginModel) {
		cofigReader.fundTransferConfig();
		try {
		Reporter.log("<b>======@@>internetBankingExecute <@@=====</b>", true);
		fundTransferTab = fluentWaitCodeXpath(driver, "//a[text()='Fund Transfer']", "fundTransferTab");
		clickElement(fundTransferTab, "FundTransferTab");

		
		  threeTabPresentOrNot(); primaryAccountLabelOnBank();
		  verifyFundTransferTabs(); bankNameAccountNoDisplay();
		  checkAmountAfterTwoDecimal();
		 
		  //dont citi bank not present
		// checkRedirectBankMsg();
		  
			//Kotak login not work 
		 //cancelFundTransferFromBankDetailPage();
		 checkRetryInUnsuccessfullyTransaction();
			 
		 
		 //don't
		 //loginInvalidInfo(); 
		 //don't
		//loginBankAccoutCloseDriver.loginBankAcountCloseDriver(detailList, loginModel);
			//for this required icicNative it has encomplete test data  
		 //verifyStatusFundTransferICIC(); 
			 
		//amountEnterGreaterThenAccountBalance();
		report.internetBanking(detailList);
		}catch(NullPointerException e) {
			report.fundTransferFailReport("InternetBanking", driver,detailList);
		}
	}

	

}
