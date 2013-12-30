package game;

import lists.GameList;
import lists.GameUserList;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import player.HumanPlayer;
import player.Player;

/**
 * Class implementing a 'game' of Capitalism 
 * Game is a self contained thread, can start and stop itself 
 *
 */
public class Game extends Thread
{
	public final String name;		//name of game
	private GameList games;			//pointer to list of games
	private GameUserList players;	//players in game
	private final int numDecks;		//number of decks to play with 
	private int numPlayers;			//number of players to be expecting
	private LinkedBlockingQueue<Move> moveQueue = new LinkedBlockingQueue<Move>();	//queue to allow non sequential moves
	private boolean alive = true;	//boolean if the game is still going
	private Move lastMove=null; //can't count passes
	private Player playerTurn;
	
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
		}
		this.start();
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
				this.interrupt();
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
	 * 	Move - move to be submitted (and then consumed and see if its spat out)
	 */
	public void queueMove(Move move)
	{
		moveQueue.add(move);
	}
	
	/**
	 * helper method to validate if a move is valid
	 * @param
	 * 	Move - move to be tested against lastMove
	 * @return
	 * 	boolean - is the move good to go?
	 */
	private boolean isValidMove(Move move) // need to logic somewhere....
	{
		//normal turn
		if (move.getPlayer().equals(playerTurn)){
			return move.compareTo(lastMove)>0;
		}
		//spam case: not the player's turn
		return move.compareTo(lastMove)==0;
	}
	
	public void run()
	{
		//stuff to accept players, init mainloop, and trading
		
	}

}
