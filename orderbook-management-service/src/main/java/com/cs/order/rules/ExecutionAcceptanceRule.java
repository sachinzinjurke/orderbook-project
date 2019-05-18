package com.cs.order.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;

public class ExecutionAcceptanceRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(ExecutionAcceptanceRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		if(executionContext.getValidBookDemandQuantity()==executionContext.getExecution().getQuantity()) {
			executionContext.setShouldAcceptExecution(false);
			logger.info("*******Skipping all rules and marking orderbook as executed ");
		}else {
			executionContext.setShouldAcceptExecution(true);
			Integer executionCount=executionContext.getInstrument().getOrdreBook().getExecutionCount();
			logger.info("### ExecutionAcceptanceRule execution count :: {} ",executionCount);
			executionContext.getInstrument().getOrdreBook().setExecutionCount(++executionCount);
		}
		logger.info("Executing ExecutionAcceptanceRule for context ");
	}

}
