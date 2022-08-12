package com.metro.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.metro.bean.Transaction;

public class TransactionRowMapper implements RowMapper<Transaction> {

	@Override
	public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int id = resultSet.getInt("id");
		int cardId = resultSet.getInt("card_id");
		int sourceId = resultSet.getInt("source_metro_id");
		int destinationId = resultSet.getInt("destination_metro_id");
		Timestamp swipeInTime = resultSet.getTimestamp("swipe_in_time");
		Timestamp swipeOutTime = resultSet.getTimestamp("swipe_out_time");
		double fare = resultSet.getDouble("fare_calculated");
		
		Transaction transaction = new Transaction(id, cardId, sourceId, destinationId, swipeInTime, swipeOutTime, fare);
		return transaction;
	}

}
