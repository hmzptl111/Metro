package com.metro.service;

import com.metro.bean.User;
import com.metro.persistence.UserDaoImplementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserServiceImplementation implements UserService {
	UserDaoImplementation udi = new UserDaoImplementation();

	@Override
	public boolean addUser(String name, String email, long contact) {
		if(!udi.userEmailAlreadyInUse(email)) {
			return udi.addUser(name, email, contact);
		}
		
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		return udi.getUserByEmail(email);
	}

	@Override
	public boolean userEmailAlreadyInUse(String email) {
		return udi.userEmailAlreadyInUse(email);
	}
}