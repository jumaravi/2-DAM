package com.tfg.mped.service;

import java.util.List;

import com.tfg.mped.persistence.Platinum;

/**
 * Servicio de extracción de datos del oro
 * 
 * @author jumaravi
 */

public interface PlatinumServiceI {
	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Platinum>
	 */
		public List<Platinum> loadTimeSerieH1 (final String currency);
		
		public List<Platinum> loadTimeSerieH4 (final String currency);
		
		public List<Platinum> loadTimeSerieD (final String currency);
		
		public List<Platinum> loadTimeSerieS (final String currency);
		
		public List<Platinum> loadTimeSerieM (final String currency);
}
