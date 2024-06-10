package com.tfg.mped.service;

import java.util.List;
import com.tfg.mped.persistence.Gold;

/**
 * Servicio de extracción de datos del oro
 * 
 * @author jumaravi
 */

public interface GoldServiceI {

	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Gold>
	 */
	public List<Gold> loadTimeSerieH1(final String currency);

	public List<Gold> loadTimeSerieH4(final String currency);

	public List<Gold> loadTimeSerieD(final String currency);

	public List<Gold> loadTimeSerieS(final String currency);
	
	/**
	 * Extracción de datos historicos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Gold>
	 */
	public List<Gold> loadHistoricalTimeSerieD(final String currency);

	public List<Gold> loadHistoricalTimeSerieS(final String currency);
}
