package com.metro.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.MetroStation;
import com.metro.model.persistence.MetroStationDao;

@Service
public class MetroStationServiceImplementation implements MetroStationService {
	@Autowired
	private MetroStationDao metroStationDao;
	
	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		int factor = Math.abs(source.getId() - destination.getId());
		double fair = factor * 5;
		
		return fair;
	}
	
	@Override
	public MetroStation getMetroStation(int metroStationId) {
		return metroStationDao.findById(metroStationId);
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		return metroStationDao.findAllByOrderByIdAsc();
	}
}