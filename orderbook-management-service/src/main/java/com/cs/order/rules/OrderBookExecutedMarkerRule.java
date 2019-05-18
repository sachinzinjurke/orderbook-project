package com.cs.order.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.constants.OrderBookStatusType;
import com.cs.core.ExecutionContext;

public class OrderBookExecutedMarkerRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(OrderBookExecutedMarkerRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		logger.info("Executing OrderBookStatusMarkerRule for context ");
		if(executionContext.getCanMarkAsExecuted()) {
			logger.info("*****Marking orderbook as EXECUTED for the instrument {} ",executionContext.getInstrument().getInstrumentId());
			executionContext.getInstrument().getOrdreBook().setOrderBookStatusType(OrderBookStatusType.EXECUTED);
		}else {
			logger.info("*****Can not mark orderbook as EXECUTED for the instrument {} ",executionContext.getInstrument().getInstrumentId());
		}
	}

}
