package com.metro.validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metro.bean.Card;
import com.metro.bean.User;


public class CardSignInImplementation implements CardSignIn {

	@Override
	public Card signIn(String email, String password) {
		Card card = null;
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro",  "root", "wiley");
			PreparedStatement preparedStatement = conn.prepareStatement("select * from card where email = ? and password = ?")) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			ResultSet cardResultSet = preparedStatement.executeQuery();
			if(!cardResultSet.next()) return null;
			
			int cardId = cardResultSet.getInt(1);
			String cardEmail = cardResultSet.getString(2);
			String cardPassword = cardResultSet.getString(3);
			double cardBalance = cardResultSet.getDouble(4);
			
			card = new Card(cardId, cardBalance, cardEmail, cardPassword);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return card;
	}
}