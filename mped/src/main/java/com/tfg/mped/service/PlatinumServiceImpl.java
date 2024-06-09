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

import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.Platinum;
import com.tfg.mped.persistence.repository.PlatinumRepository;
import com.tfg.mped.persistence.repository.PlatinumRepository;

/**
 * Implementación Servicio de extracción de datos del platino
 * 
 * @author jumaravi
 */

@Service
public class PlatinumServiceImpl implements PlatinumServiceI {

	/** Dependencia: Repositorio del platino */
	private final PlatinumRepository ptRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param gRepo
	 */
	@Autowired
	public PlatinumServiceImpl(PlatinumRepository ptRepo) {

		this.ptRepo = ptRepo;
	}

	@Override
	public List<Platinum> loadTimeSerieH1(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Platinum> timeSerieH1 = new ArrayList<>();
		Platinum platinumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Platinum> platinumList = new ArrayList<>(ptRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < platinumList.size(); i++) {
			Platinum platinumDataArrayInic = platinumList.get(i);
			if (platinumDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < platinumList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Platinum platinumh1 = new Platinum();

			// Recorrer los próximos 6 registros después del objeto con el indice deseado
			for (int in = index; in < index + 7 && in < platinumList.size(); in++) {

				// obtención del objeto
				platinumDataArray = platinumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					platinumh1.setDatetime(platinumDataArray.getDatetime());
					platinumh1.setOpenPrice(platinumDataArray.getOpenPrice());
					platinumh1.setCurrency(platinumDataArray.getCurrency());
					platinumh1.setUnit(platinumDataArray.getUnit());

					if (platinumh1.getHighPrice() == null) {
						platinumh1.setHighPrice(platinumDataArray.getHighPrice());
					}
					if (platinumh1.getLowPrice() == null) {
						platinumh1.setLowPrice(platinumDataArray.getLowPrice());
					}

					if (platinumDataArray.getHighPrice() > platinumh1.getHighPrice()) {
						platinumh1.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumh1.getLowPrice()) {
						platinumh1.setLowPrice(platinumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (platinumDataArray.getHighPrice() > platinumh1.getHighPrice()) {
					platinumh1.setHighPrice(platinumDataArray.getHighPrice());
				}

				if (platinumDataArray.getLowPrice() < platinumh1.getLowPrice()) {
					platinumh1.setLowPrice(platinumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == platinumList.size() - 1) {

					// asignación precio de cierre
					platinumh1.setClosePrice(platinumDataArray.getOpenPrice());

					if (platinumDataArray.getHighPrice() > platinumh1.getHighPrice()) {
						platinumh1.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumh1.getLowPrice()) {
						platinumh1.setLowPrice(platinumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					platinumh1.setId(id);
					timeSerieH1.add(platinumh1);
					isLastIteration = false;
					System.out.println(platinumh1);
				}
			}
			index += 6;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH1;
	}

	@Override
	public List<Platinum> loadTimeSerieH4(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Platinum> timeSerieH4 = new ArrayList<>();
		List<String> validHours = Arrays.asList("00:00", "04:00", "08:00", "12:00", "16:00", "20:00");
		Platinum platinumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Platinum> platinumList = new ArrayList<>(ptRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < platinumList.size(); i++) {
			Platinum platinumDataArrayInic = platinumList.get(i);
			String hourSubstring = platinumDataArrayInic.getDatetime().substring(11, 16);
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
		while (index < platinumList.size()) {

			// Control de las 24 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Platinum platinumh4 = new Platinum();

			// Recorrer los próximos 24 registros después del objeto con el indice deseado
			for (int in = index; in < index + 25 && in < platinumList.size(); in++) {

				// Obtención del objeto
				platinumDataArray = platinumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					platinumh4.setDatetime(platinumDataArray.getDatetime());
					platinumh4.setOpenPrice(platinumDataArray.getOpenPrice());
					platinumh4.setCurrency(platinumDataArray.getCurrency());
					platinumh4.setUnit(platinumDataArray.getUnit());

					if (platinumh4.getHighPrice() == null) {
						platinumh4.setHighPrice(platinumDataArray.getHighPrice());
					}
					if (platinumh4.getLowPrice() == null) {
						platinumh4.setLowPrice(platinumDataArray.getLowPrice());
					}

					if (platinumDataArray.getHighPrice() > platinumh4.getHighPrice()) {
						platinumh4.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumh4.getLowPrice()) {
						platinumh4.setLowPrice(platinumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (platinumDataArray.getHighPrice() > platinumh4.getHighPrice()) {
					platinumh4.setHighPrice(platinumDataArray.getHighPrice());
				}

				if (platinumDataArray.getLowPrice() < platinumh4.getLowPrice()) {
					platinumh4.setLowPrice(platinumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 24 || in == platinumList.size() - 1) {

					// Asignación precio de cierre
					platinumh4.setClosePrice(platinumDataArray.getOpenPrice());

					if (platinumDataArray.getHighPrice() > platinumh4.getHighPrice()) {
						platinumh4.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumh4.getLowPrice()) {
						platinumh4.setLowPrice(platinumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					platinumh4.setId(id);
					timeSerieH4.add(platinumh4);
					isLastIteration = false;
					System.out.println(platinumh4);
				}
			}
			index += 24;
			id++;
		}

		// Resultado del agrupamiento de datos en cada 4 horas
		return timeSerieH4;
	}

	@Override
	public List<Platinum> loadTimeSerieD(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Platinum> timeSerieD = new ArrayList<>();
		Platinum platinumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Platinum> platinumList = new ArrayList<>(ptRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < platinumList.size(); i++) {
			Platinum platinumDataArrayInic = platinumList.get(i);
			if (platinumDataArrayInic.getDatetime().substring(11, 16).equals("00:00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < platinumList.size()) {

			// Control de las 144 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Platinum platinumD = new Platinum();

			// Recorrer los próximos 144 registros después del objeto con el indice deseado
			for (int in = index; in < index + 145 && in < platinumList.size(); in++) {

				// Obtención del objeto
				platinumDataArray = platinumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					platinumD.setDatetime(platinumDataArray.getDatetime());
					platinumD.setOpenPrice(platinumDataArray.getOpenPrice());
					platinumD.setCurrency(platinumDataArray.getCurrency());
					platinumD.setUnit(platinumDataArray.getUnit());

					if (platinumD.getHighPrice() == null) {
						platinumD.setHighPrice(platinumDataArray.getHighPrice());
					}
					if (platinumD.getLowPrice() == null) {
						platinumD.setLowPrice(platinumDataArray.getLowPrice());
					}

					if (platinumDataArray.getHighPrice() > platinumD.getHighPrice()) {
						platinumD.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumD.getLowPrice()) {
						platinumD.setLowPrice(platinumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (platinumDataArray.getHighPrice() > platinumD.getHighPrice()) {
					platinumD.setHighPrice(platinumDataArray.getHighPrice());
				}

				if (platinumDataArray.getLowPrice() < platinumD.getLowPrice()) {
					platinumD.setLowPrice(platinumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 144 || in == platinumList.size() - 1) {

					// Asignación precio de cierre
					platinumD.setClosePrice(platinumDataArray.getOpenPrice());

					if (platinumDataArray.getHighPrice() > platinumD.getHighPrice()) {
						platinumD.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumD.getLowPrice()) {
						platinumD.setLowPrice(platinumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					platinumD.setId(id);
					timeSerieD.add(platinumD);

					System.out.println(platinumD);
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
	public List<Platinum> loadTimeSerieS(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Platinum> timeSerieS = new ArrayList<>();
		Platinum platinumDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Platinum> platinumList = new ArrayList<>(ptRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que es lunes y empieza a las 00:00
		for (int i = 0; i < platinumList.size(); i++) {
			Platinum platinumDataArrayInic = platinumList.get(i);

			// Convertir el datetime de String a LocalDateTime
			LocalDateTime datetime = LocalDateTime.parse(platinumDataArrayInic.getDatetime(), formatter);

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
		while (index < platinumList.size()) {

			// Control de las 720 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Platinum platinumS = new Platinum();

			// Recorrer los próximos 720 registros después del objeto con el indice deseado
			for (int in = index; in < index + 721 && in < platinumList.size(); in++) {

				// Obtención del objeto
				platinumDataArray = platinumList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					platinumS.setDatetime(platinumDataArray.getDatetime());
					platinumS.setOpenPrice(platinumDataArray.getOpenPrice());
					platinumS.setCurrency(platinumDataArray.getCurrency());
					platinumS.setUnit(platinumDataArray.getUnit());

					if (platinumS.getHighPrice() == null) {
						platinumS.setHighPrice(platinumDataArray.getHighPrice());
					}
					if (platinumS.getLowPrice() == null) {
						platinumS.setLowPrice(platinumDataArray.getLowPrice());
					}

					if (platinumDataArray.getHighPrice() > platinumS.getHighPrice()) {
						platinumS.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumS.getLowPrice()) {
						platinumS.setLowPrice(platinumDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (platinumDataArray.getHighPrice() > platinumS.getHighPrice()) {
					platinumS.setHighPrice(platinumDataArray.getHighPrice());
				}

				if (platinumDataArray.getLowPrice() < platinumS.getLowPrice()) {
					platinumS.setLowPrice(platinumDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 720 || in == platinumList.size() - 1) {

					// asignación precio de cierre
					platinumS.setClosePrice(platinumDataArray.getOpenPrice());

					if (platinumDataArray.getHighPrice() > platinumS.getHighPrice()) {
						platinumS.setHighPrice(platinumDataArray.getHighPrice());
					}

					if (platinumDataArray.getLowPrice() < platinumS.getLowPrice()) {
						platinumS.setLowPrice(platinumDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					platinumS.setId(id);
					timeSerieS.add(platinumS);

					System.out.println(platinumS);
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
	public List<Platinum> loadTimeSerieM(String currency) {
		// TODO Auto-generated method stub
		return null;
	}

}
