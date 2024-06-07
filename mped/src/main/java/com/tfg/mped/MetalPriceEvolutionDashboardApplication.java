package com.tfg.mped;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal
 * 
 * @author jumaravi
 */

@SpringBootApplication
@EnableScheduling
public class MetalPriceEvolutionDashboardApplication {


	public static void main(String[] args) {
		SpringApplication.run(MetalPriceEvolutionDashboardApplication.class, args);
	}

	
}
