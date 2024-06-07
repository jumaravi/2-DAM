package com.tfg.mped.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tfg.mped.constants.MetalConstants;
import com.tfg.mped.persistence.Copper;
import com.tfg.mped.persistence.Gold;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.repository.CopperRepository;
import com.tfg.mped.persistence.repository.GoldRepository;
import com.tfg.mped.persistence.repository.PalladiumRepository;
import com.tfg.mped.persistence.repository.PlatinumRepository;
import com.tfg.mped.persistence.repository.SilverRepository;
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

	/** Dependencia: Repositorio del cobre */
	private final CopperRepository cRepo;

	/** Dependencia: Repositorio del paladio */
	private final PalladiumRepository plRepo;

	/** Dependencia: Repositorio del platino */
	private final PlatinumRepository ptRepo;

	/** Dependencia: Repositorio de la plata */
	private final SilverRepository sRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 * @param cRepo
	 * @param plRepo
	 * @param ptRepo
	 * @param sRepo
	 */
	@Autowired
	public APIServiceImpl(GoldRepository gRepo, CopperRepository cRepo, PalladiumRepository plRepo, PlatinumRepository ptRepo,
			SilverRepository sRepo) {

		this.gRepo = gRepo;
		this.cRepo = cRepo;
		this.plRepo = plRepo;
		this.ptRepo = ptRepo;
		this.sRepo = sRepo;
	}

	/**
	 * Método que arranca el proceso de automatización de conexiones para cotización
	 * de metales.
	 */
	//@Scheduled(cron = "0 0/10 * * 1-5 *")
	//@Scheduled(cron = "0/10 * * * * *")
	public void fetchMetalRates() {

		fetchGoldRateByUSD();
		fetchCopperRateByUSD();
		fetchPalladiumRateByUSD();
		fetchPlatinumRateByUSD();
		fetchSilverRateByUSD();

		fetchGoldRateByEUR();
		fetchCopperRateByEUR();
		fetchPalladiumRateByEUR();
		fetchPlatinumRateByEUR();
		fetchSilverRateByEUR();
	}

	@Override
	public void fetchGoldRateByUSD() {
		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.GOLDBYUSD);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchCopperRateByUSD() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.COPPERBYUSD);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchPalladiumRateByUSD() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.PALLADIUMBYUSD);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchPlatinumRateByUSD() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.PLATINUMBYUSD);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchSilverRateByUSD() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.SILVERBYUSD);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchGoldRateByEUR() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.GOLDBYEUR);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchCopperRateByEUR() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.COPPERBYEUR);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchPalladiumRateByEUR() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.PALLADIUMBYEUR);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchPlatinumRateByEUR() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.PLATINUMBYEUR);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);
			;

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	@Override
	public void fetchSilverRateByEUR() {

		HttpURLConnection httpuc = null;

		// Conexión a la API, apertura mediante HTTP por URL y propiedad de respuesta en
		// formato JSON
		try {
			URL urlConexion = new URL(APIUrls.SILVERBYEUR);
			httpuc = (HttpURLConnection) urlConexion.openConnection();
			httpuc.setRequestProperty(MetalConstants.PROCESS, MetalConstants.FORMAT);

			// ESTO ES PARA LOG A FUTURO
			System.out.println(httpuc.getResponseCode() + " " + httpuc.getResponseMessage());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar establecer la conexión
			e.printStackTrace();
			return; // Salir del método si la conexión no se puede establecer
		}

		// Lectura respuesta de la API
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpuc.getInputStream()))) {
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

			// Procesamiento del json. Extracción de campos requeridos
			processingJson(response.toString());

		} catch (IOException e) {
			// Manejo de la excepción IOException al intentar leer la respuesta
			e.printStackTrace();
		} finally {
			// Desconexión de la API
			if (httpuc != null) {
				httpuc.disconnect();
			}
		}
	}

	/**
	 * Método para el procesado del JSON proveniente de la API
	 * 
	 * @param json
	 */
	public void processingJson(final String json) {

		// Parseo de la información
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

		// Extraer los campos relevantes del JsonObject.
		String datetime = jsonObject.has("timestamp") ? jsonObject.get("timestamp").getAsString() : null;
		String currency = jsonObject.has("currency") ? jsonObject.get("currency").getAsString() : null;
		String unit = jsonObject.has("unit") ? jsonObject.get("unit").getAsString() : null;
		String metal = jsonObject.has("metal") ? jsonObject.get("metal").getAsString() : null;
		Double openPrice = jsonObject.getAsJsonObject("rate").has("price")
				? jsonObject.getAsJsonObject("rate").get("price").getAsDouble()
				: null;
		Double highPrice = jsonObject.getAsJsonObject("rate").has("high")
				? jsonObject.getAsJsonObject("rate").get("high").getAsDouble()
				: null;
		Double lowPrice = jsonObject.getAsJsonObject("rate").has("low")
				? jsonObject.getAsJsonObject("rate").get("low").getAsDouble()
				: null;

		// LOG PARA LAS VARIABLES
		System.out.println("Timestamp: " + datetime + " Currency: " + currency + " Unit: " + unit + " Metal: " + metal
				+ " Openprice: " + openPrice + " highPrice: " + highPrice + " lowPrice: " + lowPrice);

		// Verificación de campos
		if (datetime != null && currency != null && unit != null && metal != null && openPrice != null
				&& highPrice != null && lowPrice != null) {
			addPriceQuote(datetime, currency, unit, metal, openPrice, highPrice, lowPrice);
		} else {

			// LOG DE ERROR. ALGUN CAMPO ES NULO
			System.out.println("Alguno de los campos es nulo. Realizar alguna otra acción.");
		}
	}

	@Override
	public void addPriceQuote(final String datetime, final String currency, final String unit, final String metal,
			final Double openPrice, final Double highPrice, final Double lowPrice) {

		LocalTime horaActual = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String horaFormateada = horaActual.format(formatter);

		if (metal != null) {

			// conversión a minusculas
			String metalMinuscula = metal.toLowerCase();

			// Comparar con las constantes en minúsculas
			switch (metalMinuscula) {

			case MetalConstants.GOLD:

				// Construcción objeto con diseño builder.
				Gold gold = Gold.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice)
						.lowPrice(lowPrice).currency(currency).unit(unit).build();
				// log para mostrar los datos del objeto + hora del sistema
				gRepo.save(gold);
				// LOG INSERCCION EXITOSA
				break;

			case MetalConstants.SILVER:

				Silver silver = Silver.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice)
						.lowPrice(lowPrice).currency(currency).unit(unit).build();
				// log para mostrar los datos del objeto + hora del sistema
				sRepo.save(silver);
				// LOG INSERCCION EXITOSA
				break;

			case MetalConstants.COPPER:

				Copper copper = Copper.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice)
						.lowPrice(lowPrice).currency(currency).unit(unit).build();
				// log para mostrar los datos del objeto + hora del sistema
				cRepo.save(copper);
				// LOG INSERCCION EXITOSA
				break;

			case MetalConstants.PALLADIUM:

				Palladium palladium = Palladium.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice)
						.lowPrice(lowPrice).currency(currency).unit(unit).build();
				// log para mostrar los datos del objeto + hora del sistema
				plRepo.save(palladium);
				// LOG INSERCCION EXITOSA
				break;

			case MetalConstants.PLATINUM:

				Platinum platinum = Platinum.builder().datetime(datetime).openPrice(openPrice).highPrice(highPrice)
						.lowPrice(lowPrice).currency(currency).unit(unit).build();
				// log para mostrar los datos del objeto + hora del sistema
				ptRepo.save(platinum);
				// LOG INSERCCION EXITOSA
				break;

			default: // LOG PARA DECIR QUE EL METAL NO ESTÁ EN LA LISTA DE VARIABLES- DEVOLVER EL PARÁMETRO
			}

		} else {

			// LOG: NO SE RECONOCIÓ EL METAL. Y DEVOLVER PARÁMETRO.
		}
	}

}
