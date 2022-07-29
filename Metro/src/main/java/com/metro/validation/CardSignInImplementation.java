package com.metro.validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metro.bean.Card;
import com.metro.bean.User;
import com.metro.jdbc.ConnectionUtil;

public class CardSignInImplementation implements CardSignIn {
	private Connection conn = ConnectionUtil.getConnection();
	
	@Override
	public Card signIn(String email, String password) {
		Card card = null;
		ResultSet resultSet = null;
		try(PreparedStatement preparedStatement = conn.prepareStatement("select * from card where email = ? and password = ?")) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int cardId = resultSet.getInt(1);
				String cardEmail = resultSet.getString(2);
				String cardPassword = resultSet.getString(3);
				double cardBalance = resultSet.getDouble(4);
				
				PreparedStatement userPreparedStatement = conn.prepareStatement("select * from user where email = ?");
				userPreparedStatement.setString(1, cardEmail);
				ResultSet userResultSet = userPreparedStatement.executeQuery();
				
				if(!userResultSet.next()) return null;
				
				String userEmail = userResultSet.getString(1);
				String userName = userResultSet.getString(2);
				Long userContact = userResultSet.getLong(3);
				
				User user = new User(userEmail, userName, userContact);
				
				card = new Card(cardId, cardBalance, cardEmail, cardPassword, user);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return card;
	}
}