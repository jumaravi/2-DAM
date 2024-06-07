package com.tfg.mped.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.mped.persistence.Gold;
import com.tfg.mped.persistence.repository.GoldRepository;

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

		// Lista donde se almacena la informacion
		List<Gold> timeSerieH1 = new ArrayList<>();
		Gold goldDataArray;
		int id = 0;
		int index = -1;

		List<Gold> goldList = new ArrayList<>(gRepo.findByCurrencyOrderByDatetimeAsc(currency));

		// Recorre todos los resultados de la base de datos hasta encontrar el primero
		// que empieza en hora y obtener su ID e índice.
		for (int i = 0; i < goldList.size(); i++) {
			Gold goldDataArrayInic = goldList.get(i);
			if (goldDataArrayInic.getDatetime().substring(14, 16).equals("00")) {
				index = i;
				break;
			}
		}

		while (index < goldList.size()) {

			// control de iteraciones
			boolean isFirstIteration = true;
			boolean isLastIteration = false;
			Gold goldh1 = new Gold();

			// Recorrer los próximos 6 registros después del objeto con el ID deseado
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
						goldh1.setHighPrice(0.0);
					}
					if (goldh1.getLowPrice() == null) {
						goldh1.setLowPrice(0.0);
					}

					if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
						goldh1.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() > goldh1.getLowPrice()) {
						goldh1.setLowPrice(goldDataArray.getLowPrice());
					}

					// Cambiar el valor de isFirstIteration a falso después de la primera iteración
					isFirstIteration = false;
				}

				if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
					goldh1.setHighPrice(goldDataArray.getHighPrice());
				}

				if (goldDataArray.getLowPrice() > goldh1.getLowPrice()) {
					goldh1.setLowPrice(goldDataArray.getLowPrice());
				}

				// Realizar acciones específicas para la última iteración
				if (in == index + 6 || in == goldList.size() - 1) {

					// asignación precio de cierre
					goldh1.setClosePrice(goldDataArray.getOpenPrice());

					if (goldDataArray.getHighPrice() > goldh1.getHighPrice()) {
						goldh1.setHighPrice(goldDataArray.getHighPrice());
					}

					if (goldDataArray.getLowPrice() > goldh1.getLowPrice()) {
						goldh1.setLowPrice(goldDataArray.getLowPrice());
					}

					isLastIteration = true;
				}
				if (isLastIteration) {
					goldh1.setId(id);
					timeSerieH1.add(goldh1);
					
				}
			}
			index += 6;
			id++;
		}

		return timeSerieH1;
	}

	@Override
	public List<Gold> loadTimeSerieH4(String currency) {
		gRepo.findByCurrencyOrderByDatetimeAsc(currency);
		return null;
	}

	@Override
	public List<Gold> loadTimeSerieD(String currency) {
		gRepo.findByCurrencyOrderByDatetimeAsc(currency);
		return null;
	}

	@Override
	public List<Gold> loadTimeSerieS(String currency) {
		gRepo.findByCurrencyOrderByDatetimeAsc(currency);
		return null;
	}

	@Override
	public List<Gold> loadTimeSerieM(String currency) {
		gRepo.findByCurrencyOrderByDatetimeAsc(currency);
		return null;
	}

}
