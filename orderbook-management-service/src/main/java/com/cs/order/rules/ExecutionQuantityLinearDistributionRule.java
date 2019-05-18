package com.cs.order.rules;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.ExecutionContext;
import com.cs.core.Instrument;
import com.cs.core.Order;

public class ExecutionQuantityLinearDistributionRule implements OrderBookRule{

	private static final Logger logger = LoggerFactory.getLogger(ExecutionQuantityLinearDistributionRule.class.getName());
	@Override
	public void execute(ExecutionContext executionContext) {
		
		logger.info("Executing ExecutionQuantityLinearDistributionRule for context ");
		if(executionContext.getShouldAcceptExecution()) {
			logger.info("Processing linear quantity distribution");
			if(executionContext.getValidBookDemandQuantity() > executionContext.getExecution().getQuantity()) {
				if(executionContext.getInstrument().getOrdreBook().getExecutionCount() == 1) {
					logger.info("Processing linear quantity distribution FIRST time for VALID DEMAND {} : ",executionContext.getValidBookDemandQuantity());
					distributeLinearlyToValidOrders(executionContext);	
				}else {
					logger.info("Processing linear quantity distribution NEXT time for VALID DEMAND {} : ",executionContext.getValidBookDemandQuantity());
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
			.sorted(Comparator.comparing(Order::getQuantity).reversed())
			.filter(p->p.getIsValidOrder())
			.map(order->{
				
				double weightedOrderQuantity=((order.getQuantity()*executionQuantity)/(double)validDemandCountQuantity);
				DecimalFormat df = new DecimalFormat("#.##"); 
				Double formattedWeightedOrderQuantity = Double.valueOf(df.format(weightedOrderQuantity));
				Integer qty=(int)Math.round((formattedWeightedOrderQuantity));
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
				
				return order;
			})
			.forEach(order->logger.info("**** Order Details Order id: {} ,Alloted Quantity: {}, Actual quantity: {},Instrument Id : {}, OrderBook Total Allotted Qty : {} ",order.getOrderId(),order.getAllotedQuantity(),order.getQuantity(),order.getInstrumentId(), executionContext.getTotalAllottedQtyToOrderBook()));
			
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
				.sorted(Comparator.comparing(Order::getQuantity).reversed())
				.filter(p->p.getIsValidOrder())
				.map(order->{
					Integer pendingAllotmentQtyForOrder=order.getQuantity()-order.getAllotedQuantity();
					
					double weightedOrderQuantity=((pendingAllotmentQtyForOrder*executionQuantity)/(double)validDemandCountQuantityOnNextExecution);
					DecimalFormat df = new DecimalFormat("#.##"); 
					Double formattedWeightedOrderQuantity = Double.valueOf(df.format(weightedOrderQuantity));
					Integer currentRunAllottedQuanity=(int)Math.round((formattedWeightedOrderQuantity));
					Integer totalAllottedQty=executionContext.getTotalAllottedQtyToOrderBook();
					//logger.info("totalAllottedQty:{}, actualPendingallotmentQty : {} , current run: {} ",totalAllottedQty, pendingAllotmentQtyForOrder ,currentRunAllottedQuanity);
					totalAllottedQty+=currentRunAllottedQuanity;
					if(totalAllottedQty > executionQuantity) {
						logger.info("***********Block-0 ");
						logger.info("***totalAllottedQty : {} , current run allotted : {} , executionQuantity {} ", totalAllottedQty ,currentRunAllottedQuanity,executionQuantity);
						Integer extraAllot=totalAllottedQty-executionQuantity;
						if(extraAllot==0) {
							logger.info("***Not allocating any quantity,already allocated completely");
						}else {
							logger.info("***pendingAllotmentQtyForOrder : {} , currentRunAllottedQuanity: {} , extraAllot {}, totalAllottedQty{} ", pendingAllotmentQtyForOrder ,currentRunAllottedQuanity,extraAllot,totalAllottedQty);
							if(pendingAllotmentQtyForOrder > (currentRunAllottedQuanity-extraAllot)) {
								logger.info("setting allocated quantity {} for OID {}",(currentRunAllottedQuanity-extraAllot),order.getOrderId());
								order.setAllotedQuantity(order.getAllotedQuantity() + (currentRunAllottedQuanity-extraAllot));
								
							}
							
						}
						//order.setAllotedQuantity(order.getAllotedQuantity() + currentRunAllottedQuanity);
						Boolean canMarkOrderBookAsExecuted = canMarkOrderBookAsExecuted(executionContext);
						if(canMarkOrderBookAsExecuted) {
							executionContext.setCanMarkAsExecuted(true);
						}
					}else {
						
						executionContext.setTotalAllottedQtyToOrderBook(totalAllottedQty);
						if((order.getQuantity()-order.getAllotedQuantity() >=currentRunAllottedQuanity)) {
							logger.info("**block-1");
							order.setAllotedQuantity(order.getAllotedQuantity() + currentRunAllottedQuanity);
							Boolean canMarkOrderBookAsExecuted = canMarkOrderBookAsExecuted(executionContext);
							if(canMarkOrderBookAsExecuted) {
								executionContext.setCanMarkAsExecuted(true);
							}
						}else if(currentRunAllottedQuanity > pendingAllotmentQtyForOrder) {
							logger.info("**block-2");
							order.setAllotedQuantity(order.getAllotedQuantity() + pendingAllotmentQtyForOrder);
							Boolean canMarkOrderBookAsExecuted = canMarkOrderBookAsExecuted(executionContext);
							if(canMarkOrderBookAsExecuted) {
								executionContext.setCanMarkAsExecuted(true);
							}
						}
						
						
					}
					return order;
				})
				.forEach(order->logger.info("****NEXT OID: {} ,Alloted qty: {}, Actual Qty: {},Instru : {}, OrderBoolTotalAllottedQty : {} ",order.getOrderId(),order.getAllotedQuantity(),order.getQuantity(),order.getInstrumentId(), executionContext.getTotalAllottedQtyToOrderBook()));
			}else {
				logger.info("***************ORDER IS GOOD TO GO TO MARK AS EXECUTED##############################33");
			}
			
		}catch(Exception ex) {
			logger.error("Error while distributing quantity in NEXT tun");
		}
			
	}
	/*
	 * In case of partial order quantity allotment, execution quanity > actual order demand quantity we can decide 
	 * OrderBook excution as EXECUTED
	 */
	
	private Boolean canMarkOrderBookAsExecuted(ExecutionContext executionContext) {
		Integer totalOrdersAllottedQty=getAllottedOrderCount(executionContext.getInstrument());
		if(totalOrdersAllottedQty==executionContext.getValidBookDemandQuantity()) {
			return true;
		}else {
			return false;
		}
		
	}

	private Integer getAllottedOrderCount(Instrument instrument) {
		Integer totalAllotedQuantity = instrument.getOrdreBook().getOrders().
		stream()
		.filter(p->p.getIsValidOrder())
		.map(order->{
			Integer allottedqty=order.getAllotedQuantity();
			return allottedqty;
		})
		.collect(Collectors.summingInt(Integer::intValue));
		return totalAllotedQuantity;
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
