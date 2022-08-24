package com.metro.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.bean.TransactionHistory;
import com.metro.model.persistence.MetroStationDao;
import com.metro.model.persistence.TransactionDao;

@Service
public class TransactionServiceImplementation implements TransactionService {
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	MetroStationDao metroStationDao;

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

	@Override
	public List<TransactionHistory> getTransactionHistory(int cardId) {
		List<TransactionHistory> transactionHistory = new ArrayList<TransactionHistory>();
		List<Transaction> transactions = transactionDao.getTransactionHistory(cardId);
		
		for(Transaction transaction: transactions) {
			MetroStation sourceMetroStation = metroStationDao.findById(transaction.getSourceId()).orElse(new MetroStation());
			MetroStation destinationMetroStation = metroStationDao.findById(transaction.getDestinationId()).orElse(new MetroStation());
			
			TransactionHistory th = new TransactionHistory(transaction.getId(), sourceMetroStation.getName(), destinationMetroStation.getName(), transaction.getSwipeInTime(), transaction.getSwipeOutTime(), transaction.getFare());
			
			transactionHistory.add(th);
		}
		
		return transactionHistory;
	}
}