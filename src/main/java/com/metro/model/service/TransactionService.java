package com.metro.model.service;

import java.util.List;

import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.bean.TransactionHistory;

public interface TransactionService {
	boolean addTransaction(int cardId, MetroStation source);

	boolean updateTransaction(int transactionId, int destinationMetroStationId, double fare);

	Transaction getLastTransaction(int cardId);
	
	List<TransactionHistory> getTransactionHistory(int cardId);
}
