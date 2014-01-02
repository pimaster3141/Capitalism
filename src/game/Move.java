package game;

import java.util.ArrayList;

import cards.Card;
import player.Player;

/**
 * Object representing a 'move' because objects are much easier to deal with than strings 
 * contains helper methods to compare different moves and classify the type of move 
 *
 * A move is a player playing down zero or more cards. 
 * moves are immutable
 */
public class Move //implements Comparable<Move>
{
	private final Player player;
	private final ArrayList<Card> cards;
	
	/**
	 * Constructs a move object with a Player and a collection of cards
	 * @param player 
	 * @param cards, collection of cards with same rank (empty ok, treated as pass)
	 * @throws RuntimeException if all cards do not have the same rank
	 */
	public Move(Player player, ArrayList<Card> cards) throws RuntimeException
	{
		this.player = player;
		this.cards = new ArrayList<Card>(cards);
		if (!cardsEqualRank(this.cards)) throw new RuntimeException("Card ranks not equal");
	}
	
	/**
	 * Checks if cards are all of equal rank. Helper for constructor. 
	 * @param cards ArrayList of cards to check
	 * @return true if all cards have equal rank, false otherwise
	 * An empty list is considered to have equal rank. 
	 */
	private static boolean cardsEqualRank(ArrayList<Card> cards){
		if (cards.size()<=1) return true;
		int maxRank=cards.get(0).getRank();
		for (Card c: cards)
			if (c.getRank()!=maxRank)
				return false;
		return true;
	}
	
	public ArrayList<Card> getCards()
	{
		return new ArrayList<Card>(cards);
	}

	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Gets rank of one move
	 * atm assumes all cards are equal, so rank of a move is the same as rank of the first card
	 * @return the rank of the first card in this.cards, or -1 if the set is empty
	 */
	public int getRank(){
		if (this.cards.isEmpty()) return -1;
		return this.cards.get(0).getRank();
	}
	
	/**
	 * Compares moves this and arg0 based on cards
	 * For this to be > arg0 (valid on normal turn), this must have >= the number
	 *  of cards of arg0, and the rank of the cards in this must be strictly greater 
	 *  than the rank of cards in arg0. 
	 * For this to be = arg0 (valid in spam), the number of cards is irrelevant: 
	 *  the rank of the cards must be equal to the rank of cards in arg0
	 * 
	 * @return 
	 *     0 if valid as spam
	 *     positive integer if valid on normal turn
	 *     negative integer in all other cases
	 */
	public int compareTo(Move arg0)
	{
		//1. Normal turns: play at least as many cards as arg0
		if (this.getCards().size()>=arg0.getCards().size()){
			return this.getRank()-arg0.getRank();
		}
		//2. Spams not caught in part 1: rank of this = rank of arg0. Leave game to do any other checks
		if (this.getRank()==arg0.getRank()) return 0;
		//failing this, is invalid
		return -1;
	}
	
	/**
	 * Compares the number of cards in this and another move
	 * @param arg0 the other move to compare to
	 * @return
	 *     positive integer if this has more cards than arg0
	 *     0 if this has the same amount of cards as arg0
	 *     negative integer if this has less cards than arg0
	 */
	public int compareSizeTo(Move arg0){
	    return this.getCards().size()-arg0.getCards().size();
	}
	
	   /**
     * Compares the rank of cards cards in this and another move
     * @param arg0 the other move to compare to
     * @return
     *     positive integer if this has a greater rank than arg0
     *     0 if this has the same rank as arg0
     *     negative integer if this has a smaller rank than arg0
     */
    public int compareRankTo(Move arg0){
        return this.getRank()-arg0.getRank();
    }

}
