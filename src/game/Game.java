package game;

import lists.GameList;
import lists.GameUserList;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import player.HumanPlayer;
import player.Player;

/**
 * Class implementing a 'game' of Capitalism 
 * Game is self contained, can start and stop itself 
 *
 */
public class Game implements Runnable
{
	public final String name;		//name of game
	private GameList games;			//pointer to list of games
	private GameUserList players;	//players in game
	private final int numDecks;		//number of decks to play with 
	private int numPlayers;			//number of players to be expecting
	private LinkedBlockingQueue<Move> moveQueue = new LinkedBlockingQueue<Move>();	//queue to allow non sequential moves
	private Thread self;			//reference to self 
	private boolean alive = true;	//boolean if the game is still going
	
	/*
	 * constructor for game - will attempt to adde self to game listing,
	 * and will throw an exception if it cant... like if the list rejects it because its a phony(copy)
	 * @param 
	 * 	String - name of game
	 * 	GameList - list of all the games
	 * 	HumanPlayer - initial player to be added
	 * 	int - number of players to expect
	 * 	int - number of decks to play with 
	 * @throws
	 * 	IOException - if the creation fails
	 */
	public Game(String name, GameList games, HumanPlayer creator, int numHuman, int numDecks) throws IOException
	{
		this.name = name;
		this.games = games;
		this.numPlayers = numHuman;
		this.numDecks = numDecks;
		
		this.players = new GameUserList();
		synchronized(players)
		{
			games.add(this);
			creator.updateQueue("Someting to say they created a room");
			players.add(creator);
			self = new Thread(this);
			self.start();
		}
	}
	
	/*
	 * adds a user to this game
	 * @param
	 * 	HumanPlayer - player to be added
	 * @Throws
	 * 	IOException - if the player cannot be added - like if it is cheating and trying to join multple times 
	 */
	public void addUser(HumanPlayer player) throws IOException
	{
		synchronized(players)
		{
			if(alive)
			{
				if(!players.contains(player))
					player.updateQueue("something to say they joined properly");
				players.add(player);
			}
			else
				throw new IOException("Room does not exist");
		}
	}
	
	/*
	 * removes a user from this game
	 * Will stop the game if there are no players involved
	 * 
	 * @param 
	 * 	Player - player to be removed 
	 */
	public void removeUser(Player player)
	{
		synchronized(players)
		{
			players.remove(player);
			if(players.size() <= 0)
			{
				alive = false;
				self.interrupt();
			}
		}
	}
	
	/*
	 * Method to cleanly remove the game from... everything
	 */
	private void cleanup()
	{
		//stuff to tell the squirrles that the room is gone
		games.remove(this);
	}
	
	/*
	 * method to allow players to submit moves to the queue
	 * @param
	 * 	Move - move to be sumitted (and then consumed and see if its spat out)
	 */
	public void queueMove(Move move)
	{
		moveQueue.add(move);
	}
	
	/*
	 * returns if this game is still alive (does not check if the game is still being played)
	 * @return
	 * 	boolean - if the game is still alive
	 */
	public boolean isAlive()
	{
		return this.self.isAlive();
	}
	
	/*
	 * helper method to validate if a move is valid
	 * @param
	 * 	Move - move to be tested
	 * @return
	 * 	boolean - is the move good to go?
	 */
	private boolean isValidMove(Move move) // need to logic somewhere....
	{
		return false;
	}
	
	public void run()
	{
		//stuff to accept players, init mainloop, and trading
		
	}

}
