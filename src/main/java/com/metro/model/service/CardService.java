package com.metro.model.service;

public interface CardService {
	double minimumBalance = 20;
	
	boolean generateCard(String email, String password, String name, long contact, double balance);
	
	double checkBalance(int cardId);
	
	boolean updateBalance(int cardId, double amount);

	boolean deductFare(int cardId, double journeyFare);
}