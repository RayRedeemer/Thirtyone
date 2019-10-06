// Actions and logic of the game

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ThirtyoneGameLogic {
	int numberofPlayer;
	final int numberofDeck = 2;
	String[] nameofPlayer;
	Scanner in = new Scanner(System.in);
	final int minPlayers = 2;
	final int maxPlayers = 9;
	final int hitTo = 27;
	
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
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
		int cash;
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
		nameofPlayer = new String[numberofPlayer];
		for (int i = 1; i <= numberofPlayer; i ++) {// Initialize each player to the array list one by one
			String name;
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
			players.add(new Player(name, cash));
			nameofPlayer[i - 1] = name;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Player chooseDealer(Player dealer, ArrayList<Player> players) {
		//repeatedly choose and ask the capable new dealer
		//need to be implemented
		
		Player p = dealer;
		Player tempP = p;
		ArrayList<Player> player = (ArrayList<Player>) players.clone();
		for(int i = 0; i < player.size(); i++) {
			for(int j = 0; j < player.size(); j++) {
				Player np = player.get(j);
				if(np.getCash() > p.getCash()) {
					p = np;
				}
			}
			if(p != dealer) {
				System.out.println("Player "+p.getName()+" , would you like to be dealer? (Y/n): ");
				if(in.next().equals("Y")) {
					break;
				}
				else {
					player.remove(p);
					p = tempP;
				}
			}
			i--;
		}
		return p;
	}

	public Player chooseDealerWithoutDealer(ArrayList<Player> players) {
		//repeatedly choose and ask the capable new dealer
		//need to be implemented


		ArrayList<Player> player = (ArrayList<Player>) players.clone();
		int size = player.size();
		int max = -1;
		Player p = new Player();
		Player temp = p;
		int t=0;
		do{
			for(int i = 0; i < player.size(); i++) {
				for(int j = 0; j < player.size(); j++) {
					Player np = player.get(j);
					if(np.getCash() > max) {
						p = np;
						max = np.getCash();
					}
				}

				System.out.println("Player "+p.getName()+" , would you like to be dealer? (Y/n): ");
				if(in.next().equals("Y")) {
					break;
				}
				else {
					player.remove(p);
					max = -1;
					t++;
				}
				i--;

			}
			if (t == size ) {
				System.out.println("No one want to be the dealer, Game Over!!!");
				System.exit(0);

			}
			else {
				break;
			}
		} while (true);


		return p;
	}
	
	public Player chooseDealer(ArrayList<Player> players) {
		//choose the first dealer by entering console
		
		System.out.print("Enter the name of dealer: ");
		while (true) {
			String name = in.next();
			for(int i = 0; i < numberofPlayer; i++) {
				Player p = players.get(i);
				if( p.getName().equals(name)) {
					return p;
				}
			}
			System.out.println("Invalid input! Please try again!");
		}
	}
	
	public Player chooseDealer(ArrayList<Player> players, String name) {
		//choose the first dealer by string in parameter
		
		for(int i = 0; i < numberofPlayer; i++) {
			Player p = players.get(i);
			if( p.getName() == name) {
				return p;
			}
		}
		System.out.println("Invalid input! Please try again!");
		return null;
	}
	
	public void setUp(Player dealer, ArrayList<Player> players, Deck deck) {
		//setup the game Thirty-one
		//deal a first card to each player (including dealer),
		//players bet, and deal the 2nd & 3rd
		
		int index = players.indexOf(dealer);
		int size = numberofPlayer;
		
		deck.shuffle();
		for(int i = 0; i < size; i++) {
			players.get(i).hit(deck);
		}
		for(int i = index + 1; i != index; i++) {
			if(i == size) {
				i = -1;
				continue;
			}
			Player p = players.get(i);
			if(p == dealer)continue;
			printCard(p, dealer, players);
			showMoney(dealer,players);
			do{
				System.out.println("Player "+p.getName()+", please bet (enter an appropriate number) or fold (f):");
				String str = in.next();
				if(!isNumeric(str)) {
					System.out.println("Player "+p.getName()+" folded.");
					p.fold();
					break;
				}
				else {
					int bet = Integer.parseInt(str);
					if(bet > p.getCash()) {
						System.out.println("Insuffcient cash.");
					}
					else{
						p.setBet(bet);
						p.setCash(p.getCash() - p.getBet());
						showMoney(dealer, players);
						break;
					}
				}
			}while(true);
		}
		for(int i = 0; i < size; i++) {
			Player p = players.get(i);
			if(p.isFold())continue;
			p.hit(deck);
			p.hit(deck);
		}
	}
	
	public void printCard(Player currentPlayer, Player dealer, ArrayList<Player> players) {
		if(dealer == currentPlayer) {
			System.out.print(dealer.getName() + " (dealer): ");
			for (int i = 0; i < dealer.getCardCnt(); i ++) {
				Card card = dealer.getCard(i);
				if (card == null) break;
				System.out.print(" " + card);
			}
			if (dealer.isBust()) System.out.print(" (bust)");
			System.out.println("");
			for (int i = 0; i < numberofPlayer; i ++) {
				Player p = players.get(i);
				if(p != dealer) {
					System.out.print(p.getName()+":");
					for(int j = 0; j < p.getCardCnt(); j++) {
						System.out.print(" "+p.getCard(j));
					}
					System.out.println();
				}
			}
			System.out.println("");
		}
		else{
			System.out.print(dealer.getName() + " (dealer): "+dealer.getCard(0)+" X X");
			System.out.println("");
			for (int i = 0; i < numberofPlayer; i ++) {
				Player p = players.get(i);
				if(p != dealer) {
					System.out.print(p.getName()+":");
					for(int j = 0; j < p.getCardCnt(); j++) {
						if(p != currentPlayer && j == 0) {
							System.out.print(" X");
						}
						else{
							System.out.print(" "+p.getCard(j));
						}
					}
					System.out.println();
				}
			}
			System.out.println("");
		}
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
			while (!deck.isEmpty() && !p.isFold()) {
				System.out.print("Choose action: 1 - hit; 2 - stand");
				System.out.println("");
				try {
					in = new Scanner(System.in);
					int actionNum = in.nextInt();
					if (actionNum == 1) {
						System.out.println(p.getName() + " choose to hit");
						p.hit(deck);
						if (p.getPoints() >= Player.thirtyone) {
							System.out.println(p.getName() + " has reached or exceeded 31 points.");
							break;
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
				printCard(player, dealer, players);
			}
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
			for (int i = 0; playerCnt[0][i] != null; i ++) {
				if (playerCnt[0][i] == null) break;
				System.out.print(playerCnt[0][i].getName());
				if (i < playerCnt[0].length - 1) System.out.print(", ");
				playerCnt[0][i].win();
				dealer.setCash(dealer.getCash() - playerCnt[0][i].getBet());
			}
			System.out.println("");
		}
		if (playerCnt[2][0] != null) {// loser list
			for (int i = 0; playerCnt[2][i] != null; i ++) {
				playerCnt[2][i].lose();
				dealer.setCash(dealer.getCash() + playerCnt[2][i].getBet());
			}
		}
	}

	public void showMoney(Player dealer, ArrayList<Player> players) {
		System.out.println("");
		System.out.println("Cash Pool");
		for (int i = 0; i < numberofPlayer; i ++) {
			System.out.println(players.get(i).getName() + " has $" + players.get(i).getCash());
		}
		System.out.println(dealer.getName() + " (dealer) has $" + dealer.getCash());
		System.out.println("");
	}

	public Player removePlayer(ArrayList<Player> players, Player dealer) {// remove a single player
		Player temp = new Player("notRemoveDealer", 111);
		for (int i = 0; i < numberofPlayer; i ++) {
			System.out.println(players.get(i).getName() + ": would you like to quit? Y - yes, other characters - no");
			String isQuit = in.next();
			if (isQuit.equals("Y") || isQuit.equals("y")) {
				for (int j = 0; j < numberofPlayer; j ++) {

					if (nameofPlayer[j] == null) {
						continue;
					}
					/*if (players.get(j) == dealer ) {
						nameofPlayer[j] = null;
					}*/
					if (nameofPlayer[j].equals(players.get(i).getName())) {
						nameofPlayer[j] = null;
						break;
					}
				}
				if(players.get(i) == dealer){
					temp = players.get(i);
				}
				players.remove(i);
				numberofPlayer --;
				i --;
			}
		}
		return temp;
	}

	public void cleanUpPlayer(ArrayList<Player> players, Deck deck) {// Reset the player to initial value
		for (int i = 0; i < numberofPlayer; i ++) {
			players.get(i).clear();
		}
	}
	
	public int getPlayer(ArrayList<Player> players) {// Get the amount of players
		return numberofPlayer;
	}
}