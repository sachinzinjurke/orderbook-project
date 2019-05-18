package com.cs.order.service;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.constants.OrderBookStatusType;
import com.cs.core.ExecutionContext;

public class OrderBookService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderBookService.class.getName());
	private BlockingQueue<ExecutionContext>orderBookQueue;
	
	public OrderBookService(BlockingQueue<ExecutionContext>orderBookQueue){
		this.orderBookQueue=orderBookQueue;
	}
	
	public void addOrderBookForProcessing(ExecutionContext executionContext) {
		OrderBookStatusType orderBookStatusType = executionContext.getInstrument().getOrdreBook().getOrderBookStatusType();
		if(orderBookStatusType==orderBookStatusType.OPEN || orderBookStatusType==orderBookStatusType.CLOSE) {
			logger.info("adding instrument for executuion ");
			this.orderBookQueue.add(executionContext);
			logger.info("Added instrument for executuion ");
		}else if(orderBookStatusType==orderBookStatusType.EXECUTED){
			logger.info("OrderBook for Instrument is already executed {} , rejecting OrderBook Execution",executionContext.getInstrument().getInstrumentId());
		}
		
	}
}
