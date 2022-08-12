	package com.metro.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.metro.bean.Transaction;
import com.metro.persistence.util.TransactionRowMapper;

@Repository
public class TransactionDaoImplementation implements TransactionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean addTransaction(Transaction transaction) {
		String query = "insert into transaction(card_id, source_metro_id, swipe_in_time) values(?, ?, CURRENT_TIMESTAMP)";
		int rows = jdbcTemplate.update(query, transaction.getCardId(), transaction.getSourceId());

		return (rows > 0);
	}

	@Override
	public boolean updateTransaction(Transaction transaction) {
		String query = "update transaction set destination_metro_id = ?, swipe_out_time = CURRENT_TIMESTAMP, fare_calculated = ? where id = ?";
		int rows = jdbcTemplate.update(query, transaction.getDestinationId(), transaction.getFare(), transaction.getId());

		return (rows > 0);
	}

	@Override
	public Transaction getTransactionBytransactionID(int transactionId) {
		String query = "select * from transaction where id = ?";
		Transaction transaction = (Transaction)jdbcTemplate.queryForObject(query, new TransactionRowMapper(), transactionId);

		return transaction;
	}

	@Override
	public Transaction getLastTransaction() {
		String query = "select * from transaction order by id desc limit 1";
		Transaction transaction = (Transaction)jdbcTemplate.queryForObject(query, new TransactionRowMapper());

		return transaction;
	}
}