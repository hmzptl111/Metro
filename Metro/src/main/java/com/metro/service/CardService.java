package com.metro.service;

public interface CardService {
	static double minimumBalance = 20;
	
	public boolean generateCard(String email, String password, long balance);
	
	public double checkBalance(int cardId);
	
	public boolean updateBalance(int cardId, double amount);

	public boolean deductfair(int cardId, double journeyFare);
	

}
