package com.tfg.mped.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.service.PlatinumServiceI;

@RestController
@RequestMapping("/mped")
public class PlatinumController {

	/** Dependencia: servicio del platino */
	private final PlatinumServiceI ptServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public PlatinumController(PlatinumServiceI ptServ) {

		this.ptServ = ptServ;
	}

	/**
	 * Metodo para devolver la cotizacion del platino en vela horaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/platinumh1/{currency}")
	public ResponseEntity<List<Platinum>> platinumTimeSerieH1(@PathVariable String currency) {

		List<Platinum> dataList = ptServ.loadTimeSerieH1(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del platino en vela de 4 horas segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/platinumh4/{currency}")
	public ResponseEntity<List<Platinum>> platinumTimeSerieH4(@PathVariable String currency) {

		List<Platinum> dataList = ptServ.loadTimeSerieH4(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del platino en vela diaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/platinumD/{currency}")
	public ResponseEntity<List<Platinum>> platinumTimeSerieD(@PathVariable String currency) {

		List<Platinum> dataList = ptServ.loadTimeSerieD(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del platino en vela semanal segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/platinumS/{currency}")
	public ResponseEntity<List<Platinum>> platinumTimeSerieS(@PathVariable String currency) {

		List<Platinum> dataList = ptServ.loadTimeSerieS(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
}
