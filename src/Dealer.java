// Class of dealer

public class Dealer {
	private Card[] card;
	private int numOfCards;
	private int money;
	private String name;
	private boolean reveal;// Decide whether the second card is face down
	
	Dealer () {
		numOfCards = 0;
		money = 0;
		reveal = false;
		card = new Card[21];
	}
	
	public void getInitialCard(Deck deck) {
		card[0] = deck.drawCard();
		card[1] = deck.drawCard();
		numOfCards += 2;
	}

	public void hit(Deck deck) {
		if (!deck.isEmpty()) {
			Card newCard = deck.drawCard();
			card[numOfCards] = newCard;
			numOfCards ++;
		}
	}

	public boolean isBust() {
		if (getPoint() > 21) return true;
		return false;
	}
	
	public boolean isBlackjack() {
		return getPoint() == 21;
	}

	public void clear (Deck deck) {
		card = new Card[21];
		numOfCards = 0;
		reveal = false;
		getInitialCard(deck);
	}

	public int getPoint() {// Calculate current points that the dealer has
		int points = 0;
		for (int j = 0; j < this.numOfCards; j ++) {
			points += card[j].getPoint();
		}
		for (int j = 0; (j < this.numOfCards) && (points > 21); j ++) {
			if (card[j].getPoint() == 11) points -= 10;
		}
		return points;
	}

	public Card[] getCard() {
		return card;
	}

	public int getMoney() {
		return money;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setReveal() {
		reveal = true;
	}
	
	public boolean getReveal() {
		return reveal;
	}
	
	public void moneyChange(int amount) {
		money += amount;
	}
}