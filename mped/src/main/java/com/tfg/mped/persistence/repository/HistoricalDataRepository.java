package com.tfg.mped.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tfg.mped.persistence.HistoricalData;

/**
 * Repositorio de datos historicos
 * 
 * @author jumaravi
 * 
 */
@Repository
public interface HistoricalDataRepository extends JpaRepository<HistoricalData, Integer>{

	/**
	 * Busca los registros segun el metal y la temporalidad
	 * 
	 * @param metal
	 * @param term
	 * @return List<HistoricalData>
	 */
	List<HistoricalData> findByMetalAndTimeSerieOrderByDatetimeAsc(String metal, String timeSerie);
}
