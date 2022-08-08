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
	public MetroStation getMetroStation(int metroStationId) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			Statement statement = conn.createStatement();) {

			ResultSet resultSet = statement.executeQuery("select * from metro where id = " + metroStationId);
			if(!resultSet.next()) return null;
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			
			MetroStation metro = new MetroStation(id, name);
			return metro;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<MetroStation> fetchMetroStations() {
		List<MetroStation> listOfMetroStation = new ArrayList<MetroStation>();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			Statement statement = conn.createStatement();) {

			ResultSet resultSet = statement.executeQuery("select * from metro order by id");
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);

				MetroStation metro = new MetroStation(id, name);
				listOfMetroStation.add(metro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfMetroStation;
	}
}