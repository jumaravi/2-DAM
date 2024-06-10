package com.tfg.mped.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.HistoricalData;
import com.tfg.mped.service.HistoricalDataServiceI;

@RestController
@RequestMapping("/mped")
public class HistoricalDataController {

	/** Dependencia: servicio de datos historicos */
	private final HistoricalDataServiceI hdServ;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gServ
	 */
	@Autowired
	public HistoricalDataController(HistoricalDataServiceI hdServ) {

		this.hdServ = hdServ;
	}
	
	/**
	 * Metodo para devolver la cotizacion historica del metal segun temporalidad
	 * 
	 * @param currency
	 * @return ResponseEntity
	 */
	@GetMapping("/metalHD/{metal}/{timeSerie}")
	public ResponseEntity<List<HistoricalData>> metalHDTimeSerieD(@PathVariable String metal,@PathVariable String timeSerie) {

		
		List<HistoricalData> dataList = hdServ.loadHistoricalData(metal, timeSerie);
		
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}
	//http://localhost:8090/metalHD/gold/D
}
