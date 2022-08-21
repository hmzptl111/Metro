package com.metro.model.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metro.bean.Transaction;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
	@Transactional
	@Modifying
	@Query(value = "insert into transaction(card_id, source_metro_id, swipe_in_time) values(:cardId, :sourceMetroId, CURRENT_TIMESTAMP)", nativeQuery = true)
	int addTransaction(@Param("cardId") int cardId, @Param("sourceMetroId") int sourceMetroId);

	Transaction findById(int transactionId);

	@Transactional
	@Modifying
	@Query(value = "update transaction set destination_metro_id = :destinationMetroId, swipe_out_time = CURRENT_TIMESTAMP, fare_calculated = :fare where id = :id", nativeQuery = true)
	int updateTransaction(@Param("id") int id, @Param("destinationMetroId") int destinationMetroId, @Param("fare") double fare);
	
	@Query(value = "select * from transaction where card_id = :cardId order by id desc limit 1", nativeQuery = true)
	Transaction getLastTransaction(@Param("cardId") int cardId);
}
