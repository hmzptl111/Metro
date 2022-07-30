package com.metro.persistence;

import java.util.List;

import com.metro.bean.MetroStation;

public interface MetroDao {
	public double calculateFare(MetroStation source, MetroStation destination);
	
	public List<MetroStation> fetchMetroStations();
}
