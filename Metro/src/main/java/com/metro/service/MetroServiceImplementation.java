package com.metro.service;

import java.util.List;

import com.metro.bean.MetroStation;
import com.metro.persistence.MetroDaoImplementation;

public class MetroServiceImplementation implements MetroService {
	private MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		int factor = Math.abs(source.getId() - destination.getId());
		double fair = factor * 5;
		
		return fair;
	}
	
	@Override
	public MetroStation getMetroStation(int metroStationId) {
		return mdi.getMetroStation(metroStationId);
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		return mdi.fetchMetroStations();
	}
}