package com.metro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.MetroStation;
import com.metro.persistence.MetroDaoImplementation;

@Service
public class MetroServiceImplementation implements MetroService {
	private MetroDaoImplementation mdi = new MetroDaoImplementation();
	
	@Autowired
	private MetroDaoImplementation metroDaoImplementation;

	@Autowired
	public void setMetroDaoImplementation(MetroDaoImplementation metroDaoImplementation) {
		this.metroDaoImplementation = metroDaoImplementation;
	}
	
	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		int factor = Math.abs(source.getId() - destination.getId());
		double fair = factor * 5;
		
		return fair;
	}
	
	@Override
	public MetroStation getMetroStation(int metroStationId) {
		return metroDaoImplementation.getMetroStation(metroStationId);
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		return metroDaoImplementation.fetchMetroStations();
	}
}