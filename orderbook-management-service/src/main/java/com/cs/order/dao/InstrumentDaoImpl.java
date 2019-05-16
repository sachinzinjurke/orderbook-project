package com.cs.order.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs.core.Instrument;
import com.cs.core.OrderBook;
import com.cs.order.constants.Cache;
import com.cs.order.modal.OrderBookStatus;

public class InstrumentDaoImpl implements InstrumentDAO{

	private static final Logger logger = LoggerFactory.getLogger(InstrumentDaoImpl.class.getName());
	@Override
	public List<Instrument> getAllInstruments() {
		
		  logger.info("Fetching all instrument "); List<Instrument> instruments =
		  Cache.INSTRUMENT_CACHE.entrySet().stream().sorted(Comparator.comparing(e ->
		  e.getKey())) .map(e -> new Instrument(e.getKey(),
		  e.getValue())).collect(Collectors.toList()); return instruments;
	}

	@Override
	public Optional<Instrument> getInstrument(int instrumentId) {
		logger.info("Fetching instrument id for {}",instrumentId);
		  OrderBook orderBook = Cache.INSTRUMENT_CACHE.get(instrumentId); Instrument
		  instrument=new Instrument(instrumentId,orderBook); Optional<Instrument>
		  optInstrument = Optional.of(instrument); return optInstrument;
	}

	@Override
	public boolean updateOrderBook(OrderBookStatus orderBookStatus) {
		logger.info("Updating orderbook status with request {}",orderBookStatus);
		OrderBook orderBook = Cache.INSTRUMENT_CACHE.get(orderBookStatus.getInstrumentId());
		if(orderBook!=null) {
			logger.info("Updating status, found instrument");
			orderBook.setOrderBookStatusType(orderBookStatus.getOrderBookStatusType());
			return true;
		}
		return false;
	}

}
