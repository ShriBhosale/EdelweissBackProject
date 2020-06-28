package com.shreeya.fundtransferpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.model.FundTransferModel;
import com.shreeya.util.ExtendReporter;
import com.shreeya.util.ScreenshortProvider;
import com.shreeya.util.SeleniumCoder;

public class FundTransferReport extends ExtendReporter {


	public FundTransferReport(String folderPathString,String scenario,int orderNo) {
		super(folderPathString, scenario, orderNo);
		
	}
	
	public void amountTextfieldReport(List<String> detailList) {
		testCreation("AmountTextfield");
		print(detailList);
	}
	
	public void upiTextfieldReport(List<String> detailList) {
		testCreation("UPITextfield");
		print(detailList);
	}
	

	public void upi_idReport(List<String> detailList) {
		testCreation("UPI_Id");
		print(detailList);	
	}
	
	public void internetBanking(List<String> detailList) {
		testCreation("InternetBanking");
		print(detailList);
	}
	

	public void fundTransferLogFlush() {
		Reporter.log("=== fundTransferLogFlush ===", true);
		logFlush();
	}

	public void fundTransferReport(List<String> detailList,FundTransferModel model,String placeOrderSeeMargin) {
		Reporter.log("=====> fundTransferReport <====", true);
		testCreation(model.getBank()+"_"+placeOrderSeeMargin+"_"+model.getReferNo());
		print(detailList);
		
	}
	
	public void fundTransferFailReport(String keywordName,WebDriver driver,List<String> detailList) {
		testCreation(keywordName+"Fail");
		if(detailList.size()!=0) {
			print(detailList);
		}	
		errorFail("<b>===Abnormal termination===</b>");
		errorFail("Not found or not intractable "+SeleniumCoder.elementNameError);
		printLog(ScreenshortProvider.captureScreen(driver, "Abnormaltermination"));
		
	}

	public void profileReport(List<String> detailList) {
		Reporter.log("===> profileReport <===", true);
		testCreation("Profile");
		print(detailList);
	}
}
