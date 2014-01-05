package player;

import game.Game;
import game.Move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import cards.Card;

/**
 * 
 * This class is a generic player class
 * defines basic functions to play a game
 * extended by human and AI
 * 
 * 
 * 
 * Just notes on how this works. 
 * Players join games strictly by the joinGame and leaveGame methods
 * 
 * subclasses (and other classes have no visibility to game, pendingMove or the queues - they are all handled in house
 * 		id also like to try to try to keep hand in house too (without accessors) but if its too hard, then screw it. 
 * 
 * Cards are removed from the hand when attempting a move. so if a move is accepted in the game, a valid check for if they are done (which
 * 		needs to be done before processing ANY other moves is if hand is empty via boolean isEmptyHanded()
 * 
 * InPlay needs to be updated the same time when assigning a ranking. (like when you are doing the above check to
 * 		prevent increment errors.
 * 
 * Game states are distributed to every player on every move. player will decide if they are useful or not
 * 		via the preprocess and processState methods
 * 
 * moves are submitted only via the makeMove command... i guess there isnt any other way to submit a move since game is private... lol
 * 
 * TODO - depricate the depricated method updateQueue
 *
 */
public abstract class Player implements Runnable 
{
	public final String name;		//name of player
	private ArrayList<Card> hand;	//listing of cards held
	private Game game;				//game player is in
	private boolean inPlay = false;	//if the player is still playing (has cards)
	private boolean pendingMove = false;	//if the player is waiting for a response from game
	private LinkedBlockingQueue<GameState> responses;	//buffer for game updates from game
	private Thread stateConsumer;	//consumer of above buffer
	
	/*
	 * Generic constructor, initialize fields
	 * @param
	 *  String - the name of the player
	 */
	public Player(String name)
	{
		this.name = name;	
		this.hand = new ArrayList<Card>();
		
	}
	
	/**
	 * Compares players for equality (based on name)
	 * @param other - other Player to compare to
	 * @return - are they the same russians?
	 */
	public boolean equals(Player other)
	{
		return this.name.equals(other.name);
	}
	
	public boolean isEmptyHanded()
	{
		synchronized(hand)
		{
			return (this.hand.size() == 0);
		}
	}
	
	/**
	 * Used by Game to push a state to this player
	 * @param s - state contaning the updateed game
	 */
	public void pushState(GameState s)
	{
		if (game == null)
			throw new IllegalArgumentException("Null game??");
		else
			responses.add(s);
	}
	
	/**
	 * Used by players to join a game
	 * Starts the consumer for game states
	 * @param g - game to join
	 * @throws IOException - if trying to join a game when in a game
	 */
	public void joinGame(Game g) throws IOException
	{
		synchronized(game)
		{
			//check if in a game
			if (game != null)
				throw new IOException("your game state isnt clear");
			else
			{
				this.game = g;
				//instantiate a new consumer for the game buffer
				stateConsumer = new Thread()
				{
					public void run()
					{
						System.out.println("Player " + name + " starting state consumer for game: " + game.name);
						while(game != null)
						{
							try
							{	//process the state updates
								preProcessState(responses.take());
							}
							catch (InterruptedException e)
							{	//stop the consumer
								System.out.println("Player " + name + " stopping state consumer for game: " + game.name);
								break;
							}
						}
					}
				};
				
				//clear the buffer for init
				responses.clear();
				//add self to the gameLists (update GUL)
				this.game.addUser(this);
				//start consumer
				stateConsumer.start();
			}
		}
	}
	
	/**
	 * Leaves a game cleanly, updates fields and stops consumer
	 */
	public void leaveGame()
	{
		synchronized(game)
		{
			//check if you are in a game
			if (game == null)
				throw new IllegalArgumentException("Null game???");
			else
			{
				//update GUL
				this.game.removeUser(this);
				
				//cleanup the consumer
				this.game = null;
				responses.clear();
				stateConsumer.interrupt(); //halt consumer
				this.reset(); //reset the playerState
			}
		}			
	}
	
	/*
	 * Resets the player for he next round
	 */
	public void reset()
	{
		responses.clear();
		synchronized(hand)
		{
			hand.clear();
		}
		inPlay = false;
		pendingMove = false;
	}
	
	/*
	 * Returns a shallow copy of the players hand
	 * @param
	 *  ArrayList<Card> - Copy of hand
	 */
	public ArrayList<Card> getHand()
	{
		synchronized(hand)
		{
			return new ArrayList<Card>(hand);
		}
	}
	
	/**
	 * Processes a move command, updates the hand to reflect the move and sets 'locks'
	 * to prevent multiple move submissions. 
	 * @param m - move to push to the game
	 * @throws IOException - if you are currently waiting for a ressponse from the game
	 */
	protected synchronized void makeMove (Move m) throws IOException
	{
		if (pendingMove)
			throw new IOException("chillax, you're waiting for a move");
		synchronized (hand)
		{
			//remove cards from hand
			for (Card c : m.getCards())
				hand.remove(hand.indexOf(c));
		}
		//set lock
		pendingMove = true;
		//push move
		game.queueMove(m);	
	}
	
	/**
	 * filters game state updates from the queue, resets the player for next move if necessary
	 * resets cards if needed (like if they screwed up and made a crappy move)
	 * @param s - state of the game as returned by game
	 */
	private synchronized void preProcessState(GameState s)
	{
		if(s.getPlayer().equals(this)) //check if the last move was this player
		{
			if(!s.accepted) //if this russian's move was not accepted 
				synchronized(hand)
				{
					hand.addAll(s.getLastMove()); //you screwed up, you get your cards back
				}
			pendingMove = false; //take off the lock so you can make new moves
		}
		processState(s);	//postprocess state
	}
	
	/**
	 * Warning: can only be set once
	 * @param c the desired hand to set
	 * @return true if hand was set, else false if there was an existing non-empty hand
	 */
	public boolean setHand(ArrayList<Card> c)
	{
		synchronized(hand)
		{
			if (this.hand.isEmpty())
			{
				this.hand = new ArrayList<Card>(c);
				inPlay = true;
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Stuff
	 * @param play - stuff
	 */
	public void setPlay(boolean play)
	{
	    inPlay=play;
	}
	
	/**
	 * Are we done yet?
	 * @return - DONE??!
	 */
	public boolean inPlay(){
	    return inPlay;
	}
	
	/**
	 * Signature for string messages to be sent to remote clients 
	 * DEPRICATED - TRY NOT TO USE THIS CUZ I KINDA WANT TO GET RID OF IT
	 * @param message - stuff you want to tell Russia
	 */
	public abstract void updateQueue(String message);
	
	/**
	 * Signature to process game updates
	 * @param s - the updated state of the game
	 */
	protected abstract void processState (GameState s);
}
