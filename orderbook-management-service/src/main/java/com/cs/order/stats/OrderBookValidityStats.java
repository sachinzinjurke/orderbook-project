package com.cs.order.stats;

import java.util.List;

import com.cs.core.Order;

public class OrderBookValidityStats {
	
	private List<Order>validOrders;
	private List<Order>invalidOrders;
	private Integer smallestOrderInBook;
	private Integer biggestOrderInBook;
	private Integer orderBookDemand;;
	
	public List<Order> getValidOrders() {
		return validOrders;
	}
	public void setValidOrders(List<Order> validOrders) {
		this.validOrders = validOrders;
	}
	public List<Order> getInvalidOrders() {
		return invalidOrders;
	}
	public void setInvalidOrders(List<Order> invalidOrders) {
		this.invalidOrders = invalidOrders;
	}
	
	public Integer getSmallestOrderInBook() {
		return smallestOrderInBook;
	}
	public void setSmallestOrderInBook(Integer smallestOrderInBook) {
		this.smallestOrderInBook = smallestOrderInBook;
	}
	public Integer getBiggestOrderInBook() {
		return biggestOrderInBook;
	}
	public void setBiggestOrderInBook(Integer biggestOrderInBook) {
		this.biggestOrderInBook = biggestOrderInBook;
	}
	public Integer getOrderBookDemand() {
		return orderBookDemand;
	}
	public void setOrderBookDemand(Integer orderBookDemand) {
		this.orderBookDemand = orderBookDemand;
	}
	
	

}
