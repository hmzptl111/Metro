package com.metro.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.metro.bean.User;

@Repository
public class CardDaoImplementation implements CardDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean updateBalance(int cardId, double amount) {
		String query = "update card set balance = ? where id = ?";
		int rows = jdbcTemplate.update(query, checkBalance(cardId) + amount, cardId);
		
		return (rows > 0);
	}

	@Override
	public double checkBalance(int cardId) {
		String query = "select balance from card where id = ?";
		double balance = jdbcTemplate.queryForObject(query, Double.class, cardId);

		return balance;
	}

	@Override
	public boolean generateCard(User user, String password, long balance) {
		String query = "insert into card(email, password, balance) values(?, ?, ?)";
		int rows = jdbcTemplate.update(query, user.getEmail(), password, balance);
		
		return (rows > 0);
	}
	
	@Override
	public boolean deductFare(int cardId, double journeyFare) {
		String query = "update card set balance = ? where id = ?";
		int rows = jdbcTemplate.update(query, checkBalance(cardId) - journeyFare, cardId);

		return (rows > 0);
	}
}