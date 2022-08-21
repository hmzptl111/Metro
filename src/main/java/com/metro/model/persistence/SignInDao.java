package com.metro.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metro.bean.Card;

@Repository
public interface SignInDao extends JpaRepository<Card, Integer> {
	Card findByEmailAndPassword(String email, String password);
}