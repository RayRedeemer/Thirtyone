// Actions and logic of the game

import java.util.ArrayList;
import java.util.Scanner;

public class BlackJackGameLogic {
	int numberofPlayer;
	int numberofDeck;
	String[] nameofPlayer = new String[0];
	Scanner in = new Scanner(System.in);

	public void initializeDeck(Deck deck) {
		System.out.print("Enter the amount of deck: ");
		while (true) {
			try {
				Scanner scanner = new Scanner(System.in);
				numberofDeck = scanner.nextInt();
				if (numberofDeck <= 0) System.out.println("Invalid input! Please try again!");
				else {
					deck.setDeck(numberofDeck);
					break;
				}
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again!");
			}
		}
	}

	public void initializeDealer(Dealer dealer, Deck deck) {
		System.out.println("Is the dealer a player or an AI? Y - player, other characters - AI.");
		String isPlayer = in.next();
		if (isPlayer.equals("Y") || isPlayer.equals("y")) {// Real player can decide the dealer's name
			System.out.print("Enter the name of dealer: ");
			while (true) {
				String name = in.next();
				if (name == null || name.equals("Computer")) System.out.println("Invalid input! Please try again!");
				else {
					dealer.setName(name);
					break;
				}
			}
		}
		else dealer.setName("Computer");
		dealer.getInitialCard(deck);
	}

	public void initializePlayer(ArrayList<Player> players, Dealer dealer, Deck deck) {
		System.out.print("Enter the amount of players: ");
		while (true) {
			try {
				Scanner scanner = new Scanner(System.in);
				numberofPlayer = scanner.nextInt();
				if (numberofPlayer < 1) System.out.println("Invalid input! Please try again!");
				else break;
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again!");
			}
		}
		nameofPlayer = new String[numberofPlayer];
		for (int i = 1; i <= numberofPlayer; i ++) {// Initialize each player to the array list one by one
			String name;
			int bet;
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
				if (isRepeat || name.equals(dealer.getName())) System.out.println("Invalid input! Please try again!");
				else break;
			}
			// Each player make their bet separately
			System.out.print("Enter the bet amount of player " + i + ": ");
			while (true) {
				try {
					Scanner scanner = new Scanner(System.in);
					bet = scanner.nextInt();
					if (bet < 0) System.out.println("Invalid input! Please try again!");
					else break;
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again!");
				}
			}
			players.add(new Player(name, bet));
			players.get(i - 1).getInitialCard(deck);
			nameofPlayer[i - 1] = name;
		}
	}

	public void printCard(Dealer dealer, ArrayList<Player> players) {
		System.out.print(dealer.getName() + " (dealer): " + dealer.getCard()[0]);
		if (dealer.getReveal()) {
			for (int i = 1; i < dealer.getCard().length; i ++) {
				if (dealer.getCard()[i] == null) break;
				System.out.print(" " + dealer.getCard()[i]);
			}
			if (dealer.isBust()) System.out.print(" (bust)");
		}
		else System.out.print(" X");
		System.out.println("");
		for (int i = 0; i < numberofPlayer; i ++) {
			recursivePrint(players.get(i));
		}
		System.out.println("");
	}

//	private void recursivePrint(Player p) {// Use recursive method to print every split players
//		Card[] card = p.getCards();
//		System.out.print(p.getName() + ": ");
//		for (int k = 0; k < card.length; k ++) {
//			if (card[k] == null) break;
//			System.out.print(card[k] + " ");
//		}
//		if (p.isBust()) System.out.print(" (bust)");
//		System.out.println("");
//		if (p.getSplit() != 0) {
//			for (int d = 0; d < p.getSplit(); d++){
//				recursivePrint( p.getSplitList(d+1) );
//			}
//		}
//		else return;
//	}

	public void playerMove(Player player, Dealer dealer, ArrayList<Player> players, Deck deck) {
		if (player == null) return;
		for (int j = 0; j <= player.getSplit(); j ++) {
			Player p;
			if (j == 0) p = player;
			else {
				p = player.getSplitList(j);
				playerMove(p, dealer, players, deck);
				continue;
			}
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
							printCard(dealer, players);
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
					else if (actionNum == 3) {
						System.out.println(p.getName() + " choose to double up.");
						p.doubleup(deck);
						break;// Double up only has 1 hit chance
					}
					else if (actionNum == 4 && canSplit) {
						System.out.println(p.getName() + " choose to split.");
						p.split(deck);
						printCard(dealer, players);
						System.out.print("Choose action: 1 - hit; 2 - stand; 3 - double up");
						canSplit = false;
						if (p.getCards()[0].getCard().equals(p.getCards()[1].getCard())) {
							System.out.print("; 4 - split");
							canSplit = true;
						}
						System.out.println("");
					}
					else System.out.println("Invalid input! Please try again!");
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again!");
				}
			}
			printCard(dealer, players);
		}
	}

	public void dealerMove(Dealer dealer, ArrayList<Player> players, Deck deck) {// Only happen when all players' rounds are finished
		System.out.println(dealer.getName() + " (dealer)'s turn.");
		dealer.setReveal();
		while (dealer.getPoint() < 17 && !deck.isEmpty()) {
			dealer.hit(deck);
		}
		printCard(dealer, players);
	}

	public void statistic(Dealer dealer, Player[][] playerCnt) {// Count winners/loser and players' money
		if (playerCnt[0][0] != null) {// winner list
			System.out.print("The winner(s): ");
			for (int i = 0; i < playerCnt[0].length; i ++) {
				if (playerCnt[0][i] == null) break;
				System.out.print(playerCnt[0][i].getName());
				if (i < playerCnt[0].length - 1) System.out.print(", ");
				playerCnt[0][i].win(dealer);
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
				playerCnt[2][i].lose(dealer);
			}
		}
	}

	public void showMoney(Dealer dealer, ArrayList<Player> players) {
		System.out.println("");
		System.out.println("Scoreboard");
		for (int i = 0; i < numberofPlayer; i ++) {
			if (players.get(i).getSplit() > 0) players.get(i).totalCash();
			System.out.println(players.get(i).getName() + " has $" + players.get(i).getMoney());
		}
		System.out.println(dealer.getName() + " (dealer) has $" + dealer.getMoney());
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
			players.get(i).cleanUp(deck);
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

	/*public void addPlayer(ArrayList<Player> players, Dealer dealer, Deck deck) {// Add more players to the next round (not finished)
		System.out.println("How many new players will come to the next round? Input 0 for none.");
		try {
			Scanner scanner = new Scanner(System.in);
			int addNum = scanner.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input! Please try again!");
		}
		for (int i = 0; i < addNum; i ++) {
			numberofPlayer ++;
			String name;
			int bet;
			System.out.print("Enter the name of new player " + i + ": ");
			while (true) {
				name = in.next();
				boolean isRepeat = false;
				for (int j = 0; j < numberofPlayer; j ++) {
					if (nameofPlayer[j] == null) continue;
					if (nameofPlayer[j].equals(name)) {
						isRepeat = true;
						break;
					}
				}
				if (isRepeat || name.equals(dealer.getName())) System.out.println("Invalid input! Please try again!");
				else break;
			}
			System.out.print("Enter the bet amount of new player " + i + ": ");
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
			players.add(new Player(name, bet));
			players.get(numberofPlayer).getInitialCard(deck);
			numberofPlayer ++;
		}
	}*/

	public int getPlayer(ArrayList<Player> players) {// Get the amount of players
		return players.size();
	}
}