package lists;

import java.io.IOException;
import java.util.ArrayList;
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
	 * When the player has "won", take it out of play
	 * @param player
	 */
	public void wonGame(Player player){
	    player.setPlay(false);
	}
	
	/**
	 * When there is a new round, put all players back in play
	 * (can do other things too)
	 */
	public void newRound(){
	    for (Player p: players){
	        p.setPlay(true);
	    }
	}

	/**
	 * Increments player index by 1. 
	 * To allow for rotation through players, when 
	 * playerIndex= this.players.size(), reset to 0. 
	 * @throws Exception 
	 */
	public synchronized void incrementPlayer() throws Exception{
        playerIndex.incrementAndGet();
        playerIndex.compareAndSet(this.players.size(),0);
        int iter=0;
		while (!this.getCurrentPlayer().inPlay() && iter<this.players.size()){
		    playerIndex.incrementAndGet();
	        playerIndex.compareAndSet(this.players.size(),0);
	        iter++;
		}
		if (iter==this.players.size()) throw new Exception("no more players in play");
	}
	
	private void incrementHelper(){
	}
	
	/**
	 * Increments player index by 1, and gets the corresponding player 
	 * To allow for rotation through players, when 
	 * playerIndex= this.players.size(), reset to 0. 
	 * @throws Exception 
	 */
	public synchronized Player incrementAndGetPlayer() throws Exception{
		this.incrementPlayer();
		return this.getCurrentPlayer();
	}
	
	/**
	 * Finds player in list, and resets to that position
	 * If player not found, it keeps the original position
	 * @param p Player to find
	 * @return index of player, or -1 if not found or not in play
	 */
	public synchronized int setCounter(Player p){
		if (players.contains(p)&& p.inPlay())
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
	public Player findPlayerWith(Card c){
		synchronized(players)
		{
			for(Player p : players)
				if(p.getHand().contains(c))
					return p;
		}
		return null;//card not found in any player's hand
	}
	
}
