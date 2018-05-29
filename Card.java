package euchre;

public class Card {
	private int suit, value;

	public Card(int s, int v) {
		if (s >= 0 && s <= 3)
			suit = s;
		else
			suit = 0;
		if (v >= 9 && v <= 14)
			value = v;
		else
			value = 9;
	}

	public int getValue() {
		return value;
	}
	
	public int getSuit() {
		return suit;
	}

	public void setJackValue(int trump){
		if(value == 11){	
			if(trump == suit)
				value = 16;
			else if(trump == ((suit + 2) % 4)){
				value = 15;
				suit = trump;
			}
		}
	}
	
	public String toString() {
		if (suit == 0) {
			if (value >= 9 && value < 11)
				return value + " of Spades";
			if (value == 11)
				return "Jack of Spades";
			if (value == 12)
				return "Queen of Spades";
			if(value == 13)
				return "King of Spades";
			if(value == 14)
				return "Ace of Spades";
			if(value == 15)
				return "Left Bauer of Spades (Jack of Clubs)";
			if(value == 16)
				return "Right Bauer of Spades (Jack of Spades)";
		}

		if (suit == 1) {
			if (value >= 9 && value < 11)
				return value + " of Hearts";
			if (value == 11)
				return "Jack of Hearts";
			if (value == 12)
				return "Queen of Hearts";
			if (value == 13)
				return "King of Hearts";
			if(value == 14)
				return "Ace of Hearts";
			if(value == 15)
				return "Left Bauer of Hearts (Jack of Diamonds)";
			if(value == 16)
				return "Right Bauer of Hearts (Jack of Hearts)";
		}

		if (suit == 2) {
			if (value >= 9 && value < 11)
				return value + " of Clubs";
			if (value == 11)
				return "Jack of Clubs";
			if (value == 12)
				return "Queen of Clubs";
			if (value == 13)
				return "King of Clubs";
			if(value == 14)
				return "Ace of Clubs";
			if(value == 15)
				return "Left Bauer of Clubs (Jack of Spades)";
			if(value == 16)
				return "Right Bauer of Clubs (Jack of Clubs)";
		}

		else {
			if (value >= 9 && value < 11)
				return value + " of Diamonds";
			if (value == 11)
				return "Jack of Diamonds";
			if (value == 12)
				return "Queen of Diamonds";
			if (value == 13)
				return "King of Diamonds";
			if(value == 14)
				return "Ace of Diamonds";
			if(value == 15)
				return "Left Bauer of Diamonds (Jack of Hearts)";
			if(value == 16)
				return "Right Bauer of Diamonds (Jack of Diamonds)";
		}
		return("Card not found");
	}

}
