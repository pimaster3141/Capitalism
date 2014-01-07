package lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import game.Game;

/**
 * Class to hold the games that are ongoing. 
 * will define methods for adding and removing games from the server
 * will also handle updating all the users of any changes in the listings
 * 
 * This Implementation is designed to be thread safe
 *
 */

public class GameList 
{
	private final ServerUserList users;		//List of all the users on the server
	private final Map<String, Game> games;	//structure to hold all the games
	
	/*
	 * constructor for GameList
	 * @pram 
	 * 	ServerUserList - master list of all people on server
	 * 	(used to inform everyone on server)
	 */
	public GameList(ServerUserList users)
	{
		this.users = users;
		this.games = new HashMap<String, Game>();
	}

	/*
	 * method to add a game to the list and inform everyone of the change
	 * @param 
	 * 	Game - game to be added
	 * @throws 
	 * 	IOException - if the game already exists
	 */
	public synchronized void add(Game game)throws IOException
	{
		//check if game(name) is valid (not used)
		if(this.contains(game.name))
			throw new IOException("Game already exists");
		//Add the game
		games.put(game.name,  game);
		//Tell everyone of the goodies
		users.informAll(getGames());
		return;
	}

	/*
	 * method to remove a game in the list and informs everyone of the change
	 */
	public void remove(Game game)
	{
		games.remove(game.name);
		//inform everyone 
		users.informAll(getGames());
		return;
	}

	/*
	 * returns a string representation of all the games following transmit grammar
	 * @return 
	 * 	String - string list of all the names of the games in the list
	 */
	private String getGames()
	{
		StringBuilder gameList = new StringBuilder("some key word to be sent to the client");
		ArrayList<String> copy = new ArrayList<String>();
		for (String gameString : games.keySet())
			copy.add(new String(gameString));
		Collections.sort(copy);
		for(String s : copy)
			gameList.append(s + ' ');
		gameList.deleteCharAt(gameList.length() - 1);
		return gameList.toString();		
	}

	/*
	 * helper method to test if a game already exists
	 * @param 
	 * 	String - name of game
	 * @return 
	 * 	boolean - if the game exists or not
	 */
	private synchronized boolean contains(String name)
	{
		return games.containsKey(name);
	}
	
	/*
	 * Accessor to get the game object from a name TODO: do smething with null returns
	 * @param 
	 * 	String - name of the game to be found
	 * @return 
	 * 	Game - game object 
	 */
	public synchronized Game getGameFromName(String gameName) throws IOException
	{
		if (!this.contains(gameName))
			throw new IOException ("Game does not exist");
		return games.get(gameName);
	}

}
