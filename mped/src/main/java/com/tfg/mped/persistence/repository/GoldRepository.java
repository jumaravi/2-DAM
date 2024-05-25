package com.tfg.mped.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tfg.mped.persistence.Gold;

/**
 * Repositorio del oro
 * 
 * @author jumaravi
 * 
 */

@Repository
public interface GoldRepository extends JpaRepository<Gold, Integer> {

	/**
	 * Busca un registro por la fecha
	 * 
	 * @param Date
	 * @return Gold
	 */
	public Gold findByDatetime(final String date);
	
	/**
	 * Busca una serie de registros por divisa y unidad de medida
	 * 
	 * @param currency
	 * @param unit
	 * @return List<Gold>
	 */
	public List<Gold> findByCurrencyAndUnit(final String currency, final String unit);
	
}
