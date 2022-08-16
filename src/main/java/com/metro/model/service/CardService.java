package com.metro.model.service;

public interface CardService {
	static double minimumBalance = 20;
	
	public boolean generateCard(String email, String password, double balance);
	
	public double checkBalance(int cardId);
	
	public boolean updateBalance(int cardId, double amount);

	public boolean deductFare(int cardId, double journeyFare);
}