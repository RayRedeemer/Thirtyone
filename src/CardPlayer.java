
public abstract class CardPlayer {
	//Abstract class for all cardplayer
	
	private String name;
	private int cash;
	private Card[] cards;
	private int bet;
	private int cardCnt;
	private boolean isFold;
	
	private static final int maxCards = 21; 
	
	public CardPlayer() {
	}
	
	public CardPlayer(String name, int cash) {
		this.name = name;
		this.cash = cash;
		initCards(maxCards);
		bet = 0;
		cardCnt = 0;
		isFold = false;
	}
	
	public void setName(String string) {
		name = string;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCash(int i) {
		cash = i;
	}
	
	public int getCash() {
		return cash;
	}
	
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getBet() {
		return bet;
	}
	
	public void addCard(Card card) {
		cards[cardCnt++] = card;
	}
	
	public Card getCard(int index) {
		return cards[index];
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	public int getCardCnt() {
		return cardCnt;
	}
	
	public void initCards(int num) {
		cards = new Card[num];
	}
	
	public boolean isFold() {
		return isFold;
	}
	
	public void fold() {
		isFold = true;
	}
	
	public void clear() {
		bet = 0;
		cardCnt = 0;
		cards = new Card[cards.length];
		isFold = false;
	}
	
	public abstract void setInitCards(Deck deck);
	public abstract int getPoints();
	public abstract void win();
	public abstract void lose();
}
