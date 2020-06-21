package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class ExecutionModel {

	@CsvBindByName(column = "ReferNo")
	private String referNo;
	@CsvBindByName(column = "UserIdEQ")
	private String  userIdEQ;
	@CsvBindByName(column = "PasswordEQ")
	private String passwordEQ;
	@CsvBindByName(column = "YobEQ")
	private String yobEQ;
	@CsvBindByName(column = "UserIdCO")
	private String userIdCO;
	@CsvBindByName(column = "PasswordCO")
	private String  passwordCO;
	@CsvBindByName(column = "YobCO")
	private String yobCO;
	@CsvBindByName(column = "UserIdMI")
	private String userIdMi;
	@CsvBindByName(column = "PasswordMI")
	private String passwordMI;
	@CsvBindByName(column = "YobMI")
	private String yobMI;
	@CsvBindByName(column = "Module")
	private String module;
	
	public ExecutionModel() {}
	
	
	public ExecutionModel(String referNo, String userIdEQ, String passwordEQ, String yobEQ, String userIdCO,
			String passwordCO, String yobCO, String userIdMi, String passwordMI, String yobMI, String module) {
		super();
		this.referNo = referNo;
		this.userIdEQ = userIdEQ;
		this.passwordEQ = passwordEQ;
		this.yobEQ = yobEQ;
		this.userIdCO = userIdCO;
		this.passwordCO = passwordCO;
		this.yobCO = yobCO;
		this.userIdMi = userIdMi;
		this.passwordMI = passwordMI;
		this.yobMI = yobMI;
		this.module = module;
	}
	public String getReferNo() {
		return referNo;
	}
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getUserIdEQ() {
		return userIdEQ;
	}
	public void setUserIdEQ(String userIdEQ) {
		this.userIdEQ = userIdEQ;
	}
	public String getPasswordEQ() {
		return passwordEQ;
	}
	public void setPasswordEQ(String passwordEQ) {
		this.passwordEQ = passwordEQ;
	}
	public String getYobEQ() {
		return yobEQ;
	}
	public void setYobEQ(String yobEQ) {
		this.yobEQ = yobEQ;
	}
	public String getUserIdCO() {
		return userIdCO;
	}
	public void setUserIdCO(String userIdCO) {
		this.userIdCO = userIdCO;
	}
	public String getPasswordCO() {
		return passwordCO;
	}
	public void setPasswordCO(String passwordCO) {
		this.passwordCO = passwordCO;
	}
	public String getYobCO() {
		return yobCO;
	}
	public void setYobCO(String yobCO) {
		this.yobCO = yobCO;
	}
	public String getUserIdMi() {
		return userIdMi;
	}
	public void setUserIdMi(String userIdMi) {
		this.userIdMi = userIdMi;
	}
	public String getPasswordMI() {
		return passwordMI;
	}
	public void setPasswordMI(String passwordMI) {
		this.passwordMI = passwordMI;
	}
	public String getYobMI() {
		return yobMI;
	}
	public void setYobMI(String yobMI) {
		this.yobMI = yobMI;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Override
	public String toString() {
		return "ExecutionModel [referNo=" + referNo + ", userIdEQ=" + userIdEQ + ", passwordEQ=" + passwordEQ
				+ ", yobEQ=" + yobEQ + ", userIdCO=" + userIdCO + ", passwordCO=" + passwordCO + ", yobCO=" + yobCO
				+ ", userIdMi=" + userIdMi + ", passwordMI=" + passwordMI + ", yobMI=" + yobMI + ", module=" + module
				+ "]";
	}
	
	

}
