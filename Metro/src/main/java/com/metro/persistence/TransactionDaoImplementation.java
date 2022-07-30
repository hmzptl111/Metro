package com.metro.persistence;

import java.time.LocalDate;
import java.time.LocalTime;

import com.metro.bean.MetroStation;

public class TransactionDaoImplementation implements TransactionDao {

	@Override
	public boolean addTransaction(MetroStation source, MetroStation destination, LocalDate swipeInDate, LocalTime swipeInTime) {
		//add a new record in transaction table
		//if rows returned is > 0, return true, else false
		return true;
	}

	@Override
	public boolean updateTransaction(int transactionId, LocalDate date, LocalTime time) {
		//update transaction
		//set swipe_out_date = date,
		//swipe_out_time = time
		//where id = transactionId;
		return true;
	}
}