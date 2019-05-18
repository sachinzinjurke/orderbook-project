package com.cs.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cs.constants.OrderBookStatusType;

public class OrderBook {

	private List<Order> orders=new ArrayList<Order>();
	private OrderBookStatusType orderBookStatusType=OrderBookStatusType.OPEN;
	private Execution execution;
	private Integer executionCount=0;
	
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public OrderBookStatusType getOrderBookStatusType() {
		return orderBookStatusType;
	}
	public void setOrderBookStatusType(OrderBookStatusType orderBookStatusType) {
		this.orderBookStatusType = orderBookStatusType;
	}
	/*
	 * Order will be added only order book status is OPEN
	 */
	public boolean add(Order order) {
		if(this.orderBookStatusType==OrderBookStatusType.OPEN) {
			this.orders.add(order);
			return true;
		}else {
			return false;
		}
	}
	
	public void addExecution(Execution execution) {
		this.execution=execution;
		this.execution.execute(this.orders);
	}
	public Integer getExecutionCount() {
		return executionCount;
	}
	public void setExecutionCount(Integer executionCount) {
		this.executionCount = executionCount;
	}
	@Override
	public String toString() {
		return "OrderBook [orders=" + orders + ", orderBookStatusType=" + orderBookStatusType + ", execution="
				+ execution + "]";
	}
	
}
