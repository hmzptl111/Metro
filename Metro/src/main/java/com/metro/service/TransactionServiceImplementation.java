package com.metro.service;

import com.metro.bean.Metro;
import com.metro.bean.Transaction;
import com.metro.persistence.MetroDaoImplementation;
import com.metro.persistence.TransactionDaoImplementation;

public class TransactionServiceImplementation implements TransactionService {
	TransactionDaoImplementation tdi = new TransactionDaoImplementation();
	MetroDaoImplementation mdi = new MetroDaoImplementation();

	@Override
	public boolean addTransaction(int cardid, Metro source, Metro destination) {
		if(tdi.getTransactionByCardID(cardid)!=null)
			return false;
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

}
