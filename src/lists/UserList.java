package lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import player.Player;

/**
 * This class is a generic class that holds a list of users (clients)
 * defines methods to add, remove, and other potentially helpful things or possibly useless ideas
 * like ability to message the entire list synchronysly 
 */
public abstract class UserList
{
	//protected Map<String, Player> players;
	protected List<Player> players;
	
	/*
	 * constructor to init list
	 */
	public UserList()
	{
		this.players = new LinkedList<Player>();
	}
	
	/*
	 * method to see if an entry exists in this list
	 * @param
	 * 	Player - the player to be checked
	 * @return
	 * 	bololean - if the player exists
	 * 
	 * note, if we make this pubic, synchronize on players.
	 */
	private boolean contains(Player player)
	{
		//return players.contains(player);
		for (Player p : players)
			if (player.equals(p))
				return true;
		return false;
	}

	/*
	 * method to remove a player from the list, will update everyone of the change 
	 * @param 
	 * 	Player - Player to be removed
	 */
	public void remove(Player player)
	{
		synchronized(players)
		{
			players.remove(player);
			informAll(getList());
		}
	}

	/*
	 * method to add a player to the list, will update everyone of the change
	 * @param 
	 * 	Player - Player to be added
	 * @throws
	 * 	IOException - if the player cannot be added 
	 */
	public void add(Player player) throws IOException
	{
		synchronized(players)
		{
			if(this.contains(player))
				throw new IOException("Username Already Exists");
			players.add(player);
			informAll(getList());
		}
	}

	/*
	 * accessor to return the size of the list
	 * @return 
	 * 	int - number of people in the list
	 */
	public int size()
	{
		return players.size();
	}
	
	/*
	 * method to inform everyone on the list with a message
	 * @param
	 *  String - message to be sent to everyone
	 */
	public void informAll(String message)
	{
		Player[] copy;
		//make a copy of the list to work with... so we dont sacrifice throughput by long locks
		synchronized (players)
		{
			copy = players.toArray(new Player[0]);
		}
		
		//spam everyone
		for(Player player : copy)
			player.updateQueue(message);
	}

	/*
	 * returns a string represenation of everyone in the list
	 * shouldnt need synchornization - used only by class and subclasses
	 * @return String - formateted list of users in this list
	 */
	private String getList()
	{
		if(this.size() <= 0)
			return "";
		StringBuilder output = new StringBuilder("");
		ArrayList<String> copy = new ArrayList<String>();
		
		for(Player p : players)
			copy.add(new String(p.name));
		
		Collections.sort(copy);
		for(String s : copy)
			output.append(s + " ");
		return output.substring(0, output.length() -1);
	}

}
