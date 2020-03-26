package com.shreeya.model;

import com.opencsv.bean.CsvBindByName;

public class TestDataModel {
	@CsvBindByName(column = "OrderNo")
	String orderNo;
	@CsvBindByName(column = "UserId")
	String userId;
	@CsvBindByName(column = "password")
	String password;
	@CsvBindByName(column = "YOB")
	String yob;
	@CsvBindByName(column = "Action")
	String action;
	@CsvBindByName(column = "OrderType")
	String orderType;
	@CsvBindByName(column = "Script")
	String script;
	@CsvBindByName(column = "Segment")
	String segment;
	@CsvBindByName(column = "OrderPrice")
	String orderPrice;
	@CsvBindByName(column = "ProductType")
	String productType;
	@CsvBindByName(column = "Qty")
	String qty;
	@CsvBindByName(column = "OrderValidity")
	String orderValidity;
	@CsvBindByName(column = "Scenario")
	String scenario;
	@CsvBindByName(column = "OrderPriceMod")
	String orderPriceMod;
	@CsvBindByName(column = "QtyMod")
	String qtyMod;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
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

	public String getOrderValidity() {
		return orderValidity;
	}

	public void setOrderValidity(String orderValidity) {
		this.orderValidity = orderValidity;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getOrderPriceMod() {
		return orderPriceMod;
	}

	public void setOrderPriceMod(String orderPriceMod) {
		this.orderPriceMod = orderPriceMod;
	}

	public String getQtyMod() {
		return qtyMod;
	}

	public void setQtyMod(String qtyMod) {
		this.qtyMod = qtyMod;
	}

	@Override
	public String toString() {
		return "TestDataModel [orderNo=" + orderNo + ", userId=" + userId + ", password=" + password + ", yob=" + yob
				+ ", action=" + action + ", orderType=" + orderType + ", script=" + script + ", segment=" + segment
				+ ", orderPrice=" + orderPrice + ", productType=" + productType + ", qty=" + qty + ", orderValidity="
				+ orderValidity + ", scenario=" + scenario + ", orderPriceMod=" + orderPriceMod + ", qtyMod=" + qtyMod
				+ "]";
	}

}
