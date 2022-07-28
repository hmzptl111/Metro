package com.metro.persistence;

import java.time.LocalDate;
import java.time.LocalTime;

import com.metro.bean.Metro;
import com.metro.bean.Transaction;

public interface TransactionDao {
	public boolean addTransaction(Metro source, Metro destination, LocalDate swipeInDate, LocalTime swipeInTime);
	
	public boolean updateTransaction(int transactionId, LocalDate date, LocalTime time);
}