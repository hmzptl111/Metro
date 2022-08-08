package com.metro.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metro.bean.User;

public class UserDaoImplementation implements UserDao {
	
	public User getUserByEmail(String email) {
		User user = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("select * from user where email = ?");) {
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) return null;

			String userEmail = resultSet.getString(1);
			String userName = resultSet.getString(2);
			long userContact = resultSet.getLong(3);
			
			user = new User(userEmail, userName, userContact);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public boolean userEmailAlreadyInUse(String email) {
		ResultSet resultSet = null;
//		String userEmail = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("select email from user where email = ?");) {
			preparedStatement.setString(1, email);

			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) return false;
//			userEmail = resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public boolean addUser(String name, String email, long contact) {
		int rows = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("insert into user(email, name, contact) values(?, ?, ?)");) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, name);
			preparedStatement.setLong(3, contact);

			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rows > 0); 
	}
}