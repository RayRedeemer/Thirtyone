public class Card {
	private int point;
	private String card;

	@Override
	public String toString() {// Help to print card index
		return card;
	}

	Card () {}

	Card (String card, int point) {
		this.card = card;
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

	public String getCard() {
		return card;
	}
	
	public boolean equals(Object card) {
		if (this.toString() == card.toString()) return true;
		return false;
	}
}
