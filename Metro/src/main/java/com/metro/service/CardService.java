package com.metro.service;

import com.metro.bean.Transaction;
import com.metro.bean.User;

public interface CardService {
	static double minimumBalance = 20;
	
	public boolean generateCard(String email, String password, long balance);
	
	public double checkBalance(int cardId);
	
	public boolean updateBalance(int cardId, double amount);
	
	public Transaction getLastTransaction();
}
