package com.metro.persistence;

import com.metro.bean.Transaction;


public interface TransactionDao {


	public boolean addTransaction(Transaction t);

	public Transaction getTransactionBytransactionID(int tid);

	public boolean updateTransaction(Transaction t);
	public Transaction getLastTransaction();

}
