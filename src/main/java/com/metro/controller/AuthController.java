package com.metro.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metro.bean.Card;
import com.metro.bean.Transaction;
import com.metro.model.service.SignInService;
import com.metro.model.service.TransactionService;

@Controller
public class AuthController {
	@Autowired
	SignInService signInService;
	
	@Autowired
	TransactionService transactionService;
	
	
//	======================SIGN IN======================
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public ModelAndView signInGET(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("card") == null) {
			modelAndView.setViewName("signIn");
		} else {
			modelAndView.setViewName("index");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ModelAndView signInPOST(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = signInService.signIn(email, password);
		if(card == null) {
			String message = "Incorrect credentials";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("signIn");
		} else {
			session.setAttribute("card", card);
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
//	======================SIGN IN======================
	
	
//	======================SIGN OUT======================
	@RequestMapping(value = "/signOut", method = RequestMethod.POST)
	public ModelAndView signOut(HttpSession session) {
		session.invalidate();
		
		return new ModelAndView("redirect:/signIn");
	}
//	======================SIGN OUT======================
}
