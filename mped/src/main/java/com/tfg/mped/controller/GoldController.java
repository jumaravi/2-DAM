package com.tfg.mped.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Gold;
import com.tfg.mped.service.GoldServiceI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/mped")
public class GoldController {

	/** Dependencia: servicio del oro */
	private final GoldServiceI gServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public GoldController(GoldServiceI gServ) {

		this.gServ = gServ;
	}

	/**
	 * Metodo para devolver la cotizacion del oro en vela horaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */

	@GetMapping("/goldh1/{currency}")
	public ResponseEntity<List<Gold>> goldTimeSerieH1(@PathVariable String currency) {

		List<Gold> dataList = gServ.loadTimeSerieH1(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del oro en vela de 4 horas segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/goldh4/{currency}")
	public ResponseEntity<List<Gold>> goldTimeSerieH4(@PathVariable String currency) {

		List<Gold> dataList = gServ.loadTimeSerieH4(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del oro en vela diaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/goldD/{currency}")
	public ResponseEntity<List<Gold>> goldTimeSerieD(@PathVariable String currency) {

		List<Gold> dataList = gServ.loadTimeSerieD(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del oro en vela semanal segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/goldS/{currency}")
	public ResponseEntity<List<Gold>> goldTimeSerieS(@PathVariable String currency) {

		List<Gold> dataList = gServ.loadTimeSerieS(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion del oro en vela mensual segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/goldM/{currency}")
	public ResponseEntity<List<Gold>> goldTimeSerieM(@PathVariable String currency) {

		List<Gold> dataList = gServ.loadTimeSerieM(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
}
