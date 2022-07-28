package com.metro.persistence;

import java.util.List;

import com.metro.bean.Metro;

public interface MetroDao {
	public double calculateFare(Metro source, Metro destination);
	
	public List<Metro> fetchMetroStations();
}
