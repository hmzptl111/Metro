package com.metro.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metro.bean.User;
import com.metro.jdbc.ConnectionUtil;

public class CardDaoImplementation implements CardDao {
	private Connection conn = ConnectionUtil.getConnection();

	@Override
	public boolean updateBalance(int cardId, double amount) {
		//check balance
		int rows = 0;
		double currentCardBalance = checkBalance(cardId);
		double updatedCardBalance = currentCardBalance + amount;
		//update card set balance = updatedCardBalance where card_id = cardId;
		try(PreparedStatement preparedStatement = conn.prepareStatement("update card set balance = ? where id = ?");) {
			preparedStatement.setDouble(1, updatedCardBalance);
			preparedStatement.setInt(2, cardId);
			
			rows = preparedStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return (rows > 0);
	}

	@Override
	public double checkBalance(int cardId) {
		//select balance from card where id = cardId
		double balance = -1;
		
		try(PreparedStatement preparedStatement = conn.prepareStatement("select balance from card where id = ?");) {
			preparedStatement.setInt(1, cardId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				balance = resultSet.getDouble(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return balance;
	}

	@Override
	public boolean generateCard(User user, String password, long balance) {
		int rows = 0;
		try (PreparedStatement preparedStatement = conn.prepareStatement("insert into card(email, password, balance) values(?, ?, ?)");) {
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, password);
			preparedStatement.setLong(3, balance);

			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (rows > 0);
	}
}