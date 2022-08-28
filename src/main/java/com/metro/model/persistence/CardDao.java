package com.metro.model.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.metro.bean.Card;

@Repository
public interface CardDao extends JpaRepository<Card, Integer> {
	@Transactional
	@Modifying
	@Query(value = "insert into card(email, password, balance) values(:email, :password, :balance)", nativeQuery = true)
	int generateCard(@Param("email") String email, @Param("password") String password, @Param("balance") double balance);

	@Query(value = "select balance from Card where id = :cardId")
	double checkBalance(@Param("cardId") int cardId);

	@Transactional
	@Modifying
	@Query(value = "update card set balance = (select balance from (select balance from card where id = :cardId) as c) + :amount where id = :cardId", nativeQuery = true)
	int updateBalance(@Param("cardId") int cardId, @Param("amount") double amount);
	
	@Transactional
	@Modifying
	@Query(value = "update card set balance = (select balance from (select balance from card where id = :cardId) as c) - :journeyFare where id = :cardId", nativeQuery = true)
	int deductFare(@Param("cardId") int cardId, @Param("journeyFare") double journeyFare);
}
