package com.metro.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.metro.bean.Card;

public class CardRowMapper implements RowMapper<Card> {

	@Override
	public Card mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int id = resultSet.getInt("id");
		double balance = resultSet.getDouble("balance");
		String email = resultSet.getString("email");
		String password = resultSet.getString("password");
		
		Card card = new Card(id, balance, email, password);
		return card;
	}

}
