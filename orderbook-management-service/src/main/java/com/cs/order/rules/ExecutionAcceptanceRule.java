package com.cs.order.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;
import com.cs.core.Order;

public class ExecutionAcceptanceRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(ExecutionAcceptanceRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		/*
		 * Should not accept execution if valid book demand == execution quantity
		 */
		if(executionContext.getValidBookDemandQuantity()==executionContext.getExecution().getQuantity()) {
			executionContext.setShouldAcceptExecution(false);
			executionContext.setCanMarkAsExecuted(true);
			logger.info("*******Skipping all rules and marking orderbook as executed ");
		}
		/*
		 * Should allocate all the quantity to orders if execution quantity > valid demand quantity
		 */
		else if(executionContext.getValidBookDemandQuantity() < executionContext.getExecution().getQuantity()) {
			logger.info("*******Allocating all execution quanity to orderbook as valid demand quantity {} is less than execution quantity {}",executionContext.getValidBookDemandQuantity(),executionContext.getExecution().getQuantity());
			for (Order order : executionContext.getInstrument().getOrdreBook().getOrders()) {
				order.setAllotedQuantity(order.getQuantity());
			}
			executionContext.setShouldAcceptExecution(false);
			executionContext.setCanMarkAsExecuted(true);
		}/*
		 * Start processing the orderbook with rules
		 */
		else {
			executionContext.setShouldAcceptExecution(true);
			Integer executionCount=executionContext.getInstrument().getOrdreBook().getExecutionCount();
			executionContext.getInstrument().getOrdreBook().setExecutionCount(executionCount +1);
			logger.info("### #######################################################################");
			logger.info("### ExecutionAcceptanceRule execution count :: {} ",executionContext.getInstrument().getOrdreBook().getExecutionCount());
			logger.info("### #######################################################################");
			
		}
		logger.info("Executing ExecutionAcceptanceRule for context ");
	}

}
