package com.shreeya.fundtransferpages;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.shreeya.MyTestLauncher;
import com.shreeya.util.SeleniumCoder;

public class FundTransferExecution extends SeleniumCoder{
	WebDriver driver;
	List<String> keywordList;
	FundTransferBankExecution fundTransferBankExecution;
	FundTransferReport report;
	FundTransferAmount FundTransferAmount;
	FundTransferUPITextfield fundTransferUPITextfield;
	FundTransferUPI_Id fundTransferUPI_Id;
	public FundTransferExecution(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		fundTransferBankExecution=new FundTransferBankExecution(driver);
		FundTransferAmount=new FundTransferAmount(driver);
		report = new FundTransferReport(MyTestLauncher.reportFolderPath[1], "FundTransfer", 0);
		fundTransferUPITextfield=new FundTransferUPITextfield(driver);
		fundTransferUPI_Id=new FundTransferUPI_Id(driver);
	}
	public void fundTransferExecute() {
		FundTransferKeyword keyword=new FundTransferKeyword();
		keywordList=keyword.keywordProvider();
		for(String step:keywordList) {
			switch(step) {
			case "FundTransfer":
				fundTransferBankExecution.fundTransferExecute(report);
				break;
			case "AmountTextfield":
				FundTransferAmount.amountTextfieldExecute(report);
				break;
			case "UPITextfield":
				fundTransferUPITextfield.UPITextfieldExecution(report);
				break;
			case "UPI_Id":
				fundTransferUPI_Id.upi_idExecution(report);
				break;
				
			}
		}
		report.fundTransferLogFlush();
	}
}
