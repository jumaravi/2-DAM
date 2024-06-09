package com.tfg.mped.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.service.SilverServiceI;

@RestController
@RequestMapping("/mped")
public class SilverController {

	/** Dependencia: servicio de la plata */
	private final SilverServiceI sServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public SilverController(SilverServiceI sServ) {

		this.sServ = sServ;
	}
	
	/**
	 * Metodo para devolver la cotizacion de la plata en vela horaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/silverh1/{currency}")
	public ResponseEntity<List<Silver>> silverTimeSerieH1(@PathVariable String currency) {

		List<Silver> dataList = sServ.loadTimeSerieH1(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion de la plata en vela de 4 horas segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/silverh4/{currency}")
	public ResponseEntity<List<Silver>> silverTimeSerieH4(@PathVariable String currency) {

		List<Silver> dataList = sServ.loadTimeSerieH4(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion de la plata en vela diaria segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/silverD/{currency}")
	public ResponseEntity<List<Silver>> silverTimeSerieD(@PathVariable String currency) {

		List<Silver> dataList = sServ.loadTimeSerieD(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion de la plata en vela semanal segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/silverS/{currency}")
	public ResponseEntity<List<Silver>> silverTimeSerieS(@PathVariable String currency) {

		List<Silver> dataList = sServ.loadTimeSerieS(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	/**
	 * Metodo para devolver la cotizacion de la plata en vela mensual segun divisa
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/silverM/{currency}")
	public ResponseEntity<List<Silver>> silverTimeSerieM(@PathVariable String currency) {

		List<Silver> dataList = sServ.loadTimeSerieM(currency);

		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
}
