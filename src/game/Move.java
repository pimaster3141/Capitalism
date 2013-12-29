package game;

import java.util.ArrayList;

import cards.Card;
import player.Player;

/**
 * Object representing a 'move' becasue objects are much easier to deal with than strings 
 * contains helper methods to compare different moves and classify the type of move 
 *
 * moves are immutable
 */
public class Move implements Comparable<Move>
{
	private final Player player;
	private final ArrayList<Card> cards;
	
	public Move(Player player, ArrayList<Card> cards)
	{
		this.player = player;
		this.cards = new ArrayList<Card>(cards);
	}
	
	public ArrayList<Card> getCards()
	{
		return new ArrayList<Card>(cards);
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public int compareTo(Move arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
