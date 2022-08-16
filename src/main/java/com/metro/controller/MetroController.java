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
	SignInService signInService;
	
	@Autowired
	CardService cardService;
	
	@Autowired
	MetroStationService metroStationService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	UserService userService;
		
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
		
		return new ModelAndView("signIn");
	}
//	======================SIGN OUT======================
	
	
//	======================CHECK BALANCE======================
	@RequestMapping(value = "/checkBalance", method = RequestMethod.GET)
	public ModelAndView checkBalance(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		double balance = cardService.checkBalance(card.getId());
		
		modelAndView.addObject("balance", balance);
		modelAndView.setViewName("balance");
		
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
	
	
//	======================FETCH ALL METRO STATIONS======================
	@ModelAttribute("metroStations")
	public List<MetroStation> getMetroStations(){
		List<MetroStation> metroStations = metroStationService.fetchMetroStations();
		
		return metroStations;
	}
//	======================FETCH ALL METRO STATIONS======================
	
	
//	======================SWIPE IN======================
	@RequestMapping(value = "/swipeIn", method = RequestMethod.GET)
	public ModelAndView swipeInGET(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("card") != null) {
			if(session.getAttribute("isSwipedIn") != "true") {
				modelAndView.setViewName("swipeIn");				
			} else {
				modelAndView.setViewName("swipeOut");
			}
		} else {
			modelAndView.setViewName("signIn");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/swipeIn", method = RequestMethod.POST)
	public ModelAndView swipeInPOST(@RequestParam("metroStation") String metroStation, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		List<MetroStation> metroStations = getMetroStations();
		MetroStation sourceMetroStation = metroStations.stream().filter(ms -> ms.getName().equals(metroStation)).distinct().collect(Collectors.toList()).get(0);
		boolean isTransactionAdded = transactionService.addTransaction(card.getId(), sourceMetroStation);

		String message = null;
		if(isTransactionAdded) {
			message = "Swiped in";
		} else {
			message = "Couldn't swipe in";
		}
		session.setAttribute("isSwipedIn", "true");
		modelAndView.addObject("message", message);
		modelAndView.setViewName("index");
		
		return modelAndView;
	}
//	======================SWIPE IN======================
	
	
//	======================SWIPE OUT======================
	@RequestMapping(value = "/swipeOut", method = RequestMethod.GET)
	public ModelAndView swipeOutGET(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("card") != null) {
			if(session.getAttribute("isSwipedIn") != "true") {
				modelAndView.setViewName("swipeIn");				
			} else {
				modelAndView.setViewName("swipeOut");
			}
		} else {
			modelAndView.setViewName("signIn");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/swipeOut", method = RequestMethod.POST)
	public ModelAndView swipeOutPOST(@RequestParam("metroStation") String metroStation, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		Card card = (Card)session.getAttribute("card");
		List<MetroStation> metroStations = getMetroStations();
		Transaction lastTransaction = transactionService.getLastTransaction(card.getId());
		lastTransaction.setSwipeOutTime(Timestamp.from(Instant.now()));
		MetroStation sourceMetroStation = metroStationService.getMetroStation(lastTransaction.getSourceId());
		MetroStation destinationMetroStation = metroStations.stream().filter(ms -> ms.getName().equals(metroStation)).distinct().collect(Collectors.toList()).get(0);
		
		if(sourceMetroStation.equals(destinationMetroStation)) {
			boolean shouldBeCharged = (lastTransaction.getSwipeOutTime().getTime() - lastTransaction.getSwipeInTime().getTime()) >= 1800000;
			if(transactionService.updateTransaction(lastTransaction.getId(), destinationMetroStation.getId(), shouldBeCharged ? CardService.minimumBalance: 0)) {
				if(shouldBeCharged && cardService.deductFare(card.getId(), CardService.minimumBalance)) {
					modelAndView.addObject("message", "Fare: ₹" + CardService.minimumBalance);
				} else {
					modelAndView.addObject("message", "Fare: ₹0");
				}
			} else {
				modelAndView.addObject("message", "Couldn't swipe out");
			}
		} else {
			double fare = metroStationService.calculateFare(sourceMetroStation, destinationMetroStation);
			double cardBalance = cardService.checkBalance(card.getId());
			if((cardBalance - fare) > CardService.minimumBalance) {
				if(transactionService.updateTransaction(lastTransaction.getId(), destinationMetroStation.getId(), fare)) {
					if(cardService.deductFare(card.getId(), fare)){
						modelAndView.addObject("message", "Fare: ₹" + fare);
					}
				} else {
					modelAndView.addObject("message", "Couldn't swipe out");
				}
			} else {
				modelAndView.addObject("message", "You do not have enough balance in the card, please recharge");
				modelAndView.setViewName("updateBalance");
			}
		}
		
		session.setAttribute("isSwipedIn", "false");
		modelAndView.setViewName("message");
		
		return modelAndView;
	}
//	======================SWIPE OUT======================
	
	
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
		
		if(!userService.userEmailAlreadyInUse(email)) {
			if(userService.addUser(name, email, contact)) {
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
}
