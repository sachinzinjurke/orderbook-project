package com.cs.core;

public class ExecutionContext {
	
	private Execution execution;
	private Instrument instrument;
	private Integer validBookDemandQuantity;
	private Boolean shouldAcceptExecution;
	private Integer totalAllottedQtyToOrderBook=0;
	
	public Execution getExecution() {
		return execution;
	}
	public void setExecution(Execution execution) {
		this.execution = execution;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	public Integer getValidBookDemandQuantity() {
		return validBookDemandQuantity;
	}
	public void setValidBookDemandQuantity(Integer validBookDemandQuantity) {
		this.validBookDemandQuantity = validBookDemandQuantity;
	}
	public Boolean getShouldAcceptExecution() {
		return shouldAcceptExecution;
	}
	public void setShouldAcceptExecution(Boolean shouldAcceptExecution) {
		this.shouldAcceptExecution = shouldAcceptExecution;
	}
	public Integer getTotalAllottedQtyToOrderBook() {
		return totalAllottedQtyToOrderBook;
	}
	public void setTotalAllottedQtyToOrderBook(Integer totalAllottedQtyToOrderBook) {
		this.totalAllottedQtyToOrderBook = totalAllottedQtyToOrderBook;
	}
	
	

}
