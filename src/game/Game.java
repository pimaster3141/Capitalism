package game;

import lists.GameList;
import lists.GameUserList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import player.HumanPlayer;
import player.Player;
import cards.Card;
import cards.Deck;

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
	private int numDecks;		//number of decks to play with 
	private final int numPlayers;			//number of players to be expecting
	private LinkedBlockingQueue<Move> moveQueue = new LinkedBlockingQueue<Move>();	//queue to allow non sequential moves
	private boolean alive = true;	//boolean if the game is still going
	private Move lastMove=null; //can't count passes
	//private Player playerTurn; //accepted your removal, just need to replace it everywhere
	private final Card START_CARD = new Card("diamond", 3);
	private ArrayList<Card> pile= new ArrayList<Card>();
	private AtomicInteger consecPasses=new AtomicInteger(0);
	private ArrayList<Player> hierarchy= new ArrayList<Player>();
	private final int CLEAR_RANK=2;
	private Deck deck;
	
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
	public Game(String name, GameList games, int numHuman, int numDecks) throws IOException
	{
		this.name = name;
		this.games = games;
		this.numPlayers = numHuman;
		
        try {
            this.deck= new Deck(numDecks);
            this.numDecks = numDecks;
        } catch (Exception e) {
            this.deck= new Deck();
            this.numDecks=1;
        }
		
		this.players = new GameUserList();
//		synchronized(players)
//		{
//			games.add(this);
//			creator.updateQueue("Someting to say they created a room");
//			players.add(creator);
//		}
//		
//		this.start();
	}
	
	/*
	 * adds a user to this game ONLY TO BE CALLED THROUGH PLAYER.JOINGAME
	 * @param
	 * 	HumanPlayer - player to be added
	 * @Throws
	 * 	IOException - if the player cannot be added - like if it is cheating and trying to join multiple times 
	 */
	public void addUser(Player player) throws IOException
	{
		synchronized(players)
		{
			if(alive)
			{
				players.add(player);
				player.updateQueue("something to say they joined properly");
			}
			else
				throw new IOException("Room does not exist");
			players.notify();
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
	 * Adds the cards in a player's move to the pile. 
	 * Lets the player know that the moves were accepted, 
	 * so it removes those cards from its collection
	 * @param move Move containing player and cards played
	 */
	public void playValidMove(Move move)
	{
	    pile.addAll(move.getCards());
        //TODO do we want to be specific and tell it which cards to remove from its hand?
	    move.getPlayer().updateQueue("move accepted");
	}
	
	/**
	 * Tell the player of move that it was not accepted.
	 * @param move, Move containing player
	 */
	public void wagFinger(Move move){
	    //TODO do we want to be specific and tell it which cards to keep?
        move.getPlayer().updateQueue("invalid move");	    
	}
	
	/**
	 * helper method to validate if a move is valid only on normal turn
	 * @param
	 * 	Move - move to be tested against lastMove
	 * @return
	 * 	boolean - is the move valid on a normal turn? 
	 * Must be the player's turn to be true. 
	 * Additionally, to be true, must have at least as many cards as 
	 * the last move, and a strictly bigger rank.
	 * Or can be a 2 of any size (clear the deck). 
	 */
	private boolean isValidOnTurn(Move move)
	{
	    //make sure it is the player's turn
		if (move.getPlayer().equals(this.players.getCurrentPlayer())){
		    //allow for passes
		    if (move.getCards().isEmpty()) return true;
		    //special ranks (ex. 2)
		    if (move.getRank()==CLEAR_RANK){
		        //valid regardless of size, but can't start a pile
		        return (!pile.isEmpty());
		    }
		    //else follow standard conditions
			return move.compareSizeTo(lastMove)>=0 && 
			    move.compareRankTo(lastMove)>0;
		}
		//invalid if it's not the player's turn
		return false;
	}
	
	/**
	 * Sees if a move is valid in spam case
	 * @param move
	 * @return
	 *  true if it completes the previous rank
	 *  false otherwise
	 */
	private boolean isValidSpam(Move move){
	    //TODO verify that hand is actually completed
		return move.compareRankTo(lastMove)==0;
	}

	
	/**
	 * Handles normal turns, playing cards and storing the move
	 * @param m Move that was played on turn (includes pass)
	 */
	private void doTurn(Move m){
	    if (m.getCards().isEmpty()){//pass
	        consecPasses.incrementAndGet();
	        //clear pile if >=2 consecutive passes
	        if (consecPasses.compareAndSet(2, 0)){
	            pile.clear();
	        }	        
	    }
	    else{//not a pass
	        pile.addAll(m.getCards());
	        lastMove=m;	        
	    }
        //Iterate to next person
	    try{
	        this.players.incrementPlayer();
	        
	    }
	}

	/**
	 * Handles special spam case, clearing pile and resetting counters
	 * @param m Spam move that was played
	 */
	private void doSpam(Move m){
        pile.addAll(m.getCards());
        lastMove=m;
        //assuming it really was a spam
        pile.clear();
        consecPasses.set(0);
        //spammer gets to play another turn
        this.players.setCounter(m.getPlayer());
	}
	
	/**
	 * Distribute deck among all players of game
	 */
	private synchronized void distributeDeck(){
        final int numCards=this.deck.divide(numPlayers);
	    for (int i=0; i<this.numPlayers; i++){
	        ArrayList<Card> hand= this.deck.dealCards(numCards);
	        this.players.incrementAndGetPlayer().setHand(hand);
	    }
	}
	
	private void addToHierarchy(Player p){
	    hierarchy.add(p);
	    //TODO remove p from play
	    this.players.wonGame(p);
	}
	
	/**
	 * Runs the Game, from accepting players to actually playing rounds of the game
	 */
	public void run()
	{
		try
		{
			//block until all players connect
			synchronized(players)
			{
				while(players.size() < numPlayers)
					players.wait();
			}
			
			//start mainloop - now in a game
			System.out.println("starting game");
			
			while(alive){//Looping rounds
	            //At the beginning of each round: distribute deck, and find player with start card
	            this.distributeDeck(); //deal cards
	            players.setCounter(this.players.findPlayerWith(START_CARD));
	            
	            //Playing the game
	            while(alive)//Looping moves in game
	            {
	                //play game
	                Move m = moveQueue.take();
	                if(this.isValidOnTurn(m))
	                    this.doTurn(m);
	                else if (this.isValidSpam(m))
	                    this.doSpam(m);
	                //see if the player that just played won (empty hand)
	                if (m.getPlayer().getHand().isEmpty()){
	                    this.addToHierarchy(m.getPlayer());
	                }
	                //see if hierarchy is full?
	            }			    
			}
		}
		catch(InterruptedException e)
		{
			//do something... don't know yet
		}
		
		cleanup();
	}

}
