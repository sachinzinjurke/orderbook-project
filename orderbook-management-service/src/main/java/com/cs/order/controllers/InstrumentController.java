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
import com.cs.core.OrderBook;
import com.cs.order.constants.Cache;
import com.cs.order.dao.InstrumentDAO;
import com.cs.order.modal.OrderBookStatus;
import com.cs.order.service.OrderBookService;

@RestController
//@RequestMapping(path = "/instruments")
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
	public Instrument retrieveStudent(@PathVariable Integer id) {
		Instrument instrument=instrumentDAO.getInstrument(id);
		return instrument;
	}
	
	@PostMapping("/instruments/orderbookstatus")
	  void updateOrderBook(@RequestBody OrderBookStatus orderBookStatus) {
	    Boolean update= instrumentDAO.updateOrderBook(orderBookStatus);
	    if(update) {
	    	ExecutionContext executionContext=new ExecutionContext();
	    	OrderBook orderBook = Cache.INSTRUMENT_CACHE.get(orderBookStatus.getInstrumentId());
	    	Instrument instrument=new Instrument(orderBookStatus.getInstrumentId(),orderBook);
	    	executionContext.setExecution(new Execution());
	    	executionContext.setInstrument(instrument);
	    	orderBookService.addInstrumentForProcessing(executionContext);
	    }
	  }
}
