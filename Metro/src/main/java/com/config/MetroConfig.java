package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.metro.bean.User;
import com.metro.persistence.CardDaoImplementation;
import com.metro.persistence.MetroDaoImplementation;
import com.metro.persistence.TransactionDaoImplementation;
import com.metro.persistence.UserDaoImplementation;
import com.metro.presentation.MetroPresentationImplementation;
import com.metro.service.CardServiceImplementation;
import com.metro.service.MetroServiceImplementation;
import com.metro.service.TransactionServiceImplementation;
import com.metro.service.UserServiceImplementation;
import com.metro.validation.CardSignInImplementation;

@Configuration
public class MetroConfig {
	@Bean
	public DriverManagerDataSource getDriverManagerDataSource() {
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dmds.setUrl("jdbc:mysql://localhost:3306/metro");
		dmds.setUsername("root");
		dmds.setPassword("wiley");
		
		return dmds;
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(getDriverManagerDataSource());
		
		return jdbcTemplate;
	}
	
	@Bean
	public CardDaoImplementation getCardDaoImplementation() {
		CardDaoImplementation cardDaoImplementation = new CardDaoImplementation();
		cardDaoImplementation.setJdbcTemplate(getJdbcTemplate());
		return cardDaoImplementation;
	}
	
	@Bean
	public UserDaoImplementation getUserDaoImplementation() {
		UserDaoImplementation userDaoImplementation = new UserDaoImplementation();
		userDaoImplementation.setJdbcTemplate(getJdbcTemplate());
		
		return userDaoImplementation;
	}
	
	@Bean
	public TransactionDaoImplementation getTransactionDaoImplementation() {
		TransactionDaoImplementation transactionDaoImplementation = new TransactionDaoImplementation();
		transactionDaoImplementation.setJdbcTemplate(getJdbcTemplate());
		
		return transactionDaoImplementation;
	}
	
	@Bean
	public MetroDaoImplementation getMetroDaoImplementation() {
		MetroDaoImplementation metroDaoImplementation = new MetroDaoImplementation();
		metroDaoImplementation.setJdbcTemplate(getJdbcTemplate());
		
		return metroDaoImplementation;
	}
	
	@Bean
	public CardServiceImplementation getCardServiceImplementation() {
		CardServiceImplementation cardServiceImplementation = new CardServiceImplementation();
		cardServiceImplementation.setCardDaoImplementation(getCardDaoImplementation());
		cardServiceImplementation.setUserDaoImplementation(null);
		
		return cardServiceImplementation;
	}
	
	@Bean
	public UserServiceImplementation getUserServiceImplementation() {
		UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
		userServiceImplementation.setUserDaoImplementation(getUserDaoImplementation());
		
		return userServiceImplementation;
	}
	
	@Bean
	public TransactionServiceImplementation getTransactionServiceImplementation() {
		TransactionServiceImplementation transactionServiceImplementation = new TransactionServiceImplementation();
		transactionServiceImplementation.setTransactionServiceImplementation(getTransactionDaoImplementation());
		
		return transactionServiceImplementation;
	}
	
	@Bean
	public MetroServiceImplementation getMetroServiceImplementation() {
		MetroServiceImplementation metroServiceImplementation = new MetroServiceImplementation();
		metroServiceImplementation.setMetroDaoImplementation(getMetroDaoImplementation());
		
		return metroServiceImplementation;
	}
	
	@Bean(name = "metroPresentationImplementation")
	public MetroPresentationImplementation getMetroPresentationImplementation() {
		MetroPresentationImplementation metroPresentationImplementation = new MetroPresentationImplementation();
		metroPresentationImplementation.setCardServiceImplementation(getCardServiceImplementation());
		metroPresentationImplementation.setUserServiceImplementation(getUserServiceImplementation());
		
		return metroPresentationImplementation;
	}
	
	@Bean
	public CardSignInImplementation getCardSignInImplementation() {
		CardSignInImplementation cardSignInImplementation = new CardSignInImplementation();
		cardSignInImplementation.setJdbcTemplate(getJdbcTemplate());
		
		return cardSignInImplementation;
	}
}
