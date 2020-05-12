package com.shreeya.model;

public class LatestLoginModel {
	
	private int referNo;
	private String UserId;
	private String password;
	private String yob;
	private String module;
	private String segment;
	public LatestLoginModel(int referNo, String userId, String password, String yob, String module,String segment) {
		super();
		this.referNo = referNo;
		UserId = userId;
		this.password = password;
		this.yob = yob;
		this.module = module;
		this.segment=segment;
	}
	
	public LatestLoginModel() {}

	public int getReferNo() {
		return referNo;
	}

	public void setReferNo(int referNo) {
		this.referNo = referNo;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	@Override
	public String toString() {
		return "LatestLoginModel [referNo=" + referNo + ", UserId=" + UserId + ", password=" + password + ", yob=" + yob
				+ ", module=" + module + ", segment=" + segment + "]";
	}

	
	
	
}
