package com.tfg.mped.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tfg.mped.persistence.Gold;
import com.tfg.mped.persistence.repository.GoldRepository;

/**
 * Implementación Servicio de extracción de datos del oro
 * 
 * @author jumaravi
 */

@Service
public class GoldServiceImpl implements GoldServiceI {

	/** Dependencia: Repositorio del oro */
	private final GoldRepository gRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 */
	@Autowired
	public GoldServiceImpl(GoldRepository gRepo) {

		this.gRepo = gRepo;
	}

	@Override
	public List<Gold> loadTimeSerieH1(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Gold> timeSerieH1 = new ArrayList<>();
		Gold goldDataArray;
		int id = 0;
		Integer index = -1;

		// Resultados de la base de datos
		List<Gold> goldList = new ArrayList<>(gRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < goldList.size(); i++) {
			Gold goldDataArrayInic = goldList.get(i);
			if (goldDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < goldList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Gold goldh1 = new Gold();

			// Recorrer los próximos 6 registros después del objeto con el indice deseado
			for (int in = index; in < index + 7 && in < goldList.size(); in++) {

				// obtención del objeto
				goldDataArray = goldList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					goldh1.setDatetime(goldDataArray.getDatetime());
					goldh1.setOpenPrice(goldDataArray.getOpenPrice());
					goldh1.setCurrency(goldDataArray.getCurrency());
					goldh1.setUnit(goldDataArray.getUnit());

					if (goldh1.getHighPrice() == null) {
						goldh1.setHighPrice(goldDataArray.getHighPrice());
					}
					if (goldh1.getLowPrice() == null) {
						goldh1.setLowPrice(goldDataArray.getLowPrice());
					}

					if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
						goldh1.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldh1.getLowPrice()) {
						goldh1.setLowPrice(goldDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
					goldh1.setHighPrice(goldDataArray.getHighPrice());
				}

				if (goldDataArray.getLowPrice() < goldh1.getLowPrice()) {
					goldh1.setLowPrice(goldDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == goldList.size() - 1) {

					// asignación precio de cierre
					goldh1.setClosePrice(goldDataArray.getOpenPrice());

					if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
						goldh1.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldh1.getLowPrice()) {
						goldh1.setLowPrice(goldDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					goldh1.setId(id);
					timeSerieH1.add(goldh1);
					isLastIteration = false;
					System.out.println(goldh1);
				}
			}
			index += 6;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH1;
	}

	@Override
	public List<Gold> loadTimeSerieH4(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Gold> timeSerieH4 = new ArrayList<>();
		List<String> validHours = Arrays.asList("00:00", "04:00", "08:00", "12:00", "16:00", "20:00");
		Gold goldDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Gold> goldList = new ArrayList<>(gRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < goldList.size(); i++) {
			Gold goldDataArrayInic = goldList.get(i);
			String hourSubstring = goldDataArrayInic.getDatetime().substring(11, 16);
			if (validHours.contains(hourSubstring)) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < goldList.size()) {

			// Control de las 24 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Gold goldh4 = new Gold();

			// Recorrer los próximos 24 registros después del objeto con el indice deseado
			for (int in = index; in < index + 25 && in < goldList.size(); in++) {

				// Obtención del objeto
				goldDataArray = goldList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					goldh4.setDatetime(goldDataArray.getDatetime());
					goldh4.setOpenPrice(goldDataArray.getOpenPrice());
					goldh4.setCurrency(goldDataArray.getCurrency());
					goldh4.setUnit(goldDataArray.getUnit());

					if (goldh4.getHighPrice() == null) {
						goldh4.setHighPrice(goldDataArray.getHighPrice());
					}
					if (goldh4.getLowPrice() == null) {
						goldh4.setLowPrice(goldDataArray.getLowPrice());
					}

					if (goldDataArray.getHighPrice() > goldh4.getHighPrice()) {
						goldh4.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldh4.getLowPrice()) {
						goldh4.setLowPrice(goldDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (goldDataArray.getHighPrice() > goldh4.getHighPrice()) {
					goldh4.setHighPrice(goldDataArray.getHighPrice());
				}

				if (goldDataArray.getLowPrice() < goldh4.getLowPrice()) {
					goldh4.setLowPrice(goldDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 24 || in == goldList.size() - 1) {

					// Asignación precio de cierre
					goldh4.setClosePrice(goldDataArray.getOpenPrice());

					if (goldDataArray.getHighPrice() > goldh4.getHighPrice()) {
						goldh4.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldh4.getLowPrice()) {
						goldh4.setLowPrice(goldDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					goldh4.setId(id);
					timeSerieH4.add(goldh4);
					isLastIteration = false;
					System.out.println(goldh4);
				}
			}
			index += 24;
			id++;
		}

		// Resultado del agrupamiento de datos en cada 4 horas
		return timeSerieH4;
	}

	@Override
	public List<Gold> loadTimeSerieD(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Gold> timeSerieD = new ArrayList<>();
		Gold goldDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Gold> goldList = new ArrayList<>(gRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < goldList.size(); i++) {
			Gold goldDataArrayInic = goldList.get(i);
			if (goldDataArrayInic.getDatetime().substring(11, 16).equals("00:00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < goldList.size()) {

			// Control de las 144 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Gold goldD = new Gold();

			// Recorrer los próximos 144 registros después del objeto con el indice deseado
			for (int in = index; in < index + 145 && in < goldList.size(); in++) {

				// Obtención del objeto
				goldDataArray = goldList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					goldD.setDatetime(goldDataArray.getDatetime());
					goldD.setOpenPrice(goldDataArray.getOpenPrice());
					goldD.setCurrency(goldDataArray.getCurrency());
					goldD.setUnit(goldDataArray.getUnit());

					if (goldD.getHighPrice() == null) {
						goldD.setHighPrice(goldDataArray.getHighPrice());
					}
					if (goldD.getLowPrice() == null) {
						goldD.setLowPrice(goldDataArray.getLowPrice());
					}

					if (goldDataArray.getHighPrice() > goldD.getHighPrice()) {
						goldD.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldD.getLowPrice()) {
						goldD.setLowPrice(goldDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (goldDataArray.getHighPrice() > goldD.getHighPrice()) {
					goldD.setHighPrice(goldDataArray.getHighPrice());
				}

				if (goldDataArray.getLowPrice() < goldD.getLowPrice()) {
					goldD.setLowPrice(goldDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 144 || in == goldList.size() - 1) {

					// Asignación precio de cierre
					goldD.setClosePrice(goldDataArray.getOpenPrice());

					if (goldDataArray.getHighPrice() > goldD.getHighPrice()) {
						goldD.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldD.getLowPrice()) {
						goldD.setLowPrice(goldDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					goldD.setId(id);
					timeSerieD.add(goldD);

					System.out.println(goldD);
					isLastIteration = false;
				}
			}
			index += 144;
			id++;
		}

		// Resultado del agrupamiento de datos en un día
		return timeSerieD;
	}

	@Override
	public List<Gold> loadTimeSerieS(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Gold> timeSerieS = new ArrayList<>();
		Gold goldDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Gold> goldList = new ArrayList<>(gRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que es lunes y empieza a las 00:00
		for (int i = 0; i < goldList.size(); i++) {
			Gold goldDataArrayInic = goldList.get(i);

			// Convertir el datetime de String a LocalDateTime
			LocalDateTime datetime = LocalDateTime.parse(goldDataArrayInic.getDatetime(), formatter);

			// Verificar si es lunes y la hora es 00:00
			if (datetime.getDayOfWeek() == DayOfWeek.MONDAY && datetime.getHour() == 0 && datetime.getMinute() == 0) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < goldList.size()) {

			// Control de las 720 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Gold goldS = new Gold();

			// Recorrer los próximos 720 registros después del objeto con el indice deseado
			for (int in = index; in < index + 721 && in < goldList.size(); in++) {

				// Obtención del objeto
				goldDataArray = goldList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					goldS.setDatetime(goldDataArray.getDatetime());
					goldS.setOpenPrice(goldDataArray.getOpenPrice());
					goldS.setCurrency(goldDataArray.getCurrency());
					goldS.setUnit(goldDataArray.getUnit());

					if (goldS.getHighPrice() == null) {
						goldS.setHighPrice(goldDataArray.getHighPrice());
					}
					if (goldS.getLowPrice() == null) {
						goldS.setLowPrice(goldDataArray.getLowPrice());
					}

					if (goldDataArray.getHighPrice() > goldS.getHighPrice()) {
						goldS.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldS.getLowPrice()) {
						goldS.setLowPrice(goldDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (goldDataArray.getHighPrice() > goldS.getHighPrice()) {
					goldS.setHighPrice(goldDataArray.getHighPrice());
				}

				if (goldDataArray.getLowPrice() < goldS.getLowPrice()) {
					goldS.setLowPrice(goldDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 720 || in == goldList.size() - 1) {

					// asignación precio de cierre
					goldS.setClosePrice(goldDataArray.getOpenPrice());

					if (goldDataArray.getHighPrice() > goldS.getHighPrice()) {
						goldS.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() < goldS.getLowPrice()) {
						goldS.setLowPrice(goldDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					goldS.setId(id);
					timeSerieS.add(goldS);

					System.out.println(goldS);
					isLastIteration = false;
				}
			}
			index += 720;
			id++;
		}

		// Resultado del agrupamiento de datos en una semana
		return timeSerieS;
	}

	

	@Override
	public List<Gold> loadHistoricalTimeSerieD(String currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Gold> loadHistoricalTimeSerieS(String currency) {
		// TODO Auto-generated method stub
		return null;
	}



}

