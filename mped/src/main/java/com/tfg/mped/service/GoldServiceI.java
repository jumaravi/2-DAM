package com.tfg.mped.service;

import java.util.List;
import com.tfg.mped.persistence.Gold;

public interface GoldServiceI {
		
	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Gold>
	 */
		public List<Gold> loadTimeSerieH1 (final String currency);
		
		public List<Gold> loadTimeSerieH4 (final String currency);
		
		public List<Gold> loadTimeSerieD (final String currency);
		
		public List<Gold> loadTimeSerieS (final String currency);
		
		public List<Gold> loadTimeSerieM (final String currency);
}
