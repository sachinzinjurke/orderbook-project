package com.cs.order.rules;

import java.text.DecimalFormat;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;

public class ExecutionQuantityLinearDistributionRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(ExecutionQuantityLinearDistributionRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		logger.info("Executing ExecutionQuantityLinearDistributionRule for context ");
		if(executionContext.getShouldAcceptExecution()) {
			logger.info("Processing linear quantity distribution");
			if(executionContext.getValidBookDemandQuantity() > executionContext.getExecution().getQuantity()) {
				if(executionContext.getInstrument().getOrdreBook().getExecutionCount() == 1) {
					logger.info("Processing linear quantity distribution FIRST time");
					distributeLinearlyToValidOrders(executionContext);	
				}else {
					logger.info("Processing linear quantity distribution NEXT time");
					logger.info("Execution count :: {}",executionContext.getInstrument().getOrdreBook().getExecutionCount() );
					distributeLinearlyToValidOrdersOnNextExecution(executionContext);
				}
				
			}
			
		}else {
			logger.info("Skipping ExecutionQuantityLinearDistributionRule due to execution acceptance rule ");
		}
		
	}
	
	private void distributeLinearlyToValidOrders(ExecutionContext executionContext) {
		
		try {
			Integer executionQuantity = executionContext.getExecution().getQuantity();
			Integer validDemandCountQuantity =deriveTotalValidDemand(executionContext);
			executionContext.getInstrument().getOrdreBook().getOrders().
			stream()
			.filter(p->p.getIsValidOrder())
			.map(order->{
				
				double distributedQuantity=((order.getQuantity()*executionQuantity)/(double)validDemandCountQuantity);
				DecimalFormat df = new DecimalFormat("#.##"); 
				Double formattedDistributedQuantity = Double.valueOf(df.format(distributedQuantity));
				Integer qty=(int)Math.round((formattedDistributedQuantity));
				Integer totalAllottedQty=executionContext.getTotalAllottedQtyToOrderBook();
				totalAllottedQty+=qty;
				if(totalAllottedQty > executionQuantity) {
					//logger.info("Rule failed :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
					qty=qty-1;
					totalAllottedQty=totalAllottedQty-1;
					order.setAllotedQuantity(order.getAllotedQuantity() + qty);
					
				}else {
					//logger.info("Rule Passed :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
					order.setAllotedQuantity(order.getAllotedQuantity() + qty);
					executionContext.setTotalAllottedQtyToOrderBook(totalAllottedQty);
					
				}
				//logger.info("Final Count :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
				//order.setAllotedQuantity(qty);
				return order;
			})
			.forEach(order->logger.info("**** Order Details Order id: {} ,Alloted Quantity: {}, Actual quantity: {},Instrument Id : {}, OrderBool Total Allotted Qty : {} ",order.getOrderId(),order.getAllotedQuantity(),order.getQuantity(),order.getInstrumentId(), executionContext.getTotalAllottedQtyToOrderBook()));
			
		}catch(Exception ex) {
			logger.error("Exception during first run ",ex);
		}
			
	}
	private Integer deriveTotalValidDemand(ExecutionContext executionContext) {
		Integer validDemandCountQuantity = executionContext.getInstrument().getOrdreBook().getOrders().
				stream()
				.filter(p->p.getIsValidOrder())
				.map(order->order.getQuantity())
				.collect(Collectors.summingInt(Integer::intValue));
		
		return validDemandCountQuantity;
	}

	private void distributeLinearlyToValidOrdersOnNextExecution(ExecutionContext executionContext) {
		
		try {
				
			Integer executionQuantity = executionContext.getExecution().getQuantity();
			Integer validDemandCountQuantityOnNextExecution =deriveTotalValidDemandOnNextExecution(executionContext);
			
			if(validDemandCountQuantityOnNextExecution!=0) {
				executionContext.getInstrument().getOrdreBook().getOrders().
				stream()
				.filter(p->p.getIsValidOrder())
				.map(order->{
					Integer actualPendingallotmentQty=order.getQuantity()-order.getAllotedQuantity();
					
					double distributedQuantity=((actualPendingallotmentQty*executionQuantity)/(double)validDemandCountQuantityOnNextExecution);
					DecimalFormat df = new DecimalFormat("#.##"); 
					Double formattedDistributedQuantity = Double.valueOf(df.format(distributedQuantity));
					Integer runAllottedQuantity=(int)Math.round((formattedDistributedQuantity));
					Integer totalAllottedQty=executionContext.getTotalAllottedQtyToOrderBook();
					logger.info("actualPendingallotmentQty : {} , run allotted : {} ", actualPendingallotmentQty ,runAllottedQuantity);
					totalAllottedQty+=runAllottedQuantity;
					if(totalAllottedQty > executionQuantity) {
						//logger.info("Rule failed :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
						runAllottedQuantity=runAllottedQuantity-1;
						totalAllottedQty=totalAllottedQty-1;
						order.setAllotedQuantity(order.getAllotedQuantity() + runAllottedQuantity);
					}else {
						//logger.info("Rule Passed :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
						executionContext.setTotalAllottedQtyToOrderBook(totalAllottedQty);
						if((order.getQuantity()-order.getAllotedQuantity() >=runAllottedQuantity)) {
							order.setAllotedQuantity(order.getAllotedQuantity() + runAllottedQuantity);
						}else if(runAllottedQuantity > actualPendingallotmentQty) {
							order.setAllotedQuantity(order.getAllotedQuantity() + actualPendingallotmentQty);
						}
						
						
					}
					//logger.info("Final Count :: Order id {}, totalAllottedQty:  {},executionQuantity: {}  ",order.getOrderId(),totalAllottedQty,executionQuantity);
					//order.setAllotedQuantity(qty);
					//Integer nextRunAlltment=order.getAllotedQuantity()+qty;
					//order.setAllotedQuantity(nextRunAlltment);
					return order;
				})
				.forEach(order->logger.info("****Next Execution Order Details Order id: {} ,Alloted Quantity: {}, Actual quantity: {},Instrument Id : {}, OrderBool Total Allotted Qty : {} ",order.getOrderId(),order.getAllotedQuantity(),order.getQuantity(),order.getInstrumentId(), executionContext.getTotalAllottedQtyToOrderBook()));
			}else {
				logger.info("***************ORDER IS GOOD TO GO TO MARK AS EXECUTED##############################33");
			}
			
		}catch(Exception ex) {
			logger.error("Error while distributing quantity in NEXT tun");
		}
			
	}
	
	private Integer deriveTotalValidDemandOnNextExecution(ExecutionContext executionContext) {
		Integer validDemandCountQuantityOnNextExecution = executionContext.getInstrument().getOrdreBook().getOrders().
				stream()
				.filter(p->p.getIsValidOrder())
				.map(order->{
					Integer allottedqty=order.getQuantity()-order.getAllotedQuantity();
					return allottedqty;
				})
				.collect(Collectors.summingInt(Integer::intValue));
		
		return validDemandCountQuantityOnNextExecution;
	}
}
