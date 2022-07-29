package com.metro.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import com.metro.bean.Card;
import com.metro.bean.Metro;
import com.metro.bean.Transaction;
import com.metro.service.CardService;
import com.metro.service.CardServiceImplementation;
import com.metro.service.MetroServiceImplementation;
import com.metro.service.TransactionServiceImplementation;
import com.metro.service.UserServiceImplementation;
import com.metro.validation.CardSignInImplementation;

import lombok.Data;

@Data
public class MetroPresentationImplementation implements MetroPresentation {
	private Card card;
	
	@Override
	public void menu() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("1. New user registration");
		System.out.println("2. Request new card");
		System.out.println("3. Sign in");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				System.out.println("Name: ");
				String newUserName = sc.next();
				System.out.println("Email: ");
				String newUserEmail = sc.next();
				System.out.println("Contact: ");
				long newUserContact = sc.nextLong();
				
				//create user
				UserServiceImplementation usi = new UserServiceImplementation();
				if(usi.addUser(newUserName, newUserEmail, newUserContact)) {
					System.out.println("User registration successful");
				} else {
					System.out.println("User registration unsuccessful");
				}
				break;
			case 2:
				//to create a new card, person has to be a registered user
				
				System.out.println("Email: ");
				String newCardEmail = sc.next();
				System.out.println("Password: ");
				String newCardPassword = sc.next();
				System.out.println("Balance: ");
				long newCardBalance = sc.nextLong();
				
				//create card
				CardServiceImplementation csi = new CardServiceImplementation();
				if(csi.generateCard(newCardEmail, newCardPassword, newCardBalance)) {
					System.out.println("New card generated");
				} else {
					System.out.println("Couldn't generate new card");
				}
				break;
			case 3:
				System.out.println("Email: ");
				String signInCardEmail = sc.next();
				System.out.println("Password: ");
				String signInCardPassword = sc.next();
				
				CardSignInImplementation csii = new CardSignInImplementation();
				
				card = csii.signIn(signInCardEmail, signInCardPassword);
				if(card != null) {
					System.out.println("Signed in");
					metro_menu();
				} else {
					System.out.println("Incorrect credentials");
				}
				break;
			default:
				System.exit(0);
		}
		
		
//		sc.close();
	}

	@Override
	public void metro_menu() {
		Scanner sc = new Scanner(System.in);
		boolean isCardSwipedIn;
		
		//get last transaction
		CardServiceImplementation csi = new CardServiceImplementation();
		
		//if swipeOutDate/swipeOutTime is null, isCardSwipedIn = true
		//else, isCardSwipedIn = false
		isCardSwipedIn = (csi.getLastTransaction().getSwipeOutDate() == null || csi.getLastTransaction().getSwipeOutTime() == null);
		
		if(isCardSwipedIn) {
			//if isCardSwipedIn = true
			//show Swipe-out in menu
			System.out.println("1. Swipe-out");
		} else {
			//else
			//show Swipe-in in menu
			System.out.println("1. Swipe-in");
		}
		System.out.println("2. Update balance");
		System.out.println("3. Check balance");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				if(!isCardSwipedIn) {
					//fetch metro stations
					MetroServiceImplementation msi = new MetroServiceImplementation();
					List<Metro> listOfMetroStations = msi.fetchMetroStations();

					//ask user for email and password
					//if authentication fails, alert user
					
					//ask for source
					int index = 1;
					System.out.println("Source: ");
					for(Metro metro: listOfMetroStations) {
						System.out.println(index + ". " + metro.getName());
						index++;
					}
					int metroSourceChoice = sc.nextInt();
					Metro sourceMetroStation = listOfMetroStations.get(metroSourceChoice);
					
					//ask for source
					index = 1;
					System.out.println("Destination: ");
					for(Metro metro: listOfMetroStations) {
						System.out.println(index + ". " + metro.getName());
						index++;
					}
					int metroDestinationChoice = sc.nextInt();
					Metro destinationMetroStation = listOfMetroStations.get(metroDestinationChoice);
					
					
					//if minimum balance condition is not satisfied, alert user to update balance
					double journeyFare = msi.calculateFare(sourceMetroStation, destinationMetroStation);
					double cardBalance = csi.checkBalance(choice);
					if((cardBalance - journeyFare) > CardService.minimumBalance) {
						//enough balance to travel and maintain minimum balance in card
						//create a new transaction
						LocalDate currentDate = LocalDate.now();
						LocalTime currentTime = LocalTime.now();
						
						TransactionServiceImplementation tsi = new TransactionServiceImplementation();
						if(tsi.addTransaction(sourceMetroStation, destinationMetroStation, currentDate, currentTime)) {
							System.out.println("Swiped-out");
						} else {
							System.out.println("Couldn't swiped-out");
						}
					} else {
						//not enough balance in card, after journey
						//alert user to update card balance
						System.out.println("You do not have enough balance in the card, please recharge");
					}
				} else {
					//take the last transaction and update it
					LocalDate currentDate = LocalDate.now();
					LocalTime currentTime = LocalTime.now();
					
					int transactionToUpdate = csi.getLastTransaction().getId();
					TransactionServiceImplementation tsi = new TransactionServiceImplementation();
					if(tsi.updateTransaction(transactionToUpdate, currentDate, currentTime)) {
						System.out.println("Swiped-out");
					} else {
						System.out.println("Couldn't swiped-out");
					}
				}
				
				break;
			case 2:
				System.out.println("Add amount: ₹");
				double incrementCardBalanceByAmount = sc.nextDouble();
				if(csi.updateBalance(card.getId(), incrementCardBalanceByAmount)) {
					System.out.println("Card balance updated");
				} else {
					System.out.println("Couldn't update card balance");
				}
				
				break;
			case 3:
				//ask user for email and password
				//if authentication fails, alert user
				
				System.out.println("Card balance: " + csi.checkBalance(card.getId()));
				break;
			default:
				System.exit(0);
		}
		
//		sc.close();
	}
}