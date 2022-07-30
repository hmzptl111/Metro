package com.metro.service;

import com.metro.bean.Metro;

public interface TransactionService {
	
	public boolean addTransaction(int cardid, Metro source, Metro destination);

	boolean updateTransaction(int transactionId);
}
