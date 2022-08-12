package com.metro.presentation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metro.bean.Card;
import com.metro.bean.MetroStation;
import com.metro.bean.Transaction;
import com.metro.service.CardService;
import com.metro.service.CardServiceImplementation;
import com.metro.service.MetroServiceImplementation;
import com.metro.service.TransactionServiceImplementation;
import com.metro.service.UserServiceImplementation;
import com.metro.validation.CardSignInImplementation;

import lombok.Data;

@Data
@Component
public class MetroPresentationImplementation implements MetroPresentation {
	private Card card;
	
	@Autowired
	private CardServiceImplementation cardServiceImplementation;
	@Autowired
	private UserServiceImplementation userServiceImplementation;
	@Autowired
	private CardSignInImplementation cardSignInImplementation;
	@Autowired
	private TransactionServiceImplementation transactionServiceImplementation;
	@Autowired
	private MetroServiceImplementation metroStationServiceImplementation;
	
	@Autowired
	public void setCardServiceImplementation(CardServiceImplementation cardServiceImplementation) {
		this.cardServiceImplementation = cardServiceImplementation;
	}
	@Autowired
	public void setUserServiceImplementation(UserServiceImplementation userServiceImplementation) {
		this.userServiceImplementation = userServiceImplementation;
	}
	@Autowired
	public void setTransactionServiceImplementation(TransactionServiceImplementation transactionServiceImplementation) {
		this.transactionServiceImplementation = transactionServiceImplementation;
	}
	@Autowired
	public void setMetroServiceImplementation(MetroServiceImplementation metroStationServiceImplementation) {
		this.metroStationServiceImplementation = metroStationServiceImplementation;
	}
	
	@Override
	public void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Request new card");
		System.out.println("2. Sign in");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			System.out.println("Email: ");
			String newCardEmail = sc.next();
			
			if(!userServiceImplementation.userEmailAlreadyInUse(newCardEmail)) {
				System.out.println("You are not a registered user");
				System.out.println("1. Register");
				System.out.println("X. Press any other key to exit");
				
				int userChoice = sc.nextInt();
				if(userChoice == 1) {
					System.out.println("Name: ");
					String newUserName = sc.next();
					System.out.println("Contact: ");
					long newUserContact = sc.nextLong();
					
					if(userServiceImplementation.addUser(newUserName, newCardEmail, newUserContact)) {
						System.out.println("User registration successful");
						System.out.println("New card password: ");
						String newCardPassword = sc.next();
						System.out.println("Balance: ");
						long newCardBalance = sc.nextLong();
						
						if(cardServiceImplementation.generateCard(newCardEmail, newCardPassword, newCardBalance)) {
							System.out.println("New card generated");
						} else {
							System.out.println("Couldn't generate new card");
						}
					} else {
						System.out.println("User registration unsuccessful");
					}
				} else {
					System.out.println("Bye");
					System.exit(0);
				}
			} else {
				System.out.println("It seems you already have a card, please sign in");
			}
			menu();
			break;
		case 2:
			System.out.println("Email: ");
			String signInCardEmail = sc.next();
			System.out.println("Password: ");
			String signInCardPassword = sc.next();
			
			card = cardSignInImplementation.signIn(signInCardEmail, signInCardPassword);
			if(card != null) {
				System.out.println("Signed in");
				metro();
			} else {
				System.out.println("Incorrect credentials");
			}
			menu();
			break;
		default:
			System.exit(0);
		}
	}

	@Override
	public void metro() {
		Scanner sc = new Scanner(System.in);
		boolean isCardSwipedIn = (
				transactionServiceImplementation.getLastTransaction() != null &&
						transactionServiceImplementation.getLastTransaction().getSwipeInTime() != null &&
								transactionServiceImplementation.getLastTransaction().getSwipeOutTime() == null
				);
		
		if(isCardSwipedIn) {
			System.out.println("1. Swipe out");
		} else {
			System.out.println("1. Swipe in");
		}
		System.out.println("2. Update balance");
		System.out.println("3. Check balance");
		System.out.println("4. Sign out");
		System.out.println("X. Press any other key to exit");
		
		int choice = sc.nextInt();
		switch(choice) {
		case 1:
			List<MetroStation> listOfMetroStations = metroStationServiceImplementation.fetchMetroStations();
			if(!isCardSwipedIn) {
				double cardBalance = cardServiceImplementation.checkBalance(card.getId());
				if(cardBalance < CardService.minimumBalance) {
					System.out.println("Please recharge, minimum card balance should be " + CardService.minimumBalance);
					break;
				}
				
				System.out.println("Source: ");
				for(MetroStation metro: listOfMetroStations) {
					System.out.println(metro.getId() + ". " + metro.getName());
				}
				int metroSourceChoice = sc.nextInt();
				MetroStation sourceMetroStation = listOfMetroStations.get(metroSourceChoice - 1);
				
				if(transactionServiceImplementation.addTransaction(card.getId(), sourceMetroStation)) {
					System.out.println("Swiped in");
				} else {
					System.out.println("Couldn't swipe in");
				}
			} else {
				System.out.println("Destination: ");
				for(MetroStation metro: listOfMetroStations) {
					System.out.println(metro.getId() + ". " + metro.getName());
				}
				int metroDestinationChoice = sc.nextInt();
				Transaction lastTransaction = transactionServiceImplementation.getLastTransaction();
				lastTransaction.setSwipeOutTime(Timestamp.from(Instant.now()));
				MetroStation sourceMetroStation = metroStationServiceImplementation.getMetroStation(lastTransaction.getSourceId());
				MetroStation destinationMetroStation = listOfMetroStations.get(metroDestinationChoice - 1);
				
				if(sourceMetroStation.equals(destinationMetroStation)) {
					boolean shouldBeCharged = (lastTransaction.getSwipeOutTime().getTime() - lastTransaction.getSwipeInTime().getTime()) >= 1800000;
					if(transactionServiceImplementation.updateTransaction(lastTransaction.getId(), destinationMetroStation.getId(), shouldBeCharged ? CardService.minimumBalance: 0)) {
						if(shouldBeCharged && cardServiceImplementation.deductFare(card.getId(), CardService.minimumBalance)) {
							System.out.println("Fare: ₹" + CardService.minimumBalance);
						} else {
							System.out.println("Fare: ₹0");
						}
						System.out.println("Card balance: ₹" + cardServiceImplementation.checkBalance(card.getId()));
						System.out.println("Swiped out");
					} else {
						System.out.println("Couldn't swipe out");
					}
				} else {
					double journeyFare = metroStationServiceImplementation.calculateFare(sourceMetroStation, destinationMetroStation);
					double cardBalance = cardServiceImplementation.checkBalance(card.getId());
					if((cardBalance - journeyFare) > CardService.minimumBalance) {
						if(transactionServiceImplementation.updateTransaction(lastTransaction.getId(), destinationMetroStation.getId(), journeyFare)) {
							if(cardServiceImplementation.deductFare(card.getId(), journeyFare)){
								System.out.println("Fare: ₹" + journeyFare);
								System.out.println("Card balance: ₹" + cardServiceImplementation.checkBalance(card.getId()));
								System.out.println("Swiped out");
							}
						} else {
							System.out.println("Couldn't swipe out");
						}
					} else {
						System.out.println("You do not have enough balance in the card, please recharge");
					}
				}
			}
			metro();
			break;
		case 2:
			System.out.println("Add amount: ₹");
			double incrementCardBalanceByAmount = sc.nextDouble();
			if(cardServiceImplementation.updateBalance(card.getId(), incrementCardBalanceByAmount)) {
				System.out.println("Card balance updated");
			} else {
				System.out.println("Couldn't update card balance");
			}
			metro();
			break;
		case 3:
			System.out.println("Card balance: " + cardServiceImplementation.checkBalance(card.getId()));
			metro();
			break;
		case 4:
			card = null;
			menu();
			break;
		default:
			System.out.println("Bye");
			System.exit(0);
		}
	}
}