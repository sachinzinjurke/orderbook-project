package com.cs.order.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.constants.OrderType;
import com.cs.core.ExecutionContext;

public class OrderValidityMarkerRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(OrderValidityMarkerRule.class.getName());
	
	/*
	 * (non-Javadoc)
	 * @see com.cs.order.rules.OrderBookRule#execute(com.cs.core.ExecutionContext)
	 * Rule will evaluate validity status against each order if order price is > execution price
	 */
	
	@Override
	public void execute(ExecutionContext executionContext) {

		logger.info("Executing OrderValidityMarkerRule for context ");
		executionContext.getInstrument().getOrdreBook().getOrders().stream().forEach(order->{
			if(order.getOrderType()==OrderType.LIMIT) {
				if(order.getPrice() < executionContext.getExecution().getPrice()) {
					order.setIsValidOrder(false);
				}else {
					order.setIsValidOrder(true);
				}
			}else {
				//Market orders are always marked as invalid for execution
				order.setIsValidOrder(false);
			}
		});
		logger.info("Finished executing OrderValidityMarkerRule ");
	}

}
