package com.shreeya.fundtransferpages;

import java.util.List;

import com.shreeya.util.ExtendReporter;

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
	
	public void fundTransferLogFlush() {
		logFlush();
	}
}
