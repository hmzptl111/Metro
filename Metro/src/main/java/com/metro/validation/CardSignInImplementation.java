package com.metro.validation;

import com.metro.bean.Card;
import com.metro.bean.User;

public class CardSignInImplementation implements CardSignIn {

	@Override
	public Card signIn(String email, String password) {
		//use the email and password provided to fetch card record,
		//create a card instance
		Card card = new Card();
		//use the email to fetch user record
		//set user in card instance and return it
		User user = new User();
		card.setUser(user);
		return card;
	}
}