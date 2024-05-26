package com.tfg.mped.persistence;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * Tabla del Paladio
 * 
 * @author jumaravi
 */

@Entity
@Table(name = "T_PALLADIUM")
@Data
@Builder
public class Palladium implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** Identificador (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID_PK_PALLADIUM")
	private int id;

	@Column(name = "C_DATETIME", nullable = false)
	private String datetime;

	/** Precio de apertura */
	@Column(name = "C_OPENPRICE", nullable = false)
	private Double openPrice;

	/** Precio maximo */
	@Column(name = "C_HIGHPRICE", nullable = false)
	private Double highPrice;

	/** Precio minimo */
	@Column(name = "C_LOWPRICE", nullable = false)
	private Double lowPrice;

	/** Divisa */
	@Column(name = "C_CURRENCY", nullable = false)
	private String currency;

	/** Unidad */
	@Column(name = "C_UNIT", nullable = false)
	private String unit;
	
}
