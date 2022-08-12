package com.metro.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.metro.bean.MetroStation;

public class MetroStationRowMapper implements RowMapper<MetroStation> {

	@Override
	public MetroStation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		
		MetroStation metroStation = new MetroStation(id, name);
		return metroStation;
	}

}
