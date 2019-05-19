package com.cs.order.rules;



import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cs.core.ExecutionContext;

public class ValidBookDemandRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(ValidBookDemandRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
			logger.info("Executing ValidBookDemandRule for context ");
			Integer validDemandCountQuantity = executionContext.getInstrument().getOrdreBook().getOrders().
			stream()
			.filter(p->p.getIsValidOrder())
			.map(order->order.getQuantity())
			.collect(Collectors.summingInt(Integer::intValue));
			executionContext.setValidBookDemandQuantity(validDemandCountQuantity);
			logger.info(" Total valid demand quanity is {} ",validDemandCountQuantity);
	}

}
