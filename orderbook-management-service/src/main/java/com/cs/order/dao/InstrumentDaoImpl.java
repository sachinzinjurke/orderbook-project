package com.cs.order.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cs.core.Instrument;
import com.cs.core.OrderBook;
import com.cs.order.constants.Cache;

public class InstrumentDaoImpl implements InstrumentDAO{

	@Override
	public List<Instrument> getAllInstruments() {
		
		List<Instrument> instruments = Cache.INSTRUMENT_CACHE.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
		.map(e -> new Instrument(e.getKey(), e.getValue())).collect(Collectors.toList());
		return instruments;
	}

	@Override
	public Optional<Instrument> getInstrument(int instrumentId) {
		OrderBook orderBook = Cache.INSTRUMENT_CACHE.get(instrumentId);
		Instrument instrument=new Instrument(instrumentId,orderBook);
		Optional<Instrument> optInstrument = Optional.of(instrument);
		return optInstrument;
	}

}
