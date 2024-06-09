package com.tfg.mped.service;

import java.util.List;
import com.tfg.mped.persistence.Copper;

/**
 * Servicio de extracción de datos del oro
 * 
 * @author jumaravi
 */

public interface CopperServiceI {
	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Copper>
	 */
		public List<Copper> loadTimeSerieH1 (final String currency);
		
		public List<Copper> loadTimeSerieH4 (final String currency);
		
		public List<Copper> loadTimeSerieD (final String currency);
		
		public List<Copper> loadTimeSerieS (final String currency);
		
		public List<Copper> loadTimeSerieM (final String currency);
}
