package com.metro.model.persistence;

import com.metro.bean.Transaction;

public interface TransactionDao {
	public boolean addTransaction(Transaction transaction);

	public Transaction getTransactionBytransactionID(int transactionId);

	public boolean updateTransaction(Transaction transaction);
	
	public Transaction getLastTransaction(int cardId);
}
