package com.metro.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.metro.bean.MetroStation;
import com.metro.jdbc.ConnectionUtil;

public class MetroDaoImplementation implements MetroDao {
	
	private Connection conn = ConnectionUtil.getConnection();
	@Override
	public double calculateFare(MetroStation source, MetroStation destination) {
		int startingPoint = source.getId();
		int endPoint = destination.getId();
		int factor = startingPoint - endPoint;
		if(factor<0) {
			factor *=-1;
		}
		double fair = factor * 5;
		return fair;
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		List<MetroStation> listOfMetroStation = new ArrayList<MetroStation>();
//		return listOfMetroStation;
//		User user = null;
		try (Statement statement = conn.createStatement()) {

			ResultSet resultSet = statement.executeQuery("select * from metro");
			
//			if(!resultSet.next()) return null;
			while(resultSet.next()) {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			
			MetroStation metro = new MetroStation(id,name);
			listOfMetroStation.add(metro);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfMetroStation;
	}
}