package com.metro.service;

import java.util.List;

import com.metro.bean.Metro;

public interface MetroService {
	public double calculateFare(Metro source, Metro destination);
	
	public List<Metro> fetchMetroStations();
}
