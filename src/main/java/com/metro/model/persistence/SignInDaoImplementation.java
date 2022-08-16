package com.metro.model.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.metro.bean.Card;
import com.metro.model.persistence.util.CardRowMapper;

@Repository
public class SignInDaoImplementation implements SignInDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Card signIn(String email, String password) {
		Card card = null;
		try {
			String query = "select * from card where email = ? and password = ?";
			card = jdbcTemplate.queryForObject(query, new CardRowMapper(), email, password);
		} catch(EmptyResultDataAccessException e) {
			return card;
		}
		
		return card;
	}
}