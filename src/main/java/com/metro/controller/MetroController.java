package com.metro.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metro.bean.Card;
import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.model.service.CardService;
import com.metro.model.service.MetroStationService;
import com.metro.model.service.SignInService;
import com.metro.model.service.TransactionService;
import com.metro.model.service.UserService;

@Controller
public class MetroController {
	@Autowired
	TransactionService transactionService;
		
//	======================INDEX PAGE======================
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndex(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		if(card == null) {
			modelAndView.setViewName("signIn");
		} else {
			Transaction lastTransaction = transactionService.getLastTransaction(card.getId());
			if(lastTransaction != null) {
				Timestamp lastTransactionSwipeInTime = lastTransaction.getSwipeInTime();
				Timestamp lastTransactionSwipeOutTime = lastTransaction.getSwipeOutTime();
				if(lastTransactionSwipeInTime != null && lastTransactionSwipeOutTime == null) {
					session.setAttribute("isSwipedIn", "true");
				}
			}
			modelAndView.setViewName("index");
		}
		
		return modelAndView;
	}
//	======================INDEX PAGE======================
}
