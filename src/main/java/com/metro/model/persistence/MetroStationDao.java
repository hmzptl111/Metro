package com.metro.model.persistence;

import java.util.List;

import com.metro.bean.MetroStation;

public interface MetroStationDao {
	public MetroStation getMetroStation(int metroStationId);
	
	public List<MetroStation> fetchMetroStations();
}
