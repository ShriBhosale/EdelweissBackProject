package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class OrderPlaceModel {


	private String referNo;
	private String scriptName;
	private String exchange;
	private String orderType;
	private String orderPrice;
	private String productType;
	private String qty;
	
	public OrderPlaceModel(String referNo, String scriptName, String exchange, String orderType, String orderPrice,
			String productType, String qty) {
		super();
		this.referNo = referNo;
		this.scriptName = scriptName;
		this.exchange = exchange;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.productType = productType;
		this.qty = qty;
	}

	public String getReferNo() {
		return referNo;
	}

	public void setReferNo(String referNo) {
		this.referNo = referNo;
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

	@Override
	public String toString() {
		return "OrderPlaceModel [referNo=" + referNo + ", scriptName=" + scriptName + ", exchange=" + exchange
				+ ", orderType=" + orderType + ", orderPrice=" + orderPrice + ", productType=" + productType + ", qty="
				+ qty + "]";
	}
	
	
	
	
	
	
}
