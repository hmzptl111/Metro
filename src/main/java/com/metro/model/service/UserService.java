package com.metro.model.service;

import com.metro.bean.User;

public interface UserService {
	User getUserByEmail(String email);
	
	boolean addUser(String name, String email, long contact);
}
