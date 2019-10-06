// Main game process

import java.util.Scanner;
import java.util.ArrayList;

public class Thirtyone {
	public static void start () {
		ArrayList<Player> players = new ArrayList<>();// Use array list to store Player in order to support multiplayer game
		Player dealer;
		Deck deck = new Deck();
		ThirtyoneGameLogic thirtyoneGame = new ThirtyoneGameLogic();
		Scanner in = new Scanner(System.in);
		String dealerName;
		
		thirtyoneGame.initialize(players,deck);
		dealer = thirtyoneGame.chooseDealer(players);
		dealerName = dealer.getName();
		dealer.setCash(dealer.getCash() * 3);
		System.out.println("");
		
		while (true) {
			thirtyoneGame.setUp(dealer,players,deck);
			for (int i = 0; i < thirtyoneGame.getPlayer(players); i ++) {
				Player currentPlayer = players.get(i);
				if(currentPlayer == dealer || currentPlayer.isFold())continue;
				thirtyoneGame.printCard(currentPlayer,dealer, players);
				thirtyoneGame.playerMove(currentPlayer, dealer, players, deck);
				if (deck.isEmpty()) {
					System.out.println("The deck is empty. This round ends.");
					break;
				}
			}
			if (!deck.isEmpty() && !Judge.judgeAllBust(players)  ) thirtyoneGame.dealerMove(dealer, players, deck);
			thirtyoneGame.statistic(dealer, Judge.judgeAll(players, dealer));
			thirtyoneGame.showMoney(dealer, players);
			// reset deck, dealer and players
			deck.setDeck(deck.getNumberofDeck());
			dealer.clear();
			if (thirtyoneGame.getPlayer(players) == 1) System.out.println("Would you like to start the next round? N - no, other characters - yes");
			else System.out.println("Would you all like to start the next round? Y - yes, N - all quit, other characters: some might quit");
			String gameEnd = in.next();
			if (gameEnd.equals("N") || gameEnd.equals("n")) break;
			else if (!gameEnd.equals("Y") && !gameEnd.equals("y") && thirtyoneGame.getPlayer(players) > 0) {
				String tempName = thirtyoneGame.removePlayer(players, dealer).getName();
				if (thirtyoneGame.getPlayer(players) == 0) {
					System.out.println("There's no players now.");
					break;
				}
				if(tempName == dealerName ){
					dealer = thirtyoneGame.chooseDealerWithoutDealer(players);
				}
				else {
					dealer = thirtyoneGame.chooseDealer(dealer, players);
				};// Each player can decide whether leaves the game
			}
			thirtyoneGame.cleanUpPlayer(players, deck);
			/*dealer = thirtyoneGame.chooseDealer(dealer, players);*/
			dealerName = dealer.getName();
		}
		System.out.println("");
		System.out.println("Game Over!");
	}
	
	public static void main(String args[]) {
		start();
	}
}