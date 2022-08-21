package com.metro.model.service;

import com.metro.bean.Card;

public interface SignInService {
	Card signIn(String email, String password);
}
