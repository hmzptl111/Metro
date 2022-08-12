package com.metro.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.metro.bean.MetroStation;
import com.metro.persistence.util.MetroStationRowMapper;

@Repository
public class MetroDaoImplementation implements MetroDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public MetroStation getMetroStation(int metroStationId) {
		String query = "select * from metro where id = ?";
		MetroStation metroStation = jdbcTemplate.queryForObject(query, new MetroStationRowMapper(), metroStationId);

		return metroStation;
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		String query = "select * from metro order by id";
		List<MetroStation> metroStations = jdbcTemplate.query(query, new MetroStationRowMapper());

		return metroStations;
	}
}