package com.cs.order.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cs.core.Instrument;
import com.cs.core.OrderBook;
/*
 * This class will hold the Instrument static information
 */

public class Cache {

	public static Map<Integer,Instrument> INSTRUMENT_CACHE_MAP=new ConcurrentHashMap<Integer, Instrument>();
	static {
		
		for(int i=1;i<5;i++) {
			Instrument instrument=new Instrument();
			instrument.setInstrumentId(i);
			instrument.setOrdreBook(new OrderBook());
			INSTRUMENT_CACHE_MAP.put(i, instrument);
		}
	}
}
