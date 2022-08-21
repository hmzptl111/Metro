package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.model.persistence.UserDao;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(String email, String name, long contact) {
		User user = userDao.findByEmail(email);
		if(user == null) {
			int rows = userDao.addUser(email, name, contact);
			return (rows > 0);
		}
		
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.findByEmail(email);
	}
}