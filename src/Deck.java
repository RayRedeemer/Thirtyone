import java.util.Random;

public class Deck {
	public static final int cardsInDeck = 52;
	private Card[] deck;
	private int numberofDeck; // How many pokers (52 cards in each)
	String[] tempName = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	Integer[] tempPoint = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

	public Deck () {}
	
	public void setDeck(int newNumber) {// Use cycle to set all cards
		numberofDeck = newNumber;
		deck = new Card[cardsInDeck * numberofDeck];
		for (int i = 0; i < 4 * numberofDeck; i ++) {
			for (int j = 0; j < 13; j ++) {
				deck[i * 13 + j] = new Card(tempName[j], tempPoint[j]);
			}
		}
	}

	public Card drawCard () {// Randomly get a card from the deck
		Random r = new Random () ;
		int drawNumber;
		if (this.isEmpty()) {
			System.out.println("Empty deck.");
			return null;
		}
		do {
			drawNumber = r.nextInt(cardsInDeck * numberofDeck);
		} while (deck[drawNumber] == null);
		Card tempCard = deck[drawNumber];
		deck[drawNumber] = null;
		return tempCard;
	}

	/*public Card drawCardTest () {
		int drawNumber;
		if (this.isEmpty()) {
			System.out.println("Empty deck.");
			return null;
		}
		Card tempCard = deck[5];
		return tempCard;
	}*/

	public Card[] getDeck() {
		return deck;
	}

	public int getNumberofDeck() {
		return numberofDeck;
	}
	
	public boolean isEmpty() {// No card in the deck
		for (int i = 0; i < cardsInDeck * numberofDeck; i ++) {
			if (deck[i] != null) return false;
		}
		return true;
	}
}
