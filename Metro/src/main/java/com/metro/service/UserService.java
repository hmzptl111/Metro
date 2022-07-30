package com.metro.service;

import com.metro.bean.User;

public interface UserService {
	public User getUserByEmail(String email);
	
	public boolean userEmailAlreadyInUse(String email);
	
	public boolean addUser(String name, String email, long contact);
}
