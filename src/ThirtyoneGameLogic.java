// Actions and logic of the game

import java.util.ArrayList;
import java.util.Scanner;

public class ThirtyoneGameLogic {
	int numberofPlayer;
	final int numberofDeck = 2;
	String[] nameofPlayer;
	Scanner in = new Scanner(System.in);
	final int minPlayers = 2;
	final int maxPlayers = 9;
	final int hitTo = 17;
	
	
	public void initialize(ArrayList<Player> players, Deck deck) {
		// TODO Auto-generated method stub
		deck.setDeck(numberofDeck);
		System.out.print("Enter the amount of players: ("+minPlayers+"-"+maxPlayers+")");
		while (true) {
			try {
				in = new Scanner(System.in);
				numberofPlayer = in.nextInt();
				if (numberofPlayer < minPlayers || numberofPlayer > maxPlayers) System.out.println("Invalid input! Please try again!");
				else break;
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again!");
			}
		}
		nameofPlayer = new String[numberofPlayer];
		for (int i = 1; i <= numberofPlayer; i ++) {// Initialize each player to the array list one by one
			String name;
			int cash;
			System.out.print("Enter the name of player " + i + ": ");
			while (true) {
				name = in.next();
				boolean isRepeat = false;
				for (int j = 0; j < numberofPlayer; j ++) {
					if (nameofPlayer[j] == null) break;
					if (nameofPlayer[j].equals(name)) {
						isRepeat = true;
						break;
					}
				}
				if (isRepeat) System.out.println("Invalid input! Please try again!");
				else break;
			}
			// Each player make their bet separately
			System.out.print("Enter the cash of every player: ");
			while (true) {
				try {
					in = new Scanner(System.in);
					cash = in.nextInt();
					if (cash < 0) System.out.println("Invalid input! Please try again!");
					else break;
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again!");
				}
			}
			players.add(new Player(name, cash));
			nameofPlayer[i - 1] = name;
		}
	}
	
	public Player chooseDealer(ArrayList<Player> players) {
		System.out.print("Enter the name of dealer: ");
		while (true) {
			String name = in.next();
			for(int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if( p.getName() == name) {
					return p;
				}
			}
			System.out.println("Invalid input! Please try again!");
		}
	}
	
	public Player chooseDealer(ArrayList<Player> players, String name) {
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if( p.getName() == name) {
				return p;
			}
		}
		System.out.println("Invalid input! Please try again!");
		return null;
	}
	
	public void setUp(Player dealer, ArrayList<Player> players, Deck deck) {
		
	}
	
	public void printCard(Player currentPlayer, Player dealer, ArrayList<Player> players) {
		System.out.print(dealer.getName() + " (dealer): " + dealer.getCards()[0]);
		if (dealer == currentPlayer) {
			for (int i = 1; i < dealer.getCardCnt(); i ++) {
				if (dealer.getCards()[i] == null) break;
				System.out.print(" " + dealer.getCards()[i]);
			}
			if (dealer.isBust()) System.out.print(" (bust)");
		}
		else System.out.print(" X");
		System.out.println("");
		for (int i = 0; i < numberofPlayer; i ++) {
			;
		}
		System.out.println("");
	}
	
	public void playerMove(Player player, Player dealer, ArrayList<Player> players, Deck deck) {
		if (player == null) return;
		for (int j = 0; j <= 0; j ++) {
			Player p = player;
			if (p.isBust()) {
				System.out.println(p.getName() + " is skipped because of bust.");
				break;
			}
			System.out.println(p.getName() + "'s turn.");
			System.out.print("Choose action: 1 - hit; 2 - stand; 3 - double up");
			boolean canSplit = false;
			if (p.getCards()[0].getCard().equals(p.getCards()[1].getCard())) {// Split is only available when the first 2 cards are identical
				System.out.print("; 4 - split");
				canSplit = true;
			}
			System.out.println("");
			while (!deck.isEmpty()) {
				try {
					Scanner scanner = new Scanner(System.in);
					int actionNum = scanner.nextInt();
					if (actionNum == 1) {
						System.out.println(p.getName() + " choose to hit");
						p.hit(deck);
						if (p.getPoints() >= 21) {
							System.out.println(p.getName() + " has reached or exceeded 21 points.");
							break;
						}
						else {
							printCard(player,dealer, players);
							System.out.print("Choose action: 1 - hit; 2 - stand; 3 - double up");
							canSplit = false;
							if (p.getCards()[0].getCard().equals(p.getCards()[1].getCard())) {
								System.out.print("; 4 - split");
								canSplit = true;
							}
							System.out.println("");
						}
					}
					else if (actionNum == 2) {
						System.out.println(p.getName() + " choose to stand.");
						break;
					}
					else System.out.println("Invalid input! Please try again!");
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again!");
				}
			}
			printCard(player, dealer, players);
		}
	}

	public void dealerMove(Player dealer, ArrayList<Player> players, Deck deck) {// Only happen when all players' rounds are finished
		System.out.println(dealer.getName() + " (dealer)'s turn.");
		while (dealer.getPoints() < hitTo && !deck.isEmpty()) {
			dealer.hit(deck);
		}
		printCard(dealer, dealer, players);
	}

	public void statistic(Player dealer, Player[][] playerCnt) {// Count winners/loser and players' money
		if (playerCnt[0][0] != null) {// winner list
			System.out.print("The winner(s): ");
			for (int i = 0; i < playerCnt[0].length; i ++) {
				if (playerCnt[0][i] == null) break;
				System.out.print(playerCnt[0][i].getName());
				if (i < playerCnt[0].length - 1) System.out.print(", ");
				playerCnt[0][i].win();
				dealer.setCash(dealer.getCash() - playerCnt[0][i].getBet());
			}
			System.out.println("");
		}
		if (playerCnt[1][0] != null) {// players who tie with the dealer
			for (int i = 0; i < playerCnt[1].length; i ++) {
				if (playerCnt[1][i] == null) break;
				System.out.print(playerCnt[1][i].getName() + " ");
				if (i < playerCnt[1].length - 1) System.out.print(", ");
			}
			System.out.println("are tie with " + dealer.getName() + " (dealer)");
		}
		if (playerCnt[0][0] == null && playerCnt[1][0] == null) System.out.println(dealer.getName() + " (dealer) is the winner");// No one is win or tie
		if (playerCnt[2][0] != null) {// loser list
			System.out.println("Others are lost.");
			for (int i = 0; i < playerCnt[2].length; i ++) {
				if (playerCnt[2][i] == null) break;
				playerCnt[2][i].lose();
				dealer.setCash(dealer.getCash() + playerCnt[0][i].getBet());
			}
		}
	}

	public void showMoney(Player dealer, ArrayList<Player> players) {
		System.out.println("");
		System.out.println("Scoreboard");
		for (int i = 0; i < numberofPlayer; i ++) {
			System.out.println(players.get(i).getName() + " has $" + players.get(i).getCash());
		}
		System.out.println(dealer.getName() + " (dealer) has $" + dealer.getCash());
		System.out.println("");
	}

	public void removePlayer(ArrayList<Player> players) {// remove a single player
		for (int i = 0; i < numberofPlayer; i ++) {
			System.out.println(players.get(i).getName() + ": would you like to quit? Y - yes, other characters - no");
			String isQuit = in.next();
			if (isQuit.equals("Y") || isQuit.equals("y")) {
				for (int j = 0; j < numberofPlayer; j ++) {
					if (nameofPlayer[j].equals(players.get(i).getName())) {
						nameofPlayer[j] = null;
						break;
					}
				}
				players.remove(i);
				numberofPlayer --;
				i --;
			}
		}
	}

	public void cleanUpPlayer(ArrayList<Player> players, Deck deck) {// Reset the player to initial value
		for (int i = 0; i < numberofPlayer; i ++) {
			players.get(i).clear();
			System.out.print(players.get(i).getName() + ": Input your new bet: ");
			while (true) {
				try {
					Scanner scanner = new Scanner(System.in);
					int newBet = scanner.nextInt();
					if (newBet < 0 ) System.out.println("Invalid input! Please try again!");
					else {
						players.get(i).setBet(newBet);
						break;
					}
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again!");
				}
			}
		}
	}
	
	public int getPlayer(ArrayList<Player> players) {// Get the amount of players
		return players.size();
	}
}