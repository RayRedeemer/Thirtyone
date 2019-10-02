import java.util.ArrayList;

public class Player {
	ArrayList<Player> split = new ArrayList<Player>();//array list for storing split players
	private String name;
	private int cash;
	private Card[] ownedcards;
	private int bet;
	private int cardCnt;
	private int splitNum;
	
	public Player() {
		cash = 0;
		cardCnt = 0;
		ownedcards = new Card[21];
	}
	
	public Player(String newName, int money) {
		cash = 0;
		splitNum = 0;
		cardCnt = 0;
		ownedcards = new Card[21];
		name = newName;
		bet = money;
	}
	
	public void setBet(int money) {
		bet = money;
	}
	
	public void setFirst(Card first) {
		ownedcards[0] = first;
		cardCnt ++;
	}
	
	public void getInitialCard(Deck deck) {// Draw 2 cards at the beginning of each game
		ownedcards[0] = deck.drawCard();
		ownedcards[1] = deck.drawCard();
		cardCnt = 2;
	}
	
	/*public void getInitialCardTest(Deck deck) {
		ownedcards[0] = deck.drawCardTest();
		ownedcards[1] = deck.drawCardTest();
		cardCnt = 2;
	}*/
	
	public void hit(Deck deck) {
		if (!deck.isEmpty()) {
			ownedcards[cardCnt] = deck.drawCard();
			cardCnt ++;
		}
	}
	
	/*public void hitTEST(Deck deck) {
		if (!deck.isEmpty()) {
			ownedcards[cardCnt] = deck.drawCardTest();
			cardCnt ++;
		}
	}*/
	
	public void doubleup(Deck deck) {
		bet *= 2;
		hit(deck);
	}
	
	public void split(Deck deck) {
		splitNum ++;
		split.add(new Player(name + " split " + splitNum, bet));
		split.get(splitNum - 1).setFirst(ownedcards[0]);
		ownedcards[0] = ownedcards[1];
		cardCnt --;
		hit(deck);
		split.get(splitNum - 1).hit(deck);
	}

	/*public void splitTest(Deck deck) {                               //ru guo split cheng gong, fan hui zhen; fou ze fan hui jia
		splitNum ++;
		split.add(new Player(name + " split " + splitNum, bet));
		split.get(splitNum - 1).setFirst(ownedcards[0]);
		ownedcards[0] = ownedcards[1];
		cardCnt --;
		hitTEST(deck);
		split.get(splitNum - 1).hitTEST(deck);
	}*/
	
	public Player getSplitList(int i) {
		return split.get(i - 1);
	}
	
	public void win(Dealer dealer) {            //tong hit
		cash += bet;
		dealer.moneyChange(- bet);
	}
	
	public void lose(Dealer dealer) {         //tong hit
		cash -= bet;
		dealer.moneyChange(bet);
	}
	
	public int totalCash() {
		for (int i = 0; i < splitNum; i ++) {
			cash += split.get(i).totalCash();
		}
		return cash;
	}
	
	public void cleanUp(Deck deck) {
		splitNum = 0;
		bet = 0;
		cardCnt = 0;
		ownedcards = new Card[21];
		split.clear();
		getInitialCard(deck);
	}
	
	public Card[] getCards() { //tong hit
		return ownedcards;
	}
	
	public int getSplit() {
		return splitNum;
	}
	
	public boolean isBust() {
		if (getPoints() > 21) return true;
		return false;
	}
	
	public boolean isBlackjack() {
		return getPoints() == 21;
	}
	
	public int getPoints() {// Calculate current points that the player has
		int points = 0;
		for (int j = 0; j < ownedcards.length; j ++) {
			if (ownedcards[j] == null) break;
			points += ownedcards[j].getPoint();
		}
		for (int j = 0; (j < ownedcards.length) && (points > 21); j ++) {
			if (ownedcards[j] == null) break;
			if (ownedcards[j].getPoint() == 11) points -= 10;
		}
		return points;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return cash;
	}

//	public int getRecursiveSize (Player p) {
//		int sum = 0;
//		if (p.getSplit() != 0) {
//			for (int i = 0; i < p.getSplit(); i ++){
//				sum = sum + getRecursiveSize(p.split.get(i)) + 1;
//			}
//			return sum;
//		}
//		else return 0;
//	}
//
//	public ArrayList<Player> recursiveAccess (Player p) {// Use recursive method to calculate split players
//		ArrayList<Player> players = new ArrayList<>();
//		if (p.getSplit() != 0) {
//			players.add(p);
//			for (int i = 0; i < p.getSplit(); i ++){
//				players.addAll(recursiveAccess(p.split.get(i)));
//			}
//			return players;
//		}
//		else {
//			players.add(p);
//			return players;
//		}
//	}
}