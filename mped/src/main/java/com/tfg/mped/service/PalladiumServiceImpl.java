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

import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.Palladium;
import com.tfg.mped.persistence.repository.PalladiumRepository;

/**
 * Implementación Servicio de extracción de datos del paladio
 * 
 * @author jumaravi
 */

@Service
public class PalladiumServiceImpl implements PalladiumServiceI {

	/** Dependencia: Repositorio del paladio */
	private final PalladiumRepository plRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param plRepo
	 */
	@Autowired
	public PalladiumServiceImpl(PalladiumRepository plRepo) {

		this.plRepo = plRepo;
	}

	@Override
	public List<Palladium> loadTimeSerieH1(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Palladium> timeSerieH1 = new ArrayList<>();
		Palladium palladiumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Palladium> palladiumList = new ArrayList<>(plRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < palladiumList.size(); i++) {
			Palladium palladiumDataArrayInic = palladiumList.get(i);
			if (palladiumDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < palladiumList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Palladium palladiumh1 = new Palladium();

			// Recorrer los próximos 6 registros después del objeto con el indice deseado
			for (int in = index; in < index + 7 && in < palladiumList.size(); in++) {

				// obtención del objeto
				palladiumDataArray = palladiumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					palladiumh1.setDatetime(palladiumDataArray.getDatetime());
					palladiumh1.setOpenPrice(palladiumDataArray.getOpenPrice());
					palladiumh1.setCurrency(palladiumDataArray.getCurrency());
					palladiumh1.setUnit(palladiumDataArray.getUnit());

					if (palladiumh1.getHighPrice() == null) {
						palladiumh1.setHighPrice(palladiumDataArray.getHighPrice());
					}
					if (palladiumh1.getLowPrice() == null) {
						palladiumh1.setLowPrice(palladiumDataArray.getLowPrice());
					}

					if (palladiumDataArray.getHighPrice() > palladiumh1.getHighPrice()) {
						palladiumh1.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumh1.getLowPrice()) {
						palladiumh1.setLowPrice(palladiumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (palladiumDataArray.getHighPrice() > palladiumh1.getHighPrice()) {
					palladiumh1.setHighPrice(palladiumDataArray.getHighPrice());
				}

				if (palladiumDataArray.getLowPrice() < palladiumh1.getLowPrice()) {
					palladiumh1.setLowPrice(palladiumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == palladiumList.size() - 1) {

					// asignación precio de cierre
					palladiumh1.setClosePrice(palladiumDataArray.getOpenPrice());

					if (palladiumDataArray.getHighPrice() > palladiumh1.getHighPrice()) {
						palladiumh1.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumh1.getLowPrice()) {
						palladiumh1.setLowPrice(palladiumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					palladiumh1.setId(id);
					timeSerieH1.add(palladiumh1);
					isLastIteration = false;
					System.out.println(palladiumh1);
				}
			}
			index += 6;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH1;
	}

	@Override
	public List<Palladium> loadTimeSerieH4(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Palladium> timeSerieH4 = new ArrayList<>();
		List<String> validHours = Arrays.asList("00:00", "04:00", "08:00", "12:00", "16:00", "20:00");
		Palladium palladiumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Palladium> palladiumList = new ArrayList<>(plRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < palladiumList.size(); i++) {
			Palladium palladiumDataArrayInic = palladiumList.get(i);
			String hourSubstring = palladiumDataArrayInic.getDatetime().substring(11, 16);
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
		while (index < palladiumList.size()) {

			// Control de las 24 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Palladium palladiumh4 = new Palladium();

			// Recorrer los próximos 24 registros después del objeto con el indice deseado
			for (int in = index; in < index + 25 && in < palladiumList.size(); in++) {

				// Obtención del objeto
				palladiumDataArray = palladiumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					palladiumh4.setDatetime(palladiumDataArray.getDatetime());
					palladiumh4.setOpenPrice(palladiumDataArray.getOpenPrice());
					palladiumh4.setCurrency(palladiumDataArray.getCurrency());
					palladiumh4.setUnit(palladiumDataArray.getUnit());

					if (palladiumh4.getHighPrice() == null) {
						palladiumh4.setHighPrice(palladiumDataArray.getHighPrice());
					}
					if (palladiumh4.getLowPrice() == null) {
						palladiumh4.setLowPrice(palladiumDataArray.getLowPrice());
					}

					if (palladiumDataArray.getHighPrice() > palladiumh4.getHighPrice()) {
						palladiumh4.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumh4.getLowPrice()) {
						palladiumh4.setLowPrice(palladiumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (palladiumDataArray.getHighPrice() > palladiumh4.getHighPrice()) {
					palladiumh4.setHighPrice(palladiumDataArray.getHighPrice());
				}

				if (palladiumDataArray.getLowPrice() < palladiumh4.getLowPrice()) {
					palladiumh4.setLowPrice(palladiumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 24 || in == palladiumList.size() - 1) {

					// Asignación precio de cierre
					palladiumh4.setClosePrice(palladiumDataArray.getOpenPrice());

					if (palladiumDataArray.getHighPrice() > palladiumh4.getHighPrice()) {
						palladiumh4.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumh4.getLowPrice()) {
						palladiumh4.setLowPrice(palladiumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					palladiumh4.setId(id);
					timeSerieH4.add(palladiumh4);
					isLastIteration = false;
					System.out.println(palladiumh4);
				}
			}
			index += 24;
			id++;
		}

		// Resultado del agrupamiento de datos en cada 4 horas
		return timeSerieH4;
	}

	@Override
	public List<Palladium> loadTimeSerieD(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Palladium> timeSerieD = new ArrayList<>();
		Palladium palladiumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Palladium> palladiumList = new ArrayList<>(plRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < palladiumList.size(); i++) {
			Palladium palladiumDataArrayInic = palladiumList.get(i);
			if (palladiumDataArrayInic.getDatetime().substring(11, 16).equals("00:00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < palladiumList.size()) {

			// Control de las 144 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Palladium palladiumD = new Palladium();

			// Recorrer los próximos 144 registros después del objeto con el indice deseado
			for (int in = index; in < index + 145 && in < palladiumList.size(); in++) {

				// Obtención del objeto
				palladiumDataArray = palladiumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					palladiumD.setDatetime(palladiumDataArray.getDatetime());
					palladiumD.setOpenPrice(palladiumDataArray.getOpenPrice());
					palladiumD.setCurrency(palladiumDataArray.getCurrency());
					palladiumD.setUnit(palladiumDataArray.getUnit());

					if (palladiumD.getHighPrice() == null) {
						palladiumD.setHighPrice(palladiumDataArray.getHighPrice());
					}
					if (palladiumD.getLowPrice() == null) {
						palladiumD.setLowPrice(palladiumDataArray.getLowPrice());
					}

					if (palladiumDataArray.getHighPrice() > palladiumD.getHighPrice()) {
						palladiumD.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumD.getLowPrice()) {
						palladiumD.setLowPrice(palladiumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (palladiumDataArray.getHighPrice() > palladiumD.getHighPrice()) {
					palladiumD.setHighPrice(palladiumDataArray.getHighPrice());
				}

				if (palladiumDataArray.getLowPrice() < palladiumD.getLowPrice()) {
					palladiumD.setLowPrice(palladiumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 144 || in == palladiumList.size() - 1) {

					// Asignación precio de cierre
					palladiumD.setClosePrice(palladiumDataArray.getOpenPrice());

					if (palladiumDataArray.getHighPrice() > palladiumD.getHighPrice()) {
						palladiumD.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumD.getLowPrice()) {
						palladiumD.setLowPrice(palladiumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					palladiumD.setId(id);
					timeSerieD.add(palladiumD);

					System.out.println(palladiumD);
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
	public List<Palladium> loadTimeSerieS(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Palladium> timeSerieS = new ArrayList<>();
		Palladium palladiumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Palladium> palladiumList = new ArrayList<>(plRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que es lunes y empieza a las 00:00
		for (int i = 0; i < palladiumList.size(); i++) {
			Palladium palladiumDataArrayInic = palladiumList.get(i);

			// Convertir el datetime de String a LocalDateTime
			LocalDateTime datetime = LocalDateTime.parse(palladiumDataArrayInic.getDatetime(), formatter);

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
		while (index < palladiumList.size()) {

			// Control de las 720 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Palladium palladiumS = new Palladium();

			// Recorrer los próximos 720 registros después del objeto con el indice deseado
			for (int in = index; in < index + 721 && in < palladiumList.size(); in++) {

				// Obtención del objeto
				palladiumDataArray = palladiumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					palladiumS.setDatetime(palladiumDataArray.getDatetime());
					palladiumS.setOpenPrice(palladiumDataArray.getOpenPrice());
					palladiumS.setCurrency(palladiumDataArray.getCurrency());
					palladiumS.setUnit(palladiumDataArray.getUnit());

					if (palladiumS.getHighPrice() == null) {
						palladiumS.setHighPrice(palladiumDataArray.getHighPrice());
					}
					if (palladiumS.getLowPrice() == null) {
						palladiumS.setLowPrice(palladiumDataArray.getLowPrice());
					}

					if (palladiumDataArray.getHighPrice() > palladiumS.getHighPrice()) {
						palladiumS.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumS.getLowPrice()) {
						palladiumS.setLowPrice(palladiumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (palladiumDataArray.getHighPrice() > palladiumS.getHighPrice()) {
					palladiumS.setHighPrice(palladiumDataArray.getHighPrice());
				}

				if (palladiumDataArray.getLowPrice() < palladiumS.getLowPrice()) {
					palladiumS.setLowPrice(palladiumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 720 || in == palladiumList.size() - 1) {

					// asignación precio de cierre
					palladiumS.setClosePrice(palladiumDataArray.getOpenPrice());

					if (palladiumDataArray.getHighPrice() > palladiumS.getHighPrice()) {
						palladiumS.setHighPrice(palladiumDataArray.getHighPrice());
					}

					if (palladiumDataArray.getLowPrice() < palladiumS.getLowPrice()) {
						palladiumS.setLowPrice(palladiumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					palladiumS.setId(id);
					timeSerieS.add(palladiumS);

					System.out.println(palladiumS);
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
	public List<Palladium> loadTimeSerieM(String currency) {
		// TODO Auto-generated method stub
		return null;
	}

}
