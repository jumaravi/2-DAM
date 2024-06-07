package com.tfg.mped.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tfg.mped.persistence.Copper;



/**
 * Repositorio del cobre
 * 
 * @author jumaravi
 * 
 */

@Repository
public interface CopperRepository extends JpaRepository<Copper, Integer>{

	/**
	 * Busca un registro por la fecha
	 * 
	 * @param Date
	 * @return Copper
	 */
	public Copper findByDatetime(final String date);
	
	/**
	 * Busca una serie de registros por divisa
	 * 
	 * @param currency
	 * @return List<Copper>
	 */
	public List<Copper> findByCurrencyOrderByDatetimeDesc(final String currency);
}
