package com.metro.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metro.bean.Card;
import com.metro.bean.Transaction;
import com.metro.bean.User;
import com.metro.jdbc.ConnectionUtil;

public class CardDaoImplementation implements CardDao {
	private Connection conn = ConnectionUtil.getConnection();

	@Override
	public Transaction getLastTransaction() {
		//select * from transaction order by id desc limit 1;
		Transaction lastTransaction = new Transaction();
		return lastTransaction;
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		//check balance
		double currentCardBalance = checkBalance(cardId);
		double updatedCardBalance = currentCardBalance + amount;
		//update card set balance = updatedCardBalance where card_id = cardId;
		return true;
	}

	@Override
	public double checkBalance(int cardId) {
		//select balance from card where card_id = cardId
		return 100.00;
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