package com.tfg.mped.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.Silver;
import com.tfg.mped.persistence.repository.SilverRepository;

/**
 * Implementación Servicio de extracción de datos de la plata
 * 
 * @author jumaravi
 */

@Service
public class SilverServiceImpl implements SilverServiceI {

	/** Dependencia: Repositorio del plata */
	private final SilverRepository sRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 */
	@Autowired
	public SilverServiceImpl(SilverRepository sRepo) {

		this.sRepo = sRepo;
	}

	@Override
	public List<Silver> loadTimeSerieH1(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Silver> timeSerieH1 = new ArrayList<>();
		Silver silverDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Silver> silverList = new ArrayList<>(sRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < silverList.size(); i++) {
			Silver silverDataArrayInic = silverList.get(i);
			if (silverDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < silverList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Silver silverh1 = new Silver();

			// Recorrer los próximos 6 registros después del objeto con el indice deseado
			for (int in = index; in < index + 7 && in < silverList.size(); in++) {

				// obtención del objeto
				silverDataArray = silverList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					silverh1.setDatetime(silverDataArray.getDatetime());
					silverh1.setOpenPrice(silverDataArray.getOpenPrice());
					silverh1.setCurrency(silverDataArray.getCurrency());
					silverh1.setUnit(silverDataArray.getUnit());
					silverh1.setClosePrice(silverDataArray.getOpenPrice());
					
					if (silverh1.getHighPrice() == null) {
						silverh1.setHighPrice(silverDataArray.getHighPrice());
					}
					if (silverh1.getLowPrice() == null) {
						silverh1.setLowPrice(silverDataArray.getLowPrice());
					}

					if (silverDataArray.getHighPrice() > silverh1.getHighPrice()) {
						silverh1.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverh1.getLowPrice()) {
						silverh1.setLowPrice(silverDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}
				
				silverh1.setClosePrice(silverDataArray.getOpenPrice());

				if (silverDataArray.getHighPrice() > silverh1.getHighPrice()) {
					silverh1.setHighPrice(silverDataArray.getHighPrice());
				}

				if (silverDataArray.getLowPrice() < silverh1.getLowPrice()) {
					silverh1.setLowPrice(silverDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == silverList.size() - 1) {

					// asignación precio de cierre
					silverh1.setClosePrice(silverDataArray.getOpenPrice());

					if (silverDataArray.getHighPrice() > silverh1.getHighPrice()) {
						silverh1.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverh1.getLowPrice()) {
						silverh1.setLowPrice(silverDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					silverh1.setId(id);
					timeSerieH1.add(silverh1);
					isLastIteration = false;
					System.out.println(silverh1);
				}
			}
			index += 6;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH1;
	}

	@Override
	public List<Silver> loadTimeSerieH4(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Silver> timeSerieH4 = new ArrayList<>();
		List<String> validHours = Arrays.asList("00:00", "04:00", "08:00", "12:00", "16:00", "20:00");
		Silver silverDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Silver> silverList = new ArrayList<>(sRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < silverList.size(); i++) {
			Silver silverDataArrayInic = silverList.get(i);
			String hourSubstring = silverDataArrayInic.getDatetime().substring(11, 16);
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
		while (index < silverList.size()) {

			// Control de las 24 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Silver silverh4 = new Silver();

			// Recorrer los próximos 24 registros después del objeto con el indice deseado
			for (int in = index; in < index + 25 && in < silverList.size(); in++) {

				// Obtención del objeto
				silverDataArray = silverList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					silverh4.setDatetime(silverDataArray.getDatetime());
					silverh4.setOpenPrice(silverDataArray.getOpenPrice());
					silverh4.setCurrency(silverDataArray.getCurrency());
					silverh4.setUnit(silverDataArray.getUnit());
					silverh4.setClosePrice(silverDataArray.getOpenPrice());
					
					if (silverh4.getHighPrice() == null) {
						silverh4.setHighPrice(silverDataArray.getHighPrice());
					}
					if (silverh4.getLowPrice() == null) {
						silverh4.setLowPrice(silverDataArray.getLowPrice());
					}

					if (silverDataArray.getHighPrice() > silverh4.getHighPrice()) {
						silverh4.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverh4.getLowPrice()) {
						silverh4.setLowPrice(silverDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				silverh4.setClosePrice(silverDataArray.getOpenPrice());
				
				if (silverDataArray.getHighPrice() > silverh4.getHighPrice()) {
					silverh4.setHighPrice(silverDataArray.getHighPrice());
				}

				if (silverDataArray.getLowPrice() < silverh4.getLowPrice()) {
					silverh4.setLowPrice(silverDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 24 || in == silverList.size() - 1) {

					// Asignación precio de cierre
					silverh4.setClosePrice(silverDataArray.getOpenPrice());

					if (silverDataArray.getHighPrice() > silverh4.getHighPrice()) {
						silverh4.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverh4.getLowPrice()) {
						silverh4.setLowPrice(silverDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					silverh4.setId(id);
					timeSerieH4.add(silverh4);
					isLastIteration = false;
					System.out.println(silverh4);
				}
			}
			index += 24;
			id++;
		}

		// Resultado del agrupamiento de datos en cada 4 horas
		return timeSerieH4;
	}

	@Override
	public List<Silver> loadTimeSerieD(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Silver> timeSerieD = new ArrayList<>();
		Silver silverDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Silver> silverList = new ArrayList<>(sRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < silverList.size(); i++) {
			Silver silverDataArrayInic = silverList.get(i);
			if (silverDataArrayInic.getDatetime().substring(11, 16).equals("00:00")) {
				index = i;
				break;
			}
		}
		
		// Si no se encuentra el registro, se manda una lista vacia
				if (index == -1) {

					return Collections.emptyList();
				}

		// Recorrido de los resultados de la base de datos
		while (index < silverList.size()) {

			// Control de las 144 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Silver silverD = new Silver();

			// Recorrer los próximos 144 registros después del objeto con el indice deseado
			for (int in = index; in < index + 145 && in < silverList.size(); in++) {

				// Obtención del objeto
				silverDataArray = silverList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					silverD.setDatetime(silverDataArray.getDatetime());
					silverD.setOpenPrice(silverDataArray.getOpenPrice());
					silverD.setCurrency(silverDataArray.getCurrency());
					silverD.setUnit(silverDataArray.getUnit());
					silverD.setClosePrice(silverDataArray.getOpenPrice());
					
					if (silverD.getHighPrice() == null) {
						silverD.setHighPrice(silverDataArray.getHighPrice());
					}
					if (silverD.getLowPrice() == null) {
						silverD.setLowPrice(silverDataArray.getLowPrice());
					}

					if (silverDataArray.getHighPrice() > silverD.getHighPrice()) {
						silverD.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverD.getLowPrice()) {
						silverD.setLowPrice(silverDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				silverD.setClosePrice(silverDataArray.getOpenPrice());
				
				if (silverDataArray.getHighPrice() > silverD.getHighPrice()) {
					silverD.setHighPrice(silverDataArray.getHighPrice());
				}

				if (silverDataArray.getLowPrice() < silverD.getLowPrice()) {
					silverD.setLowPrice(silverDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 144 || in == silverList.size() - 1) {

					// Asignación precio de cierre
					silverD.setClosePrice(silverDataArray.getOpenPrice());

					if (silverDataArray.getHighPrice() > silverD.getHighPrice()) {
						silverD.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverD.getLowPrice()) {
						silverD.setLowPrice(silverDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					silverD.setId(id);
					timeSerieD.add(silverD);

					System.out.println(silverD);
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
	public List<Silver> loadTimeSerieS(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Silver> timeSerieS = new ArrayList<>();
		Silver silverDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Silver> silverList = new ArrayList<>(sRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que es lunes y empieza a las 00:00
		for (int i = 0; i < silverList.size(); i++) {
			Silver silverDataArrayInic = silverList.get(i);

			// Convertir el datetime de String a LocalDateTime
			LocalDateTime datetime = LocalDateTime.parse(silverDataArrayInic.getDatetime(), formatter);

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
		while (index < silverList.size()) {

			// Control de las 720 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Silver silverS = new Silver();

			// Recorrer los próximos 720 registros después del objeto con el indice deseado
			for (int in = index; in < index + 721 && in < silverList.size(); in++) {

				// Obtención del objeto
				silverDataArray = silverList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					silverS.setDatetime(silverDataArray.getDatetime());
					silverS.setOpenPrice(silverDataArray.getOpenPrice());
					silverS.setCurrency(silverDataArray.getCurrency());
					silverS.setUnit(silverDataArray.getUnit());
					silverS.setClosePrice(silverDataArray.getOpenPrice());

					if (silverS.getHighPrice() == null) {
						silverS.setHighPrice(silverDataArray.getHighPrice());
					}
					if (silverS.getLowPrice() == null) {
						silverS.setLowPrice(silverDataArray.getLowPrice());
					}

					if (silverDataArray.getHighPrice() > silverS.getHighPrice()) {
						silverS.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverS.getLowPrice()) {
						silverS.setLowPrice(silverDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				silverS.setClosePrice(silverDataArray.getOpenPrice());

				
				if (silverDataArray.getHighPrice() > silverS.getHighPrice()) {
					silverS.setHighPrice(silverDataArray.getHighPrice());
				}

				if (silverDataArray.getLowPrice() < silverS.getLowPrice()) {
					silverS.setLowPrice(silverDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 720 || in == silverList.size() - 1) {

					// asignación precio de cierre
					silverS.setClosePrice(silverDataArray.getOpenPrice());

					if (silverDataArray.getHighPrice() > silverS.getHighPrice()) {
						silverS.setHighPrice(silverDataArray.getHighPrice());
					}

					if (silverDataArray.getLowPrice() < silverS.getLowPrice()) {
						silverS.setLowPrice(silverDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					silverS.setId(id);
					timeSerieS.add(silverS);

					System.out.println(silverS);
					isLastIteration = false;
				}
			}
			index += 720;
			id++;
		}

		// Resultado del agrupamiento de datos en una semana
		return timeSerieS;
	}
}
