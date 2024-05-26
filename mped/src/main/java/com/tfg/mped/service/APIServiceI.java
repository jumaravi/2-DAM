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
	 * 
	 * @throws Exception
	 */
	public void fetchGoldRateByUSD();

	/**
	 * Cotización del cobre en dolar americano
	 * 
	 * @throws Exception
	 */
	public void fetchCopperRateByUSD();

	/**
	 * Cotización del paladio en dolar americano
	 * 
	 * @throws Exception
	 */
	public void fetchPalladiumRateByUSD();

	/**
	 * Cotización del platino en dolar americano
	 * 
	 * @throws Exception
	 */
	public void fetchPlatinumRateByUSD();

	/**
	 * Cotización de la plata en dolar americano
	 * 
	 * @throws Exception
	 */
	public void fetchSilverRateByUSD();

	/**
	 * Cotización del oro en euro
	 * 
	 * @throws Exception
	 */
	public void fetchGoldRateByEUR();

	/**
	 * Cotización del cobre en dolar euro
	 * 
	 * @throws Exception
	 */
	public void fetchCopperRateByEUR();

	/**
	 * Cotización del paladio en euro
	 * 
	 * @throws Exception
	 */
	public void fetchPalladiumRateByEUR();

	/**
	 * Cotización del platino en euro
	 * 
	 * @throws Exception
	 */
	public void fetchPlatinumRateByEUR();

	
	/**
	 * Cotización de la plata en euro
	 * 
	 * @throws Exception
	 */
	public void fetchSilverRateByEUR();

}
