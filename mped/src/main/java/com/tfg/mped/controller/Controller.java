package com.tfg.mped.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tfg.mped.persistence.Gold;
import com.tfg.mped.service.GoldServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/prueba")
public class Controller {

	@Autowired
	GoldServiceImpl gServ;
	
	@GetMapping("/primermetodo/{currency}")
	public ResponseEntity <List <Gold>> postMethodName(@PathVariable String currency) {
		
		List<Gold> lista = gServ.loadTimeSerieH1(currency);
		
		return new ResponseEntity<> (lista, HttpStatus.OK);
	}
	
}
