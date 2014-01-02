package lists;

import java.util.concurrent.atomic.AtomicInteger;

import cards.Card;
import player.Player;

public class GameUserList extends UserList
{
	private AtomicInteger playerIndex= new AtomicInteger(0);
	
	/**
	 * @return current Player as determined by playerIndex
	 */
	public Player getCurrentPlayer()
	{
		return this.players.get(playerIndex.get());
	}

	/**
	 * Increments player index by 1. 
	 * To allow for rotation through players, when 
	 * playerIndex= this.players.size(), reset to 0. 
	 */
	public synchronized void incrementPlayer(){
		playerIndex.incrementAndGet();
		playerIndex.compareAndSet(this.players.size(),0);
	}
	
	/**
	 * Increments player index by 1, and gets the corresponding player 
	 * To allow for rotation through players, when 
	 * playerIndex= this.players.size(), reset to 0. 
	 */
	public synchronized Player incrementAndGetPlayer(){
		playerIndex.incrementAndGet();
		playerIndex.compareAndSet(this.players.size(),0);
		return this.getCurrentPlayer();
	}
	
	/**
	 * Finds player in list, and resets to that position
	 * If player not found, it keeps the original position
	 * @param p Player to find
	 * @return index of player, or -1 if not found
	 */
	public synchronized int setCounter(Player p){
		if (players.contains(p))
		{
			playerIndex.set(players.indexOf(p));
			return playerIndex.get();
		}
		return -1;
	}
	
	/**
	 * Find the (first) player with a given card
	 * @param c the Card to find in player's cards
	 * @return the first Player in the list with the card. 
	 */
	public synchronized Player findPlayerWith(Card c){
	    for (int i=0; i<this.size(); i++){
	        Player p= this.players.get(i);
	        synchronized(p){
	            if (p.getHand().contains(c)) return p;
	        }
	    }
	    return null;//card not found in any player's hand
	}
	
}
