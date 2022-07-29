package com.metro.service;

import com.metro.bean.Transaction;
import com.metro.bean.User;
import com.metro.persistence.CardDaoImplementation;
import com.metro.persistence.UserDaoImplementation;


public class CardServiceImplementation implements CardService {
	private CardDaoImplementation cdi = new CardDaoImplementation();

	@Override
	public double checkBalance(int cardId) {
		return cdi.checkBalance(cardId);
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		if(amount < 0) return false;
		
		return cdi.updateBalance(cardId, amount);
	}

	@Override
	public Transaction getLastTransaction() {
		return cdi.getLastTransaction();
	}

	@Override
	public boolean generateCard(String email, String password, long balance) {
		if(balance < 100) return false;
		
		UserDaoImplementation udi = new UserDaoImplementation();
		User user = udi.getUserByEmail(email);
		if(user != null) {
			return cdi.generateCard(user, password, balance);
		}
		
		return false;
	}
}
