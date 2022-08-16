package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.Card;
import com.metro.model.persistence.SignInDao;

@Service
public class SignInServiceImplementation implements SignInService {
	
	@Autowired
	private SignInDao signInDao;

	@Override
	public Card signIn(String email, String password) {
		return signInDao.signIn(email, password);
	}
}