package com.metro.model.persistence;

import com.metro.bean.Card;

public interface SignInDao {
	public Card signIn(String email, String password);
}