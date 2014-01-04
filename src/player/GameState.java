package player;

import java.util.ArrayList;

import cards.Card;
import game.Move;

/**
 * Message class to relay game state information from game to player
 * Should be created on every game state change and sent to ALL players
 * state is IMMUTABLE (well as much as possible)
 */
public class GameState
{
	public final boolean accepted;			//if the previous move by prevoius player was accepted
	private final ArrayList<Card> cards;	//cards of the prevoius move
	private final Player previousPlayer; 	//player of the prevoius move
	private final ArrayList<Card> discard;	//current discard pile
	private final Player nextPlayer;		//next player up (i guess its the current player since the previous guy just finished)
	
	/**
	 * Construct a new message - just fill in the fields
	 * @param accepted - did you like my moves?
	 * @param move - remind me what my move was
	 * @param discard - trash? whats in the trash?
	 * @param nextPlayer - oh good. next player (null if no player is next i guess)
	 */
	public GameState(boolean accepted, Move move, ArrayList<Card> discard, Player nextPlayer)
	{
		this.accepted = accepted;
		this.cards = move.getCards();
		this.previousPlayer = move.getPlayer();
		this.discard = new ArrayList<Card>(discard);
		this.nextPlayer = nextPlayer;
	}
	
	/**
	 * Get the cards of the last move
	 * @return
	 */
	public ArrayList<Card> getLastMove()
	{
		return new ArrayList<Card>(cards);
	}
	
	/**
	 * Get the player 
	 * @return - player of the last move
	 */
	public Player getPlayer()
	{
		return previousPlayer;
	}
	
	/**
	 * get the discard pile 
	 * @return - ooohhh trash
	 */
	public ArrayList<Card> getDiscard()
	{
		return new ArrayList<Card>(discard);
	}
	
	/**
	 * get the next player
	 * @return - who is the next guy??
	 */
	public Player getNextPlayer()
	{
		return nextPlayer;
	}
}
