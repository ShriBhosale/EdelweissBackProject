package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class FundTransferModel {

	@CsvBindByName(column = "ReferNo")
	private String referNo;
	@CsvBindByName(column = "Bank")
	private String bank;
	@CsvBindByName(column = "PaymentMode")
	private String paymentMode;
	@CsvBindByName(column = "Amount")
	private String amount;
	public FundTransferModel(String referNo, String bank, String paymentMode, String amount) {
		super();
		this.referNo = referNo;
		this.bank = bank;
		this.paymentMode = paymentMode;
		this.amount = amount;
	}
	public FundTransferModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getReferNo() {
		return referNo;
	}
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "FundTransferModel [referNo=" + referNo + ", bank=" + bank + ", paymentMode=" + paymentMode
				+ ", amount=" + amount + "]";
	}
	
	
}
