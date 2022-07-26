package com.metro.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metro.bean.Card;
import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.bean.TransactionHistory;
import com.metro.model.service.CardService;
import com.metro.model.service.MetroStationService;
import com.metro.model.service.TransactionService;

@Controller
public class TransactionController extends MetroStationController {
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	MetroStationService metroStationService;
	
	@Autowired
	CardService cardService;
	
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
			modelAndView.setViewName("redirect:/signIn");
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
			modelAndView.setViewName("redirect:/signIn");
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
	
//	======================TRANSACTION HISTORY======================
	@RequestMapping(value = "/transactionHistory", method = RequestMethod.GET)
	public ModelAndView transactionHistoryGET(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		Card card = (Card)session.getAttribute("card");
		if(card == null) {
			modelAndView.setViewName("redirect:/signIn");
			
			return modelAndView;
		}
		
		List<TransactionHistory> transactions = transactionService.getTransactionHistory(card.getId());
		
		modelAndView.addObject("transactions", transactions);
		modelAndView.setViewName("transactionHistory");
		
		return modelAndView;
	}
//	======================TRANSACTION HISTORY======================
}
