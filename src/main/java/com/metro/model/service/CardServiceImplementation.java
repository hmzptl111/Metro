package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.model.persistence.CardDaoImplementation;
import com.metro.model.persistence.UserDaoImplementation;

@Service
public class CardServiceImplementation implements CardService {
	@Autowired
	private CardDaoImplementation cardDaoImplementation;
	
	@Autowired
	private UserDaoImplementation userDaoImplementation;

	@Override
	public double checkBalance(int cardId) {
		return cardDaoImplementation.checkBalance(cardId);
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		if(amount < 0) return false;
		
		return cardDaoImplementation.updateBalance(cardId, amount);
	}

	@Override
	public boolean generateCard(String email, String password, double balance) {
		if(balance < 100) return false;
		
		User user = userDaoImplementation.getUserByEmail(email);
		if(user != null) {
			return cardDaoImplementation.generateCard(user, password, balance);
		}
		
		return false;
	}
	
	@Override
	public boolean deductFare(int cardId, double journeyFare) {
		return cardDaoImplementation.deductFare(cardId,  journeyFare);
	}
}