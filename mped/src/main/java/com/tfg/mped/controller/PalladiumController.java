package com.tfg.mped.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.service.PalladiumServiceI;

@RestController
@RequestMapping("/mped")
public class PalladiumController {

	/** Dependencia: servicio del paladio */
	private final PalladiumServiceI plServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public PalladiumController(PalladiumServiceI plServ) {

		this.plServ = plServ;
	}
	
	/**
	 * Metodo para devolver la cotizacion del paladio en vela horaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/palladiumh1/{currency}")
	public ResponseEntity<List<Palladium>> palladiumTimeSerieH1(@PathVariable String currency) {

		List<Palladium> dataList = plServ.loadTimeSerieH1(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del paladio en vela de 4 horas segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/palladiumh4/{currency}")
	public ResponseEntity<List<Palladium>> palladiumTimeSerieH4(@PathVariable String currency) {

		List<Palladium> dataList = plServ.loadTimeSerieH4(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del paladio en vela diaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/palladiumD/{currency}")
	public ResponseEntity<List<Palladium>> palladiumTimeSerieD(@PathVariable String currency) {

		List<Palladium> dataList = plServ.loadTimeSerieD(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del paladio en vela semanal segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/palladiumS/{currency}")
	public ResponseEntity<List<Palladium>> palladiumTimeSerieS(@PathVariable String currency) {

		List<Palladium> dataList = plServ.loadTimeSerieS(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del paladio en vela mensual segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/palladiumM/{currency}")
	public ResponseEntity<List<Palladium>> palladiumTimeSerieM(@PathVariable String currency) {

		List<Palladium> dataList = plServ.loadTimeSerieM(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
}
