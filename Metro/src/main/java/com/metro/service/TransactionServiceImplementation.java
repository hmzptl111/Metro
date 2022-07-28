package com.metro.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.metro.bean.Metro;
import com.metro.bean.Transaction;
import com.metro.persistence.TransactionDaoImplementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();

	@Override
	public boolean addTransaction(Metro source, Metro destination, LocalDate swipeInDate, LocalTime swipeInTime) {
		return tdi.addTransaction(source, destination, swipeInDate, swipeInTime);
	}

	@Override
	public boolean updateTransaction(int transactionId, LocalDate date, LocalTime time) {
		return tdi.updateTransaction(transactionId, date, time);
	}

}
