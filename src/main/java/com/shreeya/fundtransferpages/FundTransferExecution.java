package com.shreeya.fundtransferpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.shreeya.MyTestLauncher;
import com.shreeya.model.LatestLoginModel;
import com.shreeya.util.SeleniumCoder;

public class FundTransferExecution extends SeleniumCoder{
	WebDriver driver;
	List<String> keywordList;
	FundTransferBankExecution fundTransferBankExecution;
	FundTransferReport report;
	FundTransferAmount FundTransferAmount;
	FundTransferUPITextfield fundTransferUPITextfield;
	FundTransferUPI_Id fundTransferUPI_Id;
	FundTransferInternetBanking internetBanking;
	FundTransferProfile profile;
	public FundTransferExecution(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		fundTransferBankExecution=new FundTransferBankExecution(driver);
		FundTransferAmount=new FundTransferAmount(driver);
		report = new FundTransferReport(MyTestLauncher.reportFolderPath[1], "FundTransfer", 0);
		fundTransferUPITextfield=new FundTransferUPITextfield(driver);
		fundTransferUPI_Id=new FundTransferUPI_Id(driver);
		internetBanking=new FundTransferInternetBanking(driver);
		profile=new FundTransferProfile(driver);
	}
	public void fundTransferExecute(LatestLoginModel model) {
		Reporter.log("<b><font color='Yellow'>=========@@@@ FundTransfer_"+model.getReferNo()+" @@@@========</font></b>", true);
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
			case "InternetBanking":
				internetBanking.internetBankingExecute(report,model);
				break;
			case "Profile":
				profile.profileExecution(report);
				break;
			}
		}
		report.fundTransferLogFlush();
	}
}
