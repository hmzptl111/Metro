package com.metro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.persistence.CardDaoImplementation;
import com.metro.persistence.UserDaoImplementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class UserServiceImplementation implements UserService {
	UserDaoImplementation udi = new UserDaoImplementation();
	
	@Autowired
	private UserDaoImplementation userDaoImplementation;
	
	@Autowired
	public void setUserDaoImplementation(UserDaoImplementation userDaoImplementation) {
		this.userDaoImplementation = userDaoImplementation;
	}

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