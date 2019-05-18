package com.cs.constants;

public enum OrderBookStatusType {
	
	OPEN("OPEN"),CLOSE("CLOSE"),EXECUTED("EXECUTED");
	
	private String status;
	
	private OrderBookStatusType(String status){
		this.status=status;
	}
	private String getStatus() {
		return this.status;
	}
}
