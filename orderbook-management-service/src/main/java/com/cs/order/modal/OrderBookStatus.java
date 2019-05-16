package com.cs.order.modal;

import com.cs.constants.OrderBookStatusType;

public class OrderBookStatus {
	
	private int instrumentId;
	private OrderBookStatusType orderBookStatusType;
	
	public int getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}
	public OrderBookStatusType getOrderBookStatusType() {
		return orderBookStatusType;
	}
	public void setOrderBookStatusType(OrderBookStatusType orderBookStatusType) {
		this.orderBookStatusType = orderBookStatusType;
	}
	@Override
	public String toString() {
		return "OrderBookStatus [instrumentId=" + instrumentId + ", orderBookStatusType=" + orderBookStatusType + "]";
	}
	
	

}
