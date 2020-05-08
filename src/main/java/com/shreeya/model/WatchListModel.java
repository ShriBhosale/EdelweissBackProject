package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class WatchListModel {

	@CsvBindByName(column = "ReferNo")
	private String referNo;
	
	@CsvBindByName(column = "WatchListName")
	private String watchListName;
	
	@CsvBindByName(column = "ScriptName")
	private String scriptName;
	
	@CsvBindByName(column = "Exchange")
	private String exchange;
	
	@CsvBindByName(column = "DafaultWatchList")
	private String dafaultWatchList;

	@CsvBindByName(column = "Keyword")
	private String keyword;
	
	@CsvBindByName(column = "VerifyScript")
	private String verifyScript;

	@CsvBindByName(column = "TestCaseName")
	private String testCaseName;

	@CsvBindByName(column = "OrderType")
	private String orderType;

	@CsvBindByName(column = "OrderPrice")
	private String orderPrice;
	
	@CsvBindByName(column = "ProductType")
	private String productType;

	@CsvBindByName(column = "Qty")
	private String qty;

	@CsvBindByName(column = "DeleteScript")
	private String deleteScript;

	

	public WatchListModel(String referNo, String watchListName, String scriptName, String exchange,
			String dafaultWatchList, String keyword, String verifyScript, String testCaseName, String orderType,
			String orderPrice, String productType, String qty, String deleteScript) {
		super();
		this.referNo = referNo;
		this.watchListName = watchListName;
		this.scriptName = scriptName;
		this.exchange = exchange;
		this.dafaultWatchList = dafaultWatchList;
		this.keyword = keyword;
		this.verifyScript = verifyScript;
		this.testCaseName = testCaseName;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.productType = productType;
		this.qty = qty;
		this.deleteScript = deleteScript;
	}

	public WatchListModel() {}

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}

	public String getWatchListName() {
		return watchListName;
	}

	public void setWatchListName(String watchListName) {
		this.watchListName = watchListName;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getDafaultWatchList() {
		return dafaultWatchList;
	}

	public void setDafaultWatchList(String dafaultWatchList) {
		this.dafaultWatchList = dafaultWatchList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getVerifyScript() {
		return verifyScript;
	}

	public void setVerifyScript(String verifyScript) {
		this.verifyScript = verifyScript;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
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

	public String getDeleteScript() {
		return deleteScript;
	}

	public void setDeleteScript(String deleteScript) {
		this.deleteScript = deleteScript;
	}

	@Override
	public String toString() {
		return "WatchListModel [referNo=" + referNo + ", watchListName=" + watchListName + ", scriptName=" + scriptName
				+ ", exchange=" + exchange + ", dafaultWatchList=" + dafaultWatchList + ", keyword=" + keyword
				+ ", verifyScript=" + verifyScript + ", testCaseName=" + testCaseName + ", orderType=" + orderType
				+ ", orderPrice=" + orderPrice + ", productType=" + productType + ", qty=" + qty + ", deleteScript="
				+ deleteScript + "]";
	}

	

	
}
