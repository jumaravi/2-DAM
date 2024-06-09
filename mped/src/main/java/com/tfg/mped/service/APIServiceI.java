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
	void addPriceQuote(final String datetime, final String currency, final String unit, final String metal,
			final Double openPrice, final Double highPrice, final Double lowPrice);

	/**
	 * Cotización del oro en dolar americano
	 */
	public void fetchGoldRateByUSD();

	/**
	 * Cotización del cobre en dolar americano
	 */
	public void fetchCopperRateByUSD();

	/**
	 * Cotización del paladio en dolar americano
	 */
	public void fetchPalladiumRateByUSD();

	/**
	 * Cotización del platino en dolar americano
	 */
	public void fetchPlatinumRateByUSD();

	/**
	 * Cotización de la plata en dolar americano
	 */
	public void fetchSilverRateByUSD();

	/**
	 * Cotización del oro en euro
	 */
	public void fetchGoldRateByEUR();

	/**
	 * Cotización del cobre en dolar euro
	 */
	public void fetchCopperRateByEUR();

	/**
	 * Cotización del paladio en euro
	 */
	public void fetchPalladiumRateByEUR();

	/**
	 * Cotización del platino en euro
	 */
	public void fetchPlatinumRateByEUR();

	
	/**
	 * Cotización de la plata en euro
	 */
	public void fetchSilverRateByEUR();

}
