package com.metro.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.model.persistence.TransactionDao;

@Service
public class TransactionServiceImplementation implements TransactionService {
	@Autowired
	TransactionDao transactionDao;

	@Override
	public boolean addTransaction(int cardId, MetroStation sourceMetroStation) {
		int rows = transactionDao.addTransaction(cardId, sourceMetroStation.getId());
		
		return (rows > 0);
	}

	@Override
	public boolean updateTransaction(int transactionId, int destinationMetroStationId, double fare) {
		int rows = transactionDao.updateTransaction(transactionId, destinationMetroStationId, fare);

		return (rows > 0);
	}
	
	@Override
	public Transaction getLastTransaction(int cardId) {
		return transactionDao.getLastTransaction(cardId);
	}
}