package com.metro.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.metro.bean.User;
import com.metro.persistence.util.UserRowMapper;

@Repository
public class UserDaoImplementation implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public User getUserByEmail(String email) {
		String query = "select * from user where email = ?";
		
		User user = jdbcTemplate.queryForObject(query, new UserRowMapper(), email);
		
		return user;
	}
	
	@Override
	public boolean userEmailAlreadyInUse(String email) {
		String query = "select email from user where email = ?";
        try {
        	String userEmail = (String) jdbcTemplate.queryForObject(query, String.class, email);
        	if(userEmail == null) return false;
        } catch (EmptyResultDataAccessException e) {
        	return false;
        }

		return true;
	}
	
	@Override
	public boolean addUser(String name, String email, long contact) {
		String query = "insert into user(email, name, contact) values(?, ?, ?)";
		int rows = jdbcTemplate.update(query, email, name, contact);
		
		return (rows > 0); 
	}
}