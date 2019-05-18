package com.cs.order.callback;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;
import com.cs.order.rules.OrderBookRuleCollection;

public class OrderBookProcessingThread implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderBookProcessingThread.class.getName());
	
	private Boolean isAlive=true;

	private BlockingQueue<ExecutionContext>orderBookQueue;
	
	private OrderBookRuleCollection orderBookRuleCollection;
	
	public OrderBookProcessingThread(BlockingQueue<ExecutionContext>orderBookQueue) {
		this.orderBookQueue=orderBookQueue;
	}
	@Override
	public void run() {
		
		while(isAlive) {
			if(orderBookQueue.isEmpty()) {
				//logger.info("Queue is empty");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				logger.info("Fetching executionContext from the queue");
				try {
					ExecutionContext executionContext = orderBookQueue.poll(100,TimeUnit.MILLISECONDS);
					logger.info("successfully fetched executionContext from the queue");
					logger.info("Processing execution context with quantity : {} and price : {}  ",executionContext.getExecution().getQuantity(),executionContext.getExecution().getPrice());
					this.orderBookRuleCollection.processRules(executionContext);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}
	public OrderBookRuleCollection getOrderBookRuleCollection() {
		return orderBookRuleCollection;
	}
	public void setOrderBookRuleCollection(OrderBookRuleCollection orderBookRuleCollection) {
		this.orderBookRuleCollection = orderBookRuleCollection;
	}
	

}
