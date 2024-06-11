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
import com.tfg.mped.persistence.Copper;
import com.tfg.mped.persistence.repository.CopperRepository;

/**
 * Implementación Servicio de extracción de datos del cobre
 * 
 * @author jumaravi
 */

@Service
public class CopperServiceImpl implements CopperServiceI {

	/** Dependencia: Repositorio del cobre */
	private final CopperRepository cRepo;

	/**
	 * Constructor sobrecargado
	 * 
	 * @param cRepo
	 */
	@Autowired
	public CopperServiceImpl(CopperRepository cRepo) {

		this.cRepo = cRepo;
	}

	@Override
	public List<Copper> loadTimeSerieH1(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Copper> timeSerieH1 = new ArrayList<>();
		Copper copperDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Copper> copperList = new ArrayList<>(cRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < copperList.size(); i++) {
			Copper copperDataArrayInic = copperList.get(i);
			if (copperDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < copperList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Copper copperh1 = new Copper();

			// Recorrer los próximos 6 registros después del objeto con el indice deseado
			for (int in = index; in < index + 7 && in < copperList.size(); in++) {

				// obtención del objeto
				copperDataArray = copperList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					copperh1.setDatetime(copperDataArray.getDatetime());
					copperh1.setOpenPrice(copperDataArray.getOpenPrice());
					copperh1.setCurrency(copperDataArray.getCurrency());
					copperh1.setUnit(copperDataArray.getUnit());
					copperh1.setClosePrice(copperDataArray.getOpenPrice());

					if (copperh1.getHighPrice() == null) {
						copperh1.setHighPrice(copperDataArray.getHighPrice());
					}
					if (copperh1.getLowPrice() == null) {
						copperh1.setLowPrice(copperDataArray.getLowPrice());
					}

					if (copperDataArray.getHighPrice() > copperh1.getHighPrice()) {
						copperh1.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperh1.getLowPrice()) {
						copperh1.setLowPrice(copperDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (copperDataArray.getHighPrice() > copperh1.getHighPrice()) {
					copperh1.setHighPrice(copperDataArray.getHighPrice());
				}

				if (copperDataArray.getLowPrice() < copperh1.getLowPrice()) {
					copperh1.setLowPrice(copperDataArray.getLowPrice());
				}
				
				copperh1.setClosePrice(copperDataArray.getOpenPrice());
				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == copperList.size() - 1) {

					// asignación precio de cierre
					copperh1.setClosePrice(copperDataArray.getOpenPrice());

					if (copperDataArray.getHighPrice() > copperh1.getHighPrice()) {
						copperh1.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperh1.getLowPrice()) {
						copperh1.setLowPrice(copperDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					copperh1.setId(id);
					timeSerieH1.add(copperh1);
					System.out.println(copperh1);
				}
			}
			index += 6;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH1;
	}

	@Override
	public List<Copper> loadTimeSerieH4(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Copper> timeSerieH4 = new ArrayList<>();
		List<String> validHours = Arrays.asList("00:00", "04:00", "08:00", "12:00", "16:00", "20:00");
		Copper copperDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Copper> copperList = new ArrayList<>(cRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < copperList.size(); i++) {
			Copper copperDataArrayInic = copperList.get(i);
			String hourSubstring = copperDataArrayInic.getDatetime().substring(11, 16);
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
		while (index < copperList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Copper copperh4 = new Copper();

			// Recorrer los próximos 24 registros después del objeto con el indice deseado
			for (int in = index; in < index + 25 && in < copperList.size(); in++) {

				// obtención del objeto
				copperDataArray = copperList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					copperh4.setDatetime(copperDataArray.getDatetime());
					copperh4.setOpenPrice(copperDataArray.getOpenPrice());
					copperh4.setCurrency(copperDataArray.getCurrency());
					copperh4.setUnit(copperDataArray.getUnit());
					copperh4.setClosePrice(copperDataArray.getOpenPrice());

					if (copperh4.getHighPrice() == null) {
						copperh4.setHighPrice(copperDataArray.getHighPrice());
					}
					if (copperh4.getLowPrice() == null) {
						copperh4.setLowPrice(copperDataArray.getLowPrice());
					}

					if (copperDataArray.getHighPrice() > copperh4.getHighPrice()) {
						copperh4.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperh4.getLowPrice()) {
						copperh4.setLowPrice(copperDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				copperh4.setClosePrice(copperDataArray.getOpenPrice());
				
				if (copperDataArray.getHighPrice() > copperh4.getHighPrice()) {
					copperh4.setHighPrice(copperDataArray.getHighPrice());
				}

				if (copperDataArray.getLowPrice() < copperh4.getLowPrice()) {
					copperh4.setLowPrice(copperDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 24 || in == copperList.size() - 1) {

					// asignación precio de cierre
					copperh4.setClosePrice(copperDataArray.getOpenPrice());

					if (copperDataArray.getHighPrice() > copperh4.getHighPrice()) {
						copperh4.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperh4.getLowPrice()) {
						copperh4.setLowPrice(copperDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					copperh4.setId(id);
					timeSerieH4.add(copperh4);
					isLastIteration = false;
					System.out.println(copperh4);
				}
			}
			index += 24;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieH4;
	}

	@Override
	public List<Copper> loadTimeSerieD(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Copper> timeSerieD = new ArrayList<>();
		Copper copperDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Copper> copperList = new ArrayList<>(cRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su índice.
		for (int i = 0; i < copperList.size(); i++) {
			Copper copperDataArrayInic = copperList.get(i);
			if (copperDataArrayInic.getDatetime().substring(11, 16).equals("00:00")) {
				index = i;
				break;
			}
		}

		// Si no se encuentra el registro, se manda una lista vacia
		if (index == -1) {

			return Collections.emptyList();
		}

		// Recorrido de los resultados de la base de datos
		while (index < copperList.size()) {

			// control de las 6 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Copper copperD = new Copper();

			// Recorrer los próximos 144 registros después del objeto con el indice deseado
			for (int in = index; in < index + 145 && in < copperList.size(); in++) {

				// obtención del objeto
				copperDataArray = copperList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// adjudicación de hora y precio apertura
					copperD.setDatetime(copperDataArray.getDatetime());
					copperD.setOpenPrice(copperDataArray.getOpenPrice());
					copperD.setCurrency(copperDataArray.getCurrency());
					copperD.setUnit(copperDataArray.getUnit());
					copperD.setClosePrice(copperDataArray.getOpenPrice());
					
					if (copperD.getHighPrice() == null) {
						copperD.setHighPrice(copperDataArray.getHighPrice());
					}
					if (copperD.getLowPrice() == null) {
						copperD.setLowPrice(copperDataArray.getLowPrice());
					}

					if (copperDataArray.getHighPrice() > copperD.getHighPrice()) {
						copperD.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperD.getLowPrice()) {
						copperD.setLowPrice(copperDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				copperD.setClosePrice(copperDataArray.getOpenPrice());
				
				if (copperDataArray.getHighPrice() > copperD.getHighPrice()) {
					copperD.setHighPrice(copperDataArray.getHighPrice());
				}

				if (copperDataArray.getLowPrice() < copperD.getLowPrice()) {
					copperD.setLowPrice(copperDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 144 || in == copperList.size() - 1) {

					// asignación precio de cierre
					copperD.setClosePrice(copperDataArray.getOpenPrice());

					if (copperDataArray.getHighPrice() > copperD.getHighPrice()) {
						copperD.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperD.getLowPrice()) {
						copperD.setLowPrice(copperDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					copperD.setId(id);
					timeSerieD.add(copperD);

					System.out.println(copperD);
					isLastIteration = false;
				}
			}
			index += 144;
			id++;
		}

		// Resultado del agrupamiento de datos en cada hora
		return timeSerieD;
	}

	@Override
	public List<Copper> loadTimeSerieS(String currency) {

		// Lista donde se almacena la informacion y variables
		List<Copper> timeSerieS = new ArrayList<>();
		Copper copperDataArray;
		int id = 0;
		int index = -1;

		// Resultados de la base de datos
		List<Copper> copperList = new ArrayList<>(cRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que es lunes y empieza a las 00:00
		for (int i = 0; i < copperList.size(); i++) {
			Copper copperDataArrayInic = copperList.get(i);

			// Convertir el datetime de String a LocalDateTime
			LocalDateTime datetime = LocalDateTime.parse(copperDataArrayInic.getDatetime(), formatter);

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
		while (index < copperList.size()) {

			// Control de las 720 iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Copper copperS = new Copper();

			// Recorrer los próximos 720 registros después del objeto con el indice deseado
			for (int in = index; in < index + 721 && in < copperList.size(); in++) {

				// Obtención del objeto
				copperDataArray = copperList.get(in);

				// Realizar acciones específicas solo durante la primera iteración
				if (isFirstIteration) {

					// Adjudicación de hora y precio apertura
					copperS.setDatetime(copperDataArray.getDatetime());
					copperS.setOpenPrice(copperDataArray.getOpenPrice());
					copperS.setCurrency(copperDataArray.getCurrency());
					copperS.setUnit(copperDataArray.getUnit());
					copperS.setClosePrice(copperDataArray.getOpenPrice());
					
					if (copperS.getHighPrice() == null) {
						copperS.setHighPrice(copperDataArray.getHighPrice());
					}
					if (copperS.getLowPrice() == null) {
						copperS.setLowPrice(copperDataArray.getLowPrice());
					}

					if (copperDataArray.getHighPrice() > copperS.getHighPrice()) {
						copperS.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperS.getLowPrice()) {
						copperS.setLowPrice(copperDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				copperS.setClosePrice(copperDataArray.getOpenPrice());
				
				if (copperDataArray.getHighPrice() > copperS.getHighPrice()) {
					copperS.setHighPrice(copperDataArray.getHighPrice());
				}

				if (copperDataArray.getLowPrice() < copperS.getLowPrice()) {
					copperS.setLowPrice(copperDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 720 || in == copperList.size() - 1) {

					// asignación precio de cierre
					copperS.setClosePrice(copperDataArray.getOpenPrice());

					if (copperDataArray.getHighPrice() > copperS.getHighPrice()) {
						copperS.setHighPrice(copperDataArray.getHighPrice());
					}

					if (copperDataArray.getLowPrice() < copperS.getLowPrice()) {
						copperS.setLowPrice(copperDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					copperS.setId(id);
					timeSerieS.add(copperS);

					System.out.println(copperS);
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
