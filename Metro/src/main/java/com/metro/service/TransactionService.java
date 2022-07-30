package com.metro.service;


import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;


public interface TransactionService {
	
	public boolean addTransaction(int cardid, MetroStation source, MetroStation destination);

	boolean updateTransaction(int transactionId);

	Transaction getLastTransaction();
}
