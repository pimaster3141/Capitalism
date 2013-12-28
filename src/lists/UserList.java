package lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import player.Player;

/**
 * This class is a generic class that holds a list of users (clients)
 * defines methods to add, remove, and other potentially helpful things or possibly useless ideas
 * like ability to message the entire list synchronysly 
 */
public abstract class UserList
{
	private Map<String, Player> players;
	
	/*
	 * constructor to init list
	 */
	public UserList()
	{
		this.players = new HashMap<String, Player>();
	}
	
	/*
	 * method to see if an entry exists in this list
	 * @param
	 * 	Player - the player to be checked
	 * @return
	 * 	bololean - if the player exists
	 */
	public boolean contains(Player player)
	{
		return players.containsKey(player.name);
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
			players.remove(player.name);
			informAll(getList());
			return;
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
			players.put(player.name, player);
			informAll(getList());
			return;
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
			copy = players.values().toArray(new Player[0]);
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
	protected String getList()
	{
		if(this.size() <= 0)
			return "";
		StringBuilder output = new StringBuilder("");
		ArrayList<String> copy = new ArrayList<String>();
		for(String s : players.keySet())
			copy.add(new String(s));
		Collections.sort(copy);
		for(String s : copy)
			output.append(s + " ");
		return output.substring(0, output.length() -1);
	}

}
