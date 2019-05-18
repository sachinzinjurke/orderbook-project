package com.cs.order.rules;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;

public class OrderBookRuleCollection {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderBookRuleCollection.class.getName());
	
	private List<OrderBookRule> orderBookRuleList;

	public List<OrderBookRule> getOrderBookRuleList() {
		return orderBookRuleList;
	}

	public void setOrderBookRuleList(List<OrderBookRule> orderBookRuleList) {
		this.orderBookRuleList = orderBookRuleList;
	}
	
	public void processRules(ExecutionContext executionContext) {
		logger.info("Started processing rules for execution context");
		for (OrderBookRule orderBookRule : orderBookRuleList) {
			orderBookRule.execute(executionContext);
		}
		logger.info("Finished processing rules for execution context");
	}
}
