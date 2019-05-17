package com.cs.order.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cs.core.Instrument;
import com.cs.core.OrderBook;


public class Cache {

	public static Map<Integer,OrderBook> INSTRUMENT_CACHE=new ConcurrentHashMap<Integer, OrderBook>();
	public static Map<Integer,Instrument> INSTRUMENT_CACHE_MAP=new ConcurrentHashMap<Integer, Instrument>();
	static {
		INSTRUMENT_CACHE.put(1, new OrderBook());
		INSTRUMENT_CACHE.put(2, new OrderBook());
		INSTRUMENT_CACHE.put(3, new OrderBook());
		INSTRUMENT_CACHE.put(4, new OrderBook());
		
		for(int i=1;i<5;i++) {
			Instrument instrument=new Instrument();
			instrument.setInstrumentId(i);
			instrument.setOrdreBook(new OrderBook());
			INSTRUMENT_CACHE_MAP.put(i, instrument);
		}
	}
}
