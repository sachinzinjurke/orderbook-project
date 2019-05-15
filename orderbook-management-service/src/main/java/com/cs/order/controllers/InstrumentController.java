package com.cs.order.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cs.core.Instrument;
import com.cs.order.dao.InstrumentDAO;

@RestController
//@RequestMapping(path = "/instruments")
public class InstrumentController {

	@Autowired
	private InstrumentDAO instrumentDAO;

	@GetMapping(path = "/instruments", produces = "application/json")
	public List<Instrument> getInstruments() {
		return instrumentDAO.getAllInstruments();
	}
	@GetMapping("/instruments/{id}")
	public Instrument retrieveStudent(@PathVariable Integer id) {
		Optional<Instrument> instrument=instrumentDAO.getInstrument(id);
		if(instrument.isPresent()) {
			return instrument.get();
		}else {
			return null;
		}
		
	}
}
