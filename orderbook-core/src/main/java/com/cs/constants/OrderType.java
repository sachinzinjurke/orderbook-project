package com.cs.constants;

import java.util.Random;

public enum OrderType {
	
	LIMIT("LIMIT"),MARKET("MARKET");
	
	private String orderType;
	
	private OrderType(String orderType) {
		this.orderType=orderType;
	}
	public String getOrderType() {
		return this.orderType;
	}
	
	public static OrderType getRandomOrderType() {
        Random random = new Random();
        //return values()[random.nextInt(values().length)];
        return OrderType.LIMIT;
    }
}
