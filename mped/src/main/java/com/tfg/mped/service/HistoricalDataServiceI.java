package com.tfg.mped.service;

import java.util.List;
import com.tfg.mped.persistence.HistoricalData;

/**
 * Servicio de extracción de datos historicos
 * 
 * @author jumaravi
 */
public interface HistoricalDataServiceI {

	/**
	 * Extracción de datos historicos en funcion del metal y la temporalidad
	 * 
	 * @param currency
	 * @return List<Gold>
	 */
	public List<HistoricalData> loadHistoricalData(final String metal, final String timeSerie);
}
