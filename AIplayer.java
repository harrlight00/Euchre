package euchre;

import java.util.ArrayList;
import java.util.List;

public class AIplayer {

	public AIplayer() {
		
	}

	public boolean callTrump(Card card, boolean isTeamDealer, boolean isDealer, Hand hand){
		int suit = card.getSuit(), count = 0;
		for(int i = 0; i < hand.size(); i++){
			if(hand.getSuitValue(i) == suit){
				count += hand.getSuitValue(i);
				if(hand.getSuitValue(i) == 11)
					count += 5;
			}
			else if((hand.getSuitValue(i) + 2) % 4 == suit && hand.getIndexValue(i) == 11)
				count += 15;
		}
		if(isDealer){
			count += card.getValue();
			if(card.getValue() == 11)
				count += 5;
		}
		if(!isTeamDealer){
			count -= (card.getValue() / 2);
			if(card.getValue() == 11)
				count -= 3;
		}
		if(count >= 20)
			return true;
		return false;
	}
	
	public int callTrump(Hand hand, boolean stick){
		List<Integer> list = new ArrayList<Integer>();
		list.add(checkSuit(0, hand));
		list.add(checkSuit(1, hand));
		list.add(checkSuit(2, hand));
		list.add(checkSuit(3, hand));
		int maxc = 0;
		for(int i = 1; i <= 3; i++){
			if(list.get(i) >= list.get(maxc))
				maxc = i;
		}
		if(list.get(maxc) >= 20 || stick)
			return maxc;
		return -1;
	}
	
	public int checkSuit(int sui,Hand hand){
		int suit = sui, count = 0;
		for(int i = 0; i < hand.size(); i++){
			if(hand.getSuitValue(i) == suit){
				count += hand.getSuitValue(i);
				if(hand.getSuitValue(i) == 11)
					count += 5;
			}
			else if((hand.getSuitValue(i) + 2) % 4 == suit && hand.getIndexValue(i) == 11)
				count += 15;
		}
		return count;
	}
	
	public int findWorst(Hand hand, int trump){
		int worst = -1;
		for(int i = 0; i < hand.size(); i++){
			if(hand.getCard(i).getSuit() != trump){
				worst = i;
			}
		}
		for(int i = 0; i < hand.size(); i++){
			if(hand.getCard(i).getSuit() != trump){
				if(hand.getCard(worst).getValue() > hand.getCard(i).getValue())
					worst = i;
			}
		}
		return worst;
	}
	
	public int throwTrump(Hand hand, int trump){
		int card = -1;
		for(int i = 0; i < hand.size(); i++){
			if(hand.getCard(i).getSuit() == trump){
				card = i;
			}
		}
		for(int i = 0; i < hand.size(); i++){
			if(hand.getCard(i).getSuit() == trump){
				if(hand.getCard(card).getValue() > hand.getCard(i).getValue())
					card = i;
			}
		}
		return card;
	}
	
	public int lead(boolean hasCalled, int trump, Hand hand){
		int card = 0;
		if(hasCalled){
			for(int i = 0; i < hand.size(); i++){
				if(hand.getSuitValue(i) == trump)
					card = i;
			}
			for(int i = 0; i < hand.size(); i++){
				if(hand.getSuitValue(i) == trump)
					if(hand.getIndexValue(i) > hand.getIndexValue(card))
						card = i;
			}
		}
		else{
			for(int i = 0; i < hand.size(); i++){
				if(hand.getSuitValue(i) != trump)
					card = i;
			}
			for(int i = 0; i < hand.size(); i++){
				if(hand.getSuitValue(i) != trump)
					if(hand.getIndexValue(i) >= hand.getIndexValue(card))
						card = i;
			}
		}
		return card;
	}

	public int play(int numCards, Card card1, Card card2, Card card3, Hand hand, int trump){
		int card = -1, min = -1;
		List<Card> list = new ArrayList<Card>();
		
		boolean goodCard = true;
		int lead = card1.getSuit();
		
		list.add(card1);
		if(numCards >= 2)
			list.add(card2);
		if(numCards >= 3)
			list.add(card3);
		
		for(int i = 0; i < hand.size(); i++){
			if(lead == hand.getSuitValue(i)){
				card = i;
				min = i;
			}
		}
		
		if(card != -1){
			for(int i = 0; i < hand.size(); i++){
				if(lead == hand.getSuitValue(i)){
					if(hand.getIndexValue(i) > hand.getIndexValue(card))
						card = i;
					else if(hand.getIndexValue(i) < hand.getIndexValue(card))
						min = i;
				}
			}
			for(int i = 0; i < numCards; i++){
				if(hand.getCard(card).getValue() < list.get(i).getValue())
					goodCard = false;
			}
			if(!goodCard)
				card = min;
		}
		
		if(card == -1)
			card = throwTrump(hand,trump);
		if(card == -1)
			card = findWorst(hand, trump);
		//The fuck it clause
		if(card == -1)
			card = 0;
		
		return card;
	}

}
