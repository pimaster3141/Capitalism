package lists;

import java.util.concurrent.atomic.AtomicInteger;

import player.Player;

public class GameUserList extends UserList
{
	private AtomicInteger playerIndex= new AtomicInteger(0);
	
	/**
	 * @return current Player as determined by playerIndex
	 */
	public Player getCurrentPlayer()
	{
		//return (Player) this.players.values().toArray()[playerIndex.get()];
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
	 * Finds player in map, and resets to that position
	 * If player not found, it resets to the original position
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
		
//		final int maxIterations=this.players.size();
//		for (int i=0; i<maxIterations; i++){
//			if (this.getCurrentPlayer().equals(p)){
//				return this.playerIndex.get();
//			}
//			else{
//				this.incrementPlayer();
//			}
//		}
//		return -1; //:( not found
	}
	
}
