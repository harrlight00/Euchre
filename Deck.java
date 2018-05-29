package euchre;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> deck = new ArrayList<Card>();

	public Deck() {
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 9; value <= 14; value++)
				deck.add(new Card(suit, value));
		}
	}

	public Card draw() {
		int card;
		if (deck.size() > 0) {
			card = (int) (Math.random() * deck.size());
			return deck.remove(card);
		}
		this.reset();
		card = (int) (Math.random() * deck.size());
		return deck.remove(card);
	}

	public void reset() {
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 9; value <= 14; value++)
				deck.add(new Card(suit, value));
		}
	}
}
