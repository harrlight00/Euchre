package euchre;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> list;

	public Hand() {
		list = new ArrayList<Card>();
	}

	public void add(Card c) {
		list.add(c);
	}

	public void clear() {
		list.clear();
	}

	/*public int sum() {
		int sum = 0;
		for (int i = 0; i < list.size(); i++)
			sum += list.get(i).getValue();
		return sum;
	}*/

	public Card peek() {
		if (list.size() > 0)
			return list.get(0);
		return null;
	}
	
	public Card getCard(int i){
		if (list.size() > 0)
			return list.get(i);
		return null;
	}

	public Card removeCard(int i){
		if (list.size() > 0)
			return list.remove(i);
		return null;
	}
	
	//Tests if hand has a suit matching lead card
	public boolean goodCard(int card, Card lead){
		boolean suitMatch = false;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getSuit() == lead.getSuit())
				suitMatch = true;
		}
		if(suitMatch && list.get(card).getSuit() != lead.getSuit())
			return false;
		return true;
	}
	
	public int findWinner(int trump){
		int card = -1, suit = list.get(0).getSuit();
		
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getSuit() == trump)
				card = i;
		}
		
		if(card != -1){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).getSuit() == trump && list.get(i).getValue() > list.get(card).getValue())
					card = i;
			}
		}
		else{
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).getSuit() == suit)
					card = i;
			}
			if(card != -1){
				for(int i = 0; i < list.size(); i++){
					if(list.get(i).getSuit() == suit && list.get(i).getValue() > list.get(card).getValue())
						card = i;
				}
			}
		}
			
		return card;
	}
	
	public int size() {
		return list.size();
	}

	@SuppressWarnings("null")
	public int getIndexValue(int i) {
		if (list.get(i) != null)
			return list.get(i).getValue();
		else
			return (Integer) null;
	}
	
	@SuppressWarnings("null")
	public int getSuitValue(int i) {
		if (list.get(i) != null)
			return list.get(i).getSuit();
		else
			return (Integer) null;
	}

	public boolean contains(Card c) {
		return list.contains(c);
	}

	public void setJacks(int trump){
		for(Card card : list){
			card.setJackValue(trump);
		}
	}
	
	public String toString() {
		String toReturn = "";
		boolean first = true;
		for (int i = 0; i < list.size(); i++) {
			if (first)
				toReturn += list.get(i) + "";
			else
				toReturn += ", " + list.get(i);
			first = false;
		}
		if (toReturn.equals(""))
			return "no cards";
		return toReturn;
	}
}