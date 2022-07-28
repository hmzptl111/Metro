package com.metro.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static String url = "jdbc:mysql://localhost:3306/metro";
	private static String userName = "root";
	private static String password = "wiley";
	static Connection con = null;

	public static Connection getConnection() {

		try {
			con = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;

	}

	public static void closeConnection() {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}