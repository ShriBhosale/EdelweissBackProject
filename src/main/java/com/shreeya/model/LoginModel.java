package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class LoginModel {
	@CsvBindByName(column = "ReferNo")
	private String referNo;
	@CsvBindByName(column = "UserId")
	private String userId;
	@CsvBindByName(column = "Password")
	private String password;
	@CsvBindByName(column = "yob")
	private String yob;
	@CsvBindByName(column = "StartingRowNo")
	private String startingRowNo;
	@CsvBindByName(column = "EndRowNo")
	private String endRowNo;
	@CsvBindByName(column = "ExecutionType")
	private String executionType;
	public String getReferNo() {
		return referNo;
	}
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getYob() {
		return yob;
	}
	public void setYob(String yob) {
		this.yob = yob;
	}
	public String getStartingRowNo() {
		return startingRowNo;
	}
	public void setStartingRowNo(String startingRowNo) {
		this.startingRowNo = startingRowNo;
	}
	public String getEndRowNo() {
		return endRowNo;
	}
	public void setEndRowNo(String endRowNo) {
		this.endRowNo = endRowNo;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	@Override
	public String toString() {
		return "LoginModel [referNo=" + referNo + ", userId=" + userId + ", password=" + password + ", yob=" + yob
				+ ", startingRowNo=" + startingRowNo + ", endRowNo=" + endRowNo + ", executionType=" + executionType
				+ "]";
	}
	
	
	
}
