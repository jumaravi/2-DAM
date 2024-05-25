package com.tfg.mped.service;

/**
 * Servicio de extracción de datos de la API
 * 
 * @author jumaravi
 */
public interface APIServiceI {

	/**
	 * Insercción de cotizaciones en función del metal
	 * 
	 * @param metal
	 * @param datetime
	 * @param openPrice
	 * @param highPrice
	 * @param lowPrice
	 * @param currency
	 * @param unit
	 */
	void addPriceQuote(final String datetime, final String currency, final String unit, final String metal, final Double openPrice, final Double highPrice,
			final Double lowPrice);

	public void fetchGoldRateByUSD() throws Exception;
}
