package com.tfg.mped.service;

import java.util.List;

import com.tfg.mped.persistence.Silver;

public interface SilverServiceI {
	
	/**
	 * Extracción de datos en funcion de la temporalidad
	 * 
	 * @param currency
	 * @return List<Silver>
	 */
	public List<Silver> loadTimeSerieH1(final String currency);

	public List<Silver> loadTimeSerieH4(final String currency);

	public List<Silver> loadTimeSerieD(final String currency);

	public List<Silver> loadTimeSerieS(final String currency);

	public List<Silver> loadTimeSerieM(final String currency);
}
