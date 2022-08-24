package com.metro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.metro.bean.MetroStation;
import com.metro.model.service.MetroStationService;

@Controller
public class MetroStationController {
	@Autowired
	MetroStationService metroStationService;
	
	
//	======================FETCH ALL METRO STATIONS======================
	@ModelAttribute("metroStations")
	public List<MetroStation> getMetroStations(){
		List<MetroStation> metroStations = metroStationService.fetchMetroStations();
		
		return metroStations;
	}
//	======================FETCH ALL METRO STATIONS======================
}
