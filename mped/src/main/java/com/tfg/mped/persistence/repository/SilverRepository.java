package com.tfg.mped.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.mped.persistence.Silver;

/**
 * Repositorio de la plata
 * 
 * @author jumaravi
 * 
 */

@Repository
public interface SilverRepository extends JpaRepository<Silver, Integer> {

	/**
	 * Busca un registro por la fecha
	 * 
	 * @param Date
	 * @return Silver
	 */
	public Silver findByDatetime(final String date);
	
	/**
	 * Busca una serie de registros por divisa y unidad de medida
	 * 
	 * @param currency
	 * @param unit
	 * @return List<Silver>
	 */
	public List<Silver> findByCurrencyAndUnit(final String currency, final String unit);
	
}
