// Main game process

import java.util.Scanner;
import java.util.ArrayList;

public class Blackjack {
	public static void start () {
		ArrayList<Player> players = new ArrayList<>();// Use array list to store Player in order to support multiplayer game
		Player dealer;
		Deck deck = new Deck();
		BlackJackGameLogic BJgame = new BlackJackGameLogic();
		Scanner in = new Scanner(System.in);
	
		BJgame.initializeDeck(deck);
		BJgame.initializeDealer(dealer, deck);
		BJgame.initializePlayer(players, dealer, deck);
		System.out.println("");
		
		while (true) {
			BJgame.printCard(dealer, players);
			if (Judge.judgeNatural(dealer, players)) {// A natural blackjack has occured
				BJgame.showMoney(dealer, players);
				// reset deck, dealer and players
				deck.setDeck(deck.getNumberofDeck());
				dealer.clear(deck);
				if (BJgame.getPlayer(players) == 1) System.out.println("Would you like to start the next round? N - no, other characters - yes");
				else System.out.println("Would you all like to start the next round? Y - yes, N - all quit, other characters: some might quit");
				String gameEnd = in.next();
				if (gameEnd.equals("N") || gameEnd.equals("n")) break;
				else if (!gameEnd.equals("Y") && !gameEnd.equals("y") && BJgame.getPlayer(players) > 0) BJgame.removePlayer(players);// Each player can decide whether leaves the game
				if (BJgame.getPlayer(players) == 0) {
					System.out.println("There's no players now.");
					break;
				}
				BJgame.cleanUpPlayer(players, deck);
				//BJgame.addPlayer(players, dealer, deck);
				continue;
			}
			for (int i = 0; i < BJgame.getPlayer(players); i ++) {
				BJgame.playerMove(players.get(i), dealer, players, deck);
				if (deck.isEmpty()) {
					System.out.println("The deck is empty. This round ends.");
					break;
				}
			}
			if (!deck.isEmpty() && !Judge.judgeAllBust(players)  ) BJgame.dealerMove(dealer, players, deck);
			BJgame.statistic(dealer, Judge.judgeAll(players, dealer));
			BJgame.showMoney(dealer, players);
			// reset deck, dealer and players
			deck.setDeck(deck.getNumberofDeck());
			dealer.clear(deck);
			if (BJgame.getPlayer(players) == 1) System.out.println("Would you like to start the next round? N - no, other characters - yes");
			else System.out.println("Would you all like to start the next round? Y - yes, N - all quit, other characters: some might quit");
			String gameEnd = in.next();
			if (gameEnd.equals("N") || gameEnd.equals("n")) break;
			else if (!gameEnd.equals("Y") && !gameEnd.equals("y") && BJgame.getPlayer(players) > 1) BJgame.removePlayer(players);// Each player can decide whether leaves the game
			if (BJgame.getPlayer(players) == 0) {
				System.out.println("There's no players now.");
				break;
			}
			BJgame.cleanUpPlayer(players, deck);
			//BJgame.addPlayer(players, dealer, deck);
		}
		System.out.println("");
		System.out.println("Game Over!");
	}
	
	public static void main(String args[]) {
		start();
	}
}