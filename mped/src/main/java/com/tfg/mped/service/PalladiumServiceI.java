package com.tfg.mped.service;

import java.util.List;

import com.tfg.mped.persistence.Palladium;

/**
 * Servicio de extracción de datos del oro
 * 
 * @author jumaravi
 */

public interface PalladiumServiceI {
	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Palladium>
	 */
		public List<Palladium> loadTimeSerieH1 (final String currency);
		
		public List<Palladium> loadTimeSerieH4 (final String currency);
		
		public List<Palladium> loadTimeSerieD (final String currency);
		
		public List<Palladium> loadTimeSerieS (final String currency);

}
