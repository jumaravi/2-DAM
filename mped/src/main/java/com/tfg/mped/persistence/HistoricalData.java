package com.tfg.mped.persistence;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tabla de datos historicos genericos
 * 
 * @author jumaravi
 */

@Entity
@Table(name = "T_HISTORICALDATA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalData implements Serializable{

	
	/** Serial Version */
	private static final long serialVersionUID = 1L;
	
	/** Identificador (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID_PK_HISTORICALDATA")
	private int id;
	
	@Column(name = "C_METAL", nullable = false)
	private String metal;
	
	@Column(name = "C_DATETIME", nullable = false)
	private String datetime;

	/** Precio de apertura */
	@Column(name = "C_OPENPRICE", nullable = false)
	private Double openPrice;
	
	/** Precio de cierre */
	@Column(name = "C_CLOSEPRICE", nullable = false)
	private Double closePrice;

	/** Precio maximo */
	@Column(name = "C_HIGHPRICE", nullable = false)
	private Double highPrice;

	/** Precio minimo */
	@Column(name = "C_LOWPRICE", nullable = false)
	private Double lowPrice;
	
	/** Temporalidad */
	@Column(name = "C_TIMESERIE", nullable = false)
	private String timeSerie;
}
