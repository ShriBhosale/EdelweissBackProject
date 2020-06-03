package com.shreeya.fundtransferpages;

import java.util.List;

import com.shreeya.model.FundTransferModel;
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
	

	public void upi_idReport(List<String> detailList) {
		testCreation("UPI_Id");
		print(detailList);	
	}
	

	public void fundTransferLogFlush() {
		logFlush();
	}

	public void fundTransferReport(List<String> detailList,FundTransferModel model) {
		testCreation(model.getBank()+"_"+model.getReferNo());
		print(detailList);
		
	}
}
