package com.metro.persistence;

import java.util.List;

import com.metro.bean.MetroStation;

public interface MetroDao {
	public MetroStation getMetroStation(int metroStationId);
	
	public List<MetroStation> fetchMetroStations();
}
