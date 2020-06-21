package com.shreeya;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.shreeya.fundtransferpages.FundTransferBankExecution;
import com.shreeya.model.LoginModel;
import com.shreeya.mypositionspages.MyPositionsExecution;
import com.shreeya.seemarginpages.SeeMarginExecution;

public class KeywardDriven {
	
	@BeforeMethod
	public void beforeExecution() {
		Reporter.log("Before execution ",true);
	}
	
	

	//@Parameters({"Reference","UserId","Pwd","Yob","StartNo","EndNo","Module"})
	@Test(enabled = true)
	public void fundTransfterKeyword() throws InterruptedException, IOException {
		LoginModel loginModel=new LoginModel("1","sanjay15","abc123","1985","1","2","fundtransfer");
//		FundTransferExecution fundTransfer=new FundTransferExecution();
//		fundTransfer.fundTransferExecute(loginModel);
	}
	
	//@Parameters({"Reference","UserId","Pwd","Yob","StartNo","EndNo","Module"})
	@Test(enabled = true)
	public void seeMarginKeyword() throws InterruptedException, IOException {
		LoginModel loginModel=new LoginModel("2","60003800","abc123","2000","1","2","seeMargin");
		/*
		 * SeeMarginExecution seeMargin=new SeeMarginExecution();
		 * seeMargin.seeMarginExecute(loginModel);
		 */
	}
	
	
	@Test(enabled = false)
	public void myPositionsKeyword() throws InterruptedException, IOException {
		LoginModel loginModel=new LoginModel("1","60003800","abc123","2000","1","2","fundtransfer");
		/*
		 * MyPositionsExecution myPositions=new MyPositionsExecution();
		 * myPositions.myPositionsExecute(loginModel);
		 */
	}
	
	@AfterMethod
	public void afterExecution() {
		Reporter.log("After execution ",true);
	}
}
