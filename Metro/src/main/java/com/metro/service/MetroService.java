package com.metro.service;

import java.util.List;

import com.metro.bean.MetroStation;

public interface MetroService {
	public double calculateFare(MetroStation source, MetroStation destination);
	
	public MetroStation getMetroStation(int metroStationId);
	
	public List<MetroStation> fetchMetroStations();
}
