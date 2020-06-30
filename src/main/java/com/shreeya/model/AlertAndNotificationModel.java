package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class AlertAndNotificationModel {
	
	@CsvBindByName(column = "referNo")
	private String referNo;
	
	@CsvBindByName(column = "StockName")
	private String stockName;
	
	@CsvBindByName(column = "ValueIs")
	private String valueIs;
	
	@CsvBindByName(column = "Value")
	private String value;
	
	@CsvBindByName(column = "Segment")
	private String segment;
	
	@CsvBindByName(column = "ModifyValule")
	private String modifyValule;
	
	@CsvBindByName(column = "ModifyValueIs")
	private String modifyValueIs;
	
	@CsvBindByName(column = "TickSize")
	private String tickSize;
	
	@CsvBindByName(column = "ProductType")
	private String productType;
	
	
	@CsvBindByName(column = "Qty")
	private String qty;
	
	

	

	public AlertAndNotificationModel(String referNo, String stockName, String valueIs, String value, String segment,
			String modifyValule, String modifyValueIs, String tickSize, String productType, String qty) {
		super();
		this.referNo = referNo;
		this.stockName = stockName;
		this.valueIs = valueIs;
		this.value = value;
		this.segment = segment;
		this.modifyValule = modifyValule;
		this.modifyValueIs = modifyValueIs;
		this.tickSize = tickSize;
		this.productType = productType;
		this.qty = qty;
	}

	public AlertAndNotificationModel() {}

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getValueIs() {
		return valueIs;
	}

	public void setValueIs(String valueIs) {
		this.valueIs = valueIs;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}
	
	

	public String getModifyValule() {
		return modifyValule;
	}

	public void setModifyValule(String modifyValule) {
		this.modifyValule = modifyValule;
	}

	public String getTickSize() {
		return tickSize;
	}

	public void setTickSize(String tickSize) {
		this.tickSize = tickSize;
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

	public String getModifyValueIs() {
		return modifyValueIs;
	}

	public void setModifyValueIs(String modifyValueIs) {
		this.modifyValueIs = modifyValueIs;
	}

	@Override
	public String toString() {
		return "AlertAndNotificationModel [referNo=" + referNo + ", stockName=" + stockName + ", valueIs=" + valueIs
				+ ", value=" + value + ", segment=" + segment + ", modifyValule=" + modifyValule + ", modifyValueIs="
				+ modifyValueIs + ", tickSize=" + tickSize + ", productType=" + productType + ", qty=" + qty + "]";
	}

	

}
