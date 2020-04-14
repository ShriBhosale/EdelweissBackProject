package com.shreeya.amazone;

import com.opencsv.bean.CsvBindByName;

public class AmazoneModel {

	@CsvBindByName(column = "Refrences")
	private String refrences;
	
	@CsvBindByName(column = "SearchItem")
	private String searchItem;

	public String getRefrences() {
		return refrences;
	}

	public void setRefrences(String refrences) {
		this.refrences = refrences;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	@Override
	public String toString() {
		return "AmazoneModel [refrences=" + refrences + ", searchItem=" + searchItem + "]";
	}
	
	
	
}
