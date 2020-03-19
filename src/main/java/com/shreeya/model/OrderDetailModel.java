package com.shreeya.model;



public class OrderDetailModel {

	
	private String buyAndSell;
	private String tradingSymbol;
	private String productType;
	private String status;
	private String orderPrice;
	private String orderType;
	private String exchange;
	private String validity;
	public String getbuyAndSell() {
		return buyAndSell;
	}
	public void setbuyAndSell(String buyAndSell) {
		this.buyAndSell = buyAndSell;
	}
	public String getTradingSymbol() {
		return tradingSymbol;
	}
	public void setTradingSymbol(String tradingSymbol) {
		this.tradingSymbol = tradingSymbol;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	@Override
	public String toString() {
		return "OrderDetailModel [buyAndSell=" + buyAndSell + ", tradingSymbol=" + tradingSymbol
				+ ", productType=" + productType + ", status=" + status + ", orderPrice=" + orderPrice + ", orderType="
				+ orderType + ", exchange=" + exchange + ", validity=" + validity + "]";
	}
	
	
}
