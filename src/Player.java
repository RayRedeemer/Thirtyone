public class Player extends CardPlayer{
	//Thirty-one player class
	
	private static final int maxCards = 16;
	public static final int thirtyone = 31;
	private static final int fourteen = 14;
	
	public Player() {
	}
	
	public Player(String newName, int money) {
		super(newName,money);
		initCards(maxCards);
	}
		
	public void hit(Deck deck) {
		if (!deck.isEmpty()) {
			addCard(deck.drawCard());
		}
	}
	
	public boolean isBust() {
		return getPoints() > thirtyone;
	}
	
	public boolean isThirtyone() {
		return ( getPoints() == thirtyone && getCardCnt() == 3 );
	}
	
	public int getThirtyone() {
		return thirtyone;
	}
	
	public boolean isFourteen() {
		return getPoints() == fourteen;
	}
	
	@Override
	public void setInitCards(Deck deck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void win() {
		// TODO Auto-generated method stub
		setCash(getCash() + getBet() * 2);
	}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPoints() {
		// TODO Auto-generated method stub
		int points = 0;
		for (int j = 0; j < getCardCnt(); j ++) {
			points += getCard(j).getPoint();
		}
		for (int j = 0; (j < getCardCnt()) && (points > thirtyone); j ++) {
			if (getCard(j).getPoint() == 11) {
				points -= 10;
				break;
			}
		}
		return points;
	}
}