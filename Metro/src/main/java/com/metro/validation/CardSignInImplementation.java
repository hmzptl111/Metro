package com.metro.validation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.metro.bean.Card;
import com.metro.persistence.util.CardRowMapper;

@Component
public class CardSignInImplementation implements CardSignIn {
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Card signIn(String email, String password) {
		String query = "select * from card where email = ? and password = ?";
		Card card = jdbcTemplate.queryForObject(query, new CardRowMapper(), email, password);

		return card;
	}
}