package com.metro.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.metro.bean.MetroStation;

public class MetroDaoImplementation implements MetroDao {
	
	
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
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
				Statement statement = conn.createStatement()) {

			ResultSet resultSet = statement.executeQuery("select * from metro");
			
//			if(!resultSet.next()) return null;
			while(resultSet.next()) {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			
			MetroStation metro = new MetroStation(id,name);
			listOfMetroStation.add(metro);
			
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfMetroStation;
	}
}