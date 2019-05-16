package com.cs.order.dao;

import java.util.List;
import java.util.Optional;

import com.cs.core.Instrument;
import com.cs.order.modal.OrderBookStatus;

public interface InstrumentDAO {
	
	public List<Instrument>getAllInstruments();
	public Optional<Instrument> getInstrument(int instrumentId);
	public boolean updateOrderBook(OrderBookStatus orderBookStatus);

}
