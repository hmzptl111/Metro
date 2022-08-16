package com.metro.model.service;

import com.metro.bean.Card;

public interface SignInService {
	public Card signIn(String email, String password);
}
