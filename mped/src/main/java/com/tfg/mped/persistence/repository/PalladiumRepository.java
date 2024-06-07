package com.tfg.mped.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.mped.persistence.Palladium;

/**
 * Repositorio del paladio
 * 
 * @author jumaravi
 * 
 */

@Repository
public interface PalladiumRepository extends JpaRepository<Palladium, Integer> {
	
	/**
	 * Busca un registro por la fecha
	 * 
	 * @param Date
	 * @return Palladium
	 */
	public Palladium findByDatetime(final String date);
	
	/**
	 * Busca una serie de registros por divisa y unidad de medida
	 * 
	 * @param currency
	 * @return List<Palladium>
	 */
	public List<Palladium> findByCurrencyOrderByDatetimeDesc(final String currency);
	
}
