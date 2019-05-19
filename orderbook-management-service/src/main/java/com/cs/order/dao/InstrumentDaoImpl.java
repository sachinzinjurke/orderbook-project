package com.cs.order.dao;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.constants.OrderBookStatusType;
import com.cs.constants.OrderType;
import com.cs.core.Instrument;
import com.cs.core.Order;
import com.cs.order.constants.Cache;
import com.cs.order.modal.OrderBookStatus;
import com.cs.order.stats.OrderBookSummaryStats;
import com.cs.order.stats.OrderBookValidityStats;

public class InstrumentDaoImpl implements InstrumentDAO {

	private static final Logger logger = LoggerFactory.getLogger(InstrumentDaoImpl.class.getName());

	@Override
	public List<Instrument> getAllInstruments() {
		logger.info("Fetching all instrument ");
		List<Instrument> instrumentList = Cache.INSTRUMENT_CACHE_MAP.values().stream().collect(Collectors.toList());
		return instrumentList;

	}

	@Override
	public Instrument getInstrument(int instrumentId) {
		logger.info("Fetching instrument id for {}", instrumentId);
		Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(instrumentId);
		return instrument;
	}

	@Override
	public boolean updateOrderBook(OrderBookStatus orderBookStatus) {
		logger.info("Updating orderbook status with request {}", orderBookStatus);
		Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(orderBookStatus.getInstrumentId());
		if (instrument != null && !(instrument.getOrdreBook().getOrderBookStatusType()==OrderBookStatusType.EXECUTED)) {
			logger.info("Updating status, found instrument");
			instrument.getOrdreBook().setOrderBookStatusType(orderBookStatus.getOrderBookStatusType());
			return true;
		}else {
			logger.info("Seems like ordrebook for request : {}  is already executed", orderBookStatus);
		}
		return false;
	}

	@Override
	public List<Order> getLimitOrders(int instrumentId) {
		logger.info("Fetching valid orders for instrument id {}", instrumentId);
		Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(instrumentId);
		List<Order> limitOrders = instrument.getOrdreBook().getOrders().stream().filter(order->order.getOrderType()==OrderType.LIMIT).collect(Collectors.toList());
		return limitOrders;
	}

	@Override
	public OrderBookSummaryStats getInstrumentOrderBookStats(int instrumentId) {
		logger.info("Fetching order book summary stats for instrument id {}", instrumentId);
		Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(instrumentId);
		IntSummaryStatistics statistics = instrument.getOrdreBook().getOrders().stream()
		        .mapToInt(Order::getQuantity)
		        .summaryStatistics();
		 
		int min = statistics.getMin();
		int max = statistics.getMax();
		long total=statistics.getSum();
		long count=statistics.getCount();
		OrderBookSummaryStats stats=new OrderBookSummaryStats();
		stats.setSmallestOrderInBook(min);
		stats.setBiggestOrderInBook(max);
		stats.setTotalOrderCount(count);
		stats.setTotalOrderBookQuantityCount(total);
		return stats;
	}

	@Override
	public OrderBookValidityStats getInstrumentValidInvalidOrderBookStats(int instrumentId) {
		logger.info("Fetching valid invalid order book summary stats for instrument id {}", instrumentId);
		Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(instrumentId);
		IntSummaryStatistics statistics = instrument.getOrdreBook().getOrders().stream()
		        .mapToInt(Order::getQuantity)
		        .summaryStatistics();
		 
		OrderBookValidityStats validInvalidStats=new OrderBookValidityStats();
		validInvalidStats.setBiggestOrderInBook(statistics.getMax());
		validInvalidStats.setSmallestOrderInBook(statistics.getMin());
		
		List<Order> inValidOrderList = instrument.getOrdreBook().getOrders().stream().filter((order)->{
			if(order.getOrderType()==OrderType.LIMIT) {
				if(order.getPrice() < 20.00) {
					return true;
				}else {
					return false;
				}
				
			}else{
				return false;
			}
		}
		).collect(Collectors.toList());
		
		List<Order> validOrderList = instrument.getOrdreBook().getOrders().stream().filter((order)->{
			if(order.getOrderType()==OrderType.LIMIT) {
				if(order.getPrice() > 20.00) {
					return true;
				}else {
					return false;
				}
				
			}else{
				return false;
			}
		}
		).collect(Collectors.toList());
		
		validInvalidStats.setValidOrders(validOrderList);
		validInvalidStats.setInvalidOrders(inValidOrderList);
		
		Integer orderBookDemandQuantity=validOrderList.stream().map(order->order.getQuantity())
		.collect(Collectors.summingInt(Integer::intValue));
		
		validInvalidStats.setOrderBookDemand(orderBookDemandQuantity);
		
		logger.info("Finished valid invalid order book summary stats for instrument id {}", instrumentId);
		
	return validInvalidStats;
	}	
}
