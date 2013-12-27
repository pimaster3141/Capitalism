package game;

import lists.GameList;
import lists.GameUserList;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import player.HumanPlayer;
import player.Player;

public class Game implements Runnable
{
	public final String name;
	private GameList games;
	private GameUserList players;
	private final int numDecks;
	private int numPlayers;
	private LinkedBlockingQueue<Move> moveQueue = new LinkedBlockingQueue<Move>();
	private Thread self;
	private boolean alive = true;
	
	public Game(String name, GameList games, HumanPlayer creator, int numHuman, int numDecks)
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
	
	private void cleanup()
	{
		//stuff to tell the squirrles that the room is gone
		games.remove(this);
	}
	
	public void queueMove(Move move)
	{
		moveQueue.add(move);
	}
	
	public boolean isAlive()
	{
		return this.self.isAlive();
	}
	
	private boolean isValidMove(Move move) // need to logic somewhere....
	{
		return false;
	}
	
	public void run()
	{
		//stuff to accept players, init mainloop, and trading
		
	}

}
