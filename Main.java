package euchre;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// Instantiate scanner
		Scanner reader = new Scanner(System.in);
		//BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

		// Instantiate Deck
		Deck deck = new Deck();

		// Instantiate AIs
		List<AIplayer> ais = new ArrayList<AIplayer>();
		AIplayer northAI = new AIplayer();
		AIplayer westAI = new AIplayer();
		AIplayer eastAI = new AIplayer();
		ais.add(westAI);
		ais.add(northAI);
		ais.add(eastAI);

		// Instantiate Hands
		List<Hand> hands = new ArrayList<Hand>();
		Hand north = new Hand();
		Hand west = new Hand();
		Hand east = new Hand();
		Hand south = new Hand();
		hands.add(west);
		hands.add(north);
		hands.add(east);
		hands.add(south);

		// Instantiate player names
		List<String> players = new ArrayList<String>();
		players.add("West");
		players.add("North");
		players.add("East");
		players.add("You");

		// Instantiate suit names
		List<String> suits = new ArrayList<String>();
		suits.add("Spades");
		suits.add("Hearts");
		suits.add("Clubs");
		suits.add("Diamonds");

		// Instantiate kitty and trick
		Hand kitty = new Hand();
		Hand trick = new Hand();

		// Instantiate miscellaneous Variables
		//scorett - score for the trick for friendly team
		//scorete - score for the trick for enemy team
		//scoregt - score for the game for friendly team
		//scorege - score for the game for enemy team
		//scorew  - winning score
		//called  - identifies player who called trump
		//dealer  - identifies player who dealt
		//turn    - identifies whose turn it is
		//trump   - identifies suit that was called as trump
		//card    - int value to identify which card to play
		//winner  - int value to identify winner of the trick
		//answer  - for parsing user input
		//pickUp  - whether the top card of the kitty has been picked up
		//isTeamDealer - whether dealer is on friendly team
		//isDealer     - whether player is dealer
		//escape       - escape variable for data entry
		
		int dealer = (int)(Math.random()*4), trump = -1, turn = dealer + 1, scorett, scorete, scoregt = 0, scorege = 0, called = -1, card = -1, winner = 0, scorew = 0;;
		boolean pickUp = false, isTeamDealer, isDealer, escape = false;
		String answer = "";
		Card card1 = null, card2 = null, card3 = null, card4 = null;

		//Introduction
		System.out.println("\t\t\t\tWelcome to Euchre!");
		System.out.println("\t\tYour teammate is North, and you are playing against");
		System.out.println("\t\t  a team consisting of East and West.  Good luck!\n\n\n\n");
		
		// Optional rules
		System.out.print("What score would you like to play to? (0-10): ");
		while(escape==false){
			try{
				scorew = Integer.parseInt(reader.nextLine());
				if(scorew<0 || scorew>10)
					throw new Exception();
				escape = true;
			}catch(Exception e){
				System.out.println("Input a real number, dingus");
			}
		}
		escape = false;
		
		boolean stickTheDealer = false, stick;
		System.out.print("\nWould you like stick the dealer on?(y/n) ");
		while(escape==false){
			try{
				answer = reader.nextLine();
				if (answer.equals("y")) {
					escape=true;
					stickTheDealer = true;
					System.out.println("Stick the dealer is on.\n");
				} else if (answer.equals("n")) {
					escape=true;
					System.out.println("Stick the dealer is off.\n");
				} else
					throw new Exception();
				escape = true;
			}catch(Exception e){
				System.out.println("Answer y or n please.");
			}
		}
		escape = false;

		// GAMESTART
		while (scorege < scorew && scoregt < scorew) {
			// Deal
			for (int i = 1; i <= 5; i++) {
				north.add(deck.draw());
				west.add(deck.draw());
				east.add(deck.draw());
				south.add(deck.draw());
				if (i != 5)
					kitty.add(deck.draw());
			}

			// Introduction
			System.out.println("Dealer is: " + players.get(dealer));
			System.out.print("Your hand: " + south);
			System.out.println("\n\nTop card of kitty: " + kitty.peek() + "\n");
			turn = dealer + 1;

			// First round to pick trump
			outerloop: for (int i = 0; i < 4; i++) {
				turn %= 4;
				if (!pickUp && turn != 3 && turn != 1) {
					if (dealer == 0 || dealer == 2) {
						isTeamDealer = true;
						if (dealer == turn)
							isDealer = true;
						else
							isDealer = false;
					} else {
						isDealer = false;
						isTeamDealer = false;
					}
					pickUp = ais.get(turn).callTrump(kitty.peek(),
							isTeamDealer, isDealer, hands.get(turn));
					if (!pickUp)
						System.out.println(players.get(turn) + " has passed.");
				} else if (!pickUp && turn == 1) {
					if (dealer == 1 || dealer == 3) {
						isTeamDealer = true;
						if (dealer == turn)
							isDealer = true;
						else
							isDealer = false;
					} else {
						isDealer = false;
						isTeamDealer = false;
					}
					pickUp = ais.get(turn).callTrump(kitty.peek(),
							isTeamDealer, isDealer, hands.get(turn));
					if (!pickUp)
						System.out.println(players.get(turn) + " has passed.");
				} else if (!pickUp) {
					System.out.print("\nPick up " + kitty.peek() + " to "
							+ players.get(dealer) + "? (y/n) ");
					while(escape==false){
						try{
							answer = reader.nextLine();
							if (answer.equals("y")) {
								pickUp = true;
							} if (answer.equals("n")) {
								System.out.println("You have passed.");
							} else
								throw new Exception();
							escape = true;
						}catch(Exception e){
							System.out.println("Answer y or n please.");
						}
					}
					escape = false;
				}
				if (pickUp) {
					called = turn;
					trump = kitty.peek().getSuit();
					break outerloop;
				}
				turn++;
				turn %= 4;
			}

			if (pickUp) {
				if(called == dealer){
					System.out.println(players.get(called) + " has "
						+ "ordered himself to pick up the "
						+ kitty.peek());
				} else{
					System.out.println(players.get(called) + " ordered "
						+ players.get(dealer) + " to pick up the "
						+ kitty.peek());
				}
				System.out.println("Trump is " + suits.get(trump));
			} else
				System.out.println("Dealer has not picked up the "
						+ kitty.peek() + "\n");

			// Pickup if first round works
			if (pickUp) {
				hands.get(dealer).add(kitty.removeCard(0));
				if (dealer != 3) {
					hands.get(dealer)
							.removeCard(
									ais.get(dealer).findWorst(
											hands.get(dealer), trump));
					System.out.println("Dealer has discarded.\n");
				} else {
					System.out.println("\nYou must discard:");
					System.out.print("Your hand: " + south);
					System.out.println("Which card? (0-4)");
					while(escape==false){
						try{
							hands.get(dealer).removeCard(Integer.parseInt(reader.nextLine()));
							escape = true;
						}catch(Exception e){
							System.out.println("Input a real number, dingus");
						}
					}
					escape = false;
				}
			}

			// Second round to pick trump
			turn = dealer + 1;
			if (!pickUp) {
				outerloop: for (int i = 0; i < 4; i++) {
					turn %= 4;
					if (!pickUp && turn != 3) {
						stick = false;
						if (turn == dealer && stickTheDealer)
							stick = true;
						trump = ais.get(turn).callTrump(hands.get(turn), stick);
						if (trump == -1)
							System.out.println(players.get(turn)
									+ " has passed.");
					} else if (!pickUp) {
						System.out
								.println("\nCall a suit? (n/Spades/Clubs/Hearts/Diamonds)");
						while(escape==false){
							try{
								answer = reader.nextLine();
								if (suits.contains(answer)){
									trump = suits.indexOf(answer);
								} if (answer.equals("n")) {
									if(stickTheDealer && dealer == turn) {
										System.out.print("You need to pick a suit.");
										continue;
									}
									System.out.println("You have passed.");
								} else
									throw new Exception();
								escape = true;
							}catch(Exception e){
								System.out.println("Answer a suit or n please.");
							}
						}
						escape = false;
					}
					if (trump != -1) {
						called = turn;
						pickUp = true;
						break outerloop;
					}
					turn++;
					turn %= 4;
				}
				if (pickUp)
					System.out.println(players.get(called)
							+ " called trump as " + suits.get(trump));
				else
					System.out.println("No one has called trump.  Round over.");

			}

			// SetJacks
			for (Hand hand : hands) {
				hand.setJacks(trump);
			}

			// Game
			if (trump != -1) {
				turn = dealer + 1;
				turn%=4;
				scorete = 0;
				scorett = 0;
				for (int i = 1; i <= 5; i++) {
					System.out.println("Trick " + i + ":\n");
					if (turn != 3) {
						if (called == turn)
							card = ais.get(turn).lead(true, trump,
									hands.get(turn));
						else
							card = ais.get(turn).lead(false, trump,
									hands.get(turn));
						card1 = hands.get(turn).getCard(card);
						System.out.println(players.get(turn) + " has led the "
								+ hands.get(turn).removeCard(card));
					} else {
						System.out.println("Your hand: " + south);
						System.out.print("Your lead: Which card? (0-"
								+ (south.size() - 1) + ")");
						while(escape==false){
							try{
								card = Integer.parseInt(reader.nextLine());
								if(card<0||card>(south.size() - 1))
									throw new Exception();
								escape = true;
							}catch(Exception e){
								System.out.println("Input a real number, dingus");
							}
						}
						escape = false;
						card1 = hands.get(turn).getCard(card);
						System.out.println("You have led the "
								+ hands.get(turn).removeCard(card));
					}
					turn++;
					turn %= 4;
					for (int j = 0; j < 3; j++) {
						if (turn != 3) {
							card = ais.get(turn).play((j + 1), card1, card2,
									card3, hands.get(turn), trump);
							if (j == 0)
								card2 = hands.get(turn).getCard(card);
							if (j == 1)
								card3 = hands.get(turn).getCard(card);
							if (j == 2)
								card4 = hands.get(turn).getCard(card);
							System.out.println(players.get(turn)
									+ " has played the "
									+ hands.get(turn).removeCard(card));
						} else {
							System.out.println("Your hand: " + south);
							System.out.print("Your turn: Which card? (0-"
									+ (south.size() - 1) + ")");
							while(escape==false){
								try{
									card = Integer.parseInt(reader.nextLine());
									if(card<0||card>(south.size() - 1)||!south.goodCard(card, card1))
										throw new Exception();
									escape = true;
								}catch(Exception e){
									System.out.println("Please play a valid card.  Make sure to follow suit.");
								}
							}
							escape = false;
							if (j == 0)
								card2 = hands.get(turn).getCard(card);
							if (j == 1)
								card3 = hands.get(turn).getCard(card);
							if (j == 2)
								card4 = hands.get(turn).getCard(card);
							System.out.println("You have played the "
									+ hands.get(turn).removeCard(card));
						}
						turn++;
						turn %= 4;
					}
					trick.add(card1);
					trick.add(card2);
					trick.add(card3);
					trick.add(card4);
					winner = (trick.findWinner(trump) + turn)%4;
					System.out.println("\n" + players.get(winner)
							+ " won the trick!");
					if (winner % 2 == 0)
						scorete++;
					else
						scorett++;
					System.out.println("Trick score: You/North: " + scorett
							+ ", West/East: " + scorete + "\n");
					card1 = null;
					card2 = null;
					card3 = null;
					card4 = null;
					trick.clear();
					turn=winner;
				}
				if (scorete >= 3) {
					scorege++;
					if (called % 2 != 0)
						scorege++;
					if(scorete == 5)
						scorege++;
				} else {
					scoregt++;
					if (called % 2 == 0)
						scoregt++;
					if(scorett == 5)
						scoregt++;
				}
				scorett = 0;
				scorete = 0;
				turn = winner;
				winner = 0;
				card = -1;
				System.out.println("Overall score: You/North: " + scoregt
						+ ", West/East: " + scorege + "\n");
			}

			// Reset
			for (int i = 0; i < hands.size(); i++) {
				hands.get(i).clear();
			}
			deck.reset();
			kitty.clear();
			dealer++;
			trump = -1;
			turn = dealer + 1;
			pickUp = false;
		}

		if (scorege >= scorew)
			System.out.println("Your team lost.");
		else
			System.out.println("You won.  Congrats!");
		
		reader.close();

	}

}
