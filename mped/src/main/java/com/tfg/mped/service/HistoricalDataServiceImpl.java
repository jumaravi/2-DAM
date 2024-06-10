package com.tfg.mped.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.mped.persistence.HistoricalData;

import com.tfg.mped.persistence.repository.HistoricalDataRepository;

/**
 * Implementación Servicio de extracción de datos historicos
 * 
 * @author jumaravi
 */
@Service
public class HistoricalDataServiceImpl implements HistoricalDataServiceI {

	/** Dependencia: Repositorio de datos historicos */
	private final HistoricalDataRepository hdRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 */
	@Autowired
	public HistoricalDataServiceImpl(HistoricalDataRepository hdRepo) {

		this.hdRepo = hdRepo;
	}

	@Override
	public List<HistoricalData> loadHistoricalData(String metal, String timeSerie) {

		List<HistoricalData> historicalData = hdRepo.findByMetalAndTimeSerieOrderByDatetimeAsc(metal, timeSerie);
		return historicalData;
	}

}
