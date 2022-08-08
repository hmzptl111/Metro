	package com.metro.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.metro.bean.Transaction;

public class TransactionDaoImplementation implements TransactionDao {

	@Override
	public boolean addTransaction(Transaction transaction) {
		int rows = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			PreparedStatement preparedStatement =
			conn.prepareStatement("INSERT INTO transaction(card_id,source_metro_id,swipe_in_time) values(?,?,CURRENT_TIMESTAMP)");) {

			preparedStatement.setInt(1, transaction.getCardId());
			preparedStatement.setInt(2, transaction.getSourceId());

			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (rows > 0);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		int rows = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			PreparedStatement preparedStatement =
			conn.prepareStatement("UPDATE transaction SET destination_metro_id = ?, swipe_out_time = CURRENT_TIMESTAMP, fare_calculated = ? WHERE id=?");) {

			preparedStatement.setInt(1, transaction.getDestinationId());
			preparedStatement.setDouble(2, transaction.getFare());
			preparedStatement.setInt(3, transaction.getId());
			
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (rows > 0);
	}

	@Override
	public Transaction getTransactionBytransactionID(int tid) {
		Transaction transaction = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("select * from transaction where id = ?");) {
			
			preparedStatement.setInt(1, tid);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) return null;

			int transactionId = resultSet.getInt(1);
			int cardId = resultSet.getInt(2);
			int sourceMetroId = resultSet.getInt(3);
			int destinationMetroId = resultSet.getInt(4);
			Timestamp swipeInTime = resultSet.getTimestamp(5);
			Timestamp swipeOutTime = resultSet.getTimestamp(6);
			double fare = resultSet.getDouble(7);

			transaction = new Transaction(transactionId, cardId, sourceMetroId, destinationMetroId, swipeInTime,
					swipeOutTime, fare);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transaction;
	}

	@Override
	public Transaction getLastTransaction() {
		Transaction transaction = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("select * from transaction order by id desc limit 1");) {

			ResultSet resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) return null;

			int transactionId = resultSet.getInt(1);
			int cardId = resultSet.getInt(2);
			int sourceMetroId = resultSet.getInt(3);
			int destinationMetroId = resultSet.getInt(4);
			Timestamp swipeInTime = resultSet.getTimestamp(5);
			Timestamp swipeOutTime = resultSet.getTimestamp(6);
			double fare = resultSet.getDouble(7);

			transaction = new Transaction(transactionId, cardId, sourceMetroId, destinationMetroId, swipeInTime,
					swipeOutTime, fare);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transaction;
	}
}