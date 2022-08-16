package com.metro.model.service;

import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;

public interface TransactionService {
	
	public boolean addTransaction(int cardId, MetroStation source);

	boolean updateTransaction(int transactionId, int destinationMetroStationId, double fare);

	Transaction getLastTransaction(int cardId);
}
