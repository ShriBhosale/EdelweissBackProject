package com.shreeya.fundtransferpages;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.shreeya.MyTestLauncher;
import com.shreeya.util.SeleniumCoder;

public class FundTransferExecution extends SeleniumCoder{
	WebDriver driver;
	List<String> keywordList;
	FundTransferBankExecution fundTransferBankExecution;
	FundTransferReport fundTransferReport;
	FundTransferAmount FundTransferAmount;
	FundTransferUPITextfield fundTransferUPITextfield;
	public FundTransferExecution(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		fundTransferBankExecution=new FundTransferBankExecution(driver);
		FundTransferAmount=new FundTransferAmount(driver);
		fundTransferReport = new FundTransferReport(MyTestLauncher.reportFolderPath[1], "FundTransfer", 0);
		fundTransferUPITextfield=new FundTransferUPITextfield(driver);
	}
	public void fundTransferExecute() {
		FundTransferKeyword keyword=new FundTransferKeyword();
		keywordList=keyword.keywordProvider();
		for(String step:keywordList) {
			switch(step) {
			case "FundTransfer":
				fundTransferBankExecution.fundTransferExecute();
				break;
			case "AmountTextfield":
				FundTransferAmount.amountTextfieldExecute(fundTransferReport);
				break;
			case "UPITextfield":
				fundTransferUPITextfield.UPITextfieldExecution(fundTransferReport);
				break;
			case "UPI_Id":
				break;
				
			}
		}
		fundTransferReport.fundTransferLogFlush();
	}
}
