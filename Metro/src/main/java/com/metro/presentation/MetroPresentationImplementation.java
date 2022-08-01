package com.metro.presentation;

import java.util.List;
import java.util.Scanner;

import com.metro.bean.Card;
import com.metro.bean.MetroStation;
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
		
//		System.out.println("1. New user registration");
		System.out.println("1. Request new card");
		System.out.println("2. Sign in");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				System.out.println("Email: ");
				String newCardEmail = sc.next();
				
				//check if email exist in user table
				UserServiceImplementation usi = new UserServiceImplementation();
				if(!usi.userEmailAlreadyInUse(newCardEmail)) {
					System.out.println("You are not a registered user");
					System.out.println("1. Register");
					System.out.println("X. Press any other key to exit");
					
					int userChoice = sc.nextInt();
					if(userChoice == 1) {
						System.out.println("Name: ");
						String newUserName = sc.next();
						System.out.println("Contact: ");
						long newUserContact = sc.nextLong();
						
						//create user
						if(usi.addUser(newUserName, newCardEmail, newUserContact)) {
							System.out.println("User registration successful");
							
							System.out.println("New Card Password: ");
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
						} else {
							System.out.println("User registration unsuccessful");
						}
					} else {
						System.exit(0);
					}
				} else {
					//user exists
					System.out.println("New Card Password: ");
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
				}
				
				break;
			case 2:
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
		TransactionServiceImplementation tsi = new TransactionServiceImplementation();
		CardServiceImplementation csi = new CardServiceImplementation();
		//if swipeOutDate/swipeOutTime is null, isCardSwipedIn = true
		//else, isCardSwipedIn = false
		isCardSwipedIn = (tsi.getLastTransaction().getSwipeInTime() != null && tsi.getLastTransaction().getSwipeOutTime() == null);
		
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
		System.out.println("4. Sign out");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
			case 1:
				if(!isCardSwipedIn) {
					//fetch metro stations
					MetroServiceImplementation msi = new MetroServiceImplementation();
					List<MetroStation> listOfMetroStations = msi.fetchMetroStations();

					//ask user for email and password
					//if authentication fails, alert user
					
					//ask for source
					int index = 0;
					System.out.println("Source: ");
					for(MetroStation metro: listOfMetroStations) {
						System.out.println(index + ". " + metro.getName());
						index++;
					}
					int metroSourceChoice = sc.nextInt();
					MetroStation sourceMetroStation = listOfMetroStations.get(metroSourceChoice);
					
					//ask for source
					index = 0;
					System.out.println("Destination: ");
					for(MetroStation metro: listOfMetroStations) {
						System.out.println(index + ". " + metro.getName());
						index++;
					}
					int metroDestinationChoice = sc.nextInt();
					MetroStation destinationMetroStation = listOfMetroStations.get(metroDestinationChoice);
					
					//if minimum balance condition is not satisfied, alert user to update balance
					double journeyFare = msi.calculateFare(sourceMetroStation, destinationMetroStation);
					double cardBalance = csi.checkBalance(choice);
					int cardId = card.getId();
					if((cardBalance - journeyFare) > CardService.minimumBalance) {
						//enough balance to travel and maintain minimum balance in card
						//create a new transaction
						
						if(tsi.addTransaction(cardId,sourceMetroStation, destinationMetroStation)) {
							csi.deductfair(cardId,journeyFare);
							System.out.println("Swiped-in");
						} else {
							System.out.println("Couldn't swiped-in");
						}
					} else {
						//not enough balance in card, after journey
						//alert user to update card balance
						System.out.println("You do not have enough balance in the card, please recharge");
					}
				} else {
					//take the last transaction and update it

					int transactionToUpdate = tsi.getLastTransaction().getId();

					if(tsi.updateTransaction(transactionToUpdate)) {
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
				System.out.println("Card balance: " + csi.checkBalance(card.getId()));
				break;
			case 4:
				card = null;
				menu();
				break;
			default:
				System.exit(0);
		}
		
//		sc.close();
		metro_menu();
	}
}