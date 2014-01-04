package player;

import java.util.ArrayList;

import cards.Card;
import game.Move;

public class Outcome
{
	public final boolean accepted;
	private final ArrayList<Card> cards;
	
	public Outcome(boolean accepted, Move move)
	{
		this.accepted = accepted;
		this.cards = move.getCards();
	}
	
	public ArrayList<Card> getCards()
	{
		return new ArrayList<Card>(cards);
	}
}
