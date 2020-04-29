package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class LoginTestModel {

	@CsvBindByName(column = "Reference_no")
	private String reference_no;
	
	@CsvBindByName(column = "TestScenario")
	private String testScenario;
	
	@CsvBindByName(column = "User_Id")
	private String user_Id;
	
	@CsvBindByName(column = "Password")
	private String password;
	
	@CsvBindByName(column = "Yob")
	private String yob;
	
	@CsvBindByName(column = "Expected_Result")
	private String expected_Result;

	public String getReference_no() {
		return reference_no;
	}

	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
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

	public String getExpected_Result() {
		return expected_Result;
	}

	public void setExpected_Result(String expected_Result) {
		this.expected_Result = expected_Result;
	}
	
	

	public String getTestScenario() {
		return testScenario;
	}

	public void setTestScenario(String testScenario) {
		this.testScenario = testScenario;
	}

	@Override
	public String toString() {
		return "LoginTestModel [reference_no=" + reference_no + ", testScenario=" + testScenario + ", user_Id="
				+ user_Id + ", password=" + password + ", yob=" + yob + ", expected_Result=" + expected_Result + "]";
	}

	
	
	
}
