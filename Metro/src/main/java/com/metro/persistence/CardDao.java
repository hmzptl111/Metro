package com.metro.persistence;

import com.metro.bean.User;

public interface CardDao {
	public boolean generateCard(User user, String password, long balance);
	
	public double checkBalance(int cardId);
	
	public boolean updateBalance(int cardId, double amount);
	
}
