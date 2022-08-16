package com.metro.model.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.metro.bean.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String email = resultSet.getString("email");
		String name = resultSet.getString("name");
		long contact = resultSet.getLong("contact");
		
		User user = new User(email, name, contact);
		return user;
	}

}
