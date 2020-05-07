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

	

	public WatchListModel(String referNo, String watchListName, String scriptName, String exchange,
			String dafaultWatchList, String keyword, String verifyScript, String testCaseName) {
		super();
		this.referNo = referNo;
		this.watchListName = watchListName;
		this.scriptName = scriptName;
		this.exchange = exchange;
		this.dafaultWatchList = dafaultWatchList;
		this.keyword = keyword;
		this.verifyScript = verifyScript;
		this.testCaseName = testCaseName;
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

	@Override
	public String toString() {
		return "WatchListModel [referNo=" + referNo + ", watchListName=" + watchListName + ", scriptName=" + scriptName
				+ ", exchange=" + exchange + ", dafaultWatchList=" + dafaultWatchList + ", keyword=" + keyword
				+ ", verifyScript=" + verifyScript + ", testCaseName=" + testCaseName + "]";
	}

	
	
	

	
	
	
}
