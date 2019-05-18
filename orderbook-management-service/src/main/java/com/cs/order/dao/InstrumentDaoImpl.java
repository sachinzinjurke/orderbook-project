package com.cs.order.dao;

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

public class InstrumentDaoImpl implements InstrumentDAO {

	private static final Logger logger = LoggerFactory.getLogger(InstrumentDaoImpl.class.getName());

	@Override
	public List<Instrument> getAllInstruments() {
		logger.info("Fetching all instrument ");
		List<Instrument> instrumentList = Cache.INSTRUMENT_CACHE_MAP.values().stream().collect(Collectors.toList());
		return instrumentList;
		/*
		 * List<Instrument> instruments =
		 * Cache.INSTRUMENT_CACHE.entrySet().stream().sorted(Comparator.comparing(e ->
		 * e.getKey())) .map(e -> new Instrument(e.getKey(),
		 * e.getValue())).collect(Collectors.toList()); return instruments;
		 */

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

}
