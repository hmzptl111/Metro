package com.metro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
	private int id;
	private double balance;
	private String email;
	private String password;
//	private User user;
}
