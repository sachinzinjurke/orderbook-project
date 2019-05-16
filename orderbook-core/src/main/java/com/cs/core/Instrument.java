package com.cs.core;

public class Instrument {
	
	private int instrumentId;
	private OrderBook ordreBook;
	
	public Instrument() {
		
	}
	public Instrument(int instrumentId,OrderBook ordreBook) {
		this.instrumentId=instrumentId;
		this.ordreBook=ordreBook;
	}
	public int getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}
	public OrderBook getOrdreBook() {
		return ordreBook;
	}
	public void setOrdreBook(OrderBook ordreBook) {
		this.ordreBook = ordreBook;
	}
	
	

}
