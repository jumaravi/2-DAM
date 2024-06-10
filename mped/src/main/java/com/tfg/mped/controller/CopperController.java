package com.tfg.mped.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Copper;
import com.tfg.mped.service.CopperServiceI;

@RestController
@RequestMapping("/mped")
public class CopperController {

	/** Dependencia: servicio del cobre */
	private final CopperServiceI cServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public CopperController(CopperServiceI cServ) {

		this.cServ = cServ;
	}
	
	/**
	 * Metodo para devolver la cotizacion del cobre en vela horaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/copperh1/{currency}")
	public ResponseEntity<List<Copper>> copperTimeSerieH1(@PathVariable String currency) {
		
		List<Copper> dataList = cServ.loadTimeSerieH1(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del cobre en vela de 4 horas segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/copperh4/{currency}")
	public ResponseEntity<List<Copper>> copperTimeSerieH4(@PathVariable String currency) {

		List<Copper> dataList = cServ.loadTimeSerieH4(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del cobre en vela diaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/copperD/{currency}")
	public ResponseEntity<List<Copper>> copperTimeSerieD(@PathVariable String currency) {

		List<Copper> dataList = cServ.loadTimeSerieD(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del cobre en vela semanal segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/copperS/{currency}")
	public ResponseEntity<List<Copper>> copperTimeSerieS(@PathVariable String currency) {

		List<Copper> dataList = cServ.loadTimeSerieS(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
}
