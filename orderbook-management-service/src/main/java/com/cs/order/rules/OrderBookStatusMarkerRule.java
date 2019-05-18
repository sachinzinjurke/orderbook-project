package com.cs.order.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;

public class OrderBookStatusMarkerRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(OrderBookStatusMarkerRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		logger.info("Executing OrderBookStatusMarkerRule for context ");
	}

}
