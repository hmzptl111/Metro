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

	public boolean addTransaction(Transaction t ) {
		int rows = 0;			
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
					PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO transaction(card_id,source_metro_id,destination_metro_id,swipe_in_time,fare_calculated) values(?,?,?,CURRENT_TIMESTAMP,?)");) {

				preparedStatement.setInt(1, t.getCardId());
				preparedStatement.setInt(2, t.getSourceId());
				preparedStatement.setInt(3,t.getDestinationId());
				preparedStatement.setDouble(4, t.getFare());
				

				rows = preparedStatement.executeUpdate();
				conn.close();	

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return (rows>0);}

	@Override
	public boolean updateTransaction(Transaction t) {
		
		int rows = 0;			
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
				PreparedStatement preparedStatement = conn.prepareStatement("UPDATE transaction SET swipe_out_time=CURRENT_TIMESTAMP WHERE id=?");) {

			preparedStatement.setInt(1,  t.getId());
			rows = preparedStatement.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (rows>0);
	}

	@Override
	public Transaction getTransactionBytransactionID(int tid) {
		Transaction transaction = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
				PreparedStatement preparedStatement = conn.prepareStatement("select * from transaction where id = ?");) {
			preparedStatement.setInt(1, tid);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) return null;
			
			int transactionid = resultSet.getInt(1);
			int tcardid = resultSet.getInt(2);
		    int source_metro_id =resultSet.getInt(3);
		    int destination_metro_id =resultSet.getInt(4);
		    Timestamp swipe_in_time =resultSet.getTimestamp(5);
		    Timestamp swipe_out_time=resultSet.getTimestamp(6);
		    double fare_calculated =resultSet.getDouble(7);
		    
			transaction = new Transaction(transactionid, tcardid, source_metro_id,destination_metro_id,swipe_in_time,swipe_out_time,fare_calculated);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transaction;
	}
	@Override
	public Transaction getLastTransaction() {
		Transaction transaction = null;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
				PreparedStatement preparedStatement = conn.prepareStatement("select * from transaction order by id desc limit 1");) {
	
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) return null;
			
			int transactionid = resultSet.getInt(1);
			int tcardid = resultSet.getInt(2);
		    int source_metro_id =resultSet.getInt(3);
		    int destination_metro_id =resultSet.getInt(4);
		    Timestamp swipe_in_time =resultSet.getTimestamp(5);
		    Timestamp swipe_out_time=resultSet.getTimestamp(6);
		    double fare_calculated =resultSet.getDouble(7);
		    
			
			
			transaction = new Transaction(transactionid, tcardid, source_metro_id,destination_metro_id,swipe_in_time,swipe_out_time,fare_calculated);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return transaction;
	}
}