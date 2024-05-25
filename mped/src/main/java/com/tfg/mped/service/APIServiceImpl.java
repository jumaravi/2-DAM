package com.tfg.mped.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tfg.mped.constants.MetalConstants;
import com.tfg.mped.persistence.Gold;
import com.tfg.mped.persistence.repository.GoldRepository;
import com.tfg.mped.service.url.APIUrls;

/**
 * Implementación Servicio de extracción de datos de la API
 * 
 * @author jumaravi
 */

@Service
public class APIServiceImpl implements APIServiceI {
	
	
	/** Dependencia: Repositorio del oro */
	private final GoldRepository gRepo;
	
	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 */
	@Autowired
	public APIServiceImpl(GoldRepository gRepo) {

		this.gRepo = gRepo;
	}

	

	
	
	@Override
	@Scheduled(fixedRate = 10000)
	public void fetchGoldRateByUSD() throws Exception {
		
		//Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en formato JSON
        URL urlConexion = new URL(APIUrls.GOLDBYUSD);
        HttpURLConnection httpuc = (HttpURLConnection) urlConexion.openConnection();
        httpuc.setRequestProperty("Accept", "application/json");

        //ESTO ES PARA LOG A FUTURO
        System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

        //Lectura respuesta de la API
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            
            //Procesamiento del json. Extracción de campos requeridos
            processingJson(response.toString());
        } finally {
        	
        	//Desconexión de la API
            httpuc.disconnect();
        }
    
    }
	
	/**
	 * Método para el procesado del JSON proveniente de la API
	 * 
	 * @param json
	 */
	public void processingJson(final String json) {
		
		Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

     // Extraer los campos relevantes del JsonObject.
        String datetime = jsonObject.has("timestamp") ? jsonObject.get("timestamp").getAsString() : null;
        String currency = jsonObject.has("currency") ? jsonObject.get("currency").getAsString() : null;
        String unit = jsonObject.has("unit") ? jsonObject.get("unit").getAsString() : null;
        String metal = jsonObject.has("metal") ? jsonObject.get("metal").getAsString() : null;
        Double openPrice = jsonObject.getAsJsonObject("rate").has("price") ? jsonObject.getAsJsonObject("rate").get("price").getAsDouble() : null;
        Double highPrice = jsonObject.getAsJsonObject("rate").has("high") ? jsonObject.getAsJsonObject("rate").get("high").getAsDouble() : null;
        Double lowPrice = jsonObject.getAsJsonObject("rate").has("low") ? jsonObject.getAsJsonObject("rate").get("low").getAsDouble() : null;

     // LOG PARA LAS VARIABLES
        System.out.println("Timestamp: " + datetime +" Currency: " + currency + " Unit: " + unit+ " Metal: " + metal + " Openprice: " + openPrice + " highPrice: " + highPrice + " lowPrice: " + lowPrice);
        
        
     // Verificación de campos
        if (datetime != null && currency != null && unit != null && metal != null && openPrice != null && highPrice != null && lowPrice != null) {
        	addPriceQuote(datetime, currency, unit, metal, openPrice, highPrice, lowPrice);
        } else {
            
        	//LOG DE ERROR. ALGUN CAMPO ES NULO
            System.out.println("Alguno de los campos es nulo. Realizar alguna otra acción.");
        }
	}
	
	
	@Override
	public void addPriceQuote(final String datetime, final String currency, final String unit, final String metal, final Double openPrice, final Double highPrice,
			final Double lowPrice) {

		if (metal != null) {
			
			//conversión a minusculas
			String metalMinuscula = metal.toLowerCase();

			// Comparar con las constantes en minúsculas
			switch (metalMinuscula) { 
			case MetalConstants.GOLD:
				
				System.out.println(" Timestamp: " + datetime +" Currency: " + currency + " Unit: " + unit+ " Metal: " + metal + " Openprice: " + openPrice + " highPrice: " + highPrice + " lowPrice: " + lowPrice);
		        
				//Construcción objeto con diseño builder.
				Gold gold = Gold.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice).lowPrice(lowPrice).currency(currency).unit(unit).build();
				gRepo.save(gold);
				
				//LOG INSERCCION EXITOSA
				break;
			case MetalConstants.SILVER:
				// NOSONAR TODO Lógica para la plata
				break;
			case MetalConstants.COPPER:
				//NOSONAR  TODO Lógica para el cobre
				break;
			case MetalConstants.PALLADIUM:
				//NOSONAR TODO Lógica para el paladio
				break;
			case MetalConstants.PLATINUM:
				//NOSONAR TODO  Lógica para el platino
				break;
				
			default: //TODO log de que no está en las constantes
			}
		

		} else {
		}
	}



	
	
	//aqui habría que hacer un método para extraer los datos que quiero del json
}
