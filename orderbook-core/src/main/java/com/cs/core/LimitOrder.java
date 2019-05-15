package com.cs.core;

import java.util.Date;

import com.cs.constants.OrderType;

public class LimitOrder extends Order{
	
	private OrderType orderType;

	public LimitOrder(Integer orderId, Integer quantity, Date entryDate, Integer instrumentId, Double price) {
		super(orderId, quantity, entryDate, instrumentId, price);
		this.orderType=OrderType.LIMIT;
	}

	@Override
	public String toString() {
		return "LimitOrder [orderType=" + orderType + ", getOrderId()=" + getOrderId() + ", getQuantity()="
				+ getQuantity() + ", getEntryDate()=" + getEntryDate() + ", getInstrumentId()=" + getInstrumentId()
				+ ", getPrice()=" + getPrice() + ", getValidOrder()=" + getValidOrder() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
}
