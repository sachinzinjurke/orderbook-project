package com.cs.order.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cs.core.Execution;
import com.cs.core.ExecutionContext;
import com.cs.core.Instrument;
import com.cs.core.Order;
import com.cs.core.OrderBook;
import com.cs.order.constants.Cache;
import com.cs.order.dao.InstrumentDAO;
import com.cs.order.modal.OrderBookStatus;
import com.cs.order.service.OrderBookService;

@RestController
public class InstrumentController {
	
	@Autowired
	private OrderBookService orderBookService ;

	@Autowired
	private InstrumentDAO instrumentDAO;

	@GetMapping(path = "/instruments", produces = "application/json")
	public List<Instrument> getInstruments() {
		return instrumentDAO.getAllInstruments();
	}
	@GetMapping("/instruments/{id}")
	public Instrument getInstrument(@PathVariable Integer id) {
		Instrument instrument=instrumentDAO.getInstrument(id);
		return instrument;
	}
	@GetMapping("/instruments/getlimitorders/{id}")
	public  List<Order> getLimitOrders(@PathVariable Integer id) {
		List<Order> limitOrders=instrumentDAO.getLimitOrders(id);
		return limitOrders;
	}
	@PostMapping("/instruments/orderbookstatus")
	  void updateOrderBook(@RequestBody OrderBookStatus orderBookStatus) {
	    Boolean update= instrumentDAO.updateOrderBook(orderBookStatus);
	    if(update) {
	    	ExecutionContext executionContext=new ExecutionContext();
	    	Instrument instrument = Cache.INSTRUMENT_CACHE_MAP.get(orderBookStatus.getInstrumentId());
	    	executionContext.setExecution(new Execution());
	    	executionContext.setInstrument(instrument);
	    	orderBookService.addOrderBookForProcessing(executionContext);
	    }
	  }
}
