package com.metro.persistence;

import java.util.ArrayList;
import java.util.List;

import com.metro.bean.MetroStation;

public class MetroDaoImplementation implements MetroDao {
	
	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		//calculate fare and return
		return 20;
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		List<MetroStation> listOfMetroStation = new ArrayList<MetroStation>();
		return listOfMetroStation;
	}
}