package com.cs.order.constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cs.core.Instrument;
import com.cs.core.OrderBook;


public class Cache {

	public static Map<Integer,OrderBook> INSTRUMENT_CACHE=new ConcurrentHashMap<Integer, OrderBook>();
	static {
		INSTRUMENT_CACHE.put(1, new OrderBook());
		INSTRUMENT_CACHE.put(2, new OrderBook());
		INSTRUMENT_CACHE.put(3, new OrderBook());
		INSTRUMENT_CACHE.put(4, new OrderBook());
	}
}
