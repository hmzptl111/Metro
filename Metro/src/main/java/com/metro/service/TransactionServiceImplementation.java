package com.metro.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.persistence.MetroDaoImplementation;
import com.metro.persistence.TransactionDaoImplementation;

@Service
public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();
	
	@Autowired
	TransactionDaoImplementation transactionDaoImplementation;
	
	@Autowired
	public void setTransactionServiceImplementation(TransactionDaoImplementation transactionDaoImplementation) {
		this.transactionDaoImplementation = transactionDaoImplementation; 
	}

	@Override
	public boolean addTransaction(int cardId, MetroStation source) {
		Transaction transaction = new Transaction();
		transaction.setCardId(cardId);
		transaction.setSourceId(source.getId());
		
		return transactionDaoImplementation.addTransaction(transaction);
	}

	@Override
	public boolean updateTransaction(int transactionId, int destinationMetroStationId, double fare) {
		Transaction transaction = transactionDaoImplementation.getTransactionBytransactionID(transactionId);
		transaction.setDestinationId(destinationMetroStationId);
		transaction.setFare(fare);
		
		return transactionDaoImplementation.updateTransaction(transaction);
	}
	
	@Override
	public Transaction getLastTransaction() {
		return transactionDaoImplementation.getLastTransaction();
	}
}