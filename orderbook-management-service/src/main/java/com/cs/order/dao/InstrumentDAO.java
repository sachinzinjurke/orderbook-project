package com.cs.order.dao;

import java.util.List;
import java.util.Optional;

import com.cs.core.Instrument;
import com.cs.core.Order;
import com.cs.order.modal.OrderBookStatus;

public interface InstrumentDAO {
	
	public List<Instrument>getAllInstruments();
	public Instrument getInstrument(int instrumentId);
	public List<Order> getLimitOrders(int instrumentId);
	public boolean updateOrderBook(OrderBookStatus orderBookStatus);

}
