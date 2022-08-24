package com.metro.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metro.bean.Card;
import com.metro.model.service.CardService;
import com.metro.model.service.UserService;

@Controller
public class CardController {
	@Autowired
	CardService cardService;
	
	@Autowired
	UserService userService;
	
//	======================NEW CARD======================
	@RequestMapping(value = "/newCard", method = RequestMethod.GET)
	public ModelAndView newCardGET(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("card") != null) {
			modelAndView.setViewName("index");
		} else {
			modelAndView.setViewName("newCard");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/newCard", method = RequestMethod.POST)
	public ModelAndView newCardPOST(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("contact") long contact, @RequestParam("password") String password, @RequestParam("balance") double balance) {
		ModelAndView modelAndView = new ModelAndView();
		
		if(userService.getUserByEmail(email) == null) {
			if(userService.addUser(email, name, contact)) {
				if(cardService.generateCard(email, password, balance)) {
					modelAndView.addObject("message", "Card generated");
				} else {
					modelAndView.addObject("message", "Couldn't generate card, please read manual and input valid credentials");
				}
			} else {
				modelAndView.addObject("message", "User registration unsuccessful");
			}
		} else {
			modelAndView.addObject("message", "It seems like you already have a card");
		}
		
		modelAndView.setViewName("message");
		
		return modelAndView;
	}
//	======================NEW CARD======================
	
//	======================CHECK BALANCE======================
	@RequestMapping(value = "/checkBalance", method = RequestMethod.GET)
	public ModelAndView checkBalance(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		
		if(card == null) {
			modelAndView.addObject("message", "Couldn't process card");
			modelAndView.setViewName("message");
		} else {
			double balance = cardService.checkBalance(card.getId());
			
			modelAndView.addObject("balance", balance);
			modelAndView.setViewName("balance");
		}
		
		return modelAndView;
	}
//	======================CHECK BALANCE======================
	
	
//	======================UPDATE BALANCE======================
	@RequestMapping(value = "/updateBalance", method = RequestMethod.GET)
	public ModelAndView updateBalanceGET(HttpSession session) {
		return new ModelAndView("updateBalance");
	}
	
	@RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
	public ModelAndView updateBalancePOST(@RequestParam("amount") double amount, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		boolean isCardBalanceUpdated = cardService.updateBalance(card.getId(), amount);
		String message = isCardBalanceUpdated ? "Card balance updated successfully": "Couldn't update card balance";
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("updateBalance");
		
		return modelAndView;
	}
//	======================UPDATE BALANCE======================
	
	
}
