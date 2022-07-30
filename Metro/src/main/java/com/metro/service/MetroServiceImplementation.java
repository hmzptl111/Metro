package com.metro.service;

import java.util.List;

import com.metro.bean.MetroStation;
import com.metro.persistence.MetroDaoImplementation;

public class MetroServiceImplementation implements MetroService {
	private MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		return mdi.calculateFare(source, destination);
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		return mdi.fetchMetroStations();
	}

}
