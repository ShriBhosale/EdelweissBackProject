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
	@CsvBindByName(column="ExpireDate")
	private String expireDate;
	@CsvBindByName(column="CardSecurityCode")
	private String cardSecurityCode;
	@CsvBindByName(column="OTP")
	private String otp;
	@CsvBindByName(column = "OrderType")
	private String orderType;
	@CsvBindByName(column = "OrderPrice")
	private String orderPrice;
	@CsvBindByName(column = "ProductType")
	private String productType;
	@CsvBindByName(column = "Qty")
	private String qty;
	@CsvBindByName(column = "Exchange")
	private String exchange;
		
	
	
	
	public FundTransferModel(String referNo, String bank, String paymentMode, String amount, String userName,
			String password, String remarks, String debitCardNo, String accountType, String expireDate,
			String cardSecurityCode, String otp, String orderType, String orderPrice, String productType, String qty,
			String exchange) {
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
		this.expireDate = expireDate;
		this.cardSecurityCode = cardSecurityCode;
		this.otp = otp;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.productType = productType;
		this.qty = qty;
		this.exchange = exchange;
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
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getCardSecurityCode() {
		return cardSecurityCode;
	}
	public void setCardSecurityCode(String cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	@Override
	public String toString() {
		return "FundTransferModel [referNo=" + referNo + ", bank=" + bank + ", paymentMode=" + paymentMode + ", amount="
				+ amount + ", userName=" + userName + ", password=" + password + ", remarks=" + remarks
				+ ", debitCardNo=" + debitCardNo + ", accountType=" + accountType + ", expireDate=" + expireDate
				+ ", cardSecurityCode=" + cardSecurityCode + ", otp=" + otp + ", orderType=" + orderType
				+ ", orderPrice=" + orderPrice + ", productType=" + productType + ", qty=" + qty + ", exchange="
				+ exchange + "]";
	}
	
	
	
}
