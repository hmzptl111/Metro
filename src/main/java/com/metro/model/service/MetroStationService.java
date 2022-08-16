package com.metro.model.service;

import java.util.List;

import com.metro.bean.MetroStation;

public interface MetroStationService {
	public double calculateFare(MetroStation source, MetroStation destination);
	
	public MetroStation getMetroStation(int metroStationId);
	
	public List<MetroStation> fetchMetroStations();
}
