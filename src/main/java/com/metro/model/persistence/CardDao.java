package com.metro.model.persistence;

import com.metro.bean.User;

public interface CardDao {
	public boolean generateCard(User user, String password, double balance);

	public double checkBalance(int cardId);

	public boolean updateBalance(int cardId, double amount);

	public boolean deductFare(int cardId, double journeyFare);
}
