package com.metro.model.persistence;

import com.metro.bean.User;

public interface UserDao {
	public User getUserByEmail(String email);
	
	public boolean userEmailAlreadyInUse(String email);
	
	public boolean addUser(String name, String email, long contact);
}
