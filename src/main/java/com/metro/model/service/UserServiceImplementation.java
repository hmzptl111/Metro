package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.model.persistence.UserDaoImplementation;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserDaoImplementation userDaoImplementation;

	@Override
	public boolean addUser(String name, String email, long contact) {
		if(!userDaoImplementation.userEmailAlreadyInUse(email)) {
			return userDaoImplementation.addUser(name, email, contact);
		}
		
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		return userDaoImplementation.getUserByEmail(email);
	}

	@Override
	public boolean userEmailAlreadyInUse(String email) {
		return userDaoImplementation.userEmailAlreadyInUse(email);
	}
}