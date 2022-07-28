package com.metro.service;

import java.util.List;

import com.metro.bean.Metro;
import com.metro.persistence.MetroDaoImplementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MetroServiceImplementation implements MetroService {
	private MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public double calculateFare(Metro source, Metro destination) {
		return mdi.calculateFare(source, destination);
	}

	@Override
	public List<Metro> fetchMetroStations() {
		return mdi.fetchMetroStations();
	}

}
