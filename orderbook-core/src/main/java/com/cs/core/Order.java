package com.cs.core;

import java.util.Date;

public abstract class Order {
	
	private Integer orderId;
	private Integer quantity;
	private Date entryDate;
	private Integer instrumentId;
	private Double price;
	private Boolean validOrder;
	
	public Order(Integer orderId, Integer quantity, Date entryDate, Integer instrumentId, Double price) {
		super();
		this.orderId = orderId;
		this.quantity = quantity;
		this.entryDate = entryDate;
		this.instrumentId = instrumentId;
		this.price = price;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Integer getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Boolean getValidOrder() {
		return validOrder;
	}
	public void setValidOrder(Boolean validOrder) {
		this.validOrder = validOrder;
	}
	
	

}
