package com.metro.service;

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
}