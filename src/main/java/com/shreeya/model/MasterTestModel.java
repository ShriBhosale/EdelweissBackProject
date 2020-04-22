package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class MasterTestModel {

	@CsvBindByName(column = "ReferencesNo")
	private String referNo;
	
	@CsvBindByName(column = "Keyword")
	private String keyword;
	
	@CsvBindByName(column = "Steps")
	private String steps;

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "MasterTestModel [referNo=" + referNo + ", keyword=" + keyword + ", steps=" + steps + "]";
	}
	
	
	
}
