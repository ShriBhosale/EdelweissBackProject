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
	@CsvBindByName(column="UserName")
	private String userName;
	@CsvBindByName(column="Password")
	private String password;
	@CsvBindByName(column="Remarks")
	private String remarks;
	@CsvBindByName(column="DebitCardNo")
	private String debitCardNo;
	@CsvBindByName(column="AccountType")
	private String accountType;
	
	@CsvBindByName(column="TranscationMode")
	private String transcationMode;
	
	@CsvBindByName(column="Negative")
	private String negative;
	
	
	public FundTransferModel(String referNo, String bank, String paymentMode, String amount, String userName,
			String password, String remarks, String debitCardNo, String accountType, String transcationMode,
			String negative) {
		super();
		this.referNo = referNo;
		this.bank = bank;
		this.paymentMode = paymentMode;
		this.amount = amount;
		this.userName = userName;
		this.password = password;
		this.remarks = remarks;
		this.debitCardNo = debitCardNo;
		this.accountType = accountType;
		this.transcationMode = transcationMode;
		this.negative = negative;
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
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDebitCardNo() {
		return debitCardNo;
	}
	public void setDebitCardNo(String debitCardNo) {
		this.debitCardNo = debitCardNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getTranscationMode() {
		return transcationMode;
	}
	public void setTranscationMode(String transcationMode) {
		this.transcationMode = transcationMode;
	}
	public String getNegative() {
		return negative;
	}
	public void setNegative(String negative) {
		this.negative = negative;
	}
	@Override
	public String toString() {
		return "FundTransferModel [referNo=" + referNo + ", bank=" + bank + ", paymentMode=" + paymentMode + ", amount="
				+ amount + ", userName=" + userName + ", password=" + password + ", remarks=" + remarks
				+ ", debitCardNo=" + debitCardNo + ", accountType=" + accountType + ", transcationMode="
				+ transcationMode + ", negative=" + negative + "]";
	}
	
	
}
