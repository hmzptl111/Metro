package com.metro.service;


import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.persistence.MetroDaoImplementation;
import com.metro.persistence.TransactionDaoImplementation;

public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();
	MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public boolean addTransaction(int cardid, MetroStation source, MetroStation destination) {
		Transaction t = new Transaction();
		t.setCardId(cardid);
		t.setSourceId(source.getId());
		t.setDestinationId(destination.getId());
		t.setFare(mdi.calculateFare(source, destination));
		return tdi.addTransaction(t);
	}

	@Override
	public boolean updateTransaction(int transactionId) {
		Transaction t = tdi.getTransactionBytransactionID(transactionId);
		if (t == null)
			return false;
		else if (t.getSwipeOutTime() == null && t.getSwipeInTime() != null) {
			return tdi.updateTransaction(t);
		}
		return false;
	}
	@Override
	public Transaction getLastTransaction() {
		// TODO Auto-generated method stub
		return tdi.getLastTransaction();
	}

}
