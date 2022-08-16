	package com.metro.model.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.metro.bean.Transaction;
import com.metro.model.persistence.util.TransactionRowMapper;

@Repository
public class TransactionDaoImplementation implements TransactionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean addTransaction(Transaction transaction) {
		String query = "insert into transaction(card_id, source_metro_id, swipe_in_time) values(?, ?, CURRENT_TIMESTAMP)";
		int rows = 0;
		
		try {
			rows = jdbcTemplate.update(query, transaction.getCardId(), transaction.getSourceId());
		} catch(EmptyResultDataAccessException e) {
			return (rows > 0);
		}

		return (rows > 0);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		String query = "update transaction set destination_metro_id = ?, swipe_out_time = CURRENT_TIMESTAMP, fare_calculated = ? where id = ?";
		int rows = 0;

		try {
			rows = jdbcTemplate.update(query, transaction.getDestinationId(), transaction.getFare(), transaction.getId());
		} catch(EmptyResultDataAccessException e) {
			return (rows > 0);
		}
		
		return (rows > 0);
	}

	@Override
	public Transaction getTransactionBytransactionID(int transactionId) {
		String query = "select * from transaction where id = ?";
		Transaction transaction = null;
		
		try {
			transaction = (Transaction)jdbcTemplate.queryForObject(query, new TransactionRowMapper(), transactionId);
		} catch(EmptyResultDataAccessException e) {
			return transaction;
		}

		return transaction;
	}

	@Override
	public Transaction getLastTransaction(int cardId) {
		String query = "select * from transaction where card_id = ? order by id desc limit 1";
		Transaction transaction = null;
		
		try {
			transaction = (Transaction)jdbcTemplate.queryForObject(query, new TransactionRowMapper(), cardId);
		} catch(EmptyResultDataAccessException e) {
			return transaction;
		}

		return transaction;
	}
}