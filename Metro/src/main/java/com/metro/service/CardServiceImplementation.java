package com.metro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.persistence.CardDaoImplementation;
import com.metro.persistence.UserDaoImplementation;

@Service
public class CardServiceImplementation implements CardService {
	@Autowired
	private CardDaoImplementation cardDaoImplementation;
	@Autowired
	private UserDaoImplementation userDaoImplementation;
	
	@Autowired
	public void setCardDaoImplementation(CardDaoImplementation cardDaoImplementation) {
		this.cardDaoImplementation = cardDaoImplementation;
	}
	@Autowired
	public void setUserDaoImplementation(UserDaoImplementation userDaoImplementation) {
		this.userDaoImplementation = userDaoImplementation;
	}

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
	public boolean generateCard(String email, String password, long balance) {
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