package player;

import java.util.ArrayList;

import cards.Card;
import game.Move;

/**
 * Message class to relay game state information from game to player
 *
 */
public class GameState
{
	public final boolean accepted;
	private final ArrayList<Card> cards;
	private final Player previousPlayer;
	private final ArrayList<Card> discard;
	private final Player nextPlayer;
	
	public GameState(boolean accepted, Move move, ArrayList<Card> discard, Player nextPlayer)
	{
		this.accepted = accepted;
		this.cards = move.getCards();
		this.previousPlayer = move.getPlayer();
		this.discard = new ArrayList<Card>(discard);
		this.nextPlayer = nextPlayer;
	}
	
	public ArrayList<Card> getLastMove()
	{
		return new ArrayList<Card>(cards);
	}
	
	public Player getPlayer()
	{
		return previousPlayer;
	}
	
	public ArrayList<Card> getDiscard()
	{
		return new ArrayList<Card>(discard);
	}
	
	public Player getNextPlayer()
	{
		return nextPlayer;
	}
}
