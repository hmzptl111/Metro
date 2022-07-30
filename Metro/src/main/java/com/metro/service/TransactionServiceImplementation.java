package com.metro.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.metro.bean.MetroStation;
import com.metro.persistence.TransactionDaoImplementation;

public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();

	@Override
	public boolean addTransaction(MetroStation source, MetroStation destination, LocalDate swipeInDate, LocalTime swipeInTime) {
		return tdi.addTransaction(source, destination, swipeInDate, swipeInTime);
	}

	@Override
	public boolean updateTransaction(int transactionId, LocalDate date, LocalTime time) {
		return tdi.updateTransaction(transactionId, date, time);
	}

}
