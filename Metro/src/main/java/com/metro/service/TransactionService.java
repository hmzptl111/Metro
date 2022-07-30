package com.metro.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.metro.bean.MetroStation;

public interface TransactionService {
	public boolean addTransaction(MetroStation source, MetroStation destination, LocalDate swipeInDate, LocalTime swipeInTime);
	
	public boolean updateTransaction(int transactionId, LocalDate date, LocalTime time);
}
