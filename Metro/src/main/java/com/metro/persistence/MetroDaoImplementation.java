package com.metro.persistence;

import java.util.ArrayList;
import java.util.List;

import com.metro.bean.Metro;

public class MetroDaoImplementation implements MetroDao {
	
	@Override
	public double calculateFare(Metro source, Metro destination) {
		//calculate fare and return
		return 20;
	}

	@Override
	public List<Metro> fetchMetroStations() {
		List<Metro> listOfMetroStation = new ArrayList<Metro>();
		return listOfMetroStation;
	}
}