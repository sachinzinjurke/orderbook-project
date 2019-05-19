package com.cs.order.dao;

import java.util.List;

import com.cs.core.Instrument;
import com.cs.core.Order;
import com.cs.order.modal.OrderBookStatus;
import com.cs.order.stats.OrderBookSummaryStats;
import com.cs.order.stats.OrderBookValidityStats;

public interface InstrumentDAO {
	
	public List<Instrument>getAllInstruments();
	public Instrument getInstrument(int instrumentId);
	public OrderBookSummaryStats getInstrumentOrderBookStats(int instrumentId);
	public OrderBookValidityStats getInstrumentValidInvalidOrderBookStats(int instrumentId);
	public List<Order> getLimitOrders(int instrumentId);
	public boolean updateOrderBook(OrderBookStatus orderBookStatus);

}
