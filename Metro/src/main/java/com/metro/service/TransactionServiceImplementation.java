package com.metro.service;


import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.persistence.MetroDaoImplementation;
import com.metro.persistence.TransactionDaoImplementation;

public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();
	MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public boolean addTransaction(int cardId, MetroStation source) {
		Transaction transaction = new Transaction();
		transaction.setCardId(cardId);
		transaction.setSourceId(source.getId());
		
		return tdi.addTransaction(transaction);
	}

	@Override
	public boolean updateTransaction(int transactionId, int destinationMetroStationId, double fare) {
		Transaction transaction = tdi.getTransactionBytransactionID(transactionId);
		transaction.setDestinationId(destinationMetroStationId);
		transaction.setFare(fare);
		
		return tdi.updateTransaction(transaction);
	}
	
	@Override
	public Transaction getLastTransaction() {
		return tdi.getLastTransaction();
	}
}