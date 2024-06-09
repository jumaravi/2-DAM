package com.tfg.mped.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tfg.mped.persistence.Platinum;

/**
 * Repositorio del platino
 * 
 * @author jumaravi
 * 
 */

@Repository
public interface PlatinumRepository extends JpaRepository<Platinum, Integer> {

	/**
	 * Busca un registro por la fecha
	 * 
	 * @param Date
	 * @return Platinum
	 */
	public Platinum findByDatetime(final String date);
	
	/**
	 * Busca una serie de registros segun divisa
	 * 
	 * @param currency
	 * @return List<Platinum>
	 */
	public List<Platinum> findByCurrencyOrderByDatetimeAsc(final String currency);
	
}
