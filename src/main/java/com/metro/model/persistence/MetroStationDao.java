package com.metro.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metro.bean.MetroStation;

@Repository
public interface MetroStationDao extends JpaRepository<MetroStation, Integer> {
	MetroStation findById(int metroStationId);
	
	List<MetroStation> findAllByOrderByIdAsc();
}
