package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.User;
import com.metro.model.persistence.CardDao;
import com.metro.model.persistence.UserDao;

@Service
public class CardServiceImplementation implements CardService {
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public double checkBalance(int cardId) {
		return cardDao.checkBalance(cardId);
	}

	@Override
	public boolean updateBalance(int cardId, double amount) {
		if(amount < 0) return false;
		
		int rows = cardDao.updateBalance(cardId, amount);
		
		return (rows > 0);
	}

	@Override
	public boolean generateCard(String email, String password, String name, long contact, double balance) {
		if(balance < 100) return false;
		
		User user = userDao.findByEmail(email);		
		if(user == null && userDao.addUser(email, name, contact) > 0) {
			int rows = cardDao.generateCard(email, password, balance);
			return (rows > 0);
		}
		
		return false;
	}
	
	@Override
	public boolean deductFare(int cardId, double journeyFare) {
		int rows = cardDao.deductFare(cardId,  journeyFare);
		
		return (rows > 0);
	}
}